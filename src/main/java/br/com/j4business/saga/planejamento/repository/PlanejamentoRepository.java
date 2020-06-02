package br.com.j4business.saga.planejamento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.planejamento.model.Planejamento;


@Repository("planejamentoRepository")

public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long>{

	@Query("SELECT p FROM Planejamento p WHERE p.planejamentoDescricao like :planejamentoDescricao%")
	public List<Planejamento> findByPlanejamentoDescricao(@Param("planejamentoDescricao")String planejamentoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Planejamento p WHERE p.planejamentoNome like :planejamentoNome%")
	public List<Planejamento> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome,Pageable pageable);

	@Query("SELECT p FROM Planejamento p WHERE p.planejamentoDescricao like :planejamentoDescricao%")
	public List<Planejamento> findByPlanejamentoDescricao(@Param("planejamentoDescricao")String planejamentoDescricao);
	
	@Query("SELECT p FROM Planejamento p WHERE p.planejamentoNome like :planejamentoNome%")
	public List<Planejamento> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome);

}