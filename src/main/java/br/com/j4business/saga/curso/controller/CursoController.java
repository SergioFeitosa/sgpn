package br.com.j4business.saga.curso.controller;

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

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.curso.model.CursoByCursoForm;
import br.com.j4business.saga.curso.model.CursoForm;
import br.com.j4business.saga.curso.service.CursoService;

@Controller
public class CursoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/cursoAdd")
	public ModelAndView cursoAdd(CursoForm cursoForm) {

		ModelAndView mv = new ModelAndView("curso/cursoAdd");
		cursoForm = cursoService.cursoParametros(cursoForm);
		mv.addObject("cursoForm", cursoForm);
		mv.addObject("cursoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/cursoCreate")
	public ModelAndView cursoCreate(@Valid CursoForm cursoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return cursoAdd(cursoForm);
		}

		if (cursoForm.getCursoPK() > 0) {
			return this.cursoSave(cursoForm, result, attributes);
			
		}
		
		try {
			cursoService.create(cursoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cursoNome")) {
			        ObjectError error = new ObjectError("cursoNome","Nome do Curso já existente no cadastro.");
			        result.addError(error);			
			}
            
			return cursoAdd(cursoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/cursoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/cursoDelete/{id}")
	public ModelAndView cursoDelete(@PathVariable("id") long cursoPK, @Valid CursoForm cursoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/cursoHome");

		Curso curso = cursoService.getCursoByCursoPK(cursoPK);
		try {
			cursoService.delete(cursoPK);

			attributes.addFlashAttribute("mensagem", "Curso excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Curso não excluído. Existe(m) relacionamento(s) de Curso ** "+ curso.getCursoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/cursoEdit/{id}")
	public ModelAndView cursoEdit(@PathVariable("id") Long cursoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("curso/cursoEdit");
		Curso curso = cursoService.getCursoByCursoPK(cursoId);
		CursoForm cursoForm = cursoService.converteCurso(curso);
		mv.addObject("cursoForm", cursoForm);
		mv.addObject("cursoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/cursoHome")
	public ModelAndView cursoHome(@Valid CursoByCursoForm cursoByCursoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("curso/cursoHome");

		List<Curso> cursoList = new ArrayList<Curso>();

		int cursosTotal = 0;
		
		if (cursoByCursoForm.getSearchCursoNome() == null) {
			cursoByCursoForm.setSearchCursoNome("");
			cursoByCursoForm.setSearchCursoDescricao("");
			if (cursoByCursoForm.getCursoSortTipo() == null) {
				cursoByCursoForm.setCursoSortTipo("CursoNome");
			}

		}

		if (cursoByCursoForm.getCursoSortTipo().equalsIgnoreCase("CursoNome") || cursoByCursoForm.getCursoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "cursoNome");

		} else if (cursoByCursoForm.getCursoSortTipo().equalsIgnoreCase("CursoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "cursoDescricao");

		}

		if ((!cursoByCursoForm.getSearchCursoNome().equalsIgnoreCase(""))) {
			cursoList = cursoService.getByCursoNome(cursoByCursoForm.getSearchCursoNome(), pageable);
			cursosTotal = cursoService.getByCursoNome(cursoByCursoForm.getSearchCursoNome()).size();

		} else {
			cursoList = cursoService.getByCursoDescricao(cursoByCursoForm.getSearchCursoDescricao(), pageable);
			cursosTotal = cursoService.getByCursoDescricao(cursoByCursoForm.getSearchCursoDescricao()).size();
		}

		Page<Curso> cursoPage = new PageImpl<Curso>(cursoList, pageable, cursosTotal+1);

		mv.addObject("cursoPage", cursoPage);
		mv.addObject("page", cursoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/cursoSave")
	public ModelAndView cursoSave(@Valid CursoForm cursoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return cursoAdd(cursoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/cursoHome");

		try {
			cursoService.save(cursoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("cursoNome")) {
			        ObjectError error = new ObjectError("cursoNome","Nome da Ação já existente no cadastro.");
			        result.addError(error);			
			}
            return cursoAdd(cursoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/cursoRelMenu")
	public ModelAndView cursoRelMenu() {

		ModelAndView mv = new ModelAndView("curso/cursoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/cursoRel001")
	public ModelAndView cursoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("curso/cursoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/cursoRel002")
	public ModelAndView cursoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("curso/cursoRel002");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/cursoView/{id}")
	public ModelAndView cursoView(@PathVariable("id") Long cursoId) {

		Curso curso = cursoService.getCursoByCursoPK(cursoId);
		ModelAndView mv = new ModelAndView("curso/cursoView");
		CursoForm cursoForm = cursoService.converteCursoView(curso);
		mv.addObject("cursoForm", cursoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}