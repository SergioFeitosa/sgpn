package br.com.j4business.saga.cenario.controller;

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
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenario.model.CenarioByCenarioForm;
import br.com.j4business.saga.cenario.model.CenarioForm;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.cenarioelemento.service.CenarioElementoService;

@Controller
public class CenarioController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private CenarioElementoService cenarioElementoService;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/cenarioAdd")
	public ModelAndView cenarioAdd(CenarioForm cenarioForm) {

		ModelAndView mv = new ModelAndView("cenario/cenarioAdd");
		cenarioForm = cenarioService.cenarioParametros(cenarioForm);
		mv.addObject("cenarioForm", cenarioForm);
		mv.addObject("cenarioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/cenarioCreate")
	public ModelAndView cenarioCreate(@Valid CenarioForm cenarioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return cenarioAdd(cenarioForm);
		}

		if (cenarioForm.getCenarioPK() > 0) {
			return this.cenarioSave(cenarioForm, result, attributes);
			
		}
		
		try {
			cenarioService.create(cenarioForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cenarioNome")) {
			        ObjectError error = new ObjectError("cenarioNome","Nome do Cenário já existente no cadastro.");
			        result.addError(error);			
			}
            
			return cenarioAdd(cenarioForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/cenarioHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/cenarioDelete/{id}")
	public ModelAndView cenarioDelete(@PathVariable("id") long cenarioPK, @Valid CenarioForm cenarioForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/cenarioHome");

		Cenario cenario = cenarioService.getCenarioByCenarioPK(cenarioPK);
		try {
			cenarioService.delete(cenarioPK);

			attributes.addFlashAttribute("mensagem", "Cenário excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Cenário não excluído. Existe(m) relacionamento(s) de Cenário ** "+ cenario.getCenarioNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/cenarioEdit/{id}")
	public ModelAndView cenarioEdit(@PathVariable("id") Long cenarioPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenario/cenarioEdit");
		Cenario cenario = cenarioService.getCenarioByCenarioPK(cenarioPK);
		CenarioForm cenarioForm = cenarioService.converteCenario(cenario);
		mv.addObject("cenarioForm", cenarioForm);
		mv.addObject("cenarioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable cenarioElementoPageable = PageRequest.of(0, 200, Direction.ASC, "elemento.elementoNome");
		mv.addObject("cenarioElementoPage", cenarioElementoService.getByCenarioPK(cenarioPK, cenarioElementoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/cenarioHome")
	public ModelAndView cenarioHome(@Valid CenarioByCenarioForm cenarioByCenarioForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenario/cenarioHome");

		List<Cenario> cenarios = new ArrayList<Cenario>();

		int cenariosTotal = 0;
		
		if (cenarioByCenarioForm.getSearchCenarioNome() == null) {
			cenarioByCenarioForm.setSearchCenarioNome("");
			cenarioByCenarioForm.setSearchCenarioDescricao("");
			if (cenarioByCenarioForm.getCenarioSortTipo() == null) {
				cenarioByCenarioForm.setCenarioSortTipo("CenarioNome");
			}

		}

		if (cenarioByCenarioForm.getCenarioSortTipo().equalsIgnoreCase("CenarioNome") || cenarioByCenarioForm.getCenarioSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "cenarioNome");

		} else if (cenarioByCenarioForm.getCenarioSortTipo().equalsIgnoreCase("CenarioDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "cenarioDescricao");

		}

		if ((!cenarioByCenarioForm.getSearchCenarioNome().equalsIgnoreCase(""))) {
			cenarios = cenarioService.getByCenarioNome(cenarioByCenarioForm.getSearchCenarioNome(), pageable);
			cenariosTotal = cenarioService.getByCenarioNome(cenarioByCenarioForm.getSearchCenarioNome()).size();

		} else {
			cenarios = cenarioService.getByCenarioDescricao(cenarioByCenarioForm.getSearchCenarioDescricao(), pageable);
			cenariosTotal = cenarioService.getByCenarioDescricao(cenarioByCenarioForm.getSearchCenarioDescricao()).size();
		}

		Page<Cenario> pageCenarios = new PageImpl<Cenario>(cenarios, pageable, cenariosTotal+1);

		mv.addObject("cenarioPage", pageCenarios);
		mv.addObject("page", pageCenarios);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/cenarioSave")
	public ModelAndView cenarioSave(@Valid CenarioForm cenarioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return cenarioAdd(cenarioForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/cenarioHome");

		try {
			cenarioService.save(cenarioForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cenarioNome")) {
			        ObjectError error = new ObjectError("cenarioNome","Nome do Cenário já existente no cadastro.");
			        result.addError(error);			
			}
            return cenarioAdd(cenarioForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/cenarioRelMenu")
	public ModelAndView cenarioRelMenu() {

		ModelAndView mv = new ModelAndView("cenario/cenarioRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/cenarioRel001")
	public ModelAndView cenarioRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenario/cenarioRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/cenarioRel002")
	public ModelAndView cenarioRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenario/cenarioRel002");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/cenarioView/{id}")
	public ModelAndView cenarioView(@PathVariable("id") Long cenarioId) {

		Cenario cenario = cenarioService.getCenarioByCenarioPK(cenarioId);
		ModelAndView mv = new ModelAndView("cenario/cenarioView");
		CenarioForm cenarioForm = cenarioService.converteCenarioView(cenario);
		mv.addObject("cenarioForm", cenarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}