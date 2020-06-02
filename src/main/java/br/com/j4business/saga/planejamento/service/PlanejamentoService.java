package br.com.j4business.saga.planejamento.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamento.model.PlanejamentoForm;

@Service
public interface PlanejamentoService {
	
	public List<Planejamento> getPlanejamentoAll();
	public Page<Planejamento> getPlanejamentoAll(Pageable pageable);
	public Planejamento getPlanejamentoByPlanejamentoPK(long planejamentoPK);
	public Planejamento create(PlanejamentoForm planejamentoForm);
	public Planejamento save(PlanejamentoForm planejamentoForm);
	public void delete(Long planejamentoPK);
	
	public Planejamento convertePlanejamentoForm(PlanejamentoForm planejamentoForm);
	public PlanejamentoForm convertePlanejamento(Planejamento planejamento);
	public PlanejamentoForm convertePlanejamentoView(Planejamento planejamento);

	public PlanejamentoForm planejamentoParametros(PlanejamentoForm planejamentoForm);

	public List<Planejamento> getByPlanejamentoNome(String planejamentoNome,Pageable pageable);
	public List<Planejamento> getByPlanejamentoDescricao(String planejamentoDescricao,Pageable pageable);

	public List<Planejamento> getByPlanejamentoNome(String planejamentoNome);
	public List<Planejamento> getByPlanejamentoDescricao(String planejamentoDescricao);

}