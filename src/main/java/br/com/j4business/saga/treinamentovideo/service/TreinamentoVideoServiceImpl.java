package br.com.j4business.saga.treinamentovideo.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideo;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideoForm;
import br.com.j4business.saga.treinamentovideo.repository.TreinamentoVideoRepository;
import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.video.service.VideoService;

@Service("treinamentoVideoService")
public class TreinamentoVideoServiceImpl implements TreinamentoVideoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private TreinamentoVideoRepository treinamentoVideoRepository;

	@Autowired
	private VideoService videoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(TreinamentoVideoService.class.getName());


	@Override
	public List<TreinamentoVideo> getByTreinamentoNome(String treinamentoNome,Pageable pageable) {
		return treinamentoVideoRepository.findByTreinamentoNome(treinamentoNome,pageable);
	}

	@Override
	public List<TreinamentoVideo> getByVideoNome(String videoNome,Pageable pageable) {
		return treinamentoVideoRepository.findByVideoNome(videoNome,pageable);
	}

	@Override
	public List<TreinamentoVideo> getByTreinamentoNome(String treinamentoNome) {
		return treinamentoVideoRepository.findByTreinamentoNome(treinamentoNome);
	}

	@Override
	public List<TreinamentoVideo> getByVideoNome(String videoNome) {
		return treinamentoVideoRepository.findByVideoNome(videoNome);
	}

	@Override
	public List<TreinamentoVideo> getByVideoPK(long videoPK,Pageable pageable) {
		return treinamentoVideoRepository.findByVideoPK(videoPK,pageable);
	}

	@Override
	public List<TreinamentoVideo> getByTreinamentoPK(long treinamentoPK,Pageable pageable) {
		return treinamentoVideoRepository.findByTreinamentoPK(treinamentoPK,pageable);
	}

	@Override
	public List<TreinamentoVideo> getByTreinamentoPK(long treinamentoPK) {
		return treinamentoVideoRepository.findByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<TreinamentoVideo> getTreinamentoVideoAtivoByTreinamentoPK(long treinamentoPK) {
		return treinamentoVideoRepository.findTreinamentoVideoAtivoByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<TreinamentoVideo> getTreinamentoVideoAll(Pageable pageable) {
		return treinamentoVideoRepository.findTreinamentoVideoAll(pageable);
	}

	@Override
	public TreinamentoVideo getTreinamentoVideoByTreinamentoVideoPK(long treinamentoVideoPK) {
		Optional<TreinamentoVideo> treinamentoVideo = treinamentoVideoRepository.findById(treinamentoVideoPK);
		return treinamentoVideo.get();
	}

	@Override
	public TreinamentoVideo getByTreinamentoAndVideo (Treinamento treinamento,Video video) {
		
		return treinamentoVideoRepository.findByTreinamentoAndVideo(treinamento,video);
	}

	@Transactional
	public TreinamentoVideo create(TreinamentoVideoForm treinamentoVideoForm) {
		
		TreinamentoVideo treinamentoVideo = new TreinamentoVideo();
		
		treinamentoVideo = this.converteTreinamentoVideoForm(treinamentoVideoForm);
		
		treinamentoVideo = entityManager.merge(treinamentoVideo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Video Create " + "\n Usuário => " + username + 
				" // Id => "+treinamentoVideo.getTreinamentoVideoPK() + 
				" // Treinamento Id => "+treinamentoVideo.getTreinamento().getTreinamentoPK() + 
				" // Video Id => "+treinamentoVideo.getVideo().getVideoPK()); 
		
		return treinamentoVideo;
	}

	@Transactional
	public TreinamentoVideo save(TreinamentoVideoForm treinamentoVideoForm) {

		TreinamentoVideo treinamentoVideo = new TreinamentoVideo();
		
		treinamentoVideo = this.converteTreinamentoVideoForm(treinamentoVideoForm);
		
		treinamentoVideo = entityManager.merge(treinamentoVideo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("TreinamentoVideo Save " + "\n Usuário => " + username + 
										" // Id => "+treinamentoVideo.getTreinamentoVideoPK() + 
										" // Treinamento Id => "+treinamentoVideo.getTreinamento().getTreinamentoPK() + 
										" // Video Id => "+treinamentoVideo.getVideo().getVideoPK());
		return treinamentoVideo;
	}

	@Transactional
	public void delete(Long treinamentoVideoPK) {

		TreinamentoVideo treinamentoVideoTemp = this.getTreinamentoVideoByTreinamentoVideoPK(treinamentoVideoPK);

		treinamentoVideoRepository.delete(treinamentoVideoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("TreinamentoVideo Save " + "\n Usuário => " + username + 
										" // Id => "+treinamentoVideoTemp.getTreinamentoVideoPK() + 
										" // Treinamento Id => "+treinamentoVideoTemp.getTreinamento().getTreinamentoPK() + 
										" // Video Id => "+treinamentoVideoTemp.getVideo().getVideoPK()); 
    }

	@Transactional
	public void deleteByVideo(Video video) {
		
		List<TreinamentoVideo> treinamentoVideoList = treinamentoVideoRepository.findByVideo(video);
		for (TreinamentoVideo treinamentoVideo2 : treinamentoVideoList) {
			treinamentoVideoRepository.delete(treinamentoVideo2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		treinamentoVideoList.forEach((TreinamentoVideo treinamentoVideo) -> {
			logger.info("TreinamentoVideo Delete2 " + "\n Usuário => " + username + 
											" // Id => "+treinamentoVideo.getTreinamentoVideoPK() + 
											" // Treinamento Id => "+treinamentoVideo.getTreinamento().getTreinamentoPK() + 
											" // Video Id => "+treinamentoVideo.getVideo().getVideoPK()); 

		});
		
    }

	@Transactional
	public TreinamentoVideo converteTreinamentoVideoForm(TreinamentoVideoForm treinamentoVideoForm) {
		
		TreinamentoVideo treinamentoVideo = new TreinamentoVideo();
		
		if (treinamentoVideoForm.getTreinamentoVideoPK() > 0) {
			treinamentoVideo = this.getTreinamentoVideoByTreinamentoVideoPK(treinamentoVideoForm.getTreinamentoVideoPK());
		}
		
		
		Video video = videoService.getVideoByVideoPK(Long.parseLong(treinamentoVideoForm.getVideoNome()));
		treinamentoVideo.setVideo(video);

		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(Long.parseLong(treinamentoVideoForm.getTreinamentoNome()));
		treinamentoVideo.setTreinamento(treinamento);

		treinamentoVideo.setTreinamentoVideoMotivoOperacao(treinamentoVideoForm.getTreinamentoVideoMotivoOperacao().replaceAll("\\s+"," "));
		treinamentoVideo.setTreinamentoVideoStatus(treinamentoVideoForm.getTreinamentoVideoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(treinamentoVideoForm.getTreinamentoVideoResponsavel()));
		treinamentoVideo.setColaboradorResponsavel(colaborador);
		
		
		return treinamentoVideo;
	}

	@Transactional
	public TreinamentoVideoForm converteTreinamentoVideo(TreinamentoVideo treinamentoVideo) {
	
		TreinamentoVideoForm treinamentoVideoForm = new TreinamentoVideoForm();
		
		treinamentoVideoForm.setTreinamentoVideoPK(treinamentoVideo.getTreinamentoVideoPK());
		treinamentoVideoForm.setTreinamentoPK(treinamentoVideo.getTreinamento().getTreinamentoPK());
		treinamentoVideoForm.setVideoPK(treinamentoVideo.getVideo().getVideoPK());
		treinamentoVideoForm.setTreinamentoNome(treinamentoVideo.getTreinamento().getTreinamentoPK()+"");
		treinamentoVideoForm.setVideoNome(treinamentoVideo.getVideo().getVideoPK()+"");

		treinamentoVideoForm.setTreinamentoVideoMotivoOperacao(treinamentoVideo.getTreinamentoVideoMotivoOperacao());
		treinamentoVideoForm.setTreinamentoVideoStatus(AtributoStatus.valueOf(treinamentoVideo.getTreinamentoVideoStatus()));
		treinamentoVideoForm.setTreinamentoVideoResponsavel(treinamentoVideo.getTreinamento().getTreinamentoPK()+"");
		
	return treinamentoVideoForm;
	}
	
	@Transactional
	public TreinamentoVideoForm converteTreinamentoVideoView(TreinamentoVideo treinamentoVideo) {
	
		TreinamentoVideoForm treinamentoVideoForm = new TreinamentoVideoForm();
		
		treinamentoVideoForm.setTreinamentoVideoPK(treinamentoVideo.getTreinamentoVideoPK());
		treinamentoVideoForm.setTreinamentoPK(treinamentoVideo.getTreinamento().getTreinamentoPK());
		treinamentoVideoForm.setVideoPK(treinamentoVideo.getVideo().getVideoPK());
		treinamentoVideoForm.setTreinamentoNome(treinamentoVideo.getTreinamento().getTreinamentoNome());
		treinamentoVideoForm.setVideoNome(treinamentoVideo.getVideo().getVideoNome());

		treinamentoVideoForm.setTreinamentoVideoMotivoOperacao(treinamentoVideo.getTreinamentoVideoMotivoOperacao());
		treinamentoVideoForm.setTreinamentoVideoStatus(AtributoStatus.valueOf(treinamentoVideo.getTreinamentoVideoStatus()));
		treinamentoVideoForm.setTreinamentoVideoResponsavel(treinamentoVideo.getTreinamento().getTreinamentoNome());
		
	return treinamentoVideoForm;
	}
	

	@Transactional
	public TreinamentoVideoForm treinamentoVideoParametros(TreinamentoVideoForm treinamentoVideoForm) {
		treinamentoVideoForm.setTreinamentoVideoStatus(AtributoStatus.valueOf("ATIVO"));
	return treinamentoVideoForm;
	}
	

}
