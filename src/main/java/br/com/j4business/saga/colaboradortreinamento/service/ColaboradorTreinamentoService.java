package br.com.j4business.saga.colaboradortreinamento.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamento;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamentoForm;

@Service
public interface ColaboradorTreinamentoService {
	
	public List<ColaboradorTreinamento> getColaboradorTreinamentoAll(Pageable pageable);
	public ColaboradorTreinamento getColaboradorTreinamentoByColaboradorTreinamentoPK(long colaboradorTreinamentoPK);
	public ColaboradorTreinamento save(ColaboradorTreinamentoForm colaboradorTreinamentoForm);
	public void delete(Long colaboradorTreinamentoPK);
	public void deleteByTreinamento(Treinamento treinamento);
	public ColaboradorTreinamento create(ColaboradorTreinamentoForm colaboradorTreinamentoForm);
	public ColaboradorTreinamento getByColaboradorAndTreinamento(Colaborador colaborador, Treinamento treinamento);

	public ColaboradorTreinamento converteColaboradorTreinamentoForm(ColaboradorTreinamentoForm colaboradorTreinamentoForm);
	public ColaboradorTreinamentoForm converteColaboradorTreinamento(ColaboradorTreinamento colaboradorTreinamento);
	public ColaboradorTreinamentoForm converteColaboradorTreinamentoView(ColaboradorTreinamento colaboradorTreinamento);

	public ColaboradorTreinamentoForm colaboradorTreinamentoParametros(ColaboradorTreinamentoForm colaboradorTreinamentoForm);

	public List<ColaboradorTreinamento> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorTreinamento> getByTreinamentoPK(long treinamentoPK);
	public List<ColaboradorTreinamento> getByTreinamentoPK(long treinamentoPK,Pageable pageable);

	public List<ColaboradorTreinamento> getByTreinamentoNome(String treinamentoNome,Pageable pageable);
	public List<ColaboradorTreinamento> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorTreinamento> getByTreinamentoNome(String treinamentoNome);
	public List<ColaboradorTreinamento> getByColaboradorNome(String colaboradorNome);
	
}