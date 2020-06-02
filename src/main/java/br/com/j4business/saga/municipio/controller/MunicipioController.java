package br.com.j4business.saga.municipio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.municipio.model.Municipio;
import br.com.j4business.saga.municipio.model.MunicipioByMunicipioForm;
import br.com.j4business.saga.municipio.model.MunicipioForm;
import br.com.j4business.saga.municipio.service.MunicipioService;

@Controller
public class MunicipioController {

	public static final int MAX_PAGE_ITEM_DISPLAY = 5;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/municipioAdd", method = RequestMethod.GET)
	public ModelAndView municipioAdd(MunicipioForm municipioForm) {

		ModelAndView mv = new ModelAndView("municipio/municipioAdd");
		municipioForm = municipioService.municipioParametros(municipioForm);
		mv.addObject("municipioForm", municipioForm);
		mv.addObject("municipioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/municipioCreate", method = RequestMethod.POST)
	public ModelAndView municipioCreate(@Valid MunicipioForm municipioForm, BindingResult result, RedirectAttributes attributes) {

		Municipio municipio = null;
		if (result.hasErrors()) {
			return municipioAdd(municipioForm);
		}
		if (municipioForm.getMunicipioPK() > 0) {
			municipio = municipioService.getMunicipioByMunicipioPK(municipioForm.getMunicipioPK());
			municipioService.save(municipio);
		} else {
			municipio = municipioService.create(municipioForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/municipioHome");
		attributes.addFlashAttribute("mensagem", "Municipio Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/municipioDelete/{id}", method = RequestMethod.GET)
	public ModelAndView municipioDelete(@PathVariable("id") long municipioPK, @Valid MunicipioForm municipioForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/municipioHome");

		Municipio municipio = municipioService.getMunicipioByMunicipioPK(municipioPK);
		try {
			municipioService.delete(municipioPK);

			attributes.addFlashAttribute("mensagem", "Municipio excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Municipio não excluído. Existe(m) relacionamento(s) de Municipio ** "+ municipio.getMunicipioNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
				
		return mv;
	}

	@RequestMapping(path = "/municipioEdit/{id}", method = RequestMethod.GET)
	public ModelAndView municipioEdit(@PathVariable("id") Long municipioId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("municipio/municipioEdit");
		Municipio municipio = municipioService.getMunicipioByMunicipioPK(municipioId);
		MunicipioForm municipioForm = municipioService.converteMunicipio(municipio);
		mv.addObject("municipioForm", municipioForm);
		mv.addObject("municipioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/municipioHome", method = RequestMethod.GET)
	public ModelAndView municipioHome(@Valid MunicipioByMunicipioForm municipioByMunicipioForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		
		ModelAndView mv = new ModelAndView("municipio/municipioHome");
		int municipiosTotal = 0;
		List<Municipio> municipioList = new ArrayList<Municipio>();

		if (municipioByMunicipioForm.getSearchMunicipioNome() == null) {
			municipioByMunicipioForm.setSearchMunicipioNome("");
			municipioByMunicipioForm.setSearchMunicipioCEP("");
			municipioByMunicipioForm.setSearchMunicipioUF("");
			if (municipioByMunicipioForm.getMunicipioSortTipo() == null) {
				municipioByMunicipioForm.setMunicipioSortTipo("MunicipioNome");
			}

		}

		if (municipioByMunicipioForm.getMunicipioSortTipo().equalsIgnoreCase("MunicipioNome") || municipioByMunicipioForm.getMunicipioSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "municipioNome");

		} else if (municipioByMunicipioForm.getMunicipioSortTipo().equalsIgnoreCase("MunicipioCEP")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "municipioCEP");

		} else if (municipioByMunicipioForm.getMunicipioSortTipo().equalsIgnoreCase("MunicipioUF")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "municipioUF");

		}

		if ((!municipioByMunicipioForm.getSearchMunicipioNome().equalsIgnoreCase(""))) {
			municipioList = municipioService.getByMunicipioNome(municipioByMunicipioForm.getSearchMunicipioNome(), pageable);
			municipiosTotal = municipioService.getByMunicipioNome(municipioByMunicipioForm.getSearchMunicipioNome()).size();

		} else 	if ((!municipioByMunicipioForm.getSearchMunicipioUF().equalsIgnoreCase(""))) {
			municipioList = municipioService.getByMunicipioUF(municipioByMunicipioForm.getSearchMunicipioUF(), pageable);
			municipiosTotal = municipioService.getByMunicipioUF(municipioByMunicipioForm.getSearchMunicipioUF()).size();

		} else {
			municipioList = municipioService.getByMunicipioCEP(municipioByMunicipioForm.getSearchMunicipioCEP(), pageable);
			municipiosTotal = municipioService.getByMunicipioCEP(municipioByMunicipioForm.getSearchMunicipioCEP()).size();
		}

		Page<Municipio> municipioPage = new PageImpl<Municipio>(municipioList, pageable, municipiosTotal+1);

		mv.addObject("municipioPage", municipioPage);
		mv.addObject("page", municipioPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/municipioSave", method = RequestMethod.POST)
	public ModelAndView municipioSave(@Valid MunicipioForm municipioForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return municipioAdd(municipioForm);
		}
		Municipio municipio = municipioService.converteMunicipioForm(municipioForm);
		ModelAndView mv = new ModelAndView("redirect:/municipioHome");
		municipioService.save(municipio);
		attributes.addFlashAttribute("mensagem", "Municipio Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/municipioRelMenu", method = RequestMethod.GET)
	public ModelAndView municipioRelMenu() {

		ModelAndView mv = new ModelAndView("municipio/municipioRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/municipioRel001")
	public ModelAndView municipioRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("municipio/municipioRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "municipioNome");
		mv.addObject("municipioPage", municipioService.getMunicipioAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/municipioView/{id}", method = RequestMethod.GET)
	public ModelAndView municipioView(@PathVariable("id") Long municipioId) {

		Municipio municipio = municipioService.getMunicipioByMunicipioPK(municipioId);
		ModelAndView mv = new ModelAndView("municipio/municipioView");
		MunicipioForm municipioForm = municipioService.converteMunicipioView(municipio);
		mv.addObject("municipioForm", municipioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}
}