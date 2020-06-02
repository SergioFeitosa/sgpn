package br.com.j4business.saga.processocurso.controller;

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
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.curso.model.CursoForm;
import br.com.j4business.saga.curso.service.CursoService;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;
import br.com.j4business.saga.processocurso.model.ProcessoCursoByProcessoForm;
import br.com.j4business.saga.processocurso.model.ProcessoCursoForm;
import br.com.j4business.saga.processocurso.service.ProcessoCursoService;

@Controller
public class ProcessoCursoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private ProcessoCursoService processoCursoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/processoCursoAdd", method = RequestMethod.GET)
	public ModelAndView processoCursoAdd(ProcessoCursoForm processoCursoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCurso/processoCursoAdd");
		processoCursoForm = processoCursoService.processoCursoParametros(processoCursoForm);
		mv.addObject("processoCursoForm", processoCursoForm);
		mv.addObject("processoCursoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoCursoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cursoPageable = new PageRequest(0, 200, Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(cursoPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoCursoCreate", method = RequestMethod.POST)
	public ModelAndView processoCursoCreate(@Valid ProcessoCursoForm processoCursoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoCursoAdd(processoCursoForm,pageable);
		}

		if (processoCursoForm.getProcessoCursoPK() > 0) {
			return this.processoCursoSave(processoCursoForm, result, attributes,pageable);
			
		}
		
		try {
			processoCursoService.create(processoCursoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoCursoUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Curso já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoCursoAdd(processoCursoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoCursoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/processoCursoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView processoCursoDelete(@PathVariable("id") long processoCursoId, @Valid CursoForm cursoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoCursoHome");
		
		
		ProcessoCurso processoCurso = processoCursoService.getProcessoCursoByProcessoCursoPK(processoCursoId);
		try {
			processoCursoService.delete(processoCursoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Curso excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Curso não excluído. Existe(m) relacionamento(s) de Processo/Curso ** "+ 
										  processoCurso.getProcesso().getProcessoNome() +
										  " / " +
										  processoCurso.getCurso().getCursoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoCursoEdit/{processoCursoPK}", method = RequestMethod.GET)
	public ModelAndView processoCursoEdit(@PathVariable("processoCursoPK") Long processoCursoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCurso/processoCursoEdit");
		ProcessoCurso processoCurso = processoCursoService.getProcessoCursoByProcessoCursoPK(processoCursoPK);
		ProcessoCursoForm processoCursoForm = processoCursoService.converteProcessoCurso(processoCurso);
		mv.addObject("processoCursoForm", processoCursoForm);
		mv.addObject("processoCursoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoCursoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cursoPageable = new PageRequest(0, 200, Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(cursoPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/processoCursoHome", method = RequestMethod.GET)
	public ModelAndView processoCursoHome(@Valid ProcessoCursoByProcessoForm processoCursoByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCurso/processoCursoHome");
		
		List<ProcessoCurso> processoCursoList = new ArrayList<ProcessoCurso>();
		
		int processoCursoTotal = 0;
		
		if (processoCursoByProcessoForm.getSearchCursoNome() == null) {
			processoCursoByProcessoForm.setSearchProcessoNome("");
			processoCursoByProcessoForm.setSearchCursoNome("");
			if (processoCursoByProcessoForm.getProcessoCursoSortTipo() == null) {
				processoCursoByProcessoForm.setProcessoCursoSortTipo("CursoNome");	
			}
			
		}

		if (processoCursoByProcessoForm.getProcessoCursoSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoCursoByProcessoForm.getProcessoCursoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","curso.cursoNome"); 
		
		} else if (processoCursoByProcessoForm.getProcessoCursoSortTipo().equalsIgnoreCase("CursoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"curso.cursoNome","processo.processoNome"); 

		}

		if ( ! processoCursoByProcessoForm.getSearchCursoNome().equalsIgnoreCase("")){
			processoCursoList = processoCursoService.getByCursoNome(processoCursoByProcessoForm.getSearchCursoNome(),pageable);
			processoCursoTotal = processoCursoService.getByCursoNome(processoCursoByProcessoForm.getSearchCursoNome()).size();
			
		} else {
			processoCursoList = processoCursoService.getByProcessoNome(processoCursoByProcessoForm.getSearchProcessoNome(),pageable);
			processoCursoTotal = processoCursoService.getByProcessoNome(processoCursoByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoCurso> processoCursoPage = new PageImpl<ProcessoCurso>(processoCursoList,pageable,processoCursoTotal+1);
		
		mv.addObject("processoCursoPage", processoCursoPage);
		mv.addObject("page",processoCursoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoCursoSave", method = RequestMethod.POST)
	public ModelAndView processoCursoSave(@Valid ProcessoCursoForm processoCursoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoCursoAdd(processoCursoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoCursoHome");

		try {
			processoCursoService.save(processoCursoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoCursoUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Curso já existente no cadastro.");
		        result.addError(error);			
		}
            return processoCursoAdd(processoCursoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoCursoRelMenu", method = RequestMethod.GET)
	public ModelAndView processoCursoRelMenu() {

		ModelAndView mv = new ModelAndView("processoCurso/processoCursoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/processoCursoRel001")
	public ModelAndView processoCursoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCurso/processoCursoRel001");
		Pageable processoCursoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","curso.cursoNome");
		mv.addObject("processoCursoPage", processoCursoService.getProcessoCursoAll(processoCursoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoCursoView/{id}", method = RequestMethod.GET)
	public ModelAndView processoCursoView(@PathVariable("id") Long processoCursoId) {

		ProcessoCurso processoCurso = processoCursoService.getProcessoCursoByProcessoCursoPK(processoCursoId);
		ModelAndView mv = new ModelAndView("processoCurso/processoCursoView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoCurso.getProcesso());
		CursoForm cursoForm = cursoService.converteCursoView(processoCurso.getCurso());
		ProcessoCursoForm processoCursoForm = processoCursoService.converteProcessoCursoView(processoCurso);
		mv.addObject("processoCursoForm", processoCursoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("cursoForm", cursoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}