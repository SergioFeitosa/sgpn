package br.com.j4business.saga.treinamentovideo.controller;

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
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagem;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.video.model.VideoForm;
import br.com.j4business.saga.video.service.VideoService;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideo;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideoByTreinamentoForm;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideoForm;
import br.com.j4business.saga.treinamentovideo.service.TreinamentoVideoService;

@Controller
public class TreinamentoVideoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private TreinamentoVideoService treinamentoVideoService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/treinamentoVideoAdd", method = RequestMethod.GET)
	public ModelAndView treinamentoVideoAdd(TreinamentoVideoForm treinamentoVideoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoVideo/treinamentoVideoAdd");
		treinamentoVideoForm = treinamentoVideoService.treinamentoVideoParametros(treinamentoVideoForm);
		mv.addObject("treinamentoVideoForm", treinamentoVideoForm);
		mv.addObject("treinamentoVideoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("treinamentoVideoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable videoPageable = new PageRequest(0, 200, Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(videoPageable));
		Pageable treinamentoPageable = new PageRequest(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoVideoCreate", method = RequestMethod.POST)
	public ModelAndView treinamentoVideoCreate(@Valid TreinamentoVideoForm treinamentoVideoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return treinamentoVideoAdd(treinamentoVideoForm,pageable);
		}

		if (treinamentoVideoForm.getTreinamentoVideoPK() > 0) {
			return this.treinamentoVideoSave(treinamentoVideoForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (treinamentoVideoForm.getTreinamentoVideoStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<TreinamentoVideo> treinamentoVideoAtivoList = treinamentoVideoService.getTreinamentoVideoAtivoByTreinamentoPK(Long.parseLong(treinamentoVideoForm.getTreinamentoNome()));
				
				if (treinamentoVideoAtivoList.size() > 4) {
					ObjectError error = new ObjectError("treinamentoVideoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return treinamentoVideoAdd(treinamentoVideoForm,pageable);
				}
				
			}
			
			treinamentoVideoService.create(treinamentoVideoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoVideoUnique")) {
			        ObjectError error = new ObjectError("treinamentoNome","Relacionamento entre Treinamento e Video já existente no cadastro.");
			        result.addError(error);			
			}
            
			return treinamentoVideoAdd(treinamentoVideoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/treinamentoVideoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/treinamentoVideoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoVideoDelete(@PathVariable("id") long treinamentoVideoId, @Valid VideoForm videoForm, BindingResult result, RedirectAttributes attributes) {


		ModelAndView mv = new ModelAndView("redirect:/treinamentoVideoHome");
		
		
		TreinamentoVideo treinamentoVideo = treinamentoVideoService.getTreinamentoVideoByTreinamentoVideoPK(treinamentoVideoId);
		try {
			treinamentoVideoService.delete(treinamentoVideoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Treinamento/Video excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Treinamento/Video não excluído. Existe(m) relacionamento(s) de Treinamento/Video ** "+ 
										  treinamentoVideo.getTreinamento().getTreinamentoNome() +
										  " / " +
										  treinamentoVideo.getVideo().getVideoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/treinamentoVideoEdit/{treinamentoVideoPK}", method = RequestMethod.GET)
	public ModelAndView treinamentoVideoEdit(@PathVariable("treinamentoVideoPK") Long treinamentoVideoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoVideo/treinamentoVideoEdit");
		TreinamentoVideo treinamentoVideo = treinamentoVideoService.getTreinamentoVideoByTreinamentoVideoPK(treinamentoVideoPK);
		TreinamentoVideoForm treinamentoVideoForm = treinamentoVideoService.converteTreinamentoVideo(treinamentoVideo);
		mv.addObject("treinamentoVideoForm", treinamentoVideoForm);
		mv.addObject("treinamentoVideoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("treinamentoVideoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable videoPageable = new PageRequest(0, 200, Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(videoPageable));
		Pageable treinamentoPageable = new PageRequest(0, 200, Direction.ASC, "treinamentoNome");
		mv.addObject("treinamentoPage", treinamentoService.getTreinamentoAll(treinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/treinamentoVideoHome", method = RequestMethod.GET)
	public ModelAndView treinamentoVideoHome(@Valid TreinamentoVideoByTreinamentoForm treinamentoVideoByTreinamentoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoVideo/treinamentoVideoHome");
		
		List<TreinamentoVideo> treinamentoVideoList = new ArrayList<TreinamentoVideo>();
		
		int treinamentoVideoTotal = 0;
		
		if (treinamentoVideoByTreinamentoForm.getSearchVideoNome() == null) {
			treinamentoVideoByTreinamentoForm.setSearchTreinamentoNome("");
			treinamentoVideoByTreinamentoForm.setSearchVideoNome("");
			if (treinamentoVideoByTreinamentoForm.getTreinamentoVideoSortTipo() == null) {
				treinamentoVideoByTreinamentoForm.setTreinamentoVideoSortTipo("VideoNome");	
			}
			
		}

		if (treinamentoVideoByTreinamentoForm.getTreinamentoVideoSortTipo().equalsIgnoreCase("TreinamentoNome")
				|| treinamentoVideoByTreinamentoForm.getTreinamentoVideoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"treinamento.treinamentoNome","video.videoNome"); 
		
		} else if (treinamentoVideoByTreinamentoForm.getTreinamentoVideoSortTipo().equalsIgnoreCase("VideoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"video.videoNome","treinamento.treinamentoNome"); 

		}

		if ( ! treinamentoVideoByTreinamentoForm.getSearchVideoNome().equalsIgnoreCase("")){
			treinamentoVideoList = treinamentoVideoService.getByVideoNome(treinamentoVideoByTreinamentoForm.getSearchVideoNome(),pageable);
			treinamentoVideoTotal = treinamentoVideoService.getByVideoNome(treinamentoVideoByTreinamentoForm.getSearchVideoNome()).size();
			
		} else {
			treinamentoVideoList = treinamentoVideoService.getByTreinamentoNome(treinamentoVideoByTreinamentoForm.getSearchTreinamentoNome(),pageable);
			treinamentoVideoTotal = treinamentoVideoService.getByTreinamentoNome(treinamentoVideoByTreinamentoForm.getSearchTreinamentoNome()).size();

		} 
		
		Page<TreinamentoVideo> treinamentoVideoPage = new PageImpl<TreinamentoVideo>(treinamentoVideoList,pageable,treinamentoVideoTotal+1);
		
		mv.addObject("treinamentoVideoPage", treinamentoVideoPage);
		mv.addObject("page",treinamentoVideoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoVideoSave", method = RequestMethod.POST)
	public ModelAndView treinamentoVideoSave(@Valid TreinamentoVideoForm treinamentoVideoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return treinamentoVideoAdd(treinamentoVideoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/treinamentoVideoHome");

		try {
			
			List<TreinamentoVideo> treinamentoVideoAtivoList = treinamentoVideoService.getTreinamentoVideoAtivoByTreinamentoPK(treinamentoVideoForm.getTreinamentoPK());
			
			if (treinamentoVideoAtivoList.size() > 5) {
				ObjectError error = new ObjectError("treinamentoVideoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return treinamentoVideoAdd(treinamentoVideoForm,pageable);
			}
			
			treinamentoVideoService.save(treinamentoVideoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("treinamentoVideoUnique")) {
		        ObjectError error = new ObjectError("treinamentoNome","Relacionamento entre Treinamento e Video já existente no cadastro.");
		        result.addError(error);			
		}
            return treinamentoVideoAdd(treinamentoVideoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/treinamentoVideoRelMenu", method = RequestMethod.GET)
	public ModelAndView treinamentoVideoRelMenu() {

		ModelAndView mv = new ModelAndView("treinamentoVideo/treinamentoVideoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@RequestMapping("/treinamentoVideoRel001")
	public ModelAndView treinamentoVideoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("treinamentoVideo/treinamentoVideoRel001");
		Pageable treinamentoVideoPageable = new PageRequest(0, 200, Direction.ASC, "treinamento.treinamentoNome","video.videoNome");
		mv.addObject("treinamentoVideoPage", treinamentoVideoService.getTreinamentoVideoAll(treinamentoVideoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/treinamentoVideoView/{id}", method = RequestMethod.GET)
	public ModelAndView treinamentoVideoView(@PathVariable("id") Long treinamentoVideoId) {

		TreinamentoVideo treinamentoVideo = treinamentoVideoService.getTreinamentoVideoByTreinamentoVideoPK(treinamentoVideoId);
		ModelAndView mv = new ModelAndView("treinamentoVideo/treinamentoVideoView");
		TreinamentoForm treinamentoForm = treinamentoService.converteTreinamentoView(treinamentoVideo.getTreinamento());
		VideoForm videoForm = videoService.converteVideoView(treinamentoVideo.getVideo());
		TreinamentoVideoForm treinamentoVideoForm = treinamentoVideoService.converteTreinamentoVideoView(treinamentoVideo);
		mv.addObject("treinamentoVideoForm", treinamentoVideoForm);
		mv.addObject("treinamentoForm", treinamentoForm);
		mv.addObject("videoForm", videoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}