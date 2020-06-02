package br.com.j4business.saga.colaboradorfuncao.controller;

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

import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.funcao.model.FuncaoForm;
import br.com.j4business.saga.funcao.service.FuncaoService;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncao;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncaoByColaboradorForm;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncaoForm;
import br.com.j4business.saga.colaboradorfuncao.service.ColaboradorFuncaoService;

@Controller
public class ColaboradorFuncaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ColaboradorFuncaoService colaboradorFuncaoService;

	@Autowired
	private FuncaoService funcaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/colaboradorFuncaoAdd", method = RequestMethod.GET)
	public ModelAndView colaboradorFuncaoAdd(ColaboradorFuncaoForm colaboradorFuncaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFuncao/colaboradorFuncaoAdd");
		colaboradorFuncaoForm = colaboradorFuncaoService.colaboradorFuncaoParametros(colaboradorFuncaoForm);
		mv.addObject("colaboradorFuncaoForm", colaboradorFuncaoForm);
		mv.addObject("colaboradorFuncaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorFuncaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable funcaoPageable = new PageRequest(0, 200, Direction.ASC, "funcaoNome");
		mv.addObject("funcaoPage", funcaoService.getFuncaoAll(funcaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorFuncaoCreate", method = RequestMethod.POST)
	public ModelAndView colaboradorFuncaoCreate(@Valid ColaboradorFuncaoForm colaboradorFuncaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorFuncaoAdd(colaboradorFuncaoForm,pageable);
		}

		if (colaboradorFuncaoForm.getColaboradorFuncaoPK() > 0) {
			return this.colaboradorFuncaoSave(colaboradorFuncaoForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorFuncaoService.create(colaboradorFuncaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorFuncaoUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Função já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorFuncaoAdd(colaboradorFuncaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorFuncaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/colaboradorFuncaoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorFuncaoDelete(@PathVariable("id") long colaboradorFuncaoId, @Valid FuncaoForm funcaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorFuncaoHome");
		
		
		ColaboradorFuncao colaboradorFuncao = colaboradorFuncaoService.getColaboradorFuncaoByColaboradorFuncaoPK(colaboradorFuncaoId);
		try {
			colaboradorFuncaoService.delete(colaboradorFuncaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Funcao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Funcao não excluído. Existe(m) relacionamento(s) de Colaborador/Funcao ** "+ 
										  colaboradorFuncao.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorFuncao.getFuncao().getFuncaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorFuncaoEdit/{colaboradorFuncaoPK}", method = RequestMethod.GET)
	public ModelAndView colaboradorFuncaoEdit(@PathVariable("colaboradorFuncaoPK") Long colaboradorFuncaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFuncao/colaboradorFuncaoEdit");
		ColaboradorFuncao colaboradorFuncao = colaboradorFuncaoService.getColaboradorFuncaoByColaboradorFuncaoPK(colaboradorFuncaoPK);
		ColaboradorFuncaoForm colaboradorFuncaoForm = colaboradorFuncaoService.converteColaboradorFuncao(colaboradorFuncao);
		mv.addObject("colaboradorFuncaoForm", colaboradorFuncaoForm);
		mv.addObject("colaboradorFuncaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorFuncaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable funcaoPageable = new PageRequest(0, 200, Direction.ASC, "funcaoNome");
		mv.addObject("funcaoPage", funcaoService.getFuncaoAll(funcaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/colaboradorFuncaoHome", method = RequestMethod.GET)
	public ModelAndView colaboradorFuncaoHome(@Valid ColaboradorFuncaoByColaboradorForm colaboradorFuncaoByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFuncao/colaboradorFuncaoHome");
		
		List<ColaboradorFuncao> colaboradorFuncaoList = new ArrayList<ColaboradorFuncao>();
		
		int colaboradorFuncoesTotal = 0;
		
		if (colaboradorFuncaoByColaboradorForm.getSearchFuncaoNome() == null) {
			colaboradorFuncaoByColaboradorForm.setSearchColaboradorNome("");
			colaboradorFuncaoByColaboradorForm.setSearchFuncaoNome("");
			if (colaboradorFuncaoByColaboradorForm.getColaboradorFuncaoSortTipo() == null) {
				colaboradorFuncaoByColaboradorForm.setColaboradorFuncaoSortTipo("FuncaoNome");	
			}
			
		}

		if (colaboradorFuncaoByColaboradorForm.getColaboradorFuncaoSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorFuncaoByColaboradorForm.getColaboradorFuncaoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","funcao.funcaoNome"); 
		
		} else if (colaboradorFuncaoByColaboradorForm.getColaboradorFuncaoSortTipo().equalsIgnoreCase("FuncaoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"funcao.funcaoNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorFuncaoByColaboradorForm.getSearchFuncaoNome().equalsIgnoreCase("")){
			colaboradorFuncaoList = colaboradorFuncaoService.getByFuncaoNome(colaboradorFuncaoByColaboradorForm.getSearchFuncaoNome(),pageable);
			colaboradorFuncoesTotal = colaboradorFuncaoService.getByFuncaoNome(colaboradorFuncaoByColaboradorForm.getSearchFuncaoNome()).size();
			
		} else {
			colaboradorFuncaoList = colaboradorFuncaoService.getByColaboradorNome(colaboradorFuncaoByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorFuncoesTotal = colaboradorFuncaoService.getByColaboradorNome(colaboradorFuncaoByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorFuncao> colaboradorFuncaoPage = new PageImpl<ColaboradorFuncao>(colaboradorFuncaoList,pageable,colaboradorFuncoesTotal+1);
		
		mv.addObject("colaboradorFuncaoPage", colaboradorFuncaoPage);
		mv.addObject("page",colaboradorFuncaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorFuncaoSave", method = RequestMethod.POST)
	public ModelAndView colaboradorFuncaoSave(@Valid ColaboradorFuncaoForm colaboradorFuncaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorFuncaoAdd(colaboradorFuncaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorFuncaoHome");

		try {
			colaboradorFuncaoService.save(colaboradorFuncaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorFuncaoUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Certificação já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorFuncaoAdd(colaboradorFuncaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorFuncaoRelMenu", method = RequestMethod.GET)
	public ModelAndView colaboradorFuncaoRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorFuncao/colaboradorFuncaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/colaboradorFuncaoRel001")
	public ModelAndView colaboradorFuncaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFuncao/colaboradorFuncaoRel001");
		Pageable colaboradorFuncaoPageable = new PageRequest(0, 200, Direction.ASC, "colaborador.pessoaNome","funcao.funcaoNome");
		mv.addObject("colaboradorFuncaoPage", colaboradorFuncaoService.getColaboradorFuncaoAll(colaboradorFuncaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorFuncaoView/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorFuncaoView(@PathVariable("id") Long colaboradorFuncaoId) {

		ColaboradorFuncao colaboradorFuncao = colaboradorFuncaoService.getColaboradorFuncaoByColaboradorFuncaoPK(colaboradorFuncaoId);
		ModelAndView mv = new ModelAndView("colaboradorFuncao/colaboradorFuncaoView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorFuncao.getColaborador());
		FuncaoForm funcaoForm = funcaoService.converteFuncaoView(colaboradorFuncao.getFuncao());
		ColaboradorFuncaoForm colaboradorFuncaoForm = colaboradorFuncaoService.converteColaboradorFuncaoView(colaboradorFuncao);
		mv.addObject("colaboradorFuncaoForm", colaboradorFuncaoForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("funcaoForm", funcaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}