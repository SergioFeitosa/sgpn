package br.com.j4business.saga.planejamentoacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcao;

@Repository("planejamentoAcaoRepository")
public interface PlanejamentoAcaoRepository extends JpaRepository<PlanejamentoAcao, Long>{

/*	 @Query("SELECT ea FROM PlanejamentoAcao ea where ea.planejamento.planejamentoPK = :id") 
	    List<PlanejamentoAcao> findByPlanejamentoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pa FROM PlanejamentoAcao pa INNER JOIN pa.planejamento p WHERE p = :planejamento")
	public List<PlanejamentoAcao> findByPlanejamento(@Param("planejamento")Planejamento planejamento);

	@Query("SELECT pa FROM PlanejamentoAcao pa INNER JOIN pa.planejamento p INNER JOIN pa.acao a WHERE p = :planejamento AND a = :acao")
	public PlanejamentoAcao findByPlanejamentoAndAcao(@Param("planejamento")Planejamento planejamento, @Param("acao") Acao acao);
	
	@Query("SELECT pa FROM PlanejamentoAcao pa")
	public List<PlanejamentoAcao> findPlanejamentoAcaoAll(Pageable pageable);

	@Query("SELECT pa FROM PlanejamentoAcao pa INNER JOIN pa.acao a WHERE a.acaoNome like :acaoNome%")
	public List<PlanejamentoAcao> findByAcaoNome(@Param("acaoNome")String acaoNome,Pageable pageable);
	
	@Query("SELECT pa FROM PlanejamentoAcao pa INNER JOIN pa.planejamento p WHERE p.planejamentoNome like :planejamentoNome%")
	public List<PlanejamentoAcao> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome,Pageable pageable);
	
	@Query("SELECT pa FROM PlanejamentoAcao pa INNER JOIN pa.planejamento p WHERE p.planejamentoPK = :planejamentoPK")
	public List<PlanejamentoAcao> findByPlanejamentoPK(@Param("planejamentoPK")long planejamentoPK,Pageable pageable);
	


}