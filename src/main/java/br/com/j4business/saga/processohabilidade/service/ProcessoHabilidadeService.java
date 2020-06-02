package br.com.j4business.saga.processohabilidade.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidade;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidadeForm;

@Service
public interface ProcessoHabilidadeService {
	
	public List<ProcessoHabilidade> getProcessoHabilidadeAll(Pageable pageable);
	public ProcessoHabilidade getProcessoHabilidadeByProcessoHabilidadePK(long processoHabilidadePK);
	public ProcessoHabilidade save(ProcessoHabilidadeForm processoHabilidadeForm);
	public void delete(Long processoHabilidadePK);
	public void deleteByHabilidade(Habilidade habilidade);
	public ProcessoHabilidade create(ProcessoHabilidadeForm processoHabilidadeForm);
	public ProcessoHabilidade getByProcessoAndHabilidade(Processo processo, Habilidade habilidade);

	public ProcessoHabilidade converteProcessoHabilidadeForm(ProcessoHabilidadeForm processoHabilidadeForm);
	public ProcessoHabilidadeForm converteProcessoHabilidade(ProcessoHabilidade processoHabilidade);
	public ProcessoHabilidadeForm converteProcessoHabilidadeView(ProcessoHabilidade processoHabilidade);

	public ProcessoHabilidadeForm processoHabilidadeParametros(ProcessoHabilidadeForm processoHabilidadeForm);

	public List<ProcessoHabilidade> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoHabilidade> getByHabilidadePK(long habilidadePK,Pageable pageable);

	public List<ProcessoHabilidade> getByHabilidadeNome(String habilidadeNome,Pageable pageable);
	public List<ProcessoHabilidade> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoHabilidade> getByHabilidadeNome(String habilidadeNome);
	public List<ProcessoHabilidade> getByProcessoNome(String processoNome);
	
}