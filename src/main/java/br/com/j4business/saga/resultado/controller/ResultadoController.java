package br.com.j4business.saga.resultado.controller;

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

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.resultado.model.ResultadoByResultadoForm;
import br.com.j4business.saga.resultado.model.ResultadoForm;
import br.com.j4business.saga.resultado.service.ResultadoService;

@Controller
public class ResultadoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ResultadoService resultadoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/resultadoAdd", method = RequestMethod.GET)
	public ModelAndView resultadoAdd(ResultadoForm resultadoForm) {

		ModelAndView mv = new ModelAndView("resultado/resultadoAdd");
		resultadoForm = resultadoService.resultadoParametros(resultadoForm);
		mv.addObject("resultadoForm", resultadoForm);
		mv.addObject("resultadoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/resultadoCreate", method = RequestMethod.POST)
	public ModelAndView resultadoCreate(@Valid ResultadoForm resultadoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return resultadoAdd(resultadoForm);
		}

		if (resultadoForm.getResultadoPK() > 0) {
			return this.resultadoSave(resultadoForm, result, attributes);
			
		}
		
		try {
			resultadoService.create(resultadoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("resultadoNome")) {
			        ObjectError error = new ObjectError("resultadoNome","Nome do Resultado já existente no cadastro.");
			        result.addError(error);			
			}
            
			return resultadoAdd(resultadoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/resultadoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/resultadoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView resultadoDelete(@PathVariable("id") long resultadoPK, @Valid ResultadoForm resultadoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/resultadoHome");

		Resultado resultado = resultadoService.getResultadoByResultadoPK(resultadoPK);
		try {
			resultadoService.delete(resultadoPK);

			attributes.addFlashAttribute("mensagem", "Resultado excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Resultado não excluído. Existe(m) relacionamento(s) de Resultado ** "+ resultado.getResultadoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/resultadoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView resultadoEdit(@PathVariable("id") Long resultadoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("resultado/resultadoEdit");
		Resultado resultado = resultadoService.getResultadoByResultadoPK(resultadoId);
		ResultadoForm resultadoForm = resultadoService.converteResultado(resultado);
		mv.addObject("resultadoForm", resultadoForm);
		mv.addObject("resultadoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/resultadoHome")
	public ModelAndView resultadoHome(@Valid ResultadoByResultadoForm resultadoByResultadoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("resultado/resultadoHome");

		List<Resultado> resultadoList = new ArrayList<Resultado>();

		int resultadosTotal = 0;
		
		if (resultadoByResultadoForm.getSearchResultadoNome() == null) {
			resultadoByResultadoForm.setSearchResultadoNome("");
			resultadoByResultadoForm.setSearchResultadoDescricao("");
			if (resultadoByResultadoForm.getResultadoSortTipo() == null) {
				resultadoByResultadoForm.setResultadoSortTipo("ResultadoNome");
			}

		}

		if (resultadoByResultadoForm.getResultadoSortTipo().equalsIgnoreCase("ResultadoNome") || resultadoByResultadoForm.getResultadoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "resultadoNome");

		} else if (resultadoByResultadoForm.getResultadoSortTipo().equalsIgnoreCase("ResultadoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "resultadoDescricao");

		}

		if ((!resultadoByResultadoForm.getSearchResultadoNome().equalsIgnoreCase(""))) {
			resultadoList = resultadoService.getByResultadoNome(resultadoByResultadoForm.getSearchResultadoNome(), pageable);
			resultadosTotal = resultadoService.getByResultadoNome(resultadoByResultadoForm.getSearchResultadoNome()).size();

		} else {
			resultadoList = resultadoService.getByResultadoDescricao(resultadoByResultadoForm.getSearchResultadoDescricao(), pageable);
			resultadosTotal = resultadoService.getByResultadoDescricao(resultadoByResultadoForm.getSearchResultadoDescricao()).size();
		}

		Page<Resultado> resultadoPage = new PageImpl<Resultado>(resultadoList, pageable, resultadosTotal+1);

		mv.addObject("resultadoPage", resultadoPage);
		mv.addObject("page", resultadoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/resultadoSave", method = RequestMethod.POST)
	public ModelAndView resultadoSave(@Valid ResultadoForm resultadoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return resultadoAdd(resultadoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/resultadoHome");

		try {
			resultadoService.save(resultadoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("resultadoNome")) {
			        ObjectError error = new ObjectError("resultadoNome","Nome do Resultado já existente no cadastro.");
			        result.addError(error);			
			}
            return resultadoAdd(resultadoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/resultadoRelMenu", method = RequestMethod.GET)
	public ModelAndView resultadoRelMenu() {

		ModelAndView mv = new ModelAndView("resultado/resultadoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/resultadoRel001")
	public ModelAndView resultadoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("resultado/resultadoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/resultadoRel002")
	public ModelAndView resultadoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("resultado/resultadoRel002");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/resultadoView/{id}", method = RequestMethod.GET)
	public ModelAndView resultadoView(@PathVariable("id") Long resultadoId) {

		Resultado resultado = resultadoService.getResultadoByResultadoPK(resultadoId);
		ModelAndView mv = new ModelAndView("resultado/resultadoView");
		ResultadoForm resultadoForm = resultadoService.converteResultadoView(resultado);
		mv.addObject("resultadoForm", resultadoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}