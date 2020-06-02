package br.com.j4business.saga.avaliacaoresultadocontrato.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoresultadocontrato.model.AvaliacaoResultadoContrato;


@Service
public interface AvaliacaoResultadoContratoService {
	
	public List<AvaliacaoResultadoContrato> getAvaliacaoResultadoContratoAll(Pageable pageable);
	public AvaliacaoResultadoContrato getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(long avaliacaoResultadoContratoPK);
	public AvaliacaoResultadoContrato save(AvaliacaoResultadoContrato avaliacaoResultadoContrato);
	public void delete(Long avaliacaoResultadoContratoPK);
	public void deleteByResultado(Resultado resultado);
	public AvaliacaoResultadoContrato create(AvaliacaoResultadoContrato avaliacaoResultadoContrato);
	public AvaliacaoResultadoContrato getByAvaliacaoAndResultado(Avaliacao avaliacao, Resultado resultado);

	public List<AvaliacaoResultadoContrato> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable);
	public List<AvaliacaoResultadoContrato> getByResultadoPK(long resultadoPK,Pageable pageable);
	public AvaliacaoResultadoContrato getByAvaliacaoContratoPKAndElementoPK(long avaliacaoContratoPK,long elementoPK);
	public List<AvaliacaoResultadoContrato> getByAvaliacaoContratoPK(long avaliacaoContratoPK);

	public List<AvaliacaoResultadoContrato> getByResultadoNome(String resultadoNome,Pageable pageable);
	public List<AvaliacaoResultadoContrato> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable);
	public List<AvaliacaoResultadoContrato> getByResultadoNome(String resultadoNome);
	public List<AvaliacaoResultadoContrato> getByAvaliacaoNome(String avaliacaoNome);
	
	public AvaliacaoResultadoContrato addAvaliacaoQuestionario(AvaliacaoResultadoContrato avaliacaoResultadoContrato);

}