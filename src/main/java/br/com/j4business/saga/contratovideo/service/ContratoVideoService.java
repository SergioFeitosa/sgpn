package br.com.j4business.saga.contratovideo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contratovideo.model.ContratoVideo;
import br.com.j4business.saga.contratovideo.model.ContratoVideoForm;

@Service
public interface ContratoVideoService {
	
	public List<ContratoVideo> getContratoVideoAll(Pageable pageable);
	public ContratoVideo getContratoVideoByContratoVideoPK(long contratoVideoPK);
	public ContratoVideo save(ContratoVideoForm contratoVideoForm);
	public void delete(Long contratoVideoPK);
	public void deleteByVideo(Video video);
	public ContratoVideo create(ContratoVideoForm contratoVideoForm);
	public ContratoVideo getByContratoAndVideo(Contrato contrato, Video video);

	public ContratoVideo converteContratoVideoForm(ContratoVideoForm contratoVideoForm);
	public ContratoVideoForm converteContratoVideo(ContratoVideo contratoVideo);
	public ContratoVideoForm converteContratoVideoView(ContratoVideo contratoVideo);

	public ContratoVideoForm contratoVideoParametros(ContratoVideoForm contratoVideoForm);

	public List<ContratoVideo> getByContratoPK(long contratoPK);
	public List<ContratoVideo> getContratoVideoAtivoByContratoPK(long contratoPK);
	public List<ContratoVideo> getByContratoPK(long contratoPK,Pageable pageable);
	public List<ContratoVideo> getByVideoPK(long videoPK,Pageable pageable);

	public List<ContratoVideo> getByVideoNome(String videoNome,Pageable pageable);
	public List<ContratoVideo> getByContratoNome(String contratoNome,Pageable pageable);
	public List<ContratoVideo> getByVideoNome(String videoNome);
	public List<ContratoVideo> getByContratoNome(String contratoNome);
	
}