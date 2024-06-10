package br.com.j4business.saga.atividade.controller;

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

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.atividade.model.AtividadeByAtividadeForm;
import br.com.j4business.saga.atividade.model.AtividadeForm;
import br.com.j4business.saga.atividade.service.AtividadeService;

@Controller
public class AtividadeController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;
	
	@GetMapping(path = "/atividadeAdd")
	public ModelAndView atividadeAdd(AtividadeForm atividadeForm) {

		ModelAndView mv = new ModelAndView("atividade/atividadeAdd");
		atividadeForm = atividadeService.atividadeParametros(atividadeForm);
		mv.addObject("atividadeForm", atividadeForm);
		mv.addObject("atividadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/atividadeCreate")
	public ModelAndView atividadeCreate(@Valid AtividadeForm atividadeForm, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return atividadeAdd(atividadeForm);
		}

		if (atividadeForm.getAtividadePK() > 0) {
			return this.atividadeSave(atividadeForm, result, attributes);
			
		}
		
		try {
			atividadeService.create(atividadeForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("atividadeNome")) {
			        ObjectError error = new ObjectError("atividadeNome","Nome da Atividade já existente no cadastro.");
			        result.addError(error);			
			}
            
			return atividadeAdd(atividadeForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/atividadeHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/atividadeDelete/{id}")
	public ModelAndView atividadeDelete(@PathVariable("id") long atividadePK, @Valid AtividadeForm atividadeForm,BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/atividadeHome");

		Atividade atividade = atividadeService.getAtividadeByAtividadePK(atividadePK);
		try {
			atividadeService.delete(atividadePK);

			attributes.addFlashAttribute("mensagem", "Atividade excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Atividade não excluída. Existe(m) relacionamento(s) de Atividade ** "+ atividade.getAtividadeNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/atividadeEdit/{id}")
	public ModelAndView atividadeEdit(@PathVariable("id") Long atividadeId) {

		ModelAndView mv = new ModelAndView("atividade/atividadeEdit");
		Atividade atividade = atividadeService.getAtividadeByAtividadePK(atividadeId);
		AtividadeForm atividadeForm = atividadeService.converteAtividade(atividade);
		mv.addObject("atividadeForm", atividadeForm);
		mv.addObject("atividadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/atividadeHome")
	public ModelAndView atividadeHome(@Valid AtividadeByAtividadeForm atividadeByAtividadeForm, BindingResult result,
			RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("atividade/atividadeHome");

		List<Atividade> atividadeList = new ArrayList<Atividade>();

		int atividadesTotal = 0;

		if (atividadeByAtividadeForm.getSearchAtividadeNome() == null) {
			atividadeByAtividadeForm.setSearchAtividadeNome("");
			atividadeByAtividadeForm.setSearchAtividadeDescricao("");
			if (atividadeByAtividadeForm.getAtividadeSortTipo() == null) {
				atividadeByAtividadeForm.setAtividadeSortTipo("AtividadeNome");
			}

		}

		if (atividadeByAtividadeForm.getAtividadeSortTipo().equalsIgnoreCase("AtividadeNome")
				|| atividadeByAtividadeForm.getAtividadeSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "atividadeNome");

		} else if (atividadeByAtividadeForm.getAtividadeSortTipo().equalsIgnoreCase("AtividadeDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "atividadeDescricao");

		}

		if ((!atividadeByAtividadeForm.getSearchAtividadeNome().equalsIgnoreCase(""))) {
			atividadeList = atividadeService.getByAtividadeNome(atividadeByAtividadeForm.getSearchAtividadeNome(),
					pageable);
			atividadesTotal = atividadeService.getByAtividadeNome(atividadeByAtividadeForm.getSearchAtividadeNome())
					.size();

		} else {
			atividadeList = atividadeService
					.getByAtividadeDescricao(atividadeByAtividadeForm.getSearchAtividadeDescricao(), pageable);
			atividadesTotal = atividadeService
					.getByAtividadeDescricao(atividadeByAtividadeForm.getSearchAtividadeDescricao()).size();
		}

		Page<Atividade> atividadePage = new PageImpl<Atividade>(atividadeList, pageable, atividadesTotal + 1);

		mv.addObject("atividadePage", atividadePage);
		mv.addObject("page", atividadePage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/atividadeRelMenu")
	public ModelAndView atividadeRelMenu() {

		ModelAndView mv = new ModelAndView("atividade/atividadeRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping(path = "/atividadeRel001")
	public ModelAndView atividadeRel001() {

		ModelAndView mv = new ModelAndView("atividade/atividadeRel001");
		Pageable pageable = PageRequest.of(0, 200, Direction.ASC, "atividadeNome");
		mv.addObject("atividadePage", atividadeService.getAtividadeAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/atividadeSave")
	public ModelAndView atividadeSave(@Valid AtividadeForm atividadeForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return atividadeAdd(atividadeForm);
		}
		ModelAndView mv = new ModelAndView("redirect:/atividadeHome");

		try {
			mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
			atividadeService.save(atividadeForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("atividadeNome")) {
			        ObjectError error = new ObjectError("atividadeNome","Nome da Ação já existente no cadastro.");
			        result.addError(error);			
			}
            return atividadeAdd(atividadeForm);
	    }
		return mv;
		
	}

	
	@GetMapping(path = "/atividadeView/{id}")
	public ModelAndView atividadeView(@PathVariable("id") Long atividadeId) {

		Atividade atividade = atividadeService.getAtividadeByAtividadePK(atividadeId);
		ModelAndView mv = new ModelAndView("atividade/atividadeView");
		AtividadeForm atividadeForm = atividadeService.converteAtividadeView(atividade);
		mv.addObject("atividadeForm", atividadeForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}