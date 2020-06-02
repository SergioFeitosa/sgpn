package br.com.j4business.saga.atividade.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.atividade.model.AtividadeForm;

@Service
public interface AtividadeService {
	
	public List<Atividade> getAtividadeAll();
	public Page<Atividade> getAtividadeAll(Pageable pageable);
	public Atividade getAtividadeByAtividadePK(long atividadePK);
	public Atividade create(AtividadeForm atividadeForm);
	public Atividade save(AtividadeForm atividadeForm);
	public void delete(Long atividadePK);
	
	public Atividade converteAtividadeForm(AtividadeForm atividadeForm);
	public AtividadeForm converteAtividade(Atividade atividade);
	public AtividadeForm converteAtividadeView(Atividade atividade);

	public AtividadeForm atividadeParametros(AtividadeForm atividadeForm);

	public List<Atividade> getByAtividadeNome(String atividadeNome,Pageable pageable);
	public List<Atividade> getByAtividadeDescricao(String atividadeDescricao,Pageable pageable);
	public List<Atividade> getByAtividadeNome(String atividadeNome);
	public List<Atividade> getByAtividadeDescricao(String atividadeDescricao);

}