package br.com.j4business.saga.processoformacao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacao;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacaoForm;

@Service
public interface ProcessoFormacaoService {
	
	public List<ProcessoFormacao> getProcessoFormacaoAll(Pageable pageable);
	public ProcessoFormacao getProcessoFormacaoByProcessoFormacaoPK(long processoFormacaoPK);
	public ProcessoFormacao save(ProcessoFormacaoForm processoFormacaoForm);
	public void delete(Long processoFormacaoPK);
	public void deleteByFormacao(Formacao formacao);
	public ProcessoFormacao create(ProcessoFormacaoForm processoFormacaoForm);
	public ProcessoFormacao getByProcessoAndFormacao(Processo processo, Formacao formacao);

	public ProcessoFormacao converteProcessoFormacaoForm(ProcessoFormacaoForm processoFormacaoForm);
	public ProcessoFormacaoForm converteProcessoFormacao(ProcessoFormacao processoFormacao);
	public ProcessoFormacaoForm converteProcessoFormacaoView(ProcessoFormacao processoFormacao);

	public ProcessoFormacaoForm processoFormacaoParametros(ProcessoFormacaoForm processoFormacaoForm);

	public List<ProcessoFormacao> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoFormacao> getByFormacaoPK(long formacaoPK,Pageable pageable);

	public List<ProcessoFormacao> getByFormacaoNome(String formacaoNome,Pageable pageable);
	public List<ProcessoFormacao> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoFormacao> getByFormacaoNome(String formacaoNome);
	public List<ProcessoFormacao> getByProcessoNome(String processoNome);
	
	public List<ProcessoFormacao> getMaxNivelByProcessoPK(long processoPK,Pageable pageable);

}