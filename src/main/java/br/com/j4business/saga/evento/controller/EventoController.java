package br.com.j4business.saga.evento.controller;

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

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.evento.model.EventoByEventoForm;
import br.com.j4business.saga.evento.model.EventoForm;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.eventoprocesso.service.EventoProcessoService;

@Controller
public class EventoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EventoProcessoService eventoProcessoService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/eventoAdd", method = RequestMethod.GET)
	public ModelAndView eventoAdd(EventoForm eventoForm) {

		ModelAndView mv = new ModelAndView("evento/eventoAdd");
		eventoForm = eventoService.eventoParametros(eventoForm);
		mv.addObject("eventoForm", eventoForm);
		mv.addObject("eventoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("eventoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/eventoCreate", method = RequestMethod.POST)
	public ModelAndView eventoCreate(@Valid EventoForm eventoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return eventoAdd(eventoForm);
		}

		if (eventoForm.getEventoPK() > 0) {
			return this.eventoSave(eventoForm, result, attributes);
			
		}
		
		try {
			eventoService.create(eventoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("eventoNome")) {
			        ObjectError error = new ObjectError("eventoNome","Nome do Evento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return eventoAdd(eventoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/eventoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/eventoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView eventoDelete(@PathVariable("id") long eventoPK, @Valid EventoForm eventoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/eventoHome");

		Evento evento = eventoService.getEventoByEventoPK(eventoPK);
		try {
			eventoService.delete(eventoPK);

			attributes.addFlashAttribute("mensagem", "Evento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Evento não excluído. Existe(m) relacionamento(s) de Evento ** "+ evento.getEventoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping("/eventoHome")
	public ModelAndView eventoHome(@Valid EventoByEventoForm eventoByEventoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("evento/eventoHome");

		List<Evento> eventoList = new ArrayList<Evento>();

		int eventoTotal = 0;
		
		if (eventoByEventoForm.getSearchEventoNome() == null) {
			eventoByEventoForm.setSearchEventoNome("");
			eventoByEventoForm.setSearchEventoDescricao("");
			if (eventoByEventoForm.getEventoSortTipo() == null) {
				eventoByEventoForm.setEventoSortTipo("EventoNome");
			}

		}

		if (eventoByEventoForm.getEventoSortTipo().equalsIgnoreCase("EventoNome") || eventoByEventoForm.getEventoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "eventoNome");

		} else if (eventoByEventoForm.getEventoSortTipo().equalsIgnoreCase("EventoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "eventoDescricao");

		}
 
		if ((!eventoByEventoForm.getSearchEventoNome().equalsIgnoreCase(""))) {
			eventoList = eventoService.getByEventoNome(eventoByEventoForm.getSearchEventoNome(), pageable);
			eventoTotal = eventoService.getByEventoNome(eventoByEventoForm.getSearchEventoNome()).size();

		} else {
			eventoList = eventoService.getByEventoDescricao(eventoByEventoForm.getSearchEventoDescricao(), pageable);
			eventoTotal = eventoService.getByEventoDescricao(eventoByEventoForm.getSearchEventoDescricao()).size();
		}

		Page<Evento> eventoPage = new PageImpl<Evento>(eventoList, pageable, eventoTotal+1);

		mv.addObject("eventoPage", eventoPage);
		mv.addObject("page", eventoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/eventoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView eventoEdit(@PathVariable("id") Long eventoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("evento/eventoEdit");
		Evento evento = eventoService.getEventoByEventoPK(eventoPK);
		EventoForm eventoForm = eventoService.converteEvento(evento);
		mv.addObject("eventoForm", eventoForm);
		mv.addObject("eventoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("eventoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable eventoProcessoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("eventoProcessoPage", eventoProcessoService.getByEventoPK(eventoPK, eventoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/eventoSave", method = RequestMethod.POST)
	public ModelAndView eventoSave(@Valid EventoForm eventoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return eventoAdd(eventoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/eventoHome");

		try {
			eventoService.save(eventoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("eventoNome")) {
			        ObjectError error = new ObjectError("eventoNome","Nome do Evento já existente no cadastro.");
			        result.addError(error);			
			}
            return eventoAdd(eventoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/eventoRelMenu", method = RequestMethod.GET)
	public ModelAndView eventoRelMenu() {

		ModelAndView mv = new ModelAndView("evento/eventoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/eventoRel001")
	public ModelAndView eventoRel001() {

		ModelAndView mv = new ModelAndView("evento/eventoRel001");
		Pageable pageable = new PageRequest(0, 200 , Direction.ASC, "eventoNome");
		mv.addObject("eventoPage", eventoService.getEventoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/eventoView/{id}", method = RequestMethod.GET)
	public ModelAndView eventoView(@PathVariable("id") Long eventoId) {

		Evento evento = eventoService.getEventoByEventoPK(eventoId);
		ModelAndView mv = new ModelAndView("evento/eventoView");
		EventoForm eventoForm = eventoService.converteEventoView(evento);
		mv.addObject("eventoForm", eventoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}