package br.com.j4business.saga.usuario.controller;

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
import br.com.j4business.saga.usuario.model.Usuario;
import br.com.j4business.saga.usuario.model.UsuarioByUsuarioForm;
import br.com.j4business.saga.usuario.model.UsuarioForm;
import br.com.j4business.saga.usuario.service.UsuarioService;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/usuarioAdd")
	public ModelAndView usuarioAdd(UsuarioForm usuarioForm) {

		ModelAndView mv = new ModelAndView("usuario/usuarioAdd");
		usuarioForm = usuarioService.usuarioParametros(usuarioForm);
		mv.addObject("usuarioForm", usuarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/usuarioCreate")
	public ModelAndView usuarioCreate(@Valid UsuarioForm usuarioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return usuarioAdd(usuarioForm);
		}

		Usuario usuario = new Usuario();

		usuario = usuarioService.getByUsuarioNome(usuarioForm.getUsuarioNome());
		
		if (usuario != null) {
			return this.usuarioSave(usuarioForm, result, attributes);
			
		}
		
		try {
			usuarioService.create(usuarioForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("usuarioNome")) {
			        ObjectError error = new ObjectError("usuarioNome","Nome da Usuario já existente no cadastro.");
			        result.addError(error);			
			}
            
			return usuarioAdd(usuarioForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/usuarioHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/usuarioDelete/{id}")
	public ModelAndView usuarioDelete(@PathVariable("id") String usuarioNome, @Valid UsuarioForm usuarioForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/usuarioHome");

		Usuario usuario = usuarioService.getByUsuarioNome(usuarioNome);
		try {
			usuarioService.delete(usuarioNome);

			attributes.addFlashAttribute("mensagem", "Usuario excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Usuario não excluída. Existe(m) relacionamento(s) de Usuario ** "+ usuario.getUsuarioNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/usuarioEdit/{id}")
	public ModelAndView usuarioEdit(@PathVariable("id") String usuarioNome, Pageable pageable) {

		ModelAndView mv = new ModelAndView("usuario/usuarioEdit");
		Usuario usuario = usuarioService.getByUsuarioNome(usuarioNome);
		UsuarioForm usuarioForm = usuarioService.converteUsuario(usuario);
		mv.addObject("usuarioForm", usuarioForm);
		mv.addObject("usuarioStatusValues", 1);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/usuarioHome")
	public ModelAndView usuarioHome(@Valid UsuarioByUsuarioForm usuarioByUsuarioForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("usuario/usuarioHome");

		List<Usuario> usuarioList = new ArrayList<Usuario>();

		int usuariosTotal = 0;
		
		if (usuarioByUsuarioForm.getSearchUsuarioNome() == null) {
			usuarioByUsuarioForm.setSearchUsuarioNome("");
			if (usuarioByUsuarioForm.getUsuarioSortTipo() == null) {
				usuarioByUsuarioForm.setUsuarioSortTipo("UsuarioNome");
			}

		}

		if (usuarioByUsuarioForm.getUsuarioSortTipo().equalsIgnoreCase("UsuarioNome") || usuarioByUsuarioForm.getUsuarioSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "usuarioNome");

		}

			usuarioList = usuarioService.getByUsuarioNomeLike(usuarioByUsuarioForm.getSearchUsuarioNome(), pageable);
			usuariosTotal = usuarioService.getByUsuarioNomeLike(usuarioByUsuarioForm.getSearchUsuarioNome()).size();

		Page<Usuario> usuarioPage = new PageImpl<Usuario>(usuarioList, pageable, usuariosTotal+1);

		mv.addObject("usuarioPage", usuarioPage);
		mv.addObject("page", usuarioPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/usuarioSave")
	public ModelAndView usuarioSave(@Valid UsuarioForm usuarioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return usuarioAdd(usuarioForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/usuarioHome");

		try {
			usuarioService.save(usuarioForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("usuarioNome")) {
			        ObjectError error = new ObjectError("usuarioNome","Nome da Usuario já existente no cadastro.");
			        result.addError(error);			
			}
            return usuarioAdd(usuarioForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/usuarioRelMenu")
	public ModelAndView usuarioRelMenu() {

		ModelAndView mv = new ModelAndView("usuario/usuarioRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/usuarioRel001")
	public ModelAndView usuarioRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("usuario/usuarioRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "usuarioNome");
		mv.addObject("usuarioPage", usuarioService.getUsuarioAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/usuarioView/{id}")
	public ModelAndView usuarioView(@PathVariable("id") String usuarioNome) {

		Usuario usuario = usuarioService.getByUsuarioNome(usuarioNome);
		ModelAndView mv = new ModelAndView("usuario/usuarioView");
		UsuarioForm usuarioForm = usuarioService.converteUsuarioView(usuario);
		mv.addObject("usuarioForm", usuarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}