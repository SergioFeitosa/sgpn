package br.com.j4business.saga.ocorrenciaatendimento.controller;

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
import br.com.j4business.saga.atendimento.model.AtendimentoForm;
import br.com.j4business.saga.atendimento.service.AtendimentoService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.ocorrencia.model.OcorrenciaForm;
import br.com.j4business.saga.ocorrencia.service.OcorrenciaService;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimento;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimentoByOcorrenciaForm;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimentoForm;
import br.com.j4business.saga.ocorrenciaatendimento.service.OcorrenciaAtendimentoService;

@Controller
public class OcorrenciaAtendimentoController {

	@Autowired
	private AtendimentoService atendimentoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private OcorrenciaAtendimentoService ocorrenciaAtendimentoService;

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/ocorrenciaAtendimentoAdd")
	public ModelAndView ocorrenciaAtendimentoAdd(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrenciaAtendimento/ocorrenciaAtendimentoAdd");
		ocorrenciaAtendimentoForm = ocorrenciaAtendimentoService.ocorrenciaAtendimentoParametros(ocorrenciaAtendimentoForm);
		mv.addObject("ocorrenciaAtendimentoForm", ocorrenciaAtendimentoForm);
		mv.addObject("ocorrenciaAtendimentoStatusValues", AtributoStatus.values());
		Pageable atendimentoPageable = PageRequest.of(0, 200, Direction.ASC, "atendimentoNome");
		mv.addObject("atendimentoPage", atendimentoService.getAtendimentoAll(atendimentoPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable ocorrenciaPageable = PageRequest.of(0, 200, Direction.ASC, "ocorrenciaNome");
		mv.addObject("ocorrenciaPage", ocorrenciaService.getOcorrenciaAll(ocorrenciaPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/ocorrenciaAtendimentoCreate")
	public ModelAndView goOcorrenciaAtendimentoCreate(@Valid OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return ocorrenciaAtendimentoAdd(ocorrenciaAtendimentoForm,pageable);
		}

		if (ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoPK() > 0) {
			return this.ocorrenciaAtendimentoSave(ocorrenciaAtendimentoForm, result, attributes,pageable);
			
		}
		
		try {
			ocorrenciaAtendimentoService.create(ocorrenciaAtendimentoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("ocorrenciaAtendimentoUnique")) {
			        ObjectError error = new ObjectError("ocorrenciaNome","Relacionamento entre Ocorrência e Atendimento já existente no cadastro.");
			        result.addError(error);			
			}
            
			return ocorrenciaAtendimentoAdd(ocorrenciaAtendimentoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/ocorrenciaAtendimentoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/ocorrenciaAtendimentoDelete/{id}")
	public ModelAndView goOcorrenciaAtendimentoDelete(@PathVariable("id") long ocorrenciaAtendimentoId, @Valid AtendimentoForm atendimentoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/ocorrenciaAtendimentoHome");
		
		
		OcorrenciaAtendimento ocorrenciaAtendimento = ocorrenciaAtendimentoService.getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(ocorrenciaAtendimentoId);
		try {
			ocorrenciaAtendimentoService.delete(ocorrenciaAtendimentoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Ocorrencia/Atendimento excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Ocorrencia/Atendimento não excluído. Existe(m) relacionamento(s) de Ocorrencia/Atendimento ** "+ 
										  ocorrenciaAtendimento.getOcorrencia().getOcorrenciaNome() +
										  " / " +
										  ocorrenciaAtendimento.getAtendimento().getAtendimentoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/ocorrenciaAtendimentoEdit/{ocorrenciaAtendimentoPK}")
	public ModelAndView goOcorrenciaAtendimentoEdit(@PathVariable("ocorrenciaAtendimentoPK") Long ocorrenciaAtendimentoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrenciaAtendimento/ocorrenciaAtendimentoEdit");
		OcorrenciaAtendimento ocorrenciaAtendimento = ocorrenciaAtendimentoService.getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(ocorrenciaAtendimentoPK);
		OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm = ocorrenciaAtendimentoService.converteOcorrenciaAtendimento(ocorrenciaAtendimento);
		mv.addObject("ocorrenciaAtendimentoForm", ocorrenciaAtendimentoForm);
		mv.addObject("ocorrenciaAtendimentoStatusValues", AtributoStatus.values());

		Pageable atendimentoPageable = PageRequest.of(0, 200, Direction.ASC, "atendimentoNome");
		mv.addObject("atendimentoPage", atendimentoService.getAtendimentoAll(atendimentoPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable ocorrenciaPageable = PageRequest.of(0, 200, Direction.ASC, "ocorrenciaNome");
		mv.addObject("ocorrenciaPage", ocorrenciaService.getOcorrenciaAll(ocorrenciaPageable));
		
		ocorrenciaAtendimento.getOcorrenciaAtendimentoSequencia();
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/ocorrenciaAtendimentoHome")
	public ModelAndView goOcorrenciaAtendimentoHome(@Valid OcorrenciaAtendimentoByOcorrenciaForm ocorrenciaAtendimentoByOcorrenciaForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrenciaAtendimento/ocorrenciaAtendimentoHome");
		
		List<OcorrenciaAtendimento> ocorrenciaAtendimentoList = new ArrayList<OcorrenciaAtendimento>();
		
		int ocorrenciaAtendimentoTotal = 0;
		
		if (ocorrenciaAtendimentoByOcorrenciaForm.getSearchAtendimentoNome() == null) {
			ocorrenciaAtendimentoByOcorrenciaForm.setSearchOcorrenciaNome("");
			ocorrenciaAtendimentoByOcorrenciaForm.setSearchAtendimentoNome("");
			if (ocorrenciaAtendimentoByOcorrenciaForm.getOcorrenciaAtendimentoSortTipo() == null) {
				ocorrenciaAtendimentoByOcorrenciaForm.setOcorrenciaAtendimentoSortTipo("AtendimentoNome");	
			}
			
		}

		if (ocorrenciaAtendimentoByOcorrenciaForm.getOcorrenciaAtendimentoSortTipo().equalsIgnoreCase("OcorrenciaNome")
				|| ocorrenciaAtendimentoByOcorrenciaForm.getOcorrenciaAtendimentoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"ocorrencia.ocorrenciaNome","ocorrenciaAtendimentoSequencia"); 
		
		} else if (ocorrenciaAtendimentoByOcorrenciaForm.getOcorrenciaAtendimentoSortTipo().equalsIgnoreCase("AtendimentoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"atendimento.atendimentoNome","ocorrenciaAtendimentoSequencia"); 

		} else if (ocorrenciaAtendimentoByOcorrenciaForm.getOcorrenciaAtendimentoSortTipo().equalsIgnoreCase("Sequencia")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"ocorrenciaAtendimentoSequencia","ocorrencia.ocorrenciaNome"); 

		}

		if ( ! ocorrenciaAtendimentoByOcorrenciaForm.getSearchAtendimentoNome().equalsIgnoreCase("")){
			ocorrenciaAtendimentoList = ocorrenciaAtendimentoService.getByAtendimentoNome(ocorrenciaAtendimentoByOcorrenciaForm.getSearchAtendimentoNome(),pageable);
			ocorrenciaAtendimentoTotal = ocorrenciaAtendimentoService.getByAtendimentoNome(ocorrenciaAtendimentoByOcorrenciaForm.getSearchAtendimentoNome()).size();
		} else {
			ocorrenciaAtendimentoList = ocorrenciaAtendimentoService.getByOcorrenciaNome(ocorrenciaAtendimentoByOcorrenciaForm.getSearchOcorrenciaNome(),pageable);
			ocorrenciaAtendimentoTotal = ocorrenciaAtendimentoService.getByOcorrenciaNome(ocorrenciaAtendimentoByOcorrenciaForm.getSearchOcorrenciaNome()).size();
		}
		
		Page<OcorrenciaAtendimento> ocorrenciaAtendimentoPage = new PageImpl<OcorrenciaAtendimento>(ocorrenciaAtendimentoList,pageable,ocorrenciaAtendimentoTotal+1);
		
		mv.addObject("ocorrenciaAtendimentoPage", ocorrenciaAtendimentoPage);
		mv.addObject("page",ocorrenciaAtendimentoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/ocorrenciaAtendimentoSave")
	public ModelAndView ocorrenciaAtendimentoSave(@Valid OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return ocorrenciaAtendimentoAdd(ocorrenciaAtendimentoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/ocorrenciaAtendimentoHome");

		try {
			ocorrenciaAtendimentoService.save(ocorrenciaAtendimentoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("ocorrenciaAtendimentoUnique")) {
		        ObjectError error = new ObjectError("ocorrenciaNome","Relacionamento entre Ocorrência e Atendimento já existente no cadastro.");
		        result.addError(error);			
		}
            return ocorrenciaAtendimentoAdd(ocorrenciaAtendimentoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
		
	}

	@GetMapping(path = "/ocorrenciaAtendimentoRelMenu")
	public ModelAndView goOcorrenciaAtendimentoRelMenu() {

		ModelAndView mv = new ModelAndView("ocorrenciaAtendimento/ocorrenciaAtendimentoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/ocorrenciaAtendimentoRel001")
	public ModelAndView goOcorrenciaAtendimentoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("ocorrenciaAtendimento/ocorrenciaAtendimentoRel001");
		Pageable ocorrenciaAtendimentoPageable = PageRequest.of(0, 200, Direction.ASC, "ocorrencia.ocorrenciaNome","atendimento.atendimentoNome");
		mv.addObject("ocorrenciaAtendimentoPage", ocorrenciaAtendimentoService.getOcorrenciaAtendimentoAll(ocorrenciaAtendimentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/ocorrenciaAtendimentoView/{id}")
	public ModelAndView goOcorrenciaAtendimentoView(@PathVariable("id") Long ocorrenciaAtendimentoId) {

		OcorrenciaAtendimento ocorrenciaAtendimento = ocorrenciaAtendimentoService.getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(ocorrenciaAtendimentoId);
		ModelAndView mv = new ModelAndView("ocorrenciaAtendimento/ocorrenciaAtendimentoView");
		OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm = ocorrenciaAtendimentoService.converteOcorrenciaAtendimentoView(ocorrenciaAtendimento);
		OcorrenciaForm ocorrenciaForm = ocorrenciaService.converteOcorrenciaView(ocorrenciaAtendimento.getOcorrencia());
		AtendimentoForm atendimentoForm = atendimentoService.converteAtendimentoView(ocorrenciaAtendimento.getAtendimento());
		mv.addObject("ocorrenciaAtendimentoForm", ocorrenciaAtendimentoForm);
		mv.addObject("ocorrenciaForm", ocorrenciaForm);
		mv.addObject("atendimentoForm", atendimentoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}