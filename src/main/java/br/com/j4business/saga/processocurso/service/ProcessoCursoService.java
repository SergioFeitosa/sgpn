package br.com.j4business.saga.processocurso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;
import br.com.j4business.saga.processocurso.model.ProcessoCursoForm;

@Service
public interface ProcessoCursoService {
	
	public List<ProcessoCurso> getProcessoCursoAll(Pageable pageable);
	public ProcessoCurso getProcessoCursoByProcessoCursoPK(long processoCursoPK);
	public ProcessoCurso save(ProcessoCursoForm processoCursoForm);
	public void delete(Long processoCursoPK);
	public void deleteByCurso(Curso curso);
	public ProcessoCurso create(ProcessoCursoForm processoCursoForm);
	public ProcessoCurso getByProcessoAndCurso(Processo processo, Curso curso);

	public ProcessoCurso converteProcessoCursoForm(ProcessoCursoForm processoCursoForm);
	public ProcessoCursoForm converteProcessoCurso(ProcessoCurso processoCurso);
	public ProcessoCursoForm converteProcessoCursoView(ProcessoCurso processoCurso);

	public ProcessoCursoForm processoCursoParametros(ProcessoCursoForm processoCursoForm);

	public List<ProcessoCurso> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoCurso> getByCursoPK(long cursoPK,Pageable pageable);

	public List<ProcessoCurso> getByCursoNome(String cursoNome,Pageable pageable);
	public List<ProcessoCurso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoCurso> getByCursoNome(String cursoNome);
	public List<ProcessoCurso> getByProcessoNome(String processoNome);
	
}