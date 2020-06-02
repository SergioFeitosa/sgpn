package br.com.j4business.saga.imagem.controller;

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
import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.imagem.model.ImagemByImagemForm;
import br.com.j4business.saga.imagem.model.ImagemForm;
import br.com.j4business.saga.imagem.service.ImagemService;

@Controller
public class ImagemController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/imagemAdd", method = RequestMethod.GET)
	public ModelAndView imagemAdd(ImagemForm imagemForm) {

		ModelAndView mv = new ModelAndView("imagem/imagemAdd");
		imagemForm = imagemService.imagemParametros(imagemForm);
		mv.addObject("imagemForm", imagemForm);
		mv.addObject("imagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/imagemCreate", method = RequestMethod.POST)
	public ModelAndView imagemCreate(@Valid ImagemForm imagemForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return imagemAdd(imagemForm);
		}

		if (imagemForm.getImagemPK() > 0) {
			return this.imagemSave(imagemForm, result, attributes);
			
		}
		
		try {
			imagemService.create(imagemForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("imagemNome")) {
			        ObjectError error = new ObjectError("imagemNome","Nome da Imagem já existente no cadastro.");
			        result.addError(error);			
			}
            
			return imagemAdd(imagemForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/imagemHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/imagemDelete/{id}", method = RequestMethod.GET)
	public ModelAndView imagemDelete(@PathVariable("id") long imagemPK, @Valid ImagemForm imagemForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/imagemHome");

		Imagem imagem = imagemService.getImagemByImagemPK(imagemPK);
		try {
			imagemService.delete(imagemPK);

			attributes.addFlashAttribute("mensagem", "Imagem excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Imagem não excluída. Existe(m) relacionamento(s) de Imagem ** "+ imagem.getImagemNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/imagemEdit/{id}", method = RequestMethod.GET)
	public ModelAndView imagemEdit(@PathVariable("id") Long imagemId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("imagem/imagemEdit");
		Imagem imagem = imagemService.getImagemByImagemPK(imagemId);
		ImagemForm imagemForm = imagemService.converteImagem(imagem);
		mv.addObject("imagemForm", imagemForm);
		mv.addObject("imagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/imagemHome")
	public ModelAndView imagemHome(@Valid ImagemByImagemForm imagemByImagemForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("imagem/imagemHome");

		List<Imagem> imagemList = new ArrayList<Imagem>();

		int imagemsTotal = 0;
		
		if (imagemByImagemForm.getSearchImagemNome() == null) {
			imagemByImagemForm.setSearchImagemNome("");
			imagemByImagemForm.setSearchImagemDescricao("");
			if (imagemByImagemForm.getImagemSortTipo() == null) {
				imagemByImagemForm.setImagemSortTipo("ImagemNome");
			}

		}

		if (imagemByImagemForm.getImagemSortTipo().equalsIgnoreCase("ImagemNome") || imagemByImagemForm.getImagemSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "imagemNome");

		} else if (imagemByImagemForm.getImagemSortTipo().equalsIgnoreCase("ImagemDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "imagemDescricao");

		}

		if ((!imagemByImagemForm.getSearchImagemNome().equalsIgnoreCase(""))) {
			imagemList = imagemService.getByImagemNome(imagemByImagemForm.getSearchImagemNome(), pageable);
			imagemsTotal = imagemService.getByImagemNome(imagemByImagemForm.getSearchImagemNome()).size();

		} else {
			imagemList = imagemService.getByImagemDescricao(imagemByImagemForm.getSearchImagemDescricao(), pageable);
			imagemsTotal = imagemService.getByImagemDescricao(imagemByImagemForm.getSearchImagemDescricao()).size();
		}

		Page<Imagem> imagemPage = new PageImpl<Imagem>(imagemList, pageable, imagemsTotal+1);

		mv.addObject("imagemPage", imagemPage);
		mv.addObject("page", imagemPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/imagemSave", method = RequestMethod.POST)
	public ModelAndView imagemSave(@Valid ImagemForm imagemForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return imagemAdd(imagemForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/imagemHome");

		try {
			imagemService.save(imagemForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("imagemNome")) {
			        ObjectError error = new ObjectError("imagemNome","Nome da Imagem já existente no cadastro.");
			        result.addError(error);			
			}
            return imagemAdd(imagemForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/imagemRelMenu", method = RequestMethod.GET)
	public ModelAndView imagemRelMenu() {

		ModelAndView mv = new ModelAndView("imagem/imagemRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/imagemRel001")
	public ModelAndView imagemRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("imagem/imagemRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/imagemView/{id}", method = RequestMethod.GET)
	public ModelAndView imagemView(@PathVariable("id") Long imagemId) {

		Imagem imagem = imagemService.getImagemByImagemPK(imagemId);
		ModelAndView mv = new ModelAndView("imagem/imagemView");
		ImagemForm imagemForm = imagemService.converteImagemView(imagem);
		mv.addObject("imagemForm", imagemForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}