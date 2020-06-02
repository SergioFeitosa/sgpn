package br.com.j4business.saga.texto.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.texto.model.Texto;


@Repository("textoRepository")

public interface TextoRepository extends JpaRepository<Texto, Long>{

	@Query("SELECT p FROM Texto p WHERE p.textoDescricao like :textoDescricao%")
	public List<Texto> findByTextoDescricao(@Param("textoDescricao")String textoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Texto p WHERE p.textoNome like :textoNome%")
	public List<Texto> findByTextoNome(@Param("textoNome")String textoNome,Pageable pageable);

	@Query("SELECT p FROM Texto p WHERE p.textoDescricao like :textoDescricao%")
	public List<Texto> findByTextoDescricao(@Param("textoDescricao")String textoDescricao);
	
	@Query("SELECT p FROM Texto p WHERE p.textoNome like :textoNome%")
	public List<Texto> findByTextoNome(@Param("textoNome")String textoNome);

}