package br.com.j4business.saga.colaboradorcurso.controller;

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
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.curso.model.CursoForm;
import br.com.j4business.saga.curso.service.CursoService;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCursoByColaboradorForm;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCursoForm;
import br.com.j4business.saga.colaboradorcurso.service.ColaboradorCursoService;

@Controller
public class ColaboradorCursoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ColaboradorCursoService colaboradorCursoService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/colaboradorCursoAdd", method = RequestMethod.GET)
	public ModelAndView colaboradorCursoAdd(ColaboradorCursoForm colaboradorCursoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCurso/colaboradorCursoAdd");
		colaboradorCursoForm = colaboradorCursoService.colaboradorCursoParametros(colaboradorCursoForm);
		mv.addObject("colaboradorCursoForm", colaboradorCursoForm);
		mv.addObject("colaboradorCursoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorCursoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cursoPageable = new PageRequest(0, 200, Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(cursoPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorCursoCreate", method = RequestMethod.POST)
	public ModelAndView colaboradorCursoCreate(@Valid ColaboradorCursoForm colaboradorCursoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorCursoAdd(colaboradorCursoForm,pageable);
		}

		if (colaboradorCursoForm.getColaboradorCursoPK() > 0) {
			return this.colaboradorCursoSave(colaboradorCursoForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorCursoService.create(colaboradorCursoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorCursoUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Certificação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorCursoAdd(colaboradorCursoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorCursoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/colaboradorCursoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorCursoDelete(@PathVariable("id") long colaboradorCursoId, @Valid CursoForm cursoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorCursoHome");
		
		
		ColaboradorCurso colaboradorCurso = colaboradorCursoService.getColaboradorCursoByColaboradorCursoPK(colaboradorCursoId);
		try {
			colaboradorCursoService.delete(colaboradorCursoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Curso excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Curso não excluído. Existe(m) relacionamento(s) de Colaborador/Curso ** "+ 
										  colaboradorCurso.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorCurso.getCurso().getCursoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorCursoEdit/{colaboradorCursoPK}", method = RequestMethod.GET)
	public ModelAndView colaboradorCursoEdit(@PathVariable("colaboradorCursoPK") Long colaboradorCursoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCurso/colaboradorCursoEdit");
		ColaboradorCurso colaboradorCurso = colaboradorCursoService.getColaboradorCursoByColaboradorCursoPK(colaboradorCursoPK);
		ColaboradorCursoForm colaboradorCursoForm = colaboradorCursoService.converteColaboradorCurso(colaboradorCurso);
		mv.addObject("colaboradorCursoForm", colaboradorCursoForm);
		mv.addObject("colaboradorCursoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorCursoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable cursoPageable = new PageRequest(0, 200, Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(cursoPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/colaboradorCursoHome", method = RequestMethod.GET)
	public ModelAndView colaboradorCursoHome(@Valid ColaboradorCursoByColaboradorForm colaboradorCursoByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCurso/colaboradorCursoHome");
		
		List<ColaboradorCurso> colaboradorCursoList = new ArrayList<ColaboradorCurso>();
		
		int colaboradorCursosTotal = 0;
		
		if (colaboradorCursoByColaboradorForm.getSearchCursoNome() == null) {
			colaboradorCursoByColaboradorForm.setSearchColaboradorNome("");
			colaboradorCursoByColaboradorForm.setSearchCursoNome("");
			if (colaboradorCursoByColaboradorForm.getColaboradorCursoSortTipo() == null) {
				colaboradorCursoByColaboradorForm.setColaboradorCursoSortTipo("CursoNome");	
			}
			
		}

		if (colaboradorCursoByColaboradorForm.getColaboradorCursoSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorCursoByColaboradorForm.getColaboradorCursoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","curso.cursoNome"); 
		
		} else if (colaboradorCursoByColaboradorForm.getColaboradorCursoSortTipo().equalsIgnoreCase("CursoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"curso.cursoNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorCursoByColaboradorForm.getSearchCursoNome().equalsIgnoreCase("")){
			colaboradorCursoList = colaboradorCursoService.getByCursoNome(colaboradorCursoByColaboradorForm.getSearchCursoNome(),pageable);
			colaboradorCursosTotal = colaboradorCursoService.getByCursoNome(colaboradorCursoByColaboradorForm.getSearchCursoNome()).size();
			
		} else {
			colaboradorCursoList = colaboradorCursoService.getByColaboradorNome(colaboradorCursoByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorCursosTotal = colaboradorCursoService.getByColaboradorNome(colaboradorCursoByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorCurso> colaboradorCursoPage = new PageImpl<ColaboradorCurso>(colaboradorCursoList,pageable,colaboradorCursosTotal+1);
		
		mv.addObject("colaboradorCursoPage", colaboradorCursoPage);
		mv.addObject("page",colaboradorCursoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorCursoSave", method = RequestMethod.POST)
	public ModelAndView colaboradorCursoSave(@Valid ColaboradorCursoForm colaboradorCursoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorCursoAdd(colaboradorCursoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorCursoHome");

		try {
			colaboradorCursoService.save(colaboradorCursoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorCursoUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Certificação já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorCursoAdd(colaboradorCursoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorCursoRelMenu", method = RequestMethod.GET)
	public ModelAndView colaboradorCursoRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorCurso/colaboradorCursoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/colaboradorCursoRel001")
	public ModelAndView colaboradorCursoRel001() {

		ModelAndView mv = new ModelAndView("colaboradorCurso/colaboradorCursoRel001");
		Pageable colaboradorCursoPageable = new PageRequest(0, 200, Direction.ASC, "colaborador.pessoaNome","curso.cursoNome");
		mv.addObject("colaboradorCursoPage", colaboradorCursoService.getColaboradorCursoAll(colaboradorCursoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorCursoView/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorCursoView(@PathVariable("id") Long colaboradorCursoId) {

		ColaboradorCurso colaboradorCurso = colaboradorCursoService.getColaboradorCursoByColaboradorCursoPK(colaboradorCursoId);
		ModelAndView mv = new ModelAndView("colaboradorCurso/colaboradorCursoView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorCurso.getColaborador());
		CursoForm cursoForm = cursoService.converteCursoView(colaboradorCurso.getCurso());
		ColaboradorCursoForm colaboradorCursoForm = colaboradorCursoService.converteColaboradorCursoView(colaboradorCurso);
		mv.addObject("colaboradorCursoForm", colaboradorCursoForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("cursoForm", cursoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}