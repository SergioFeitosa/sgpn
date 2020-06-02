package br.com.j4business.saga.habilidade.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.habilidade.model.HabilidadeForm;

@Service
public interface HabilidadeService {
	
	public List<Habilidade> getHabilidadeAll();
	public Page<Habilidade> getHabilidadeAll(Pageable pageable);
	public Habilidade getHabilidadeByHabilidadePK(long habilidadePK);
	public Habilidade create(HabilidadeForm habilidadeForm);
	public Habilidade save(HabilidadeForm habilidadeForm);
	public void delete(Long habilidadePK);
	
	public Habilidade converteHabilidadeForm(HabilidadeForm habilidadeForm);
	public HabilidadeForm converteHabilidade(Habilidade habilidade);
	public HabilidadeForm converteHabilidadeView(Habilidade habilidade);

	public HabilidadeForm habilidadeParametros(HabilidadeForm habilidadeForm);

	public List<Habilidade> getByHabilidadeNome(String habilidadeNome,Pageable pageable);
	public List<Habilidade> getByHabilidadeDescricao(String habilidadeDescricao,Pageable pageable);
	public List<Habilidade> getByHabilidadeNome(String habilidadeNome);
	public List<Habilidade> getByHabilidadeDescricao(String habilidadeDescricao);

}