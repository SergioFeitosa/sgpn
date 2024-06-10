package br.com.j4business.saga.empresaprocesso.controller;

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
import br.com.j4business.saga.empresa.model.EmpresaForm;
import br.com.j4business.saga.empresa.service.EmpresaService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcesso;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcessoByEmpresaForm;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcessoForm;
import br.com.j4business.saga.empresaprocesso.service.EmpresaProcessoService;

@Controller
public class EmpresaProcessoController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EmpresaProcessoService empresaProcessoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/empresaProcessoAdd")
	public ModelAndView empresaProcessoAdd(EmpresaProcessoForm empresaProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresaProcesso/empresaProcessoAdd");
		empresaProcessoForm = empresaProcessoService.empresaProcessoParametros(empresaProcessoForm);
		mv.addObject("empresaProcessoForm", empresaProcessoForm);
		mv.addObject("empresaProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("empresaProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable empresaPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("empresaPage", empresaService.getEmpresaAll(empresaPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/empresaProcessoCreate")
	public ModelAndView empresaProcessoCreate(@Valid EmpresaProcessoForm empresaProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return empresaProcessoAdd(empresaProcessoForm,pageable);
		}

		if (empresaProcessoForm.getEmpresaProcessoPK() > 0) {
			return this.empresaProcessoSave(empresaProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			empresaProcessoService.create(empresaProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("empresaProcessoUnique")) {
			        ObjectError error = new ObjectError("empresaNome","Relacionamento entre Empresa e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return empresaProcessoAdd(empresaProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/empresaProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/empresaProcessoDelete/{id}")
	public ModelAndView empresaProcessoDelete(@PathVariable("id") long empresaProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/empresaProcessoHome");
		
		
		EmpresaProcesso empresaProcesso = empresaProcessoService.getEmpresaProcessoByEmpresaProcessoPK(empresaProcessoId);
		try {
			empresaProcessoService.delete(empresaProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Empresa/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Empresa/Processo não excluído. Existe(m) relacionamento(s) de Empresa/Processo ** "+ 
										  empresaProcesso.getEmpresa().getPessoaNome() +
										  " / " +
										  empresaProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/empresaProcessoEdit/{empresaProcessoPK}")
	public ModelAndView empresaProcessoEdit(@PathVariable("empresaProcessoPK") Long empresaProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresaProcesso/empresaProcessoEdit");
		EmpresaProcesso empresaProcesso = empresaProcessoService.getEmpresaProcessoByEmpresaProcessoPK(empresaProcessoPK);
		EmpresaProcessoForm empresaProcessoForm = empresaProcessoService.converteEmpresaProcesso(empresaProcesso);
		mv.addObject("empresaProcessoForm", empresaProcessoForm);
		mv.addObject("empresaProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("empresaProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable empresaPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("empresaPage", empresaService.getEmpresaAll(empresaPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}
	
	@GetMapping(path = "/empresaProcessoHome")
	public ModelAndView empresaProcessoHome(@Valid EmpresaProcessoByEmpresaForm empresaProcessoByEmpresaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresaProcesso/empresaProcessoHome");
		
		List<EmpresaProcesso> empresaProcessoList = new ArrayList<EmpresaProcesso>();
		
		int empresaProcessoTotal = 0;
		
		if (empresaProcessoByEmpresaForm.getSearchProcessoNome() == null) {
			empresaProcessoByEmpresaForm.setSearchEmpresaNome("");
			empresaProcessoByEmpresaForm.setSearchProcessoNome("");
			if (empresaProcessoByEmpresaForm.getEmpresaProcessoSortTipo() == null) {
				empresaProcessoByEmpresaForm.setEmpresaProcessoSortTipo("EmpresaNome");	
			}
			
		}

		if (empresaProcessoByEmpresaForm.getEmpresaProcessoSortTipo().equalsIgnoreCase("EmpresaNome")
				|| empresaProcessoByEmpresaForm.getEmpresaProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"empresa.pessoaNome","processo.processoNome"); 
		
		} else if (empresaProcessoByEmpresaForm.getEmpresaProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","empresa.pessoaNome"); 

		}

		if ( ! empresaProcessoByEmpresaForm.getSearchProcessoNome().equalsIgnoreCase("")){
			empresaProcessoList = empresaProcessoService.getByProcessoNome(empresaProcessoByEmpresaForm.getSearchProcessoNome(),pageable);
			empresaProcessoTotal = empresaProcessoService.getByProcessoNome(empresaProcessoByEmpresaForm.getSearchProcessoNome()).size();
			
		} else {
			empresaProcessoList = empresaProcessoService.getByEmpresaNome(empresaProcessoByEmpresaForm.getSearchEmpresaNome(),pageable);
			empresaProcessoTotal = empresaProcessoService.getByEmpresaNome(empresaProcessoByEmpresaForm.getSearchEmpresaNome()).size();

		} 
		
		Page<EmpresaProcesso> empresaProcessoPage = new PageImpl<EmpresaProcesso>(empresaProcessoList,pageable,empresaProcessoTotal+1);
		
		mv.addObject("empresaProcessoPage", empresaProcessoPage);
		mv.addObject("page",empresaProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/empresaProcessoSave")
	public ModelAndView empresaProcessoSave(@Valid EmpresaProcessoForm empresaProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return empresaProcessoAdd(empresaProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/empresaProcessoHome");

		try {
			empresaProcessoService.save(empresaProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("empresaProcessoUnique")) {
		        ObjectError error = new ObjectError("empresaNome","Relacionamento entre Empresa e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return empresaProcessoAdd(empresaProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/empresaProcessoRelMenu")
	public ModelAndView empresaProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("empresaProcesso/empresaProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/empresaProcessoRel001")
	public ModelAndView empresaProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("empresaProcesso/empresaProcessoRel001");
		Pageable empresaProcessoPageable = PageRequest.of(0, 200, Direction.ASC, "empresa.pessoaNome","processo.processoNome");
		mv.addObject("empresaProcessoPage", empresaProcessoService.getEmpresaProcessoAll(empresaProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/empresaProcessoView/{id}")
	public ModelAndView empresaProcessoView(@PathVariable("id") Long empresaProcessoId) {

		EmpresaProcesso empresaProcesso = empresaProcessoService.getEmpresaProcessoByEmpresaProcessoPK(empresaProcessoId);
		ModelAndView mv = new ModelAndView("empresaProcesso/empresaProcessoView");
		EmpresaProcessoForm empresaProcessoForm = empresaProcessoService.converteEmpresaProcessoView(empresaProcesso);
		EmpresaForm empresaForm = empresaService.converteEmpresaView(empresaProcesso.getEmpresa());
		ProcessoForm processoForm = processoService.converteProcessoView(empresaProcesso.getProcesso());
		mv.addObject("empresaProcessoForm", empresaProcessoForm);
		mv.addObject("empresaForm", empresaForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}