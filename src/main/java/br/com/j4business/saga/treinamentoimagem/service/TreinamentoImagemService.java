package br.com.j4business.saga.treinamentoimagem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagem;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagemForm;

@Service
public interface TreinamentoImagemService {
	
	public List<TreinamentoImagem> getTreinamentoImagemAll(Pageable pageable);
	public TreinamentoImagem getTreinamentoImagemByTreinamentoImagemPK(long treinamentoImagemPK);
	public TreinamentoImagem save(TreinamentoImagemForm treinamentoImagemForm);
	public void delete(Long treinamentoImagemPK);
	public void deleteByImagem(Imagem imagem);
	public TreinamentoImagem create(TreinamentoImagemForm treinamentoImagemForm);
	public TreinamentoImagem getByTreinamentoAndImagem(Treinamento treinamento, Imagem imagem);

	public TreinamentoImagem converteTreinamentoImagemForm(TreinamentoImagemForm treinamentoImagemForm);
	public TreinamentoImagemForm converteTreinamentoImagem(TreinamentoImagem treinamentoImagem);
	public TreinamentoImagemForm converteTreinamentoImagemView(TreinamentoImagem treinamentoImagem);

	public TreinamentoImagemForm treinamentoImagemParametros(TreinamentoImagemForm treinamentoImagemForm);

	public List<TreinamentoImagem> getByTreinamentoPK(long treinamentoPK);
	public List<TreinamentoImagem> getTreinamentoImagemAtivoByTreinamentoPK(long treinamentoPK);
	public List<TreinamentoImagem> getByTreinamentoPK(long treinamentoPK,Pageable pageable);
	public List<TreinamentoImagem> getByImagemPK(long imagemPK,Pageable pageable);

	public List<TreinamentoImagem> getByImagemNome(String imagemNome,Pageable pageable);
	public List<TreinamentoImagem> getByTreinamentoNome(String treinamentoNome,Pageable pageable);
	public List<TreinamentoImagem> getByImagemNome(String imagemNome);
	public List<TreinamentoImagem> getByTreinamentoNome(String treinamentoNome);
	
}