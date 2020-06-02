package br.com.j4business.saga.treinamentovideo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.video.model.Video;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideo;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideoForm;

@Service
public interface TreinamentoVideoService {
	
	public List<TreinamentoVideo> getTreinamentoVideoAll(Pageable pageable);
	public TreinamentoVideo getTreinamentoVideoByTreinamentoVideoPK(long treinamentoVideoPK);
	public TreinamentoVideo save(TreinamentoVideoForm treinamentoVideoForm);
	public void delete(Long treinamentoVideoPK);
	public void deleteByVideo(Video video);
	public TreinamentoVideo create(TreinamentoVideoForm treinamentoVideoForm);
	public TreinamentoVideo getByTreinamentoAndVideo(Treinamento treinamento, Video video);

	public TreinamentoVideo converteTreinamentoVideoForm(TreinamentoVideoForm treinamentoVideoForm);
	public TreinamentoVideoForm converteTreinamentoVideo(TreinamentoVideo treinamentoVideo);
	public TreinamentoVideoForm converteTreinamentoVideoView(TreinamentoVideo treinamentoVideo);

	public TreinamentoVideoForm treinamentoVideoParametros(TreinamentoVideoForm treinamentoVideoForm);

	public List<TreinamentoVideo> getByTreinamentoPK(long treinamentoPK);
	public List<TreinamentoVideo> getTreinamentoVideoAtivoByTreinamentoPK(long treinamentoPK);
	public List<TreinamentoVideo> getByTreinamentoPK(long treinamentoPK,Pageable pageable);
	public List<TreinamentoVideo> getByVideoPK(long videoPK,Pageable pageable);

	public List<TreinamentoVideo> getByVideoNome(String videoNome,Pageable pageable);
	public List<TreinamentoVideo> getByTreinamentoNome(String treinamentoNome,Pageable pageable);
	public List<TreinamentoVideo> getByVideoNome(String videoNome);
	public List<TreinamentoVideo> getByTreinamentoNome(String treinamentoNome);
	
}