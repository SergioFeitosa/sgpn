package br.com.j4business.saga.planejamentoacao.controller;

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
import br.com.j4business.saga.acao.model.AcaoForm;
import br.com.j4business.saga.acao.service.AcaoService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.planejamento.model.PlanejamentoForm;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcao;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcaoByPlanejamentoForm;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcaoForm;
import br.com.j4business.saga.planejamentoacao.service.PlanejamentoAcaoService;

@Controller
public class PlanejamentoAcaoController {

	@Autowired
	private AcaoService acaoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private PlanejamentoAcaoService planejamentoAcaoService;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/planejamentoAcaoAdd")
	public ModelAndView planejamentoAcaoAdd(PlanejamentoAcaoForm planejamentoAcaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoAcao/planejamentoAcaoAdd");
		planejamentoAcaoForm = planejamentoAcaoService.planejamentoAcaoParametros(planejamentoAcaoForm);
		mv.addObject("planejamentoAcaoForm", planejamentoAcaoForm);
		mv.addObject("planejamentoAcaoStatusValues", AtributoStatus.values());
		Pageable acaoPageable = PageRequest.of(0, 200, Direction.ASC, "acaoNome");
		mv.addObject("acaoPage", acaoService.getAcaoAll(acaoPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoPageable = PageRequest.of(0, 200, Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(planejamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/planejamentoAcaoCreate")
	public ModelAndView planejamentoAcaoCreate(@Valid PlanejamentoAcaoForm planejamentoAcaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return planejamentoAcaoAdd(planejamentoAcaoForm,pageable);
		}

		if (planejamentoAcaoForm.getPlanejamentoAcaoPK() > 0) {
			return this.planejamentoAcaoSave(planejamentoAcaoForm, result, attributes,pageable);
			
		}
		
		try {
			planejamentoAcaoService.create(planejamentoAcaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("planejamentoAcaoUnique")) {
			        ObjectError error = new ObjectError("planejamentoNome","Relacionamento entre Planejamento e Ação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return planejamentoAcaoAdd(planejamentoAcaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/planejamentoAcaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/planejamentoAcaoDelete/{id}")
	public ModelAndView planejamentoAcaoDelete(@PathVariable("id") long planejamentoAcaoId, @Valid PlanejamentoForm planejamentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/planejamentoAcaoHome");
		
		
		PlanejamentoAcao planejamentoAcao = planejamentoAcaoService.getPlanejamentoAcaoByPlanejamentoAcaoPK(planejamentoAcaoId);
		try {
			planejamentoAcaoService.delete(planejamentoAcaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Planejamento/Acao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Planejamento/Acao não excluído. Existe(m) relacionamento(s) de Planejamento/Acao ** "+ 
										  planejamentoAcao.getPlanejamento().getPlanejamentoNome() +
										  " / " +
										  planejamentoAcao.getAcao().getAcaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/planejamentoAcaoEdit/{planejamentoAcaoPK}")
	public ModelAndView planejamentoAcaoEdit(@PathVariable("planejamentoAcaoPK") Long planejamentoAcaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoAcao/planejamentoAcaoEdit");
		PlanejamentoAcao planejamentoAcao = planejamentoAcaoService.getPlanejamentoAcaoByPlanejamentoAcaoPK(planejamentoAcaoPK);
		PlanejamentoAcaoForm planejamentoAcaoForm = planejamentoAcaoService.convertePlanejamentoAcao(planejamentoAcao);
		mv.addObject("planejamentoAcaoForm", planejamentoAcaoForm);
		mv.addObject("planejamentoAcaoStatusValues", AtributoStatus.values());
		Pageable acaoPageable = PageRequest.of(0, 200, Direction.ASC, "acaoNome");
		mv.addObject("acaoPage", acaoService.getAcaoAll(acaoPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoPageable = PageRequest.of(0, 200, Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(planejamentoPageable));
		
		planejamentoAcao.getPlanejamentoAcaoSequencia();
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/planejamentoAcaoHome")
	public ModelAndView planejamentoAcaoHome(@Valid PlanejamentoAcaoByPlanejamentoForm planejamentoAcaoByPlanejamentoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoAcao/planejamentoAcaoHome");
		
		List<PlanejamentoAcao> planejamentoAcaoList = new ArrayList<PlanejamentoAcao>();
		
		int planejamentoAcaoTotal = 0;

		if (planejamentoAcaoByPlanejamentoForm.getSearchPlanejamentoNome() == null) {
			planejamentoAcaoByPlanejamentoForm.setSearchPlanejamentoNome("");
			planejamentoAcaoByPlanejamentoForm.setSearchAcaoNome("");
			if (planejamentoAcaoByPlanejamentoForm.getPlanejamentoAcaoSortTipo() == null) {
				planejamentoAcaoByPlanejamentoForm.setPlanejamentoAcaoSortTipo("PlanejamentoNome");	
			}
			
		}

		if (planejamentoAcaoByPlanejamentoForm.getPlanejamentoAcaoSortTipo().equalsIgnoreCase("PlanejamentoNome")
				|| planejamentoAcaoByPlanejamentoForm.getPlanejamentoAcaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"planejamento.planejamentoNome","planejamentoAcaoSequencia"); 
		
		} else if (planejamentoAcaoByPlanejamentoForm.getPlanejamentoAcaoSortTipo().equalsIgnoreCase("AcaoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"acao.acaoNome","planejamentoAcaoSequencia"); 

		} else if (planejamentoAcaoByPlanejamentoForm.getPlanejamentoAcaoSortTipo().equalsIgnoreCase("Sequencia")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"planejamentoAcaoSequencia","planejamento.planejamentoNome"); 

		}

		if ( ! planejamentoAcaoByPlanejamentoForm.getSearchPlanejamentoNome().equalsIgnoreCase("")){
			planejamentoAcaoList = planejamentoAcaoService.getByPlanejamentoNome(planejamentoAcaoByPlanejamentoForm.getSearchPlanejamentoNome(),pageable);
			planejamentoAcaoTotal = planejamentoAcaoService.getByPlanejamentoNome(planejamentoAcaoByPlanejamentoForm.getSearchPlanejamentoNome(),pageable).size();
			
		} else if ( ! planejamentoAcaoByPlanejamentoForm.getSearchAcaoNome().equalsIgnoreCase("")){
			planejamentoAcaoList = planejamentoAcaoService.getByAcaoNome(planejamentoAcaoByPlanejamentoForm.getSearchAcaoNome(),pageable);
			planejamentoAcaoTotal = planejamentoAcaoService.getByAcaoNome(planejamentoAcaoByPlanejamentoForm.getSearchAcaoNome(),pageable).size();

		} else {
			planejamentoAcaoList = planejamentoAcaoService.getPlanejamentoAcaoAll(pageable);
			planejamentoAcaoTotal = planejamentoAcaoService.getPlanejamentoAcaoAll(pageable).size();
		}
		
		Page<PlanejamentoAcao> planejamentoAcaoPage = new PageImpl<PlanejamentoAcao>(planejamentoAcaoList,pageable,planejamentoAcaoTotal+1);
		
		mv.addObject("planejamentoAcaoPage", planejamentoAcaoPage);
		mv.addObject("page",planejamentoAcaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/planejamentoAcaoSave")
	public ModelAndView planejamentoAcaoSave(@Valid PlanejamentoAcaoForm planejamentoAcaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return planejamentoAcaoAdd(planejamentoAcaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/planejamentoAcaoHome");

		try {
			planejamentoAcaoService.save(planejamentoAcaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("planejamentoAcaoUnique")) {
		        ObjectError error = new ObjectError("planejamentoNome","Relacionamento entre Planejamento e Ação já existente no cadastro.");
		        result.addError(error);			
		}
            return planejamentoAcaoAdd(planejamentoAcaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/planejamentoAcaoRelMenu")
	public ModelAndView planejamentoAcaoRelMenu() {

		ModelAndView mv = new ModelAndView("planejamentoAcao/planejamentoAcaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/planejamentoAcaoRel001")
	public ModelAndView planejamentoAcaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoAcao/planejamentoAcaoRel001");
		Pageable planejamentoAcaoPageable = PageRequest.of(0, 200, Direction.ASC, "planejamento.planejamentoNome","acao.acaoNome");
		mv.addObject("planejamentoAcaoPage", planejamentoAcaoService.getPlanejamentoAcaoAll(planejamentoAcaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/planejamentoAcaoView/{id}")
	public ModelAndView planejamentoAcaoView(@PathVariable("id") Long planejamentoAcaoId) {

		PlanejamentoAcao planejamentoAcao = planejamentoAcaoService.getPlanejamentoAcaoByPlanejamentoAcaoPK(planejamentoAcaoId);
		ModelAndView mv = new ModelAndView("planejamentoAcao/planejamentoAcaoView");
		PlanejamentoForm planejamentoForm = planejamentoService.convertePlanejamentoView(planejamentoAcao.getPlanejamento());
		AcaoForm  acaoForm = acaoService.converteAcaoView(planejamentoAcao.getAcao());
		PlanejamentoAcaoForm planejamentoAcaoForm = planejamentoAcaoService.convertePlanejamentoAcaoView(planejamentoAcao);
		mv.addObject("planejamentoAcaoForm", planejamentoAcaoForm);
		mv.addObject("planejamentoForm", planejamentoForm);
		mv.addObject("acaoForm", acaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}