package br.com.j4business.saga.processoimagem.controller;

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
import br.com.j4business.saga.imagem.model.ImagemForm;
import br.com.j4business.saga.imagem.service.ImagemService;
import br.com.j4business.saga.processoimagem.model.ProcessoImagem;
import br.com.j4business.saga.processoimagem.model.ProcessoImagemByProcessoForm;
import br.com.j4business.saga.processoimagem.model.ProcessoImagemForm;
import br.com.j4business.saga.processoimagem.service.ProcessoImagemService;

@Controller
public class ProcessoImagemController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ProcessoImagemService processoImagemService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/processoImagemAdd", method = RequestMethod.GET)
	public ModelAndView processoImagemAdd(ProcessoImagemForm processoImagemForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoImagem/processoImagemAdd");
		processoImagemForm = processoImagemService.processoImagemParametros(processoImagemForm);
		mv.addObject("processoImagemForm", processoImagemForm);
		mv.addObject("processoImagemPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoImagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable imagemPageable = new PageRequest(0, 200, Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(imagemPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoImagemCreate", method = RequestMethod.POST)
	public ModelAndView processoImagemCreate(@Valid ProcessoImagemForm processoImagemForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoImagemAdd(processoImagemForm,pageable);
		}

		if (processoImagemForm.getProcessoImagemPK() > 0) {
			return this.processoImagemSave(processoImagemForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (processoImagemForm.getProcessoImagemStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<ProcessoImagem> processoImagemAtivoList = processoImagemService.getProcessoImagemAtivoByProcessoPK(Long.parseLong(processoImagemForm.getProcessoNome()));
				
				if (processoImagemAtivoList.size() > 4) {
					ObjectError error = new ObjectError("processoImagemStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return processoImagemAdd(processoImagemForm,pageable);
				}
				
			}
			
			processoImagemService.create(processoImagemForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoImagemUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Imagem já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoImagemAdd(processoImagemForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoImagemHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/processoImagemDelete/{id}", method = RequestMethod.GET)
	public ModelAndView processoImagemDelete(@PathVariable("id") long processoImagemId, @Valid ImagemForm imagemForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoImagemHome");
		
		
		ProcessoImagem processoImagem = processoImagemService.getProcessoImagemByProcessoImagemPK(processoImagemId);
		try {
			processoImagemService.delete(processoImagemId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Imagem excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Imagem não excluído. Existe(m) relacionamento(s) de Processo/Imagem ** "+ 
										  processoImagem.getProcesso().getProcessoNome() +
										  " / " +
										  processoImagem.getImagem().getImagemNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoImagemEdit/{processoImagemPK}", method = RequestMethod.GET)
	public ModelAndView processoImagemEdit(@PathVariable("processoImagemPK") Long processoImagemPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoImagem/processoImagemEdit");
		ProcessoImagem processoImagem = processoImagemService.getProcessoImagemByProcessoImagemPK(processoImagemPK);
		ProcessoImagemForm processoImagemForm = processoImagemService.converteProcessoImagem(processoImagem);
		mv.addObject("processoImagemForm", processoImagemForm);
		mv.addObject("processoImagemPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoImagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable imagemPageable = new PageRequest(0, 200, Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(imagemPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/processoImagemHome", method = RequestMethod.GET)
	public ModelAndView processoImagemHome(@Valid ProcessoImagemByProcessoForm processoImagemByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoImagem/processoImagemHome");
		
		List<ProcessoImagem> processoImagemList = new ArrayList<ProcessoImagem>();
		
		int processoImagemTotal = 0;
		
		if (processoImagemByProcessoForm.getSearchImagemNome() == null) {
			processoImagemByProcessoForm.setSearchProcessoNome("");
			processoImagemByProcessoForm.setSearchImagemNome("");
			if (processoImagemByProcessoForm.getProcessoImagemSortTipo() == null) {
				processoImagemByProcessoForm.setProcessoImagemSortTipo("ImagemNome");	
			}
			
		}

		if (processoImagemByProcessoForm.getProcessoImagemSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoImagemByProcessoForm.getProcessoImagemSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","imagem.imagemNome"); 
		
		} else if (processoImagemByProcessoForm.getProcessoImagemSortTipo().equalsIgnoreCase("ImagemNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"imagem.imagemNome","processo.processoNome"); 

		}

		if ( ! processoImagemByProcessoForm.getSearchImagemNome().equalsIgnoreCase("")){
			processoImagemList = processoImagemService.getByImagemNome(processoImagemByProcessoForm.getSearchImagemNome(),pageable);
			processoImagemTotal = processoImagemService.getByImagemNome(processoImagemByProcessoForm.getSearchImagemNome()).size();
			
		} else {
			processoImagemList = processoImagemService.getByProcessoNome(processoImagemByProcessoForm.getSearchProcessoNome(),pageable);
			processoImagemTotal = processoImagemService.getByProcessoNome(processoImagemByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoImagem> processoImagemPage = new PageImpl<ProcessoImagem>(processoImagemList,pageable,processoImagemTotal+1);
		
		mv.addObject("processoImagemPage", processoImagemPage);
		mv.addObject("page",processoImagemPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoImagemSave", method = RequestMethod.POST)
	public ModelAndView processoImagemSave(@Valid ProcessoImagemForm processoImagemForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoImagemAdd(processoImagemForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoImagemHome");

		try {
			
			List<ProcessoImagem> processoImagemAtivoList = processoImagemService.getProcessoImagemAtivoByProcessoPK(processoImagemForm.getProcessoPK());
			
			if (processoImagemAtivoList.size() > 5) {
				ObjectError error = new ObjectError("processoImagemStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return processoImagemAdd(processoImagemForm,pageable);
			}
			
			processoImagemService.save(processoImagemForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoImagemUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Imagem já existente no cadastro.");
		        result.addError(error);			
		}
            return processoImagemAdd(processoImagemForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/processoImagemRelMenu", method = RequestMethod.GET)
	public ModelAndView processoImagemRelMenu() {

		ModelAndView mv = new ModelAndView("processoImagem/processoImagemRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/processoImagemRel001")
	public ModelAndView processoImagemRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoImagem/processoImagemRel001");
		Pageable processoImagemPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome","imagem.imagemNome");
		mv.addObject("processoImagemPage", processoImagemService.getProcessoImagemAll(processoImagemPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/processoImagemView/{id}", method = RequestMethod.GET)
	public ModelAndView processoImagemView(@PathVariable("id") Long processoImagemId) {

		ProcessoImagem processoImagem = processoImagemService.getProcessoImagemByProcessoImagemPK(processoImagemId);
		ModelAndView mv = new ModelAndView("processoImagem/processoImagemView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoImagem.getProcesso());
		ImagemForm imagemForm = imagemService.converteImagemView(processoImagem.getImagem());
		ProcessoImagemForm processoImagemForm = processoImagemService.converteProcessoImagemView(processoImagem);
		mv.addObject("processoImagemForm", processoImagemForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("imagemForm", imagemForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}