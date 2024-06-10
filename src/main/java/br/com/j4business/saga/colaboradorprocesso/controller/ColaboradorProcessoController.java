package br.com.j4business.saga.colaboradorprocesso.controller;

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

import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcesso;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcessoByColaboradorForm;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcessoForm;
import br.com.j4business.saga.colaboradorprocesso.service.ColaboradorProcessoService;

@Controller
public class ColaboradorProcessoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ColaboradorProcessoService colaboradorProcessoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/colaboradorProcessoAdd")
	public ModelAndView colaboradorProcessoAdd(ColaboradorProcessoForm colaboradorProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorProcesso/colaboradorProcessoAdd");
		colaboradorProcessoForm = colaboradorProcessoService.colaboradorProcessoParametros(colaboradorProcessoForm);
		mv.addObject("colaboradorProcessoForm", colaboradorProcessoForm);
		mv.addObject("colaboradorProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/colaboradorProcessoCreate")
	public ModelAndView colaboradorProcessoCreate(@Valid ColaboradorProcessoForm colaboradorProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorProcessoAdd(colaboradorProcessoForm,pageable);
		}

		if (colaboradorProcessoForm.getColaboradorProcessoPK() > 0) {
			return this.colaboradorProcessoSave(colaboradorProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorProcessoService.create(colaboradorProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorProcessoUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorProcessoAdd(colaboradorProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/colaboradorProcessoDelete/{id}")
	public ModelAndView colaboradorProcessoDelete(@PathVariable("id") long colaboradorProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorProcessoHome");
		
		
		ColaboradorProcesso colaboradorProcesso = colaboradorProcessoService.getColaboradorProcessoByColaboradorProcessoPK(colaboradorProcessoId);
		try {
			colaboradorProcessoService.delete(colaboradorProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Processo não excluído. Existe(m) relacionamento(s) de Colaborador/Processo ** "+ 
										  colaboradorProcesso.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorProcessoEdit/{colaboradorProcessoPK}")
	public ModelAndView colaboradorProcessoEdit(@PathVariable("colaboradorProcessoPK") Long colaboradorProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorProcesso/colaboradorProcessoEdit");
		ColaboradorProcesso colaboradorProcesso = colaboradorProcessoService.getColaboradorProcessoByColaboradorProcessoPK(colaboradorProcessoPK);
		ColaboradorProcessoForm colaboradorProcessoForm = colaboradorProcessoService.converteColaboradorProcesso(colaboradorProcesso);
		mv.addObject("colaboradorProcessoForm", colaboradorProcessoForm);
		mv.addObject("colaboradorProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/colaboradorProcessoHome")
	public ModelAndView colaboradorProcessoHome(@Valid ColaboradorProcessoByColaboradorForm colaboradorProcessoByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorProcesso/colaboradorProcessoHome");
		
		List<ColaboradorProcesso> colaboradorProcessoList = new ArrayList<ColaboradorProcesso>();
		
		int colaboradorProcessoTotal = 0;
		
		if (colaboradorProcessoByColaboradorForm.getSearchProcessoNome() == null) {
			colaboradorProcessoByColaboradorForm.setSearchColaboradorNome("");
			colaboradorProcessoByColaboradorForm.setSearchProcessoNome("");
			if (colaboradorProcessoByColaboradorForm.getColaboradorProcessoSortTipo() == null) {
				colaboradorProcessoByColaboradorForm.setColaboradorProcessoSortTipo("ProcessoNome");	
			}
			
		}

		if (colaboradorProcessoByColaboradorForm.getColaboradorProcessoSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorProcessoByColaboradorForm.getColaboradorProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","processo.processoNome"); 
		
		} else if (colaboradorProcessoByColaboradorForm.getColaboradorProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorProcessoByColaboradorForm.getSearchProcessoNome().equalsIgnoreCase("")){
			colaboradorProcessoList = colaboradorProcessoService.getByProcessoNome(colaboradorProcessoByColaboradorForm.getSearchProcessoNome(),pageable);
			colaboradorProcessoTotal = colaboradorProcessoService.getByProcessoNome(colaboradorProcessoByColaboradorForm.getSearchProcessoNome()).size();
			
		} else {
			colaboradorProcessoList = colaboradorProcessoService.getByColaboradorNome(colaboradorProcessoByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorProcessoTotal = colaboradorProcessoService.getByColaboradorNome(colaboradorProcessoByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorProcesso> colaboradorProcessoPage = new PageImpl<ColaboradorProcesso>(colaboradorProcessoList,pageable,colaboradorProcessoTotal+1);
		
		mv.addObject("colaboradorProcessoPage", colaboradorProcessoPage);
		mv.addObject("page",colaboradorProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/colaboradorProcessoSave")
	public ModelAndView colaboradorProcessoSave(@Valid ColaboradorProcessoForm colaboradorProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorProcessoAdd(colaboradorProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorProcessoHome");

		try {
			colaboradorProcessoService.save(colaboradorProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorProcessoUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorProcessoAdd(colaboradorProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/colaboradorProcessoRelMenu")
	public ModelAndView colaboradorProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorProcesso/colaboradorProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorProcessoRel001")
	public ModelAndView colaboradorProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorProcesso/colaboradorProcessoRel001");
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "colaborador.pessoaNome","processo.processoNome");
		mv.addObject("colaboradorProcessoPage", colaboradorProcessoService.getColaboradorProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorProcessoView/{id}")
	public ModelAndView colaboradorProcessoView(@PathVariable("id") Long colaboradorProcessoId) {

		ColaboradorProcesso colaboradorProcesso = colaboradorProcessoService.getColaboradorProcessoByColaboradorProcessoPK(colaboradorProcessoId);
		ModelAndView mv = new ModelAndView("colaboradorProcesso/colaboradorProcessoView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorProcesso.getColaborador());
		ProcessoForm processoForm = processoService.converteProcessoView(colaboradorProcesso.getProcesso());
		ColaboradorProcessoForm colaboradorProcessoForm = colaboradorProcessoService.converteColaboradorProcessoView(colaboradorProcesso);
		mv.addObject("colaboradorProcessoForm", colaboradorProcessoForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}