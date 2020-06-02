package br.com.j4business.saga.processoatividade.controller;

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
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atividade.model.AtividadeForm;
import br.com.j4business.saga.atividade.service.AtividadeService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividadeByProcessoForm;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividadeForm;
import br.com.j4business.saga.processoatividade.service.ProcessoAtividadeService;

@Controller
public class ProcessoAtividadeController {

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoAtividadeService processoAtividadeService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/processoAtividadeAdd", method = RequestMethod.GET)
	public ModelAndView processoAtividadeAdd(ProcessoAtividadeForm processoAtividadeForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoAtividade/processoAtividadeAdd");
		processoAtividadeForm = processoAtividadeService.processoAtividadeParametros(processoAtividadeForm);
		mv.addObject("processoAtividadeForm", processoAtividadeForm);
		mv.addObject("processoAtividadeStatusValues", AtributoStatus.values());
		Pageable atividadePageable = new PageRequest(0, 200, Direction.ASC, "atividadeNome");
		mv.addObject("atividadePage", atividadeService.getAtividadeAll(atividadePageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoAtividadeCreate", method = RequestMethod.POST)
	public ModelAndView processoAtividadeCreate(@Valid ProcessoAtividadeForm processoAtividadeForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoAtividadeAdd(processoAtividadeForm,pageable);
		}

		if (processoAtividadeForm.getProcessoAtividadePK() > 0) {
			return this.processoAtividadeSave(processoAtividadeForm, result, attributes,pageable);
			
		}
		
		try {
			processoAtividadeService.create(processoAtividadeForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoAtividadeUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Atividade já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoAtividadeAdd(processoAtividadeForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoAtividadeHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoAtividadeDelete/{id}", method = RequestMethod.GET)
	public ModelAndView processoAtividadeDelete(@PathVariable("id") long processoAtividadeId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoAtividadeHome");
		
		
		ProcessoAtividade processoAtividade = processoAtividadeService.getProcessoAtividadeByProcessoAtividadePK(processoAtividadeId);
		try {
			processoAtividadeService.delete(processoAtividadeId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Atividade excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Atividade não excluído. Existe(m) relacionamento(s) de Processo/Atividade ** "+ 
										  processoAtividade.getProcesso().getProcessoNome() +
										  " / " +
										  processoAtividade.getAtividade().getAtividadeNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoAtividadeEdit/{processoAtividadePK}", method = RequestMethod.GET)
	public ModelAndView processoAtividadeEdit(@PathVariable("processoAtividadePK") Long processoAtividadePK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoAtividade/processoAtividadeEdit");
		ProcessoAtividade processoAtividade = processoAtividadeService.getProcessoAtividadeByProcessoAtividadePK(processoAtividadePK);
		ProcessoAtividadeForm processoAtividadeForm = processoAtividadeService.converteProcessoAtividade(processoAtividade);
		mv.addObject("processoAtividadeForm", processoAtividadeForm);
		mv.addObject("processoAtividadeStatusValues", AtributoStatus.values());
		Pageable atividadePageable = new PageRequest(0, 200, Direction.ASC, "atividadeNome");
		mv.addObject("atividadePage", atividadeService.getAtividadeAll(atividadePageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		
		processoAtividade.getProcessoAtividadeSequencia();
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/processoAtividadeHome", method = RequestMethod.GET)
	public ModelAndView processoAtividadeHome(@Valid ProcessoAtividadeByProcessoForm processoAtividadeByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoAtividade/processoAtividadeHome");
		
		List<ProcessoAtividade> processoAtividadeList = new ArrayList<ProcessoAtividade>();
		int processoAtividadeTotal = 0;
		
		if (processoAtividadeByProcessoForm.getSearchProcessoNome() == null) {
			processoAtividadeByProcessoForm.setSearchProcessoNome("");
			processoAtividadeByProcessoForm.setSearchAtividadeNome("");
			if (processoAtividadeByProcessoForm.getProcessoAtividadeSortTipo() == null) {
				processoAtividadeByProcessoForm.setProcessoAtividadeSortTipo("ProcessoNome");	
			}
			
		}

		if (processoAtividadeByProcessoForm.getProcessoAtividadeSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoAtividadeByProcessoForm.getProcessoAtividadeSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","processoAtividadeSequencia"); 
		
		} else if (processoAtividadeByProcessoForm.getProcessoAtividadeSortTipo().equalsIgnoreCase("AtividadeNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"atividade.atividadeNome","processoAtividadeSequencia"); 

		} else if (processoAtividadeByProcessoForm.getProcessoAtividadeSortTipo().equalsIgnoreCase("Sequencia")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processoAtividadeSequencia","processo.processoNome"); 

		}

		if ( ! processoAtividadeByProcessoForm.getSearchProcessoNome().equalsIgnoreCase("")){
			processoAtividadeList = processoAtividadeService.getByProcessoNome(processoAtividadeByProcessoForm.getSearchProcessoNome(),pageable);
			processoAtividadeTotal = processoAtividadeService.getByProcessoNome(processoAtividadeByProcessoForm.getSearchProcessoNome(),pageable).size();
			
		} else if ( ! processoAtividadeByProcessoForm.getSearchAtividadeNome().equalsIgnoreCase("")){
			processoAtividadeList = processoAtividadeService.getByAtividadeNome(processoAtividadeByProcessoForm.getSearchAtividadeNome(),pageable);
			processoAtividadeTotal = processoAtividadeService.getByAtividadeNome(processoAtividadeByProcessoForm.getSearchAtividadeNome(),pageable).size();

		} else {
			processoAtividadeList = processoAtividadeService.getProcessoAtividadeAll(pageable);
			processoAtividadeTotal = processoAtividadeService.getProcessoAtividadeAll(pageable).size();
		}
		
		Page<ProcessoAtividade> processoAtividadePage = new PageImpl<ProcessoAtividade>(processoAtividadeList,pageable,processoAtividadeTotal+1);
		
		mv.addObject("processoAtividadePage", processoAtividadePage);
		mv.addObject("page",processoAtividadePage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoAtividadeSave", method = RequestMethod.POST)
	public ModelAndView processoAtividadeSave(@Valid ProcessoAtividadeForm processoAtividadeForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoAtividadeAdd(processoAtividadeForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoAtividadeHome");

		try {
			processoAtividadeService.save(processoAtividadeForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoAtividadeUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Atividade já existente no cadastro.");
		        result.addError(error);			
		}
            return processoAtividadeAdd(processoAtividadeForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoAtividadeRelMenu", method = RequestMethod.GET)
	public ModelAndView processoAtividadeRelMenu() {

		ModelAndView mv = new ModelAndView("processoAtividade/processoAtividadeRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping(path = "/processoAtividadeRel001", method = RequestMethod.GET)
	public ModelAndView processoAtividadeRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoAtividade/processoAtividadeRel001");
		Pageable processoAtividadePageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","atividade.atividadeNome");
		mv.addObject("processoAtividadePage", processoAtividadeService.getProcessoAtividadeAll(processoAtividadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoAtividadeView/{id}", method = RequestMethod.GET)
	public ModelAndView processoAtividadeView(@PathVariable("id") Long processoAtividadeId) {

		ProcessoAtividade processoAtividade = processoAtividadeService.getProcessoAtividadeByProcessoAtividadePK(processoAtividadeId);
		ModelAndView mv = new ModelAndView("processoAtividade/processoAtividadeView");
		ProcessoAtividadeForm processoAtividadeForm = processoAtividadeService.converteProcessoAtividadeView(processoAtividade);
		ProcessoForm processoForm = processoService.converteProcessoView(processoAtividade.getProcesso());
		AtividadeForm atividadeForm = atividadeService.converteAtividadeView(processoAtividade.getAtividade());
		mv.addObject("processoAtividadeForm", processoAtividadeForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("atividadeForm", atividadeForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}