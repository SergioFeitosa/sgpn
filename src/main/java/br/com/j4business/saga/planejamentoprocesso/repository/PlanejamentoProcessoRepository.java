package br.com.j4business.saga.planejamentoprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcesso;

@Repository("planejamentoProcessoRepository")
public interface PlanejamentoProcessoRepository extends PagingAndSortingRepository<PlanejamentoProcesso, Long>{

/*	 @Query("SELECT ea FROM PlanejamentoProcesso ea where ea.processo.processoPK = :id") 
	    List<PlanejamentoProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.processo p WHERE p = :processo")
	public List<PlanejamentoProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.processo p INNER JOIN ep.planejamento e WHERE p = :processo AND s = :planejamento")
	public PlanejamentoProcesso findByPlanejamentoAndProcesso( @Param("planejamento") Planejamento planejamento, @Param("processo")Processo processo);
	
	@Query("SELECT ep FROM PlanejamentoProcesso ep")
	public List<PlanejamentoProcesso> findPlanejamentoProcessoAll(Pageable pageable);

	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.planejamento e WHERE e.planejamentoPK = :planejamentoPK")
	public List<PlanejamentoProcesso> findByPlanejamentoPK(@Param("planejamentoPK")long planejamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<PlanejamentoProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.planejamento e WHERE e.planejamentoNome like :planejamentoNome%")
	public List<PlanejamentoProcesso> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<PlanejamentoProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.planejamento e WHERE e.planejamentoNome like :planejamentoNome%")
	public List<PlanejamentoProcesso> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome);
	
	@Query("SELECT ep FROM PlanejamentoProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<PlanejamentoProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	

}