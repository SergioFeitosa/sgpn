package br.com.j4business.saga.texto.controller;

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
import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.texto.model.TextoByTextoForm;
import br.com.j4business.saga.texto.model.TextoForm;
import br.com.j4business.saga.texto.service.TextoService;

@Controller
public class TextoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TextoService textoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/textoAdd")
	public ModelAndView textoAdd(TextoForm textoForm) {

		ModelAndView mv = new ModelAndView("texto/textoAdd");
		textoForm = textoService.textoParametros(textoForm);
		mv.addObject("textoForm", textoForm);
		mv.addObject("textoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/textoCreate")
	public ModelAndView textoCreate(@Valid TextoForm textoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return textoAdd(textoForm);
		}

		if (textoForm.getTextoPK() > 0) {
			return this.textoSave(textoForm, result, attributes);
			
		}
		
		try {
			textoService.create(textoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("textoNome")) {
			        ObjectError error = new ObjectError("textoNome","Nome da Texto já existente no cadastro.");
			        result.addError(error);			
			}
            
			return textoAdd(textoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/textoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/textoDelete/{id}")
	public ModelAndView textoDelete(@PathVariable("id") long textoPK, @Valid TextoForm textoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/textoHome");

		Texto texto = textoService.getTextoByTextoPK(textoPK);
		try {
			textoService.delete(textoPK);

			attributes.addFlashAttribute("mensagem", "Texto excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Texto não excluído. Existe(m) relacionamento(s) de Texto ** "+ texto.getTextoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/textoEdit/{id}")
	public ModelAndView textoEdit(@PathVariable("id") Long textoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("texto/textoEdit");
		Texto texto = textoService.getTextoByTextoPK(textoId);
		TextoForm textoForm = textoService.converteTexto(texto);
		mv.addObject("textoForm", textoForm);
		mv.addObject("textoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/textoHome")
	public ModelAndView textoHome(@Valid TextoByTextoForm textoByTextoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("texto/textoHome");

		List<Texto> textoList = new ArrayList<Texto>();

		int textosTotal = 0;
		
		if (textoByTextoForm.getSearchTextoNome() == null) {
			textoByTextoForm.setSearchTextoNome("");
			textoByTextoForm.setSearchTextoDescricao("");
			if (textoByTextoForm.getTextoSortTipo() == null) {
				textoByTextoForm.setTextoSortTipo("TextoNome");
			}

		}

		if (textoByTextoForm.getTextoSortTipo().equalsIgnoreCase("TextoNome") || textoByTextoForm.getTextoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "textoNome");

		} else if (textoByTextoForm.getTextoSortTipo().equalsIgnoreCase("TextoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "textoDescricao");

		}

		if ((!textoByTextoForm.getSearchTextoNome().equalsIgnoreCase(""))) {
			textoList = textoService.getByTextoNome(textoByTextoForm.getSearchTextoNome(), pageable);
			textosTotal = textoService.getByTextoNome(textoByTextoForm.getSearchTextoNome()).size();

		} else {
			textoList = textoService.getByTextoDescricao(textoByTextoForm.getSearchTextoDescricao(), pageable);
			textosTotal = textoService.getByTextoDescricao(textoByTextoForm.getSearchTextoDescricao()).size();
		}

		Page<Texto> textoPage = new PageImpl<Texto>(textoList, pageable, textosTotal+1);

		mv.addObject("textoPage", textoPage);
		mv.addObject("page", textoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/textoSave")
	public ModelAndView textoSave(@Valid TextoForm textoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return textoAdd(textoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/textoHome");

		try {
			textoService.save(textoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("textoNome")) {
			        ObjectError error = new ObjectError("textoNome","Nome da Texto já existente no cadastro.");
			        result.addError(error);			
			}
            return textoAdd(textoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/textoRelMenu")
	public ModelAndView textoRelMenu() {

		ModelAndView mv = new ModelAndView("texto/textoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/textoRel001")
	public ModelAndView textoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("texto/textoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/textoView/{id}")
	public ModelAndView textoView(@PathVariable("id") Long textoId) {

		Texto texto = textoService.getTextoByTextoPK(textoId);
		ModelAndView mv = new ModelAndView("texto/textoView");
		TextoForm textoForm = textoService.converteTextoView(texto);
		mv.addObject("textoForm", textoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}