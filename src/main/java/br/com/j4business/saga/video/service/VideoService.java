package br.com.j4business.saga.video.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.video.model.VideoForm;

@Service
public interface VideoService {
	
	public List<Video> getVideoAll();
	public Page<Video> getVideoAll(Pageable pageable);
	public Video getVideoByVideoPK(long videoPK);
	public Video create(VideoForm videoForm);
	public Video save(VideoForm videoForm);
	public void delete(Long videoPK);
	
	public Video converteVideoForm(VideoForm videoForm);
	public VideoForm converteVideo(Video video);
	public VideoForm converteVideoView(Video video);

	public VideoForm videoParametros(VideoForm videoForm);

	public List<Video> getByVideoNome(String videoNome,Pageable pageable);
	public List<Video> getByVideoDescricao(String videoDescricao,Pageable pageable);
	public List<Video> getByVideoNome(String videoNome);
	public List<Video> getByVideoDescricao(String videoDescricao);

}