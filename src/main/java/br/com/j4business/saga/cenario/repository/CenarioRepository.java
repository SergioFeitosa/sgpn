package br.com.j4business.saga.cenario.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.cenario.model.Cenario;


@Repository("cenarioRepository")

public interface CenarioRepository extends JpaRepository<Cenario, Long>{

	@Query("SELECT p FROM Cenario p WHERE p.cenarioDescricao like :cenarioDescricao%")
	public List<Cenario> findByCenarioDescricao(@Param("cenarioDescricao")String cenarioDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Cenario p WHERE p.cenarioNome like :cenarioNome%")
	public List<Cenario> findByCenarioNome(@Param("cenarioNome")String cenarioNome,Pageable pageable);

	@Query("SELECT p FROM Cenario p WHERE p.cenarioDescricao like :cenarioDescricao%")
	public List<Cenario> findByCenarioDescricao(@Param("cenarioDescricao")String cenarioDescricao);
	
	@Query("SELECT p FROM Cenario p WHERE p.cenarioNome like :cenarioNome%")
	public List<Cenario> findByCenarioNome(@Param("cenarioNome")String cenarioNome);

}