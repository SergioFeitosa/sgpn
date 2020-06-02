package br.com.j4business.saga.unidadeorganizacional.controller;

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
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalByUnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalprocesso.service.UnidadeorganizacionalProcessoService;

@Controller
public class UnidadeorganizacionalController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private UnidadeorganizacionalProcessoService unidadeorganizacionalProcessoService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/unidadeorganizacionalAdd", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalAdd(UnidadeorganizacionalForm unidadeorganizacionalForm) {

		ModelAndView mv = new ModelAndView("unidadeorganizacional/unidadeorganizacionalAdd");
		unidadeorganizacionalForm = unidadeorganizacionalService.unidadeorganizacionalParametros(unidadeorganizacionalForm);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("unidadeorganizacionalStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalCreate", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalCreate(@Valid UnidadeorganizacionalForm unidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return unidadeorganizacionalAdd(unidadeorganizacionalForm);
		}

		if (unidadeorganizacionalForm.getUnidadeorganizacionalPK() > 0) {
			return this.unidadeorganizacionalSave(unidadeorganizacionalForm, result, attributes);
			
		}
		
		try {
			unidadeorganizacionalService.create(unidadeorganizacionalForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalNome")) {
			        ObjectError error = new ObjectError("unidadeorganizacionalNome","Nome da Unidade Organizacional já existente no cadastro.");
			        result.addError(error);			
			}
            
			return unidadeorganizacionalAdd(unidadeorganizacionalForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalDelete/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalDelete(@PathVariable("id") long unidadeorganizacionalPK, @Valid UnidadeorganizacionalForm unidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalHome");

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(unidadeorganizacionalPK);
		try {
			unidadeorganizacionalService.delete(unidadeorganizacionalPK);

			attributes.addFlashAttribute("mensagem", "Unidade Organizacional excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Unidade Organizacional não excluída. Existe(m) relacionamento(s) de Unidade Organizacional ** "+ unidadeorganizacional.getUnidadeorganizacionalNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalEdit/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalEdit(@PathVariable("id") Long unidadeorganizacionalPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacional/unidadeorganizacionalEdit");
		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(unidadeorganizacionalPK);
		UnidadeorganizacionalForm unidadeorganizacionalForm = unidadeorganizacionalService.converteUnidadeorganizacional(unidadeorganizacional);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("unidadeorganizacionalStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable unidadeorganizacionalProcessoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("unidadeorganizacionalProcessoPage", unidadeorganizacionalProcessoService.getByUnidadeorganizacionalPK(unidadeorganizacionalPK, unidadeorganizacionalProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/unidadeorganizacionalHome")
	public ModelAndView unidadeorganizacionalHome(@Valid UnidadeorganizacionalByUnidadeorganizacionalForm unidadeorganizacionalByUnidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacional/unidadeorganizacionalHome");

		List<Unidadeorganizacional> unidadeorganizacionalList = new ArrayList<Unidadeorganizacional>();

		int unidadeorganizacionalsTotal = 0;
		
		if (unidadeorganizacionalByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome() == null) {
			unidadeorganizacionalByUnidadeorganizacionalForm.setSearchUnidadeorganizacionalNome("");
			unidadeorganizacionalByUnidadeorganizacionalForm.setSearchUnidadeorganizacionalDescricao("");
			if (unidadeorganizacionalByUnidadeorganizacionalForm.getUnidadeorganizacionalSortTipo() == null) {
				unidadeorganizacionalByUnidadeorganizacionalForm.setUnidadeorganizacionalSortTipo("UnidadeorganizacionalNome");
			}

		}

		if (unidadeorganizacionalByUnidadeorganizacionalForm.getUnidadeorganizacionalSortTipo().equalsIgnoreCase("UnidadeorganizacionalNome") || unidadeorganizacionalByUnidadeorganizacionalForm.getUnidadeorganizacionalSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "unidadeorganizacionalNome");

		} else if (unidadeorganizacionalByUnidadeorganizacionalForm.getUnidadeorganizacionalSortTipo().equalsIgnoreCase("UnidadeorganizacionalDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "unidadeorganizacionalDescricao");

		}

		if ((!unidadeorganizacionalByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome().equalsIgnoreCase(""))) {
			unidadeorganizacionalList = unidadeorganizacionalService.getByUnidadeorganizacionalNome(unidadeorganizacionalByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome(), pageable);
			unidadeorganizacionalsTotal = unidadeorganizacionalService.getByUnidadeorganizacionalNome(unidadeorganizacionalByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome()).size();

		} else {
			unidadeorganizacionalList = unidadeorganizacionalService.getByUnidadeorganizacionalDescricao(unidadeorganizacionalByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalDescricao(), pageable);
			unidadeorganizacionalsTotal = unidadeorganizacionalService.getByUnidadeorganizacionalDescricao(unidadeorganizacionalByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalDescricao()).size();
		}

		Page<Unidadeorganizacional> unidadeorganizacionalPage = new PageImpl<Unidadeorganizacional>(unidadeorganizacionalList, pageable, unidadeorganizacionalsTotal+1);

		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalPage);
		mv.addObject("page", unidadeorganizacionalPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalSave", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalSave(@Valid UnidadeorganizacionalForm unidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return unidadeorganizacionalAdd(unidadeorganizacionalForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalHome");

		try {
			unidadeorganizacionalService.save(unidadeorganizacionalForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalNome")) {
			        ObjectError error = new ObjectError("unidadeorganizacionalNome","Nome da Unidade Organizacional já existente no cadastro.");
			        result.addError(error);			
			}
            return unidadeorganizacionalAdd(unidadeorganizacionalForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalRelMenu", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalRelMenu() {

		ModelAndView mv = new ModelAndView("unidadeorganizacional/unidadeorganizacionalRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/unidadeorganizacionalRel001")
	public ModelAndView unidadeorganizacionalRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacional/unidadeorganizacionalRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalView/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalView(@PathVariable("id") Long unidadeorganizacionalId) {

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(unidadeorganizacionalId);
		ModelAndView mv = new ModelAndView("unidadeorganizacional/unidadeorganizacionalView");
		UnidadeorganizacionalForm unidadeorganizacionalForm = unidadeorganizacionalService.converteUnidadeorganizacionalView(unidadeorganizacional);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}