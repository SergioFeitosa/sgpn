package br.com.j4business.saga.ocorrencia.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.ocorrencia.model.Ocorrencia;
import br.com.j4business.saga.ocorrencia.model.OcorrenciaForm;

@Service
public interface OcorrenciaService {
	
	public List<Ocorrencia> getOcorrenciaAll();
	public Page<Ocorrencia> getOcorrenciaAll(Pageable pageable);
	public Ocorrencia getOcorrenciaByOcorrenciaPK(long ocorrenciaPK);
	public Ocorrencia create(OcorrenciaForm ocorrenciaForm);
	public Ocorrencia save(OcorrenciaForm ocorrenciaForm);
	public void delete(Long ocorrenciaPK);
	
	public Ocorrencia converteOcorrenciaForm(OcorrenciaForm ocorrenciaForm);
	public OcorrenciaForm converteOcorrencia(Ocorrencia ocorrencia);
	public OcorrenciaForm converteOcorrenciaView(Ocorrencia ocorrencia);

	public OcorrenciaForm ocorrenciaParametros(OcorrenciaForm ocorrenciaForm);

	public List<Ocorrencia> getByOcorrenciaNome(String ocorrenciaNome,Pageable pageable);
	public List<Ocorrencia> getByOcorrenciaDescricao(String ocorrenciaDescricao,Pageable pageable);
	public List<Ocorrencia> getByOcorrenciaNome(String ocorrenciaNome);
	public List<Ocorrencia> getByOcorrenciaDescricao(String ocorrenciaDescricao);

}