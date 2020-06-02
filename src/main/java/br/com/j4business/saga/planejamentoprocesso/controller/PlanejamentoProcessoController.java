package br.com.j4business.saga.planejamentoprocesso.controller;

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
import br.com.j4business.saga.planejamento.model.PlanejamentoForm;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcao;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcesso;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcessoByPlanejamentoForm;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcessoForm;
import br.com.j4business.saga.planejamentoprocesso.service.PlanejamentoProcessoService;

@Controller
public class PlanejamentoProcessoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private PlanejamentoProcessoService planejamentoProcessoService;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/planejamentoProcessoAdd", method = RequestMethod.GET)
	public ModelAndView planejamentoProcessoAdd(PlanejamentoProcessoForm planejamentoProcessoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoProcesso/planejamentoProcessoAdd");
		planejamentoProcessoForm = planejamentoProcessoService.planejamentoProcessoParametros(planejamentoProcessoForm);
		mv.addObject("planejamentoProcessoForm", planejamentoProcessoForm);
		mv.addObject("planejamentoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("planejamentoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoPageable = new PageRequest(0, 200, Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(planejamentoPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoProcessoCreate", method = RequestMethod.POST)
	public ModelAndView planejamentoProcessoCreate(@Valid PlanejamentoProcessoForm planejamentoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return planejamentoProcessoAdd(planejamentoProcessoForm,pageable);
		}

		if (planejamentoProcessoForm.getPlanejamentoProcessoPK() > 0) {
			return this.planejamentoProcessoSave(planejamentoProcessoForm, result, attributes,pageable);
			
		}
		
		try {
			planejamentoProcessoService.create(planejamentoProcessoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("planejamentoProcessoUnique")) {
			        ObjectError error = new ObjectError("planejamentoNome","Relacionamento entre Planejamento e Processo já existente no cadastro.");
			        result.addError(error);			
			}
            
			return planejamentoProcessoAdd(planejamentoProcessoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/planejamentoProcessoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/planejamentoProcessoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView planejamentoProcessoDelete(@PathVariable("id") long planejamentoProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/planejamentoProcessoHome");
		
		
		PlanejamentoProcesso planejamentoProcesso = planejamentoProcessoService.getPlanejamentoProcessoByPlanejamentoProcessoPK(planejamentoProcessoId);
		try {
			planejamentoProcessoService.delete(planejamentoProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Planejamento/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Planejamento/Processo não excluído. Existe(m) relacionamento(s) de Planejamento/Processo ** "+ 
										  planejamentoProcesso.getPlanejamento().getPlanejamentoNome() +
										  " / " +
										  planejamentoProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoProcessoEdit/{planejamentoProcessoPK}", method = RequestMethod.GET)
	public ModelAndView planejamentoProcessoEdit(@PathVariable("planejamentoProcessoPK") Long planejamentoProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoProcesso/planejamentoProcessoEdit");
		PlanejamentoProcesso planejamentoProcesso = planejamentoProcessoService.getPlanejamentoProcessoByPlanejamentoProcessoPK(planejamentoProcessoPK);
		PlanejamentoProcessoForm planejamentoProcessoForm = planejamentoProcessoService.convertePlanejamentoProcesso(planejamentoProcesso);
		mv.addObject("planejamentoProcessoForm", planejamentoProcessoForm);
		mv.addObject("planejamentoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("planejamentoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable planejamentoPageable = new PageRequest(0, 200, Direction.ASC, "planejamentoNome");
		mv.addObject("planejamentoPage", planejamentoService.getPlanejamentoAll(planejamentoPageable));
		Pageable processoPageable = new PageRequest(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/planejamentoProcessoHome", method = RequestMethod.GET)
	public ModelAndView planejamentoProcessoHome(@Valid PlanejamentoProcessoByPlanejamentoForm planejamentoProcessoByPlanejamentoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoProcesso/planejamentoProcessoHome");
		
		List<PlanejamentoProcesso> planejamentoProcessoList = new ArrayList<PlanejamentoProcesso>();
		
		int planejamentoProcessoTotal = 0;
		
		if (planejamentoProcessoByPlanejamentoForm.getSearchProcessoNome() == null) {
			planejamentoProcessoByPlanejamentoForm.setSearchPlanejamentoNome("");
			planejamentoProcessoByPlanejamentoForm.setSearchProcessoNome("");
			if (planejamentoProcessoByPlanejamentoForm.getPlanejamentoProcessoSortTipo() == null) {
				planejamentoProcessoByPlanejamentoForm.setPlanejamentoProcessoSortTipo("ProcessoNome");	
			}
			
		}

		if (planejamentoProcessoByPlanejamentoForm.getPlanejamentoProcessoSortTipo().equalsIgnoreCase("PlanejamentoNome")
				|| planejamentoProcessoByPlanejamentoForm.getPlanejamentoProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"planejamento.planejamentoNome","processo.processoNome"); 
		
		} else if (planejamentoProcessoByPlanejamentoForm.getPlanejamentoProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","planejamento.planejamentoNome"); 

		}

		if ( ! planejamentoProcessoByPlanejamentoForm.getSearchProcessoNome().equalsIgnoreCase("")){
			planejamentoProcessoList = planejamentoProcessoService.getByProcessoNome(planejamentoProcessoByPlanejamentoForm.getSearchProcessoNome(),pageable);
			planejamentoProcessoTotal = planejamentoProcessoService.getByProcessoNome(planejamentoProcessoByPlanejamentoForm.getSearchProcessoNome()).size();
			
		} else {
			planejamentoProcessoList = planejamentoProcessoService.getByPlanejamentoNome(planejamentoProcessoByPlanejamentoForm.getSearchPlanejamentoNome(),pageable);
			planejamentoProcessoTotal = planejamentoProcessoService.getByPlanejamentoNome(planejamentoProcessoByPlanejamentoForm.getSearchPlanejamentoNome()).size();

		} 
		
		Page<PlanejamentoProcesso> planejamentoProcessoPage = new PageImpl<PlanejamentoProcesso>(planejamentoProcessoList,pageable,planejamentoProcessoTotal+1);
		
		mv.addObject("planejamentoProcessoPage", planejamentoProcessoPage);
		mv.addObject("page",planejamentoProcessoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoProcessoSave", method = RequestMethod.POST)
	public ModelAndView planejamentoProcessoSave(@Valid PlanejamentoProcessoForm planejamentoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return planejamentoProcessoAdd(planejamentoProcessoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/planejamentoProcessoHome");

		try {
			planejamentoProcessoService.save(planejamentoProcessoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("planejamentoProcessoUnique")) {
		        ObjectError error = new ObjectError("planejamentoNome","Relacionamento entre Planejamento e Processo já existente no cadastro.");
		        result.addError(error);			
		}
            return planejamentoProcessoAdd(planejamentoProcessoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/planejamentoProcessoRelMenu", method = RequestMethod.GET)
	public ModelAndView planejamentoProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("planejamentoProcesso/planejamentoProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/planejamentoProcessoRel001")
	public ModelAndView planejamentoProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("planejamentoProcesso/planejamentoProcessoRel001");
		Pageable planejamentoProcessoPageable = new PageRequest(0, 200, Direction.ASC, "planejamento.planejamentoNome","processo.processoNome");
		mv.addObject("planejamentoProcessoPage", planejamentoProcessoService.getPlanejamentoProcessoAll(planejamentoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/planejamentoProcessoView/{id}", method = RequestMethod.GET)
	public ModelAndView planejamentoProcessoView(@PathVariable("id") Long planejamentoProcessoId) {

		PlanejamentoProcesso planejamentoProcesso = planejamentoProcessoService.getPlanejamentoProcessoByPlanejamentoProcessoPK(planejamentoProcessoId);
		ModelAndView mv = new ModelAndView("planejamentoProcesso/planejamentoProcessoView");
		PlanejamentoProcessoForm planejamentoProcessoForm = planejamentoProcessoService.convertePlanejamentoProcessoView(planejamentoProcesso);
		PlanejamentoForm planejamentoForm = planejamentoService.convertePlanejamentoView(planejamentoProcesso.getPlanejamento());
		ProcessoForm processoForm = processoService.converteProcessoView(planejamentoProcesso.getProcesso());
		mv.addObject("planejamentoProcessoForm", planejamentoProcessoForm);
		mv.addObject("planejamentoForm", planejamentoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}