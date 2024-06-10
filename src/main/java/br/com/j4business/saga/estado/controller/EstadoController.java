package br.com.j4business.saga.estado.controller;

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
import br.com.j4business.saga.estado.model.Estado;
import br.com.j4business.saga.estado.model.EstadoByEstadoForm;
import br.com.j4business.saga.estado.model.EstadoForm;
import br.com.j4business.saga.estado.service.EstadoService;

@Controller
public class EstadoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/estadoAdd")
	public ModelAndView estadoAdd(EstadoForm estadoForm) {

		ModelAndView mv = new ModelAndView("estado/estadoAdd");
		estadoForm = estadoService.estadoParametros(estadoForm);
		mv.addObject("estadoForm", estadoForm);
		mv.addObject("estadoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/estadoCreate")
	public ModelAndView estadoCreate(@Valid EstadoForm estadoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return estadoAdd(estadoForm);
		}

		if (estadoForm.getEstadoPK() > 0) {
			return this.estadoSave(estadoForm, result, attributes);
			
		}
		
		try {
			estadoService.create(estadoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("estadoNome")) {
		        ObjectError error = new ObjectError("estadoNome","Nome do Estado já existente no cadastro.");
		        result.addError(error);			
			}
			if (t.getConstraintName().equalsIgnoreCase("estadoSigla")) {
		        ObjectError error = new ObjectError("estadoSigla","Sigla do Estado já existente no cadastro.");
		        result.addError(error);			
			}
            
			return estadoAdd(estadoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/estadoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/estadoDelete/{id}")
	public ModelAndView estadoDelete(@PathVariable("id") long estadoPK, @Valid EstadoForm estadoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/estadoHome");

		Estado estado = estadoService.getEstadoByEstadoPK(estadoPK);
		try {
			estadoService.delete(estadoPK);

			attributes.addFlashAttribute("mensagem", "Estado excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Estado não excluído. Existe(m) relacionamento(s) de Estado ** "+ estado.getEstadoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping("/estadoHome")
	public ModelAndView estadoHome(@Valid EstadoByEstadoForm estadoByEstadoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("estado/estadoHome");

		int estadosTotal = 0;
		
		List<Estado> estadoList = new ArrayList<Estado>();
		

		if (estadoByEstadoForm.getSearchEstadoNome() == null) {
			estadoByEstadoForm.setSearchEstadoNome("");
			estadoByEstadoForm.setSearchEstadoSigla("");
			if (estadoByEstadoForm.getEstadoSortTipo() == null) {
				estadoByEstadoForm.setEstadoSortTipo("EstadoNome");
			}

		}

		if (estadoByEstadoForm.getEstadoSortTipo().equalsIgnoreCase("EstadoNome") || estadoByEstadoForm.getEstadoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "estadoNome");

		} else if (estadoByEstadoForm.getEstadoSortTipo().equalsIgnoreCase("EstadoSigla")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "estadoSigla");

		}

		if ((!estadoByEstadoForm.getSearchEstadoNome().equalsIgnoreCase(""))) {
			estadoList = estadoService.getByEstadoNome(estadoByEstadoForm.getSearchEstadoNome(), pageable);
			estadosTotal = estadoService.getByEstadoNome(estadoByEstadoForm.getSearchEstadoNome()).size();

		} else {
			estadoList = estadoService.getByEstadoSigla(estadoByEstadoForm.getSearchEstadoSigla(), pageable);
			estadosTotal = estadoService.getByEstadoSigla(estadoByEstadoForm.getSearchEstadoSigla()).size();
		}

		Page<Estado> estadoPage = new PageImpl<Estado>(estadoList, pageable, estadosTotal+1);

		mv.addObject("estadoPage", estadoPage);
		mv.addObject("page", estadoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/estadoEdit/{id}")
	public ModelAndView estadoEdit(@PathVariable("id") Long estadoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("estado/estadoEdit");
		Estado estado = estadoService.getEstadoByEstadoPK(estadoId);
		EstadoForm estadoForm = estadoService.converteEstado(estado);
		mv.addObject("estadoForm", estadoForm);
		mv.addObject("estadoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/estadoSave")
	public ModelAndView estadoSave(@Valid EstadoForm estadoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return estadoAdd(estadoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/estadoHome");

		try {
			estadoService.save(estadoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("estadoNome")) {
		        ObjectError error = new ObjectError("estadoNome","Nome do Estado já existente no cadastro.");
		        result.addError(error);			
			}
			if (t.getConstraintName().equalsIgnoreCase("estadoSigla")) {
		        ObjectError error = new ObjectError("estadoSigla","Sigla do Estado já existente no cadastro.");
		        result.addError(error);			
			}
            return estadoAdd(estadoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/estadoRelMenu")
	public ModelAndView estadoRelMenu() {

		ModelAndView mv = new ModelAndView("estado/estadoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/estadoRel001")
	public ModelAndView estadoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("estado/estadoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "estadoNome");
		mv.addObject("estadoPage", estadoService.getEstadoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/estadoView/{id}")
	public ModelAndView estadoView(@PathVariable("id") Long estadoId) {

		Estado estado = estadoService.getEstadoByEstadoPK(estadoId);
		ModelAndView mv = new ModelAndView("estado/estadoView");
		EstadoForm estadoForm = estadoService.converteEstadoView(estado);
		mv.addObject("estadoForm", estadoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}