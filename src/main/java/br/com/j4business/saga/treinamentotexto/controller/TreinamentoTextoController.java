package br.com.j4business.saga.treinamentotexto.controller;

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
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.texto.model.TextoForm;
import br.com.j4business.saga.texto.service.TextoService;
import br.com.j4business.saga.treinamento.model.TreinamentoForm;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTexto;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTextoByTreinamentoForm;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTextoForm;
import br.com.j4business.saga.treinamentotexto.service.TreinamentoTextoService;

@Controller
public class TreinamentoTextoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TextoService textoService;

	@Autowired
	private TreinamentoTextoService treinamentoTextoService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/treinamentoTextoAdd")
	public ModelAndView treinamentoTextoAdd(TreinamentoTextoForm treinamentoTextoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoTexto/treinamentoTextoAdd");
		treinamentoTextoForm = treinamentoTextoService.treinamentoTextoParametros(treinamentoTextoForm);
		mv.addObject("treinamentoTextoForm", treinamentoTextoForm);
		mv.addObject("treinamentoTextoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("treinamentoTextoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable textoPageable = PageRequest.of(0, 200, Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(textoPageable));
		Pageable treinamentoPageable = PageRequest.of(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/treinamentoTextoCreate")
	public ModelAndView treinamentoTextoCreate(@Valid TreinamentoTextoForm treinamentoTextoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return treinamentoTextoAdd(treinamentoTextoForm,pageable);
		}

		if (treinamentoTextoForm.getTreinamentoTextoPK() > 0) {
			return this.treinamentoTextoSave(treinamentoTextoForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (treinamentoTextoForm.getTreinamentoTextoStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<TreinamentoTexto> treinamentoTextoAtivoList = treinamentoTextoService.getTreinamentoTextoAtivoByTreinamentoPK(Long.parseLong(treinamentoTextoForm.getTreinamentoNome()));
				
				if (treinamentoTextoAtivoList.size() > 4) {
					ObjectError error = new ObjectError("treinamentoTextoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return treinamentoTextoAdd(treinamentoTextoForm,pageable);
				}
				
			}
			
			treinamentoTextoService.create(treinamentoTextoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoTextoUnique")) {
			        ObjectError error = new ObjectError("treinamentoNome","Relacionamento entre Treinamento e Texto já existente no cadastro.");
			        result.addError(error);			
			}
            
			return treinamentoTextoAdd(treinamentoTextoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/treinamentoTextoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/treinamentoTextoDelete/{id}")
	public ModelAndView treinamentoTextoDelete(@PathVariable("id") long treinamentoTextoId, @Valid TextoForm textoForm, BindingResult result, RedirectAttributes attributes) {


		ModelAndView mv = new ModelAndView("redirect:/treinamentoTextoHome");
		
		
		TreinamentoTexto treinamentoTexto = treinamentoTextoService.getTreinamentoTextoByTreinamentoTextoPK(treinamentoTextoId);
		try {
			treinamentoTextoService.delete(treinamentoTextoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Treinamento/Texto excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Treinamento/Texto não excluído. Existe(m) relacionamento(s) de Treinamento/Texto ** "+ 
										  treinamentoTexto.getTreinamento().getTreinamentoNome() +
										  " / " +
										  treinamentoTexto.getTexto().getTextoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/treinamentoTextoEdit/{treinamentoTextoPK}")
	public ModelAndView treinamentoTextoEdit(@PathVariable("treinamentoTextoPK") Long treinamentoTextoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoTexto/treinamentoTextoEdit");
		TreinamentoTexto treinamentoTexto = treinamentoTextoService.getTreinamentoTextoByTreinamentoTextoPK(treinamentoTextoPK);
		TreinamentoTextoForm treinamentoTextoForm = treinamentoTextoService.converteTreinamentoTexto(treinamentoTexto);
		mv.addObject("treinamentoTextoForm", treinamentoTextoForm);
		mv.addObject("treinamentoTextoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("treinamentoTextoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable textoPageable = PageRequest.of(0, 200, Direction.ASC, "textoNome");
		mv.addObject("textoPage", textoService.getTextoAll(textoPageable));
		Pageable treinamentoPageable = PageRequest.of(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/treinamentoTextoHome")
	public ModelAndView treinamentoTextoHome(@Valid TreinamentoTextoByTreinamentoForm treinamentoTextoByTreinamentoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoTexto/treinamentoTextoHome");
		
		List<TreinamentoTexto> treinamentoTextoList = new ArrayList<TreinamentoTexto>();
		
		int treinamentoTextoTotal = 0;
		
		if (treinamentoTextoByTreinamentoForm.getSearchTextoNome() == null) {
			treinamentoTextoByTreinamentoForm.setSearchTreinamentoNome("");
			treinamentoTextoByTreinamentoForm.setSearchTextoNome("");
			if (treinamentoTextoByTreinamentoForm.getTreinamentoTextoSortTipo() == null) {
				treinamentoTextoByTreinamentoForm.setTreinamentoTextoSortTipo("TextoNome");	
			}
			
		}

		if (treinamentoTextoByTreinamentoForm.getTreinamentoTextoSortTipo().equalsIgnoreCase("TreinamentoNome")
				|| treinamentoTextoByTreinamentoForm.getTreinamentoTextoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"treinamento.treinamentoNome","texto.textoNome"); 
		
		} else if (treinamentoTextoByTreinamentoForm.getTreinamentoTextoSortTipo().equalsIgnoreCase("TextoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"texto.textoNome","treinamento.treinamentoNome"); 

		}

		if ( ! treinamentoTextoByTreinamentoForm.getSearchTextoNome().equalsIgnoreCase("")){
			treinamentoTextoList = treinamentoTextoService.getByTextoNome(treinamentoTextoByTreinamentoForm.getSearchTextoNome(),pageable);
			treinamentoTextoTotal = treinamentoTextoService.getByTextoNome(treinamentoTextoByTreinamentoForm.getSearchTextoNome()).size();
			
		} else {
			treinamentoTextoList = treinamentoTextoService.getByTreinamentoNome(treinamentoTextoByTreinamentoForm.getSearchTreinamentoNome(),pageable);
			treinamentoTextoTotal = treinamentoTextoService.getByTreinamentoNome(treinamentoTextoByTreinamentoForm.getSearchTreinamentoNome()).size();

		} 
		
		Page<TreinamentoTexto> treinamentoTextoPage = new PageImpl<TreinamentoTexto>(treinamentoTextoList,pageable,treinamentoTextoTotal+1);
		
		mv.addObject("treinamentoTextoPage", treinamentoTextoPage);
		mv.addObject("page",treinamentoTextoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/treinamentoTextoSave")
	public ModelAndView treinamentoTextoSave(@Valid TreinamentoTextoForm treinamentoTextoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return treinamentoTextoAdd(treinamentoTextoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/treinamentoTextoHome");

		try {
			
			List<TreinamentoTexto> treinamentoTextoAtivoList = treinamentoTextoService.getTreinamentoTextoAtivoByTreinamentoPK(treinamentoTextoForm.getTreinamentoPK());
			
			if (treinamentoTextoAtivoList.size() > 5) {
				ObjectError error = new ObjectError("treinamentoTextoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return treinamentoTextoAdd(treinamentoTextoForm,pageable);
			}
			
			treinamentoTextoService.save(treinamentoTextoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoTextoUnique")) {
		        ObjectError error = new ObjectError("treinamentoNome","Relacionamento entre Treinamento e Texto já existente no cadastro.");
		        result.addError(error);			
		}
            return treinamentoTextoAdd(treinamentoTextoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/treinamentoTextoRelMenu")
	public ModelAndView treinamentoTextoRelMenu() {

		ModelAndView mv = new ModelAndView("treinamentoTexto/treinamentoTextoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/treinamentoTextoRel001")
	public ModelAndView treinamentoTextoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoTexto/treinamentoTextoRel001");
		Pageable treinamentoTextoPageable = PageRequest.of(0, 200, Direction.ASC, "treinamento.treinamentoNome","texto.textoNome");
		mv.addObject("treinamentoTextoPage", treinamentoTextoService.getTreinamentoTextoAll(treinamentoTextoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/treinamentoTextoView/{id}")
	public ModelAndView treinamentoTextoView(@PathVariable("id") Long treinamentoTextoId) {

		TreinamentoTexto treinamentoTexto = treinamentoTextoService.getTreinamentoTextoByTreinamentoTextoPK(treinamentoTextoId);
		ModelAndView mv = new ModelAndView("treinamentoTexto/treinamentoTextoView");
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamentoView(treinamentoTexto.getTreinamento());
		TextoForm textoForm = textoService.converteTextoView(treinamentoTexto.getTexto());
		TreinamentoTextoForm treinamentoTextoForm = treinamentoTextoService.converteTreinamentoTextoView(treinamentoTexto);
		mv.addObject("treinamentoTextoForm", treinamentoTextoForm);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("textoForm", textoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}