package br.com.j4business.saga.formacao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.formacao.model.FormacaoForm;

@Service
public interface FormacaoService {
	
	public List<Formacao> getFormacaoAll();
	public Page<Formacao> getFormacaoAll(Pageable pageable);
	public Formacao getFormacaoByFormacaoPK(long formacaoPK);
	public Formacao create(FormacaoForm formacaoForm);
	public Formacao save(FormacaoForm formacaoForm);
	public void delete(Long formacaoPK);
	
	public Formacao converteFormacaoForm(FormacaoForm formacaoForm);
	public FormacaoForm converteFormacao(Formacao formacao);
	public FormacaoForm converteFormacaoView(Formacao formacao);

	public FormacaoForm formacaoParametros(FormacaoForm formacaoForm);

	public List<Formacao> getByFormacaoNome(String formacaoNome,Pageable pageable);
	public List<Formacao> getByFormacaoDescricao(String formacaoDescricao,Pageable pageable);

	public List<Formacao> getByFormacaoNome(String formacaoNome);
	public List<Formacao> getByFormacaoDescricao(String formacaoDescricao);

}