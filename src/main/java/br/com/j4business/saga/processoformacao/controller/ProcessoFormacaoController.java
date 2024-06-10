package br.com.j4business.saga.processoformacao.controller;

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
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.formacao.model.FormacaoForm;
import br.com.j4business.saga.formacao.service.FormacaoService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacao;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacaoByProcessoForm;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacaoForm;
import br.com.j4business.saga.processoformacao.service.ProcessoFormacaoService;

@Controller
public class ProcessoFormacaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FormacaoService formacaoService;

	@Autowired
	private ProcessoFormacaoService processoFormacaoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/processoFormacaoAdd")
	public ModelAndView processoFormacaoAdd(ProcessoFormacaoForm processoFormacaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoFormacao/processoFormacaoAdd");
		processoFormacaoForm = processoFormacaoService.processoFormacaoParametros(processoFormacaoForm);
		mv.addObject("processoFormacaoForm", processoFormacaoForm);
		mv.addObject("processoFormacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoFormacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable formacaoPageable = PageRequest.of(0, 200, Direction.ASC, "formacaoNome");
		mv.addObject("formacaoPage", formacaoService.getFormacaoAll(formacaoPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/processoFormacaoCreate")
	public ModelAndView processoFormacaoCreate(@Valid ProcessoFormacaoForm processoFormacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoFormacaoAdd(processoFormacaoForm,pageable);
		}

		if (processoFormacaoForm.getProcessoFormacaoPK() > 0) {
			return this.processoFormacaoSave(processoFormacaoForm, result, attributes,pageable);
			
		}
		
		try {
			processoFormacaoService.create(processoFormacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoFormacaoUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Formação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoFormacaoAdd(processoFormacaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoFormacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/processoFormacaoDelete/{id}")
	public ModelAndView processoFormacaoDelete(@PathVariable("id") long processoFormacaoId, @Valid FormacaoForm formacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoFormacaoHome");
		
		
		ProcessoFormacao processoFormacao = processoFormacaoService.getProcessoFormacaoByProcessoFormacaoPK(processoFormacaoId);
		try {
			processoFormacaoService.delete(processoFormacaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Formacao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Formacao não excluído. Existe(m) relacionamento(s) de Processo/Formacao ** "+ 
										  processoFormacao.getProcesso().getProcessoNome() +
										  " / " +
										  processoFormacao.getFormacao().getFormacaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/processoFormacaoEdit/{processoFormacaoPK}")
	public ModelAndView processoFormacaoEdit(@PathVariable("processoFormacaoPK") Long processoFormacaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoFormacao/processoFormacaoEdit");
		ProcessoFormacao processoFormacao = processoFormacaoService.getProcessoFormacaoByProcessoFormacaoPK(processoFormacaoPK);
		ProcessoFormacaoForm processoFormacaoForm = processoFormacaoService.converteProcessoFormacao(processoFormacao);
		mv.addObject("processoFormacaoForm", processoFormacaoForm);
		mv.addObject("processoFormacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoFormacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable formacaoPageable = PageRequest.of(0, 200, Direction.ASC, "formacaoNome");
		mv.addObject("formacaoPage", formacaoService.getFormacaoAll(formacaoPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/processoFormacaoHome")
	public ModelAndView processoFormacaoHome(@Valid ProcessoFormacaoByProcessoForm processoFormacaoByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoFormacao/processoFormacaoHome");
		
		List<ProcessoFormacao> processoFormacaoList = new ArrayList<ProcessoFormacao>();
		
		int processoFormacaoTotal = 0;
		
		if (processoFormacaoByProcessoForm.getSearchFormacaoNome() == null) {
			processoFormacaoByProcessoForm.setSearchProcessoNome("");
			processoFormacaoByProcessoForm.setSearchFormacaoNome("");
			if (processoFormacaoByProcessoForm.getProcessoFormacaoSortTipo() == null) {
				processoFormacaoByProcessoForm.setProcessoFormacaoSortTipo("FormacaoNome");	
			}
			
		}

		if (processoFormacaoByProcessoForm.getProcessoFormacaoSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoFormacaoByProcessoForm.getProcessoFormacaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","formacao.formacaoNome"); 
		
		} else if (processoFormacaoByProcessoForm.getProcessoFormacaoSortTipo().equalsIgnoreCase("FormacaoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"formacao.formacaoNome","processo.processoNome"); 

		}

		if ( ! processoFormacaoByProcessoForm.getSearchFormacaoNome().equalsIgnoreCase("")){
			processoFormacaoList = processoFormacaoService.getByFormacaoNome(processoFormacaoByProcessoForm.getSearchFormacaoNome(),pageable);
			processoFormacaoTotal = processoFormacaoService.getByFormacaoNome(processoFormacaoByProcessoForm.getSearchFormacaoNome()).size();
			
		} else {
			processoFormacaoList = processoFormacaoService.getByProcessoNome(processoFormacaoByProcessoForm.getSearchProcessoNome(),pageable);
			processoFormacaoTotal = processoFormacaoService.getByProcessoNome(processoFormacaoByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoFormacao> processoFormacaoPage = new PageImpl<ProcessoFormacao>(processoFormacaoList,pageable,processoFormacaoTotal+1);
		
		mv.addObject("processoFormacaoPage", processoFormacaoPage);
		mv.addObject("page",processoFormacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/processoFormacaoSave")
	public ModelAndView processoFormacaoSave(@Valid ProcessoFormacaoForm processoFormacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoFormacaoAdd(processoFormacaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoFormacaoHome");

		try {
			processoFormacaoService.save(processoFormacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoFormacaoUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Formação já existente no cadastro.");
		        result.addError(error);			
		}
            return processoFormacaoAdd(processoFormacaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/processoFormacaoRelMenu")
	public ModelAndView processoFormacaoRelMenu() {

		ModelAndView mv = new ModelAndView("processoFormacao/processoFormacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/processoFormacaoRel001")
	public ModelAndView processoFormacaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoFormacao/processoFormacaoRel001");
		Pageable processoFormacaoPageable = PageRequest.of(0, 200, Direction.ASC, "processo.processoNome","formacao.formacaoNome");
		mv.addObject("processoFormacaoPage", processoFormacaoService.getProcessoFormacaoAll(processoFormacaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/processoFormacaoView/{id}")
	public ModelAndView processoFormacaoView(@PathVariable("id") Long processoFormacaoId) {

		ProcessoFormacao processoFormacao = processoFormacaoService.getProcessoFormacaoByProcessoFormacaoPK(processoFormacaoId);
		ModelAndView mv = new ModelAndView("processoFormacao/processoFormacaoView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoFormacao.getProcesso());
		FormacaoForm formacaoForm = formacaoService.converteFormacaoView(processoFormacao.getFormacao());
		ProcessoFormacaoForm processoFormacaoForm = processoFormacaoService.converteProcessoFormacaoView(processoFormacao);
		mv.addObject("processoFormacaoForm", processoFormacaoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("formacaoForm", formacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}