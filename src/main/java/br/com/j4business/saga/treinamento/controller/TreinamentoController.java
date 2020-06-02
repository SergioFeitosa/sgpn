package br.com.j4business.saga.treinamento.controller;

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
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoCusto;
import br.com.j4business.saga.atributo.enumeration.AtributoPrazo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.model.TreinamentoByTreinamentoForm;
import br.com.j4business.saga.treinamento.model.TreinamentoForm;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.treinamentoimagem.service.TreinamentoImagemService;
import br.com.j4business.saga.treinamentotexto.service.TreinamentoTextoService;
import br.com.j4business.saga.treinamentovideo.service.TreinamentoVideoService;

@Controller
public class TreinamentoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private TreinamentoImagemService treinamentoImagemService;

	@Autowired
	private TreinamentoTextoService treinamentoTextoService;

	@Autowired
	private TreinamentoVideoService treinamentoVideoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/treinamentoAdd", method = RequestMethod.GET)
	public ModelAndView treinamentoAdd(TreinamentoForm treinamentoForm) {

		ModelAndView mv = new ModelAndView("treinamento/treinamentoAdd");
		treinamentoForm = treinamentoService.treinamentoParametros(treinamentoForm);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("treinamentoStatusValues", AtributoStatus.values());
		mv.addObject("treinamentoAprovacaoValues", AtributoAprovacao.values());
		mv.addObject("treinamentoCustoStatusValues", AtributoCusto.values());
		mv.addObject("treinamentoPrazoStatusValues", AtributoPrazo.values());
		
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoCreate", method = RequestMethod.POST)
	public ModelAndView treinamentoCreate(@Valid TreinamentoForm treinamentoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return treinamentoAdd(treinamentoForm);
		}

		if (treinamentoForm.getTreinamentoPK() > 0) {
			return this.treinamentoSave(treinamentoForm, result, attributes);
			
		}
		
		try {
			treinamentoService.create(treinamentoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoNome")) {
			        ObjectError error = new ObjectError("treinamentoNome","Nome do Treinamento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return treinamentoAdd(treinamentoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/treinamentoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoDelete(@PathVariable("id") long treinamentoPK, @Valid TreinamentoForm treinamentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/treinamentoHome");

		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(treinamentoPK);
		try {
			treinamentoService.delete(treinamentoPK);

			attributes.addFlashAttribute("mensagem", "Treinamento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Treinamento não excluído. Existe(m) relacionamento(s) de Treinamento ** "+ treinamento.getTreinamentoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoEdit(@PathVariable("id") Long treinamentoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamento/treinamentoEdit");
		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(treinamentoId);
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamento(treinamento);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("treinamentoStatusValues", AtributoStatus.values());
		mv.addObject("treinamentoAprovacaoValues", AtributoAprovacao.values());
		mv.addObject("treinamentoCustoStatusValues", AtributoCusto.values());
		mv.addObject("treinamentoPrazoStatusValues", AtributoPrazo.values());
		
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("treinamentoVideoList", treinamentoVideoService.getTreinamentoVideoAtivoByTreinamentoPK(treinamentoId));
		mv.addObject("treinamentoImagemList", treinamentoImagemService.getTreinamentoImagemAtivoByTreinamentoPK(treinamentoId));
		mv.addObject("treinamentoTextoList", treinamentoTextoService.getTreinamentoTextoAtivoByTreinamentoPK(treinamentoId));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/treinamentoHome")
	public ModelAndView treinamentoHome(@Valid TreinamentoByTreinamentoForm treinamentoByTreinamentoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamento/treinamentoHome");

		List<Treinamento> treinamentoList = new ArrayList<Treinamento>();

		int treinamentosTotal = 0;
		
		if (treinamentoByTreinamentoForm.getSearchTreinamentoNome() == null) {
			treinamentoByTreinamentoForm.setSearchTreinamentoNome("");
			treinamentoByTreinamentoForm.setSearchTreinamentoDescricao("");
			if (treinamentoByTreinamentoForm.getTreinamentoSortTipo() == null) {
				treinamentoByTreinamentoForm.setTreinamentoSortTipo("TreinamentoNome");
			}

		}

		if (treinamentoByTreinamentoForm.getTreinamentoSortTipo().equalsIgnoreCase("TreinamentoNome") || treinamentoByTreinamentoForm.getTreinamentoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "treinamentoNome");

		} else if (treinamentoByTreinamentoForm.getTreinamentoSortTipo().equalsIgnoreCase("TreinamentoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "treinamentoDescricao");

		}

		if ((!treinamentoByTreinamentoForm.getSearchTreinamentoNome().equalsIgnoreCase(""))) {
			treinamentoList = treinamentoService.getByTreinamentoNome(treinamentoByTreinamentoForm.getSearchTreinamentoNome(), pageable);
			treinamentosTotal = treinamentoService.getByTreinamentoNome(treinamentoByTreinamentoForm.getSearchTreinamentoNome()).size();

		} else {
			treinamentoList = treinamentoService.getByTreinamentoDescricao(treinamentoByTreinamentoForm.getSearchTreinamentoDescricao(), pageable);
			treinamentosTotal = treinamentoService.getByTreinamentoDescricao(treinamentoByTreinamentoForm.getSearchTreinamentoDescricao()).size();
		}

		Page<Treinamento> treinamentoPage = new PageImpl<Treinamento>(treinamentoList, pageable, treinamentosTotal+1);

		mv.addObject("treinamentoPage", treinamentoPage);
		mv.addObject("page", treinamentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoSave", method = RequestMethod.POST)
	public ModelAndView treinamentoSave(@Valid TreinamentoForm treinamentoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return treinamentoAdd(treinamentoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/treinamentoHome");

		try {
			treinamentoService.save(treinamentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoNome")) {
			        ObjectError error = new ObjectError("treinamentoNome","Nome do Treinamento já existente no cadastro.");
			        result.addError(error);			
			}
            return treinamentoAdd(treinamentoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/treinamentoRelMenu", method = RequestMethod.GET)
	public ModelAndView treinamentoRelMenu() {

		ModelAndView mv = new ModelAndView("treinamento/treinamentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/treinamentoRel001")
	public ModelAndView treinamentoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamento/treinamentoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoView/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoView(@PathVariable("id") Long treinamentoId) {

		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(treinamentoId);
		ModelAndView mv = new ModelAndView("treinamento/treinamentoView");
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamentoView(treinamento);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}