package br.com.j4business.saga.avaliacaoresultadocontrato.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoresultadocontrato.model.AvaliacaoResultadoContrato;

@Repository("avaliacaoResultadoContratoRepository")
public interface AvaliacaoResultadoContratoRepository extends JpaRepository<AvaliacaoResultadoContrato, Long>{

/*	 @Query("SELECT ea FROM AvaliacaoResultadoContrato ea where ea.resultado.resultadoPK = :id") 
	    List<AvaliacaoResultadoContrato> findByResultadoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.resultado p WHERE p = :resultado")
	public List<AvaliacaoResultadoContrato> findByResultado(@Param("resultado")Resultado resultado);

	@Query("SELECT ar FROM AvaliacaoResultadoContrato ar INNER JOIN ar.resultado r INNER JOIN ar.avaliacao a WHERE r = :resultado AND a = :avaliacao")
	public AvaliacaoResultadoContrato findByAvaliacaoAndResultado( @Param("avaliacao") Avaliacao avaliacao, @Param("resultado")Resultado resultado);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep")
	public List<AvaliacaoResultadoContrato> findAvaliacaoResultadoContratoAll(Pageable pageable);

	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoPK = :avaliacaoPK")
	public List<AvaliacaoResultadoContrato> findByAvaliacaoPK(@Param("avaliacaoPK")long avaliacaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.resultado p WHERE p.resultadoPK = :resultadoPK")
	public List<AvaliacaoResultadoContrato> findByResultadoPK(@Param("resultadoPK")long resultadoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoResultadoContrato> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.resultado p WHERE p.resultadoNome like :resultadoNome%")
	public List<AvaliacaoResultadoContrato> findByResultadoNome(@Param("resultadoNome")String resultadoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoResultadoContrato> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.resultado p WHERE p.resultadoNome like :resultadoNome%")
	public List<AvaliacaoResultadoContrato> findByResultadoNome(@Param("resultadoNome")String resultadoNome);
	
	@Query("SELECT ep FROM AvaliacaoResultadoContrato ep INNER JOIN ep.avaliacaoContrato ap INNER JOIN ep.elemento e WHERE ap.avaliacaoContratoPK = :avaliacaoContratoPK AND e.elementoPK = :elementoPK")
	public AvaliacaoResultadoContrato findByAvaliacaoContratoPKAndElementoPK(@Param("avaliacaoContratoPK")long avaliacaoContratoPK,@Param("elementoPK")long elementoPK);
	
	@Query("SELECT ar FROM AvaliacaoResultadoContrato ar INNER JOIN ar.avaliacaoContrato ap WHERE ap.avaliacaoContratoPK = :avaliacaoContratoPK")
	public List<AvaliacaoResultadoContrato> findByAvaliacaoContratoPK(@Param("avaliacaoContratoPK")long avaliacaoContratoPK);
	

}