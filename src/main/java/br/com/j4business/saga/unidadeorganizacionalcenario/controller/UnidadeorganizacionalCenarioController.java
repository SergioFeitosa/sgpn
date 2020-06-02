package br.com.j4business.saga.unidadeorganizacionalcenario.controller;

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
import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.cenario.model.CenarioForm;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenarioByUnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenarioForm;
import br.com.j4business.saga.unidadeorganizacionalcenario.service.UnidadeorganizacionalCenarioService;

@Controller
public class UnidadeorganizacionalCenarioController {

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private UnidadeorganizacionalCenarioService unidadeorganizacionalCenarioService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/unidadeorganizacionalCenarioAdd", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalCenarioAdd(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalCenario/unidadeorganizacionalCenarioAdd");
		unidadeorganizacionalCenarioForm = unidadeorganizacionalCenarioService.unidadeorganizacionalCenarioParametros(unidadeorganizacionalCenarioForm);
		mv.addObject("unidadeorganizacionalCenarioForm", unidadeorganizacionalCenarioForm);
		mv.addObject("unidadeorganizacionalCenarioPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("unidadeorganizacionalCenarioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalCenarioCreate", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalCenarioCreate(@Valid UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return unidadeorganizacionalCenarioAdd(unidadeorganizacionalCenarioForm,pageable);
		}

		if (unidadeorganizacionalCenarioForm.getUnidadeorganizacionalCenarioPK() > 0) {
			return this.unidadeorganizacionalCenarioSave(unidadeorganizacionalCenarioForm, result, attributes,pageable);
			
		}
		
		try {
			unidadeorganizacionalCenarioService.create(unidadeorganizacionalCenarioForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalCenarioUnique")) {
			        ObjectError error = new ObjectError("unidadeorganizacionalNome","Relacionamento entre Unidadeorganizacional e Cenario já existente no cadastro.");
			        result.addError(error);			
			}
            
			return unidadeorganizacionalCenarioAdd(unidadeorganizacionalCenarioForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalCenarioHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/unidadeorganizacionalCenarioDelete/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalCenarioDelete(@PathVariable("id") long unidadeorganizacionalCenarioId, @Valid CenarioForm cenarioForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalCenarioHome");
		
		
		UnidadeorganizacionalCenario unidadeorganizacionalCenario = unidadeorganizacionalCenarioService.getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenarioId);
		try {
			unidadeorganizacionalCenarioService.delete(unidadeorganizacionalCenarioId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Unidadeorganizacional/Cenario excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Unidadeorganizacional/Cenario não excluído. Existe(m) relacionamento(s) de Unidadeorganizacional/Cenario ** "+ 
										  unidadeorganizacionalCenario.getUnidadeorganizacional().getUnidadeorganizacionalNome() +
										  " / " +
										  unidadeorganizacionalCenario.getCenario().getCenarioNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalCenarioEdit/{unidadeorganizacionalCenarioPK}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalCenarioEdit(@PathVariable("unidadeorganizacionalCenarioPK") Long unidadeorganizacionalCenarioPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalCenario/unidadeorganizacionalCenarioEdit");
		UnidadeorganizacionalCenario unidadeorganizacionalCenario = unidadeorganizacionalCenarioService.getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenarioPK);
		UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm = unidadeorganizacionalCenarioService.converteUnidadeorganizacionalCenario(unidadeorganizacionalCenario);
		mv.addObject("unidadeorganizacionalCenarioForm", unidadeorganizacionalCenarioForm);
		mv.addObject("unidadeorganizacionalCenarioPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("unidadeorganizacionalCenarioStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/unidadeorganizacionalCenarioHome", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalCenarioHome(@Valid UnidadeorganizacionalCenarioByUnidadeorganizacionalForm unidadeorganizacionalCenarioByUnidadeorganizacionalForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalCenario/unidadeorganizacionalCenarioHome");
		
		List<UnidadeorganizacionalCenario> unidadeorganizacionalCenarioList = new ArrayList<UnidadeorganizacionalCenario>();
		
		int unidadeorganizacionalCenarioTotal = 0;
		
		if (unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getSearchCenarioNome() == null) {
			unidadeorganizacionalCenarioByUnidadeorganizacionalForm.setSearchUnidadeorganizacionalNome("");
			unidadeorganizacionalCenarioByUnidadeorganizacionalForm.setSearchCenarioNome("");
			if (unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getUnidadeorganizacionalCenarioSortTipo() == null) {
				unidadeorganizacionalCenarioByUnidadeorganizacionalForm.setUnidadeorganizacionalCenarioSortTipo("CenarioNome");	
			}
			
		}

		if (unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getUnidadeorganizacionalCenarioSortTipo().equalsIgnoreCase("UnidadeorganizacionalNome")
				|| unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getUnidadeorganizacionalCenarioSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"unidadeorganizacional.unidadeorganizacionalNome","cenario.cenarioNome"); 
		
		} else if (unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getUnidadeorganizacionalCenarioSortTipo().equalsIgnoreCase("CenarioNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"cenario.cenarioNome","unidadeorganizacional.unidadeorganizacionalNome"); 

		}

		if ( ! unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getSearchCenarioNome().equalsIgnoreCase("")){
			unidadeorganizacionalCenarioList = unidadeorganizacionalCenarioService.getByCenarioNome(unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getSearchCenarioNome(),pageable);
			unidadeorganizacionalCenarioTotal = unidadeorganizacionalCenarioService.getByCenarioNome(unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getSearchCenarioNome()).size();
			
		} else {
			unidadeorganizacionalCenarioList = unidadeorganizacionalCenarioService.getByUnidadeorganizacionalNome(unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome(),pageable);
			unidadeorganizacionalCenarioTotal = unidadeorganizacionalCenarioService.getByUnidadeorganizacionalNome(unidadeorganizacionalCenarioByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome()).size();

		} 
		
		Page<UnidadeorganizacionalCenario> unidadeorganizacionalCenarioPage = new PageImpl<UnidadeorganizacionalCenario>(unidadeorganizacionalCenarioList,pageable,unidadeorganizacionalCenarioTotal+1);
		
		mv.addObject("unidadeorganizacionalCenarioPage", unidadeorganizacionalCenarioPage);
		mv.addObject("page",unidadeorganizacionalCenarioPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalCenarioSave", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalCenarioSave(@Valid UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return unidadeorganizacionalCenarioAdd(unidadeorganizacionalCenarioForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalCenarioHome");

		try {
			unidadeorganizacionalCenarioService.save(unidadeorganizacionalCenarioForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalCenarioUnique")) {
		        ObjectError error = new ObjectError("unidadeorganizacionalNome","Relacionamento entre Unidadeorganizacional e Cenario já existente no cadastro.");
		        result.addError(error);			
		}
            return unidadeorganizacionalCenarioAdd(unidadeorganizacionalCenarioForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalCenarioRelMenu", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalCenarioRelMenu() {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalCenario/unidadeorganizacionalCenarioRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/unidadeorganizacionalCenarioRel001")
	public ModelAndView unidadeorganizacionalCenarioRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalCenario/unidadeorganizacionalCenarioRel001");
		Pageable unidadeorganizacionalCenarioPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome","cenario.cenarioNome");
		mv.addObject("unidadeorganizacionalCenarioPage", unidadeorganizacionalCenarioService.getUnidadeorganizacionalCenarioAll(unidadeorganizacionalCenarioPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalCenarioView/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalCenarioView(@PathVariable("id") Long unidadeorganizacionalCenarioId) {

		UnidadeorganizacionalCenario unidadeorganizacionalCenario = unidadeorganizacionalCenarioService.getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenarioId);
		ModelAndView mv = new ModelAndView("unidadeorganizacionalCenario/unidadeorganizacionalCenarioView");
		UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm = unidadeorganizacionalCenarioService.converteUnidadeorganizacionalCenarioView(unidadeorganizacionalCenario);
		UnidadeorganizacionalForm unidadeorganizacionalForm = unidadeorganizacionalService.converteUnidadeorganizacionalView(unidadeorganizacionalCenario.getUnidadeorganizacional());
		CenarioForm cenarioForm = cenarioService.converteCenarioView(unidadeorganizacionalCenario.getCenario());
		mv.addObject("unidadeorganizacionalCenarioForm", unidadeorganizacionalCenarioForm);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("cenarioForm", cenarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}