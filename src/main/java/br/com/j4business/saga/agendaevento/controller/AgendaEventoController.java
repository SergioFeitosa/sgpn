package br.com.j4business.saga.agendaevento.controller;

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
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.evento.model.EventoForm;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.agendaevento.enumeration.AgendaEventoEnvio;
import br.com.j4business.saga.agendaevento.model.AgendaEventoByAgendaForm;
import br.com.j4business.saga.agendaevento.model.AgendaEventoForm;
import br.com.j4business.saga.agendaevento.service.AgendaEventoService;

@Controller
public class AgendaEventoController {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AgendaEventoService agendaEventoService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/agendaEventoAdd")
	public ModelAndView agendaEventoAdd(AgendaEventoForm agendaEventoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaEvento/agendaEventoAdd");
		agendaEventoForm = agendaEventoService.agendaEventoParametros(agendaEventoForm);
		mv.addObject("agendaEventoForm", agendaEventoForm);
		mv.addObject("agendaEventoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaEventoEnvioValues", AgendaEventoEnvio.values());
		mv.addObject("agendaEventoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable eventoPageable = PageRequest.of(0, 200, Direction.ASC, "eventoNome");
		mv.addObject("eventoPage", eventoService.getEventoAll(eventoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/agendaEventoCreate")
	public ModelAndView agendaEventoCreate(@Valid AgendaEventoForm agendaEventoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaEventoAdd(agendaEventoForm,pageable);
		}

		if (agendaEventoForm.getAgendaEventoPK() > 0) {
			return this.agendaEventoSave(agendaEventoForm, result, attributes,pageable);
			
		}
		
		try {
			agendaEventoService.create(agendaEventoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaEventoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Evento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return agendaEventoAdd(agendaEventoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/agendaEventoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/agendaEventoDelete/{id}")
	public ModelAndView agendaEventoDelete(@PathVariable("id") long agendaEventoId, @Valid EventoForm eventoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/agendaEventoHome");
		
		
		AgendaEvento agendaEvento = agendaEventoService.getAgendaEventoByAgendaEventoPK(agendaEventoId);
		try {
			agendaEventoService.delete(agendaEventoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Agenda/Evento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Agenda/Evento não excluído. Existe(m) relacionamento(s) de Agenda/Evento ** "+ 
										  agendaEvento.getAgenda().getAgendaNome() +
										  " / " +
										  agendaEvento.getEvento().getEventoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;

	}

	@GetMapping(path = "/agendaEventoEdit/{agendaEventoPK}")
	public ModelAndView agendaEventoEdit(@PathVariable("agendaEventoPK") Long agendaEventoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaEvento/agendaEventoEdit");
		AgendaEvento agendaEvento = agendaEventoService.getAgendaEventoByAgendaEventoPK(agendaEventoPK);
		AgendaEventoForm agendaEventoForm = agendaEventoService.converteAgendaEvento(agendaEvento);
		mv.addObject("agendaEventoForm", agendaEventoForm);
		mv.addObject("agendaEventoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaEventoEnvioValues", AgendaEventoEnvio.values());
		mv.addObject("agendaEventoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable eventoPageable = PageRequest.of(0, 200, Direction.ASC, "eventoNome");
		mv.addObject("eventoPage", eventoService.getEventoAll(eventoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/agendaEventoHome")
	public ModelAndView agendaEventoHome(@Valid AgendaEventoByAgendaForm agendaEventoByAgendaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaEvento/agendaEventoHome");
		
		List<AgendaEvento> agendaEventoList = new ArrayList<AgendaEvento>();
		
		int agendaEventosTotal = 0;
		
		if (agendaEventoByAgendaForm.getSearchEventoNome() == null) {
			agendaEventoByAgendaForm.setSearchAgendaNome("");
			agendaEventoByAgendaForm.setSearchEventoNome("");
			if (agendaEventoByAgendaForm.getAgendaEventoSortTipo() == null) {
				agendaEventoByAgendaForm.setAgendaEventoSortTipo("EventoNome");	
			}
			
		}

		if (agendaEventoByAgendaForm.getAgendaEventoSortTipo().equalsIgnoreCase("AgendaNome")
				|| agendaEventoByAgendaForm.getAgendaEventoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"agenda.agendaNome","evento.eventoNome"); 
		
		} else if (agendaEventoByAgendaForm.getAgendaEventoSortTipo().equalsIgnoreCase("EventoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"evento.eventoNome","agenda.agendaNome"); 

		}

		if ( ! agendaEventoByAgendaForm.getSearchEventoNome().equalsIgnoreCase("")){
			agendaEventoList = agendaEventoService.getByEventoNome(agendaEventoByAgendaForm.getSearchEventoNome(),pageable);
			agendaEventosTotal = agendaEventoService.getByEventoNome(agendaEventoByAgendaForm.getSearchEventoNome()).size();
			
		} else {
			agendaEventoList = agendaEventoService.getByAgendaNome(agendaEventoByAgendaForm.getSearchAgendaNome(),pageable);
			agendaEventosTotal = agendaEventoService.getByAgendaNome(agendaEventoByAgendaForm.getSearchAgendaNome()).size();

		} 
		
		Page<AgendaEvento> agendaEventoPage = new PageImpl<AgendaEvento>(agendaEventoList,pageable,agendaEventosTotal+1);
		
		mv.addObject("agendaEventoPage", agendaEventoPage);
		mv.addObject("page",agendaEventoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/agendaEventoSave")
	public ModelAndView agendaEventoSave(@Valid AgendaEventoForm agendaEventoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaEventoAdd(agendaEventoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/agendaEventoHome");

		try {
			agendaEventoService.save(agendaEventoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaEventoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Evento já existente no cadastro.");
			        result.addError(error);			
			}
            return agendaEventoAdd(agendaEventoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/agendaEventoRelMenu")
	public ModelAndView agendaEventoRelMenu() {

		ModelAndView mv = new ModelAndView("agendaEvento/agendaEventoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/agendaEventoRel001")
	public ModelAndView agendaEventoRel001() {

		ModelAndView mv = new ModelAndView("agendaEvento/agendaEventoRel001");
		Pageable agendaPageable = PageRequest.of(0, 200, Direction.ASC, "agenda.agendaNome","evento.eventoNome");
		mv.addObject("agendaEventoPage", agendaEventoService.getAgendaEventoAll(agendaPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/agendaEventoView/{id}")
	public ModelAndView agendaEventoView(@PathVariable("id") Long agendaEventoId) {

		AgendaEvento agendaEvento = agendaEventoService.getAgendaEventoByAgendaEventoPK(agendaEventoId);
		ModelAndView mv = new ModelAndView("agendaEvento/agendaEventoView");
		AgendaEventoForm agendaEventoForm = agendaEventoService.converteAgendaEventoView(agendaEvento);
		AgendaForm agendaForm = agendaService.converteAgendaView(agendaEvento.getAgenda());
		EventoForm eventoForm = eventoService.converteEventoView(agendaEvento.getEvento());
		mv.addObject("agendaEventoForm", agendaEventoForm);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("eventoForm", eventoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}