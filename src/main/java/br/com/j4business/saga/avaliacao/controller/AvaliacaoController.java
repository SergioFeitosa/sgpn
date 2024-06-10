package br.com.j4business.saga.avaliacao.controller;

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

import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacao.model.AvaliacaoByAvaliacaoForm;
import br.com.j4business.saga.avaliacao.model.AvaliacaoForm;
import br.com.j4business.saga.avaliacao.service.AvaliacaoService;
import br.com.j4business.saga.avaliacaoprocesso.service.AvaliacaoProcessoService;

@Controller
public class AvaliacaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private AvaliacaoProcessoService avaliacaoProcessoService;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/avaliacaoAdd")
	public ModelAndView avaliacaoAdd(AvaliacaoForm avaliacaoForm) { 

		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoAdd");
		avaliacaoForm = avaliacaoService.avaliacaoParametros(avaliacaoForm);
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("avaliacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/avaliacaoCreate")
	public ModelAndView avaliacaoCreate(@Valid AvaliacaoForm avaliacaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return avaliacaoAdd(avaliacaoForm);
		}

		if (avaliacaoForm.getAvaliacaoPK() > 0) {
			return this.avaliacaoSave(avaliacaoForm, result, attributes);
			
		}
		
		try {
			avaliacaoService.create(avaliacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("avaliacaoNome")) {
			        ObjectError error = new ObjectError("avaliacaoNome","Nome da Avaliação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return avaliacaoAdd(avaliacaoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping(path = "/avaliacaoDelete/{id}")
	public ModelAndView avaliacaoDelete(@PathVariable("id") long avaliacaoPK, @Valid AvaliacaoForm avaliacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoHome");

		Avaliacao avaliacao = avaliacaoService.getAvaliacaoByAvaliacaoPK(avaliacaoPK);
		try {
			avaliacaoService.delete(avaliacaoPK);

			attributes.addFlashAttribute("mensagem", "Avaliação excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Avaliação não excluída. Existe(m) relacionamento(s) de Avaliação ** "+ avaliacao.getAvaliacaoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoEdit/{id}")
	public ModelAndView avaliacaoEdit(@PathVariable("id") Long avaliacaoPK) {

		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoEdit");
		Avaliacao avaliacao = avaliacaoService.getAvaliacaoByAvaliacaoPK(avaliacaoPK);
		AvaliacaoForm avaliacaoForm = avaliacaoService.converteAvaliacao(avaliacao);
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("avaliacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable avaliacaoProcessoPageable = PageRequest.of(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("avaliacaoProcessoPage", avaliacaoProcessoService.getByAvaliacaoPK(avaliacaoPK, avaliacaoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoHome")
	public ModelAndView avaliacaoHome(@Valid AvaliacaoByAvaliacaoForm avaliacaoByAvaliacaoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoHome");
		
		List<Avaliacao> avaliacaoList = new ArrayList<Avaliacao>();

		int avaliacaosTotal = 0;
		
		if (avaliacaoByAvaliacaoForm.getSearchAvaliacaoNome() == null) {
			avaliacaoByAvaliacaoForm.setSearchAvaliacaoNome("");
			avaliacaoByAvaliacaoForm.setSearchAvaliacaoDescricao("");
			if (avaliacaoByAvaliacaoForm.getAvaliacaoSortTipo() == null) {
				avaliacaoByAvaliacaoForm.setAvaliacaoSortTipo("AvaliacaoNome");
			}

		}

		if (avaliacaoByAvaliacaoForm.getAvaliacaoSortTipo().equalsIgnoreCase("AvaliacaoNome") || avaliacaoByAvaliacaoForm.getAvaliacaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "avaliacaoNome");

		} else if (avaliacaoByAvaliacaoForm.getAvaliacaoSortTipo().equalsIgnoreCase("AvaliacaoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "avaliacaoDescricao");

		}

		if ((!avaliacaoByAvaliacaoForm.getSearchAvaliacaoNome().equalsIgnoreCase(""))) {
			avaliacaoList = avaliacaoService.getByAvaliacaoNome(avaliacaoByAvaliacaoForm.getSearchAvaliacaoNome(), pageable);
			avaliacaosTotal = avaliacaoService.getByAvaliacaoNome(avaliacaoByAvaliacaoForm.getSearchAvaliacaoNome()).size();

		} else {
			avaliacaoList = avaliacaoService.getByAvaliacaoDescricao(avaliacaoByAvaliacaoForm.getSearchAvaliacaoDescricao(), pageable);
			avaliacaosTotal = avaliacaoService.getByAvaliacaoDescricao(avaliacaoByAvaliacaoForm.getSearchAvaliacaoDescricao()).size();
		}

		Page<Avaliacao> pageAvaliacaoPage = new PageImpl<Avaliacao>(avaliacaoList, pageable, avaliacaosTotal+1);

		mv.addObject("avaliacaoPage", pageAvaliacaoPage);
		mv.addObject("page", pageAvaliacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoRelMenu")
	public ModelAndView avaliacaoRelMenu() {

		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping(path = "/avaliacaoRel001")
	public ModelAndView avaliacaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoRel002")
	public ModelAndView avaliacaoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoRel002");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/avaliacaoSave")
	public ModelAndView avaliacaoSave(@Valid AvaliacaoForm avaliacaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return avaliacaoAdd(avaliacaoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoHome");

		try {
			avaliacaoService.save(avaliacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("avaliacaoNome")) {
			        ObjectError error = new ObjectError("avaliacaoNome","Nome da Avaliação já existente no cadastro.");
			        result.addError(error);			
			}
            return avaliacaoAdd(avaliacaoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/avaliacaoView/{id}")
	public ModelAndView avaliacaoView(@PathVariable("id") Long avaliacaoId) {

		Avaliacao avaliacao = avaliacaoService.getAvaliacaoByAvaliacaoPK(avaliacaoId);
		ModelAndView mv = new ModelAndView("avaliacao/avaliacaoView");
		AvaliacaoForm avaliacaoForm = avaliacaoService.converteAvaliacaoView(avaliacao);
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}