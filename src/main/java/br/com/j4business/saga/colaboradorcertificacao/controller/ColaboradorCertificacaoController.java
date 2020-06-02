package br.com.j4business.saga.colaboradorcertificacao.controller;

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
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.certificacao.model.CertificacaoForm;
import br.com.j4business.saga.certificacao.service.CertificacaoService;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacaoByColaboradorForm;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacaoForm;
import br.com.j4business.saga.colaboradorcertificacao.service.ColaboradorCertificacaoService;
import br.com.j4business.saga.fornecedor.service.FornecedorService;

@Controller
public class ColaboradorCertificacaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ColaboradorCertificacaoService colaboradorCertificacaoService;

	@Autowired
	private CertificacaoService certificacaoService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/colaboradorCertificacaoAdd", method = RequestMethod.GET)
	public ModelAndView colaboradorCertificacaoAdd(ColaboradorCertificacaoForm colaboradorCertificacaoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCertificacao/colaboradorCertificacaoAdd");
		colaboradorCertificacaoForm = colaboradorCertificacaoService.colaboradorCertificacaoParametros(colaboradorCertificacaoForm);
		mv.addObject("colaboradorCertificacaoForm", colaboradorCertificacaoForm);
		mv.addObject("colaboradorCertificacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorCertificacaoStatusValues", AtributoStatus.values());

		Pageable certificacaoPageable = new PageRequest(0, 200, Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(certificacaoPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorCertificacaoCreate", method = RequestMethod.POST)
	public ModelAndView colaboradorCertificacaoCreate(@Valid ColaboradorCertificacaoForm colaboradorCertificacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		
		if (result.hasErrors()) {
			return colaboradorCertificacaoAdd(colaboradorCertificacaoForm,pageable);
		}

		if (colaboradorCertificacaoForm.getColaboradorCertificacaoPK() > 0) {
			return this.colaboradorCertificacaoSave(colaboradorCertificacaoForm, result, attributes,pageable);
			
		}
		
		try {
			colaboradorCertificacaoService.create(colaboradorCertificacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorCertificacaoUnique")) {
			        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Certificação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return colaboradorCertificacaoAdd(colaboradorCertificacaoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/colaboradorCertificacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/colaboradorCertificacaoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorCertificacaoDelete(@PathVariable("id") long colaboradorCertificacaoId, @Valid CertificacaoForm certificacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorCertificacaoHome");
		
		
		ColaboradorCertificacao colaboradorCertificacao = colaboradorCertificacaoService.getColaboradorCertificacaoByColaboradorCertificacaoPK(colaboradorCertificacaoId);
		try {
			colaboradorCertificacaoService.delete(colaboradorCertificacaoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Colaborador/Certificacao excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Colaborador/Certificacao não excluído. Existe(m) relacionamento(s) de Colaborador/Certificacao ** "+ 
										  colaboradorCertificacao.getColaborador().getPessoaNome() +
										  " / " +
										  colaboradorCertificacao.getCertificacao().getCertificacaoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/colaboradorCertificacaoEdit/{colaboradorCertificacaoPK}", method = RequestMethod.GET)
	public ModelAndView colaboradorCertificacaoEdit(@PathVariable("colaboradorCertificacaoPK") Long colaboradorCertificacaoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCertificacao/colaboradorCertificacaoEdit");
		ColaboradorCertificacao colaboradorCertificacao = colaboradorCertificacaoService.getColaboradorCertificacaoByColaboradorCertificacaoPK(colaboradorCertificacaoPK);
		ColaboradorCertificacaoForm colaboradorCertificacaoForm = colaboradorCertificacaoService.converteColaboradorCertificacao(colaboradorCertificacao);
		mv.addObject("colaboradorCertificacaoForm", colaboradorCertificacaoForm);
		mv.addObject("colaboradorCertificacaoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("colaboradorCertificacaoStatusValues", AtributoStatus.values());

		Pageable certificacaoPageable = new PageRequest(0, 200, Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(certificacaoPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable fornecedorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("fornecedorPage", fornecedorService.getFornecedorAll(fornecedorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/colaboradorCertificacaoHome", method = RequestMethod.GET)
	public ModelAndView colaboradorCertificacaoHome(@Valid ColaboradorCertificacaoByColaboradorForm colaboradorCertificacaoByColaboradorForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCertificacao/colaboradorCertificacaoHome");
		
		List<ColaboradorCertificacao> colaboradorCertificacaoList = new ArrayList<ColaboradorCertificacao>();
		
		int colaboradorCertificacoesTotal = 0;
		
		if (colaboradorCertificacaoByColaboradorForm.getSearchCertificacaoNome() == null) {
			colaboradorCertificacaoByColaboradorForm.setSearchColaboradorNome("");
			colaboradorCertificacaoByColaboradorForm.setSearchCertificacaoNome("");
			if (colaboradorCertificacaoByColaboradorForm.getColaboradorCertificacaoSortTipo() == null) {
				colaboradorCertificacaoByColaboradorForm.setColaboradorCertificacaoSortTipo("CertificacaoNome");	
			}
			
		}

		if (colaboradorCertificacaoByColaboradorForm.getColaboradorCertificacaoSortTipo().equalsIgnoreCase("ColaboradorNome")
				|| colaboradorCertificacaoByColaboradorForm.getColaboradorCertificacaoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"colaborador.pessoaNome","certificacao.certificacaoNome"); 
		
		} else if (colaboradorCertificacaoByColaboradorForm.getColaboradorCertificacaoSortTipo().equalsIgnoreCase("CertificacaoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"certificacao.certificacaoNome","colaborador.pessoaNome"); 

		}

		if ( ! colaboradorCertificacaoByColaboradorForm.getSearchCertificacaoNome().equalsIgnoreCase("")){
			colaboradorCertificacaoList = colaboradorCertificacaoService.getByCertificacaoNome(colaboradorCertificacaoByColaboradorForm.getSearchCertificacaoNome(),pageable);
			colaboradorCertificacoesTotal = colaboradorCertificacaoService.getByCertificacaoNome(colaboradorCertificacaoByColaboradorForm.getSearchCertificacaoNome()).size();
			
		} else {
			colaboradorCertificacaoList = colaboradorCertificacaoService.getByColaboradorNome(colaboradorCertificacaoByColaboradorForm.getSearchColaboradorNome(),pageable);
			colaboradorCertificacoesTotal = colaboradorCertificacaoService.getByColaboradorNome(colaboradorCertificacaoByColaboradorForm.getSearchColaboradorNome()).size();

		} 
		
		Page<ColaboradorCertificacao> colaboradorCertificacaoPage = new PageImpl<ColaboradorCertificacao>(colaboradorCertificacaoList,pageable,colaboradorCertificacoesTotal+1);
		
		mv.addObject("colaboradorCertificacaoPage", colaboradorCertificacaoPage);
		mv.addObject("page",colaboradorCertificacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorCertificacaoSave", method = RequestMethod.POST)
	public ModelAndView colaboradorCertificacaoSave(@Valid ColaboradorCertificacaoForm colaboradorCertificacaoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return colaboradorCertificacaoAdd(colaboradorCertificacaoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorCertificacaoHome");

		try {
			colaboradorCertificacaoService.save(colaboradorCertificacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("colaboradorCertificacaoUnique")) {
		        ObjectError error = new ObjectError("colaboradorNome","Relacionamento entre Colaborador e Certificação já existente no cadastro.");
		        result.addError(error);			
		}
            return colaboradorCertificacaoAdd(colaboradorCertificacaoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@RequestMapping(path = "/colaboradorCertificacaoRelMenu", method = RequestMethod.GET)
	public ModelAndView colaboradorCertificacaoRelMenu() {

		ModelAndView mv = new ModelAndView("colaboradorCertificacao/colaboradorCertificacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/colaboradorCertificacaoRel001")
	public ModelAndView colaboradorCertificacaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaboradorCertificacao/colaboradorCertificacaoRel001");

		Pageable certificacaoPageable = new PageRequest(0, 200, Direction.ASC, "colaborador.pessoaNome","certificacao.certificacaoNome");
		mv.addObject("colaboradorCertificacaoPage", colaboradorCertificacaoService.getColaboradorCertificacaoAll(certificacaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/colaboradorCertificacaoView/{id}", method = RequestMethod.GET)
	public ModelAndView colaboradorCertificacaoView(@PathVariable("id") Long colaboradorCertificacaoId) {

		ColaboradorCertificacao colaboradorCertificacao = colaboradorCertificacaoService.getColaboradorCertificacaoByColaboradorCertificacaoPK(colaboradorCertificacaoId);
		ModelAndView mv = new ModelAndView("colaboradorCertificacao/colaboradorCertificacaoView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaboradorCertificacao.getColaborador());
		CertificacaoForm certificacaoForm = certificacaoService.converteCertificacaoView(colaboradorCertificacao.getCertificacao());
		ColaboradorCertificacaoForm colaboradorCertificacaoForm = colaboradorCertificacaoService.converteColaboradorCertificacaoView(colaboradorCertificacao);
		mv.addObject("colaboradorCertificacaoForm", colaboradorCertificacaoForm);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("certificacaoForm", certificacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}