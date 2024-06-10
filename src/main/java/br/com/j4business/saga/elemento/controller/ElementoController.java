package br.com.j4business.saga.elemento.controller;

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
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.model.ElementoByElementoForm;
import br.com.j4business.saga.elemento.model.ElementoForm;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.elementoquestao.service.ElementoQuestaoService;

@Controller
public class ElementoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ElementoQuestaoService elementoQuestaoService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/elementoAdd")
	public ModelAndView elementoAdd(ElementoForm elementoForm) {

		ModelAndView mv = new ModelAndView("elemento/elementoAdd");
		elementoForm = elementoService.elementoParametros(elementoForm);
		mv.addObject("elementoForm", elementoForm);
		mv.addObject("elementoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/elementoCreate")
	public ModelAndView elementoCreate(@Valid ElementoForm elementoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return elementoAdd(elementoForm);
		}

		if (elementoForm.getElementoPK() > 0) {
			return this.elementoSave(elementoForm, result, attributes);
			
		}
		
		try {
			elementoService.create(elementoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("elementoNome")) {
			        ObjectError error = new ObjectError("elementoNome","Nome do Elemento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return elementoAdd(elementoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/elementoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/elementoDelete/{id}")
	public ModelAndView elementoDelete(@PathVariable("id") long elementoPK, @Valid ElementoForm elementoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/elementoHome");

		Elemento elemento = elementoService.getElementoByElementoPK(elementoPK);
		try {
			elementoService.delete(elementoPK);

			attributes.addFlashAttribute("mensagem", "Elemento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Elemento não excluído. Existe(m) relacionamento(s) de Elemento ** "+ elemento.getElementoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/elementoEdit/{id}")
	public ModelAndView elementoEdit(@PathVariable("id") Long elementoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("elemento/elementoEdit");
		Elemento elemento = elementoService.getElementoByElementoPK(elementoPK);
		ElementoForm elementoForm = elementoService.converteElemento(elemento);
		mv.addObject("elementoForm", elementoForm);
		mv.addObject("elementoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoQuestaoPageable = PageRequest.of(0, 200, Direction.ASC, "questao.questaoNome");
		mv.addObject("elementoQuestaoPage", elementoQuestaoService.getByElementoPK(elementoPK, elementoQuestaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());


		return mv;
	}

	@GetMapping("/elementoHome")
	public ModelAndView elementoHome(@Valid ElementoByElementoForm elementoByElementoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("elemento/elementoHome");

		List<Elemento> elementoList = new ArrayList<Elemento>();

		int elementosTotal = 0;
		
		if (elementoByElementoForm.getSearchElementoNome() == null) {
			elementoByElementoForm.setSearchElementoNome("");
			elementoByElementoForm.setSearchElementoDescricao("");
			if (elementoByElementoForm.getElementoSortTipo() == null) {
				elementoByElementoForm.setElementoSortTipo("ElementoNome");
			}

		}

		if (elementoByElementoForm.getElementoSortTipo().equalsIgnoreCase("ElementoNome") || elementoByElementoForm.getElementoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "elementoNome");

		} else if (elementoByElementoForm.getElementoSortTipo().equalsIgnoreCase("ElementoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "elementoDescricao");

		}

		if ((!elementoByElementoForm.getSearchElementoNome().equalsIgnoreCase(""))) {
			elementoList = elementoService.getByElementoNome(elementoByElementoForm.getSearchElementoNome(), pageable);
			elementosTotal = elementoService.getByElementoNome(elementoByElementoForm.getSearchElementoNome()).size();

		} else {
			elementoList = elementoService.getByElementoDescricao(elementoByElementoForm.getSearchElementoDescricao(), pageable);
			elementosTotal = elementoService.getByElementoDescricao(elementoByElementoForm.getSearchElementoDescricao()).size();
		}

		Page<Elemento> elementoPage = new PageImpl<Elemento>(elementoList, pageable, elementosTotal+1);

		mv.addObject("elementoPage", elementoPage);
		mv.addObject("page", elementoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/elementoSave")
	public ModelAndView elementoSave(@Valid ElementoForm elementoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return elementoAdd(elementoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/elementoHome");

		try {
			elementoService.save(elementoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("elementoNome")) {
			        ObjectError error = new ObjectError("elementoNome","Nome do Elemento já existente no cadastro.");
			        result.addError(error);			
			}
            return elementoAdd(elementoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/elementoRelMenu")
	public ModelAndView elementoRelMenu() {

		ModelAndView mv = new ModelAndView("elemento/elementoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/elementoRel001")
	public ModelAndView elementoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("elemento/elementoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/elementoRel002")
	public ModelAndView elementoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("elemento/elementoRel002");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/elementoView/{id}")
	public ModelAndView elementoView(@PathVariable("id") Long elementoId) {

		Elemento elemento = elementoService.getElementoByElementoPK(elementoId);
		ModelAndView mv = new ModelAndView("elemento/elementoView");
		ElementoForm elementoForm = elementoService.converteElementoView(elemento);
		mv.addObject("elementoForm", elementoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}