package br.com.j4business.saga.questionario.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.model.QuestionarioByQuestionarioForm;
import br.com.j4business.saga.questionario.model.QuestionarioForm;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;

@Controller
public class QuestionarioController {

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private QuestionarioQuestaoService questionarioQuestaoService;

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/questionarioAdd")
	public ModelAndView questionarioAdd(QuestionarioForm questionarioForm) {

		ModelAndView mv = new ModelAndView("questionario/questionarioAdd");
		questionarioForm = questionarioService.questionarioParametros(questionarioForm);
		mv.addObject("questionarioForm", questionarioForm);
		mv.addObject("questionarioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/questionarioCreate")
	public ModelAndView questionarioCreate(@Valid QuestionarioForm questionarioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return questionarioAdd(questionarioForm);
		}

		if (questionarioForm.getQuestionarioPK() > 0) {
			return this.questionarioSave(questionarioForm, result, attributes);
			
		}
		
		try {
			questionarioService.create(questionarioForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("questionarioNome")) {
			        ObjectError error = new ObjectError("questionarioNome","Nome do Questionário já existente no cadastro.");
			        result.addError(error);			
			}
            
			return questionarioAdd(questionarioForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/questionarioHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioDelete/{id}")
	public ModelAndView questionarioDelete(@PathVariable("id") long questionarioPK, @Valid QuestionarioForm questionarioForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/questionarioHome");

		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(questionarioPK);
		try {
			questionarioService.delete(questionarioPK);

			attributes.addFlashAttribute("mensagem", "Questionário excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Questionário não excluída. Existe(m) relacionamento(s) de Questionário ** "+ questionario.getQuestionarioNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioEdit/{id}")
	public ModelAndView questionarioEdit(@PathVariable("id") Long questionarioPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionario/questionarioEdit");
		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(questionarioPK);
		QuestionarioForm questionarioForm = questionarioService.converteQuestionario(questionario);
		mv.addObject("questionarioForm", questionarioForm);
		mv.addObject("questionarioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable questionarioQuestaoPageable = PageRequest.of(0, 200, Direction.ASC, "questao.questaoNome");
		mv.addObject("questionarioQuestaoPage", questionarioQuestaoService.getByQuestionarioPK(questionarioPK, questionarioQuestaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioHome")
	public ModelAndView questionarioHome(@Valid QuestionarioByQuestionarioForm questionarioByQuestionarioForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionario/questionarioHome");
		
		List<Questionario> questionarioList = new ArrayList<Questionario>();

		int questionariosTotal = 0;
		
		if (questionarioByQuestionarioForm.getSearchQuestionarioNome() == null) {
			questionarioByQuestionarioForm.setSearchQuestionarioNome("");
			questionarioByQuestionarioForm.setSearchCenarioNome("");
			questionarioByQuestionarioForm.setSearchQuestionarioDescricao("");
			if (questionarioByQuestionarioForm.getQuestionarioSortTipo() == null) {
				questionarioByQuestionarioForm.setQuestionarioSortTipo("QuestionarioNome");
			}

		}

		if (questionarioByQuestionarioForm.getQuestionarioSortTipo().equalsIgnoreCase("QuestionarioNome") || questionarioByQuestionarioForm.getQuestionarioSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "questionarioNome");

		} else if (questionarioByQuestionarioForm.getQuestionarioSortTipo().equalsIgnoreCase("QuestionarioDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "questionarioDescricao");

		} else if (questionarioByQuestionarioForm.getQuestionarioSortTipo().equalsIgnoreCase("CenarioNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "cenario.cenarioNome");

		}

		if ((!questionarioByQuestionarioForm.getSearchQuestionarioNome().equalsIgnoreCase(""))) {
			questionarioList = questionarioService.getByQuestionarioNome(questionarioByQuestionarioForm.getSearchQuestionarioNome(), pageable);
			questionariosTotal = questionarioService.getByQuestionarioNome(questionarioByQuestionarioForm.getSearchQuestionarioNome()).size();

		} else if ((!questionarioByQuestionarioForm.getSearchQuestionarioDescricao().equalsIgnoreCase(""))) { 
			questionarioList = questionarioService.getByQuestionarioDescricao(questionarioByQuestionarioForm.getSearchQuestionarioDescricao(), pageable);
			questionariosTotal = questionarioService.getByQuestionarioDescricao(questionarioByQuestionarioForm.getSearchQuestionarioDescricao()).size();
		} else { 
			questionarioList = questionarioService.getByCenarioNome(questionarioByQuestionarioForm.getSearchCenarioNome(), pageable);
			questionariosTotal = questionarioService.getByCenarioNome(questionarioByQuestionarioForm.getSearchCenarioNome()).size();
		}

		Page<Questionario> questionarioPage = new PageImpl<Questionario>(questionarioList, pageable, questionariosTotal+1);

		mv.addObject("questionarioPage", questionarioPage);
		mv.addObject("page", questionarioPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioRelMenu")
	public ModelAndView questionarioRelMenu() {

		ModelAndView mv = new ModelAndView("questionario/questionarioRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping(path = "/questionarioRel001")
	public ModelAndView questionarioRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionario/questionarioRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/questionarioSave")
	public ModelAndView questionarioSave(@Valid QuestionarioForm questionarioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return questionarioAdd(questionarioForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/questionarioHome");

		try {
			questionarioService.save(questionarioForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("questionarioNome")) {
			        ObjectError error = new ObjectError("questionarioNome","Nome do Questionário já existente no cadastro.");
			        result.addError(error);			
			}
            return questionarioAdd(questionarioForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/questionarioView/{id}")
	public ModelAndView questionarioView(@PathVariable("id") Long questionarioId) {

		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(questionarioId);
		ModelAndView mv = new ModelAndView("questionario/questionarioView");
		QuestionarioForm questionarioForm = questionarioService.converteQuestionarioView(questionario);
		mv.addObject("questionarioForm", questionarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}