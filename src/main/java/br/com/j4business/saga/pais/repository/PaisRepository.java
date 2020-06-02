package br.com.j4business.saga.pais.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.pais.model.Pais;


@Repository("paisRepository")

public interface PaisRepository extends JpaRepository<Pais, Long>{

	@Query("SELECT p FROM Pais p WHERE p.paisNome like :paisNome%")
	public List<Pais> findByPaisNome(@Param("paisNome")String paisNome,Pageable pageable);

	@Query("SELECT p FROM Pais p WHERE p.paisSigla like :paisSigla%")
	public List<Pais> findByPaisSigla(@Param("paisSigla")String paisSigla,Pageable pageable);
	
	@Query("SELECT p FROM Pais p WHERE p.paisNome like :paisNome%")
	public List<Pais> findByPaisNome(@Param("paisNome")String paisNome);

	@Query("SELECT p FROM Pais p WHERE p.paisSigla like :paisSigla%")
	public List<Pais> findByPaisSigla(@Param("paisSigla")String paisSigla);
	
}