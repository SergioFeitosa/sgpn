package br.com.j4business.saga.processo.controller;

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
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.model.ProcessoByProcessoForm;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processoatividade.service.ProcessoAtividadeService;
import br.com.j4business.saga.processocertificacao.service.ProcessoCertificacaoService;
import br.com.j4business.saga.processocurso.service.ProcessoCursoService;
import br.com.j4business.saga.processoformacao.service.ProcessoFormacaoService;
import br.com.j4business.saga.processohabilidade.service.ProcessoHabilidadeService;
import br.com.j4business.saga.processoimagem.service.ProcessoImagemService;
import br.com.j4business.saga.processotexto.service.ProcessoTextoService;
import br.com.j4business.saga.processovideo.service.ProcessoVideoService;

@Controller
public class ProcessoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoAtividadeService processoAtividadeService;

	@Autowired
	private ProcessoCertificacaoService processoCertificacaoService;

	@Autowired
	private ProcessoCursoService processoCursoService;

	@Autowired
	private ProcessoFormacaoService processoFormacaoService;

	@Autowired
	private ProcessoHabilidadeService processoHabilidadeService;

	@Autowired
	private ProcessoImagemService processoImagemService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private ProcessoTextoService processoTextoService;

	@Autowired
	private ProcessoVideoService processoVideoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/processoAdd", method = RequestMethod.GET)
	public ModelAndView processoAdd(ProcessoForm processoForm) {

		ModelAndView mv = new ModelAndView("processo/processoAdd");
		processoForm = processoService.processoParametros(processoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("processoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoCreate", method = RequestMethod.POST)
	public ModelAndView processoCreate(@Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return processoAdd(processoForm);
		}

		if (processoForm.getProcessoPK() > 0) {
			return this.processoSave(processoForm, result, attributes);
			
		}
		
		try {
			processoService.create(processoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoNome")) {
			        ObjectError error = new ObjectError("processoNome","Nome do Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoAdd(processoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView processoDelete(@PathVariable("id") long processoPK, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoHome");

		Processo processo = processoService.getProcessoByProcessoPK(processoPK);
		try {
			processoService.delete(processoPK);

			attributes.addFlashAttribute("mensagem", "Processo excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Processo não excluída. Existe(m) relacionamento(s) de Processo ** "+ processo.getProcessoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView processoEdit(@PathVariable("id") Long processoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processo/processoEdit");
		Processo processo = processoService.getProcessoByProcessoPK(processoId);
		ProcessoForm processoForm = processoService.converteProcesso(processo);
		mv.addObject("processoForm", processoForm);
		mv.addObject("processoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoAtividadePageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","processoAtividadeSequencia");
		mv.addObject("processoAtividadePage", processoAtividadeService.getByProcessoPK(processo.getProcessoPK(),processoAtividadePageable));
		Pageable processoCertificacaoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","certificacao.certificacaoNome");
		mv.addObject("processoCertificacaoPage", processoCertificacaoService.getByProcessoPK(processo.getProcessoPK(),processoCertificacaoPageable));
		Pageable processoCursoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","curso.cursoNome");
		mv.addObject("processoCursoPage", processoCursoService.getByProcessoPK(processo.getProcessoPK(),processoCursoPageable));
		Pageable processoFormacaoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","formacao.formacaoNome");
		mv.addObject("processoFormacaoPage", processoFormacaoService.getByProcessoPK(processo.getProcessoPK(),processoFormacaoPageable));
		Pageable processoHabilidadePageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","habilidade.habilidadeNome");
		mv.addObject("processoHabilidadePage", processoHabilidadeService.getByProcessoPK(processo.getProcessoPK(),processoHabilidadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		mv.addObject("processoImagemList", processoImagemService.getProcessoImagemAtivoByProcessoPK(processoId));
		mv.addObject("processoTextoList", processoTextoService.getProcessoTextoAtivoByProcessoPK(processoId));
		mv.addObject("processoVideoList", processoVideoService.getProcessoVideoAtivoByProcessoPK(processoId));
		
		return mv;
	}

	@RequestMapping(path = "/processoHome", method = RequestMethod.GET)
	public ModelAndView processoHome(@Valid ProcessoByProcessoForm processoByProcessoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processo/processoHome");

		List<Processo> processoList = new ArrayList<Processo>();

		int processosTotal = 0;
		
		if (processoByProcessoForm.getSearchProcessoNome() == null) {
			processoByProcessoForm.setSearchProcessoNome("");
			processoByProcessoForm.setSearchProcessoDescricao("");
			if (processoByProcessoForm.getProcessoSortTipo() == null) {
				processoByProcessoForm.setProcessoSortTipo("ProcessoNome");
			}

		}

		if (processoByProcessoForm.getProcessoSortTipo().equalsIgnoreCase("ProcessoNome") || processoByProcessoForm.getProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "processoNome");

		} else if (processoByProcessoForm.getProcessoSortTipo().equalsIgnoreCase("ProcessoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "processoDescricao");

		}

		if ((!processoByProcessoForm.getSearchProcessoNome().equalsIgnoreCase(""))) {
			processoList = processoService.getByProcessoNome(processoByProcessoForm.getSearchProcessoNome(), pageable);
			processosTotal = processoService.getByProcessoNome(processoByProcessoForm.getSearchProcessoNome()).size();

		} else {
			processoList = processoService.getByProcessoDescricao(processoByProcessoForm.getSearchProcessoDescricao(), pageable);
			processosTotal = processoService.getByProcessoDescricao(processoByProcessoForm.getSearchProcessoDescricao()).size();
		}

		Page<Processo> processoPage = new PageImpl<Processo>(processoList, pageable, processosTotal+1);

		mv.addObject("processoPage", processoPage);
		mv.addObject("page", processoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoSave", method = RequestMethod.POST)
	public ModelAndView processoSave(@Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return processoAdd(processoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoHome");

		try {
			processoService.save(processoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoNome")) {
			        ObjectError error = new ObjectError("processoNome","Nome do Processo já existente no cadastro.");
			        result.addError(error);			
			}
            return processoAdd(processoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoRelMenu", method = RequestMethod.GET)
	public ModelAndView processoRelMenu() {

		ModelAndView mv = new ModelAndView("processo/processoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoRel001", method = RequestMethod.GET)
	public ModelAndView processoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processo/processoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoView/{id}", method = RequestMethod.GET)
	public ModelAndView processoView(@PathVariable("id") Long processoId) {

		Processo processo = processoService.getProcessoByProcessoPK(processoId);
		ModelAndView mv = new ModelAndView("processo/processoView");
		ProcessoForm processoForm = processoService.converteProcessoView(processo);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;

	}


}