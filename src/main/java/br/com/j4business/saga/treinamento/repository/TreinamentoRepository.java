package br.com.j4business.saga.treinamento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.treinamento.model.Treinamento;


@Repository("treinamentoRepository")

public interface TreinamentoRepository extends JpaRepository<Treinamento, Long>{

	@Query("SELECT p FROM Treinamento p WHERE p.treinamentoDescricao like :treinamentoDescricao%")
	public List<Treinamento> findByTreinamentoDescricao(@Param("treinamentoDescricao")String treinamentoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Treinamento p WHERE p.treinamentoNome like :treinamentoNome%")
	public List<Treinamento> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome,Pageable pageable);

	@Query("SELECT p FROM Treinamento p WHERE p.treinamentoDescricao like :treinamentoDescricao%")
	public List<Treinamento> findByTreinamentoDescricao(@Param("treinamentoDescricao")String treinamentoDescricao);
	
	@Query("SELECT p FROM Treinamento p WHERE p.treinamentoNome like :treinamentoNome%")
	public List<Treinamento> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome);

}