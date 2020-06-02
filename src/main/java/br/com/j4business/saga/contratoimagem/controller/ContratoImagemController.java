package br.com.j4business.saga.contratoimagem.controller;

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

import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.imagem.model.ImagemForm;
import br.com.j4business.saga.imagem.service.ImagemService;
import br.com.j4business.saga.contratoimagem.model.ContratoImagem;
import br.com.j4business.saga.contratoimagem.model.ContratoImagemByContratoForm;
import br.com.j4business.saga.contratoimagem.model.ContratoImagemForm;
import br.com.j4business.saga.contratoimagem.service.ContratoImagemService;

@Controller
public class ContratoImagemController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ContratoImagemService contratoImagemService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/contratoImagemAdd", method = RequestMethod.GET)
	public ModelAndView contratoImagemAdd(ContratoImagemForm contratoImagemForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoImagem/contratoImagemAdd");
		contratoImagemForm = contratoImagemService.contratoImagemParametros(contratoImagemForm);
		mv.addObject("contratoImagemForm", contratoImagemForm);
		mv.addObject("contratoImagemPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("contratoImagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable imagemPageable = new PageRequest(0, 200, Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(imagemPageable));
		Pageable contratoPageable = new PageRequest(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoImagemCreate", method = RequestMethod.POST)
	public ModelAndView contratoImagemCreate(@Valid ContratoImagemForm contratoImagemForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return contratoImagemAdd(contratoImagemForm,pageable);
		}

		if (contratoImagemForm.getContratoImagemPK() > 0) {
			return this.contratoImagemSave(contratoImagemForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (contratoImagemForm.getContratoImagemStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<ContratoImagem> contratoImagemAtivoList = contratoImagemService.getContratoImagemAtivoByContratoPK(Long.parseLong(contratoImagemForm.getContratoNome()));
				
				if (contratoImagemAtivoList.size() > 4) {
					ObjectError error = new ObjectError("contratoImagemStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return contratoImagemAdd(contratoImagemForm,pageable);
				}
				
			}
			
			contratoImagemService.create(contratoImagemForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoImagemUnique")) {
			        ObjectError error = new ObjectError("contratoNome","Relacionamento entre Contrato e Imagem já existente no cadastro.");
			        result.addError(error);			
			}
            
			return contratoImagemAdd(contratoImagemForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/contratoImagemHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/contratoImagemDelete/{id}", method = RequestMethod.GET)
	public ModelAndView contratoImagemDelete(@PathVariable("id") long contratoImagemId, @Valid ImagemForm imagemForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/contratoImagemHome");
		
		
		ContratoImagem contratoImagem = contratoImagemService.getContratoImagemByContratoImagemPK(contratoImagemId);
		try {
			contratoImagemService.delete(contratoImagemId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Contrato/Imagem excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Contrato/Imagem não excluído. Existe(m) relacionamento(s) de Contrato/Imagem ** "+ 
										  contratoImagem.getContrato().getContratoNome() +
										  " / " +
										  contratoImagem.getImagem().getImagemNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoImagemEdit/{contratoImagemPK}", method = RequestMethod.GET)
	public ModelAndView contratoImagemEdit(@PathVariable("contratoImagemPK") Long contratoImagemPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoImagem/contratoImagemEdit");
		ContratoImagem contratoImagem = contratoImagemService.getContratoImagemByContratoImagemPK(contratoImagemPK);
		ContratoImagemForm contratoImagemForm = contratoImagemService.converteContratoImagem(contratoImagem);
		mv.addObject("contratoImagemForm", contratoImagemForm);
		mv.addObject("contratoImagemPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("contratoImagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable imagemPageable = new PageRequest(0, 200, Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(imagemPageable));
		Pageable contratoPageable = new PageRequest(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/contratoImagemHome", method = RequestMethod.GET)
	public ModelAndView contratoImagemHome(@Valid ContratoImagemByContratoForm contratoImagemByContratoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoImagem/contratoImagemHome");
		
		List<ContratoImagem> contratoImagemList = new ArrayList<ContratoImagem>();
		
		int contratoImagemTotal = 0;
		
		if (contratoImagemByContratoForm.getSearchImagemNome() == null) {
			contratoImagemByContratoForm.setSearchContratoNome("");
			contratoImagemByContratoForm.setSearchImagemNome("");
			if (contratoImagemByContratoForm.getContratoImagemSortTipo() == null) {
				contratoImagemByContratoForm.setContratoImagemSortTipo("ImagemNome");	
			}
			
		}

		if (contratoImagemByContratoForm.getContratoImagemSortTipo().equalsIgnoreCase("ContratoNome")
				|| contratoImagemByContratoForm.getContratoImagemSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","imagem.imagemNome"); 
		
		} else if (contratoImagemByContratoForm.getContratoImagemSortTipo().equalsIgnoreCase("ImagemNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"imagem.imagemNome","contrato.contratoNome"); 

		}

		if ( ! contratoImagemByContratoForm.getSearchImagemNome().equalsIgnoreCase("")){
			contratoImagemList = contratoImagemService.getByImagemNome(contratoImagemByContratoForm.getSearchImagemNome(),pageable);
			contratoImagemTotal = contratoImagemService.getByImagemNome(contratoImagemByContratoForm.getSearchImagemNome()).size();
			
		} else {
			contratoImagemList = contratoImagemService.getByContratoNome(contratoImagemByContratoForm.getSearchContratoNome(),pageable);
			contratoImagemTotal = contratoImagemService.getByContratoNome(contratoImagemByContratoForm.getSearchContratoNome()).size();

		} 
		
		Page<ContratoImagem> contratoImagemPage = new PageImpl<ContratoImagem>(contratoImagemList,pageable,contratoImagemTotal+1);
		
		mv.addObject("contratoImagemPage", contratoImagemPage);
		mv.addObject("page",contratoImagemPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoImagemSave", method = RequestMethod.POST)
	public ModelAndView contratoImagemSave(@Valid ContratoImagemForm contratoImagemForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return contratoImagemAdd(contratoImagemForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/contratoImagemHome");

		try {
			
			List<ContratoImagem> contratoImagemAtivoList = contratoImagemService.getContratoImagemAtivoByContratoPK(contratoImagemForm.getContratoPK());
			
			if (contratoImagemAtivoList.size() > 5) {
				ObjectError error = new ObjectError("contratoImagemStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return contratoImagemAdd(contratoImagemForm,pageable);
			}
			
			contratoImagemService.save(contratoImagemForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoImagemUnique")) {
		        ObjectError error = new ObjectError("contratoNome","Relacionamento entre Contrato e Imagem já existente no cadastro.");
		        result.addError(error);			
		}
            return contratoImagemAdd(contratoImagemForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/contratoImagemRelMenu", method = RequestMethod.GET)
	public ModelAndView contratoImagemRelMenu() {

		ModelAndView mv = new ModelAndView("contratoImagem/contratoImagemRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/contratoImagemRel001")
	public ModelAndView contratoImagemRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoImagem/contratoImagemRel001");
		Pageable contratoImagemPageable = new PageRequest(0, 200, Direction.ASC, "contrato.contratoNome","imagem.imagemNome");
		mv.addObject("contratoImagemPage", contratoImagemService.getContratoImagemAll(contratoImagemPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/contratoImagemView/{id}", method = RequestMethod.GET)
	public ModelAndView contratoImagemView(@PathVariable("id") Long contratoImagemId) {

		ContratoImagem contratoImagem = contratoImagemService.getContratoImagemByContratoImagemPK(contratoImagemId);
		ModelAndView mv = new ModelAndView("contratoImagem/contratoImagemView");
		ContratoForm contratoForm = contratoService.converteContratoView(contratoImagem.getContrato());
		ImagemForm imagemForm = imagemService.converteImagemView(contratoImagem.getImagem());
		ContratoImagemForm contratoImagemForm = contratoImagemService.converteContratoImagemView(contratoImagem);
		mv.addObject("contratoImagemForm", contratoImagemForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("imagemForm", imagemForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}