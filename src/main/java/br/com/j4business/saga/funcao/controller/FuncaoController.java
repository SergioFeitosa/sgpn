package br.com.j4business.saga.funcao.controller;

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
import br.com.j4business.saga.funcao.model.Funcao;
import br.com.j4business.saga.funcao.model.FuncaoByFuncaoForm;
import br.com.j4business.saga.funcao.model.FuncaoForm;
import br.com.j4business.saga.funcao.service.FuncaoService;

@Controller
public class FuncaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FuncaoService funcaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/funcaoAdd", method = RequestMethod.GET)
	public ModelAndView funcaoAdd(FuncaoForm funcaoForm) {

		ModelAndView mv = new ModelAndView("funcao/funcaoAdd");
		funcaoForm = funcaoService.funcaoParametros(funcaoForm);
		mv.addObject("funcaoForm", funcaoForm);
		mv.addObject("funcaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/funcaoCreate", method = RequestMethod.POST)
	public ModelAndView funcaoCreate(@Valid FuncaoForm funcaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return funcaoAdd(funcaoForm);
		}

		if (funcaoForm.getFuncaoPK() > 0) {
			return this.funcaoSave(funcaoForm, result, attributes);
			
		}
		
		try {
			funcaoService.create(funcaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("funcaoNome")) {
			        ObjectError error = new ObjectError("funcaoNome","Nome da Função já existente no cadastro.");
			        result.addError(error);			
			}
            
			return funcaoAdd(funcaoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/funcaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/funcaoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView funcaoDelete(@PathVariable("id") long funcaoPK, @Valid FuncaoForm funcaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/funcaoHome");

		Funcao funcao = funcaoService.getFuncaoByFuncaoPK(funcaoPK);
		try {
			funcaoService.delete(funcaoPK);

			attributes.addFlashAttribute("mensagem", "Função excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Função não excluída. Existe(m) relacionamento(s) de Função ** "+ funcao.getFuncaoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/funcaoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView funcaoEdit(@PathVariable("id") Long funcaoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("funcao/funcaoEdit");
		Funcao funcao = funcaoService.getFuncaoByFuncaoPK(funcaoId);
		FuncaoForm funcaoForm = funcaoService.converteFuncao(funcao);
		mv.addObject("funcaoForm", funcaoForm);
		mv.addObject("funcaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/funcaoHome")
	public ModelAndView funcaoHome(@Valid FuncaoByFuncaoForm funcaoByFuncaoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("funcao/funcaoHome");

		List<Funcao> funcaoList = new ArrayList<Funcao>();

		int funcaosTotal = 0;
		
		if (funcaoByFuncaoForm.getSearchFuncaoNome() == null) {
			funcaoByFuncaoForm.setSearchFuncaoNome("");
			funcaoByFuncaoForm.setSearchFuncaoDescricao("");
			if (funcaoByFuncaoForm.getFuncaoSortTipo() == null) {
				funcaoByFuncaoForm.setFuncaoSortTipo("FuncaoNome");
			}

		}

		if (funcaoByFuncaoForm.getFuncaoSortTipo().equalsIgnoreCase("FuncaoNome") || funcaoByFuncaoForm.getFuncaoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "funcaoNome");

		} else if (funcaoByFuncaoForm.getFuncaoSortTipo().equalsIgnoreCase("FuncaoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "funcaoDescricao");

		}

		if ((!funcaoByFuncaoForm.getSearchFuncaoNome().equalsIgnoreCase(""))) {
			funcaoList = funcaoService.getByFuncaoNome(funcaoByFuncaoForm.getSearchFuncaoNome(), pageable);
			funcaosTotal = funcaoService.getByFuncaoNome(funcaoByFuncaoForm.getSearchFuncaoNome()).size();

		} else {
			funcaoList = funcaoService.getByFuncaoDescricao(funcaoByFuncaoForm.getSearchFuncaoDescricao(), pageable);
			funcaosTotal = funcaoService.getByFuncaoDescricao(funcaoByFuncaoForm.getSearchFuncaoDescricao()).size();
		}

		Page<Funcao> funcaoPage = new PageImpl<Funcao>(funcaoList, pageable, funcaosTotal+1);

		mv.addObject("funcaoPage", funcaoPage);
		mv.addObject("page", funcaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/funcaoSave", method = RequestMethod.POST)
	public ModelAndView funcaoSave(@Valid FuncaoForm funcaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return funcaoAdd(funcaoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/funcaoHome");

		try {
			funcaoService.save(funcaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("funcaoNome")) {
			        ObjectError error = new ObjectError("funcaoNome","Nome da Função já existente no cadastro.");
			        result.addError(error);			
			}
            return funcaoAdd(funcaoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/funcaoRelMenu", method = RequestMethod.GET)
	public ModelAndView funcaoRelMenu() {

		ModelAndView mv = new ModelAndView("funcao/funcaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/funcaoRel001")
	public ModelAndView funcaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("funcao/funcaoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "funcaoNome");
		mv.addObject("funcaoPage", funcaoService.getFuncaoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/funcaoView/{id}", method = RequestMethod.GET)
	public ModelAndView funcaoView(@PathVariable("id") Long funcaoId) {

		Funcao funcao = funcaoService.getFuncaoByFuncaoPK(funcaoId);
		ModelAndView mv = new ModelAndView("funcao/funcaoView");
		FuncaoForm funcaoForm = funcaoService.converteFuncaoView(funcao);
		mv.addObject("funcaoForm", funcaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}