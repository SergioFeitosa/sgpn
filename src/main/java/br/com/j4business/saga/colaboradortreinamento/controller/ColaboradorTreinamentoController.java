package br.com.j4business.saga.colaboradortreinamento.controller;

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
import br.com.j4business.saga.treinamento.model.TreinamentoForm;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamento;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamentoByColaboradorForm;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamentoForm;
import br.com.j4business.saga.colaboradortreinamento.service.ColaboradorTreinamentoService;

@Controller
public class ColaboradorTreinamentoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ColaboradorTreinamentoService colaboradorTreinamentoService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/colaboradorTreinamentoAdd")
	public ModelAndView colaboradorTreinamentoAdd(ColaboradorTreinamentoForm colaboradorTreinamentoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorTreinamento/colaboradorTreinamentoAdd");
		colaboradorTreinamentoForm = colaboradorTreinamentoService.colaboradorTreinamentoParametros(colaboradorTreinamentoForm);
		mv.addObject("colaboradorTreinamentoForm", colaboradorTreinamentoForm);
		mv.addObject("colaboradorTreinamentoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorTreinamentoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable treinamentoPageable = PageRequest.of(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/colaboradorTreinamentoCreate")
	public ModelAndView colaboradorTreinamentoCreate(@Valid ColaboradorTreinamentoForm colaboradorTreinamentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorTreinamentoAdd(colaboradorTreinamentoForm,pageable);
		}

		if (colaboradorTreinamentoForm.getColaboradorTreinamentoPK() > 0) {
			return this.colaboradorTreinamentoSave(colaboradorTreinamentoForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorTreinamentoService.create(colaboradorTreinamentoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorTreinamentoUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Treinamento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorTreinamentoAdd(colaboradorTreinamentoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorTreinamentoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/colaboradorTreinamentoDelete/{id}")
	public ModelAndView colaboradorTreinamentoDelete(@PathVariable("id") long colaboradorTreinamentoId, @Valid TreinamentoForm treinamentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorTreinamentoHome");
		
		
		ColaboradorTreinamento colaboradorTreinamento = colaboradorTreinamentoService.getColaboradorTreinamentoByColaboradorTreinamentoPK(colaboradorTreinamentoId);
		try {
			colaboradorTreinamentoService.delete(colaboradorTreinamentoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Treinamento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Treinamento não excluído. Existe(m) relacionamento(s) de Colaborador/Treinamento ** "+ 
										  colaboradorTreinamento.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorTreinamento.getTreinamento().getTreinamentoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorTreinamentoEdit/{colaboradorTreinamentoPK}")
	public ModelAndView colaboradorTreinamentoEdit(@PathVariable("colaboradorTreinamentoPK") Long colaboradorTreinamentoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorTreinamento/colaboradorTreinamentoEdit");
		ColaboradorTreinamento colaboradorTreinamento = colaboradorTreinamentoService.getColaboradorTreinamentoByColaboradorTreinamentoPK(colaboradorTreinamentoPK);
		ColaboradorTreinamentoForm colaboradorTreinamentoForm = colaboradorTreinamentoService.converteColaboradorTreinamento(colaboradorTreinamento);
		mv.addObject("colaboradorTreinamentoForm", colaboradorTreinamentoForm);
		mv.addObject("colaboradorTreinamentoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorTreinamentoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable treinamentoPageable = PageRequest.of(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/colaboradorTreinamentoHome")
	public ModelAndView colaboradorTreinamentoHome(@Valid ColaboradorTreinamentoByColaboradorForm colaboradorTreinamentoByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorTreinamento/colaboradorTreinamentoHome");
		
		List<ColaboradorTreinamento> colaboradorTreinamentoList = new ArrayList<ColaboradorTreinamento>();
		
		int colaboradorTreinamentoTotal = 0;
		
		if (colaboradorTreinamentoByColaboradorForm.getSearchTreinamentoNome() == null) {
			colaboradorTreinamentoByColaboradorForm.setSearchColaboradorNome("");
			colaboradorTreinamentoByColaboradorForm.setSearchTreinamentoNome("");
			if (colaboradorTreinamentoByColaboradorForm.getColaboradorTreinamentoSortTipo() == null) {
				colaboradorTreinamentoByColaboradorForm.setColaboradorTreinamentoSortTipo("TreinamentoNome");	
			}
			
		}

		if (colaboradorTreinamentoByColaboradorForm.getColaboradorTreinamentoSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorTreinamentoByColaboradorForm.getColaboradorTreinamentoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","treinamento.treinamentoNome"); 
		
		} else if (colaboradorTreinamentoByColaboradorForm.getColaboradorTreinamentoSortTipo().equalsIgnoreCase("TreinamentoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"treinamento.treinamentoNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorTreinamentoByColaboradorForm.getSearchTreinamentoNome().equalsIgnoreCase("")){
			colaboradorTreinamentoList = colaboradorTreinamentoService.getByTreinamentoNome(colaboradorTreinamentoByColaboradorForm.getSearchTreinamentoNome(),pageable);
			colaboradorTreinamentoTotal = colaboradorTreinamentoService.getByTreinamentoNome(colaboradorTreinamentoByColaboradorForm.getSearchTreinamentoNome()).size();
			
		} else {
			colaboradorTreinamentoList = colaboradorTreinamentoService.getByColaboradorNome(colaboradorTreinamentoByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorTreinamentoTotal = colaboradorTreinamentoService.getByColaboradorNome(colaboradorTreinamentoByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorTreinamento> colaboradorTreinamentoPage = new PageImpl<ColaboradorTreinamento>(colaboradorTreinamentoList,pageable,colaboradorTreinamentoTotal+1);
		
		mv.addObject("colaboradorTreinamentoPage", colaboradorTreinamentoPage);
		mv.addObject("page",colaboradorTreinamentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/colaboradorTreinamentoSave")
	public ModelAndView colaboradorTreinamentoSave(@Valid ColaboradorTreinamentoForm colaboradorTreinamentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorTreinamentoAdd(colaboradorTreinamentoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorTreinamentoHome");

		try {
			colaboradorTreinamentoService.save(colaboradorTreinamentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorTreinamentoUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Treinamento já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorTreinamentoAdd(colaboradorTreinamentoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/colaboradorTreinamentoRelMenu")
	public ModelAndView colaboradorTreinamentoRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorTreinamento/colaboradorTreinamentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorTreinamentoRel001")
	public ModelAndView colaboradorTreinamentoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorTreinamento/colaboradorTreinamentoRel001");
		Pageable treinamentoPageable = PageRequest.of(0, 200, Direction.ASC, "colaborador.pessoaNome","treinamento.treinamentoNome");
		mv.addObject("colaboradorTreinamentoPage", colaboradorTreinamentoService.getColaboradorTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorTreinamentoView/{id}")
	public ModelAndView colaboradorTreinamentoView(@PathVariable("id") Long colaboradorTreinamentoId) {

		ColaboradorTreinamento colaboradorTreinamento = colaboradorTreinamentoService.getColaboradorTreinamentoByColaboradorTreinamentoPK(colaboradorTreinamentoId);
		ModelAndView mv = new ModelAndView("colaboradorTreinamento/colaboradorTreinamentoView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorTreinamento.getColaborador());
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamentoView(colaboradorTreinamento.getTreinamento());
		ColaboradorTreinamentoForm colaboradorTreinamentoForm = colaboradorTreinamentoService.converteColaboradorTreinamentoView(colaboradorTreinamento);
		mv.addObject("colaboradorTreinamentoForm", colaboradorTreinamentoForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}