package br.com.j4business.saga.agendatreinamento.controller;

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

import br.com.j4business.saga.agenda.model.AgendaForm;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.treinamento.model.TreinamentoForm;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.agendatreinamento.enumeration.AgendaTreinamentoEnvio;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamento;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamentoByAgendaForm;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamentoForm;
import br.com.j4business.saga.agendatreinamento.service.AgendaTreinamentoService;

@Controller
public class AgendaTreinamentoController {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AgendaTreinamentoService agendaTreinamentoService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/agendaTreinamentoAdd", method = RequestMethod.GET)
	public ModelAndView agendaTreinamentoAdd(AgendaTreinamentoForm agendaTreinamentoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaTreinamento/agendaTreinamentoAdd");
		agendaTreinamentoForm = agendaTreinamentoService.agendaTreinamentoParametros(agendaTreinamentoForm);
		mv.addObject("agendaTreinamentoForm", agendaTreinamentoForm);
		mv.addObject("agendaTreinamentoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaTreinamentoEnvioValues", AgendaTreinamentoEnvio.values());
		mv.addObject("agendaTreinamentoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = new PageRequest(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable treinamentoPageable = new PageRequest(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/agendaTreinamentoCreate", method = RequestMethod.POST)
	public ModelAndView agendaTreinamentoCreate(@Valid AgendaTreinamentoForm agendaTreinamentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaTreinamentoAdd(agendaTreinamentoForm,pageable);
		}

		if (agendaTreinamentoForm.getAgendaTreinamentoPK() > 0) {
			return this.agendaTreinamentoSave(agendaTreinamentoForm, result, attributes,pageable);
			
		}
		
		try {
			agendaTreinamentoService.create(agendaTreinamentoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaTreinamentoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Treinamento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return agendaTreinamentoAdd(agendaTreinamentoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/agendaTreinamentoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/agendaTreinamentoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView agendaTreinamentoDelete(@PathVariable("id") long agendaTreinamentoId, @Valid TreinamentoForm treinamentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/agendaTreinamentoHome");
		
		
		AgendaTreinamento agendaTreinamento = agendaTreinamentoService.getAgendaTreinamentoByAgendaTreinamentoPK(agendaTreinamentoId);
		try {
			agendaTreinamentoService.delete(agendaTreinamentoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Agenda/Treinamento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Agenda/Treinamento não excluído. Existe(m) relacionamento(s) de Agenda/Treinamento ** "+ 
										  agendaTreinamento.getAgenda().getAgendaNome() +
										  " / " +
										  agendaTreinamento.getTreinamento().getTreinamentoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaTreinamentoEdit/{agendaTreinamentoPK}", method = RequestMethod.GET)
	public ModelAndView agendaTreinamentoEdit(@PathVariable("agendaTreinamentoPK") Long agendaTreinamentoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaTreinamento/agendaTreinamentoEdit");
		AgendaTreinamento agendaTreinamento = agendaTreinamentoService.getAgendaTreinamentoByAgendaTreinamentoPK(agendaTreinamentoPK);
		AgendaTreinamentoForm agendaTreinamentoForm = agendaTreinamentoService.converteAgendaTreinamento(agendaTreinamento);
		mv.addObject("agendaTreinamentoForm", agendaTreinamentoForm);
		mv.addObject("agendaTreinamentoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("agendaTreinamentoEnvioValues", AgendaTreinamentoEnvio.values());
		mv.addObject("agendaTreinamentoStatusValues", AtributoStatus.values());

		Pageable agendaPageable = new PageRequest(0, 200, Direction.ASC, "agendaNome");
		mv.addObject("agendaPage", agendaService.getAgendaAll(agendaPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable treinamentoPageable = new PageRequest(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/agendaTreinamentoHome", method = RequestMethod.GET)
	public ModelAndView agendaTreinamentoHome(@Valid AgendaTreinamentoByAgendaForm agendaTreinamentoByAgendaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("agendaTreinamento/agendaTreinamentoHome");
		
		List<AgendaTreinamento> agendaTreinamentoList = new ArrayList<AgendaTreinamento>();
		
		int agendaTreinamentosTotal = 0;
		
		if (agendaTreinamentoByAgendaForm.getSearchTreinamentoNome() == null) {
			agendaTreinamentoByAgendaForm.setSearchAgendaNome("");
			agendaTreinamentoByAgendaForm.setSearchTreinamentoNome("");
			if (agendaTreinamentoByAgendaForm.getAgendaTreinamentoSortTipo() == null) {
				agendaTreinamentoByAgendaForm.setAgendaTreinamentoSortTipo("TreinamentoNome");	
			}
			
		}

		if (agendaTreinamentoByAgendaForm.getAgendaTreinamentoSortTipo().equalsIgnoreCase("AgendaNome")
				|| agendaTreinamentoByAgendaForm.getAgendaTreinamentoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"agenda.agendaNome","treinamento.treinamentoNome"); 
		
		} else if (agendaTreinamentoByAgendaForm.getAgendaTreinamentoSortTipo().equalsIgnoreCase("TreinamentoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"treinamento.treinamentoNome","agenda.agendaNome"); 

		}

		if ( ! agendaTreinamentoByAgendaForm.getSearchTreinamentoNome().equalsIgnoreCase("")){
			agendaTreinamentoList = agendaTreinamentoService.getByTreinamentoNome(agendaTreinamentoByAgendaForm.getSearchTreinamentoNome(),pageable);
			agendaTreinamentosTotal = agendaTreinamentoService.getByTreinamentoNome(agendaTreinamentoByAgendaForm.getSearchTreinamentoNome()).size();
			
		} else {
			agendaTreinamentoList = agendaTreinamentoService.getByAgendaNome(agendaTreinamentoByAgendaForm.getSearchAgendaNome(),pageable);
			agendaTreinamentosTotal = agendaTreinamentoService.getByAgendaNome(agendaTreinamentoByAgendaForm.getSearchAgendaNome()).size();

		} 
		
		Page<AgendaTreinamento> agendaTreinamentoPage = new PageImpl<AgendaTreinamento>(agendaTreinamentoList,pageable,agendaTreinamentosTotal+1);
		
		mv.addObject("agendaTreinamentoPage", agendaTreinamentoPage);
		mv.addObject("page",agendaTreinamentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/agendaTreinamentoSave", method = RequestMethod.POST)
	public ModelAndView agendaTreinamentoSave(@Valid AgendaTreinamentoForm agendaTreinamentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return agendaTreinamentoAdd(agendaTreinamentoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/agendaTreinamentoHome");

		try {
			agendaTreinamentoService.save(agendaTreinamentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("agendaTreinamentoUnique")) {
			        ObjectError error = new ObjectError("agendaNome","Relacionamento entre Cenário e Treinamento já existente no cadastro.");
			        result.addError(error);			
			}
            return agendaTreinamentoAdd(agendaTreinamentoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@RequestMapping(path = "/agendaTreinamentoRelMenu", method = RequestMethod.GET)
	public ModelAndView agendaTreinamentoRelMenu() {

		ModelAndView mv = new ModelAndView("agendaTreinamento/agendaTreinamentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/agendaTreinamentoRel001")
	public ModelAndView agendaTreinamentoRel001() {

		ModelAndView mv = new ModelAndView("agendaTreinamento/agendaTreinamentoRel001");
		Pageable agendaPageable = new PageRequest(0, 200, Direction.ASC, "agenda.agendaNome","treinamento.treinamentoNome");
		mv.addObject("agendaTreinamentoPage", agendaTreinamentoService.getAgendaTreinamentoAll(agendaPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/agendaTreinamentoView/{id}", method = RequestMethod.GET)
	public ModelAndView agendaTreinamentoView(@PathVariable("id") Long agendaTreinamentoId) {

		AgendaTreinamento agendaTreinamento = agendaTreinamentoService.getAgendaTreinamentoByAgendaTreinamentoPK(agendaTreinamentoId);
		ModelAndView mv = new ModelAndView("agendaTreinamento/agendaTreinamentoView");
		AgendaTreinamentoForm agendaTreinamentoForm = agendaTreinamentoService.converteAgendaTreinamentoView(agendaTreinamento);
		AgendaForm agendaForm = agendaService.converteAgendaView(agendaTreinamento.getAgenda());
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamentoView(agendaTreinamento.getTreinamento());
		mv.addObject("agendaTreinamentoForm", agendaTreinamentoForm);
		mv.addObject("agendaForm", agendaForm);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}