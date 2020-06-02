package br.com.j4business.saga.estruturafisicaunidadeorganizacional.controller;

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

import br.com.j4business.saga.estruturafisica.model.EstruturafisicaForm;
import br.com.j4business.saga.estruturafisica.service.EstruturafisicaService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcesso;
import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacionalByEstruturafisicaForm;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacionalForm;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.service.EstruturafisicaUnidadeorganizacionalService;

@Controller
public class EstruturafisicaUnidadeorganizacionalController {

	@Autowired
	private EstruturafisicaService estruturafisicaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private EstruturafisicaUnidadeorganizacionalService estruturafisicaUnidadeorganizacionalService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalAdd", method = RequestMethod.GET)
	public ModelAndView estruturafisicaUnidadeorganizacionalAdd(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("estruturafisicaUnidadeorganizacional/estruturafisicaUnidadeorganizacionalAdd");
		estruturafisicaUnidadeorganizacionalForm = estruturafisicaUnidadeorganizacionalService.estruturafisicaUnidadeorganizacionalParametros(estruturafisicaUnidadeorganizacionalForm);
		mv.addObject("estruturafisicaUnidadeorganizacionalForm", estruturafisicaUnidadeorganizacionalForm);
		mv.addObject("estruturafisicaUnidadeorganizacionalPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("estruturafisicaUnidadeorganizacionalStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable estruturafisicaPageable = new PageRequest(0, 200, Direction.ASC, "estruturafisicaNome");
		mv.addObject("estruturafisicaPage", estruturafisicaService.getEstruturafisicaAll(estruturafisicaPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalCreate", method = RequestMethod.POST)
	public ModelAndView estruturafisicaUnidadeorganizacionalCreate(@Valid EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return estruturafisicaUnidadeorganizacionalAdd(estruturafisicaUnidadeorganizacionalForm,pageable);
		}

		if (estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaUnidadeorganizacionalPK() > 0) {
			return this.estruturafisicaUnidadeorganizacionalSave(estruturafisicaUnidadeorganizacionalForm, result, attributes,pageable);
			
		}
		
		try {
			estruturafisicaUnidadeorganizacionalService.create(estruturafisicaUnidadeorganizacionalForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("estruturafisicaUnidadeorganizacionalUnique")) {
			        ObjectError error = new ObjectError("estruturafisicaNome","Relacionamento entre Estrutura Física e Unidade Organizacional já existente no cadastro.");
			        result.addError(error);			
			}
            
			return estruturafisicaUnidadeorganizacionalAdd(estruturafisicaUnidadeorganizacionalForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/estruturafisicaUnidadeorganizacionalHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalDelete/{id}", method = RequestMethod.GET)
	public ModelAndView estruturafisicaUnidadeorganizacionalDelete(@PathVariable("id") long estruturafisicaUnidadeorganizacionalId, @Valid UnidadeorganizacionalForm unidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/estruturafisicaUnidadeorganizacionalHome");
		
		
		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional = estruturafisicaUnidadeorganizacionalService.getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacionalId);
		try {
			estruturafisicaUnidadeorganizacionalService.delete(estruturafisicaUnidadeorganizacionalId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Estrutura Física / Unidade Organizacional excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Estrutura Física / Unidade Organizacional não excluído. Existe(m) relacionamento(s) de Estrutura Física / Unidade Organizacional ** "+ 
										  estruturafisicaUnidadeorganizacional.getEstruturafisica().getEstruturafisicaNome() +
										  " / " +
										  estruturafisicaUnidadeorganizacional.getUnidadeorganizacional().getUnidadeorganizacionalNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalEdit/{estruturafisicaUnidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView estruturafisicaUnidadeorganizacionalEdit(@PathVariable("estruturafisicaUnidadeorganizacionalPK") Long estruturafisicaUnidadeorganizacionalPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("estruturafisicaUnidadeorganizacional/estruturafisicaUnidadeorganizacionalEdit");
		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional = estruturafisicaUnidadeorganizacionalService.getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacionalPK);
		EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm = estruturafisicaUnidadeorganizacionalService.converteEstruturafisicaUnidadeorganizacional(estruturafisicaUnidadeorganizacional);
		mv.addObject("estruturafisicaUnidadeorganizacionalForm", estruturafisicaUnidadeorganizacionalForm);
		mv.addObject("estruturafisicaUnidadeorganizacionalPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("estruturafisicaUnidadeorganizacionalStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable estruturafisicaPageable = new PageRequest(0, 200, Direction.ASC, "estruturafisicaNome");
		mv.addObject("estruturafisicaPage", estruturafisicaService.getEstruturafisicaAll(estruturafisicaPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalHome", method = RequestMethod.GET)
	public ModelAndView estruturafisicaUnidadeorganizacionalHome(@Valid EstruturafisicaUnidadeorganizacionalByEstruturafisicaForm estruturafisicaUnidadeorganizacionalByEstruturafisicaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("estruturafisicaUnidadeorganizacional/estruturafisicaUnidadeorganizacionalHome");
		
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		
		int estruturafisicaUnidadeorganizacionalTotal = 0;
		
		if (estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getSearchUnidadeorganizacionalNome() == null) {
			estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.setSearchEstruturafisicaNome("");
			estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.setSearchUnidadeorganizacionalNome("");
			if (estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getEstruturafisicaUnidadeorganizacionalSortTipo() == null) {
				estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.setEstruturafisicaUnidadeorganizacionalSortTipo("UnidadeorganizacionalNome");	
			}
			
		}

		if (estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getEstruturafisicaUnidadeorganizacionalSortTipo().equalsIgnoreCase("EstruturafisicaNome")
				|| estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getEstruturafisicaUnidadeorganizacionalSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"estruturafisica.estruturafisicaNome","unidadeorganizacional.unidadeorganizacionalNome"); 
		
		} else if (estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getEstruturafisicaUnidadeorganizacionalSortTipo().equalsIgnoreCase("UnidadeorganizacionalNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"unidadeorganizacional.unidadeorganizacionalNome","estruturafisica.estruturafisicaNome"); 

		}

		if ( ! estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getSearchUnidadeorganizacionalNome().equalsIgnoreCase("")){
			estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalNome(estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getSearchUnidadeorganizacionalNome(),pageable);
			estruturafisicaUnidadeorganizacionalTotal = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalNome(estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getSearchUnidadeorganizacionalNome()).size();
			
		} else {
			estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaNome(estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getSearchEstruturafisicaNome(),pageable);
			estruturafisicaUnidadeorganizacionalTotal = estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaNome(estruturafisicaUnidadeorganizacionalByEstruturafisicaForm.getSearchEstruturafisicaNome()).size();

		} 
		
		Page<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalPage = new PageImpl<EstruturafisicaUnidadeorganizacional>(estruturafisicaUnidadeorganizacionalList,pageable,estruturafisicaUnidadeorganizacionalTotal+1);
		
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalPage);
		mv.addObject("page",estruturafisicaUnidadeorganizacionalPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalSave", method = RequestMethod.POST)
	public ModelAndView estruturafisicaUnidadeorganizacionalSave(@Valid EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return estruturafisicaUnidadeorganizacionalAdd(estruturafisicaUnidadeorganizacionalForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/estruturafisicaUnidadeorganizacionalHome");

		try {
			estruturafisicaUnidadeorganizacionalService.save(estruturafisicaUnidadeorganizacionalForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("estruturafisicaUnidadeorganizacionalUnique")) {
			        ObjectError error = new ObjectError("estruturafisicaNome","Relacionamento entre Estrutura Física e Unidade Organizacional já existente no cadastro.");
			        result.addError(error);			
			}
            return estruturafisicaUnidadeorganizacionalAdd(estruturafisicaUnidadeorganizacionalForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalRelMenu", method = RequestMethod.GET)
	public ModelAndView estruturafisicaUnidadeorganizacionalRelMenu() {

		ModelAndView mv = new ModelAndView("estruturafisicaUnidadeorganizacional/estruturafisicaUnidadeorganizacionalRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/estruturafisicaUnidadeorganizacionalRel001")
	public ModelAndView estruturafisicaUnidadeorganizacionalRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("estruturafisicaUnidadeorganizacional/estruturafisicaUnidadeorganizacionalRel001");
		Pageable estruturafisicaUnidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "estruturafisica.estruturafisicaNome", "unidadeorganizacional.unidadeorganizacionalNome");
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaUnidadeorganizacionalAll(estruturafisicaUnidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/estruturafisicaUnidadeorganizacionalView/{id}", method = RequestMethod.GET)
	public ModelAndView estruturafisicaUnidadeorganizacionalView(@PathVariable("id") Long estruturafisicaUnidadeorganizacionalId) {

		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional = estruturafisicaUnidadeorganizacionalService.getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacionalId);
		ModelAndView mv = new ModelAndView("estruturafisicaUnidadeorganizacional/estruturafisicaUnidadeorganizacionalView");
		EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm = estruturafisicaUnidadeorganizacionalService.converteEstruturafisicaUnidadeorganizacionalView(estruturafisicaUnidadeorganizacional);
		EstruturafisicaForm estruturafisicaForm = estruturafisicaService.converteEstruturafisicaView(estruturafisicaUnidadeorganizacional.getEstruturafisica());
		UnidadeorganizacionalForm unidadeorganizacionalForm = unidadeorganizacionalService.converteUnidadeorganizacionalView(estruturafisicaUnidadeorganizacional.getUnidadeorganizacional());
		mv.addObject("estruturafisicaUnidadeorganizacionalForm", estruturafisicaUnidadeorganizacionalForm);
		mv.addObject("estruturafisicaForm", estruturafisicaForm);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}