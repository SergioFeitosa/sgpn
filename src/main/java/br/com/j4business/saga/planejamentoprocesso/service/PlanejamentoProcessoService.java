package br.com.j4business.saga.planejamentoprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcesso;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcessoForm;

@Service
public interface PlanejamentoProcessoService {
	
	public List<PlanejamentoProcesso> getPlanejamentoProcessoAll(Pageable pageable);
	public PlanejamentoProcesso getPlanejamentoProcessoByPlanejamentoProcessoPK(long planejamentoProcessoPK);
	public PlanejamentoProcesso save(PlanejamentoProcessoForm planejamentoProcessoForm);
	public void delete(Long planejamentoProcessoPK);
	public void deleteByProcesso(Processo processo);
	public PlanejamentoProcesso create(PlanejamentoProcessoForm planejamentoProcessoForm);
	public PlanejamentoProcesso getByPlanejamentoAndProcesso(Planejamento planejamento, Processo processo);

	public PlanejamentoProcesso convertePlanejamentoProcessoForm(PlanejamentoProcessoForm planejamentoProcessoForm);
	public PlanejamentoProcessoForm convertePlanejamentoProcesso(PlanejamentoProcesso planejamentoProcesso);
	public PlanejamentoProcessoForm convertePlanejamentoProcessoView(PlanejamentoProcesso planejamentoProcesso);

	public PlanejamentoProcessoForm planejamentoProcessoParametros(PlanejamentoProcessoForm planejamentoProcessoForm);

	public List<PlanejamentoProcesso> getByPlanejamentoPK(long planejamentoPK,Pageable pageable);
	public List<PlanejamentoProcesso> getByProcessoPK(long processoPK,Pageable pageable);

	public List<PlanejamentoProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<PlanejamentoProcesso> getByPlanejamentoNome(String planejamentoNome,Pageable pageable);
	public List<PlanejamentoProcesso> getByProcessoNome(String processoNome);
	public List<PlanejamentoProcesso> getByPlanejamentoNome(String planejamentoNome);
	
}