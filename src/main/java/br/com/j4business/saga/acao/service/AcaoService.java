package br.com.j4business.saga.acao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.acao.model.AcaoForm;

@Service
public interface AcaoService {
	
	public List<Acao> getAcaoAll();
	public Page<Acao> getAcaoAll(Pageable pageable);
	public Acao getAcaoByAcaoPK(long acaoPK);
	public Acao create(AcaoForm acaoForm);
	public Acao save(AcaoForm acaoForm);
	public void delete(Long acaoPK);
	
	public Acao converteAcaoForm(AcaoForm acaoForm);
	public AcaoForm converteAcao(Acao acao);
	public AcaoForm converteAcaoView(Acao acao);

	public AcaoForm acaoParametros(AcaoForm acaoForm);

	public List<Acao> getByAcaoNome(String acaoNome,Pageable pageable);
	public List<Acao> getByAcaoDescricao(String acaoDescricao,Pageable pageable);

	public List<Acao> getByAcaoNome(String acaoNome);
	public List<Acao> getByAcaoDescricao(String acaoDescricao);

}