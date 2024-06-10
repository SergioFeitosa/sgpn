package br.com.j4business.saga.colaboradorformacao.controller;

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

import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.formacao.model.FormacaoForm;
import br.com.j4business.saga.formacao.service.FormacaoService;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacao;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacaoByColaboradorForm;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacaoForm;
import br.com.j4business.saga.colaboradorformacao.service.ColaboradorFormacaoService;

@Controller
public class ColaboradorFormacaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ColaboradorFormacaoService colaboradorFormacaoService;

	@Autowired
	private FormacaoService formacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/colaboradorFormacaoAdd")
	public ModelAndView colaboradorFormacaoAdd(ColaboradorFormacaoForm colaboradorFormacaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFormacao/colaboradorFormacaoAdd");
		colaboradorFormacaoForm = colaboradorFormacaoService.colaboradorFormacaoParametros(colaboradorFormacaoForm);
		mv.addObject("colaboradorFormacaoForm", colaboradorFormacaoForm);
		mv.addObject("colaboradorFormacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorFormacaoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable formacaoPageable = PageRequest.of(0, 200, Direction.ASC, "formacaoNome");
		mv.addObject("formacaoPage", formacaoService.getFormacaoAll(formacaoPageable));
		Pageable fornecedorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorFormacaoCreate")
	public ModelAndView colaboradorFormacaoCreate(@Valid ColaboradorFormacaoForm colaboradorFormacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorFormacaoAdd(colaboradorFormacaoForm,pageable);
		}

		if (colaboradorFormacaoForm.getColaboradorFormacaoPK() > 0) {
			return this.colaboradorFormacaoSave(colaboradorFormacaoForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorFormacaoService.create(colaboradorFormacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorFormacaoUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Formação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorFormacaoAdd(colaboradorFormacaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorFormacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/colaboradorFormacaoDelete/{id}")
	public ModelAndView colaboradorFormacaoDelete(@PathVariable("id") long colaboradorFormacaoId, @Valid FormacaoForm formacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorFormacaoHome");
		
		
		ColaboradorFormacao colaboradorFormacao = colaboradorFormacaoService.getColaboradorFormacaoByColaboradorFormacaoPK(colaboradorFormacaoId);
		try {
			colaboradorFormacaoService.delete(colaboradorFormacaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Formacao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Formacao não excluído. Existe(m) relacionamento(s) de Colaborador/Formacao ** "+ 
										  colaboradorFormacao.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorFormacao.getFormacao().getFormacaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/colaboradorFormacaoEdit/{colaboradorFormacaoPK}")
	public ModelAndView colaboradorFormacaoEdit(@PathVariable("colaboradorFormacaoPK") Long colaboradorFormacaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFormacao/colaboradorFormacaoEdit");
		ColaboradorFormacao colaboradorFormacao = colaboradorFormacaoService.getColaboradorFormacaoByColaboradorFormacaoPK(colaboradorFormacaoPK);
		ColaboradorFormacaoForm colaboradorFormacaoForm = colaboradorFormacaoService.converteColaboradorFormacao(colaboradorFormacao);
		mv.addObject("colaboradorFormacaoForm", colaboradorFormacaoForm);
		mv.addObject("colaboradorFormacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorFormacaoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable formacaoPageable = PageRequest.of(0, 200, Direction.ASC, "formacaoNome");
		mv.addObject("formacaoPage", formacaoService.getFormacaoAll(formacaoPageable));
		Pageable fornecedorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}
	
	@GetMapping(path = "/colaboradorFormacaoHome")
	public ModelAndView colaboradorFormacaoHome(@Valid ColaboradorFormacaoByColaboradorForm colaboradorFormacaoByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFormacao/colaboradorFormacaoHome");
		
		List<ColaboradorFormacao> colaboradorFormacaoList = new ArrayList<ColaboradorFormacao>();
		
		int colaboradorFormacoesTotal = 0;
		
		if (colaboradorFormacaoByColaboradorForm.getSearchFormacaoNome() == null) {
			colaboradorFormacaoByColaboradorForm.setSearchColaboradorNome("");
			colaboradorFormacaoByColaboradorForm.setSearchFormacaoNome("");
			if (colaboradorFormacaoByColaboradorForm.getColaboradorFormacaoSortTipo() == null) {
				colaboradorFormacaoByColaboradorForm.setColaboradorFormacaoSortTipo("FormacaoNome");	
			}
			
		}

		if (colaboradorFormacaoByColaboradorForm.getColaboradorFormacaoSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorFormacaoByColaboradorForm.getColaboradorFormacaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","formacao.formacaoNome"); 
		
		} else if (colaboradorFormacaoByColaboradorForm.getColaboradorFormacaoSortTipo().equalsIgnoreCase("FormacaoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"formacao.formacaoNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorFormacaoByColaboradorForm.getSearchFormacaoNome().equalsIgnoreCase("")){
			colaboradorFormacaoList = colaboradorFormacaoService.getByFormacaoNome(colaboradorFormacaoByColaboradorForm.getSearchFormacaoNome(),pageable);
			colaboradorFormacoesTotal = colaboradorFormacaoService.getByFormacaoNome(colaboradorFormacaoByColaboradorForm.getSearchFormacaoNome()).size();
			
		} else {
			colaboradorFormacaoList = colaboradorFormacaoService.getByColaboradorNome(colaboradorFormacaoByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorFormacoesTotal = colaboradorFormacaoService.getByColaboradorNome(colaboradorFormacaoByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorFormacao> colaboradorFormacaoPage = new PageImpl<ColaboradorFormacao>(colaboradorFormacaoList,pageable,colaboradorFormacoesTotal+1);
		
		mv.addObject("colaboradorFormacaoPage", colaboradorFormacaoPage);
		mv.addObject("page",colaboradorFormacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/colaboradorFormacaoSave")
	public ModelAndView colaboradorFormacaoSave(@Valid ColaboradorFormacaoForm colaboradorFormacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorFormacaoAdd(colaboradorFormacaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorFormacaoHome");

		try {
			colaboradorFormacaoService.save(colaboradorFormacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorFormacaoUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Formação já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorFormacaoAdd(colaboradorFormacaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/colaboradorFormacaoRelMenu")
	public ModelAndView colaboradorFormacaoRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorFormacao/colaboradorFormacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorFormacaoRel001")
	public ModelAndView colaboradorFormacaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorFormacao/colaboradorFormacaoRel001");
		Pageable colaboradorFormacaoPage = PageRequest.of(0, 200, Direction.ASC, "colaborador.pessoaNome","formacao.formacaoNome");
		mv.addObject("colaboradorFormacaoPage", colaboradorFormacaoService.getColaboradorFormacaoAll(colaboradorFormacaoPage));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorFormacaoView/{id}")
	public ModelAndView colaboradorFormacaoView(@PathVariable("id") Long colaboradorFormacaoId) {

		ColaboradorFormacao colaboradorFormacao = colaboradorFormacaoService.getColaboradorFormacaoByColaboradorFormacaoPK(colaboradorFormacaoId);
		ModelAndView mv = new ModelAndView("colaboradorFormacao/colaboradorFormacaoView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorFormacao.getColaborador());
		FormacaoForm formacaoForm = formacaoService.converteFormacaoView(colaboradorFormacao.getFormacao());
		ColaboradorFormacaoForm colaboradorFormacaoForm = colaboradorFormacaoService.converteColaboradorFormacaoView(colaboradorFormacao);
		mv.addObject("colaboradorFormacaoForm", colaboradorFormacaoForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("formacaoForm", formacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}