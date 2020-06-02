package br.com.j4business.saga.agenda.controller;

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

import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.model.AgendaByAgendaForm;
import br.com.j4business.saga.agenda.model.AgendaForm;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

@Controller
public class AgendaController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/agendaAdd", method = RequestMethod.GET)
	public ModelAndView agendaAdd(AgendaForm agendaForm) {

		ModelAndView mv = new ModelAndView("agenda/agendaAdd");
		agendaForm = agendaService.agendaParametros(agendaForm);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("agendaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaCreate", method = RequestMethod.POST)
	public ModelAndView agendaCreate(@Valid AgendaForm agendaForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return agendaAdd(agendaForm);
		}

		if (agendaForm.getAgendaPK() > 0) {
			return this.agendaSave(agendaForm, result, attributes);
			
		}

		try {
			agendaService.create(agendaForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equals("agendaNome")) {
			        ObjectError error = new ObjectError("agendaNome","Nome da Agenda já existente no cadastro.");
			        result.addError(error);			
			}
            
			return agendaAdd(agendaForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/agendaHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaDelete/{id}", method = RequestMethod.GET)
	public ModelAndView agendaDelete(@PathVariable("id") long agendaPK, @Valid AgendaForm agendaForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/agendaHome");

		Agenda agenda = agendaService.getAgendaByAgendaPK(agendaPK);
		try {
			agendaService.delete(agendaPK);

			attributes.addFlashAttribute("mensagem", "Agenda excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Agenda não excluída. Existe(m) relacionamento(s) de Agenda ** "+ agenda.getAgendaNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@RequestMapping(path = "/agendaEdit/{id}", method = RequestMethod.GET)
	public ModelAndView agendaEdit(@PathVariable("id") Long agendaId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("agenda/agendaEdit");
		Agenda agenda = agendaService.getAgendaByAgendaPK(agendaId);
		AgendaForm agendaForm = agendaService.converteAgenda(agenda);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("agendaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaHome", method = RequestMethod.GET)
	public ModelAndView agendaHome(@Valid AgendaByAgendaForm agendaByAgendaForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("agenda/agendaHome");
		
		List<Agenda> agendaList = new ArrayList<Agenda>();

		int agendasTotal = 0;
		
		if (agendaByAgendaForm.getSearchAgendaNome() == null) {
			agendaByAgendaForm.setSearchAgendaNome("");
			agendaByAgendaForm.setSearchAgendaDescricao("");
			if (agendaByAgendaForm.getAgendaSortTipo() == null) {
				agendaByAgendaForm.setAgendaSortTipo("AgendaNome");
			}

		}

		if (agendaByAgendaForm.getAgendaSortTipo().equalsIgnoreCase("AgendaNome") || agendaByAgendaForm.getAgendaSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "agendaNome");

		} else if (agendaByAgendaForm.getAgendaSortTipo().equalsIgnoreCase("AgendaDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "agendaDescricao");

		}

		if ((!agendaByAgendaForm.getSearchAgendaNome().equalsIgnoreCase(""))) {
			agendaList = agendaService.getByAgendaNome(agendaByAgendaForm.getSearchAgendaNome(), pageable);
			agendasTotal = agendaService.getByAgendaNome(agendaByAgendaForm.getSearchAgendaNome()).size();

		} else {
			agendaList = agendaService.getByAgendaDescricao(agendaByAgendaForm.getSearchAgendaDescricao(), pageable);
			agendasTotal = agendaService.getByAgendaDescricao(agendaByAgendaForm.getSearchAgendaDescricao()).size();
		}

		Page<Agenda> agendaPage = new PageImpl<Agenda>(agendaList, pageable, agendasTotal+1);

		mv.addObject("agendaPage", agendaPage);
		mv.addObject("page", agendaPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaRelMenu", method = RequestMethod.GET)
	public ModelAndView agendaRelMenu() {

		ModelAndView mv = new ModelAndView("agenda/agendaRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping(path = "/agendaRel001", method = RequestMethod.GET)
	public ModelAndView agendaRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("agenda/agendaRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaSave", method = RequestMethod.POST)
	public ModelAndView agendaSave(@Valid AgendaForm agendaForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return agendaAdd(agendaForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/agendaHome");

		try {
			agendaService.save(agendaForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaNome")) {
			        ObjectError error = new ObjectError("agendaNome","Nome da Agenda já existente no cadastro.");
			        result.addError(error);			
			}
            return agendaAdd(agendaForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/agendaView/{id}", method = RequestMethod.GET)
	public ModelAndView agendaView(@PathVariable("id") Long agendaId) {

		Agenda agenda = agendaService.getAgendaByAgendaPK(agendaId);
		ModelAndView mv = new ModelAndView("agenda/agendaView");
		AgendaForm agendaForm = agendaService.converteAgendaView(agenda);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}


}