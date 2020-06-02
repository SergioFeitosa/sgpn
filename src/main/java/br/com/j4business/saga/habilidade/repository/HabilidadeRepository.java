package br.com.j4business.saga.habilidade.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.habilidade.model.Habilidade;


@Repository("habilidadeRepository")

public interface HabilidadeRepository extends JpaRepository<Habilidade, Long>{

	@Query("SELECT p FROM Habilidade p WHERE p.habilidadeDescricao like :habilidadeDescricao%")
	public List<Habilidade> findByHabilidadeDescricao(@Param("habilidadeDescricao")String habilidadeDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Habilidade p WHERE p.habilidadeNome like :habilidadeNome%")
	public List<Habilidade> findByHabilidadeNome(@Param("habilidadeNome")String habilidadeNome,Pageable pageable);

	@Query("SELECT p FROM Habilidade p WHERE p.habilidadeDescricao like :habilidadeDescricao%")
	public List<Habilidade> findByHabilidadeDescricao(@Param("habilidadeDescricao")String habilidadeDescricao);
	
	@Query("SELECT p FROM Habilidade p WHERE p.habilidadeNome like :habilidadeNome%")
	public List<Habilidade> findByHabilidadeNome(@Param("habilidadeNome")String habilidadeNome);

}