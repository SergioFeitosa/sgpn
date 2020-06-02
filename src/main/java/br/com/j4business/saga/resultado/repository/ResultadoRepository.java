package br.com.j4business.saga.resultado.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.resultado.model.Resultado;


@Repository("resultadoRepository")

public interface ResultadoRepository extends JpaRepository<Resultado, Long>{

	@Query("SELECT p FROM Resultado p WHERE p.resultadoDescricao like :resultadoDescricao%")
	public List<Resultado> findByResultadoDescricao(@Param("resultadoDescricao")String resultadoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Resultado p WHERE p.resultadoNome like :resultadoNome%")
	public List<Resultado> findByResultadoNome(@Param("resultadoNome")String resultadoNome,Pageable pageable);

	@Query("SELECT p FROM Resultado p WHERE p.resultadoDescricao like :resultadoDescricao%")
	public List<Resultado> findByResultadoDescricao(@Param("resultadoDescricao")String resultadoDescricao);
	
	@Query("SELECT p FROM Resultado p WHERE p.resultadoNome like :resultadoNome%")
	public List<Resultado> findByResultadoNome(@Param("resultadoNome")String resultadoNome);

	@Query("SELECT p FROM Resultado p WHERE p.resultadoNome = :resultadoNome")
	public Resultado findByResultadoNomeCompleto(@Param("resultadoNome")String resultadoNome);

}