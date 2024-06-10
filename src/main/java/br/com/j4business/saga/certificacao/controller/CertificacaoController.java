package br.com.j4business.saga.certificacao.controller;

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
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.certificacao.model.CertificacaoByCertificacaoForm;
import br.com.j4business.saga.certificacao.model.CertificacaoForm;
import br.com.j4business.saga.certificacao.service.CertificacaoService;

@Controller
public class CertificacaoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private CertificacaoService certificacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/certificacaoAdd")
	public ModelAndView certificacaoAdd(CertificacaoForm certificacaoForm) {

		ModelAndView mv = new ModelAndView("certificacao/certificacaoAdd");
		certificacaoForm = certificacaoService.certificacaoParametros(certificacaoForm);
		mv.addObject("certificacaoForm", certificacaoForm);
		mv.addObject("certificacaoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/certificacaoCreate")
	public ModelAndView certificacaoCreate(@Valid CertificacaoForm certificacaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return certificacaoAdd(certificacaoForm);
		}

		if (certificacaoForm.getCertificacaoPK() > 0) {
			return this.certificacaoSave(certificacaoForm, result, attributes);
			
		}
		
		try {
			certificacaoService.create(certificacaoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("certificacaoNome")) {
			        ObjectError error = new ObjectError("certificacaoNome","Nome da Certificação já existente no cadastro.");
			        result.addError(error);			
			}
            
			return certificacaoAdd(certificacaoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/certificacaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/certificacaoDelete/{id}")
	public ModelAndView certificacaoDelete(@PathVariable("id") long certificacaoPK, @Valid CertificacaoForm certificacaoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/certificacaoHome");

		Certificacao certificacao = certificacaoService.getCertificacaoByCertificacaoPK(certificacaoPK);
		try {
			certificacaoService.delete(certificacaoPK);

			attributes.addFlashAttribute("mensagem", "Certificação excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Certificação não excluída. Existe(m) relacionamento(s) de Certificação ** "+ certificacao.getCertificacaoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/certificacaoEdit/{id}")
	public ModelAndView certificacaoEdit(@PathVariable("id") Long certificacaoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("certificacao/certificacaoEdit");
		Certificacao certificacao = certificacaoService.getCertificacaoByCertificacaoPK(certificacaoId);
		CertificacaoForm certificacaoForm = certificacaoService.converteCertificacao(certificacao);
		mv.addObject("certificacaoForm", certificacaoForm);
		mv.addObject("certificacaoStatusValues", AtributoStatus.values());

		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/certificacaoHome")
	public ModelAndView certificacaoHome(@Valid CertificacaoByCertificacaoForm certificacaoByCertificacaoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("certificacao/certificacaoHome");
		
		List<Certificacao> certificacaoList = new ArrayList<Certificacao>();

		int certificacoesTotal = 0;
		
		if (certificacaoByCertificacaoForm.getSearchCertificacaoNome() == null) {
			certificacaoByCertificacaoForm.setSearchCertificacaoNome("");
			certificacaoByCertificacaoForm.setSearchCertificacaoDescricao("");
			if (certificacaoByCertificacaoForm.getCertificacaoSortTipo() == null) {
				certificacaoByCertificacaoForm.setCertificacaoSortTipo("CertificacaoNome");
			}

		}

		if (certificacaoByCertificacaoForm.getCertificacaoSortTipo().equalsIgnoreCase("CertificacaoNome") || certificacaoByCertificacaoForm.getCertificacaoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "certificacaoNome");

		} else if (certificacaoByCertificacaoForm.getCertificacaoSortTipo().equalsIgnoreCase("CertificacaoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "certificacaoDescricao");

		}

		if ((!certificacaoByCertificacaoForm.getSearchCertificacaoNome().equalsIgnoreCase(""))) {
			certificacaoList = certificacaoService.getByCertificacaoNome(certificacaoByCertificacaoForm.getSearchCertificacaoNome(), pageable);
			certificacoesTotal = certificacaoService.getByCertificacaoNome(certificacaoByCertificacaoForm.getSearchCertificacaoNome()).size();

		} else {
			certificacaoList = certificacaoService.getByCertificacaoDescricao(certificacaoByCertificacaoForm.getSearchCertificacaoDescricao(), pageable);
			certificacoesTotal = certificacaoService.getByCertificacaoDescricao(certificacaoByCertificacaoForm.getSearchCertificacaoDescricao()).size();
		}

		Page<Certificacao> certificacaoPage = new PageImpl<Certificacao>(certificacaoList, pageable, certificacoesTotal+1);

		mv.addObject("certificacaoPage", certificacaoPage);
		mv.addObject("page", certificacaoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/certificacaoRelMenu")
	public ModelAndView certificacaoRelMenu() {

		ModelAndView mv = new ModelAndView("certificacao/certificacaoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/certificacaoRel001")
	public ModelAndView certificacaoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("certificacao/certificacaoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/certificacaoRel002")
	public ModelAndView certificacaoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("certificacao/certificacaoRel002");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/certificacaoSave")
	public ModelAndView certificacaoSave(@Valid CertificacaoForm certificacaoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return certificacaoAdd(certificacaoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/certificacaoHome");

		try {
			certificacaoService.save(certificacaoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("certificacaoNome")) {
			        ObjectError error = new ObjectError("certificacaoNome","Nome da Certificação já existente no cadastro.");
			        result.addError(error);			
			}
            return certificacaoAdd(certificacaoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/certificacaoView/{id}")
	public ModelAndView certificacaoView(@PathVariable("id") Long certificacaoId) {

		Certificacao certificacao = certificacaoService.getCertificacaoByCertificacaoPK(certificacaoId);
		ModelAndView mv = new ModelAndView("certificacao/certificacaoView");
		CertificacaoForm certificacaoForm = certificacaoService.converteCertificacaoView(certificacao);
		mv.addObject("certificacaoForm", certificacaoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}