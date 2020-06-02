package br.com.j4business.saga.servicoprocesso.controller;

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
import br.com.j4business.saga.servico.model.ServicoForm;
import br.com.j4business.saga.servico.service.ServicoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcessoByServicoForm;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcessoForm;
import br.com.j4business.saga.servicoprocesso.service.ServicoProcessoService;

@Controller
public class ServicoProcessoController {

	@Autowired
	private ServicoService servicoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ServicoProcessoService servicoProcessoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/servicoProcessoAdd", method = RequestMethod.GET)
	public ModelAndView servicoProcessoAdd(ServicoProcessoForm servicoProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("servicoProcesso/servicoProcessoAdd");
		servicoProcessoForm = servicoProcessoService.servicoProcessoParametros(servicoProcessoForm);
		mv.addObject("servicoProcessoForm", servicoProcessoForm);
		mv.addObject("servicoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		Pageable servicoPageable = new PageRequest(0, 200, Direction.ASC, "servicoNome");
		mv.addObject("servicoPage", servicoService.getServicoAll(servicoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoProcessoCreate", method = RequestMethod.POST)
	public ModelAndView servicoProcessoCreate(@Valid ServicoProcessoForm servicoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return servicoProcessoAdd(servicoProcessoForm,pageable);
		}

		if (servicoProcessoForm.getServicoProcessoPK() > 0) {
			return this.servicoProcessoSave(servicoProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			servicoProcessoService.create(servicoProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("servicoProcessoUnique")) {
			        ObjectError error = new ObjectError("servicoNome","Relacionamento entre Serviço e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return servicoProcessoAdd(servicoProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/servicoProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoProcessoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView goServicoProcessoDelete(@PathVariable("id") long servicoProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/servicoProcessoHome");
		
		
		ServicoProcesso servicoProcesso = servicoProcessoService.getServicoProcessoByServicoProcessoPK(servicoProcessoId);
		try {
			servicoProcessoService.delete(servicoProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Servico/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Servico/Processo não excluído. Existe(m) relacionamento(s) de Servico/Processo ** "+ 
										  servicoProcesso.getServico().getServicoNome() +
										  " / " +
										  servicoProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoProcessoEdit/{servicoProcessoPK}", method = RequestMethod.GET)
	public ModelAndView goServicoProcessoEdit(@PathVariable("servicoProcessoPK") Long servicoProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("servicoProcesso/servicoProcessoEdit");
		ServicoProcesso servicoProcesso = servicoProcessoService.getServicoProcessoByServicoProcessoPK(servicoProcessoPK);
		ServicoProcessoForm servicoProcessoForm = servicoProcessoService.converteServicoProcesso(servicoProcesso);
		mv.addObject("servicoProcessoForm", servicoProcessoForm);
		mv.addObject("servicoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		Pageable servicoPageable = new PageRequest(0, 200, Direction.ASC, "servicoNome");
		mv.addObject("servicoPage", servicoService.getServicoAll(servicoPageable));
		
		servicoProcesso.getServicoProcessoSequencia();
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/servicoProcessoHome", method = RequestMethod.GET)
	public ModelAndView goServicoProcessoHome(@Valid ServicoProcessoByServicoForm servicoProcessoByServicoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("servicoProcesso/servicoProcessoHome");
		
		List<ServicoProcesso> servicoProcessoList = new ArrayList<ServicoProcesso>();
		
		int servicoProcessoTotal = 0;
		
		if (servicoProcessoByServicoForm.getSearchProcessoNome() == null) {
			servicoProcessoByServicoForm.setSearchServicoNome("");
			servicoProcessoByServicoForm.setSearchProcessoNome("");
			if (servicoProcessoByServicoForm.getServicoProcessoSortTipo() == null) {
				servicoProcessoByServicoForm.setServicoProcessoSortTipo("ServicoNome");	
			}
			
		}

		if (servicoProcessoByServicoForm.getServicoProcessoSortTipo().equalsIgnoreCase("ServicoNome")
				|| servicoProcessoByServicoForm.getServicoProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"servico.servicoNome","servicoProcessoSequencia"); 
		
		} else if (servicoProcessoByServicoForm.getServicoProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","servicoProcessoSequencia"); 

		} else if (servicoProcessoByServicoForm.getServicoProcessoSortTipo().equalsIgnoreCase("Sequencia")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"servicoProcessoSequencia","servico.servicoNome"); 

		}

		if ( ! servicoProcessoByServicoForm.getSearchProcessoNome().equalsIgnoreCase("")){
			servicoProcessoList = servicoProcessoService.getByProcessoNome(servicoProcessoByServicoForm.getSearchProcessoNome(),pageable);
			servicoProcessoTotal = servicoProcessoService.getByProcessoNome(servicoProcessoByServicoForm.getSearchProcessoNome()).size();
		} else {
			servicoProcessoList = servicoProcessoService.getByServicoNome(servicoProcessoByServicoForm.getSearchServicoNome(),pageable);
			servicoProcessoTotal = servicoProcessoService.getByServicoNome(servicoProcessoByServicoForm.getSearchServicoNome()).size();
		}
		
			Page<ServicoProcesso> servicoProcessoPage = new PageImpl<ServicoProcesso>(servicoProcessoList,pageable,servicoProcessoTotal+1);
		
		mv.addObject("servicoProcessoPage", servicoProcessoPage);
		mv.addObject("page",servicoProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoProcessoSave", method = RequestMethod.POST)
	public ModelAndView servicoProcessoSave(@Valid ServicoProcessoForm servicoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return servicoProcessoAdd(servicoProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/servicoProcessoHome");

		try {
			servicoProcessoService.save(servicoProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("servicoProcessoUnique")) {
		        ObjectError error = new ObjectError("servicoNome","Relacionamento entre Serviço e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return servicoProcessoAdd(servicoProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/servicoProcessoRelMenu", method = RequestMethod.GET)
	public ModelAndView goServicoProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("servicoProcesso/servicoProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/servicoProcessoRel001")
	public ModelAndView goServicoProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("servicoProcesso/servicoProcessoRel001");
		Pageable servicoProcessoPageable = new PageRequest(0, 200, Direction.ASC, "servico.servicoNome","processo.processoNome");
		mv.addObject("servicoProcessoPage", servicoProcessoService.getServicoProcessoAll(servicoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/servicoProcessoView/{id}", method = RequestMethod.GET)
	public ModelAndView goServicoProcessoView(@PathVariable("id") Long servicoProcessoId) {

		ServicoProcesso servicoProcesso = servicoProcessoService.getServicoProcessoByServicoProcessoPK(servicoProcessoId);
		ModelAndView mv = new ModelAndView("servicoProcesso/servicoProcessoView");
		ServicoProcessoForm servicoProcessoForm = servicoProcessoService.converteServicoProcessoView(servicoProcesso);
		ServicoForm servicoForm = servicoService.converteServicoView(servicoProcesso.getServico());
		ProcessoForm processoForm = processoService.converteProcessoView(servicoProcesso.getProcesso());
		mv.addObject("servicoProcessoForm", servicoProcessoForm);
		mv.addObject("servicoForm", servicoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

}