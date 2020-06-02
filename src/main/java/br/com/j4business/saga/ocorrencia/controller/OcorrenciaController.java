package br.com.j4business.saga.ocorrencia.controller;

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
import br.com.j4business.saga.ocorrencia.model.Ocorrencia;
import br.com.j4business.saga.ocorrencia.model.OcorrenciaByOcorrenciaForm;
import br.com.j4business.saga.ocorrencia.model.OcorrenciaForm;
import br.com.j4business.saga.ocorrencia.service.OcorrenciaService;
import br.com.j4business.saga.ocorrenciaatendimento.service.OcorrenciaAtendimentoService;

@Controller
public class OcorrenciaController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private OcorrenciaAtendimentoService ocorrenciaAtendimentoService;

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/ocorrenciaAdd", method = RequestMethod.GET)
	public ModelAndView ocorrenciaAdd(OcorrenciaForm ocorrenciaForm) {

		ModelAndView mv = new ModelAndView("ocorrencia/ocorrenciaAdd");
		ocorrenciaForm = ocorrenciaService.ocorrenciaParametros(ocorrenciaForm);
		mv.addObject("ocorrenciaForm", ocorrenciaForm);
		mv.addObject("ocorrenciaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/ocorrenciaCreate", method = RequestMethod.POST)
	public ModelAndView ocorrenciaCreate(@Valid OcorrenciaForm ocorrenciaForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return ocorrenciaAdd(ocorrenciaForm);
		}

		if (ocorrenciaForm.getOcorrenciaPK() > 0) {
			return this.ocorrenciaSave(ocorrenciaForm, result, attributes);
			
		}
		
		try {
			ocorrenciaService.create(ocorrenciaForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("ocorrenciaNome")) {
			        ObjectError error = new ObjectError("ocorrenciaNome","Nome da Ocorrência já existente no cadastro.");
			        result.addError(error);			
			}
            
			return ocorrenciaAdd(ocorrenciaForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/ocorrenciaHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/ocorrenciaDelete/{id}", method = RequestMethod.GET)
	public ModelAndView ocorrenciaDelete(@PathVariable("id") long ocorrenciaPK, @Valid OcorrenciaForm ocorrenciaForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/ocorrenciaHome");

		Ocorrencia ocorrencia = ocorrenciaService.getOcorrenciaByOcorrenciaPK(ocorrenciaPK);
		try {
			ocorrenciaService.delete(ocorrenciaPK);

			attributes.addFlashAttribute("mensagem", "Ocorrência excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Ocorrência não excluída. Existe(m) relacionamento(s) de Ocorrência ** "+ ocorrencia.getOcorrenciaNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/ocorrenciaEdit/{id}", method = RequestMethod.GET)
	public ModelAndView ocorrenciaEdit(@PathVariable("id") Long ocorrenciaPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrencia/ocorrenciaEdit");
		Ocorrencia ocorrencia = ocorrenciaService.getOcorrenciaByOcorrenciaPK(ocorrenciaPK);
		OcorrenciaForm ocorrenciaForm = ocorrenciaService.converteOcorrencia(ocorrencia);
		mv.addObject("ocorrenciaForm", ocorrenciaForm);
		mv.addObject("ocorrenciaStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable ocorrenciaAtendimentoPageable = new PageRequest(0, 200, Direction.ASC, "atendimento.atendimentoNome");
		mv.addObject("ocorrenciaAtendimentoPage", ocorrenciaAtendimentoService.getByOcorrenciaPK(ocorrenciaPK, ocorrenciaAtendimentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/ocorrenciaHome")
	public ModelAndView ocorrenciaHome(@Valid OcorrenciaByOcorrenciaForm ocorrenciaByOcorrenciaForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrencia/ocorrenciaHome");

		List<Ocorrencia> ocorrenciaList = new ArrayList<Ocorrencia>();

		int ocorrenciasTotal = 0;
		
		if (ocorrenciaByOcorrenciaForm.getSearchOcorrenciaNome() == null) {
			ocorrenciaByOcorrenciaForm.setSearchOcorrenciaNome("");
			ocorrenciaByOcorrenciaForm.setSearchOcorrenciaDescricao("");
			if (ocorrenciaByOcorrenciaForm.getOcorrenciaSortTipo() == null) {
				ocorrenciaByOcorrenciaForm.setOcorrenciaSortTipo("OcorrenciaNome");
			}

		}

		if (ocorrenciaByOcorrenciaForm.getOcorrenciaSortTipo().equalsIgnoreCase("OcorrenciaNome") || ocorrenciaByOcorrenciaForm.getOcorrenciaSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "ocorrenciaNome");

		} else if (ocorrenciaByOcorrenciaForm.getOcorrenciaSortTipo().equalsIgnoreCase("OcorrenciaDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "ocorrenciaDescricao");

		}

		if ((!ocorrenciaByOcorrenciaForm.getSearchOcorrenciaNome().equalsIgnoreCase(""))) {
			ocorrenciaList = ocorrenciaService.getByOcorrenciaNome(ocorrenciaByOcorrenciaForm.getSearchOcorrenciaNome(), pageable);
			ocorrenciasTotal = ocorrenciaService.getByOcorrenciaNome(ocorrenciaByOcorrenciaForm.getSearchOcorrenciaNome()).size();

		} else {
			ocorrenciaList = ocorrenciaService.getByOcorrenciaDescricao(ocorrenciaByOcorrenciaForm.getSearchOcorrenciaDescricao(), pageable);
			ocorrenciasTotal = ocorrenciaService.getByOcorrenciaDescricao(ocorrenciaByOcorrenciaForm.getSearchOcorrenciaDescricao()).size();
		}

		Page<Ocorrencia> ocorrenciaPage = new PageImpl<Ocorrencia>(ocorrenciaList, pageable, ocorrenciasTotal+1);

		mv.addObject("ocorrenciaPage", ocorrenciaPage);
		mv.addObject("page", ocorrenciaPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/ocorrenciaSave", method = RequestMethod.POST)
	public ModelAndView ocorrenciaSave(@Valid OcorrenciaForm ocorrenciaForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return ocorrenciaAdd(ocorrenciaForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/ocorrenciaHome");

		try {
			ocorrenciaService.save(ocorrenciaForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("ocorrenciaNome")) {
			        ObjectError error = new ObjectError("ocorrenciaNome","Nome da Ocorrência já existente no cadastro.");
			        result.addError(error);			
			}
            return ocorrenciaAdd(ocorrenciaForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/ocorrenciaRelMenu", method = RequestMethod.GET)
	public ModelAndView ocorrenciaRelMenu() {

		ModelAndView mv = new ModelAndView("ocorrencia/ocorrenciaRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/ocorrenciaRel001")
	public ModelAndView ocorrenciaRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrencia/ocorrenciaRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "ocorrenciaNome");
		mv.addObject("ocorrenciaPage", ocorrenciaService.getOcorrenciaAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/ocorrenciaView/{id}", method = RequestMethod.GET)
	public ModelAndView ocorrenciaView(@PathVariable("id") Long ocorrenciaId) {

		Ocorrencia ocorrencia = ocorrenciaService.getOcorrenciaByOcorrenciaPK(ocorrenciaId);
		ModelAndView mv = new ModelAndView("ocorrencia/ocorrenciaView");
		OcorrenciaForm ocorrenciaForm = ocorrenciaService.converteOcorrenciaView(ocorrencia);
		mv.addObject("ocorrenciaForm", ocorrenciaForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;

	}

}