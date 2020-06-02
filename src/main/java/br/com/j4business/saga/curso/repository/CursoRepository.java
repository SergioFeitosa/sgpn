package br.com.j4business.saga.curso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.curso.model.Curso;


@Repository("cursoRepository")

public interface CursoRepository extends JpaRepository<Curso, Long>{

	@Query("SELECT p FROM Curso p WHERE p.cursoDescricao like :cursoDescricao%")
	public List<Curso> findByCursoDescricao(@Param("cursoDescricao")String cursoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Curso p WHERE p.cursoNome like :cursoNome%")
	public List<Curso> findByCursoNome(@Param("cursoNome")String cursoNome,Pageable pageable);

	@Query("SELECT p FROM Curso p WHERE p.cursoDescricao like :cursoDescricao%")
	public List<Curso> findByCursoDescricao(@Param("cursoDescricao")String cursoDescricao);
	
	@Query("SELECT p FROM Curso p WHERE p.cursoNome like :cursoNome%")
	public List<Curso> findByCursoNome(@Param("cursoNome")String cursoNome);

}