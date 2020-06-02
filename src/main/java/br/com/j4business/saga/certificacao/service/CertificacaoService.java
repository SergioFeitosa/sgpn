package br.com.j4business.saga.certificacao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.certificacao.model.CertificacaoForm;

@Service
public interface CertificacaoService {
	
	public List<Certificacao> getCertificacaoAll();
	public Page<Certificacao> getCertificacaoAll(Pageable pageable);
	public Certificacao getCertificacaoByCertificacaoPK(long certificacaoPK);
	public Certificacao create(CertificacaoForm certificacaoForm);
	public Certificacao save(CertificacaoForm certificacaoForm);
	public void delete(Long certificacaoPK);
	
	public Certificacao converteCertificacaoForm(CertificacaoForm certificacaoForm);
	public CertificacaoForm converteCertificacao(Certificacao certificacao);
	public CertificacaoForm converteCertificacaoView(Certificacao certificacao);

	public CertificacaoForm certificacaoParametros(CertificacaoForm certificacaoForm);

	public List<Certificacao> getByCertificacaoNome(String certificacaoNome,Pageable pageable);
	public List<Certificacao> getByCertificacaoDescricao(String certificacaoDescricao,Pageable pageable);

	public List<Certificacao> getByCertificacaoNome(String certificacaoNome);
	public List<Certificacao> getByCertificacaoDescricao(String certificacaoDescricao);

}