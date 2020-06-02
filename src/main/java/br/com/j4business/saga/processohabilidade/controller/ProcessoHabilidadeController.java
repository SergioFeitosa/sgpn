package br.com.j4business.saga.processohabilidade.controller;

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

import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.habilidade.model.HabilidadeForm;
import br.com.j4business.saga.habilidade.service.HabilidadeService;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidade;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidadeByProcessoForm;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidadeForm;
import br.com.j4business.saga.processohabilidade.service.ProcessoHabilidadeService;

@Controller
public class ProcessoHabilidadeController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private HabilidadeService habilidadeService;

	@Autowired
	private ProcessoHabilidadeService processoHabilidadeService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/processoHabilidadeAdd", method = RequestMethod.GET)
	public ModelAndView processoHabilidadeAdd(ProcessoHabilidadeForm processoHabilidadeForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoHabilidade/processoHabilidadeAdd");
		processoHabilidadeForm = processoHabilidadeService.processoHabilidadeParametros(processoHabilidadeForm);
		mv.addObject("processoHabilidadeForm", processoHabilidadeForm);
		mv.addObject("processoHabilidadePrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoHabilidadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable habilidadePageable = new PageRequest(0, 200, Direction.ASC, "habilidadeNome");
		mv.addObject("habilidadePage", habilidadeService.getHabilidadeAll(habilidadePageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoHabilidadeCreate", method = RequestMethod.POST)
	public ModelAndView processoHabilidadeCreate(@Valid ProcessoHabilidadeForm processoHabilidadeForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoHabilidadeAdd(processoHabilidadeForm,pageable);
		}

		if (processoHabilidadeForm.getProcessoHabilidadePK() > 0) {
			return this.processoHabilidadeSave(processoHabilidadeForm, result, attributes,pageable);
			
		}
		
		try {
			processoHabilidadeService.create(processoHabilidadeForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoHabilidadeUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Habilidade já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoHabilidadeAdd(processoHabilidadeForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoHabilidadeHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/processoHabilidadeDelete/{id}", method = RequestMethod.GET)
	public ModelAndView processoHabilidadeDelete(@PathVariable("id") long processoHabilidadeId, @Valid HabilidadeForm habilidadeForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoHabilidadeHome");
		
		
		ProcessoHabilidade processoHabilidade = processoHabilidadeService.getProcessoHabilidadeByProcessoHabilidadePK(processoHabilidadeId);
		try {
			processoHabilidadeService.delete(processoHabilidadeId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Habilidade excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Habilidade não excluído. Existe(m) relacionamento(s) de Processo/Habilidade ** "+ 
										  processoHabilidade.getProcesso().getProcessoNome() +
										  " / " +
										  processoHabilidade.getHabilidade().getHabilidadeNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoHabilidadeEdit/{processoHabilidadePK}", method = RequestMethod.GET)
	public ModelAndView processoHabilidadeEdit(@PathVariable("processoHabilidadePK") Long processoHabilidadePK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoHabilidade/processoHabilidadeEdit");
		ProcessoHabilidade processoHabilidade = processoHabilidadeService.getProcessoHabilidadeByProcessoHabilidadePK(processoHabilidadePK);
		ProcessoHabilidadeForm processoHabilidadeForm = processoHabilidadeService.converteProcessoHabilidade(processoHabilidade);
		mv.addObject("processoHabilidadeForm", processoHabilidadeForm);
		mv.addObject("processoHabilidadePrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoHabilidadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable habilidadePageable = new PageRequest(0, 200, Direction.ASC, "habilidadeNome");
		mv.addObject("habilidadePage", habilidadeService.getHabilidadeAll(habilidadePageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/processoHabilidadeHome", method = RequestMethod.GET)
	public ModelAndView processoHabilidadeHome(@Valid ProcessoHabilidadeByProcessoForm processoHabilidadeByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoHabilidade/processoHabilidadeHome");
		
		List<ProcessoHabilidade> processoHabilidadeList = new ArrayList<ProcessoHabilidade>();
		
		int processoHabilidadeTotal = 0;
		
		if (processoHabilidadeByProcessoForm.getSearchHabilidadeNome() == null) {
			processoHabilidadeByProcessoForm.setSearchProcessoNome("");
			processoHabilidadeByProcessoForm.setSearchHabilidadeNome("");
			if (processoHabilidadeByProcessoForm.getProcessoHabilidadeSortTipo() == null) {
				processoHabilidadeByProcessoForm.setProcessoHabilidadeSortTipo("HabilidadeNome");	
			}
			
		}

		if (processoHabilidadeByProcessoForm.getProcessoHabilidadeSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoHabilidadeByProcessoForm.getProcessoHabilidadeSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","habilidade.habilidadeNome"); 
		
		} else if (processoHabilidadeByProcessoForm.getProcessoHabilidadeSortTipo().equalsIgnoreCase("HabilidadeNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"habilidade.habilidadeNome","processo.processoNome"); 

		}

		if ( ! processoHabilidadeByProcessoForm.getSearchHabilidadeNome().equalsIgnoreCase("")){
			processoHabilidadeList = processoHabilidadeService.getByHabilidadeNome(processoHabilidadeByProcessoForm.getSearchHabilidadeNome(),pageable);
			processoHabilidadeTotal = processoHabilidadeService.getByHabilidadeNome(processoHabilidadeByProcessoForm.getSearchHabilidadeNome()).size();
			
		} else {
			processoHabilidadeList = processoHabilidadeService.getByProcessoNome(processoHabilidadeByProcessoForm.getSearchProcessoNome(),pageable);
			processoHabilidadeTotal = processoHabilidadeService.getByProcessoNome(processoHabilidadeByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoHabilidade> processoHabilidadePage = new PageImpl<ProcessoHabilidade>(processoHabilidadeList,pageable,processoHabilidadeTotal+1);
		
		mv.addObject("processoHabilidadePage", processoHabilidadePage);
		mv.addObject("page",processoHabilidadePage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoHabilidadeSave", method = RequestMethod.POST)
	public ModelAndView processoHabilidadeSave(@Valid ProcessoHabilidadeForm processoHabilidadeForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoHabilidadeAdd(processoHabilidadeForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoHabilidadeHome");

		try {
			processoHabilidadeService.save(processoHabilidadeForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoHabilidadeUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Habilidade já existente no cadastro.");
		        result.addError(error);			
		}
            return processoHabilidadeAdd(processoHabilidadeForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoHabilidadeRelMenu", method = RequestMethod.GET)
	public ModelAndView processoHabilidadeRelMenu() {

		ModelAndView mv = new ModelAndView("processoHabilidade/processoHabilidadeRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/processoHabilidadeRel001")
	public ModelAndView processoHabilidadeRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoHabilidade/processoHabilidadeRel001");
		Pageable processoHabilidadePageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","habilidade.habilidadeNome");
		mv.addObject("processoHabilidadePage", processoHabilidadeService.getProcessoHabilidadeAll(processoHabilidadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoHabilidadeView/{id}", method = RequestMethod.GET)
	public ModelAndView processoHabilidadeView(@PathVariable("id") Long processoHabilidadeId) {

		ProcessoHabilidade processoHabilidade = processoHabilidadeService.getProcessoHabilidadeByProcessoHabilidadePK(processoHabilidadeId);
		ModelAndView mv = new ModelAndView("processoHabilidade/processoHabilidadeView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoHabilidade.getProcesso());
		HabilidadeForm habilidadeForm = habilidadeService.converteHabilidadeView(processoHabilidade.getHabilidade());
		ProcessoHabilidadeForm processoHabilidadeForm = processoHabilidadeService.converteProcessoHabilidadeView(processoHabilidade);
		mv.addObject("processoHabilidadeForm", processoHabilidadeForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("habilidadeForm", habilidadeForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}