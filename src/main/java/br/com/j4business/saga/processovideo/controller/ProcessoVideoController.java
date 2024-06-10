package br.com.j4business.saga.processovideo.controller;

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
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processovideo.model.ProcessoVideo;
import br.com.j4business.saga.processovideo.model.ProcessoVideoByProcessoForm;
import br.com.j4business.saga.processovideo.model.ProcessoVideoForm;
import br.com.j4business.saga.processovideo.service.ProcessoVideoService;
import br.com.j4business.saga.video.model.VideoForm;
import br.com.j4business.saga.video.service.VideoService;

@Controller
public class ProcessoVideoController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private ProcessoVideoService processoVideoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/processoVideoAdd")
	public ModelAndView processoVideoAdd(ProcessoVideoForm processoVideoForm,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoVideo/processoVideoAdd");
		processoVideoForm = processoVideoService.processoVideoParametros(processoVideoForm);
		mv.addObject("processoVideoForm", processoVideoForm);
		mv.addObject("processoVideoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoVideoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable videoPageable = PageRequest.of(0, 200, Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(videoPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/processoVideoCreate")
	public ModelAndView processoVideoCreate(@Valid ProcessoVideoForm processoVideoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoVideoAdd(processoVideoForm,pageable);
		}

		if (processoVideoForm.getProcessoVideoPK() > 0) {
			return this.processoVideoSave(processoVideoForm, result, attributes,pageable);
			
		}
		
		try {
			
			if (processoVideoForm.getProcessoVideoStatus().toString().equalsIgnoreCase("ATIVO")) {
				
				List<ProcessoVideo> processoVideoAtivoList = processoVideoService.getProcessoVideoAtivoByProcessoPK(Long.parseLong(processoVideoForm.getProcessoNome()));
				
				if (processoVideoAtivoList.size() > 4) {
					ObjectError error = new ObjectError("processoVideoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
			        result.addError(error);			
					return processoVideoAdd(processoVideoForm,pageable);
				}
				
			}
			
			processoVideoService.create(processoVideoForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoVideoUnique")) {
			        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Video já existente no cadastro.");
			        result.addError(error);			
			}
            
			return processoVideoAdd(processoVideoForm,pageable);
	    }

		ModelAndView mv = new ModelAndView("redirect:/processoVideoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/processoVideoDelete/{id}")
	public ModelAndView processoVideoDelete(@PathVariable("id") long processoVideoId, @Valid VideoForm videoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/processoVideoHome");
		
		
		ProcessoVideo processoVideo = processoVideoService.getProcessoVideoByProcessoVideoPK(processoVideoId);
		try {
			processoVideoService.delete(processoVideoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Processo/Video excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Processo/Video não excluído. Existe(m) relacionamento(s) de Processo/Video ** "+ 
										  processoVideo.getProcesso().getProcessoNome() +
										  " / " +
										  processoVideo.getVideo().getVideoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/processoVideoEdit/{processoVideoPK}")
	public ModelAndView processoVideoEdit(@PathVariable("processoVideoPK") Long processoVideoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoVideo/processoVideoEdit");
		ProcessoVideo processoVideo = processoVideoService.getProcessoVideoByProcessoVideoPK(processoVideoPK);
		ProcessoVideoForm processoVideoForm = processoVideoService.converteProcessoVideo(processoVideo);
		mv.addObject("processoVideoForm", processoVideoForm);
		mv.addObject("processoVideoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("processoVideoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable videoPageable = PageRequest.of(0, 200, Direction.ASC, "videoNome");
		mv.addObject("videoPage", videoService.getVideoAll(videoPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/processoVideoHome")
	public ModelAndView processoVideoHome(@Valid ProcessoVideoByProcessoForm processoVideoByProcessoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoVideo/processoVideoHome");
		
		List<ProcessoVideo> processoVideoList = new ArrayList<ProcessoVideo>();
		
		int processoVideoTotal = 0;
		
		if (processoVideoByProcessoForm.getSearchVideoNome() == null) {
			processoVideoByProcessoForm.setSearchProcessoNome("");
			processoVideoByProcessoForm.setSearchVideoNome("");
			if (processoVideoByProcessoForm.getProcessoVideoSortTipo() == null) {
				processoVideoByProcessoForm.setProcessoVideoSortTipo("VideoNome");	
			}
			
		}

		if (processoVideoByProcessoForm.getProcessoVideoSortTipo().equalsIgnoreCase("ProcessoNome")
				|| processoVideoByProcessoForm.getProcessoVideoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","video.videoNome"); 
		
		} else if (processoVideoByProcessoForm.getProcessoVideoSortTipo().equalsIgnoreCase("VideoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"video.videoNome","processo.processoNome"); 

		}

		if ( ! processoVideoByProcessoForm.getSearchVideoNome().equalsIgnoreCase("")){
			processoVideoList = processoVideoService.getByVideoNome(processoVideoByProcessoForm.getSearchVideoNome(),pageable);
			processoVideoTotal = processoVideoService.getByVideoNome(processoVideoByProcessoForm.getSearchVideoNome()).size();
			
		} else {
			processoVideoList = processoVideoService.getByProcessoNome(processoVideoByProcessoForm.getSearchProcessoNome(),pageable);
			processoVideoTotal = processoVideoService.getByProcessoNome(processoVideoByProcessoForm.getSearchProcessoNome()).size();

		} 
		
		Page<ProcessoVideo> processoVideoPage = new PageImpl<ProcessoVideo>(processoVideoList,pageable,processoVideoTotal+1);
		
		mv.addObject("processoVideoPage", processoVideoPage);
		mv.addObject("page",processoVideoPage);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/processoVideoSave")
	public ModelAndView processoVideoSave(@Valid ProcessoVideoForm processoVideoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return processoVideoAdd(processoVideoForm,pageable);
		}

		ModelAndView mv = new ModelAndView("redirect:/processoVideoHome");

		try {
			
			List<ProcessoVideo> processoVideoAtivoList = processoVideoService.getProcessoVideoAtivoByProcessoPK(processoVideoForm.getProcessoPK());
			
			if (processoVideoAtivoList.size() > 5) {
				ObjectError error = new ObjectError("processoVideoStatus","Quantidade de vídeos não pode exceder a 5 vídeos ativos.");
		        result.addError(error);			
				return processoVideoAdd(processoVideoForm,pageable);
			}
			
			processoVideoService.save(processoVideoForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("processoVideoUnique")) {
		        ObjectError error = new ObjectError("processoNome","Relacionamento entre Processo e Video já existente no cadastro.");
		        result.addError(error);			
		}
            return processoVideoAdd(processoVideoForm,pageable);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/processoVideoRelMenu")
	public ModelAndView processoVideoRelMenu() {

		ModelAndView mv = new ModelAndView("processoVideo/processoVideoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/processoVideoRel001")
	public ModelAndView processoVideoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("processoVideo/processoVideoRel001");
		Pageable processoVideoPageable = PageRequest.of(0, 200, Direction.ASC, "processo.processoNome","video.videoNome");
		mv.addObject("processoVideoPage", processoVideoService.getProcessoVideoAll(processoVideoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/processoVideoView/{id}")
	public ModelAndView processoVideoView(@PathVariable("id") Long processoVideoId) {

		ProcessoVideo processoVideo = processoVideoService.getProcessoVideoByProcessoVideoPK(processoVideoId);
		ModelAndView mv = new ModelAndView("processoVideo/processoVideoView");
		ProcessoForm processoForm = processoService.converteProcessoView(processoVideo.getProcesso());
		VideoForm videoForm = videoService.converteVideoView(processoVideo.getVideo());
		ProcessoVideoForm processoVideoForm = processoVideoService.converteProcessoVideoView(processoVideo);
		mv.addObject("processoVideoForm", processoVideoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("videoForm", videoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


}