package br.com.j4business.saga.curso.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.curso.model.CursoForm;

@Service
public interface CursoService {
	
	public List<Curso> getCursoAll();
	public Page<Curso> getCursoAll(Pageable pageable);
	public Curso getCursoByCursoPK(long cursoPK);
	public Curso create(CursoForm cursoForm);
	public Curso save(CursoForm cursoForm);
	public void delete(Long cursoPK);
	
	public Curso converteCursoForm(CursoForm cursoForm);
	public CursoForm converteCurso(Curso curso);
	public CursoForm converteCursoView(Curso curso);

	public CursoForm cursoParametros(CursoForm cursoForm);

	public List<Curso> getByCursoNome(String cursoNome,Pageable pageable);
	public List<Curso> getByCursoDescricao(String cursoDescricao,Pageable pageable);
	public List<Curso> getByCursoNome(String cursoNome);
	public List<Curso> getByCursoDescricao(String cursoDescricao);

}