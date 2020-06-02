package br.com.j4business.saga.planejamento.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.j4business.saga.acao.service.AcaoService;
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoCusto;
import br.com.j4business.saga.atributo.enumeration.AtributoPrazo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamento.model.PlanejamentoByPlanejamentoForm;
import br.com.j4business.saga.planejamento.model.PlanejamentoForm;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.planejamentoacao.service.PlanejamentoAcaoService;
import br.com.j4business.saga.planejamentoprocesso.service.PlanejamentoProcessoService;

@Controller
public class PlanejamentoController {

	@Autowired
	private AcaoService acaoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private PlanejamentoAcaoService planejamentoAcaoService;

	@Autowired
	private PlanejamentoProcessoService planejamentoProcessoService;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/planejamentoAdd", method = RequestMethod.GET)
	public ModelAndView planejamentoAdd(PlanejamentoForm planejamentoForm) {

		ModelAndView mv = new ModelAndView("planejamento/planejamentoAdd");
		planejamentoForm = planejamentoService.planejamentoParametros(planejamentoForm);
		mv.addObject("planejamentoForm", planejamentoForm);
		mv.addObject("planejamentoStatusValues", AtributoStatus.values());
		mv.addObject("planejamentoAprovacaoValues", AtributoAprovacao.values());
		mv.addObject("planejamentoCustoStatusValues", AtributoCusto.values());
		mv.addObject("planejamentoPrazoStatusValues", AtributoPrazo.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/planejamentoCreate", method = RequestMethod.POST)
	public ModelAndView planejamentoCreate(@Valid PlanejamentoForm planejamentoForm, BindingResult result, RedirectAttributes attributes) {

		Calendar planejamentoDataPrevistaInicioCalendar = Calendar.getInstance();
		Calendar planejamentoDataPrevistaTerminoCalendar = Calendar.getInstance();
		Calendar planejamentoDataRealInicioCalendar = Calendar.getInstance();
		Calendar planejamentoDataRealTerminoCalendar = Calendar.getInstance();
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			

			String planejamentoDataPrevistaInicioTemp = planejamentoForm.getPlanejamentoDataPrevistaInicio();
			planejamentoDataPrevistaInicioCalendar.setTime(sdf.parse(planejamentoDataPrevistaInicioTemp));
	
			String planejamentoDataPrevistaTerminoTemp = planejamentoForm.getPlanejamentoDataPrevistaTermino();
			planejamentoDataPrevistaTerminoCalendar.setTime(sdf.parse(planejamentoDataPrevistaTerminoTemp));
	
			String planejamentoDataRealInicioTemp = planejamentoForm.getPlanejamentoDataRealInicio();
			planejamentoDataRealInicioCalendar.setTime(sdf.parse(planejamentoDataRealInicioTemp));
	
			String planejamentoDataRealTerminoTemp = planejamentoForm.getPlanejamentoDataRealTermino();
			planejamentoDataRealTerminoCalendar.setTime(sdf.parse(planejamentoDataRealTerminoTemp));
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(planejamentoDataPrevistaTerminoCalendar.before(planejamentoDataPrevistaInicioCalendar)) {
	        ObjectError error = new ObjectError("planejamentoDataPrevistaTermino","Data Prevista de término deve ser maior ou igual a Data de Início.");
	        result.addError(error);			
            return planejamentoAdd(planejamentoForm);
	    }
		if(planejamentoDataRealInicioCalendar != null) {
			if(planejamentoDataRealInicioCalendar.before(planejamentoDataRealInicioCalendar)) {
		        ObjectError error = new ObjectError("planejamentoDataRealTermino","Data Real de término deve ser maior ou igual a Data de Início.");
		        result.addError(error);			
	            return planejamentoAdd(planejamentoForm);
		    }
	    }
		
		if (result.hasErrors()) {
			return planejamentoAdd(planejamentoForm);
		}

		if (planejamentoForm.getPlanejamentoPK() > 0) {
			return this.planejamentoSave(planejamentoForm, result, attributes);
			
		}
		
		try {
			planejamentoService.create(planejamentoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("planejamentoNome")) {
			        ObjectError error = new ObjectError("planejamentoNome","Nome do Planejamento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return planejamentoAdd(planejamentoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/planejamentoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView planejamentoDelete(@PathVariable("id") long planejamentoPK, @Valid PlanejamentoForm planejamentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/planejamentoHome");

		Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(planejamentoPK);
		try {
			planejamentoService.delete(planejamentoPK);

			attributes.addFlashAttribute("mensagem", "Planejamento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Planejamento não excluído. Existe(m) relacionamento(s) de Planejamento ** "+ planejamento.getPlanejamentoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/planejamentoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView planejamentoEdit(@PathVariable("id") Long planejamentoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamento/planejamentoEdit");
		Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(planejamentoPK);
		PlanejamentoForm planejamentoForm = planejamentoService.convertePlanejamento(planejamento);
		mv.addObject("planejamentoForm", planejamentoForm);
		mv.addObject("planejamentoStatusValues", AtributoStatus.values());
		mv.addObject("planejamentoAprovacaoValues", AtributoAprovacao.values());
		mv.addObject("planejamentoCustoStatusValues", AtributoCusto.values());
		mv.addObject("planejamentoPrazoStatusValues", AtributoPrazo.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoAcaoPageable = new PageRequest(0, 200, Direction.ASC, "acao.acaoNome");
		mv.addObject("planejamentoAcaoPage", planejamentoAcaoService.getByPlanejamentoPK(planejamentoPK, planejamentoAcaoPageable));
		Pageable planejamentoProcessoPageable = new PageRequest(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("planejamentoProcessoPage", planejamentoProcessoService.getByPlanejamentoPK(planejamentoPK, planejamentoProcessoPageable));
		Pageable acaoPageable = new PageRequest(0, 200, Direction.ASC, "acaoNome");
		mv.addObject("acaoPage", acaoService.getAcaoAll(acaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoHome", method = RequestMethod.GET)
	public ModelAndView planejamentoHome(@Valid PlanejamentoByPlanejamentoForm planejamentoByPlanejamentoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamento/planejamentoHome");
		
		List<Planejamento> planejamentoList = new ArrayList<Planejamento>();

		int planejamentoTotal = 0;
		
		if (planejamentoByPlanejamentoForm.getSearchPlanejamentoNome() == null) {
			planejamentoByPlanejamentoForm.setSearchPlanejamentoNome("");
			planejamentoByPlanejamentoForm.setSearchPlanejamentoDescricao("");
			if (planejamentoByPlanejamentoForm.getPlanejamentoSortTipo() == null) {
				planejamentoByPlanejamentoForm.setPlanejamentoSortTipo("PlanejamentoNome");
			}

		}

		if (planejamentoByPlanejamentoForm.getPlanejamentoSortTipo().equalsIgnoreCase("PlanejamentoNome") || planejamentoByPlanejamentoForm.getPlanejamentoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "planejamentoNome");

		} else if (planejamentoByPlanejamentoForm.getPlanejamentoSortTipo().equalsIgnoreCase("PlanejamentoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "planejamentoDescricao");

		}

		if ((!planejamentoByPlanejamentoForm.getSearchPlanejamentoNome().equalsIgnoreCase(""))) {
			planejamentoList = planejamentoService.getByPlanejamentoNome(planejamentoByPlanejamentoForm.getSearchPlanejamentoNome(), pageable);
			planejamentoTotal = planejamentoService.getByPlanejamentoNome(planejamentoByPlanejamentoForm.getSearchPlanejamentoNome()).size();

		} else {
			planejamentoList = planejamentoService.getByPlanejamentoDescricao(planejamentoByPlanejamentoForm.getSearchPlanejamentoDescricao(), pageable);
			planejamentoTotal = planejamentoService.getByPlanejamentoDescricao(planejamentoByPlanejamentoForm.getSearchPlanejamentoDescricao()).size();
		}

		Page<Planejamento> planejamentoPage = new PageImpl<Planejamento>(planejamentoList, pageable, planejamentoTotal+1);

		mv.addObject("planejamentoPage", planejamentoPage);
		mv.addObject("page", planejamentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoRelMenu", method = RequestMethod.GET)
	public ModelAndView planejamentoRelMenu() {

		ModelAndView mv = new ModelAndView("planejamento/planejamentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping(path = "/planejamentoRel001", method = RequestMethod.GET)
	public ModelAndView planejamentoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamento/planejamentoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoSave", method = RequestMethod.POST)
	public ModelAndView planejamentoSave(@Valid PlanejamentoForm planejamentoForm, BindingResult result, RedirectAttributes attributes) {

		Calendar planejamentoDataPrevistaInicioCalendar = Calendar.getInstance();
		Calendar planejamentoDataPrevistaTerminoCalendar = Calendar.getInstance();
		Calendar planejamentoDataRealInicioCalendar = Calendar.getInstance();
		Calendar planejamentoDataRealTerminoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String planejamentoDataPrevistaInicioTemp = planejamentoForm.getPlanejamentoDataPrevistaInicio();
			planejamentoDataPrevistaInicioCalendar.setTime(sdf.parse(planejamentoDataPrevistaInicioTemp));
	
			String planejamentoDataPrevistaTerminoTemp = planejamentoForm.getPlanejamentoDataPrevistaTermino();
			planejamentoDataPrevistaTerminoCalendar.setTime(sdf.parse(planejamentoDataPrevistaTerminoTemp));

			if (planejamentoForm.getPlanejamentoDataRealInicio() != "") {
				String planejamentoDataRealInicioTemp = planejamentoForm.getPlanejamentoDataRealInicio();
				planejamentoDataRealInicioCalendar.setTime(sdf.parse(planejamentoDataRealInicioTemp));
			}

			if (planejamentoForm.getPlanejamentoDataRealTermino() != "") {
				String planejamentoDataRealTerminoTemp = planejamentoForm.getPlanejamentoDataRealTermino();
				planejamentoDataRealTerminoCalendar.setTime(sdf.parse(planejamentoDataRealTerminoTemp));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(planejamentoDataPrevistaTerminoCalendar.before(planejamentoDataPrevistaInicioCalendar)) {
	        ObjectError error = new ObjectError("planejamentoDataPrevistaTermino","Data Prevista de término deve ser maior ou igual a Data Prevista de Início.");
	        result.addError(error);			
            return planejamentoAdd(planejamentoForm);
	    }
		if (planejamentoForm.getPlanejamentoDataRealInicio() != "" && planejamentoForm.getPlanejamentoDataRealTermino() != "") {
			if(planejamentoDataRealTerminoCalendar.before(planejamentoDataRealInicioCalendar)) {
		        ObjectError error = new ObjectError("planejamentoDataRealTermino","Data Real de término deve ser maior ou igual a Data Real de Início.");
		        result.addError(error);			
	            return planejamentoAdd(planejamentoForm);
		    }
	    }

		if (result.hasErrors()) {
			return planejamentoAdd(planejamentoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/planejamentoHome");

		try {
			planejamentoService.save(planejamentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("planejamentoNome")) {
			        ObjectError error = new ObjectError("planejamentoNome","Nome do Planejamento já existente no cadastro.");
			        result.addError(error);			
			}
            return planejamentoAdd(planejamentoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/planejamentoView/{id}", method = RequestMethod.GET)
	public ModelAndView planejamentoView(@PathVariable("id") Long planejamentoId) {

		Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(planejamentoId);
		ModelAndView mv = new ModelAndView("planejamento/planejamentoView");
		PlanejamentoForm planejamentoForm = planejamentoService.convertePlanejamentoView(planejamento);
		mv.addObject("planejamentoForm", planejamentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}