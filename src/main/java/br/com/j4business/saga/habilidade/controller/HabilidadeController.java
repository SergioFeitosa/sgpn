package br.com.j4business.saga.habilidade.controller;

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
import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.habilidade.model.HabilidadeByHabilidadeForm;
import br.com.j4business.saga.habilidade.model.HabilidadeForm;
import br.com.j4business.saga.habilidade.service.HabilidadeService;

@Controller
public class HabilidadeController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private HabilidadeService habilidadeService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/habilidadeAdd", method = RequestMethod.GET)
	public ModelAndView habilidadeAdd(HabilidadeForm habilidadeForm) {

		ModelAndView mv = new ModelAndView("habilidade/habilidadeAdd");
		habilidadeForm = habilidadeService.habilidadeParametros(habilidadeForm);
		mv.addObject("habilidadeForm", habilidadeForm);
		mv.addObject("habilidadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/habilidadeCreate", method = RequestMethod.POST)
	public ModelAndView habilidadeCreate(@Valid HabilidadeForm habilidadeForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return habilidadeAdd(habilidadeForm);
		}

		if (habilidadeForm.getHabilidadePK() > 0) {
			return this.habilidadeSave(habilidadeForm, result, attributes);
			
		}
		
		try {
			habilidadeService.create(habilidadeForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("habilidadeNome")) {
			        ObjectError error = new ObjectError("habilidadeNome","Nome da Habilidade já existente no cadastro.");
			        result.addError(error);			
			}
            
			return habilidadeAdd(habilidadeForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/habilidadeHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/habilidadeDelete/{id}", method = RequestMethod.GET)
	public ModelAndView habilidadeDelete(@PathVariable("id") long habilidadePK, @Valid HabilidadeForm habilidadeForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/habilidadeHome");

		Habilidade habilidade = habilidadeService.getHabilidadeByHabilidadePK(habilidadePK);
		try {
			habilidadeService.delete(habilidadePK);

			attributes.addFlashAttribute("mensagem", "Habilidade excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Habilidade não excluída. Existe(m) relacionamento(s) de Habilidade ** "+ habilidade.getHabilidadeNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/habilidadeEdit/{id}", method = RequestMethod.GET)
	public ModelAndView habilidadeEdit(@PathVariable("id") Long habilidadeId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("habilidade/habilidadeEdit");
		Habilidade habilidade = habilidadeService.getHabilidadeByHabilidadePK(habilidadeId);
		HabilidadeForm habilidadeForm = habilidadeService.converteHabilidade(habilidade);
		mv.addObject("habilidadeForm", habilidadeForm);
		mv.addObject("habilidadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/habilidadeHome")
	public ModelAndView habilidadeHome(@Valid HabilidadeByHabilidadeForm habilidadeByHabilidadeForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("habilidade/habilidadeHome");

		List<Habilidade> habilidadeList = new ArrayList<Habilidade>();

		int habilidadesTotal = 0;
		
		if (habilidadeByHabilidadeForm.getSearchHabilidadeNome() == null) {
			habilidadeByHabilidadeForm.setSearchHabilidadeNome("");
			habilidadeByHabilidadeForm.setSearchHabilidadeDescricao("");
			if (habilidadeByHabilidadeForm.getHabilidadeSortTipo() == null) {
				habilidadeByHabilidadeForm.setHabilidadeSortTipo("HabilidadeNome");
			}

		}

		if (habilidadeByHabilidadeForm.getHabilidadeSortTipo().equalsIgnoreCase("HabilidadeNome") || habilidadeByHabilidadeForm.getHabilidadeSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "habilidadeNome");

		} else if (habilidadeByHabilidadeForm.getHabilidadeSortTipo().equalsIgnoreCase("HabilidadeDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "habilidadeDescricao");

		}

		if ((!habilidadeByHabilidadeForm.getSearchHabilidadeNome().equalsIgnoreCase(""))) {
			habilidadeList = habilidadeService.getByHabilidadeNome(habilidadeByHabilidadeForm.getSearchHabilidadeNome(), pageable);
			habilidadesTotal = habilidadeService.getByHabilidadeNome(habilidadeByHabilidadeForm.getSearchHabilidadeNome()).size();

		} else {
			habilidadeList = habilidadeService.getByHabilidadeDescricao(habilidadeByHabilidadeForm.getSearchHabilidadeDescricao(), pageable);
			habilidadesTotal = habilidadeService.getByHabilidadeDescricao(habilidadeByHabilidadeForm.getSearchHabilidadeDescricao()).size();
		}

		Page<Habilidade> habilidadePage = new PageImpl<Habilidade>(habilidadeList, pageable, habilidadesTotal+1);

		mv.addObject("habilidadePage", habilidadePage);
		mv.addObject("page", habilidadePage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/habilidadeSave", method = RequestMethod.POST)
	public ModelAndView habilidadeSave(@Valid HabilidadeForm habilidadeForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return habilidadeAdd(habilidadeForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/habilidadeHome");

		try {
			habilidadeService.save(habilidadeForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("habilidadeNome")) {
			        ObjectError error = new ObjectError("habilidadeNome","Nome da Habilidade já existente no cadastro.");
			        result.addError(error);			
			}
            return habilidadeAdd(habilidadeForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/habilidadeRelMenu", method = RequestMethod.GET)
	public ModelAndView habilidadeRelMenu() {

		ModelAndView mv = new ModelAndView("habilidade/habilidadeRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/habilidadeRel001")
	public ModelAndView habilidadeRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("habilidade/habilidadeRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "habilidadeNome");
		mv.addObject("habilidadePage", habilidadeService.getHabilidadeAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/habilidadeView/{id}", method = RequestMethod.GET)
	public ModelAndView habilidadeView(@PathVariable("id") Long habilidadeId) {

		Habilidade habilidade = habilidadeService.getHabilidadeByHabilidadePK(habilidadeId);
		ModelAndView mv = new ModelAndView("habilidade/habilidadeView");
		HabilidadeForm habilidadeForm = habilidadeService.converteHabilidadeView(habilidade);
		mv.addObject("habilidadeForm", habilidadeForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}