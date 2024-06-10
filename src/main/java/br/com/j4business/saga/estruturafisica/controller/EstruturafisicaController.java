package br.com.j4business.saga.estruturafisica.controller;

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
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisica.model.EstruturafisicaByEstruturafisicaForm;
import br.com.j4business.saga.estruturafisica.model.EstruturafisicaForm;
import br.com.j4business.saga.estruturafisica.service.EstruturafisicaService;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.service.EstruturafisicaUnidadeorganizacionalService;

@Controller
public class EstruturafisicaController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EstruturafisicaService estruturafisicaService;

	@Autowired
	private EstruturafisicaUnidadeorganizacionalService estruturafisicaUnidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/estruturafisicaAdd")
	public ModelAndView estruturafisicaAdd(EstruturafisicaForm estruturafisicaForm) {

		ModelAndView mv = new ModelAndView("estruturafisica/estruturafisicaAdd");
		estruturafisicaForm = estruturafisicaService.estruturafisicaParametros(estruturafisicaForm);
		mv.addObject("estruturafisicaForm", estruturafisicaForm);
		mv.addObject("estruturafisicaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/estruturafisicaCreate")
	public ModelAndView estruturafisicaCreate(@Valid EstruturafisicaForm estruturafisicaForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return estruturafisicaAdd(estruturafisicaForm);
		}

		if (estruturafisicaForm.getEstruturafisicaPK() > 0) {
			return this.estruturafisicaSave(estruturafisicaForm, result, attributes);
			
		}
		
		try {
			estruturafisicaService.create(estruturafisicaForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("estruturafisicaNome")) {
			        ObjectError error = new ObjectError("estruturafisicaNome","Nome da Estrutura Física já existente no cadastro.");
			        result.addError(error);			
			}
            
			return estruturafisicaAdd(estruturafisicaForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/estruturafisicaHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/estruturafisicaDelete/{id}")
	public ModelAndView estruturafisicaDelete(@PathVariable("id") long estruturafisicaPK, @Valid EstruturafisicaForm estruturafisicaForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/estruturafisicaHome");

		Estruturafisica estruturafisica = estruturafisicaService.getEstruturafisicaByEstruturafisicaPK(estruturafisicaPK);
		try {
			estruturafisicaService.delete(estruturafisicaPK);

			attributes.addFlashAttribute("mensagem", "Estrutura Física excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Estrutura Física não excluída. Existe(m) relacionamento(s) de Estrutura Física ** "+ estruturafisica.getEstruturafisicaNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@GetMapping(path = "/estruturafisicaEdit/{id}")
	public ModelAndView estruturafisicaEdit(@PathVariable("id") Long estruturafisicaPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("estruturafisica/estruturafisicaEdit");
		Estruturafisica estruturafisica = estruturafisicaService.getEstruturafisicaByEstruturafisicaPK(estruturafisicaPK);
		EstruturafisicaForm estruturafisicaForm = estruturafisicaService.converteEstruturafisica(estruturafisica);
		mv.addObject("estruturafisicaForm", estruturafisicaForm);
		mv.addObject("estruturafisicaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable estruturafisicaUnidadeorganizacionalPageable = PageRequest.of(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome");
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaPK(estruturafisicaPK, estruturafisicaUnidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/estruturafisicaHome")
	public ModelAndView estruturafisicaHome(@Valid EstruturafisicaByEstruturafisicaForm estruturafisicaByEstruturafisicaForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("estruturafisica/estruturafisicaHome");

		List<Estruturafisica> estruturafisicaList = new ArrayList<Estruturafisica>();

		int estruturafisicasTotal = 0;
		
		if (estruturafisicaByEstruturafisicaForm.getSearchEstruturafisicaNome() == null) {
			estruturafisicaByEstruturafisicaForm.setSearchEstruturafisicaNome("");
			estruturafisicaByEstruturafisicaForm.setSearchEstruturafisicaDescricao("");
			if (estruturafisicaByEstruturafisicaForm.getEstruturafisicaSortTipo() == null) {
				estruturafisicaByEstruturafisicaForm.setEstruturafisicaSortTipo("EstruturafisicaNome");
			}

		}

		if (estruturafisicaByEstruturafisicaForm.getEstruturafisicaSortTipo().equalsIgnoreCase("EstruturafisicaNome") || estruturafisicaByEstruturafisicaForm.getEstruturafisicaSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "estruturafisicaNome");

		} else if (estruturafisicaByEstruturafisicaForm.getEstruturafisicaSortTipo().equalsIgnoreCase("EstruturafisicaDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "estruturafisicaDescricao");

		}

		if ((!estruturafisicaByEstruturafisicaForm.getSearchEstruturafisicaNome().equalsIgnoreCase(""))) {
			estruturafisicaList = estruturafisicaService.getByEstruturafisicaNome(estruturafisicaByEstruturafisicaForm.getSearchEstruturafisicaNome(), pageable);
			estruturafisicasTotal = estruturafisicaService.getByEstruturafisicaNome(estruturafisicaByEstruturafisicaForm.getSearchEstruturafisicaNome()).size();

		} else {
			estruturafisicaList = estruturafisicaService.getByEstruturafisicaDescricao(estruturafisicaByEstruturafisicaForm.getSearchEstruturafisicaDescricao(), pageable);
			estruturafisicasTotal = estruturafisicaService.getByEstruturafisicaDescricao(estruturafisicaByEstruturafisicaForm.getSearchEstruturafisicaDescricao()).size();
		}

		Page<Estruturafisica> estruturafisicaPage = new PageImpl<Estruturafisica>(estruturafisicaList, pageable, estruturafisicasTotal+1);

		mv.addObject("estruturafisicaPage", estruturafisicaPage);
		mv.addObject("page", estruturafisicaPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/estruturafisicaSave")
	public ModelAndView estruturafisicaSave(@Valid EstruturafisicaForm estruturafisicaForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return estruturafisicaAdd(estruturafisicaForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/estruturafisicaHome");

		try {
			estruturafisicaService.save(estruturafisicaForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("estruturafisicaNome")) {
			        ObjectError error = new ObjectError("estruturafisicaNome","Nome da Estrutura Física já existente no cadastro.");
			        result.addError(error);			
			}
            return estruturafisicaAdd(estruturafisicaForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/estruturafisicaRelMenu")
	public ModelAndView estruturafisicaRelMenu() {

		ModelAndView mv = new ModelAndView("estruturafisica/estruturafisicaRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/estruturafisicaRel001")
	public ModelAndView estruturafisicaRel001() {

		ModelAndView mv = new ModelAndView("estruturafisica/estruturafisicaRel001");
		Pageable pageable = PageRequest.of(0, 200 , Direction.ASC, "estruturafisicaNome");
		mv.addObject("estruturafisicaPage", estruturafisicaService.getEstruturafisicaAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/estruturafisicaView/{id}")
	public ModelAndView estruturafisicaView(@PathVariable("id") Long estruturafisicaId) {

		Estruturafisica estruturafisica = estruturafisicaService.getEstruturafisicaByEstruturafisicaPK(estruturafisicaId);
		ModelAndView mv = new ModelAndView("estruturafisica/estruturafisicaView");
		EstruturafisicaForm estruturafisicaForm = estruturafisicaService.converteEstruturafisicaView(estruturafisica);
		mv.addObject("estruturafisicaForm", estruturafisicaForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}