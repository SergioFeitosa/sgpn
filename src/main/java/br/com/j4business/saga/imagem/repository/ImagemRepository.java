package br.com.j4business.saga.imagem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.imagem.model.Imagem;


@Repository("imagemRepository")

public interface ImagemRepository extends JpaRepository<Imagem, Long>{

	@Query("SELECT p FROM Imagem p WHERE p.imagemDescricao like :imagemDescricao%")
	public List<Imagem> findByImagemDescricao(@Param("imagemDescricao")String imagemDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Imagem p WHERE p.imagemNome like :imagemNome%")
	public List<Imagem> findByImagemNome(@Param("imagemNome")String imagemNome,Pageable pageable);

	@Query("SELECT p FROM Imagem p WHERE p.imagemDescricao like :imagemDescricao%")
	public List<Imagem> findByImagemDescricao(@Param("imagemDescricao")String imagemDescricao);
	
	@Query("SELECT p FROM Imagem p WHERE p.imagemNome like :imagemNome%")
	public List<Imagem> findByImagemNome(@Param("imagemNome")String imagemNome);

}