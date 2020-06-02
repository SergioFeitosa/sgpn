package br.com.j4business.saga.empresa.controller;

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
import br.com.j4business.saga.empresa.enumeration.EmpresaRamo;
import br.com.j4business.saga.empresa.model.Empresa;
import br.com.j4business.saga.empresa.model.EmpresaByEmpresaForm;
import br.com.j4business.saga.empresa.model.EmpresaForm;
import br.com.j4business.saga.empresa.service.EmpresaService;
import br.com.j4business.saga.empresaprocesso.service.EmpresaProcessoService;

@Controller
public class EmpresaController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EmpresaProcessoService empresaProcessoService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/empresaAdd", method = RequestMethod.GET)
	public ModelAndView empresaAdd(EmpresaForm empresaForm, Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresa/empresaAdd");
		empresaForm = empresaService.empresaParametros(empresaForm);
		mv.addObject("empresaForm", empresaForm);
		mv.addObject("empresaRamoValues", listarEmpresaRamos());
		mv.addObject("empresaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/empresaCreate", method = RequestMethod.POST)
	public ModelAndView empresaCreate(@Valid EmpresaForm empresaForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		if (result.hasErrors()) {
			return empresaAdd(empresaForm,pageable);
		}

		if (empresaForm.getEmpresaPK() > 0) {
			return this.empresaSave(empresaForm, result, attributes,pageable);
			
		}
		
		try {
			empresaService.create(empresaForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cnpj")) {
			        ObjectError error = new ObjectError("empresaCNPJ","CNPJ da Empresa já existente no cadastro.");
			        result.addError(error);			
			}
            
			return empresaAdd(empresaForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/empresaHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/empresaDelete/{id}", method = RequestMethod.GET)
	public ModelAndView empresaDelete(@PathVariable("id") long empresaPK, @Valid EmpresaForm empresaForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/empresaHome");

		Empresa empresa = empresaService.getEmpresaByEmpresaPK(empresaPK);
		try {
			empresaService.delete(empresaPK);

			attributes.addFlashAttribute("mensagem", "Empresa excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Empresa não excluída. Existe(m) relacionamento(s) de Empresa ** "+ empresa.getPessoaNome() + " ** no cadastro.");
	    }
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/empresaEdit/{id}", method = RequestMethod.GET)
	public ModelAndView empresaEdit(@PathVariable("id") Long empresaPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresa/empresaEdit");
		Empresa empresa = empresaService.getEmpresaByEmpresaPK(empresaPK);
		EmpresaForm empresaForm = empresaService.converteEmpresa(empresa);

		mv.addObject("empresaForm", empresaForm);
		mv.addObject("empresaRamoValues", listarEmpresaRamos());
		mv.addObject("empresaStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable empresaProcessoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("empresaProcessoPage", empresaProcessoService.getByEmpresaPK(empresaPK, empresaProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/empresaHome")
	public ModelAndView empresaHome(@Valid EmpresaByEmpresaForm empresaByEmpresaForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresa/empresaHome");
		List<Empresa> empresaList = new ArrayList<Empresa>();

		int empresasTotal = 0;
		
		if (empresaByEmpresaForm.getSearchEmpresaNome() == null) {
			empresaByEmpresaForm.setSearchEmpresaNome("");
			empresaByEmpresaForm.setSearchEmpresaNomeFantasia("");
			if (empresaByEmpresaForm.getEmpresaSortTipo() == null) {
				empresaByEmpresaForm.setEmpresaSortTipo("EmpresaNome");
			}

		}

		if (empresaByEmpresaForm.getEmpresaSortTipo().equalsIgnoreCase("EmpresaNome") || empresaByEmpresaForm.getEmpresaSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "pessoaNome");

		} else if (empresaByEmpresaForm.getEmpresaSortTipo().equalsIgnoreCase("EmpresaNomeFantasia")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "empresaNomeFantasia");

		}

		if ((!empresaByEmpresaForm.getSearchEmpresaNome().equalsIgnoreCase(""))) {
			empresaList = empresaService.getByEmpresaNome(empresaByEmpresaForm.getSearchEmpresaNome(), pageable);
			empresasTotal = empresaService.getByEmpresaNome(empresaByEmpresaForm.getSearchEmpresaNome()).size();

		} else {
			empresaList = empresaService.getByEmpresaNomeFantasia(empresaByEmpresaForm.getSearchEmpresaNomeFantasia(), pageable);
			empresasTotal = empresaService.getByEmpresaNomeFantasia(empresaByEmpresaForm.getSearchEmpresaNomeFantasia()).size();
		}

		Page<Empresa> empresaPage = new PageImpl<Empresa>(empresaList, pageable, empresasTotal+1);

		mv.addObject("empresaPage", empresaPage);
		mv.addObject("page", empresaPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/empresaRelMenu", method = RequestMethod.GET)
	public ModelAndView empresaRelMenu() {

		ModelAndView mv = new ModelAndView("empresa/empresaRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/empresaRel001")
	public ModelAndView empresaRel001() {

		ModelAndView mv = new ModelAndView("empresa/empresaRel001");
		Pageable pageable = new PageRequest(0, 200 , Direction.ASC, "pessoaNome");
		mv.addObject("empresaPage", empresaService.getEmpresaAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/empresaSave", method = RequestMethod.POST)
	public ModelAndView empresaSave(@Valid EmpresaForm empresaForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		if (result.hasErrors()) {
			return empresaAdd(empresaForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/empresaHome");

		try {
			empresaService.save(empresaForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cnpj")) {
			        ObjectError error = new ObjectError("empresaCNPJ","CNPJ da Empresa já existente no cadastro.");
			        result.addError(error);			
			}
            return empresaAdd(empresaForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/empresaView/{id}", method = RequestMethod.GET)
	public ModelAndView empresaView(@PathVariable("id") Long empresaId) {

		Empresa empresa = empresaService.getEmpresaByEmpresaPK(empresaId);
		ModelAndView mv = new ModelAndView("empresa/empresaView");
		EmpresaForm empresaForm = empresaService.converteEmpresaView(empresa);
		mv.addObject("empresaForm", empresaForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	public EnumSet<EmpresaRamo> listarEmpresaRamos() {
	    return EnumSet.allOf(EmpresaRamo.class);
	}

	
}