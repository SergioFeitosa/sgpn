package br.com.j4business.saga.avaliacao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacao.model.AvaliacaoForm;

@Service
public interface AvaliacaoService {
	
	public List<Avaliacao> getAvaliacaoAll();
	public Page<Avaliacao> getAvaliacaoAll(Pageable pageable);
	public Avaliacao getAvaliacaoByAvaliacaoPK(long avaliacaoPK);
	public Avaliacao create(AvaliacaoForm avaliacaoForm);
	public Avaliacao save(AvaliacaoForm avaliacaoForm);
	public void delete(Long avaliacaoPK);
	
	public Avaliacao converteAvaliacaoForm(AvaliacaoForm avaliacaoForm);
	public AvaliacaoForm converteAvaliacao(Avaliacao avaliacao);
	public AvaliacaoForm converteAvaliacaoView(Avaliacao avaliacao);

	public AvaliacaoForm avaliacaoParametros(AvaliacaoForm avaliacaoForm);

	public List<Avaliacao> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable);
	public List<Avaliacao> getByAvaliacaoDescricao(String avaliacaoDescricao,Pageable pageable);

	public List<Avaliacao> getByAvaliacaoNome(String avaliacaoNome);
	public List<Avaliacao> getByAvaliacaoDescricao(String avaliacaoDescricao);

}