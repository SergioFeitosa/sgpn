package br.com.j4business.saga.formacao.controller;

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

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.formacao.model.FormacaoByFormacaoForm;
import br.com.j4business.saga.formacao.model.FormacaoForm;
import br.com.j4business.saga.formacao.service.FormacaoService;

@Controller
public class FormacaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FormacaoService formacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/formacaoAdd", method = RequestMethod.GET)
	public ModelAndView formacaoAdd(FormacaoForm formacaoForm) {

		ModelAndView mv = new ModelAndView("formacao/formacaoAdd");
		formacaoForm = formacaoService.formacaoParametros(formacaoForm);
		mv.addObject("formacaoForm", formacaoForm);
		mv.addObject("formacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/formacaoCreate", method = RequestMethod.POST)
	public ModelAndView formacaoCreate(@Valid FormacaoForm formacaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return formacaoAdd(formacaoForm);
		}

		if (formacaoForm.getFormacaoPK() > 0) {
			return this.formacaoSave(formacaoForm, result, attributes);
			
		}
		
		try {
			formacaoService.create(formacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("formacaoNome")) {
			        ObjectError error = new ObjectError("formacaoNome","Nome da Formação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return formacaoAdd(formacaoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/formacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/formacaoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView formacaoDelete(@PathVariable("id") long formacaoPK, @Valid FormacaoForm formacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/formacaoHome");

		Formacao formacao = formacaoService.getFormacaoByFormacaoPK(formacaoPK);
		try {
			formacaoService.delete(formacaoPK);

			attributes.addFlashAttribute("mensagem", "Formação excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Formação não excluída. Existe(m) relacionamento(s) de Formação ** "+ formacao.getFormacaoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/formacaoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView formacaoEdit(@PathVariable("id") Long formacaoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("formacao/formacaoEdit");
		Formacao formacao = formacaoService.getFormacaoByFormacaoPK(formacaoId);
		FormacaoForm formacaoForm = formacaoService.converteFormacao(formacao);
		mv.addObject("formacaoForm", formacaoForm);
		mv.addObject("formacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/formacaoHome", method = RequestMethod.GET)
	public ModelAndView formacaoHome(@Valid FormacaoByFormacaoForm formacaoByFormacaoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("formacao/formacaoHome");
		
		List<Formacao> formacaoList = new ArrayList<Formacao>();

		int formacaoTotal = 0;
		
		if (formacaoByFormacaoForm.getSearchFormacaoNome() == null) {
			formacaoByFormacaoForm.setSearchFormacaoNome("");
			formacaoByFormacaoForm.setSearchFormacaoDescricao("");
			if (formacaoByFormacaoForm.getFormacaoSortTipo() == null) {
				formacaoByFormacaoForm.setFormacaoSortTipo("FormacaoNome");
			}

		}

		if (formacaoByFormacaoForm.getFormacaoSortTipo().equalsIgnoreCase("FormacaoNome") || formacaoByFormacaoForm.getFormacaoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "formacaoNome");

		} else if (formacaoByFormacaoForm.getFormacaoSortTipo().equalsIgnoreCase("FormacaoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "formacaoDescricao");

		}

		if ((!formacaoByFormacaoForm.getSearchFormacaoNome().equalsIgnoreCase(""))) {
			formacaoList = formacaoService.getByFormacaoNome(formacaoByFormacaoForm.getSearchFormacaoNome(), pageable);
			formacaoTotal = formacaoService.getByFormacaoNome(formacaoByFormacaoForm.getSearchFormacaoNome()).size();

		} else {
			formacaoList = formacaoService.getByFormacaoDescricao(formacaoByFormacaoForm.getSearchFormacaoDescricao(), pageable);
			formacaoTotal = formacaoService.getByFormacaoDescricao(formacaoByFormacaoForm.getSearchFormacaoDescricao()).size();
		}

		Page<Formacao> formacaoPage = new PageImpl<Formacao>(formacaoList, pageable, formacaoTotal+1);

		mv.addObject("formacaoPage", formacaoPage);
		mv.addObject("page", formacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/formacaoRelMenu", method = RequestMethod.GET)
	public ModelAndView formacaoRelMenu() {

		ModelAndView mv = new ModelAndView("formacao/formacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping(path = "/formacaoRel001", method = RequestMethod.GET)
	public ModelAndView formacaoRel001() {

		ModelAndView mv = new ModelAndView("formacao/formacaoRel001");
		Pageable formacaoPageable = new PageRequest(0, 200 , Direction.ASC, "formacaoNome");
		mv.addObject("formacaoPage", formacaoService.getFormacaoAll(formacaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/formacaoSave", method = RequestMethod.POST)
	public ModelAndView formacaoSave(@Valid FormacaoForm formacaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return formacaoAdd(formacaoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/formacaoHome");

		try {
			formacaoService.save(formacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("formacaoNome")) {
			        ObjectError error = new ObjectError("formacaoNome","Nome do Formacao já existente no cadastro.");
			        result.addError(error);			
			}
            return formacaoAdd(formacaoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/formacaoView/{id}", method = RequestMethod.GET)
	public ModelAndView formacaoView(@PathVariable("id") Long formacaoId) {

		Formacao formacao = formacaoService.getFormacaoByFormacaoPK(formacaoId);
		ModelAndView mv = new ModelAndView("formacao/formacaoView");
		FormacaoForm formacaoForm = formacaoService.converteFormacaoView(formacao);
		mv.addObject("formacaoForm", formacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}