package br.com.j4business.saga.unidadeorganizacionalcontrato.controller;

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

import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContrato;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContratoByUnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContratoForm;
import br.com.j4business.saga.unidadeorganizacionalcontrato.service.UnidadeorganizacionalContratoService;

@Controller
public class UnidadeorganizacionalContratoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UnidadeorganizacionalContratoService unidadeorganizacionalContratoService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/unidadeorganizacionalContratoAdd", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalContratoAdd(UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalContrato/unidadeorganizacionalContratoAdd");
		unidadeorganizacionalContratoForm = unidadeorganizacionalContratoService.unidadeorganizacionalContratoParametros(unidadeorganizacionalContratoForm);
		mv.addObject("unidadeorganizacionalContratoForm", unidadeorganizacionalContratoForm);
		mv.addObject("unidadeorganizacionalContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("unidadeorganizacionalContratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = new PageRequest(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalContratoCreate", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalContratoCreate(@Valid UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return unidadeorganizacionalContratoAdd(unidadeorganizacionalContratoForm,pageable);
		}

		if (unidadeorganizacionalContratoForm.getUnidadeorganizacionalContratoPK() > 0) {
			return this.unidadeorganizacionalContratoSave(unidadeorganizacionalContratoForm, result, attributes,pageable);
			
		}
		
		try {
			unidadeorganizacionalContratoService.create(unidadeorganizacionalContratoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalContratoUnique")) {
			        ObjectError error = new ObjectError("unidadeorganizacionalNome","Relacionamento entre Unidade Organizacional e Contrato já existente no cadastro.");
			        result.addError(error);			
			}
            
			return unidadeorganizacionalContratoAdd(unidadeorganizacionalContratoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalContratoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/unidadeorganizacionalContratoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalContratoDelete(@PathVariable("id") long unidadeorganizacionalContratoId, @Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalContratoHome");
		
		
		UnidadeorganizacionalContrato unidadeorganizacionalContrato = unidadeorganizacionalContratoService.getUnidadeorganizacionalContratoByUnidadeorganizacionalContratoPK(unidadeorganizacionalContratoId);
		try {
			unidadeorganizacionalContratoService.delete(unidadeorganizacionalContratoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Unidadeorganizacional/Contrato excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Unidadeorganizacional/Contrato não excluído. Existe(m) relacionamento(s) de Unidadeorganizacional/Contrato ** "+ 
										  unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalNome() +
										  " / " +
										  unidadeorganizacionalContrato.getContrato().getContratoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalContratoEdit/{unidadeorganizacionalContratoPK}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalContratoEdit(@PathVariable("unidadeorganizacionalContratoPK") Long unidadeorganizacionalContratoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalContrato/unidadeorganizacionalContratoEdit");
		UnidadeorganizacionalContrato unidadeorganizacionalContrato = unidadeorganizacionalContratoService.getUnidadeorganizacionalContratoByUnidadeorganizacionalContratoPK(unidadeorganizacionalContratoPK);
		UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm = unidadeorganizacionalContratoService.converteUnidadeorganizacionalContrato(unidadeorganizacionalContrato);
		mv.addObject("unidadeorganizacionalContratoForm", unidadeorganizacionalContratoForm);
		mv.addObject("unidadeorganizacionalContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("unidadeorganizacionalContratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = new PageRequest(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/unidadeorganizacionalContratoHome", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalContratoHome(@Valid UnidadeorganizacionalContratoByUnidadeorganizacionalForm unidadeorganizacionalContratoByUnidadeorganizacionalForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalContrato/unidadeorganizacionalContratoHome");
		
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratoList = new ArrayList<UnidadeorganizacionalContrato>();
		
		int unidadeorganizacionalContratoTotal = 0;
		
		if (unidadeorganizacionalContratoByUnidadeorganizacionalForm.getSearchContratoNome() == null) {
			unidadeorganizacionalContratoByUnidadeorganizacionalForm.setSearchUnidadeorganizacionalNome("");
			unidadeorganizacionalContratoByUnidadeorganizacionalForm.setSearchContratoNome("");
			if (unidadeorganizacionalContratoByUnidadeorganizacionalForm.getUnidadeorganizacionalContratoSortTipo() == null) {
				unidadeorganizacionalContratoByUnidadeorganizacionalForm.setUnidadeorganizacionalContratoSortTipo("ContratoNome");	
			}
			
		}

		if (unidadeorganizacionalContratoByUnidadeorganizacionalForm.getUnidadeorganizacionalContratoSortTipo().equalsIgnoreCase("UnidadeorganizacionalNome")
				|| unidadeorganizacionalContratoByUnidadeorganizacionalForm.getUnidadeorganizacionalContratoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"unidadeorganizacional.unidadeorganizacionalNome","contrato.contratoNome"); 
		
		} else if (unidadeorganizacionalContratoByUnidadeorganizacionalForm.getUnidadeorganizacionalContratoSortTipo().equalsIgnoreCase("ContratoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","unidadeorganizacional.unidadeorganizacionalNome"); 

		}

		if ( ! unidadeorganizacionalContratoByUnidadeorganizacionalForm.getSearchContratoNome().equalsIgnoreCase("")){
			unidadeorganizacionalContratoList = unidadeorganizacionalContratoService.getByContratoNome(unidadeorganizacionalContratoByUnidadeorganizacionalForm.getSearchContratoNome(),pageable);
			unidadeorganizacionalContratoTotal = unidadeorganizacionalContratoService.getByContratoNome(unidadeorganizacionalContratoByUnidadeorganizacionalForm.getSearchContratoNome()).size();
			
		} else {
			unidadeorganizacionalContratoList = unidadeorganizacionalContratoService.getByUnidadeorganizacionalNome(unidadeorganizacionalContratoByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome(),pageable);
			unidadeorganizacionalContratoTotal = unidadeorganizacionalContratoService.getByUnidadeorganizacionalNome(unidadeorganizacionalContratoByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome()).size();

		} 
		
		Page<UnidadeorganizacionalContrato> unidadeorganizacionalContratoPage = new PageImpl<UnidadeorganizacionalContrato>(unidadeorganizacionalContratoList,pageable,unidadeorganizacionalContratoTotal+1);
		
		mv.addObject("unidadeorganizacionalContratoPage", unidadeorganizacionalContratoPage);
		mv.addObject("page",unidadeorganizacionalContratoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalContratoSave", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalContratoSave(@Valid UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return unidadeorganizacionalContratoAdd(unidadeorganizacionalContratoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalContratoHome");

		try {
			unidadeorganizacionalContratoService.save(unidadeorganizacionalContratoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalContratoUnique")) {
		        ObjectError error = new ObjectError("unidadeorganizacionalNome","Relacionamento entre Unidade Organizacional e Contrato já existente no cadastro.");
		        result.addError(error);			
		}
            return unidadeorganizacionalContratoAdd(unidadeorganizacionalContratoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalContratoRelMenu", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalContratoRelMenu() {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalContrato/unidadeorganizacionalContratoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/unidadeorganizacionalContratoRel001")
	public ModelAndView unidadeorganizacionalContratoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalContrato/unidadeorganizacionalContratoRel001");
		Pageable unidadeorganizacionalContratoPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome","contrato.contratoNome");
		mv.addObject("unidadeorganizacionalContratoPage", unidadeorganizacionalContratoService.getUnidadeorganizacionalContratoAll(unidadeorganizacionalContratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalContratoView/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalContratoView(@PathVariable("id") Long unidadeorganizacionalContratoId) {

		UnidadeorganizacionalContrato unidadeorganizacionalContrato = unidadeorganizacionalContratoService.getUnidadeorganizacionalContratoByUnidadeorganizacionalContratoPK(unidadeorganizacionalContratoId);
		ModelAndView mv = new ModelAndView("unidadeorganizacionalContrato/unidadeorganizacionalContratoView");
		UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm = unidadeorganizacionalContratoService.converteUnidadeorganizacionalContratoView(unidadeorganizacionalContrato);
		UnidadeorganizacionalForm unidadeorganizacionalForm = unidadeorganizacionalService.converteUnidadeorganizacionalView(unidadeorganizacionalContrato.getUnidadeorganizacional());
		ContratoForm contratoForm = contratoService.converteContratoView(unidadeorganizacionalContrato.getContrato());
		mv.addObject("unidadeorganizacionalContratoForm", unidadeorganizacionalContratoForm);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}