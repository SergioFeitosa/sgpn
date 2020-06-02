package br.com.j4business.saga.treinamentoimagem.controller;

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

import br.com.j4business.saga.treinamento.model.TreinamentoForm;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.imagem.model.ImagemForm;
import br.com.j4business.saga.imagem.service.ImagemService;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagem;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagemByTreinamentoForm;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagemForm;
import br.com.j4business.saga.treinamentoimagem.service.TreinamentoImagemService;

@Controller
public class TreinamentoImagemController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private TreinamentoImagemService treinamentoImagemService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/treinamentoImagemAdd", method = RequestMethod.GET)
	public ModelAndView treinamentoImagemAdd(TreinamentoImagemForm treinamentoImagemForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoImagem/treinamentoImagemAdd");
		treinamentoImagemForm = treinamentoImagemService.treinamentoImagemParametros(treinamentoImagemForm);
		mv.addObject("treinamentoImagemForm", treinamentoImagemForm);
		mv.addObject("treinamentoImagemPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("treinamentoImagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable imagemPageable = new PageRequest(0, 200, Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(imagemPageable));
		Pageable treinamentoPageable = new PageRequest(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoImagemCreate", method = RequestMethod.POST)
	public ModelAndView treinamentoImagemCreate(@Valid TreinamentoImagemForm treinamentoImagemForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return treinamentoImagemAdd(treinamentoImagemForm,pageable);
		}

		if (treinamentoImagemForm.getTreinamentoImagemPK() > 0) {
			return this.treinamentoImagemSave(treinamentoImagemForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (treinamentoImagemForm.getTreinamentoImagemStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<TreinamentoImagem> treinamentoImagemAtivoList = treinamentoImagemService.getTreinamentoImagemAtivoByTreinamentoPK(Long.parseLong(treinamentoImagemForm.getTreinamentoNome()));
				
				if (treinamentoImagemAtivoList.size() > 4) {
					ObjectError error = new ObjectError("treinamentoImagemStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return treinamentoImagemAdd(treinamentoImagemForm,pageable);
				}
				
			}
			
			treinamentoImagemService.create(treinamentoImagemForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoImagemUnique")) {
			        ObjectError error = new ObjectError("treinamentoNome","Relacionamento entre Treinamento e Imagem já existente no cadastro.");
			        result.addError(error);			
			}
            
			return treinamentoImagemAdd(treinamentoImagemForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/treinamentoImagemHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/treinamentoImagemDelete/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoImagemDelete(@PathVariable("id") long treinamentoImagemId, @Valid ImagemForm imagemForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/treinamentoImagemHome");
		
		
		TreinamentoImagem treinamentoImagem = treinamentoImagemService.getTreinamentoImagemByTreinamentoImagemPK(treinamentoImagemId);
		try {
			treinamentoImagemService.delete(treinamentoImagemId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Treinamento/Imagem excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Treinamento/Imagem não excluído. Existe(m) relacionamento(s) de Treinamento/Imagem ** "+ 
										  treinamentoImagem.getTreinamento().getTreinamentoNome() +
										  " / " +
										  treinamentoImagem.getImagem().getImagemNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoImagemEdit/{treinamentoImagemPK}", method = RequestMethod.GET)
	public ModelAndView treinamentoImagemEdit(@PathVariable("treinamentoImagemPK") Long treinamentoImagemPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoImagem/treinamentoImagemEdit");
		TreinamentoImagem treinamentoImagem = treinamentoImagemService.getTreinamentoImagemByTreinamentoImagemPK(treinamentoImagemPK);
		TreinamentoImagemForm treinamentoImagemForm = treinamentoImagemService.converteTreinamentoImagem(treinamentoImagem);
		mv.addObject("treinamentoImagemForm", treinamentoImagemForm);
		mv.addObject("treinamentoImagemPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("treinamentoImagemStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable imagemPageable = new PageRequest(0, 200, Direction.ASC, "imagemNome");
		mv.addObject("imagemPage", imagemService.getImagemAll(imagemPageable));
		Pageable treinamentoPageable = new PageRequest(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/treinamentoImagemHome", method = RequestMethod.GET)
	public ModelAndView treinamentoImagemHome(@Valid TreinamentoImagemByTreinamentoForm treinamentoImagemByTreinamentoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoImagem/treinamentoImagemHome");
		
		List<TreinamentoImagem> treinamentoImagemList = new ArrayList<TreinamentoImagem>();
		
		int treinamentoImagemTotal = 0;
		
		if (treinamentoImagemByTreinamentoForm.getSearchImagemNome() == null) {
			treinamentoImagemByTreinamentoForm.setSearchTreinamentoNome("");
			treinamentoImagemByTreinamentoForm.setSearchImagemNome("");
			if (treinamentoImagemByTreinamentoForm.getTreinamentoImagemSortTipo() == null) {
				treinamentoImagemByTreinamentoForm.setTreinamentoImagemSortTipo("ImagemNome");	
			}
			
		}

		if (treinamentoImagemByTreinamentoForm.getTreinamentoImagemSortTipo().equalsIgnoreCase("TreinamentoNome")
				|| treinamentoImagemByTreinamentoForm.getTreinamentoImagemSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"treinamento.treinamentoNome","imagem.imagemNome"); 
		
		} else if (treinamentoImagemByTreinamentoForm.getTreinamentoImagemSortTipo().equalsIgnoreCase("ImagemNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"imagem.imagemNome","treinamento.treinamentoNome"); 

		}

		if ( ! treinamentoImagemByTreinamentoForm.getSearchImagemNome().equalsIgnoreCase("")){
			treinamentoImagemList = treinamentoImagemService.getByImagemNome(treinamentoImagemByTreinamentoForm.getSearchImagemNome(),pageable);
			treinamentoImagemTotal = treinamentoImagemService.getByImagemNome(treinamentoImagemByTreinamentoForm.getSearchImagemNome()).size();
			
		} else {
			treinamentoImagemList = treinamentoImagemService.getByTreinamentoNome(treinamentoImagemByTreinamentoForm.getSearchTreinamentoNome(),pageable);
			treinamentoImagemTotal = treinamentoImagemService.getByTreinamentoNome(treinamentoImagemByTreinamentoForm.getSearchTreinamentoNome()).size();

		} 
		
		Page<TreinamentoImagem> treinamentoImagemPage = new PageImpl<TreinamentoImagem>(treinamentoImagemList,pageable,treinamentoImagemTotal+1);
		
		mv.addObject("treinamentoImagemPage", treinamentoImagemPage);
		mv.addObject("page",treinamentoImagemPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoImagemSave", method = RequestMethod.POST)
	public ModelAndView treinamentoImagemSave(@Valid TreinamentoImagemForm treinamentoImagemForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return treinamentoImagemAdd(treinamentoImagemForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/treinamentoImagemHome");

		try {
			
			List<TreinamentoImagem> treinamentoImagemAtivoList = treinamentoImagemService.getTreinamentoImagemAtivoByTreinamentoPK(treinamentoImagemForm.getTreinamentoPK());
			
			if (treinamentoImagemAtivoList.size() > 5) {
				ObjectError error = new ObjectError("treinamentoImagemStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return treinamentoImagemAdd(treinamentoImagemForm,pageable);
			}
			
			treinamentoImagemService.save(treinamentoImagemForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoImagemUnique")) {
		        ObjectError error = new ObjectError("treinamentoNome","Relacionamento entre Treinamento e Imagem já existente no cadastro.");
		        result.addError(error);			
		}
            return treinamentoImagemAdd(treinamentoImagemForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/treinamentoImagemRelMenu", method = RequestMethod.GET)
	public ModelAndView treinamentoImagemRelMenu() {

		ModelAndView mv = new ModelAndView("treinamentoImagem/treinamentoImagemRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/treinamentoImagemRel001")
	public ModelAndView treinamentoImagemRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoImagem/treinamentoImagemRel001");
		Pageable treinamentoImagemPageable = new PageRequest(0, 200, Direction.ASC, "treinamento.treinamentoNome","imagem.imagemNome");
		mv.addObject("treinamentoImagemPage", treinamentoImagemService.getTreinamentoImagemAll(treinamentoImagemPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoImagemView/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoImagemView(@PathVariable("id") Long treinamentoImagemId) {

		TreinamentoImagem treinamentoImagem = treinamentoImagemService.getTreinamentoImagemByTreinamentoImagemPK(treinamentoImagemId);
		ModelAndView mv = new ModelAndView("treinamentoImagem/treinamentoImagemView");
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamentoView(treinamentoImagem.getTreinamento());
		ImagemForm imagemForm = imagemService.converteImagemView(treinamentoImagem.getImagem());
		TreinamentoImagemForm treinamentoImagemForm = treinamentoImagemService.converteTreinamentoImagemView(treinamentoImagem);
		mv.addObject("treinamentoImagemForm", treinamentoImagemForm);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("imagemForm", imagemForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}