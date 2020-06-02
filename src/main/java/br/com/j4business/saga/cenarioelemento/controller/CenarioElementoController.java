package br.com.j4business.saga.cenarioelemento.controller;

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

import br.com.j4business.saga.cenario.model.CenarioForm;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.elemento.model.ElementoForm;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.cenarioelemento.model.CenarioElemento;
import br.com.j4business.saga.cenarioelemento.model.CenarioElementoByCenarioForm;
import br.com.j4business.saga.cenarioelemento.model.CenarioElementoForm;
import br.com.j4business.saga.cenarioelemento.service.CenarioElementoService;

@Controller
public class CenarioElementoController {

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private CenarioElementoService cenarioElementoService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/cenarioElementoAdd", method = RequestMethod.GET)
	public ModelAndView cenarioElementoAdd(CenarioElementoForm cenarioElementoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenarioElemento/cenarioElementoAdd");
		cenarioElementoForm = cenarioElementoService.cenarioElementoParametros(cenarioElementoForm);
		mv.addObject("cenarioElementoForm", cenarioElementoForm);
		mv.addObject("cenarioElementoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("cenarioElementoStatusValues", AtributoStatus.values());

		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoPageable = new PageRequest(0, 200, Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(elementoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/cenarioElementoCreate", method = RequestMethod.POST)
	public ModelAndView cenarioElementoCreate(@Valid CenarioElementoForm cenarioElementoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return cenarioElementoAdd(cenarioElementoForm,pageable);
		}

		if (cenarioElementoForm.getCenarioElementoPK() > 0) {
			return this.cenarioElementoSave(cenarioElementoForm, result, attributes,pageable);
			
		}
		
		try {
			cenarioElementoService.create(cenarioElementoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cenarioElementoUnique")) {
			        ObjectError error = new ObjectError("cenarioNome","Relacionamento entre Cenário e Elemento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return cenarioElementoAdd(cenarioElementoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/cenarioElementoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/cenarioElementoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView cenarioElementoDelete(@PathVariable("id") long cenarioElementoId, @Valid ElementoForm elementoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/cenarioElementoHome");
		
		
		CenarioElemento cenarioElemento = cenarioElementoService.getCenarioElementoByCenarioElementoPK(cenarioElementoId);
		try {
			cenarioElementoService.delete(cenarioElementoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Cenario/Elemento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Cenario/Elemento não excluído. Existe(m) relacionamento(s) de Cenario/Elemento ** "+ 
										  cenarioElemento.getCenario().getCenarioNome() +
										  " / " +
										  cenarioElemento.getElemento().getElementoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/cenarioElementoEdit/{cenarioElementoPK}", method = RequestMethod.GET)
	public ModelAndView cenarioElementoEdit(@PathVariable("cenarioElementoPK") Long cenarioElementoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenarioElemento/cenarioElementoEdit");
		CenarioElemento cenarioElemento = cenarioElementoService.getCenarioElementoByCenarioElementoPK(cenarioElementoPK);
		CenarioElementoForm cenarioElementoForm = cenarioElementoService.converteCenarioElemento(cenarioElemento);
		mv.addObject("cenarioElementoForm", cenarioElementoForm);
		mv.addObject("cenarioElementoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("cenarioElementoStatusValues", AtributoStatus.values());

		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable elementoPageable = new PageRequest(0, 200, Direction.ASC, "elementoNome");
		mv.addObject("elementoPage", elementoService.getElementoAll(elementoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/cenarioElementoHome", method = RequestMethod.GET)
	public ModelAndView cenarioElementoHome(@Valid CenarioElementoByCenarioForm cenarioElementoByCenarioForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("cenarioElemento/cenarioElementoHome");
		
		List<CenarioElemento> cenarioElementoList = new ArrayList<CenarioElemento>();
		
		int cenarioElementosTotal = 0;
		
		if (cenarioElementoByCenarioForm.getSearchElementoNome() == null) {
			cenarioElementoByCenarioForm.setSearchCenarioNome("");
			cenarioElementoByCenarioForm.setSearchElementoNome("");
			if (cenarioElementoByCenarioForm.getCenarioElementoSortTipo() == null) {
				cenarioElementoByCenarioForm.setCenarioElementoSortTipo("ElementoNome");	
			}
			
		}

		if (cenarioElementoByCenarioForm.getCenarioElementoSortTipo().equalsIgnoreCase("CenarioNome")
				|| cenarioElementoByCenarioForm.getCenarioElementoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"cenario.cenarioNome","elemento.elementoNome"); 
		
		} else if (cenarioElementoByCenarioForm.getCenarioElementoSortTipo().equalsIgnoreCase("ElementoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"elemento.elementoNome","cenario.cenarioNome"); 

		}

		if ( ! cenarioElementoByCenarioForm.getSearchElementoNome().equalsIgnoreCase("")){
			cenarioElementoList = cenarioElementoService.getByElementoNome(cenarioElementoByCenarioForm.getSearchElementoNome(),pageable);
			cenarioElementosTotal = cenarioElementoService.getByElementoNome(cenarioElementoByCenarioForm.getSearchElementoNome()).size();
			
		} else {
			cenarioElementoList = cenarioElementoService.getByCenarioNome(cenarioElementoByCenarioForm.getSearchCenarioNome(),pageable);
			cenarioElementosTotal = cenarioElementoService.getByCenarioNome(cenarioElementoByCenarioForm.getSearchCenarioNome()).size();

		} 
		
		Page<CenarioElemento> cenarioElementoPage = new PageImpl<CenarioElemento>(cenarioElementoList,pageable,cenarioElementosTotal+1);
		
		mv.addObject("cenarioElementoPage", cenarioElementoPage);
		mv.addObject("page",cenarioElementoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/cenarioElementoSave", method = RequestMethod.POST)
	public ModelAndView cenarioElementoSave(@Valid CenarioElementoForm cenarioElementoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return cenarioElementoAdd(cenarioElementoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/cenarioElementoHome");

		try {
			cenarioElementoService.save(cenarioElementoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cenarioElementoUnique")) {
			        ObjectError error = new ObjectError("cenarioNome","Relacionamento entre Cenário e Elemento já existente no cadastro.");
			        result.addError(error);			
			}
            return cenarioElementoAdd(cenarioElementoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@RequestMapping(path = "/cenarioElementoRelMenu", method = RequestMethod.GET)
	public ModelAndView cenarioElementoRelMenu() {

		ModelAndView mv = new ModelAndView("cenarioElemento/cenarioElementoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/cenarioElementoRel001")
	public ModelAndView cenarioElementoRel001() {

		ModelAndView mv = new ModelAndView("cenarioElemento/cenarioElementoRel001");
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenario.cenarioNome","elemento.elementoNome");
		mv.addObject("cenarioElementoPage", cenarioElementoService.getCenarioElementoAll(cenarioPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/cenarioElementoView/{id}", method = RequestMethod.GET)
	public ModelAndView cenarioElementoView(@PathVariable("id") Long cenarioElementoId) {

		CenarioElemento cenarioElemento = cenarioElementoService.getCenarioElementoByCenarioElementoPK(cenarioElementoId);
		ModelAndView mv = new ModelAndView("cenarioElemento/cenarioElementoView");
		CenarioElementoForm cenarioElementoForm = cenarioElementoService.converteCenarioElementoView(cenarioElemento);
		CenarioForm cenarioForm = cenarioService.converteCenarioView(cenarioElemento.getCenario());
		ElementoForm elementoForm = elementoService.converteElementoView(cenarioElemento.getElemento());
		mv.addObject("cenarioElementoForm", cenarioElementoForm);
		mv.addObject("cenarioForm", cenarioForm);
		mv.addObject("elementoForm", elementoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}