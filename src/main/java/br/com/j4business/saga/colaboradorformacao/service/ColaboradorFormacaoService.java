package br.com.j4business.saga.colaboradorformacao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacao;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacaoForm;

@Service
public interface ColaboradorFormacaoService {
	
	public List<ColaboradorFormacao> getColaboradorFormacaoAll(Pageable pageable);
	public ColaboradorFormacao getColaboradorFormacaoByColaboradorFormacaoPK(long colaboradorFormacaoPK);
	public ColaboradorFormacao save(ColaboradorFormacaoForm colaboradorFormacaoForm);
	public void delete(Long colaboradorFormacaoPK);
	public void deleteByFormacao(Formacao formacao);
	public ColaboradorFormacao create(ColaboradorFormacaoForm colaboradorFormacaoForm);
	public ColaboradorFormacao getByColaboradorAndFormacao(Colaborador colaborador, Formacao formacao);

	public ColaboradorFormacao converteColaboradorFormacaoForm(ColaboradorFormacaoForm colaboradorFormacaoForm);
	public ColaboradorFormacaoForm converteColaboradorFormacao(ColaboradorFormacao colaboradorFormacao);
	public ColaboradorFormacaoForm converteColaboradorFormacaoView(ColaboradorFormacao colaboradorFormacao);

	public ColaboradorFormacaoForm colaboradorFormacaoParametros(ColaboradorFormacaoForm colaboradorFormacaoForm);

	public List<ColaboradorFormacao> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorFormacao> getByFormacaoPK(long formacaoPK,Pageable pageable);

	public List<ColaboradorFormacao> getByFormacaoNome(String formacaoNome,Pageable pageable);
	public List<ColaboradorFormacao> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorFormacao> getByFormacaoNome(String formacaoNome);
	public List<ColaboradorFormacao> getByColaboradorNome(String colaboradorNome);
	
	public List<ColaboradorFormacao> getMaxNivelByColaboradorPK(long colaboradorPK,Pageable pageable);
}