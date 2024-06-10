package br.com.j4business.saga.contratovideo.controller;

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

import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.video.model.VideoForm;
import br.com.j4business.saga.video.service.VideoService;
import br.com.j4business.saga.contratovideo.model.ContratoVideo;
import br.com.j4business.saga.contratovideo.model.ContratoVideoByContratoForm;
import br.com.j4business.saga.contratovideo.model.ContratoVideoForm;
import br.com.j4business.saga.contratovideo.service.ContratoVideoService;

@Controller
public class ContratoVideoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private ContratoVideoService contratoVideoService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/contratoVideoAdd")
	public ModelAndView contratoVideoAdd(ContratoVideoForm contratoVideoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoVideo/contratoVideoAdd");
		contratoVideoForm = contratoVideoService.contratoVideoParametros(contratoVideoForm);
		mv.addObject("contratoVideoForm", contratoVideoForm);
		mv.addObject("contratoVideoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("contratoVideoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable videoPageable = PageRequest.of(0, 200, Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(videoPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/contratoVideoCreate")
	public ModelAndView contratoVideoCreate(@Valid ContratoVideoForm contratoVideoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return contratoVideoAdd(contratoVideoForm,pageable);
		}

		if (contratoVideoForm.getContratoVideoPK() > 0) {
			return this.contratoVideoSave(contratoVideoForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (contratoVideoForm.getContratoVideoStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<ContratoVideo> contratoVideoAtivoList = contratoVideoService.getContratoVideoAtivoByContratoPK(Long.parseLong(contratoVideoForm.getContratoNome()));
				
				if (contratoVideoAtivoList.size() > 4) {
					ObjectError error = new ObjectError("contratoVideoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return contratoVideoAdd(contratoVideoForm,pageable);
				}
				
			}
			
			contratoVideoService.create(contratoVideoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoVideoUnique")) {
			        ObjectError error = new ObjectError("contratoNome","Relacionamento entre Contrato e Video já existente no cadastro.");
			        result.addError(error);			
			}
            
			return contratoVideoAdd(contratoVideoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/contratoVideoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/contratoVideoDelete/{id}")
	public ModelAndView contratoVideoDelete(@PathVariable("id") long contratoVideoId, @Valid VideoForm videoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/contratoVideoHome");
		
		
		ContratoVideo contratoVideo = contratoVideoService.getContratoVideoByContratoVideoPK(contratoVideoId);
		try {
			contratoVideoService.delete(contratoVideoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Contrato/Video excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Contrato/Video não excluído. Existe(m) relacionamento(s) de Contrato/Video ** "+ 
										  contratoVideo.getContrato().getContratoNome() +
										  " / " +
										  contratoVideo.getVideo().getVideoNome() +
										  " ** no cadastro.");
	    }

		return mv;
	}

	@GetMapping(path = "/contratoVideoEdit/{contratoVideoPK}")
	public ModelAndView contratoVideoEdit(@PathVariable("contratoVideoPK") Long contratoVideoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoVideo/contratoVideoEdit");
		ContratoVideo contratoVideo = contratoVideoService.getContratoVideoByContratoVideoPK(contratoVideoPK);
		ContratoVideoForm contratoVideoForm = contratoVideoService.converteContratoVideo(contratoVideo);
		mv.addObject("contratoVideoForm", contratoVideoForm);
		mv.addObject("contratoVideoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("contratoVideoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable videoPageable = PageRequest.of(0, 200, Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(videoPageable));
		Pageable contratoPageable = PageRequest.of(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/contratoVideoHome")
	public ModelAndView contratoVideoHome(@Valid ContratoVideoByContratoForm contratoVideoByContratoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoVideo/contratoVideoHome");
		
		List<ContratoVideo> contratoVideoList = new ArrayList<ContratoVideo>();
		
		int contratoVideoTotal = 0;
		
		if (contratoVideoByContratoForm.getSearchVideoNome() == null) {
			contratoVideoByContratoForm.setSearchContratoNome("");
			contratoVideoByContratoForm.setSearchVideoNome("");
			if (contratoVideoByContratoForm.getContratoVideoSortTipo() == null) {
				contratoVideoByContratoForm.setContratoVideoSortTipo("VideoNome");	
			}
			
		}

		if (contratoVideoByContratoForm.getContratoVideoSortTipo().equalsIgnoreCase("ContratoNome")
				|| contratoVideoByContratoForm.getContratoVideoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","video.videoNome"); 
		
		} else if (contratoVideoByContratoForm.getContratoVideoSortTipo().equalsIgnoreCase("VideoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"video.videoNome","contrato.contratoNome"); 

		}

		if ( ! contratoVideoByContratoForm.getSearchVideoNome().equalsIgnoreCase("")){
			contratoVideoList = contratoVideoService.getByVideoNome(contratoVideoByContratoForm.getSearchVideoNome(),pageable);
			contratoVideoTotal = contratoVideoService.getByVideoNome(contratoVideoByContratoForm.getSearchVideoNome()).size();
			
		} else {
			contratoVideoList = contratoVideoService.getByContratoNome(contratoVideoByContratoForm.getSearchContratoNome(),pageable);
			contratoVideoTotal = contratoVideoService.getByContratoNome(contratoVideoByContratoForm.getSearchContratoNome()).size();

		} 
		
		Page<ContratoVideo> contratoVideoPage = new PageImpl<ContratoVideo>(contratoVideoList,pageable,contratoVideoTotal+1);
		
		mv.addObject("contratoVideoPage", contratoVideoPage);
		mv.addObject("page",contratoVideoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/contratoVideoSave")
	public ModelAndView contratoVideoSave(@Valid ContratoVideoForm contratoVideoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return contratoVideoAdd(contratoVideoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/contratoVideoHome");

		try {
			
			List<ContratoVideo> contratoVideoAtivoList = contratoVideoService.getContratoVideoAtivoByContratoPK(contratoVideoForm.getContratoPK());
			
			if (contratoVideoAtivoList.size() > 5) {
				ObjectError error = new ObjectError("contratoVideoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return contratoVideoAdd(contratoVideoForm,pageable);
			}
			
			contratoVideoService.save(contratoVideoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("contratoVideoUnique")) {
		        ObjectError error = new ObjectError("contratoNome","Relacionamento entre Contrato e Video já existente no cadastro.");
		        result.addError(error);			
		}
            return contratoVideoAdd(contratoVideoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/contratoVideoRelMenu")
	public ModelAndView contratoVideoRelMenu() {

		ModelAndView mv = new ModelAndView("contratoVideo/contratoVideoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/contratoVideoRel001")
	public ModelAndView contratoVideoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("contratoVideo/contratoVideoRel001");
		Pageable contratoVideoPageable = PageRequest.of(0, 200, Direction.ASC, "contrato.contratoNome","video.videoNome");
		mv.addObject("contratoVideoPage", contratoVideoService.getContratoVideoAll(contratoVideoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/contratoVideoView/{id}")
	public ModelAndView contratoVideoView(@PathVariable("id") Long contratoVideoId) {

		ContratoVideo contratoVideo = contratoVideoService.getContratoVideoByContratoVideoPK(contratoVideoId);
		ModelAndView mv = new ModelAndView("contratoVideo/contratoVideoView");
		ContratoForm contratoForm = contratoService.converteContratoView(contratoVideo.getContrato());
		VideoForm videoForm = videoService.converteVideoView(contratoVideo.getVideo());
		ContratoVideoForm contratoVideoForm = contratoVideoService.converteContratoVideoView(contratoVideo);
		mv.addObject("contratoVideoForm", contratoVideoForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("videoForm", videoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}