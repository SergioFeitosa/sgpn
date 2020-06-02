package br.com.j4business.saga.processovideo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processovideo.model.ProcessoVideo;
import br.com.j4business.saga.processovideo.model.ProcessoVideoForm;

@Service
public interface ProcessoVideoService {
	
	public List<ProcessoVideo> getProcessoVideoAll(Pageable pageable);
	public ProcessoVideo getProcessoVideoByProcessoVideoPK(long processoVideoPK);
	public ProcessoVideo save(ProcessoVideoForm processoVideoForm);
	public void delete(Long processoVideoPK);
	public void deleteByVideo(Video video);
	public ProcessoVideo create(ProcessoVideoForm processoVideoForm);
	public ProcessoVideo getByProcessoAndVideo(Processo processo, Video video);

	public ProcessoVideo converteProcessoVideoForm(ProcessoVideoForm processoVideoForm);
	public ProcessoVideoForm converteProcessoVideo(ProcessoVideo processoVideo);
	public ProcessoVideoForm converteProcessoVideoView(ProcessoVideo processoVideo);

	public ProcessoVideoForm processoVideoParametros(ProcessoVideoForm processoVideoForm);

	public List<ProcessoVideo> getByProcessoPK(long processoPK);
	public List<ProcessoVideo> getProcessoVideoAtivoByProcessoPK(long processoPK);
	public List<ProcessoVideo> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoVideo> getByVideoPK(long videoPK,Pageable pageable);

	public List<ProcessoVideo> getByVideoNome(String videoNome,Pageable pageable);
	public List<ProcessoVideo> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoVideo> getByVideoNome(String videoNome);
	public List<ProcessoVideo> getByProcessoNome(String processoNome);
	
}