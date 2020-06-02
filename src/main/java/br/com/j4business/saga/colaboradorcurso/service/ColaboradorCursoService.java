package br.com.j4business.saga.colaboradorcurso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCursoForm;

@Service
public interface ColaboradorCursoService {
	
	public List<ColaboradorCurso> getColaboradorCursoAll(Pageable pageable);
	public ColaboradorCurso getColaboradorCursoByColaboradorCursoPK(long colaboradorCursoPK);
	public ColaboradorCurso save(ColaboradorCursoForm colaboradorCursoForm);
	public void delete(Long colaboradorCursoPK);
	public void deleteByCurso(Curso curso);
	public ColaboradorCurso create(ColaboradorCursoForm colaboradorCursoForm);
	public ColaboradorCurso getByColaboradorAndCurso(Colaborador colaborador, Curso curso);

	public ColaboradorCurso converteColaboradorCursoForm(ColaboradorCursoForm colaboradorCursoForm);
	public ColaboradorCursoForm converteColaboradorCurso(ColaboradorCurso colaboradorCurso);
	public ColaboradorCursoForm converteColaboradorCursoView(ColaboradorCurso colaboradorCurso);

	public ColaboradorCursoForm colaboradorCursoParametros(ColaboradorCursoForm colaboradorCursoForm);

	public List<ColaboradorCurso> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorCurso> getByCursoPK(long cursoPK,Pageable pageable);

	public List<ColaboradorCurso> getByCursoNome(String cursoNome,Pageable pageable);
	public List<ColaboradorCurso> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorCurso> getByCursoNome(String cursoNome);
	public List<ColaboradorCurso> getByColaboradorNome(String colaboradorNome);
	
}