package br.com.j4business.saga.colaboradorhabilidade.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidade;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidadeForm;

@Service
public interface ColaboradorHabilidadeService {
	
	public List<ColaboradorHabilidade> getColaboradorHabilidadeAll(Pageable pageable);
	public ColaboradorHabilidade getColaboradorHabilidadeByColaboradorHabilidadePK(long colaboradorHabilidadePK);
	public ColaboradorHabilidade save(ColaboradorHabilidadeForm colaboradorHabilidadeForm);
	public void delete(Long colaboradorHabilidadePK);
	public void deleteByHabilidade(Habilidade habilidade);
	public ColaboradorHabilidade create(ColaboradorHabilidadeForm colaboradorHabilidadeForm);
	public ColaboradorHabilidade getByColaboradorAndHabilidade(Colaborador colaborador, Habilidade habilidade);

	public ColaboradorHabilidade converteColaboradorHabilidadeForm(ColaboradorHabilidadeForm colaboradorHabilidadeForm);
	public ColaboradorHabilidadeForm converteColaboradorHabilidade(ColaboradorHabilidade colaboradorHabilidade);
	public ColaboradorHabilidadeForm converteColaboradorHabilidadeView(ColaboradorHabilidade colaboradorHabilidade);

	public ColaboradorHabilidadeForm colaboradorHabilidadeParametros(ColaboradorHabilidadeForm colaboradorHabilidadeForm);

	public List<ColaboradorHabilidade> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorHabilidade> getByHabilidadePK(long habilidadePK,Pageable pageable);

	public List<ColaboradorHabilidade> getByHabilidadeNome(String habilidadeNome,Pageable pageable);
	public List<ColaboradorHabilidade> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorHabilidade> getByHabilidadeNome(String habilidadeNome);
	public List<ColaboradorHabilidade> getByColaboradorNome(String colaboradorNome);
	
}