package br.com.j4business.saga.treinamento.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.model.TreinamentoForm;

@Service
public interface TreinamentoService {
	
	public List<Treinamento> getTreinamentoAll();
	public Page<Treinamento> getTreinamentoAll(Pageable pageable);
	public Treinamento getTreinamentoByTreinamentoPK(long treinamentoPK);
	public Treinamento create(TreinamentoForm treinamentoForm);
	public Treinamento save(TreinamentoForm treinamentoForm);
	public void delete(Long treinamentoPK);
	
	public Treinamento converteTreinamentoForm(TreinamentoForm treinamentoForm);
	public TreinamentoForm converteTreinamento(Treinamento treinamento);
	public TreinamentoForm converteTreinamentoView(Treinamento treinamento);

	public TreinamentoForm treinamentoParametros(TreinamentoForm treinamentoForm);

	public List<Treinamento> getByTreinamentoNome(String treinamentoNome,Pageable pageable);
	public List<Treinamento> getByTreinamentoDescricao(String treinamentoDescricao,Pageable pageable);
	public List<Treinamento> getByTreinamentoNome(String treinamentoNome);
	public List<Treinamento> getByTreinamentoDescricao(String treinamentoDescricao);

}