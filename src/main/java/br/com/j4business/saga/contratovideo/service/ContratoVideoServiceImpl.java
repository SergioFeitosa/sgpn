package br.com.j4business.saga.contratovideo.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.contratotexto.model.ContratoTexto;
import br.com.j4business.saga.contratovideo.model.ContratoVideo;
import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.video.service.VideoService;
import br.com.j4business.saga.contratovideo.model.ContratoVideoForm;
import br.com.j4business.saga.contratovideo.repository.ContratoVideoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("contratoVideoService")
public class ContratoVideoServiceImpl implements ContratoVideoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ContratoVideoRepository contratoVideoRepository;

	@Autowired
	private VideoService videoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ContratoVideoService.class.getName());


	@Override
	public List<ContratoVideo> getByContratoNome(String contratoNome,Pageable pageable) {
		return contratoVideoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<ContratoVideo> getByVideoNome(String videoNome,Pageable pageable) {
		return contratoVideoRepository.findByVideoNome(videoNome,pageable);
	}

	@Override
	public List<ContratoVideo> getByContratoNome(String contratoNome) {
		return contratoVideoRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<ContratoVideo> getByVideoNome(String videoNome) {
		return contratoVideoRepository.findByVideoNome(videoNome);
	}

	@Override
	public List<ContratoVideo> getByVideoPK(long videoPK,Pageable pageable) {
		return contratoVideoRepository.findByVideoPK(videoPK,pageable);
	}

	@Override
	public List<ContratoVideo> getByContratoPK(long contratoPK,Pageable pageable) {
		return contratoVideoRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<ContratoVideo> getByContratoPK(long contratoPK) {
		return contratoVideoRepository.findByContratoPK(contratoPK);
	}

	@Override
	public List<ContratoVideo> getContratoVideoAtivoByContratoPK(long contratoPK) {
		return contratoVideoRepository.findContratoVideoAtivoByContratoPK(contratoPK);
	}

	@Override
	public List<ContratoVideo> getContratoVideoAll(Pageable pageable) {
		return contratoVideoRepository.findContratoVideoAll(pageable);
	}

	@Override
	public ContratoVideo getContratoVideoByContratoVideoPK(long contratoVideoPK) {
		return contratoVideoRepository.findOne(contratoVideoPK);
	}

	@Override
	public ContratoVideo getByContratoAndVideo (Contrato contrato,Video video) {
		
		return contratoVideoRepository.findByContratoAndVideo(contrato,video);
	}

	@Transactional
	public ContratoVideo create(ContratoVideoForm contratoVideoForm) {
		
		ContratoVideo contratoVideo = new ContratoVideo();
		
		contratoVideo = this.converteContratoVideoForm(contratoVideoForm);
		
		contratoVideo = entityManager.merge(contratoVideo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Video Create " + "\n Usuário => " + username + 
				" // Id => "+contratoVideo.getContratoVideoPK() + 
				" // Contrato Id => "+contratoVideo.getContrato().getContratoPK() + 
				" // Video Id => "+contratoVideo.getVideo().getVideoPK()); 
		
		return contratoVideo;
	}

	@Transactional
	public ContratoVideo save(ContratoVideoForm contratoVideoForm) {

		ContratoVideo contratoVideo = new ContratoVideo();
		
		contratoVideo = this.converteContratoVideoForm(contratoVideoForm);
		
		contratoVideo = entityManager.merge(contratoVideo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ContratoVideo Save " + "\n Usuário => " + username + 
										" // Id => "+contratoVideo.getContratoVideoPK() + 
										" // Contrato Id => "+contratoVideo.getContrato().getContratoPK() + 
										" // Video Id => "+contratoVideo.getVideo().getVideoPK());
		return contratoVideo;
	}

	@Transactional
	public void delete(Long contratoVideoPK) {

		ContratoVideo contratoVideoTemp = this.getContratoVideoByContratoVideoPK(contratoVideoPK);

		contratoVideoRepository.delete(contratoVideoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ContratoVideo Save " + "\n Usuário => " + username + 
										" // Id => "+contratoVideoTemp.getContratoVideoPK() + 
										" // Contrato Id => "+contratoVideoTemp.getContrato().getContratoPK() + 
										" // Video Id => "+contratoVideoTemp.getVideo().getVideoPK()); 
    }

	@Transactional
	public void deleteByVideo(Video video) {
		
		List<ContratoVideo> contratoVideoList = contratoVideoRepository.findByVideo(video);

		contratoVideoRepository.delete(contratoVideoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		contratoVideoList.forEach((ContratoVideo contratoVideo) -> {
			
			logger.info("ContratoVideo Delete2 " + "\n Usuário => " + username + 
											" // Id => "+contratoVideo.getContratoVideoPK() + 
											" // Contrato Id => "+contratoVideo.getContrato().getContratoPK() + 
											" // Video Id => "+contratoVideo.getVideo().getVideoPK()); 
		});
    }

	@Transactional
	public ContratoVideo converteContratoVideoForm(ContratoVideoForm contratoVideoForm) {
		
		ContratoVideo contratoVideo = new ContratoVideo();
		
		if (contratoVideoForm.getContratoVideoPK() > 0) {
			contratoVideo = this.getContratoVideoByContratoVideoPK(contratoVideoForm.getContratoVideoPK());
		}
		
		
		Video video = videoService.getVideoByVideoPK(Long.parseLong(contratoVideoForm.getVideoNome()));
		contratoVideo.setVideo(video);

		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(contratoVideoForm.getContratoNome()));
		contratoVideo.setContrato(contrato);

		contratoVideo.setContratoVideoMotivoOperacao(contratoVideoForm.getContratoVideoMotivoOperacao().replaceAll("\\s+"," "));
		contratoVideo.setContratoVideoStatus(contratoVideoForm.getContratoVideoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(contratoVideoForm.getContratoVideoResponsavel()));
		contratoVideo.setColaboradorResponsavel(colaborador);
		
		
		return contratoVideo;
	}

	@Transactional
	public ContratoVideoForm converteContratoVideo(ContratoVideo contratoVideo) {
	
		ContratoVideoForm contratoVideoForm = new ContratoVideoForm();
		
		contratoVideoForm.setContratoVideoPK(contratoVideo.getContratoVideoPK());
		contratoVideoForm.setContratoPK(contratoVideo.getContrato().getContratoPK());
		contratoVideoForm.setVideoPK(contratoVideo.getVideo().getVideoPK());
		contratoVideoForm.setContratoNome(contratoVideo.getContrato().getContratoPK()+"");
		contratoVideoForm.setVideoNome(contratoVideo.getVideo().getVideoPK()+"");

		contratoVideoForm.setContratoVideoMotivoOperacao(contratoVideo.getContratoVideoMotivoOperacao());
		contratoVideoForm.setContratoVideoStatus(AtributoStatus.valueOf(contratoVideo.getContratoVideoStatus()));
		contratoVideoForm.setContratoVideoResponsavel(contratoVideo.getContrato().getContratoPK()+"");
		
	return contratoVideoForm;
	}
	
	@Transactional
	public ContratoVideoForm converteContratoVideoView(ContratoVideo contratoVideo) {
	
		ContratoVideoForm contratoVideoForm = new ContratoVideoForm();
		
		contratoVideoForm.setContratoVideoPK(contratoVideo.getContratoVideoPK());
		contratoVideoForm.setContratoPK(contratoVideo.getContrato().getContratoPK());
		contratoVideoForm.setVideoPK(contratoVideo.getVideo().getVideoPK());
		contratoVideoForm.setContratoNome(contratoVideo.getContrato().getContratoNome());
		contratoVideoForm.setVideoNome(contratoVideo.getVideo().getVideoNome());

		contratoVideoForm.setContratoVideoMotivoOperacao(contratoVideo.getContratoVideoMotivoOperacao());
		contratoVideoForm.setContratoVideoStatus(AtributoStatus.valueOf(contratoVideo.getContratoVideoStatus()));
		contratoVideoForm.setContratoVideoResponsavel(contratoVideo.getContrato().getContratoNome());
		
	return contratoVideoForm;
	}
	

	@Transactional
	public ContratoVideoForm contratoVideoParametros(ContratoVideoForm contratoVideoForm) {
		contratoVideoForm.setContratoVideoStatus(AtributoStatus.valueOf("ATIVO"));
	return contratoVideoForm;
	}
	

}
