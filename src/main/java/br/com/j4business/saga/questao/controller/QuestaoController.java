package br.com.j4business.saga.questao.controller;

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
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questao.model.QuestaoByQuestaoForm;
import br.com.j4business.saga.questao.model.QuestaoForm;
import br.com.j4business.saga.questao.service.QuestaoService;

@Controller
public class QuestaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/questaoAdd")
	public ModelAndView questaoAdd(QuestaoForm questaoForm) {

		ModelAndView mv = new ModelAndView("questao/questaoAdd");
		questaoForm = questaoService.questaoParametros(questaoForm);
		mv.addObject("questaoForm", questaoForm);
		mv.addObject("questaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoPageable = PageRequest.of(0, 200, Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(elementoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/questaoCreate")
	public ModelAndView questaoCreate(@Valid QuestaoForm questaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return questaoAdd(questaoForm);
		}

		if (questaoForm.getQuestaoPK() > 0) {
			return this.questaoSave(questaoForm, result, attributes);
			
		}
		
		try {
			questaoService.create(questaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("questaoNome")) {
			        ObjectError error = new ObjectError("questaoNome","Nome da Questão já existente no cadastro.");
			        result.addError(error);			
			}
            
			return questaoAdd(questaoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/questaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questaoDelete/{id}")
	public ModelAndView questaoDelete(@PathVariable("id") long questaoPK, @Valid QuestaoForm questaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/questaoHome");

		Questao questao = questaoService.getQuestaoByQuestaoPK(questaoPK);
		try {
			questaoService.delete(questaoPK);

			attributes.addFlashAttribute("mensagem", "Questão excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Questão não excluída. Existe(m) relacionamento(s) de Questão ** "+ questao.getQuestaoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/questaoEdit/{id}")
	public ModelAndView questaoEdit(@PathVariable("id") Long questaoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("questao/questaoEdit");
		Questao questao = questaoService.getQuestaoByQuestaoPK(questaoId);
		QuestaoForm questaoForm = questaoService.converteQuestao(questao);
		mv.addObject("questaoForm", questaoForm);
		mv.addObject("questaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoPageable = PageRequest.of(0, 200, Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(elementoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questaoHome")
	public ModelAndView questaoHome(@Valid QuestaoByQuestaoForm questaoByQuestaoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("questao/questaoHome");
		
		List<Questao> questaoList = new ArrayList<Questao>();

		int questaosTotal = 0;
		
		if (questaoByQuestaoForm.getSearchQuestaoNome() == null) {
			questaoByQuestaoForm.setSearchQuestaoNome("");
			questaoByQuestaoForm.setSearchQuestaoDescricao("");
			if (questaoByQuestaoForm.getQuestaoSortTipo() == null) {
				questaoByQuestaoForm.setQuestaoSortTipo("QuestaoNome");
			}

		}

		if (questaoByQuestaoForm.getQuestaoSortTipo().equalsIgnoreCase("QuestaoNome") || questaoByQuestaoForm.getQuestaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "questaoNome");

		} else if (questaoByQuestaoForm.getQuestaoSortTipo().equalsIgnoreCase("QuestaoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "questaoDescricao");

		}

		if ((!questaoByQuestaoForm.getSearchQuestaoNome().equalsIgnoreCase(""))) {
			questaoList = questaoService.getByQuestaoNome(questaoByQuestaoForm.getSearchQuestaoNome(), pageable);
			questaosTotal = questaoService.getByQuestaoNome(questaoByQuestaoForm.getSearchQuestaoNome()).size();

		} else {
			questaoList = questaoService.getByQuestaoDescricao(questaoByQuestaoForm.getSearchQuestaoDescricao(), pageable);
			questaosTotal = questaoService.getByQuestaoDescricao(questaoByQuestaoForm.getSearchQuestaoDescricao()).size();
		}

		Page<Questao> questaoPage = new PageImpl<Questao>(questaoList, pageable, questaosTotal+1);

		mv.addObject("questaoPage", questaoPage);
		mv.addObject("page", questaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questaoRelMenu")
	public ModelAndView questaoRelMenu() {

		ModelAndView mv = new ModelAndView("questao/questaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping(path = "/questaoRel001")
	public ModelAndView questaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("questao/questaoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "questaoNome");
		mv.addObject("questaoPage", questaoService.getQuestaoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/questaoSave")
	public ModelAndView questaoSave(@Valid QuestaoForm questaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return questaoAdd(questaoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/questaoHome");

		try {
			questaoService.save(questaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("questaoNome")) {
			        ObjectError error = new ObjectError("questaoNome","Nome da Questão já existente no cadastro.");
			        result.addError(error);			
			}
            return questaoAdd(questaoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/questaoView/{id}")
	public ModelAndView questaoView(@PathVariable("id") Long questaoId) {

		Questao questao = questaoService.getQuestaoByQuestaoPK(questaoId);
		ModelAndView mv = new ModelAndView("questao/questaoView");
		QuestaoForm questaoForm = questaoService.converteQuestaoView(questao);
		mv.addObject("questaoForm", questaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}