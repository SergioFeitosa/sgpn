package br.com.j4business.saga.fornecedor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
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
import br.com.j4business.saga.fornecedor.enumeration.FornecedorRamo;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.model.FornecedorByFornecedorForm;
import br.com.j4business.saga.fornecedor.model.FornecedorForm;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.fornecedorcontrato.service.FornecedorContratoService;
import br.com.j4business.saga.fornecedorprocesso.service.FornecedorProcessoService;

@Controller
public class FornecedorController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private FornecedorContratoService fornecedorContratoService;
	
	@Autowired
	private FornecedorProcessoService fornecedorProcessoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/fornecedorAdd", method = RequestMethod.GET)
	public ModelAndView fornecedorAdd(FornecedorForm fornecedorForm, Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedor/fornecedorAdd");
		fornecedorForm = fornecedorService.fornecedorParametros(fornecedorForm);
		mv.addObject("fornecedorForm", fornecedorForm);
		mv.addObject("fornecedorRamoValues", listarFornecedorRamos());
		mv.addObject("fornecedorStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/fornecedorCreate", method = RequestMethod.POST)
	public ModelAndView fornecedorCreate(@Valid FornecedorForm fornecedorForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		if (result.hasErrors()) {
			return fornecedorAdd(fornecedorForm,pageable);
		}

		if (fornecedorForm.getFornecedorPK() > 0) {
			return this.fornecedorSave(fornecedorForm, result, attributes,pageable);
			
		}
		
		try {
			fornecedorService.create(fornecedorForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cnpj")) {
			        ObjectError error = new ObjectError("fornecedorCNPJ","CNPJ do Fornecedor já existente no cadastro.");
			        result.addError(error);			
			}
            
			return fornecedorAdd(fornecedorForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/fornecedorHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/fornecedorDelete/{id}", method = RequestMethod.GET)
	public ModelAndView fornecedorDelete(@PathVariable("id") long fornecedorPK, @Valid FornecedorForm fornecedorForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/fornecedorHome");

		Fornecedor fornecedor = fornecedorService.getFornecedorByFornecedorPK(fornecedorPK);
		try {
			fornecedorService.delete(fornecedorPK);

			attributes.addFlashAttribute("mensagem", "Fornecedor excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Fornecedor não excluída. Existe(m) relacionamento(s) de Fornecedor ** "+ fornecedor.getPessoaNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/fornecedorEdit/{id}", method = RequestMethod.GET)
	public ModelAndView fornecedorEdit(@PathVariable("id") Long fornecedorPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedor/fornecedorEdit");
		Fornecedor fornecedor = fornecedorService.getFornecedorByFornecedorPK(fornecedorPK);
		FornecedorForm fornecedorForm = fornecedorService.converteFornecedor(fornecedor);

		mv.addObject("fornecedorForm", fornecedorForm);
		mv.addObject("fornecedorRamoValues", listarFornecedorRamos());
		mv.addObject("fornecedorStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorProcessoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("fornecedorProcessoPage", fornecedorProcessoService.getByFornecedorPK(fornecedorPK, fornecedorProcessoPageable));
		Pageable fornecedorContratoPageable = new PageRequest(0, 200, Direction.ASC, "contrato.contratoNome");
		mv.addObject("fornecedorContratoPage", fornecedorContratoService.getByFornecedorPK(fornecedorPK, fornecedorContratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/fornecedorHome")
	public ModelAndView fornecedorHome(@Valid FornecedorByFornecedorForm fornecedorByFornecedorForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedor/fornecedorHome");
		List<Fornecedor> fornecedorList = new ArrayList<Fornecedor>();

		int fornecedorsTotal = 0;
		
		if (fornecedorByFornecedorForm.getSearchFornecedorNome() == null) {
			fornecedorByFornecedorForm.setSearchFornecedorNome("");
			fornecedorByFornecedorForm.setSearchFornecedorNomeFantasia("");
			if (fornecedorByFornecedorForm.getFornecedorSortTipo() == null) {
				fornecedorByFornecedorForm.setFornecedorSortTipo("FornecedorNome");
			}

		}

		if (fornecedorByFornecedorForm.getFornecedorSortTipo().equalsIgnoreCase("FornecedorNome") || fornecedorByFornecedorForm.getFornecedorSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "pessoaNome");

		} else if (fornecedorByFornecedorForm.getFornecedorSortTipo().equalsIgnoreCase("FornecedorNomeFantasia")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "fornecedorNomeFantasia");

		}

		if ((!fornecedorByFornecedorForm.getSearchFornecedorNome().equalsIgnoreCase(""))) {
			fornecedorList = fornecedorService.getByFornecedorNome(fornecedorByFornecedorForm.getSearchFornecedorNome(), pageable);
			fornecedorsTotal = fornecedorService.getByFornecedorNome(fornecedorByFornecedorForm.getSearchFornecedorNome()).size();

		} else {
			fornecedorList = fornecedorService.getByFornecedorNomeFantasia(fornecedorByFornecedorForm.getSearchFornecedorNomeFantasia(), pageable);
			fornecedorsTotal = fornecedorService.getByFornecedorNomeFantasia(fornecedorByFornecedorForm.getSearchFornecedorNomeFantasia()).size();
		}

		Page<Fornecedor> fornecedorPage = new PageImpl<Fornecedor>(fornecedorList, pageable, fornecedorsTotal+1);

		mv.addObject("fornecedorPage", fornecedorPage);
		mv.addObject("page", fornecedorPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/fornecedorRelMenu", method = RequestMethod.GET)
	public ModelAndView fornecedorRelMenu() {

		ModelAndView mv = new ModelAndView("fornecedor/fornecedorRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/fornecedorRel001")
	public ModelAndView fornecedorRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedor/fornecedorRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/fornecedorSave", method = RequestMethod.POST)
	public ModelAndView fornecedorSave(@Valid FornecedorForm fornecedorForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		if (result.hasErrors()) {
			return fornecedorAdd(fornecedorForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/fornecedorHome");

		try {
			fornecedorService.save(fornecedorForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cnpj")) {
			        ObjectError error = new ObjectError("fornecedorCNPJ","CNPJ do Fornecedor já existente no cadastro.");
			        result.addError(error);			
			}
            return fornecedorAdd(fornecedorForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/fornecedorView/{id}", method = RequestMethod.GET)
	public ModelAndView fornecedorView(@PathVariable("id") Long fornecedorId) {

		Fornecedor fornecedor = fornecedorService.getFornecedorByFornecedorPK(fornecedorId);
		ModelAndView mv = new ModelAndView("fornecedor/fornecedorView");
		FornecedorForm fornecedorForm = fornecedorService.converteFornecedorView(fornecedor);
		mv.addObject("fornecedorForm", fornecedorForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	public EnumSet<FornecedorRamo> listarFornecedorRamos() {
	    return EnumSet.allOf(FornecedorRamo.class);
	}

	
}