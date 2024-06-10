package br.com.j4business.saga.agendacontrato.controller;

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

import br.com.j4business.saga.agenda.model.AgendaForm;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.agendacontrato.enumeration.AgendaContratoEnvio;
import br.com.j4business.saga.agendacontrato.model.AgendaContrato;
import br.com.j4business.saga.agendacontrato.model.AgendaContratoByAgendaForm;
import br.com.j4business.saga.agendacontrato.model.AgendaContratoForm;
import br.com.j4business.saga.agendacontrato.service.AgendaContratoService;

@Controller
public class AgendaContratoController {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AgendaContratoService agendaContratoService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/agendaContratoAdd")
	public ModelAndView agendaContratoAdd(AgendaContratoForm agendaContratoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaContrato/agendaContratoAdd");
		agendaContratoForm = agendaContratoService.agendaContratoParametros(agendaContratoForm);
		mv.addObject("agendaContratoForm", agendaContratoForm);
		mv.addObject("agendaContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaContratoEnvioValues", AgendaContratoEnvio.values());
		mv.addObject("agendaContratoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/agendaContratoCreate")
	public ModelAndView agendaContratoCreate(@Valid AgendaContratoForm agendaContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaContratoAdd(agendaContratoForm,pageable);
		}

		if (agendaContratoForm.getAgendaContratoPK() > 0) {
			return this.agendaContratoSave(agendaContratoForm, result, attributes,pageable);
			
		}
		
		try {
			agendaContratoService.create(agendaContratoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaContratoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Contrato já existente no cadastro.");
			        result.addError(error);			
			}
            
			return agendaContratoAdd(agendaContratoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/agendaContratoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/agendaContratoDelete/{id}")
	public ModelAndView agendaContratoDelete(@PathVariable("id") long agendaContratoId, @Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/agendaContratoHome");
		
		
		AgendaContrato agendaContrato = agendaContratoService.getAgendaContratoByAgendaContratoPK(agendaContratoId);
		try {
			agendaContratoService.delete(agendaContratoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Agenda/Contrato excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Agenda/Contrato não excluído. Existe(m) relacionamento(s) de Agenda/Contrato ** "+ 
										  agendaContrato.getAgenda().getAgendaNome() +
										  " / " +
										  agendaContrato.getContrato().getContratoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/agendaContratoEdit/{agendaContratoPK}")
	public ModelAndView agendaContratoEdit(@PathVariable("agendaContratoPK") Long agendaContratoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaContrato/agendaContratoEdit");
		AgendaContrato agendaContrato = agendaContratoService.getAgendaContratoByAgendaContratoPK(agendaContratoPK);
		AgendaContratoForm agendaContratoForm = agendaContratoService.converteAgendaContrato(agendaContrato);
		mv.addObject("agendaContratoForm", agendaContratoForm);
		mv.addObject("agendaContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaContratoEnvioValues", AgendaContratoEnvio.values());
		mv.addObject("agendaContratoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/agendaContratoHome")
	public ModelAndView agendaContratoHome(@Valid AgendaContratoByAgendaForm agendaContratoByAgendaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaContrato/agendaContratoHome");
		
		List<AgendaContrato> agendaContratoList = new ArrayList<AgendaContrato>();
		
		int agendaContratosTotal = 0;
		
		if (agendaContratoByAgendaForm.getSearchContratoNome() == null) {
			agendaContratoByAgendaForm.setSearchAgendaNome("");
			agendaContratoByAgendaForm.setSearchContratoNome("");
			if (agendaContratoByAgendaForm.getAgendaContratoSortTipo() == null) {
				agendaContratoByAgendaForm.setAgendaContratoSortTipo("ContratoNome");	
			}
			
		}

		if (agendaContratoByAgendaForm.getAgendaContratoSortTipo().equalsIgnoreCase("AgendaNome")
				|| agendaContratoByAgendaForm.getAgendaContratoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"agenda.agendaNome","contrato.contratoNome"); 
		
		} else if (agendaContratoByAgendaForm.getAgendaContratoSortTipo().equalsIgnoreCase("ContratoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","agenda.agendaNome"); 

		}

		if ( ! agendaContratoByAgendaForm.getSearchContratoNome().equalsIgnoreCase("")){
			agendaContratoList = agendaContratoService.getByContratoNome(agendaContratoByAgendaForm.getSearchContratoNome(),pageable);
			agendaContratosTotal = agendaContratoService.getByContratoNome(agendaContratoByAgendaForm.getSearchContratoNome()).size();
			
		} else {
			agendaContratoList = agendaContratoService.getByAgendaNome(agendaContratoByAgendaForm.getSearchAgendaNome(),pageable);
			agendaContratosTotal = agendaContratoService.getByAgendaNome(agendaContratoByAgendaForm.getSearchAgendaNome()).size();

		} 
		
		Page<AgendaContrato> agendaContratoPage = new PageImpl<AgendaContrato>(agendaContratoList,pageable,agendaContratosTotal+1);
		
		mv.addObject("agendaContratoPage", agendaContratoPage);
		mv.addObject("page",agendaContratoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/agendaContratoSave")
	public ModelAndView agendaContratoSave(@Valid AgendaContratoForm agendaContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaContratoAdd(agendaContratoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/agendaContratoHome");

		try {
			agendaContratoService.save(agendaContratoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaContratoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Contrato já existente no cadastro.");
			        result.addError(error);			
			}
            return agendaContratoAdd(agendaContratoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/agendaContratoRelMenu")
	public ModelAndView agendaContratoRelMenu() {

		ModelAndView mv = new ModelAndView("agendaContrato/agendaContratoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/agendaContratoRel001")
	public ModelAndView agendaContratoRel001() {

		ModelAndView mv = new ModelAndView("agendaContrato/agendaContratoRel001");
		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agenda.agendaNome","contrato.contratoNome");
		mv.addObject("agendaContratoPage", agendaContratoService.getAgendaContratoAll(agendaPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/agendaContratoView/{id}")
	public ModelAndView agendaContratoView(@PathVariable("id") Long agendaContratoId) {

		AgendaContrato agendaContrato = agendaContratoService.getAgendaContratoByAgendaContratoPK(agendaContratoId);
		ModelAndView mv = new ModelAndView("agendaContrato/agendaContratoView");
		AgendaContratoForm agendaContratoForm = agendaContratoService.converteAgendaContratoView(agendaContrato);
		AgendaForm agendaForm = agendaService.converteAgendaView(agendaContrato.getAgenda());
		ContratoForm contratoForm = contratoService.converteContratoView(agendaContrato.getContrato());
		mv.addObject("agendaContratoForm", agendaContratoForm);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}