package br.com.j4business.saga.elementoquestao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.elemento.model.ElementoForm;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.questao.model.QuestaoForm;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestao;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestaoByElementoForm;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestaoForm;
import br.com.j4business.saga.elementoquestao.service.ElementoQuestaoService;

@Controller
public class ElementoQuestaoController {

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ElementoQuestaoService elementoQuestaoService;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/elementoQuestaoAdd", method = RequestMethod.GET)
	public ModelAndView elementoQuestaoAdd(ElementoQuestaoForm elementoQuestaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("elementoQuestao/elementoQuestaoAdd");
		elementoQuestaoForm = elementoQuestaoService.elementoQuestaoParametros(elementoQuestaoForm);
		mv.addObject("elementoQuestaoForm", elementoQuestaoForm);
		mv.addObject("elementoQuestaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("elementoQuestaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoPageable = new PageRequest(0, 200, Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(elementoPageable));
		Pageable questaoPageable = new PageRequest(0, 200, Direction.ASC, "questaoNome");
		mv.addObject("questaoPage", questaoService.getQuestaoAll(questaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/elementoQuestaoCreate", method = RequestMethod.POST)
	public ModelAndView elementoQuestaoCreate(@Valid ElementoQuestaoForm elementoQuestaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return elementoQuestaoAdd(elementoQuestaoForm,pageable);
		}

		if (elementoQuestaoForm.getElementoQuestaoPK() > 0) {
			return this.elementoQuestaoSave(elementoQuestaoForm, result, attributes,pageable);
			
		}
		
		try {
			elementoQuestaoService.create(elementoQuestaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("elementoQuestaoUnique")) {
			        ObjectError error = new ObjectError("elementoNome","Relacionamento entre Elemento e Questão já existente no cadastro.");
			        result.addError(error);			
			}
            
			return elementoQuestaoAdd(elementoQuestaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/elementoQuestaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/elementoQuestaoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView elementoQuestaoDelete(@PathVariable("id") long elementoQuestaoId, @Valid QuestaoForm questaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/elementoQuestaoHome");
		
		
		ElementoQuestao elementoQuestao = elementoQuestaoService.getElementoQuestaoByElementoQuestaoPK(elementoQuestaoId);
		try {
			elementoQuestaoService.delete(elementoQuestaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Elemento/Questao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Elemento/Questao não excluído. Existe(m) relacionamento(s) de Elemento/Questao ** "+ 
										  elementoQuestao.getElemento().getElementoNome() +
										  " / " +
										  elementoQuestao.getQuestao().getQuestaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/elementoQuestaoEdit/{elementoQuestaoPK}", method = RequestMethod.GET)
	public ModelAndView elementoQuestaoEdit(@PathVariable("elementoQuestaoPK") Long elementoQuestaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("elementoQuestao/elementoQuestaoEdit");
		ElementoQuestao elementoQuestao = elementoQuestaoService.getElementoQuestaoByElementoQuestaoPK(elementoQuestaoPK);
		ElementoQuestaoForm elementoQuestaoForm = elementoQuestaoService.converteElementoQuestao(elementoQuestao);
		mv.addObject("elementoQuestaoForm", elementoQuestaoForm);
		mv.addObject("elementoQuestaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("elementoQuestaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoPageable = new PageRequest(0, 200, Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(elementoPageable));
		Pageable questaoPageable = new PageRequest(0, 200, Direction.ASC, "questaoNome");
		mv.addObject("questaoPage", questaoService.getQuestaoAll(questaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/elementoQuestaoHome", method = RequestMethod.GET)
	public ModelAndView elementoQuestaoHome(@Valid ElementoQuestaoByElementoForm elementoQuestaoByElementoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("elementoQuestao/elementoQuestaoHome");
		
		List<ElementoQuestao> elementoQuestaoList = new ArrayList<ElementoQuestao>();
		
		int elementoQuestaoTotal = 0;
		
		if (elementoQuestaoByElementoForm.getSearchQuestaoNome() == null) {
			elementoQuestaoByElementoForm.setSearchElementoNome("");
			elementoQuestaoByElementoForm.setSearchQuestaoNome("");
			if (elementoQuestaoByElementoForm.getElementoQuestaoSortTipo() == null) {
				elementoQuestaoByElementoForm.setElementoQuestaoSortTipo("QuestaoNome");	
			}
			
		}

		if (elementoQuestaoByElementoForm.getElementoQuestaoSortTipo().equalsIgnoreCase("ElementoNome")
				|| elementoQuestaoByElementoForm.getElementoQuestaoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"elemento.elementoNome","questao.questaoNome"); 
		
		} else if (elementoQuestaoByElementoForm.getElementoQuestaoSortTipo().equalsIgnoreCase("QuestaoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"questao.questaoNome","elemento.elementoNome"); 

		}

		if ( ! elementoQuestaoByElementoForm.getSearchQuestaoNome().equalsIgnoreCase("")){
			elementoQuestaoList = elementoQuestaoService.getByQuestaoNome(elementoQuestaoByElementoForm.getSearchQuestaoNome(),pageable);
			elementoQuestaoTotal = elementoQuestaoService.getByQuestaoNome(elementoQuestaoByElementoForm.getSearchQuestaoNome()).size();
			
		} else {
			elementoQuestaoList = elementoQuestaoService.getByElementoNome(elementoQuestaoByElementoForm.getSearchElementoNome(),pageable);
			elementoQuestaoTotal = elementoQuestaoService.getByElementoNome(elementoQuestaoByElementoForm.getSearchElementoNome()).size();

		} 
		
		Page<ElementoQuestao> elementoQuestaoPage = new PageImpl<ElementoQuestao>(elementoQuestaoList,pageable,elementoQuestaoTotal+1);
		
		mv.addObject("elementoQuestaoPage", elementoQuestaoPage);
		mv.addObject("page",elementoQuestaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/elementoQuestaoSave", method = RequestMethod.POST)
	public ModelAndView elementoQuestaoSave(@Valid ElementoQuestaoForm elementoQuestaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return elementoQuestaoAdd(elementoQuestaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/elementoQuestaoHome");

		try {
			elementoQuestaoService.save(elementoQuestaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("elementoQuestaoUnique")) {
			        ObjectError error = new ObjectError("elementoNome","Relacionamento entre Elemento e Questão já existente no cadastro.");
			        result.addError(error);			
			}
            return elementoQuestaoAdd(elementoQuestaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/elementoQuestaoRelMenu", method = RequestMethod.GET)
	public ModelAndView elementoQuestaoRelMenu() {

		ModelAndView mv = new ModelAndView("elementoQuestao/elementoQuestaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/elementoQuestaoRel001")
	public ModelAndView elementoQuestaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("elementoQuestao/elementoQuestaoRel001");
		Pageable elementoQuestaoPageable = new PageRequest(0, 200, Direction.ASC, "elemento.elementoNome","questao.questaoNome");
		mv.addObject("elementoQuestaoPage", elementoQuestaoService.getElementoQuestaoAll(elementoQuestaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/elementoQuestaoView/{id}", method = RequestMethod.GET)
	public ModelAndView elementoQuestaoView(@PathVariable("id") Long elementoQuestaoId) {

		ElementoQuestao elementoQuestao = elementoQuestaoService.getElementoQuestaoByElementoQuestaoPK(elementoQuestaoId);
		ModelAndView mv = new ModelAndView("elementoQuestao/elementoQuestaoView");
		ElementoQuestaoForm elementoQuestaoForm = elementoQuestaoService.converteElementoQuestaoView(elementoQuestao);
		ElementoForm elementoForm = elementoService.converteElementoView(elementoQuestao.getElemento());
		QuestaoForm questaoForm = questaoService.converteQuestaoView(elementoQuestao.getQuestao());
		mv.addObject("elementoQuestaoForm", elementoQuestaoForm);
		mv.addObject("elementoForm", elementoForm);
		mv.addObject("questaoForm", questaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}