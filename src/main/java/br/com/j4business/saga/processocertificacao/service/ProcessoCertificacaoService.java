package br.com.j4business.saga.processocertificacao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacaoForm;

@Service
public interface ProcessoCertificacaoService {
	
	public List<ProcessoCertificacao> getProcessoCertificacaoAll(Pageable pageable);
	public ProcessoCertificacao getProcessoCertificacaoByProcessoCertificacaoPK(long processoCertificacaoPK);
	public ProcessoCertificacao save(ProcessoCertificacaoForm processoCertificacaoForm);
	public void delete(Long processoCertificacaoPK);
	public void deleteByCertificacao(Certificacao certificacao);
	public ProcessoCertificacao create(ProcessoCertificacaoForm processoCertificacaoForm);
	public ProcessoCertificacao getByProcessoAndCertificacao(Processo processo, Certificacao certificacao);

	public ProcessoCertificacao converteProcessoCertificacaoForm(ProcessoCertificacaoForm processoCertificacaoForm);
	public ProcessoCertificacaoForm converteProcessoCertificacao(ProcessoCertificacao processoCertificacao);
	public ProcessoCertificacaoForm converteProcessoCertificacaoView(ProcessoCertificacao processoCertificacao);

	public ProcessoCertificacaoForm processoCertificacaoParametros(ProcessoCertificacaoForm processoCertificacaoForm);

	public List<ProcessoCertificacao> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoCertificacao> getByCertificacaoPK(long certificacaoPK,Pageable pageable);

	public List<ProcessoCertificacao> getByCertificacaoNome(String certificacaoNome,Pageable pageable);
	public List<ProcessoCertificacao> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoCertificacao> getByCertificacaoNome(String certificacaoNome);
	public List<ProcessoCertificacao> getByProcessoNome(String processoNome);
	
}