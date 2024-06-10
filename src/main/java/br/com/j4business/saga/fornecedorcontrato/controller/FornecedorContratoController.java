package br.com.j4business.saga.fornecedorcontrato.controller;

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
import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.fornecedor.model.FornecedorForm;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContrato;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContratoByFornecedorForm;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContratoForm;
import br.com.j4business.saga.fornecedorcontrato.service.FornecedorContratoService;

@Controller
public class FornecedorContratoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private FornecedorContratoService fornecedorContratoService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/fornecedorContratoAdd")
	public ModelAndView fornecedorContratoAdd(FornecedorContratoForm fornecedorContratoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorContrato/fornecedorContratoAdd");
		fornecedorContratoForm = fornecedorContratoService.fornecedorContratoParametros(fornecedorContratoForm);
		mv.addObject("fornecedorContratoForm", fornecedorContratoForm);
		mv.addObject("fornecedorContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("fornecedorContratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		Pageable fornecedorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/fornecedorContratoCreate")
	public ModelAndView fornecedorContratoCreate(@Valid FornecedorContratoForm fornecedorContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return fornecedorContratoAdd(fornecedorContratoForm,pageable);
		}

		if (fornecedorContratoForm.getFornecedorContratoPK() > 0) {
			return this.fornecedorContratoSave(fornecedorContratoForm, result, attributes,pageable);
			
		}
		
		try {
			fornecedorContratoService.create(fornecedorContratoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("fornecedorContratoUnique")) {
			        ObjectError error = new ObjectError("fornecedorNome","Relacionamento entre Fornecedor e Contrato já existente no cadastro.");
			        result.addError(error);			
			}
            
			return fornecedorContratoAdd(fornecedorContratoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/fornecedorContratoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/fornecedorContratoDelete/{id}")
	public ModelAndView fornecedorContratoDelete(@PathVariable("id") long fornecedorContratoId, @Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/fornecedorContratoHome");
		
		
		FornecedorContrato fornecedorContrato = fornecedorContratoService.getFornecedorContratoByFornecedorContratoPK(fornecedorContratoId);
		try {
			fornecedorContratoService.delete(fornecedorContratoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Fornecedor/Contrato excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Fornecedor/Contrato não excluído. Existe(m) relacionamento(s) de Fornecedor/Contrato ** "+ 
										  fornecedorContrato.getFornecedor().getPessoaNome() +
										  " / " +
										  fornecedorContrato.getContrato().getContratoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/fornecedorContratoEdit/{fornecedorContratoPK}")
	public ModelAndView fornecedorContratoEdit(@PathVariable("fornecedorContratoPK") Long fornecedorContratoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorContrato/fornecedorContratoEdit");
		FornecedorContrato fornecedorContrato = fornecedorContratoService.getFornecedorContratoByFornecedorContratoPK(fornecedorContratoPK);
		FornecedorContratoForm fornecedorContratoForm = fornecedorContratoService.converteFornecedorContrato(fornecedorContrato);
		mv.addObject("fornecedorContratoForm", fornecedorContratoForm);
		mv.addObject("fornecedorContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("fornecedorContratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		Pageable fornecedorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/fornecedorContratoHome")
	public ModelAndView fornecedorContratoHome(@Valid FornecedorContratoByFornecedorForm fornecedorContratoByFornecedorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorContrato/fornecedorContratoHome");
		
		List<FornecedorContrato> fornecedorContratoList = new ArrayList<FornecedorContrato>();
		
		int fornecedorContratoTotal = 0;
		
		if (fornecedorContratoByFornecedorForm.getSearchContratoNome() == null) {
			fornecedorContratoByFornecedorForm.setSearchFornecedorNome("");
			fornecedorContratoByFornecedorForm.setSearchContratoNome("");
			if (fornecedorContratoByFornecedorForm.getFornecedorContratoSortTipo() == null) {
				fornecedorContratoByFornecedorForm.setFornecedorContratoSortTipo("ContratoNome");	
			}
			
		}

		if (fornecedorContratoByFornecedorForm.getFornecedorContratoSortTipo().equalsIgnoreCase("FornecedorNome")
				|| fornecedorContratoByFornecedorForm.getFornecedorContratoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"fornecedor.pessoaNome","contrato.contratoNome"); 
		
		} else if (fornecedorContratoByFornecedorForm.getFornecedorContratoSortTipo().equalsIgnoreCase("ContratoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","fornecedor.pessoaNome"); 

		}

		if ( ! fornecedorContratoByFornecedorForm.getSearchContratoNome().equalsIgnoreCase("")){
			fornecedorContratoList = fornecedorContratoService.getByContratoNome(fornecedorContratoByFornecedorForm.getSearchContratoNome(),pageable);
			fornecedorContratoTotal = fornecedorContratoService.getByContratoNome(fornecedorContratoByFornecedorForm.getSearchContratoNome()).size();
			
		} else {
			fornecedorContratoList = fornecedorContratoService.getByFornecedorNome(fornecedorContratoByFornecedorForm.getSearchFornecedorNome(),pageable);
			fornecedorContratoTotal = fornecedorContratoService.getByFornecedorNome(fornecedorContratoByFornecedorForm.getSearchFornecedorNome()).size();

		} 
		
		Page<FornecedorContrato> fornecedorContratoPage = new PageImpl<FornecedorContrato>(fornecedorContratoList,pageable,fornecedorContratoTotal+1);
		
		mv.addObject("fornecedorContratoPage", fornecedorContratoPage);
		mv.addObject("page",fornecedorContratoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/fornecedorContratoSave")
	public ModelAndView fornecedorContratoSave(@Valid FornecedorContratoForm fornecedorContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return fornecedorContratoAdd(fornecedorContratoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/fornecedorContratoHome");

		try {
			fornecedorContratoService.save(fornecedorContratoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("fornecedorContratoUnique")) {
		        ObjectError error = new ObjectError("fornecedorNome","Relacionamento entre Fornecedor e Contrato já existente no cadastro.");
		        result.addError(error);			
		}
            return fornecedorContratoAdd(fornecedorContratoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/fornecedorContratoRelMenu")
	public ModelAndView fornecedorContratoRelMenu() {

		ModelAndView mv = new ModelAndView("fornecedorContrato/fornecedorContratoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/fornecedorContratoRel001")
	public ModelAndView fornecedorContratoRel001() {

		ModelAndView mv = new ModelAndView("fornecedorContrato/fornecedorContratoRel001");
		Pageable fornecedorContratoPageable = PageRequest.of(0, 200, Direction.ASC,"fornecedor.pessoaNome","contrato.contratoNome");
		mv.addObject("fornecedorContratoPage", fornecedorContratoService.getFornecedorContratoAll(fornecedorContratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/fornecedorContratoView/{id}")
	public ModelAndView fornecedorContratoView(@PathVariable("id") Long fornecedorContratoId) {

		FornecedorContrato fornecedorContrato = fornecedorContratoService.getFornecedorContratoByFornecedorContratoPK(fornecedorContratoId);
		ModelAndView mv = new ModelAndView("fornecedorContrato/fornecedorContratoView");
		FornecedorForm fornecedorForm = fornecedorService.converteFornecedorView(fornecedorContrato.getFornecedor());
		ContratoForm contratoForm = contratoService.converteContratoView(fornecedorContrato.getContrato());
		FornecedorContratoForm fornecedorContratoForm = fornecedorContratoService.converteFornecedorContratoView(fornecedorContrato);
		mv.addObject("fornecedorContratoForm", fornecedorContratoForm);
		mv.addObject("fornecedorForm", fornecedorForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}