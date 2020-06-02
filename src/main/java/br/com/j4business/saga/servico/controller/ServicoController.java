package br.com.j4business.saga.servico.controller;

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

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.servico.model.Servico;
import br.com.j4business.saga.servico.model.ServicoByServicoForm;
import br.com.j4business.saga.servico.model.ServicoForm;
import br.com.j4business.saga.servico.service.ServicoService;
import br.com.j4business.saga.servicoprocesso.service.ServicoProcessoService;

@Controller
public class ServicoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ServicoProcessoService servicoProcessoService;

	@Autowired
	private ServicoService servicoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/servicoAdd", method = RequestMethod.GET)
	public ModelAndView servicoAdd(ServicoForm servicoForm) {

		ModelAndView mv = new ModelAndView("servico/servicoAdd");
		servicoForm = servicoService.servicoParametros(servicoForm);
		mv.addObject("servicoForm", servicoForm);
		mv.addObject("servicoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoCreate", method = RequestMethod.POST)
	public ModelAndView servicoCreate(@Valid ServicoForm servicoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return servicoAdd(servicoForm);
		}

		if (servicoForm.getServicoPK() > 0) {
			return this.servicoSave(servicoForm, result, attributes);
			
		}
		
		try {
			servicoService.create(servicoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("servicoNome")) {
			        ObjectError error = new ObjectError("servicoNome","Nome do Serviço já existente no cadastro.");
			        result.addError(error);			
			}
            
			return servicoAdd(servicoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/servicoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView servicoDelete(@PathVariable("id") long servicoPK, @Valid ServicoForm servicoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/servicoHome");

		Servico servico = servicoService.getServicoByServicoPK(servicoPK);
		try {
			servicoService.delete(servicoPK);

			attributes.addFlashAttribute("mensagem", "Serviço excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Serviço não excluído. Existe(m) relacionamento(s) de Serviço ** "+ servico.getServicoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoEdit/{id}", method = RequestMethod.GET)
	public ModelAndView servicoEdit(@PathVariable("id") Long servicoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("servico/servicoEdit");
		Servico servico = servicoService.getServicoByServicoPK(servicoId);
		ServicoForm servicoForm = servicoService.converteServico(servico);
		mv.addObject("servicoForm", servicoForm);
		mv.addObject("servicoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable servicoProcessoPageable = new PageRequest(0, 200, Direction.ASC, "servico.servicoNome","servicoProcessoSequencia");
		mv.addObject("servicoProcessoPage", servicoProcessoService.getByServicoPK(servico.getServicoPK(),servicoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoHome", method = RequestMethod.GET)
	public ModelAndView servicoHome(@Valid ServicoByServicoForm servicoByServicoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("servico/servicoHome");

		List<Servico> servicoList = new ArrayList<Servico>();

		int servicosTotal = 0;
		
		if (servicoByServicoForm.getSearchServicoNome() == null) {
			servicoByServicoForm.setSearchServicoNome("");
			servicoByServicoForm.setSearchServicoDescricao("");
			if (servicoByServicoForm.getServicoSortTipo() == null) {
				servicoByServicoForm.setServicoSortTipo("ServicoNome");
			}

		}

		if (servicoByServicoForm.getServicoSortTipo().equalsIgnoreCase("ServicoNome") || servicoByServicoForm.getServicoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "servicoNome");

		} else if (servicoByServicoForm.getServicoSortTipo().equalsIgnoreCase("ServicoDescricao")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC, "servicoDescricao");

		}

		if ((!servicoByServicoForm.getSearchServicoNome().equalsIgnoreCase(""))) {
			servicoList = servicoService.getByServicoNome(servicoByServicoForm.getSearchServicoNome(), pageable);
			servicosTotal = servicoService.getByServicoNome(servicoByServicoForm.getSearchServicoNome()).size();

		} else {
			servicoList = servicoService.getByServicoDescricao(servicoByServicoForm.getSearchServicoDescricao(), pageable);
			servicosTotal = servicoService.getByServicoDescricao(servicoByServicoForm.getSearchServicoDescricao()).size();
		}

		Page<Servico> servicoPage = new PageImpl<Servico>(servicoList, pageable, servicosTotal+1);

		mv.addObject("servicoPage", servicoPage);
		mv.addObject("page", servicoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoSave", method = RequestMethod.POST)
	public ModelAndView servicoSave(@Valid ServicoForm servicoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return servicoAdd(servicoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/servicoHome");

		try {
			servicoService.save(servicoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("servicoNome")) {
			        ObjectError error = new ObjectError("servicoNome","Nome do Serviço já existente no cadastro.");
			        result.addError(error);			
			}
            return servicoAdd(servicoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/servicoRelMenu", method = RequestMethod.GET)
	public ModelAndView servicoRelMenu() {

		ModelAndView mv = new ModelAndView("servico/servicoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping(path = "/servicoRel001", method = RequestMethod.GET)
	public ModelAndView servicoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("servico/servicoRel001");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "servicoNome");
		mv.addObject("servicoPage", servicoService.getServicoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoRel002", method = RequestMethod.GET)
	public ModelAndView servicoRel002(Pageable pageable) {

		ModelAndView mv = new ModelAndView("servico/servicoRel002");
		pageable = new PageRequest(pageable.getPageNumber(), 200 , Direction.ASC, "servicoNome");
		mv.addObject("servicoPage", servicoService.getServicoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoView/{id}", method = RequestMethod.GET)
	public ModelAndView servicoView(@PathVariable("id") Long servicoId) {

		Servico servico = servicoService.getServicoByServicoPK(servicoId);
		ModelAndView mv = new ModelAndView("servico/servicoView");
		ServicoForm servicoForm = servicoService.converteServicoView(servico);
		mv.addObject("servicoForm", servicoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;

	}

}