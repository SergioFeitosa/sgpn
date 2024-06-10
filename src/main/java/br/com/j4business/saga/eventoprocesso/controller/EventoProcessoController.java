package br.com.j4business.saga.eventoprocesso.controller;

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

import br.com.j4business.saga.evento.model.EventoForm;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.eventoprocesso.enumeration.EventoProcessoImpacto;
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;
import br.com.j4business.saga.eventoprocesso.model.EventoProcessoByEventoForm;
import br.com.j4business.saga.eventoprocesso.model.EventoProcessoForm;
import br.com.j4business.saga.eventoprocesso.service.EventoProcessoService;

@Controller
public class EventoProcessoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EventoProcessoService eventoProcessoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/eventoProcessoCreate")
	public String eventoProcessoCreate() {

		return "eventoProcesso/eventoProcessoCreate";
	}

	@GetMapping(path = "/eventoProcessoAdd")
	public ModelAndView eventoProcessoAdd(EventoProcessoForm eventoProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("eventoProcesso/eventoProcessoAdd");
		eventoProcessoForm = eventoProcessoService.eventoProcessoParametros(eventoProcessoForm);
		mv.addObject("eventoProcessoForm", eventoProcessoForm);
		mv.addObject("eventoProcessoImpactoValues", EventoProcessoImpacto.values());
		mv.addObject("eventoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable eventoPageable = PageRequest.of(0, 200, Direction.ASC, "eventoNome");
		mv.addObject("eventoPage", eventoService.getEventoAll(eventoPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/eventoProcessoCreate")
	public ModelAndView eventoProcessoCreate(@Valid EventoProcessoForm eventoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return eventoProcessoAdd(eventoProcessoForm,pageable);
		}

		if (eventoProcessoForm.getEventoProcessoPK() > 0) {
			return this.eventoProcessoSave(eventoProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			eventoProcessoService.create(eventoProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("eventoProcessoUnique")) {
			        ObjectError error = new ObjectError("eventoNome","Relacionamento entre Evento e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return eventoProcessoAdd(eventoProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/eventoProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/eventoProcessoDelete/{id}")
	public ModelAndView eventoProcessoDelete(@PathVariable("id") long eventoProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/eventoProcessoHome");
		
		
		EventoProcesso eventoProcesso = eventoProcessoService.getEventoProcessoByEventoProcessoPK(eventoProcessoId);
		try {
			eventoProcessoService.delete(eventoProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Evento/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Evento/Processo não excluído. Existe(m) relacionamento(s) de Evento/Processo ** "+ 
										  eventoProcesso.getEvento().getEventoNome() +
										  " / " +
										  eventoProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/eventoProcessoEdit/{eventoProcessoPK}")
	public ModelAndView eventoProcessoEdit(@PathVariable("eventoProcessoPK") Long eventoProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("eventoProcesso/eventoProcessoEdit");
		EventoProcesso eventoProcesso = eventoProcessoService.getEventoProcessoByEventoProcessoPK(eventoProcessoPK);
		EventoProcessoForm eventoProcessoForm = eventoProcessoService.converteEventoProcesso(eventoProcesso);
		mv.addObject("eventoProcessoForm", eventoProcessoForm);
		mv.addObject("eventoProcessoImpactoValues", EventoProcessoImpacto.values());
		mv.addObject("eventoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable eventoPageable = PageRequest.of(0, 200, Direction.ASC, "eventoNome");
		mv.addObject("eventoPage", eventoService.getEventoAll(eventoPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/eventoProcessoHome")
	public ModelAndView eventoProcessoHome(@Valid EventoProcessoByEventoForm eventoProcessoByEventoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("eventoProcesso/eventoProcessoHome");
		
		List<EventoProcesso> eventoProcessoList = new ArrayList<EventoProcesso>();
		
		int eventoProcessoTotal = 0;
		
		if (eventoProcessoByEventoForm.getSearchProcessoNome() == null) {
			eventoProcessoByEventoForm.setSearchEventoNome("");
			eventoProcessoByEventoForm.setSearchProcessoNome("");
			if (eventoProcessoByEventoForm.getEventoProcessoSortTipo() == null) {
				eventoProcessoByEventoForm.setEventoProcessoSortTipo("ProcessoNome");	
			}
			
		}

		if (eventoProcessoByEventoForm.getEventoProcessoSortTipo().equalsIgnoreCase("EventoNome")
				|| eventoProcessoByEventoForm.getEventoProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"evento.eventoNome","processo.processoNome"); 
		
		} else if (eventoProcessoByEventoForm.getEventoProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","evento.eventoNome"); 

		}

		if ( ! eventoProcessoByEventoForm.getSearchProcessoNome().equalsIgnoreCase("")){
			eventoProcessoList = eventoProcessoService.getByProcessoNome(eventoProcessoByEventoForm.getSearchProcessoNome(),pageable);
			eventoProcessoTotal = eventoProcessoService.getByProcessoNome(eventoProcessoByEventoForm.getSearchProcessoNome()).size();
			
		} else {
			eventoProcessoList = eventoProcessoService.getByEventoNome(eventoProcessoByEventoForm.getSearchEventoNome(),pageable);
			eventoProcessoTotal = eventoProcessoService.getByEventoNome(eventoProcessoByEventoForm.getSearchEventoNome()).size();

		}
		
		Page<EventoProcesso> eventoProcessoPage = new PageImpl<EventoProcesso>(eventoProcessoList,pageable,eventoProcessoTotal+1);
		
		mv.addObject("eventoProcessoPage", eventoProcessoPage);
		mv.addObject("page",eventoProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/eventoProcessoSave")
	public ModelAndView eventoProcessoSave(@Valid EventoProcessoForm eventoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return eventoProcessoAdd(eventoProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/eventoProcessoHome");

		try {
			eventoProcessoService.save(eventoProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("eventoProcessoUnique")) {
		        ObjectError error = new ObjectError("eventoNome","Relacionamento entre Evento e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return eventoProcessoAdd(eventoProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/eventoProcessoRelMenu")
	public ModelAndView eventoProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("eventoProcesso/eventoProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/eventoProcessoRel001")
	public ModelAndView eventoProcessoRel001() {

		ModelAndView mv = new ModelAndView("eventoProcesso/eventoProcessoRel001");
		Pageable eventoProcessoPageable = PageRequest.of(0, 200, Direction.ASC,"evento.eventoNome","processo.processoNome");
		mv.addObject("eventoProcessoPage", eventoProcessoService.getEventoProcessoAll(eventoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/eventoProcessoView/{id}")
	public ModelAndView eventoProcessoView(@PathVariable("id") Long eventoProcessoId) {

		EventoProcesso eventoProcesso = eventoProcessoService.getEventoProcessoByEventoProcessoPK(eventoProcessoId);
		ModelAndView mv = new ModelAndView("eventoProcesso/eventoProcessoView");
		EventoForm eventoForm = eventoService.converteEventoView(eventoProcesso.getEvento());
		ProcessoForm processoForm = processoService.converteProcessoView(eventoProcesso.getProcesso());
		EventoProcessoForm eventoProcessoForm = eventoProcessoService.converteEventoProcessoView(eventoProcesso);
		mv.addObject("eventoForm", eventoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("eventoProcessoForm", eventoProcessoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}