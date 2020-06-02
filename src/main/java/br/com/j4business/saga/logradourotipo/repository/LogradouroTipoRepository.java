package br.com.j4business.saga.logradourotipo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.logradourotipo.model.LogradouroTipo;


@Repository("logradouroTipoRepository")

public interface LogradouroTipoRepository extends JpaRepository<LogradouroTipo, Long>{

	@Query("SELECT p FROM LogradouroTipo p WHERE p.logradouroTipoDescricao like :logradouroTipoDescricao%")
	public List<LogradouroTipo> findByLogradouroTipoDescricao(@Param("logradouroTipoDescricao")String logradouroTipoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM LogradouroTipo p WHERE p.logradouroTipoNome like :logradouroTipoNome%")
	public List<LogradouroTipo> findByLogradouroTipoNome(@Param("logradouroTipoNome")String logradouroTipoNome,Pageable pageable);

	@Query("SELECT p FROM LogradouroTipo p WHERE p.logradouroTipoDescricao like :logradouroTipoDescricao%")
	public List<LogradouroTipo> findByLogradouroTipoDescricao(@Param("logradouroTipoDescricao")String logradouroTipoDescricao);
	
	@Query("SELECT p FROM LogradouroTipo p WHERE p.logradouroTipoNome like :logradouroTipoNome%")
	public List<LogradouroTipo> findByLogradouroTipoNome(@Param("logradouroTipoNome")String logradouroTipoNome);

}