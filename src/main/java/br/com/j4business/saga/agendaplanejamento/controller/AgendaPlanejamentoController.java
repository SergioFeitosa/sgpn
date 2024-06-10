package br.com.j4business.saga.agendaplanejamento.controller;

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
import br.com.j4business.saga.planejamento.model.PlanejamentoForm;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.agendaplanejamento.enumeration.AgendaPlanejamentoEnvio;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamento;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamentoByAgendaForm;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamentoForm;
import br.com.j4business.saga.agendaplanejamento.service.AgendaPlanejamentoService;

@Controller
public class AgendaPlanejamentoController {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AgendaPlanejamentoService agendaPlanejamentoService;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/agendaPlanejamentoAdd")
	public ModelAndView agendaPlanejamentoAdd(AgendaPlanejamentoForm agendaPlanejamentoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaPlanejamento/agendaPlanejamentoAdd");
		agendaPlanejamentoForm = agendaPlanejamentoService.agendaPlanejamentoParametros(agendaPlanejamentoForm);
		mv.addObject("agendaPlanejamentoForm", agendaPlanejamentoForm);
		mv.addObject("agendaPlanejamentoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaPlanejamentoEnvioValues", AgendaPlanejamentoEnvio.values());
		mv.addObject("agendaPlanejamentoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoPageable = PageRequest.of(0, 200, Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(planejamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/agendaPlanejamentoCreate")
	public ModelAndView agendaPlanejamentoCreate(@Valid AgendaPlanejamentoForm agendaPlanejamentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaPlanejamentoAdd(agendaPlanejamentoForm,pageable);
		}

		if (agendaPlanejamentoForm.getAgendaPlanejamentoPK() > 0) {
			return this.agendaPlanejamentoSave(agendaPlanejamentoForm, result, attributes,pageable);
			
		}
		
		try {
			agendaPlanejamentoService.create(agendaPlanejamentoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaPlanejamentoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Planejamento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return agendaPlanejamentoAdd(agendaPlanejamentoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/agendaPlanejamentoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/agendaPlanejamentoDelete/{id}")
	public ModelAndView agendaPlanejamentoDelete(@PathVariable("id") long agendaPlanejamentoId, @Valid PlanejamentoForm planejamentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/agendaPlanejamentoHome");
		
		
		AgendaPlanejamento agendaPlanejamento = agendaPlanejamentoService.getAgendaPlanejamentoByAgendaPlanejamentoPK(agendaPlanejamentoId);
		try {
			agendaPlanejamentoService.delete(agendaPlanejamentoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Agenda/Planejamento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Agenda/Planejamento não excluído. Existe(m) relacionamento(s) de Agenda/Planejamento ** "+ 
										  agendaPlanejamento.getAgenda().getAgendaNome() +
										  " / " +
										  agendaPlanejamento.getPlanejamento().getPlanejamentoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/agendaPlanejamentoEdit/{agendaPlanejamentoPK}")
	public ModelAndView agendaPlanejamentoEdit(@PathVariable("agendaPlanejamentoPK") Long agendaPlanejamentoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaPlanejamento/agendaPlanejamentoEdit");
		AgendaPlanejamento agendaPlanejamento = agendaPlanejamentoService.getAgendaPlanejamentoByAgendaPlanejamentoPK(agendaPlanejamentoPK);
		AgendaPlanejamentoForm agendaPlanejamentoForm = agendaPlanejamentoService.converteAgendaPlanejamento(agendaPlanejamento);
		mv.addObject("agendaPlanejamentoForm", agendaPlanejamentoForm);
		mv.addObject("agendaPlanejamentoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaPlanejamentoEnvioValues", AgendaPlanejamentoEnvio.values());
		mv.addObject("agendaPlanejamentoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoPageable = PageRequest.of(0, 200, Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(planejamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/agendaPlanejamentoHome")
	public ModelAndView agendaPlanejamentoHome(@Valid AgendaPlanejamentoByAgendaForm agendaPlanejamentoByAgendaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaPlanejamento/agendaPlanejamentoHome");
		
		List<AgendaPlanejamento> agendaPlanejamentoList = new ArrayList<AgendaPlanejamento>();
		
		int agendaPlanejamentosTotal = 0;
		
		if (agendaPlanejamentoByAgendaForm.getSearchPlanejamentoNome() == null) {
			agendaPlanejamentoByAgendaForm.setSearchAgendaNome("");
			agendaPlanejamentoByAgendaForm.setSearchPlanejamentoNome("");
			if (agendaPlanejamentoByAgendaForm.getAgendaPlanejamentoSortTipo() == null) {
				agendaPlanejamentoByAgendaForm.setAgendaPlanejamentoSortTipo("PlanejamentoNome");	
			}
			
		}

		if (agendaPlanejamentoByAgendaForm.getAgendaPlanejamentoSortTipo().equalsIgnoreCase("AgendaNome")
				|| agendaPlanejamentoByAgendaForm.getAgendaPlanejamentoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"agenda.agendaNome","planejamento.planejamentoNome"); 
		
		} else if (agendaPlanejamentoByAgendaForm.getAgendaPlanejamentoSortTipo().equalsIgnoreCase("PlanejamentoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"planejamento.planejamentoNome","agenda.agendaNome"); 

		}

		if ( ! agendaPlanejamentoByAgendaForm.getSearchPlanejamentoNome().equalsIgnoreCase("")){
			agendaPlanejamentoList = agendaPlanejamentoService.getByPlanejamentoNome(agendaPlanejamentoByAgendaForm.getSearchPlanejamentoNome(),pageable);
			agendaPlanejamentosTotal = agendaPlanejamentoService.getByPlanejamentoNome(agendaPlanejamentoByAgendaForm.getSearchPlanejamentoNome()).size();
			
		} else {
			agendaPlanejamentoList = agendaPlanejamentoService.getByAgendaNome(agendaPlanejamentoByAgendaForm.getSearchAgendaNome(),pageable);
			agendaPlanejamentosTotal = agendaPlanejamentoService.getByAgendaNome(agendaPlanejamentoByAgendaForm.getSearchAgendaNome()).size();

		} 
		
		Page<AgendaPlanejamento> agendaPlanejamentoPage = new PageImpl<AgendaPlanejamento>(agendaPlanejamentoList,pageable,agendaPlanejamentosTotal+1);
		
		mv.addObject("agendaPlanejamentoPage", agendaPlanejamentoPage);
		mv.addObject("page",agendaPlanejamentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/agendaPlanejamentoSave")
	public ModelAndView agendaPlanejamentoSave(@Valid AgendaPlanejamentoForm agendaPlanejamentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaPlanejamentoAdd(agendaPlanejamentoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/agendaPlanejamentoHome");

		try {
			agendaPlanejamentoService.save(agendaPlanejamentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaPlanejamentoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Planejamento já existente no cadastro.");
			        result.addError(error);			
			}
            return agendaPlanejamentoAdd(agendaPlanejamentoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/agendaPlanejamentoRelMenu")
	public ModelAndView agendaPlanejamentoRelMenu() {

		ModelAndView mv = new ModelAndView("agendaPlanejamento/agendaPlanejamentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/agendaPlanejamentoRel001")
	public ModelAndView agendaPlanejamentoRel001() {

		ModelAndView mv = new ModelAndView("agendaPlanejamento/agendaPlanejamentoRel001");
		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agenda.agendaNome","planejamento.planejamentoNome");
		mv.addObject("agendaPlanejamentoPage", agendaPlanejamentoService.getAgendaPlanejamentoAll(agendaPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/agendaPlanejamentoView/{id}")
	public ModelAndView agendaPlanejamentoView(@PathVariable("id") Long agendaPlanejamentoId) {

		AgendaPlanejamento agendaPlanejamento = agendaPlanejamentoService.getAgendaPlanejamentoByAgendaPlanejamentoPK(agendaPlanejamentoId);
		ModelAndView mv = new ModelAndView("agendaPlanejamento/agendaPlanejamentoView");
		AgendaPlanejamentoForm agendaPlanejamentoForm = agendaPlanejamentoService.converteAgendaPlanejamentoView(agendaPlanejamento);
		AgendaForm agendaForm = agendaService.converteAgendaView(agendaPlanejamento.getAgenda());
		PlanejamentoForm planejamentoForm = planejamentoService.convertePlanejamentoView(agendaPlanejamento.getPlanejamento());
		mv.addObject("agendaPlanejamentoForm", agendaPlanejamentoForm);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("planejamentoForm", planejamentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}