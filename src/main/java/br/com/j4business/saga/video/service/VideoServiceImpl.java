package br.com.j4business.saga.video.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.video.model.VideoForm;
import br.com.j4business.saga.video.repository.VideoRepository;

@Service("videoService")
public class VideoServiceImpl implements VideoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(VideoService.class.getName());

	@Override
	public List<Video> getVideoAll() {
		return videoRepository.findAll();
	}

	@Override
	public Page<Video> getVideoAll(Pageable pageable) {
		return videoRepository.findAll(pageable);
	}

	@Override
	public Video getVideoByVideoPK(long videoPK) {
		
		Optional<Video> video = videoRepository.findById(videoPK);
		return video.get();
	}

	@Transactional
	public Video create(VideoForm videoForm) {
		
		Video video = new Video();
		
		video = this.converteVideoForm(videoForm);
		
		video = entityManager.merge(video);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Video Create " + "\n Usuário => " + username + 
										" // Id => "+video.getVideoPK() + 
										" // Video => "+video.getVideoNome() + 
										" // Descrição => "+ video.getVideoDescricao());
		
		return video;
	}

	@Transactional
	public Video save(VideoForm videoForm) {
		
		Video video = new Video();
		
		video = this.converteVideoForm(videoForm);
		
		video = entityManager.merge(video);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Video Save " + "\n Usuário => " + username + 
										" // Id => "+video.getVideoPK() + 
										" // Video => "+video.getVideoNome() + 
										" // Descrição => "+ video.getVideoDescricao());
		

		return video;
		
	}

	@Transactional
	public void delete(Long videoId) {

		Video video = this.getVideoByVideoPK(videoId);
		
		videoRepository.delete(video);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Video Delete " + "\n Usuário => " + username + 
										" // Id => "+video.getVideoPK() + 
										" // Video => "+video.getVideoNome() + 
										" // Descrição => "+ video.getVideoDescricao());
		

    }

	@Transactional
	public Video converteVideoForm(VideoForm videoForm) {
		
		Video video = new Video();
		
		if (videoForm.getVideoPK() > 0) {
			video = this.getVideoByVideoPK(videoForm.getVideoPK());
		}
		video.setVideoNome(videoForm.getVideoNome().replaceAll("\\s+"," "));
		video.setVideoDescricao(videoForm.getVideoDescricao().replaceAll("\\s+"," "));

		video.setVideoMotivoOperacao(videoForm.getVideoMotivoOperacao().replaceAll("\\s+"," "));
		video.setVideoStatus(videoForm.getVideoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(videoForm.getVideoResponsavel()));
		video.setColaboradorResponsavel(colaborador);
		
		video.setVideoURL(videoForm.getVideoURL().replaceAll("\\s+"," "));
		video.setVideoNomeBotao(videoForm.getVideoNomeBotao().replaceAll("\\s+"," "));
		
		
		return video;
	}

	@Transactional
	public VideoForm converteVideo(Video video) {
	
		VideoForm videoForm = new VideoForm();
		
		videoForm.setVideoPK(video.getVideoPK());
		videoForm.setVideoNome(video.getVideoNome());
		videoForm.setVideoDescricao(video.getVideoDescricao());

		videoForm.setVideoMotivoOperacao(video.getVideoMotivoOperacao());
		videoForm.setVideoStatus(AtributoStatus.valueOf(video.getVideoStatus()));

		videoForm.setVideoResponsavel(video.getColaboradorResponsavel().getPessoaPK()+"");
		
		videoForm.setVideoURL(video.getVideoURL());
		videoForm.setVideoNomeBotao(video.getVideoNomeBotao());

		return videoForm;
	}
	
	@Transactional
	public VideoForm converteVideoView(Video video) {
	
		VideoForm videoForm = new VideoForm();
		
		videoForm.setVideoPK(video.getVideoPK());
		videoForm.setVideoNome(video.getVideoNome());
		videoForm.setVideoDescricao(video.getVideoDescricao());

		videoForm.setVideoMotivoOperacao(video.getVideoMotivoOperacao());
		videoForm.setVideoStatus(AtributoStatus.valueOf(video.getVideoStatus()));

		videoForm.setVideoResponsavel(video.getColaboradorResponsavel().getPessoaNome());
		
		videoForm.setVideoURL(video.getVideoURL());
		videoForm.setVideoNomeBotao(video.getVideoNomeBotao());
		
	return videoForm;
	}
	

	@Transactional
	public VideoForm videoParametros(VideoForm videoForm) {
	
		
		videoForm.setVideoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return videoForm;
	}

	@Override
	public List<Video> getByVideoDescricao(String videoDescricao,Pageable pageable) {
		return videoRepository.findByVideoDescricao(videoDescricao,pageable);
	}

	@Override
	public List<Video> getByVideoNome(String videoNome,Pageable pageable) {
		return videoRepository.findByVideoNome(videoNome,pageable);
	}

	@Override
	public List<Video> getByVideoDescricao(String videoDescricao) {
		return videoRepository.findByVideoDescricao(videoDescricao);
	}

	@Override
	public List<Video> getByVideoNome(String videoNome) {
		return videoRepository.findByVideoNome(videoNome);
	}



}
