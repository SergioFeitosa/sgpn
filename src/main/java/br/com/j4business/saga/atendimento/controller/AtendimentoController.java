package br.com.j4business.saga.atendimento.controller;

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

import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.atendimento.model.AtendimentoByAtendimentoForm;
import br.com.j4business.saga.atendimento.model.AtendimentoForm;
import br.com.j4business.saga.atendimento.service.AtendimentoService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

@Controller
public class AtendimentoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AtendimentoService atendimentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/atendimentoAdd")
	public ModelAndView atendimentoAdd(AtendimentoForm atendimentoForm) {

		ModelAndView mv = new ModelAndView("atendimento/atendimentoAdd");
		atendimentoForm = atendimentoService.atendimentoParametros(atendimentoForm);
		mv.addObject("atendimentoForm", atendimentoForm);
		mv.addObject("atendimentoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/atendimentoCreate")
	public ModelAndView atendimentoCreate(@Valid AtendimentoForm atendimentoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return atendimentoAdd(atendimentoForm);
		}
		
		try {
			atendimentoService.create(atendimentoForm);
		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("atendimentoNome")) {
			        ObjectError error = new ObjectError("atendimentoNome","Nome do Atendimento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return atendimentoAdd(atendimentoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/atendimentoHome");
		attributes.addFlashAttribute("mensagem", "Atendimento Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
		
		
	}

	@GetMapping(path = "/atendimentoDelete/{id}")
	public ModelAndView atendimentoDelete(@PathVariable("id") long atendimentoPK, @Valid AtendimentoForm atendimentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/atendimentoHome");

		Atendimento atendimento = atendimentoService.getAtendimentoByAtendimentoPK(atendimentoPK);
		try {
			atendimentoService.delete(atendimentoPK);

			attributes.addFlashAttribute("mensagem", "Atendimento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Atendimento não excluído. Existe(m) relacionamento(s) de Atendimento ** "+ atendimento.getAtendimentoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@GetMapping(path = "/atendimentoEdit/{id}")
	public ModelAndView atendimentoEdit(@PathVariable("id") Long atendimentoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("atendimento/atendimentoEdit");
		Atendimento atendimento = atendimentoService.getAtendimentoByAtendimentoPK(atendimentoId);
		AtendimentoForm atendimentoForm = atendimentoService.converteAtendimento(atendimento);
		mv.addObject("atendimentoForm", atendimentoForm);
		mv.addObject("atendimentoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/atendimentoHome")
	public ModelAndView atendimentoHome(@Valid AtendimentoByAtendimentoForm atendimentoByAtendimentoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("atendimento/atendimentoHome");
		
		List<Atendimento> atendimentoList = new ArrayList<Atendimento>();

		int atendimentosTotal = 0;
		
		if (atendimentoByAtendimentoForm.getSearchAtendimentoNome() == null) {
			atendimentoByAtendimentoForm.setSearchAtendimentoNome("");
			atendimentoByAtendimentoForm.setSearchAtendimentoDescricao("");
			if (atendimentoByAtendimentoForm.getAtendimentoSortTipo() == null) {
				atendimentoByAtendimentoForm.setAtendimentoSortTipo("AtendimentoNome");
			}

		}

		if (atendimentoByAtendimentoForm.getAtendimentoSortTipo().equalsIgnoreCase("AtendimentoNome") || atendimentoByAtendimentoForm.getAtendimentoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "atendimentoNome");

		} else if (atendimentoByAtendimentoForm.getAtendimentoSortTipo().equalsIgnoreCase("AtendimentoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "atendimentoDescricao");

		}

		if ((!atendimentoByAtendimentoForm.getSearchAtendimentoNome().equalsIgnoreCase(""))) {
			atendimentoList = atendimentoService.getByAtendimentoNome(atendimentoByAtendimentoForm.getSearchAtendimentoNome(), pageable);
			atendimentosTotal = atendimentoService.getByAtendimentoNome(atendimentoByAtendimentoForm.getSearchAtendimentoNome()).size();

		} else {
			atendimentoList = atendimentoService.getByAtendimentoDescricao(atendimentoByAtendimentoForm.getSearchAtendimentoDescricao(), pageable);
			atendimentosTotal = atendimentoService.getByAtendimentoDescricao(atendimentoByAtendimentoForm.getSearchAtendimentoDescricao()).size();
		}

		Page<Atendimento> atendimentoPage = new PageImpl<Atendimento>(atendimentoList, pageable, atendimentosTotal+1);

		mv.addObject("atendimentoPage", atendimentoPage);
		mv.addObject("page", atendimentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/atendimentoRelMenu")
	public ModelAndView atendimentoRelMenu() {

		ModelAndView mv = new ModelAndView("atendimento/atendimentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/atendimentoRel001")
	public ModelAndView atendimentoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("atendimento/atendimentoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "atendimentoNome");
		mv.addObject("atendimentoPage", atendimentoService.getAtendimentoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/atendimentoSave")
	public ModelAndView atendimentoSave(@Valid AtendimentoForm atendimentoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return atendimentoAdd(atendimentoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/atendimentoHome");

		try {
			atendimentoService.save(atendimentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("atendimentoNome")) {
			        ObjectError error = new ObjectError("atendimentoNome","Nome do Atendimento já existente no cadastro.");
			        result.addError(error);			
			}
            return atendimentoAdd(atendimentoForm);
	    }
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/atendimentoView/{id}")
	public ModelAndView atendimentoView(@PathVariable("id") Long atendimentoId) {

		Atendimento atendimento = atendimentoService.getAtendimentoByAtendimentoPK(atendimentoId);
		ModelAndView mv = new ModelAndView("atendimento/atendimentoView");
		AtendimentoForm atendimentoForm = atendimentoService.converteAtendimentoView(atendimento);
		mv.addObject("atendimentoForm", atendimentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}