package br.com.j4business.saga.pais.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.pais.model.Pais;
import br.com.j4business.saga.pais.model.PaisByPaisForm;
import br.com.j4business.saga.pais.model.PaisForm;
import br.com.j4business.saga.pais.service.PaisService;

@Controller
public class PaisController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private PaisService paisService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/paisAdd")
	public ModelAndView paisAdd(PaisForm paisForm) {

		ModelAndView mv = new ModelAndView("pais/paisAdd");
		paisForm = paisService.paisParametros(paisForm);
		mv.addObject("paisForm", paisForm);
		mv.addObject("paisStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/paisCreate")
	public ModelAndView paisCreate(@Valid PaisForm paisForm, BindingResult result, RedirectAttributes attributes) {

		Pais pais = null;
		if (result.hasErrors()) {
			return paisAdd(paisForm);
		}
		if (paisForm.getPaisPK() > 0) {
			pais = paisService.getPaisByPaisPK(paisForm.getPaisPK());
			paisService.save(pais);
		} else {
			pais = paisService.create(paisForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/paisHome");
		attributes.addFlashAttribute("mensagem", "Pais Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/paisDelete/{id}")
	public ModelAndView paisDelete(@PathVariable("id") long paisPK, @Valid PaisForm paisForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/paisHome");

		Pais pais = paisService.getPaisByPaisPK(paisPK);
		try {
			paisService.delete(paisPK);

			attributes.addFlashAttribute("mensagem", "País excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "País não excluído. Existe(m) relacionamento(s) de País ** "+ pais.getPaisNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/paisEdit/{id}")
	public ModelAndView paisEdit(@PathVariable("id") Long paisId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("pais/paisEdit");
		Pais pais = paisService.getPaisByPaisPK(paisId);
		PaisForm paisForm = paisService.convertePais(pais);
		mv.addObject("paisForm", paisForm);
		mv.addObject("paisStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/paisHome")
	public ModelAndView paisHome(@Valid PaisByPaisForm paisByPaisForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("pais/paisHome");

		List<Pais> paisList = new ArrayList<Pais>();
		
		int paisesTotal = 0; 
		
		if (paisByPaisForm.getSearchPaisNome() == null) {
			paisByPaisForm.setSearchPaisNome("");
			paisByPaisForm.setSearchPaisSigla("");
			if (paisByPaisForm.getPaisSortTipo() == null) {
				paisByPaisForm.setPaisSortTipo("PaisNome");
			}

		}

		if (paisByPaisForm.getPaisSortTipo().equalsIgnoreCase("PaisNome") || paisByPaisForm.getPaisSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "paisNome");

		} else if (paisByPaisForm.getPaisSortTipo().equalsIgnoreCase("PaisSigla")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "paisSigla");

		}

		if ((!paisByPaisForm.getSearchPaisNome().equalsIgnoreCase(""))) {
			paisList = paisService.getByPaisNome(paisByPaisForm.getSearchPaisNome(), pageable);
			paisesTotal = paisService.getByPaisNome(paisByPaisForm.getSearchPaisNome()).size();

		} else {
			paisList = paisService.getByPaisSigla(paisByPaisForm.getSearchPaisSigla(), pageable);
			paisesTotal = paisService.getByPaisSigla(paisByPaisForm.getSearchPaisSigla()).size();
		}

		Page<Pais> paisPage = new PageImpl<Pais>(paisList, pageable, paisesTotal+1);

		mv.addObject("paisPage", paisPage);
		mv.addObject("page", paisPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/paisSave")
	public ModelAndView paisSave(@Valid PaisForm paisForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return paisAdd(paisForm);
		}
		Pais pais = paisService.convertePaisForm(paisForm);
		ModelAndView mv = new ModelAndView("redirect:/paisHome");
		paisService.save(pais);
		attributes.addFlashAttribute("mensagem", "Pais Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/paisRelMenu")
	public ModelAndView paisRelMenu() {

		ModelAndView mv = new ModelAndView("pais/paisRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping(path = "/paisRel001")
	public ModelAndView paisRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("pais/paisRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "paisNome");
		mv.addObject("paisPage", paisService.getPaisAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/paisView/{id}")
	public ModelAndView paisView(@PathVariable("id") Long paisId) {

		Pais pais = paisService.getPaisByPaisPK(paisId);
		ModelAndView mv = new ModelAndView("pais/paisView");
		PaisForm paisForm = paisService.convertePaisView(pais);
		mv.addObject("paisForm", paisForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}