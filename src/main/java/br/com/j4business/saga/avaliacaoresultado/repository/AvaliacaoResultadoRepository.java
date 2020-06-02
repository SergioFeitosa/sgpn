package br.com.j4business.saga.avaliacaoresultado.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoresultado.model.AvaliacaoResultado;

@Repository("avaliacaoResultadoRepository")
public interface AvaliacaoResultadoRepository extends PagingAndSortingRepository<AvaliacaoResultado, Long>{

/*	 @Query("SELECT ea FROM AvaliacaoResultado ea where ea.resultado.resultadoPK = :id") 
	    List<AvaliacaoResultado> findByResultadoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.resultado p WHERE p = :resultado")
	public List<AvaliacaoResultado> findByResultado(@Param("resultado")Resultado resultado);

	@Query("SELECT ar FROM AvaliacaoResultado ar INNER JOIN ar.resultado r INNER JOIN ar.avaliacao a WHERE r = :resultado AND a = :avaliacao")
	public AvaliacaoResultado findByAvaliacaoAndResultado( @Param("avaliacao") Avaliacao avaliacao, @Param("resultado")Resultado resultado);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep")
	public List<AvaliacaoResultado> findAvaliacaoResultadoAll(Pageable pageable);

	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoPK = :avaliacaoPK")
	public List<AvaliacaoResultado> findByAvaliacaoPK(@Param("avaliacaoPK")long avaliacaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.resultado p WHERE p.resultadoPK = :resultadoPK")
	public List<AvaliacaoResultado> findByResultadoPK(@Param("resultadoPK")long resultadoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoResultado> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.resultado p WHERE p.resultadoNome like :resultadoNome%")
	public List<AvaliacaoResultado> findByResultadoNome(@Param("resultadoNome")String resultadoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoResultado> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.resultado p WHERE p.resultadoNome like :resultadoNome%")
	public List<AvaliacaoResultado> findByResultadoNome(@Param("resultadoNome")String resultadoNome);
	
	@Query("SELECT ep FROM AvaliacaoResultado ep INNER JOIN ep.avaliacaoProcesso ap INNER JOIN ep.elemento e WHERE ap.avaliacaoProcessoPK = :avaliacaoProcessoPK AND e.elementoPK = :elementoPK")
	public AvaliacaoResultado findByAvaliacaoProcessoPKAndElementoPK(@Param("avaliacaoProcessoPK")long avaliacaoProcessoPK,@Param("elementoPK")long elementoPK);
	
	@Query("SELECT ar FROM AvaliacaoResultado ar INNER JOIN ar.avaliacaoProcesso ap WHERE ap.avaliacaoProcessoPK = :avaliacaoProcessoPK")
	public List<AvaliacaoResultado> findByAvaliacaoProcessoPK(@Param("avaliacaoProcessoPK")long avaliacaoProcessoPK);
	

}