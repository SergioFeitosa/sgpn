package br.com.j4business.saga.contrato.controller;

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
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.model.ContratoByContratoForm;
import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.contratoimagem.service.ContratoImagemService;
import br.com.j4business.saga.contratotexto.service.ContratoTextoService;
import br.com.j4business.saga.contratovideo.service.ContratoVideoService;

@Controller
public class ContratoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private ContratoImagemService contratoImagemService;

	@Autowired
	private ContratoTextoService contratoTextoService;

	@Autowired
	private ContratoVideoService contratoVideoService;

	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/contratoAdd", method = RequestMethod.GET)
	public ModelAndView contratoAdd(ContratoForm contratoForm) {

		ModelAndView mv = new ModelAndView("contrato/contratoAdd");
		contratoForm = contratoService.contratoParametros(contratoForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("contratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/contratoCreate", method = RequestMethod.POST)
	public ModelAndView contratoCreate(@Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return contratoAdd(contratoForm);
		}

		if (contratoForm.getContratoPK() > 0) {
			return this.contratoSave(contratoForm, result, attributes);
			
		}
		
		try {
			contratoService.create(contratoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoNome")) {
			        ObjectError error = new ObjectError("contratoNome","Nome do Contrato já existente no cadastro.");
			        result.addError(error);			
			}
            
			return contratoAdd(contratoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/contratoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView contratoDelete(@PathVariable("id") long contratoPK, @Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/contratoHome");

		Contrato contrato = contratoService.getContratoByContratoPK(contratoPK);
		try {
			contratoService.delete(contratoPK);

			attributes.addFlashAttribute("mensagem", "Contrato excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Contrato não excluído. Existe(m) relacionamento(s) de Contrato ** "+ contrato.getContratoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView contratoEdit(@PathVariable("id") Long contratoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("contrato/contratoEdit");
		Contrato contrato = contratoService.getContratoByContratoPK(contratoId);
		ContratoForm contratoForm = contratoService.converteContrato(contrato);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("contratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		mv.addObject("contratoVideoList", contratoVideoService.getContratoVideoAtivoByContratoPK(contratoId));
		mv.addObject("contratoImagemList", contratoImagemService.getContratoImagemAtivoByContratoPK(contratoId));
		mv.addObject("contratoTextoList", contratoTextoService.getContratoTextoAtivoByContratoPK(contratoId));

		return mv;
	}

	@RequestMapping("/contratoHome")
	public ModelAndView contratoHome(@Valid ContratoByContratoForm contratoByContratoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("contrato/contratoHome");

		List<Contrato> contratoList = new ArrayList<Contrato>();

		int contratoTotal = 0;
		
		if (contratoByContratoForm.getSearchContratoNome() == null) {
			contratoByContratoForm.setSearchContratoNome("");
			contratoByContratoForm.setSearchContratoDescricao("");
			if (contratoByContratoForm.getContratoSortTipo() == null) {
				contratoByContratoForm.setContratoSortTipo("ContratoNome");
			}

		}

		if (contratoByContratoForm.getContratoSortTipo().equalsIgnoreCase("ContratoNome") || contratoByContratoForm.getContratoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "contratoNome");

		} else if (contratoByContratoForm.getContratoSortTipo().equalsIgnoreCase("ContratoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "contratoDescricao");

		}

		if ((!contratoByContratoForm.getSearchContratoNome().equalsIgnoreCase(""))) {
			contratoList = contratoService.getByContratoNome(contratoByContratoForm.getSearchContratoNome(), pageable);
			contratoTotal = contratoService.getByContratoNome(contratoByContratoForm.getSearchContratoNome()).size();

		} else {
			contratoList = contratoService.getByContratoDescricao(contratoByContratoForm.getSearchContratoDescricao(), pageable);
			contratoTotal = contratoService.getByContratoDescricao(contratoByContratoForm.getSearchContratoDescricao()).size();
		}

		Page<Contrato> contratoPage = new PageImpl<Contrato>(contratoList, pageable, contratoTotal+1);

		mv.addObject("contratoPage", contratoPage);
		mv.addObject("page", contratoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoSave", method = RequestMethod.POST)
	public ModelAndView contratoSave(@Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return contratoAdd(contratoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/contratoHome");

		try {
			contratoService.save(contratoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoNome")) {
			        ObjectError error = new ObjectError("contratoNome","Nome do Contrato já existente no cadastro.");
			        result.addError(error);			
			}
            return contratoAdd(contratoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/contratoRelMenu", method = RequestMethod.GET)
	public ModelAndView contratoRelMenu() {

		ModelAndView mv = new ModelAndView("contrato/contratoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/contratoRel001")
	public ModelAndView contratoRel001() {

		ModelAndView mv = new ModelAndView("contrato/contratoRel001");
		Pageable pageable = new PageRequest(0, 200 , Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/contratoView/{id}", method = RequestMethod.GET)
	public ModelAndView contratoView(@PathVariable("id") Long contratoId) {

		Contrato contrato = contratoService.getContratoByContratoPK(contratoId);
		ModelAndView mv = new ModelAndView("contrato/contratoView");
		ContratoForm contratoForm = contratoService.converteContratoView(contrato);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}