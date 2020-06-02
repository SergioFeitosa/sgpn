package br.com.j4business.saga.ocorrenciaatendimento.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.ocorrencia.model.Ocorrencia;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimento;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimentoForm;

@Service
public interface OcorrenciaAtendimentoService {
	
	public List<OcorrenciaAtendimento> getOcorrenciaAtendimentoAll(Pageable pageable);
	public OcorrenciaAtendimento getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(long ocorrenciaAtendimentoPK);
	public OcorrenciaAtendimento save(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm);
	public void delete(Long ocorrenciaAtendimentoPK);
	public void deleteByAtendimento(Atendimento atendimento);
	public OcorrenciaAtendimento create(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm);
	public OcorrenciaAtendimento getByOcorrenciaAndAtendimento(Ocorrencia ocorrencia, Atendimento atendimento);

	public OcorrenciaAtendimento converteOcorrenciaAtendimentoForm(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm);
	public OcorrenciaAtendimentoForm converteOcorrenciaAtendimento(OcorrenciaAtendimento ocorrenciaAtendimento);
	public OcorrenciaAtendimentoForm converteOcorrenciaAtendimentoView(OcorrenciaAtendimento ocorrenciaAtendimento);

	public OcorrenciaAtendimentoForm ocorrenciaAtendimentoParametros(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm);

	public List<OcorrenciaAtendimento> getByAtendimentoNome(String atendimentoNome,Pageable pageable);
	public List<OcorrenciaAtendimento> getByOcorrenciaNome(String ocorrenciaNome,Pageable pageable);
	public List<OcorrenciaAtendimento> getByAtendimentoNome(String atendimentoNome);
	public List<OcorrenciaAtendimento> getByOcorrenciaNome(String ocorrenciaNome);
	public List<OcorrenciaAtendimento> getByOcorrenciaPK(long ocorrenciaPK,Pageable pageable);
	public List<OcorrenciaAtendimento> getByAtendimentoPK(long atendimentoPK,Pageable pageable);
}