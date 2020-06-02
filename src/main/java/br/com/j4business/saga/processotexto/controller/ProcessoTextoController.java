package br.com.j4business.saga.processotexto.controller;

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
import br.com.j4business.saga.texto.model.TextoForm;
import br.com.j4business.saga.texto.service.TextoService;
import br.com.j4business.saga.processotexto.model.ProcessoTexto;
import br.com.j4business.saga.processotexto.model.ProcessoTextoByProcessoForm;
import br.com.j4business.saga.processotexto.model.ProcessoTextoForm;
import br.com.j4business.saga.processotexto.service.ProcessoTextoService;

@Controller
public class ProcessoTextoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TextoService textoService;

	@Autowired
	private ProcessoTextoService processoTextoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/processoTextoAdd", method = RequestMethod.GET)
	public ModelAndView processoTextoAdd(ProcessoTextoForm processoTextoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoTexto/processoTextoAdd");
		processoTextoForm = processoTextoService.processoTextoParametros(processoTextoForm);
		mv.addObject("processoTextoForm", processoTextoForm);
		mv.addObject("processoTextoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoTextoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable textoPageable = new PageRequest(0, 200, Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(textoPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoTextoCreate", method = RequestMethod.POST)
	public ModelAndView processoTextoCreate(@Valid ProcessoTextoForm processoTextoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoTextoAdd(processoTextoForm,pageable);
		}

		if (processoTextoForm.getProcessoTextoPK() > 0) {
			return this.processoTextoSave(processoTextoForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (processoTextoForm.getProcessoTextoStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<ProcessoTexto> processoTextoAtivoList = processoTextoService.getProcessoTextoAtivoByProcessoPK(Long.parseLong(processoTextoForm.getProcessoNome()));
				
				if (processoTextoAtivoList.size() > 4) {
					ObjectError error = new ObjectError("processoTextoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return processoTextoAdd(processoTextoForm,pageable);
				}
				
			}
			
			processoTextoService.create(processoTextoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoTextoUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Texto já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoTextoAdd(processoTextoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoTextoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/processoTextoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView processoTextoDelete(@PathVariable("id") long processoTextoId, @Valid TextoForm textoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoTextoHome");
		
		
		ProcessoTexto processoTexto = processoTextoService.getProcessoTextoByProcessoTextoPK(processoTextoId);
		try {
			processoTextoService.delete(processoTextoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Texto excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Texto não excluído. Existe(m) relacionamento(s) de Processo/Texto ** "+ 
										  processoTexto.getProcesso().getProcessoNome() +
										  " / " +
										  processoTexto.getTexto().getTextoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoTextoEdit/{processoTextoPK}", method = RequestMethod.GET)
	public ModelAndView processoTextoEdit(@PathVariable("processoTextoPK") Long processoTextoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoTexto/processoTextoEdit");
		ProcessoTexto processoTexto = processoTextoService.getProcessoTextoByProcessoTextoPK(processoTextoPK);
		ProcessoTextoForm processoTextoForm = processoTextoService.converteProcessoTexto(processoTexto);
		mv.addObject("processoTextoForm", processoTextoForm);
		mv.addObject("processoTextoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoTextoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable textoPageable = new PageRequest(0, 200, Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(textoPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/processoTextoHome", method = RequestMethod.GET)
	public ModelAndView processoTextoHome(@Valid ProcessoTextoByProcessoForm processoTextoByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoTexto/processoTextoHome");
		
		List<ProcessoTexto> processoTextoList = new ArrayList<ProcessoTexto>();
		
		int processoTextoTotal = 0;
		
		if (processoTextoByProcessoForm.getSearchTextoNome() == null) {
			processoTextoByProcessoForm.setSearchProcessoNome("");
			processoTextoByProcessoForm.setSearchTextoNome("");
			if (processoTextoByProcessoForm.getProcessoTextoSortTipo() == null) {
				processoTextoByProcessoForm.setProcessoTextoSortTipo("TextoNome");	
			}
			
		}

		if (processoTextoByProcessoForm.getProcessoTextoSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoTextoByProcessoForm.getProcessoTextoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","texto.textoNome"); 
		
		} else if (processoTextoByProcessoForm.getProcessoTextoSortTipo().equalsIgnoreCase("TextoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"texto.textoNome","processo.processoNome"); 

		}

		if ( ! processoTextoByProcessoForm.getSearchTextoNome().equalsIgnoreCase("")){
			processoTextoList = processoTextoService.getByTextoNome(processoTextoByProcessoForm.getSearchTextoNome(),pageable);
			processoTextoTotal = processoTextoService.getByTextoNome(processoTextoByProcessoForm.getSearchTextoNome()).size();
			
		} else {
			processoTextoList = processoTextoService.getByProcessoNome(processoTextoByProcessoForm.getSearchProcessoNome(),pageable);
			processoTextoTotal = processoTextoService.getByProcessoNome(processoTextoByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoTexto> processoTextoPage = new PageImpl<ProcessoTexto>(processoTextoList,pageable,processoTextoTotal+1);
		
		mv.addObject("processoTextoPage", processoTextoPage);
		mv.addObject("page",processoTextoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoTextoSave", method = RequestMethod.POST)
	public ModelAndView processoTextoSave(@Valid ProcessoTextoForm processoTextoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoTextoAdd(processoTextoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoTextoHome");

		try {
			
			List<ProcessoTexto> processoTextoAtivoList = processoTextoService.getProcessoTextoAtivoByProcessoPK(processoTextoForm.getProcessoPK());
			
			if (processoTextoAtivoList.size() > 5) {
				ObjectError error = new ObjectError("processoTextoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return processoTextoAdd(processoTextoForm,pageable);
			}
			
			processoTextoService.save(processoTextoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoTextoUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Texto já existente no cadastro.");
		        result.addError(error);			
		}
            return processoTextoAdd(processoTextoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoTextoRelMenu", method = RequestMethod.GET)
	public ModelAndView processoTextoRelMenu() {

		ModelAndView mv = new ModelAndView("processoTexto/processoTextoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/processoTextoRel001")
	public ModelAndView processoTextoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoTexto/processoTextoRel001");
		Pageable processoTextoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","texto.textoNome");
		mv.addObject("processoTextoPage", processoTextoService.getProcessoTextoAll(processoTextoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoTextoView/{id}", method = RequestMethod.GET)
	public ModelAndView processoTextoView(@PathVariable("id") Long processoTextoId) {

		ProcessoTexto processoTexto = processoTextoService.getProcessoTextoByProcessoTextoPK(processoTextoId);
		ModelAndView mv = new ModelAndView("processoTexto/processoTextoView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoTexto.getProcesso());
		TextoForm textoForm = textoService.converteTextoView(processoTexto.getTexto());
		ProcessoTextoForm processoTextoForm = processoTextoService.converteProcessoTextoView(processoTexto);
		mv.addObject("processoTextoForm", processoTextoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("textoForm", textoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}