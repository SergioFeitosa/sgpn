package br.com.j4business.saga.unidadeorganizacionalprocesso.controller;

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
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcesso;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcessoByUnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcessoForm;
import br.com.j4business.saga.unidadeorganizacionalprocesso.service.UnidadeorganizacionalProcessoService;

@Controller
public class UnidadeorganizacionalProcessoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UnidadeorganizacionalProcessoService unidadeorganizacionalProcessoService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/unidadeorganizacionalProcessoAdd", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalProcessoAdd(UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalProcesso/unidadeorganizacionalProcessoAdd");
		unidadeorganizacionalProcessoForm = unidadeorganizacionalProcessoService.unidadeorganizacionalProcessoParametros(unidadeorganizacionalProcessoForm);
		mv.addObject("unidadeorganizacionalProcessoForm", unidadeorganizacionalProcessoForm);
		mv.addObject("unidadeorganizacionalProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("unidadeorganizacionalProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalProcessoCreate", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalProcessoCreate(@Valid UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return unidadeorganizacionalProcessoAdd(unidadeorganizacionalProcessoForm,pageable);
		}

		if (unidadeorganizacionalProcessoForm.getUnidadeorganizacionalProcessoPK() > 0) {
			return this.unidadeorganizacionalProcessoSave(unidadeorganizacionalProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			unidadeorganizacionalProcessoService.create(unidadeorganizacionalProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalProcessoUnique")) {
			        ObjectError error = new ObjectError("unidadeorganizacionalNome","Relacionamento entre Unidade Organizacional e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return unidadeorganizacionalProcessoAdd(unidadeorganizacionalProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/unidadeorganizacionalProcessoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalProcessoDelete(@PathVariable("id") long unidadeorganizacionalProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalProcessoHome");
		
		
		UnidadeorganizacionalProcesso unidadeorganizacionalProcesso = unidadeorganizacionalProcessoService.getUnidadeorganizacionalProcessoByUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcessoId);
		try {
			unidadeorganizacionalProcessoService.delete(unidadeorganizacionalProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Unidadeorganizacional/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Unidadeorganizacional/Processo não excluído. Existe(m) relacionamento(s) de Unidadeorganizacional/Processo ** "+ 
										  unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalNome() +
										  " / " +
										  unidadeorganizacionalProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalProcessoEdit/{unidadeorganizacionalProcessoPK}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalProcessoEdit(@PathVariable("unidadeorganizacionalProcessoPK") Long unidadeorganizacionalProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalProcesso/unidadeorganizacionalProcessoEdit");
		UnidadeorganizacionalProcesso unidadeorganizacionalProcesso = unidadeorganizacionalProcessoService.getUnidadeorganizacionalProcessoByUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcessoPK);
		UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm = unidadeorganizacionalProcessoService.converteUnidadeorganizacionalProcesso(unidadeorganizacionalProcesso);
		mv.addObject("unidadeorganizacionalProcessoForm", unidadeorganizacionalProcessoForm);
		mv.addObject("unidadeorganizacionalProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("unidadeorganizacionalProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalPage", unidadeorganizacionalService.getUnidadeorganizacionalAll(unidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/unidadeorganizacionalProcessoHome", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalProcessoHome(@Valid UnidadeorganizacionalProcessoByUnidadeorganizacionalForm unidadeorganizacionalProcessoByUnidadeorganizacionalForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalProcesso/unidadeorganizacionalProcessoHome");
		
		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessoList = new ArrayList<UnidadeorganizacionalProcesso>();
		
		int unidadeorganizacionalProcessoTotal = 0;
		
		if (unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getSearchProcessoNome() == null) {
			unidadeorganizacionalProcessoByUnidadeorganizacionalForm.setSearchUnidadeorganizacionalNome("");
			unidadeorganizacionalProcessoByUnidadeorganizacionalForm.setSearchProcessoNome("");
			if (unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getUnidadeorganizacionalProcessoSortTipo() == null) {
				unidadeorganizacionalProcessoByUnidadeorganizacionalForm.setUnidadeorganizacionalProcessoSortTipo("ProcessoNome");	
			}
			
		}

		if (unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getUnidadeorganizacionalProcessoSortTipo().equalsIgnoreCase("UnidadeorganizacionalNome")
				|| unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getUnidadeorganizacionalProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"unidadeorganizacional.unidadeorganizacionalNome","processo.processoNome"); 
		
		} else if (unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getUnidadeorganizacionalProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","unidadeorganizacional.unidadeorganizacionalNome"); 

		}

		if ( ! unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getSearchProcessoNome().equalsIgnoreCase("")){
			unidadeorganizacionalProcessoList = unidadeorganizacionalProcessoService.getByProcessoNome(unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getSearchProcessoNome(),pageable);
			unidadeorganizacionalProcessoTotal = unidadeorganizacionalProcessoService.getByProcessoNome(unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getSearchProcessoNome()).size();
			
		} else {
			unidadeorganizacionalProcessoList = unidadeorganizacionalProcessoService.getByUnidadeorganizacionalNome(unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome(),pageable);
			unidadeorganizacionalProcessoTotal = unidadeorganizacionalProcessoService.getByUnidadeorganizacionalNome(unidadeorganizacionalProcessoByUnidadeorganizacionalForm.getSearchUnidadeorganizacionalNome()).size();

		} 
		
		Page<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessoPage = new PageImpl<UnidadeorganizacionalProcesso>(unidadeorganizacionalProcessoList,pageable,unidadeorganizacionalProcessoTotal+1);
		
		mv.addObject("unidadeorganizacionalProcessoPage", unidadeorganizacionalProcessoPage);
		mv.addObject("page",unidadeorganizacionalProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalProcessoSave", method = RequestMethod.POST)
	public ModelAndView unidadeorganizacionalProcessoSave(@Valid UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return unidadeorganizacionalProcessoAdd(unidadeorganizacionalProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/unidadeorganizacionalProcessoHome");

		try {
			unidadeorganizacionalProcessoService.save(unidadeorganizacionalProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("unidadeorganizacionalProcessoUnique")) {
		        ObjectError error = new ObjectError("unidadeorganizacionalNome","Relacionamento entre Unidade Organizacional e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return unidadeorganizacionalProcessoAdd(unidadeorganizacionalProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalProcessoRelMenu", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalProcesso/unidadeorganizacionalProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/unidadeorganizacionalProcessoRel001")
	public ModelAndView unidadeorganizacionalProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("unidadeorganizacionalProcesso/unidadeorganizacionalProcessoRel001");
		Pageable unidadeorganizacionalProcessoPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome","processo.processoNome");
		mv.addObject("unidadeorganizacionalProcessoPage", unidadeorganizacionalProcessoService.getUnidadeorganizacionalProcessoAll(unidadeorganizacionalProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/unidadeorganizacionalProcessoView/{id}", method = RequestMethod.GET)
	public ModelAndView unidadeorganizacionalProcessoView(@PathVariable("id") Long unidadeorganizacionalProcessoId) {

		UnidadeorganizacionalProcesso unidadeorganizacionalProcesso = unidadeorganizacionalProcessoService.getUnidadeorganizacionalProcessoByUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcessoId);
		ModelAndView mv = new ModelAndView("unidadeorganizacionalProcesso/unidadeorganizacionalProcessoView");
		UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm = unidadeorganizacionalProcessoService.converteUnidadeorganizacionalProcessoView(unidadeorganizacionalProcesso);
		UnidadeorganizacionalForm unidadeorganizacionalForm = unidadeorganizacionalService.converteUnidadeorganizacionalView(unidadeorganizacionalProcesso.getUnidadeorganizacional());
		ProcessoForm processoForm = processoService.converteProcessoView(unidadeorganizacionalProcesso.getProcesso());
		mv.addObject("unidadeorganizacionalProcessoForm", unidadeorganizacionalProcessoForm);
		mv.addObject("unidadeorganizacionalForm", unidadeorganizacionalForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}