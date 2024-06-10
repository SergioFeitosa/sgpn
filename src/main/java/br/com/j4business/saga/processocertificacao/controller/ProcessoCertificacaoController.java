package br.com.j4business.saga.processocertificacao.controller;

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

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.certificacao.model.CertificacaoForm;
import br.com.j4business.saga.certificacao.service.CertificacaoService;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacaoByProcessoForm;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacaoForm;
import br.com.j4business.saga.processocertificacao.service.ProcessoCertificacaoService;

@Controller
public class ProcessoCertificacaoController {

	@Autowired
	private CertificacaoService certificacaoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoCertificacaoService processoCertificacaoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/processoCertificacaoAdd")
	public ModelAndView processoCertificacaoAdd(ProcessoCertificacaoForm processoCertificacaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCertificacao/processoCertificacaoAdd");
		processoCertificacaoForm = processoCertificacaoService.processoCertificacaoParametros(processoCertificacaoForm);
		mv.addObject("processoCertificacaoForm", processoCertificacaoForm);
		mv.addObject("processoCertificacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoCertificacaoStatusValues", AtributoStatus.values());
		Pageable certificacaoPageable = PageRequest.of(0, 200, Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(certificacaoPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/processoCertificacaoCreate")
	public ModelAndView processoCertificacaoCreate(@Valid ProcessoCertificacaoForm processoCertificacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoCertificacaoAdd(processoCertificacaoForm,pageable);
		}

		if (processoCertificacaoForm.getProcessoCertificacaoPK() > 0) {
			return this.processoCertificacaoSave(processoCertificacaoForm, result, attributes,pageable);
			
		}
		
		try {
			processoCertificacaoService.create(processoCertificacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoCertificacaoUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Certificação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoCertificacaoAdd(processoCertificacaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoCertificacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/processoCertificacaoDelete/{id}")
	public ModelAndView processoCertificacaoDelete(@PathVariable("id") long processoCertificacaoId, @Valid CertificacaoForm certificacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoCertificacaoHome");
		
		
		ProcessoCertificacao processoCertificacao = processoCertificacaoService.getProcessoCertificacaoByProcessoCertificacaoPK(processoCertificacaoId);
		try {
			processoCertificacaoService.delete(processoCertificacaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Certificacao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Certificacao não excluído. Existe(m) relacionamento(s) de Processo/Certificacao ** "+ 
										  processoCertificacao.getProcesso().getProcessoNome() +
										  " / " +
										  processoCertificacao.getCertificacao().getCertificacaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/processoCertificacaoEdit/{processoCertificacaoPK}")
	public ModelAndView processoCertificacaoEdit(@PathVariable("processoCertificacaoPK") Long processoCertificacaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCertificacao/processoCertificacaoEdit");
		ProcessoCertificacao processoCertificacao = processoCertificacaoService.getProcessoCertificacaoByProcessoCertificacaoPK(processoCertificacaoPK);
		ProcessoCertificacaoForm processoCertificacaoForm = processoCertificacaoService.converteProcessoCertificacao(processoCertificacao);
		mv.addObject("processoCertificacaoForm", processoCertificacaoForm);
		mv.addObject("processoCertificacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoCertificacaoStatusValues", AtributoStatus.values());
		Pageable certificacaoPageable = PageRequest.of(0, 200, Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(certificacaoPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/processoCertificacaoHome")
	public ModelAndView processoCertificacaoHome(@Valid ProcessoCertificacaoByProcessoForm processoCertificacaoByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCertificacao/processoCertificacaoHome");
		
		List<ProcessoCertificacao> processoCertificacaoList = new ArrayList<ProcessoCertificacao>();
		
		int processoCertificacaoTotal = 0;
		
		if (processoCertificacaoByProcessoForm.getSearchCertificacaoNome() == null) {
			processoCertificacaoByProcessoForm.setSearchProcessoNome("");
			processoCertificacaoByProcessoForm.setSearchCertificacaoNome("");
			if (processoCertificacaoByProcessoForm.getProcessoCertificacaoSortTipo() == null) {
				processoCertificacaoByProcessoForm.setProcessoCertificacaoSortTipo("CertificacaoNome");	
			}
			
		}

		if (processoCertificacaoByProcessoForm.getProcessoCertificacaoSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoCertificacaoByProcessoForm.getProcessoCertificacaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","certificacao.certificacaoNome"); 
		
		} else if (processoCertificacaoByProcessoForm.getProcessoCertificacaoSortTipo().equalsIgnoreCase("CertificacaoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"certificacao.certificacaoNome","processo.processoNome"); 

		}

		if ( ! processoCertificacaoByProcessoForm.getSearchCertificacaoNome().equalsIgnoreCase("")){
			processoCertificacaoList = processoCertificacaoService.getByCertificacaoNome(processoCertificacaoByProcessoForm.getSearchCertificacaoNome(),pageable);
			processoCertificacaoTotal = processoCertificacaoService.getByCertificacaoNome(processoCertificacaoByProcessoForm.getSearchCertificacaoNome()).size();
			
		} else {
			processoCertificacaoList = processoCertificacaoService.getByProcessoNome(processoCertificacaoByProcessoForm.getSearchProcessoNome(),pageable);
			processoCertificacaoTotal = processoCertificacaoService.getByProcessoNome(processoCertificacaoByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoCertificacao> processoCertificacaoPage = new PageImpl<ProcessoCertificacao>(processoCertificacaoList,pageable,processoCertificacaoTotal+1);
		
		mv.addObject("processoCertificacaoPage", processoCertificacaoPage);
		mv.addObject("page",processoCertificacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/processoCertificacaoSave")
	public ModelAndView processoCertificacaoSave(@Valid ProcessoCertificacaoForm processoCertificacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoCertificacaoAdd(processoCertificacaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoCertificacaoHome");

		try {
			processoCertificacaoService.save(processoCertificacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoCertificacaoUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Certificação já existente no cadastro.");
		        result.addError(error);			
		}
            return processoCertificacaoAdd(processoCertificacaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/processoCertificacaoRelMenu")
	public ModelAndView processoCertificacaoRelMenu() {

		ModelAndView mv = new ModelAndView("processoCertificacao/processoCertificacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/processoCertificacaoRel001")
	public ModelAndView processoCertificacaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoCertificacao/processoCertificacaoRel001");
		Pageable processoCertificacaoPageable = PageRequest.of(0, 200, Direction.ASC, "processo.processoNome", "certificacao.certificacaoNome");
		mv.addObject("processoCertificacaoPage", processoCertificacaoService.getProcessoCertificacaoAll(processoCertificacaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/processoCertificacaoView/{id}")
	public ModelAndView processoCertificacaoView(@PathVariable("id") Long processoCertificacaoId) {

		ProcessoCertificacao processoCertificacao = processoCertificacaoService.getProcessoCertificacaoByProcessoCertificacaoPK(processoCertificacaoId);
		ModelAndView mv = new ModelAndView("processoCertificacao/processoCertificacaoView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoCertificacao.getProcesso());
		CertificacaoForm certificacaoForm = certificacaoService.converteCertificacaoView(processoCertificacao.getCertificacao());
		ProcessoCertificacaoForm processoCertificacaoForm = processoCertificacaoService.converteProcessoCertificacaoView(processoCertificacao);
		mv.addObject("processoCertificacaoForm", processoCertificacaoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("certificacaoForm", certificacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}