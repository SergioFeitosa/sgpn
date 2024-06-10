package br.com.j4business.saga.processovideo.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processovideo.model.ProcessoVideo;
import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.video.service.VideoService;
import br.com.j4business.saga.processovideo.model.ProcessoVideoForm;
import br.com.j4business.saga.processovideo.repository.ProcessoVideoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("processoVideoService")
public class ProcessoVideoServiceImpl implements ProcessoVideoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoVideoRepository processoVideoRepository;

	@Autowired
	private VideoService videoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoVideoService.class.getName());


	@Override
	public List<ProcessoVideo> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoVideoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoVideo> getByVideoNome(String videoNome,Pageable pageable) {
		return processoVideoRepository.findByVideoNome(videoNome,pageable);
	}

	@Override
	public List<ProcessoVideo> getByProcessoNome(String processoNome) {
		return processoVideoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoVideo> getByVideoNome(String videoNome) {
		return processoVideoRepository.findByVideoNome(videoNome);
	}

	@Override
	public List<ProcessoVideo> getByVideoPK(long videoPK,Pageable pageable) {
		return processoVideoRepository.findByVideoPK(videoPK,pageable);
	}

	@Override
	public List<ProcessoVideo> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoVideoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoVideo> getByProcessoPK(long processoPK) {
		return processoVideoRepository.findByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoVideo> getProcessoVideoAtivoByProcessoPK(long processoPK) {
		return processoVideoRepository.findProcessoVideoAtivoByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoVideo> getProcessoVideoAll(Pageable pageable) {
		return processoVideoRepository.findProcessoVideoAll(pageable);
	}

	@Override
	public ProcessoVideo getProcessoVideoByProcessoVideoPK(long processoVideoPK) {
		Optional<ProcessoVideo> processoVideo = processoVideoRepository.findById(processoVideoPK);
		return processoVideo.get();
	}

	@Override
	public ProcessoVideo getByProcessoAndVideo (Processo processo,Video video) {
		
		return processoVideoRepository.findByProcessoAndVideo(processo,video);
	}

	@Transactional
	public ProcessoVideo create(ProcessoVideoForm processoVideoForm) {
		
		ProcessoVideo processoVideo = new ProcessoVideo();
		
		processoVideo = this.converteProcessoVideoForm(processoVideoForm);
		
		processoVideo = entityManager.merge(processoVideo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Video Create " + "\n Usuário => " + username + 
				" // Id => "+processoVideo.getProcessoVideoPK() + 
				" // Processo Id => "+processoVideo.getProcesso().getProcessoPK() + 
				" // Video Id => "+processoVideo.getVideo().getVideoPK()); 
		
		return processoVideo;
	}

	@Transactional
	public ProcessoVideo save(ProcessoVideoForm processoVideoForm) {

		ProcessoVideo processoVideo = new ProcessoVideo();
		
		processoVideo = this.converteProcessoVideoForm(processoVideoForm);
		
		processoVideo = entityManager.merge(processoVideo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoVideo Save " + "\n Usuário => " + username + 
										" // Id => "+processoVideo.getProcessoVideoPK() + 
										" // Processo Id => "+processoVideo.getProcesso().getProcessoPK() + 
										" // Video Id => "+processoVideo.getVideo().getVideoPK());
		return processoVideo;
	}

	@Transactional
	public void delete(Long processoVideoPK) {

		ProcessoVideo processoVideoTemp = this.getProcessoVideoByProcessoVideoPK(processoVideoPK);

		processoVideoRepository.delete(processoVideoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoVideo Save " + "\n Usuário => " + username + 
										" // Id => "+processoVideoTemp.getProcessoVideoPK() + 
										" // Processo Id => "+processoVideoTemp.getProcesso().getProcessoPK() + 
										" // Video Id => "+processoVideoTemp.getVideo().getVideoPK()); 
    }

	@Transactional
	public void deleteByVideo(Video video) {
		
		List<ProcessoVideo> processoVideoList = processoVideoRepository.findByVideo(video);

		for (ProcessoVideo processoVideo2 : processoVideoList) {
			processoVideoRepository.delete(processoVideo2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		processoVideoList.forEach((ProcessoVideo processoVideo) -> {			

			logger.info("ProcessoVideo Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoVideo.getProcessoVideoPK() + 
											" // Processo Id => "+processoVideo.getProcesso().getProcessoPK() + 
											" // Video Id => "+processoVideo.getVideo().getVideoPK()); 
		});
    }

	@Transactional
	public ProcessoVideo converteProcessoVideoForm(ProcessoVideoForm processoVideoForm) {
		
		ProcessoVideo processoVideo = new ProcessoVideo();
		
		if (processoVideoForm.getProcessoVideoPK() > 0) {
			processoVideo = this.getProcessoVideoByProcessoVideoPK(processoVideoForm.getProcessoVideoPK());
		}
		
		
		Video video = videoService.getVideoByVideoPK(Long.parseLong(processoVideoForm.getVideoNome()));
		processoVideo.setVideo(video);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoVideoForm.getProcessoNome()));
		processoVideo.setProcesso(processo);

		processoVideo.setProcessoVideoMotivoOperacao(processoVideoForm.getProcessoVideoMotivoOperacao().replaceAll("\\s+"," "));
		processoVideo.setProcessoVideoStatus(processoVideoForm.getProcessoVideoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoVideoForm.getProcessoVideoResponsavel()));
		processoVideo.setColaboradorResponsavel(colaborador);
		
		
		return processoVideo;
	}

	@Transactional
	public ProcessoVideoForm converteProcessoVideo(ProcessoVideo processoVideo) {
	
		ProcessoVideoForm processoVideoForm = new ProcessoVideoForm();
		
		processoVideoForm.setProcessoVideoPK(processoVideo.getProcessoVideoPK());
		processoVideoForm.setProcessoPK(processoVideo.getProcesso().getProcessoPK());
		processoVideoForm.setVideoPK(processoVideo.getVideo().getVideoPK());
		processoVideoForm.setProcessoNome(processoVideo.getProcesso().getProcessoPK()+"");
		processoVideoForm.setVideoNome(processoVideo.getVideo().getVideoPK()+"");

		processoVideoForm.setProcessoVideoMotivoOperacao(processoVideo.getProcessoVideoMotivoOperacao());
		processoVideoForm.setProcessoVideoStatus(AtributoStatus.valueOf(processoVideo.getProcessoVideoStatus()));
		processoVideoForm.setProcessoVideoResponsavel(processoVideo.getProcesso().getProcessoPK()+"");
		
	return processoVideoForm;
	}
	
	@Transactional
	public ProcessoVideoForm converteProcessoVideoView(ProcessoVideo processoVideo) {
	
		ProcessoVideoForm processoVideoForm = new ProcessoVideoForm();
		
		processoVideoForm.setProcessoVideoPK(processoVideo.getProcessoVideoPK());
		processoVideoForm.setProcessoPK(processoVideo.getProcesso().getProcessoPK());
		processoVideoForm.setVideoPK(processoVideo.getVideo().getVideoPK());
		processoVideoForm.setProcessoNome(processoVideo.getProcesso().getProcessoNome());
		processoVideoForm.setVideoNome(processoVideo.getVideo().getVideoNome());

		processoVideoForm.setProcessoVideoMotivoOperacao(processoVideo.getProcessoVideoMotivoOperacao());
		processoVideoForm.setProcessoVideoStatus(AtributoStatus.valueOf(processoVideo.getProcessoVideoStatus()));
		processoVideoForm.setProcessoVideoResponsavel(processoVideo.getProcesso().getProcessoNome());
		
	return processoVideoForm;
	}
	

	@Transactional
	public ProcessoVideoForm processoVideoParametros(ProcessoVideoForm processoVideoForm) {
		processoVideoForm.setProcessoVideoStatus(AtributoStatus.valueOf("ATIVO"));
	return processoVideoForm;
	}
	

}
