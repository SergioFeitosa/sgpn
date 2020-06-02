package br.com.j4business.saga.colaboradorcertificacao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacaoForm;

@Service
public interface ColaboradorCertificacaoService {
	
	public List<ColaboradorCertificacao> getColaboradorCertificacaoAll(Pageable pageable);
	public ColaboradorCertificacao getColaboradorCertificacaoByColaboradorCertificacaoPK(long colaboradorCertificacaoPK);
	public ColaboradorCertificacao save(ColaboradorCertificacaoForm colaboradorCertificacaoForm);
	public void delete(Long colaboradorCertificacaoPK);
	public void deleteByCertificacao(Certificacao certificacao);
	public ColaboradorCertificacao create(ColaboradorCertificacaoForm colaboradorCertificacaoForm);
	public ColaboradorCertificacao getByColaboradorAndCertificacao(Colaborador colaborador, Certificacao certificacao);

	public ColaboradorCertificacao converteColaboradorCertificacaoForm(ColaboradorCertificacaoForm colaboradorCertificacaoForm);
	public ColaboradorCertificacaoForm converteColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao);
	public ColaboradorCertificacaoForm converteColaboradorCertificacaoView(ColaboradorCertificacao colaboradorCertificacao);

	public ColaboradorCertificacaoForm colaboradorCertificacaoParametros(ColaboradorCertificacaoForm colaboradorCertificacaoForm);

	public List<ColaboradorCertificacao> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorCertificacao> getByCertificacaoPK(long certificacaoPK,Pageable pageable);

	public List<ColaboradorCertificacao> getByCertificacaoNome(String certificacaoNome,Pageable pageable);
	public List<ColaboradorCertificacao> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorCertificacao> getByCertificacaoNome(String certificacaoNome);
	public List<ColaboradorCertificacao> getByColaboradorNome(String colaboradorNome);
	
}