package br.com.j4business.saga.colaboradorhabilidade.controller;

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

import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.habilidade.model.HabilidadeForm;
import br.com.j4business.saga.habilidade.service.HabilidadeService;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidade;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidadeByColaboradorForm;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidadeForm;
import br.com.j4business.saga.colaboradorhabilidade.service.ColaboradorHabilidadeService;
import br.com.j4business.saga.fornecedor.service.FornecedorService;

@Controller
public class ColaboradorHabilidadeController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ColaboradorHabilidadeService colaboradorHabilidadeService;

	@Autowired
	private HabilidadeService habilidadeService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/colaboradorHabilidadeAdd", method = RequestMethod.GET)
	public ModelAndView colaboradorHabilidadeAdd(ColaboradorHabilidadeForm colaboradorHabilidadeForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorHabilidade/colaboradorHabilidadeAdd");
		colaboradorHabilidadeForm = colaboradorHabilidadeService.colaboradorHabilidadeParametros(colaboradorHabilidadeForm);
		mv.addObject("colaboradorHabilidadeForm", colaboradorHabilidadeForm);
		mv.addObject("colaboradorHabilidadePrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorHabilidadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		Pageable habilidadePageable = new PageRequest(0, 200, Direction.ASC, "habilidadeNome");
		mv.addObject("habilidadePage", habilidadeService.getHabilidadeAll(habilidadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorHabilidadeCreate", method = RequestMethod.POST)
	public ModelAndView colaboradorHabilidadeCreate(@Valid ColaboradorHabilidadeForm colaboradorHabilidadeForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorHabilidadeAdd(colaboradorHabilidadeForm,pageable);
		}

		if (colaboradorHabilidadeForm.getColaboradorHabilidadePK() > 0) {
			return this.colaboradorHabilidadeSave(colaboradorHabilidadeForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorHabilidadeService.create(colaboradorHabilidadeForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorHabilidadeUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Habilidade já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorHabilidadeAdd(colaboradorHabilidadeForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorHabilidadeHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/colaboradorHabilidadeDelete/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorHabilidadeDelete(@PathVariable("id") long colaboradorHabilidadeId, @Valid HabilidadeForm habilidadeForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorHabilidadeHome");
		
		
		ColaboradorHabilidade colaboradorHabilidade = colaboradorHabilidadeService.getColaboradorHabilidadeByColaboradorHabilidadePK(colaboradorHabilidadeId);
		try {
			colaboradorHabilidadeService.delete(colaboradorHabilidadeId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Habilidade excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Habilidade não excluído. Existe(m) relacionamento(s) de Colaborador/Habilidade ** "+ 
										  colaboradorHabilidade.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorHabilidade.getHabilidade().getHabilidadeNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorHabilidadeEdit/{colaboradorHabilidadePK}", method = RequestMethod.GET)
	public ModelAndView colaboradorHabilidadeEdit(@PathVariable("colaboradorHabilidadePK") Long colaboradorHabilidadePK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorHabilidade/colaboradorHabilidadeEdit");
		ColaboradorHabilidade colaboradorHabilidade = colaboradorHabilidadeService.getColaboradorHabilidadeByColaboradorHabilidadePK(colaboradorHabilidadePK);
		ColaboradorHabilidadeForm colaboradorHabilidadeForm = colaboradorHabilidadeService.converteColaboradorHabilidade(colaboradorHabilidade);
		mv.addObject("colaboradorHabilidadeForm", colaboradorHabilidadeForm);
		mv.addObject("colaboradorHabilidadePrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorHabilidadeStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		Pageable habilidadePageable = new PageRequest(0, 200, Direction.ASC, "habilidadeNome");
		mv.addObject("habilidadePage", habilidadeService.getHabilidadeAll(habilidadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/colaboradorHabilidadeHome", method = RequestMethod.GET)
	public ModelAndView colaboradorHabilidadeHome(@Valid ColaboradorHabilidadeByColaboradorForm colaboradorHabilidadeByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorHabilidade/colaboradorHabilidadeHome");
		
		List<ColaboradorHabilidade> colaboradorHabilidadeList = new ArrayList<ColaboradorHabilidade>();
		
		int colaboradorHabilidadeTotal = 0;
		
		if (colaboradorHabilidadeByColaboradorForm.getSearchHabilidadeNome() == null) {
			colaboradorHabilidadeByColaboradorForm.setSearchColaboradorNome("");
			colaboradorHabilidadeByColaboradorForm.setSearchHabilidadeNome("");
			if (colaboradorHabilidadeByColaboradorForm.getColaboradorHabilidadeSortTipo() == null) {
				colaboradorHabilidadeByColaboradorForm.setColaboradorHabilidadeSortTipo("HabilidadeNome");	
			}
			
		}

		if (colaboradorHabilidadeByColaboradorForm.getColaboradorHabilidadeSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorHabilidadeByColaboradorForm.getColaboradorHabilidadeSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","habilidade.habilidadeNome"); 
		
		} else if (colaboradorHabilidadeByColaboradorForm.getColaboradorHabilidadeSortTipo().equalsIgnoreCase("HabilidadeNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"habilidade.habilidadeNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorHabilidadeByColaboradorForm.getSearchHabilidadeNome().equalsIgnoreCase("")){
			colaboradorHabilidadeList = colaboradorHabilidadeService.getByHabilidadeNome(colaboradorHabilidadeByColaboradorForm.getSearchHabilidadeNome(),pageable);
			colaboradorHabilidadeTotal = colaboradorHabilidadeService.getByHabilidadeNome(colaboradorHabilidadeByColaboradorForm.getSearchHabilidadeNome()).size();
			
		} else {
			colaboradorHabilidadeList = colaboradorHabilidadeService.getByColaboradorNome(colaboradorHabilidadeByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorHabilidadeTotal = colaboradorHabilidadeService.getByColaboradorNome(colaboradorHabilidadeByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorHabilidade> colaboradorHabilidadePage = new PageImpl<ColaboradorHabilidade>(colaboradorHabilidadeList,pageable,colaboradorHabilidadeTotal+1);
		
		mv.addObject("colaboradorHabilidadePage", colaboradorHabilidadePage);
		mv.addObject("page",colaboradorHabilidadePage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorHabilidadeSave", method = RequestMethod.POST)
	public ModelAndView colaboradorHabilidadeSave(@Valid ColaboradorHabilidadeForm colaboradorHabilidadeForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorHabilidadeAdd(colaboradorHabilidadeForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorHabilidadeHome");

		try {
			colaboradorHabilidadeService.save(colaboradorHabilidadeForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorHabilidadeUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Habilidade já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorHabilidadeAdd(colaboradorHabilidadeForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorHabilidadeRelMenu", method = RequestMethod.GET)
	public ModelAndView colaboradorHabilidadeRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorHabilidade/colaboradorHabilidadeRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/colaboradorHabilidadeRel001")
	public ModelAndView colaboradorHabilidadeRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorHabilidade/colaboradorHabilidadeRel001");
		Pageable colaboradorHabilidadePageable = new PageRequest(0, 200, Direction.ASC, "colaborador.pessoaNome","habilidade.habilidadeNome");
		mv.addObject("colaboradorHabilidadePage", colaboradorHabilidadeService.getColaboradorHabilidadeAll(colaboradorHabilidadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorHabilidadeView/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorHabilidadeView(@PathVariable("id") Long colaboradorHabilidadeId) {

		ColaboradorHabilidade colaboradorHabilidade = colaboradorHabilidadeService.getColaboradorHabilidadeByColaboradorHabilidadePK(colaboradorHabilidadeId);
		ModelAndView mv = new ModelAndView("colaboradorHabilidade/colaboradorHabilidadeView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorHabilidade.getColaborador());
		HabilidadeForm habilidadeForm = habilidadeService.converteHabilidadeView(colaboradorHabilidade.getHabilidade());
		ColaboradorHabilidadeForm colaboradorHabilidadeForm = colaboradorHabilidadeService.converteColaboradorHabilidadeView(colaboradorHabilidade);
		mv.addObject("colaboradorHabilidadeForm", colaboradorHabilidadeForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("habilidadeForm", habilidadeForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}