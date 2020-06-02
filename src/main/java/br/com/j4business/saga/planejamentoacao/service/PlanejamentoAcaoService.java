package br.com.j4business.saga.planejamentoacao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.planejamento.model.Planejamento;

import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcao;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcaoForm;

@Service
public interface PlanejamentoAcaoService {
	
	public List<PlanejamentoAcao> getPlanejamentoAcaoAll(Pageable pageable);
	public PlanejamentoAcao getPlanejamentoAcaoByPlanejamentoAcaoPK(long planejamentoAcaoPK);
	public PlanejamentoAcao save(PlanejamentoAcaoForm planejamentoAcaoForm);
	public void delete(Long planejamentoAcaoPK);
	public void deleteByPlanejamento(Planejamento planejamento);
	public PlanejamentoAcao create(PlanejamentoAcaoForm planejamentoAcaoForm);
	public PlanejamentoAcao getByPlanejamentoAndAcao(Planejamento planejamento,Acao acao);

	public PlanejamentoAcao convertePlanejamentoAcaoForm(PlanejamentoAcaoForm planejamentoAcaoForm);
	public PlanejamentoAcaoForm convertePlanejamentoAcao(PlanejamentoAcao planejamentoAcao);
	public PlanejamentoAcaoForm convertePlanejamentoAcaoView(PlanejamentoAcao planejamentoAcao);

	public PlanejamentoAcaoForm planejamentoAcaoParametros(PlanejamentoAcaoForm planejamentoAcaoForm);

	public List<PlanejamentoAcao> getByPlanejamentoPK(long planejamentoPK,Pageable pageable);
	
	public List<PlanejamentoAcao> getByPlanejamentoNome(String planejamentoNome,Pageable pageable);
	public List<PlanejamentoAcao> getByAcaoNome(String acaoNome,Pageable pageable);
}