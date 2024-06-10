package br.com.j4business.saga.contratotexto.controller;

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

import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.texto.model.TextoForm;
import br.com.j4business.saga.texto.service.TextoService;
import br.com.j4business.saga.contratotexto.model.ContratoTexto;
import br.com.j4business.saga.contratotexto.model.ContratoTextoByContratoForm;
import br.com.j4business.saga.contratotexto.model.ContratoTextoForm;
import br.com.j4business.saga.contratotexto.service.ContratoTextoService;

@Controller
public class ContratoTextoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TextoService textoService;

	@Autowired
	private ContratoTextoService contratoTextoService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/contratoTextoAdd")
	public ModelAndView contratoTextoAdd(ContratoTextoForm contratoTextoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoTexto/contratoTextoAdd");
		contratoTextoForm = contratoTextoService.contratoTextoParametros(contratoTextoForm);
		mv.addObject("contratoTextoForm", contratoTextoForm);
		mv.addObject("contratoTextoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("contratoTextoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable textoPageable = PageRequest.of(0, 200, Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(textoPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/contratoTextoCreate")
	public ModelAndView contratoTextoCreate(@Valid ContratoTextoForm contratoTextoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return contratoTextoAdd(contratoTextoForm,pageable);
		}

		if (contratoTextoForm.getContratoTextoPK() > 0) {
			return this.contratoTextoSave(contratoTextoForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (contratoTextoForm.getContratoTextoStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<ContratoTexto> contratoTextoAtivoList = contratoTextoService.getContratoTextoAtivoByContratoPK(Long.parseLong(contratoTextoForm.getContratoNome()));
				
				if (contratoTextoAtivoList.size() > 4) {
					ObjectError error = new ObjectError("contratoTextoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return contratoTextoAdd(contratoTextoForm,pageable);
				}
				
			}
			
			contratoTextoService.create(contratoTextoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoTextoUnique")) {
			        ObjectError error = new ObjectError("contratoNome","Relacionamento entre Contrato e Texto já existente no cadastro.");
			        result.addError(error);			
			}
            
			return contratoTextoAdd(contratoTextoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/contratoTextoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/contratoTextoDelete/{id}")
	public ModelAndView contratoTextoDelete(@PathVariable("id") long contratoTextoId, @Valid TextoForm textoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/contratoTextoHome");
		
		
		ContratoTexto contratoTexto = contratoTextoService.getContratoTextoByContratoTextoPK(contratoTextoId);
		try {
			contratoTextoService.delete(contratoTextoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Contrato/Texto excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Contrato/Texto não excluído. Existe(m) relacionamento(s) de Contrato/Texto ** "+ 
										  contratoTexto.getContrato().getContratoNome() +
										  " / " +
										  contratoTexto.getTexto().getTextoNome() +
										  " ** no cadastro.");
	    }

		return mv;
	}

	@GetMapping(path = "/contratoTextoEdit/{contratoTextoPK}")
	public ModelAndView contratoTextoEdit(@PathVariable("contratoTextoPK") Long contratoTextoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoTexto/contratoTextoEdit");
		ContratoTexto contratoTexto = contratoTextoService.getContratoTextoByContratoTextoPK(contratoTextoPK);
		ContratoTextoForm contratoTextoForm = contratoTextoService.converteContratoTexto(contratoTexto);
		mv.addObject("contratoTextoForm", contratoTextoForm);
		mv.addObject("contratoTextoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("contratoTextoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable textoPageable = PageRequest.of(0, 200, Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(textoPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/contratoTextoHome")
	public ModelAndView contratoTextoHome(@Valid ContratoTextoByContratoForm contratoTextoByContratoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoTexto/contratoTextoHome");
		
		List<ContratoTexto> contratoTextoList = new ArrayList<ContratoTexto>();
		
		int contratoTextoTotal = 0;
		
		if (contratoTextoByContratoForm.getSearchTextoNome() == null) {
			contratoTextoByContratoForm.setSearchContratoNome("");
			contratoTextoByContratoForm.setSearchTextoNome("");
			if (contratoTextoByContratoForm.getContratoTextoSortTipo() == null) {
				contratoTextoByContratoForm.setContratoTextoSortTipo("TextoNome");	
			}
			
		}

		if (contratoTextoByContratoForm.getContratoTextoSortTipo().equalsIgnoreCase("ContratoNome")
				|| contratoTextoByContratoForm.getContratoTextoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","texto.textoNome"); 
		
		} else if (contratoTextoByContratoForm.getContratoTextoSortTipo().equalsIgnoreCase("TextoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"texto.textoNome","contrato.contratoNome"); 

		}

		if ( ! contratoTextoByContratoForm.getSearchTextoNome().equalsIgnoreCase("")){
			contratoTextoList = contratoTextoService.getByTextoNome(contratoTextoByContratoForm.getSearchTextoNome(),pageable);
			contratoTextoTotal = contratoTextoService.getByTextoNome(contratoTextoByContratoForm.getSearchTextoNome()).size();
			
		} else {
			contratoTextoList = contratoTextoService.getByContratoNome(contratoTextoByContratoForm.getSearchContratoNome(),pageable);
			contratoTextoTotal = contratoTextoService.getByContratoNome(contratoTextoByContratoForm.getSearchContratoNome()).size();

		} 
		
		Page<ContratoTexto> contratoTextoPage = new PageImpl<ContratoTexto>(contratoTextoList,pageable,contratoTextoTotal+1);
		
		mv.addObject("contratoTextoPage", contratoTextoPage);
		mv.addObject("page",contratoTextoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/contratoTextoSave")
	public ModelAndView contratoTextoSave(@Valid ContratoTextoForm contratoTextoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return contratoTextoAdd(contratoTextoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/contratoTextoHome");

		try {
			
			List<ContratoTexto> contratoTextoAtivoList = contratoTextoService.getContratoTextoAtivoByContratoPK(contratoTextoForm.getContratoPK());
			
			if (contratoTextoAtivoList.size() > 5) {
				ObjectError error = new ObjectError("contratoTextoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return contratoTextoAdd(contratoTextoForm,pageable);
			}
			
			contratoTextoService.save(contratoTextoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoTextoUnique")) {
		        ObjectError error = new ObjectError("contratoNome","Relacionamento entre Contrato e Texto já existente no cadastro.");
		        result.addError(error);			
		}
            return contratoTextoAdd(contratoTextoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/contratoTextoRelMenu")
	public ModelAndView contratoTextoRelMenu() {

		ModelAndView mv = new ModelAndView("contratoTexto/contratoTextoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/contratoTextoRel001")
	public ModelAndView contratoTextoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoTexto/contratoTextoRel001");
		Pageable contratoTextoPageable = PageRequest.of(0, 200, Direction.ASC, "contrato.contratoNome","texto.textoNome");
		mv.addObject("contratoTextoPage", contratoTextoService.getContratoTextoAll(contratoTextoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/contratoTextoView/{id}")
	public ModelAndView contratoTextoView(@PathVariable("id") Long contratoTextoId) {

		ContratoTexto contratoTexto = contratoTextoService.getContratoTextoByContratoTextoPK(contratoTextoId);
		ModelAndView mv = new ModelAndView("contratoTexto/contratoTextoView");
		ContratoForm contratoForm = contratoService.converteContratoView(contratoTexto.getContrato());
		TextoForm textoForm = textoService.converteTextoView(contratoTexto.getTexto());
		ContratoTextoForm contratoTextoForm = contratoTextoService.converteContratoTextoView(contratoTexto);
		mv.addObject("contratoTextoForm", contratoTextoForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("textoForm", textoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}