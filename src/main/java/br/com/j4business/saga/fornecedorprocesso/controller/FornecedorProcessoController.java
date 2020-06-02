package br.com.j4business.saga.fornecedorprocesso.controller;

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

import br.com.j4business.saga.fornecedor.model.FornecedorForm;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContrato;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcesso;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcessoByFornecedorForm;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcessoForm;
import br.com.j4business.saga.fornecedorprocesso.service.FornecedorProcessoService;

@Controller
public class FornecedorProcessoController {

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorProcessoService fornecedorProcessoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/fornecedorProcessoAdd", method = RequestMethod.GET)
	public ModelAndView fornecedorProcessoAdd(FornecedorProcessoForm fornecedorProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorProcesso/fornecedorProcessoAdd");
		fornecedorProcessoForm = fornecedorProcessoService.fornecedorProcessoParametros(fornecedorProcessoForm);
		mv.addObject("fornecedorProcessoForm", fornecedorProcessoForm);
		mv.addObject("fornecedorProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("fornecedorProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/fornecedorProcessoCreate", method = RequestMethod.POST)
	public ModelAndView fornecedorProcessoCreate(@Valid FornecedorProcessoForm fornecedorProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return fornecedorProcessoAdd(fornecedorProcessoForm,pageable);
		}

		if (fornecedorProcessoForm.getFornecedorProcessoPK() > 0) {
			return this.fornecedorProcessoSave(fornecedorProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			fornecedorProcessoService.create(fornecedorProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("fornecedorProcessoUnique")) {
			        ObjectError error = new ObjectError("fornecedorNome","Relacionamento entre Fornecedor e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return fornecedorProcessoAdd(fornecedorProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/fornecedorProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/fornecedorProcessoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView fornecedorProcessoDelete(@PathVariable("id") long fornecedorProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/fornecedorProcessoHome");
		
		
		FornecedorProcesso fornecedorProcesso = fornecedorProcessoService.getFornecedorProcessoByFornecedorProcessoPK(fornecedorProcessoId);
		try {
			fornecedorProcessoService.delete(fornecedorProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Fornecedor/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Fornecedor/Processo não excluído. Existe(m) relacionamento(s) de Fornecedor/Processo ** "+ 
										  fornecedorProcesso.getFornecedor().getPessoaNome() +
										  " / " +
										  fornecedorProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/fornecedorProcessoEdit/{fornecedorProcessoPK}", method = RequestMethod.GET)
	public ModelAndView fornecedorProcessoEdit(@PathVariable("fornecedorProcessoPK") Long fornecedorProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorProcesso/fornecedorProcessoEdit");
		FornecedorProcesso fornecedorProcesso = fornecedorProcessoService.getFornecedorProcessoByFornecedorProcessoPK(fornecedorProcessoPK);
		FornecedorProcessoForm fornecedorProcessoForm = fornecedorProcessoService.converteFornecedorProcesso(fornecedorProcesso);
		mv.addObject("fornecedorProcessoForm", fornecedorProcessoForm);
		mv.addObject("fornecedorProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("fornecedorProcessoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/fornecedorProcessoHome", method = RequestMethod.GET)
	public ModelAndView fornecedorProcessoHome(@Valid FornecedorProcessoByFornecedorForm fornecedorProcessoByFornecedorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorProcesso/fornecedorProcessoHome");
		
		List<FornecedorProcesso> fornecedorProcessoList = new ArrayList<FornecedorProcesso>();
		
		int fornecedorProcessoTotal = 0;
		
		if (fornecedorProcessoByFornecedorForm.getSearchProcessoNome() == null) {
			fornecedorProcessoByFornecedorForm.setSearchFornecedorNome("");
			fornecedorProcessoByFornecedorForm.setSearchProcessoNome("");
			if (fornecedorProcessoByFornecedorForm.getFornecedorProcessoSortTipo() == null) {
				fornecedorProcessoByFornecedorForm.setFornecedorProcessoSortTipo("ProcessoNome");	
			}
			
		}

		if (fornecedorProcessoByFornecedorForm.getFornecedorProcessoSortTipo().equalsIgnoreCase("FornecedorNome")
				|| fornecedorProcessoByFornecedorForm.getFornecedorProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"fornecedor.pessoaNome","processo.processoNome"); 
		
		} else if (fornecedorProcessoByFornecedorForm.getFornecedorProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","fornecedor.pessoaNome"); 

		}

		if ( ! fornecedorProcessoByFornecedorForm.getSearchProcessoNome().equalsIgnoreCase("")){
			fornecedorProcessoList = fornecedorProcessoService.getByProcessoNome(fornecedorProcessoByFornecedorForm.getSearchProcessoNome(),pageable);
			fornecedorProcessoTotal = fornecedorProcessoService.getByProcessoNome(fornecedorProcessoByFornecedorForm.getSearchProcessoNome()).size();
			
		} else {
			fornecedorProcessoList = fornecedorProcessoService.getByFornecedorNome(fornecedorProcessoByFornecedorForm.getSearchFornecedorNome(),pageable);
			fornecedorProcessoTotal = fornecedorProcessoService.getByFornecedorNome(fornecedorProcessoByFornecedorForm.getSearchFornecedorNome()).size();

		} 
		
		Page<FornecedorProcesso> fornecedorProcessoPage = new PageImpl<FornecedorProcesso>(fornecedorProcessoList,pageable,fornecedorProcessoTotal+1);
		
		mv.addObject("fornecedorProcessoPage", fornecedorProcessoPage);
		mv.addObject("page",fornecedorProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/fornecedorProcessoSave", method = RequestMethod.POST)
	public ModelAndView fornecedorProcessoSave(@Valid FornecedorProcessoForm fornecedorProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return fornecedorProcessoAdd(fornecedorProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/fornecedorProcessoHome");

		try {
			fornecedorProcessoService.save(fornecedorProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("fornecedorProcessoUnique")) {
		        ObjectError error = new ObjectError("fornecedorNome","Relacionamento entre Fornecedor e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return fornecedorProcessoAdd(fornecedorProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/fornecedorProcessoRelMenu", method = RequestMethod.GET)
	public ModelAndView fornecedorProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("fornecedorProcesso/fornecedorProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;

	}

	@RequestMapping("/fornecedorProcessoRel001")
	public ModelAndView fornecedorProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("fornecedorProcesso/fornecedorProcessoRel001");
		Pageable fornecedorProcessoPageable = new PageRequest(0, 200, Direction.ASC, "fornecedor.pessoaNome","processo.processoNome");
		mv.addObject("fornecedorProcessoPage", fornecedorProcessoService.getFornecedorProcessoAll(fornecedorProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/fornecedorProcessoView/{id}", method = RequestMethod.GET)
	public ModelAndView fornecedorProcessoView(@PathVariable("id") Long fornecedorProcessoId) {

		FornecedorProcesso fornecedorProcesso = fornecedorProcessoService.getFornecedorProcessoByFornecedorProcessoPK(fornecedorProcessoId);
		ModelAndView mv = new ModelAndView("fornecedorProcesso/fornecedorProcessoView");
		FornecedorForm fornecedorForm = fornecedorService.converteFornecedorView(fornecedorProcesso.getFornecedor());
		ProcessoForm processoForm = processoService.converteProcessoView(fornecedorProcesso.getProcesso());
		FornecedorProcessoForm fornecedorProcessoForm = fornecedorProcessoService.converteFornecedorProcessoView(fornecedorProcesso);
		mv.addObject("fornecedorProcessoForm", fornecedorProcessoForm);
		mv.addObject("fornecedorForm", fornecedorForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}