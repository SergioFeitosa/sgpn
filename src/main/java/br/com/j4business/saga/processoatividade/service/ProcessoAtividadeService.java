package br.com.j4business.saga.processoatividade.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.processo.model.Processo;

import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividadeForm;

@Service
public interface ProcessoAtividadeService {
	
	public List<ProcessoAtividade> getProcessoAtividadeAll(Pageable pageable);
	public ProcessoAtividade getProcessoAtividadeByProcessoAtividadePK(long processoAtividadePK);
	public ProcessoAtividade save(ProcessoAtividadeForm processoAtividadeForm);
	public void delete(Long processoAtividadePK);
	public void deleteByProcesso(Processo processo);
	public ProcessoAtividade create(ProcessoAtividadeForm processoAtividadeForm);
	public ProcessoAtividade getByProcessoAndAtividade(Processo processo,Atividade atividade);

	public ProcessoAtividade converteProcessoAtividadeForm(ProcessoAtividadeForm processoAtividadeForm);
	public ProcessoAtividadeForm converteProcessoAtividade(ProcessoAtividade processoAtividade);
	public ProcessoAtividadeForm converteProcessoAtividadeView(ProcessoAtividade processoAtividade);

	public ProcessoAtividadeForm processoAtividadeParametros(ProcessoAtividadeForm processoAtividadeForm);

	public List<ProcessoAtividade> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoAtividade> getByProcessoPK(long processoPK);
	
	public List<ProcessoAtividade> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoAtividade> getByAtividadeNome(String atividadeNome,Pageable pageable);
}