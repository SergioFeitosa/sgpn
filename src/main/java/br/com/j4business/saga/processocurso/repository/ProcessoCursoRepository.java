package br.com.j4business.saga.processocurso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;

@Repository("processoCursoRepository")
public interface ProcessoCursoRepository extends PagingAndSortingRepository<ProcessoCurso, Long>{

/*	 @Query("SELECT ea FROM ProcessoCurso ea where ea.curso.cursoPK = :id") 
	    List<ProcessoCurso> findByCursoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.curso p WHERE p = :curso")
	public List<ProcessoCurso> findByCurso(@Param("curso")Curso curso);

	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.curso p INNER JOIN ep.processo e WHERE p = :curso AND s = :processo")
	public ProcessoCurso findByProcessoAndCurso( @Param("processo") Processo processo, @Param("curso")Curso curso);
	
	@Query("SELECT ep FROM ProcessoCurso ep")
	public List<ProcessoCurso> findProcessoCursoAll(Pageable pageable);

	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoCurso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.curso p WHERE p.cursoPK = :cursoPK")
	public List<ProcessoCurso> findByCursoPK(@Param("cursoPK")long cursoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoCurso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.curso p WHERE p.cursoNome like :cursoNome%")
	public List<ProcessoCurso> findByCursoNome(@Param("cursoNome")String cursoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoCurso> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM ProcessoCurso ep INNER JOIN ep.curso p WHERE p.cursoNome like :cursoNome%")
	public List<ProcessoCurso> findByCursoNome(@Param("cursoNome")String cursoNome);
	

}