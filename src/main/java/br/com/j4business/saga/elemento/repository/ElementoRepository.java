package br.com.j4business.saga.elemento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.elemento.model.Elemento;


@Repository("elementoRepository")

public interface ElementoRepository extends JpaRepository<Elemento, Long>{

	@Query("SELECT p FROM Elemento p WHERE p.elementoNome = :elementoNome")
	public Elemento findByElementoNomeCompleto(@Param("elementoNome")String elementoNome);

	@Query("SELECT p FROM Elemento p WHERE p.elementoDescricao = :elementoDescricao")
	public Elemento findByElementoDescricaoCompleto(@Param("elementoDescricao")String elementoDescricao);
	
	@Query("SELECT p FROM Elemento p WHERE p.elementoDescricao like :elementoDescricao%")
	public List<Elemento> findByElementoDescricao(@Param("elementoDescricao")String elementoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Elemento p WHERE p.elementoNome like :elementoNome%")
	public List<Elemento> findByElementoNome(@Param("elementoNome")String elementoNome,Pageable pageable);

	@Query("SELECT p FROM Elemento p WHERE p.elementoDescricao like :elementoDescricao%")
	public List<Elemento> findByElementoDescricao(@Param("elementoDescricao")String elementoDescricao);
	
	@Query("SELECT p FROM Elemento p WHERE p.elementoNome like :elementoNome%")
	public List<Elemento> findByElementoNome(@Param("elementoNome")String elementoNome);

}