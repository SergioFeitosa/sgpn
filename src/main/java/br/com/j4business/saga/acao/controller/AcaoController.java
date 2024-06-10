package br.com.j4business.saga.acao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

import br.com.j4business.saga.colaborador.service.ColaboradorService;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.acao.model.AcaoByAcaoForm;
import br.com.j4business.saga.acao.model.AcaoForm;
import br.com.j4business.saga.acao.service.AcaoService;
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

@Controller
public class AcaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AcaoService acaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/acaoAdd")
	public ModelAndView acaoAdd(AcaoForm acaoForm) {

		ModelAndView mv = new ModelAndView("acao/acaoAdd");
		acaoForm = acaoService.acaoParametros(acaoForm);
		mv.addObject("acaoForm", acaoForm);
		mv.addObject("acaoStatusValues", AtributoStatus.values());
		mv.addObject("acaoAprovacaoValues", AtributoAprovacao.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date()); 
		mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/acaoCreate")
	public ModelAndView acaoCreate(@Valid AcaoForm acaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return acaoAdd(acaoForm);
		}

		if (acaoForm.getAcaoPK() > 0) {
			return this.acaoSave(acaoForm, result, attributes);
			
		}
		
		try {
			acaoService.create(acaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("acaoNome")) {
			        ObjectError error = new ObjectError("acaoNome","Nome da Ação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return acaoAdd(acaoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/acaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/acaoDelete/{id}")
	public ModelAndView acaoDelete(@PathVariable("id") long acaoPK, @Valid AcaoForm acaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/acaoHome");

		Acao acao = acaoService.getAcaoByAcaoPK(acaoPK);
		try {
			acaoService.delete(acaoPK);

			attributes.addFlashAttribute("mensagem", "Ação excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Ação não excluída. Existe(m) relacionamento(s) de Ação ** "+ acao.getAcaoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@GetMapping(path = "/acaoEdit/{id}")
	public ModelAndView acaoEdit(@PathVariable("id") Long acaoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("acao/acaoEdit");
		Acao acao = acaoService.getAcaoByAcaoPK(acaoId);
		AcaoForm acaoForm = acaoService.converteAcao(acao);
		mv.addObject("acaoForm", acaoForm);
		mv.addObject("acaoAprovacaoValues", AtributoAprovacao.values());
		mv.addObject("acaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/acaoHome")
	public ModelAndView acaoHome(@Valid AcaoByAcaoForm acaoByAcaoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("acao/acaoHome");
		
		List<Acao> acaoList = new ArrayList<Acao>();

		int acoesTotal = 0;
		
		if (acaoByAcaoForm.getSearchAcaoNome() == null) {
			acaoByAcaoForm.setSearchAcaoNome("");
			acaoByAcaoForm.setSearchAcaoDescricao("");
			if (acaoByAcaoForm.getAcaoSortTipo() == null) {
				acaoByAcaoForm.setAcaoSortTipo("AcaoNome");
			}

		}

		if (acaoByAcaoForm.getAcaoSortTipo().equalsIgnoreCase("AcaoNome") || acaoByAcaoForm.getAcaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "acaoNome");

		} else if (acaoByAcaoForm.getAcaoSortTipo().equalsIgnoreCase("AcaoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "acaoDescricao");

		}

		if ((!acaoByAcaoForm.getSearchAcaoNome().equalsIgnoreCase(""))) {
			acaoList = acaoService.getByAcaoNome(acaoByAcaoForm.getSearchAcaoNome(), pageable);
			acoesTotal = acaoService.getByAcaoNome(acaoByAcaoForm.getSearchAcaoNome()).size();

		} else {
			acaoList = acaoService.getByAcaoDescricao(acaoByAcaoForm.getSearchAcaoDescricao(), pageable);
			acoesTotal = acaoService.getByAcaoDescricao(acaoByAcaoForm.getSearchAcaoDescricao()).size();
		}

		Page<Acao> acaoPage = new PageImpl<Acao>(acaoList, pageable, acoesTotal+1);

		mv.addObject("acaoPage", acaoPage);
		mv.addObject("page", acaoPage);

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@GetMapping(path = "/acaoRelMenu")
	public ModelAndView acaoRelMenu() {

		ModelAndView mv = new ModelAndView("acao/acaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/acaoRel001")
	public ModelAndView acaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("acao/acaoRel001");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "acaoNome");
		mv.addObject("acaoPage", acaoService.getAcaoAll(pageable));

		return mv;
	}

	@PostMapping(path = "/acaoSave")
	public ModelAndView acaoSave(@Valid AcaoForm acaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return acaoAdd(acaoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/acaoHome");

		try {
			acaoService.save(acaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("acaoNome")) {
			        ObjectError error = new ObjectError("acaoNome","Nome da Ação já existente no cadastro.");
			        result.addError(error);			
			}
            return acaoAdd(acaoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/acaoView/{id}")
	public ModelAndView acaoView(@PathVariable("id") Long acaoId) {

		Acao acao = acaoService.getAcaoByAcaoPK(acaoId);
		ModelAndView mv = new ModelAndView("acao/acaoView");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		AcaoForm acaoForm = acaoService.converteAcaoView(acao);
		mv.addObject("acaoForm", acaoForm);
		return mv;
	}


}