package br.com.j4business.saga.avaliacaoresultado.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoresultado.model.AvaliacaoResultado;


@Service
public interface AvaliacaoResultadoService {
	
	public List<AvaliacaoResultado> getAvaliacaoResultadoAll(Pageable pageable);
	public AvaliacaoResultado getAvaliacaoResultadoByAvaliacaoResultadoPK(long avaliacaoResultadoPK);
	public AvaliacaoResultado save(AvaliacaoResultado avaliacaoResultado);
	public void delete(Long avaliacaoResultadoPK);
	public void deleteByResultado(Resultado resultado);
	public AvaliacaoResultado create(AvaliacaoResultado avaliacaoResultado);
	public AvaliacaoResultado getByAvaliacaoAndResultado(Avaliacao avaliacao, Resultado resultado);

	public List<AvaliacaoResultado> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable);
	public List<AvaliacaoResultado> getByResultadoPK(long resultadoPK,Pageable pageable);
	public AvaliacaoResultado getByAvaliacaoProcessoPKAndElementoPK(long avaliacaoProcessoPK,long elementoPK);
	public List<AvaliacaoResultado> getByAvaliacaoProcessoPK(long avaliacaoProcessoPK);

	public List<AvaliacaoResultado> getByResultadoNome(String resultadoNome,Pageable pageable);
	public List<AvaliacaoResultado> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable);
	public List<AvaliacaoResultado> getByResultadoNome(String resultadoNome);
	public List<AvaliacaoResultado> getByAvaliacaoNome(String avaliacaoNome);
	
	public AvaliacaoResultado addAvaliacaoQuestionario(AvaliacaoResultado avaliacaoResultado);

}