package br.com.j4business.saga.colaboradorcurso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;

@Repository("colaboradorCursoRepository")
public interface ColaboradorCursoRepository extends JpaRepository<ColaboradorCurso, Long>{

/*	 @Query("SELECT ea FROM ColaboradorCurso ea where ea.curso.cursoPK = :id") 
	    List<ColaboradorCurso> findByCursoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.curso p WHERE p = :curso")
	public List<ColaboradorCurso> findByCurso(@Param("curso")Curso curso);

	@Query("SELECT cc FROM ColaboradorCurso cc INNER JOIN cc.curso c INNER JOIN cc.colaborador l WHERE c = :curso AND l = :colaborador")
	public ColaboradorCurso findByColaboradorAndCurso( @Param("colaborador") Colaborador colaborador, @Param("curso")Curso curso);
	
	@Query("SELECT ep FROM ColaboradorCurso ep")
	public List<ColaboradorCurso> findColaboradorCursoAll(Pageable pageable);

	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.colaborador e WHERE e.pessoaPK = :colaboradorPK")
	public List<ColaboradorCurso> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.curso p WHERE p.cursoPK = :cursoPK")
	public List<ColaboradorCurso> findByCursoPK(@Param("cursoPK")long cursoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorCurso> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.curso p WHERE p.cursoNome like :cursoNome%")
	public List<ColaboradorCurso> findByCursoNome(@Param("cursoNome")String cursoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.colaborador e WHERE e.pessoaNome like :colaboradorNome%")
	public List<ColaboradorCurso> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT ep FROM ColaboradorCurso ep INNER JOIN ep.curso p WHERE p.cursoNome like :cursoNome%")
	public List<ColaboradorCurso> findByCursoNome(@Param("cursoNome")String cursoNome);
	

}