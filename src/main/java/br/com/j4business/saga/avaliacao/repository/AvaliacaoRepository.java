package br.com.j4business.saga.avaliacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.avaliacao.model.Avaliacao;


@Repository("avaliacaoRepository")

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>{

	@Query("SELECT p FROM Avaliacao p WHERE p.avaliacaoDescricao like :avaliacaoDescricao%")
	public List<Avaliacao> findByAvaliacaoDescricao(@Param("avaliacaoDescricao")String avaliacaoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Avaliacao p WHERE p.avaliacaoNome like :avaliacaoNome%")
	public List<Avaliacao> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome,Pageable pageable);

	@Query("SELECT p FROM Avaliacao p WHERE p.avaliacaoDescricao like :avaliacaoDescricao%")
	public List<Avaliacao> findByAvaliacaoDescricao(@Param("avaliacaoDescricao")String avaliacaoDescricao);
	
	@Query("SELECT p FROM Avaliacao p WHERE p.avaliacaoNome like :avaliacaoNome%")
	public List<Avaliacao> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome);

}