package br.com.j4business.saga.estruturafisica.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.estruturafisica.model.Estruturafisica;


@Repository("estruturafisicaRepository")

public interface EstruturafisicaRepository extends JpaRepository<Estruturafisica, Long>{

	@Query("SELECT p FROM Estruturafisica p WHERE p.estruturafisicaDescricao like :estruturafisicaDescricao%")
	public List<Estruturafisica> findByEstruturafisicaDescricao(@Param("estruturafisicaDescricao")String estruturafisicaDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Estruturafisica p WHERE p.estruturafisicaNome like :estruturafisicaNome%")
	public List<Estruturafisica> findByEstruturafisicaNome(@Param("estruturafisicaNome")String estruturafisicaNome,Pageable pageable);

	@Query("SELECT p FROM Estruturafisica p WHERE p.estruturafisicaDescricao like :estruturafisicaDescricao%")
	public List<Estruturafisica> findByEstruturafisicaDescricao(@Param("estruturafisicaDescricao")String estruturafisicaDescricao);
	
	@Query("SELECT p FROM Estruturafisica p WHERE p.estruturafisicaNome like :estruturafisicaNome%")
	public List<Estruturafisica> findByEstruturafisicaNome(@Param("estruturafisicaNome")String estruturafisicaNome);

}