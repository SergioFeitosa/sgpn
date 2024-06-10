package br.com.j4business.saga.video.controller;

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
import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.video.model.VideoByVideoForm;
import br.com.j4business.saga.video.model.VideoForm;
import br.com.j4business.saga.video.service.VideoService;

@Controller
public class VideoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/videoAdd")
	public ModelAndView videoAdd(VideoForm videoForm) {

		ModelAndView mv = new ModelAndView("video/videoAdd");
		videoForm = videoService.videoParametros(videoForm);
		mv.addObject("videoForm", videoForm);
		mv.addObject("videoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/videoCreate")
	public ModelAndView videoCreate(@Valid VideoForm videoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return videoAdd(videoForm);
		}

		if (videoForm.getVideoPK() > 0) {
			return this.videoSave(videoForm, result, attributes);
			
		}
		
		try {
			videoService.create(videoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("videoNome")) {
			        ObjectError error = new ObjectError("videoNome","Nome da Video já existente no cadastro.");
			        result.addError(error);			
			}
            
			return videoAdd(videoForm);
	    }

		ModelAndView mv = new ModelAndView("redirect:/videoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/videoDelete/{id}")
	public ModelAndView videoDelete(@PathVariable("id") long videoPK, @Valid VideoForm videoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/videoHome");

		Video video = videoService.getVideoByVideoPK(videoPK);
		try {
			videoService.delete(videoPK);

			attributes.addFlashAttribute("mensagem", "Vídeo excluída com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Vídeo não excluída. Existe(m) relacionamento(s) de Vídeo ** "+ video.getVideoNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/videoEdit/{id}")
	public ModelAndView videoEdit(@PathVariable("id") Long videoId, Pageable pageable) {

		ModelAndView mv = new ModelAndView("video/videoEdit");
		Video video = videoService.getVideoByVideoPK(videoId);
		VideoForm videoForm = videoService.converteVideo(video);
		mv.addObject("videoForm", videoForm);
		mv.addObject("videoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/videoHome")
	public ModelAndView videoHome(@Valid VideoByVideoForm videoByVideoForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("video/videoHome");

		List<Video> videoList = new ArrayList<Video>();

		int videosTotal = 0;
		
		if (videoByVideoForm.getSearchVideoNome() == null) {
			videoByVideoForm.setSearchVideoNome("");
			videoByVideoForm.setSearchVideoDescricao("");
			if (videoByVideoForm.getVideoSortTipo() == null) {
				videoByVideoForm.setVideoSortTipo("VideoNome");
			}

		}

		if (videoByVideoForm.getVideoSortTipo().equalsIgnoreCase("VideoNome") || videoByVideoForm.getVideoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "videoNome");

		} else if (videoByVideoForm.getVideoSortTipo().equalsIgnoreCase("VideoDescricao")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "videoDescricao");

		}

		if ((!videoByVideoForm.getSearchVideoNome().equalsIgnoreCase(""))) {
			videoList = videoService.getByVideoNome(videoByVideoForm.getSearchVideoNome(), pageable);
			videosTotal = videoService.getByVideoNome(videoByVideoForm.getSearchVideoNome()).size();

		} else {
			videoList = videoService.getByVideoDescricao(videoByVideoForm.getSearchVideoDescricao(), pageable);
			videosTotal = videoService.getByVideoDescricao(videoByVideoForm.getSearchVideoDescricao()).size();
		}

		Page<Video> videoPage = new PageImpl<Video>(videoList, pageable, videosTotal+1);

		mv.addObject("videoPage", videoPage);
		mv.addObject("page", videoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/videoSave")
	public ModelAndView videoSave(@Valid VideoForm videoForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return videoAdd(videoForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/videoHome");

		try {
			videoService.save(videoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("videoNome")) {
			        ObjectError error = new ObjectError("videoNome","Nome da Video já existente no cadastro.");
			        result.addError(error);			
			}
            return videoAdd(videoForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/videoRelMenu")
	public ModelAndView videoRelMenu() {

		ModelAndView mv = new ModelAndView("video/videoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/videoRel001")
	public ModelAndView videoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("video/videoRel001");
		pageable = PageRequest.of(pageable.getPageNumber(), 200 , Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/videoView/{id}")
	public ModelAndView videoView(@PathVariable("id") Long videoId) {

		Video video = videoService.getVideoByVideoPK(videoId);
		ModelAndView mv = new ModelAndView("video/videoView");
		VideoForm videoForm = videoService.converteVideoView(video);
		mv.addObject("videoForm", videoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}