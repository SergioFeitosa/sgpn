package br.com.j4business.saga.questionarioquestao.controller;

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
import br.com.j4business.saga.questao.model.QuestaoForm;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.questionario.model.QuestionarioForm;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestao;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestaoByQuestionarioForm;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestaoForm;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;

@Controller
public class QuestionarioQuestaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private QuestionarioQuestaoService questionarioQuestaoService;

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/questionarioQuestaoAdd")
	public ModelAndView questionarioQuestaoAdd(QuestionarioQuestaoForm questionarioQuestaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionarioQuestao/questionarioQuestaoAdd");
		questionarioQuestaoForm = questionarioQuestaoService.questionarioQuestaoParametros(questionarioQuestaoForm);
		mv.addObject("questionarioQuestaoForm", questionarioQuestaoForm);
		mv.addObject("questionarioQuestaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questaoPageable = PageRequest.of(0, 200, Direction.ASC, "questaoNome");
		mv.addObject("questaoPage", questaoService.getQuestaoAll(questaoPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/questionarioQuestaoCreate")
	public ModelAndView questionarioQuestaoCreate(@Valid QuestionarioQuestaoForm questionarioQuestaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return questionarioQuestaoAdd(questionarioQuestaoForm,pageable);
		}

		if (questionarioQuestaoForm.getQuestionarioQuestaoPK() > 0) {
			return this.questionarioQuestaoSave(questionarioQuestaoForm, result, attributes,pageable);
			
		}
		
		try {
			questionarioQuestaoService.create(questionarioQuestaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("questionarioQuestaoUnique")) {
			        ObjectError error = new ObjectError("questionarioNome","Relacionamento entre Questionário e Questão já existente no cadastro.");
			        result.addError(error);			
			}
            
			return questionarioQuestaoAdd(questionarioQuestaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/questionarioQuestaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioQuestaoDelete/{id}")
	public ModelAndView goQuestionarioQuestaoDelete(@PathVariable("id") long questionarioQuestaoId, @Valid QuestaoForm questaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/questionarioQuestaoHome");
		
		
		QuestionarioQuestao questionarioQuestao = questionarioQuestaoService.getQuestionarioQuestaoByQuestionarioQuestaoPK(questionarioQuestaoId);
		try {
			questionarioQuestaoService.delete(questionarioQuestaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Questionario/Questao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Questionario/Questao não excluído. Existe(m) relacionamento(s) de Questionario/Questao ** "+ 
										  questionarioQuestao.getQuestionario().getQuestionarioNome() +
										  " / " +
										  questionarioQuestao.getQuestao().getQuestaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioQuestaoEdit/{questionarioQuestaoPK}")
	public ModelAndView goQuestionarioQuestaoEdit(@PathVariable("questionarioQuestaoPK") Long questionarioQuestaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionarioQuestao/questionarioQuestaoEdit");
		QuestionarioQuestao questionarioQuestao = questionarioQuestaoService.getQuestionarioQuestaoByQuestionarioQuestaoPK(questionarioQuestaoPK);
		QuestionarioQuestaoForm questionarioQuestaoForm = questionarioQuestaoService.converteQuestionarioQuestao(questionarioQuestao);
		mv.addObject("questionarioQuestaoForm", questionarioQuestaoForm);
		mv.addObject("questionarioQuestaoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questaoPageable = PageRequest.of(0, 200, Direction.ASC, "questaoNome");
		mv.addObject("questaoPage", questaoService.getQuestaoAll(questaoPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		
		questionarioQuestao.getQuestionarioQuestaoSequencia();
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/questionarioQuestaoHome")
	public ModelAndView goQuestionarioQuestaoHome(@Valid QuestionarioQuestaoByQuestionarioForm questionarioQuestaoByQuestionarioForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionarioQuestao/questionarioQuestaoHome");
		
		List<QuestionarioQuestao> questionarioQuestaoList = new ArrayList<QuestionarioQuestao>();
		
		int questionarioQuestaoTotal = 0;
		
		if (questionarioQuestaoByQuestionarioForm.getSearchQuestaoNome() == null) {
			questionarioQuestaoByQuestionarioForm.setSearchQuestionarioNome("");
			questionarioQuestaoByQuestionarioForm.setSearchQuestaoNome("");
			if (questionarioQuestaoByQuestionarioForm.getQuestionarioQuestaoSortTipo() == null) {
				questionarioQuestaoByQuestionarioForm.setQuestionarioQuestaoSortTipo("QuestaoNome");	
			}
			
		}

		if (questionarioQuestaoByQuestionarioForm.getQuestionarioQuestaoSortTipo().equalsIgnoreCase("QuestionarioNome")
				|| questionarioQuestaoByQuestionarioForm.getQuestionarioQuestaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"questionario.questionarioNome","questionarioQuestaoSequencia"); 
		
		} else if (questionarioQuestaoByQuestionarioForm.getQuestionarioQuestaoSortTipo().equalsIgnoreCase("QuestaoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"questao.questaoNome","questionarioQuestaoSequencia"); 

		} else if (questionarioQuestaoByQuestionarioForm.getQuestionarioQuestaoSortTipo().equalsIgnoreCase("Sequencia")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"questionarioQuestaoSequencia","questionario.questionarioNome"); 

		}

		if ( ! questionarioQuestaoByQuestionarioForm.getSearchQuestaoNome().equalsIgnoreCase("")){
			questionarioQuestaoList = questionarioQuestaoService.getByQuestaoNome(questionarioQuestaoByQuestionarioForm.getSearchQuestaoNome(),pageable);
			questionarioQuestaoTotal = questionarioQuestaoService.getByQuestaoNome(questionarioQuestaoByQuestionarioForm.getSearchQuestaoNome()).size();
		} else {
			questionarioQuestaoList = questionarioQuestaoService.getByQuestionarioNome(questionarioQuestaoByQuestionarioForm.getSearchQuestionarioNome(),pageable);
			questionarioQuestaoTotal = questionarioQuestaoService.getByQuestionarioNome(questionarioQuestaoByQuestionarioForm.getSearchQuestionarioNome()).size();
		}
		
		Page<QuestionarioQuestao> questionarioQuestaoPage = new PageImpl<QuestionarioQuestao>(questionarioQuestaoList,pageable,questionarioQuestaoTotal+1);
		
		mv.addObject("questionarioQuestaoPage", questionarioQuestaoPage);
		mv.addObject("page",questionarioQuestaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/questionarioQuestaoSave")
	public ModelAndView questionarioQuestaoSave(@Valid QuestionarioQuestaoForm questionarioQuestaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return questionarioQuestaoAdd(questionarioQuestaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/questionarioQuestaoHome");

		try {
			questionarioQuestaoService.save(questionarioQuestaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("questionarioQuestaoUnique")) {
		        ObjectError error = new ObjectError("questionarioNome","Relacionamento entre Questionário e Questão já existente no cadastro.");
		        result.addError(error);			
		}
            return questionarioQuestaoAdd(questionarioQuestaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/questionarioQuestaoRelMenu")
	public ModelAndView goQuestionarioQuestaoRelMenu() {

		ModelAndView mv = new ModelAndView("questionarioQuestao/questionarioQuestaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/questionarioQuestaoRel001")
	public ModelAndView goQuestionarioQuestaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("questionarioQuestao/questionarioQuestaoRel001");
		Pageable questionarioQuestaoPageable = PageRequest.of(0, 200, Direction.ASC, "questionario.questionarioNome","questao.questaoNome");
		mv.addObject("questionarioQuestaoPage", questionarioQuestaoService.getQuestionarioQuestaoAll(questionarioQuestaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/questionarioQuestaoView/{id}")
	public ModelAndView goQuestionarioQuestaoView(@PathVariable("id") Long questionarioQuestaoId) {

		QuestionarioQuestao questionarioQuestao = questionarioQuestaoService.getQuestionarioQuestaoByQuestionarioQuestaoPK(questionarioQuestaoId);
		ModelAndView mv = new ModelAndView("questionarioQuestao/questionarioQuestaoView");
		QuestionarioQuestaoForm questionarioQuestaoForm = questionarioQuestaoService.converteQuestionarioQuestaoView(questionarioQuestao);
		QuestionarioForm questionarioForm = questionarioService.converteQuestionarioView(questionarioQuestao.getQuestionario());
		QuestaoForm questaoForm = questaoService.converteQuestaoView(questionarioQuestao.getQuestao());
		mv.addObject("questionarioQuestaoForm", questionarioQuestaoForm);
		mv.addObject("questionarioForm", questionarioForm);
		mv.addObject("questaoForm", questaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}