package br.com.j4business.saga.acao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.acao.model.Acao;


@Repository("acaoRepository")

public interface AcaoRepository extends JpaRepository<Acao, Long>{

	@Query("SELECT p FROM Acao p WHERE p.acaoDescricao like :acaoDescricao%")
	public List<Acao> findByAcaoDescricao(@Param("acaoDescricao")String acaoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Acao p WHERE p.acaoNome like :acaoNome%")
	public List<Acao> findByAcaoNome(@Param("acaoNome")String acaoNome,Pageable pageable);

	@Query("SELECT p FROM Acao p WHERE p.acaoDescricao like :acaoDescricao%")
	public List<Acao> findByAcaoDescricao(@Param("acaoDescricao")String acaoDescricao);
	
	@Query("SELECT p FROM Acao p WHERE p.acaoNome like :acaoNome%")
	public List<Acao> findByAcaoNome(@Param("acaoNome")String acaoNome);

}