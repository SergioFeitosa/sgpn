package br.com.j4business.saga.logradourotipo.controller;

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
import br.com.j4business.saga.logradourotipo.model.LogradouroTipo;
import br.com.j4business.saga.logradourotipo.model.LogradouroTipoByLogradouroTipoForm;
import br.com.j4business.saga.logradourotipo.model.LogradouroTipoForm;
import br.com.j4business.saga.logradourotipo.service.LogradouroTipoService;

@Controller
public class LogradouroTipoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private LogradouroTipoService logradouroTipoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/logradouroTipoAdd", method = RequestMethod.GET)
	public ModelAndView logradouroTipoAdd(LogradouroTipoForm logradouroTipoForm) {

		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoAdd");
		mv.addObject("logradouroTipoForm", logradouroTipoForm);
		mv.addObject("logradouroTipoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/logradouroTipoCreate", method = RequestMethod.POST)
	public ModelAndView logradouroTipoCreate(@Valid LogradouroTipoForm logradouroTipoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return logradouroTipoAdd(logradouroTipoForm);
		}

		if (logradouroTipoForm.getLogradouroTipoPK() > 0) {
			return this.logradouroTipoSave(logradouroTipoForm, result, attributes);
			
		}
		
		try {
			logradouroTipoService.create(logradouroTipoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("logradouroTipoNome")) {
			        ObjectError error = new ObjectError("logradouroTipoNome","Nome do Tipo de Logradouro já existente no cadastro.");
			        result.addError(error);			
			}
            
			return logradouroTipoAdd(logradouroTipoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/logradouroTipoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/logradouroTipoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView logradouroTipoDelete(@PathVariable("id") long logradouroTipoPK, @Valid LogradouroTipoForm logradouroTipoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/logradouroTipoHome");

		LogradouroTipo logradouroTipo = logradouroTipoService.getLogradouroTipoByLogradouroTipoPK(logradouroTipoPK);
		try {
			logradouroTipoService.delete(logradouroTipoPK);

			attributes.addFlashAttribute("mensagem", "Tipo de Logradouro excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Tipo de Logradouro não excluído. Existe(m) relacionamento(s) de Tipo de Logradouro ** "+ logradouroTipo.getLogradouroTipoNome() + " ** no cadastro.");
	    }
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@RequestMapping(path = "/logradouroTipoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView logradouroTipoEdit(@PathVariable("id") Long logradouroTipoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoEdit");
		LogradouroTipo logradouroTipo = logradouroTipoService.getLogradouroTipoByLogradouroTipoPK(logradouroTipoId);
		LogradouroTipoForm logradouroTipoForm = logradouroTipoService.converteLogradouroTipo(logradouroTipo);
		mv.addObject("logradouroTipoForm", logradouroTipoForm);
		mv.addObject("logradouroTipoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/logradouroTipoHome", method = RequestMethod.GET)
	public ModelAndView logradouroTipoHome(@Valid LogradouroTipoByLogradouroTipoForm logradouroTipoByLogradouroTipoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoHome");
		
		List<LogradouroTipo> logradouroTipoList = new ArrayList<LogradouroTipo>();

		int logradouroTiposTotal = 0;
		
		if (logradouroTipoByLogradouroTipoForm.getSearchLogradouroTipoNome() == null) {
			logradouroTipoByLogradouroTipoForm.setSearchLogradouroTipoNome("");
			logradouroTipoByLogradouroTipoForm.setSearchLogradouroTipoDescricao("");
			if (logradouroTipoByLogradouroTipoForm.getLogradouroTipoSortTipo() == null) {
				logradouroTipoByLogradouroTipoForm.setLogradouroTipoSortTipo("LogradouroTipoNome");
			}

		}

		if (logradouroTipoByLogradouroTipoForm.getLogradouroTipoSortTipo().equalsIgnoreCase("LogradouroTipoNome") || logradouroTipoByLogradouroTipoForm.getLogradouroTipoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "logradouroTipoNome");

		} else if (logradouroTipoByLogradouroTipoForm.getLogradouroTipoSortTipo().equalsIgnoreCase("LogradouroTipoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "logradouroTipoDescricao");

		}

		if ((!logradouroTipoByLogradouroTipoForm.getSearchLogradouroTipoNome().equalsIgnoreCase(""))) {
			logradouroTipoList = logradouroTipoService.getByLogradouroTipoNome(logradouroTipoByLogradouroTipoForm.getSearchLogradouroTipoNome(), pageable);
			logradouroTiposTotal = logradouroTipoService.getByLogradouroTipoNome(logradouroTipoByLogradouroTipoForm.getSearchLogradouroTipoNome()).size();

		} else {
			logradouroTipoList = logradouroTipoService.getByLogradouroTipoDescricao(logradouroTipoByLogradouroTipoForm.getSearchLogradouroTipoDescricao(), pageable);
			logradouroTiposTotal = logradouroTipoService.getByLogradouroTipoDescricao(logradouroTipoByLogradouroTipoForm.getSearchLogradouroTipoDescricao()).size();
		}

		Page<LogradouroTipo> logradouroTipoPage = new PageImpl<LogradouroTipo>(logradouroTipoList, pageable, logradouroTiposTotal+1);

		mv.addObject("logradouroTipoPage", logradouroTipoPage);
		mv.addObject("page", logradouroTipoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/logradouroTipoRelMenu", method = RequestMethod.GET)
	public ModelAndView logradouroTipoRelMenu() {

		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/logradouroTipoRel001", method = RequestMethod.GET)
	public ModelAndView logradouroTipoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "logradouroTipoNome");
		mv.addObject("logradouroTipoPage", logradouroTipoService.getLogradouroTipoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/logradouroTipoRel002", method = RequestMethod.GET)
	public ModelAndView logradouroTipoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoRel002");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "logradouroTipoNome");
		mv.addObject("logradouroTipos", logradouroTipoService.getLogradouroTipoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/logradouroTipoSave", method = RequestMethod.POST)
	public ModelAndView logradouroTipoSave(@Valid LogradouroTipoForm logradouroTipoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return logradouroTipoAdd(logradouroTipoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/logradouroTipoHome");

		try {
			logradouroTipoService.save(logradouroTipoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("logradouroTipoNome")) {
			        ObjectError error = new ObjectError("logradouroTipoNome","Nome do Tipo de Logradouro já existente no cadastro.");
			        result.addError(error);			
			}
            return logradouroTipoAdd(logradouroTipoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/logradouroTipoView/{id}", method = RequestMethod.GET)
	public ModelAndView logradouroTipoView(@PathVariable("id") Long logradouroTipoId) {

		LogradouroTipo logradouroTipo = logradouroTipoService.getLogradouroTipoByLogradouroTipoPK(logradouroTipoId);
		ModelAndView mv = new ModelAndView("logradouroTipo/logradouroTipoView");
		LogradouroTipoForm logradouroTipoForm = logradouroTipoService.converteLogradouroTipoView(logradouroTipo);
		mv.addObject("logradouroTipoForm", logradouroTipoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}