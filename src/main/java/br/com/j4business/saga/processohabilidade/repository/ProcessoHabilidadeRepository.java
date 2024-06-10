package br.com.j4business.saga.processohabilidade.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidade;

@Repository("processoHabilidadeRepository")
public interface ProcessoHabilidadeRepository extends JpaRepository<ProcessoHabilidade, Long>{

/*	 @Query("SELECT ea FROM ProcessoHabilidade ea where ea.habilidade.habilidadePK = :id") 
	    List<ProcessoHabilidade> findByHabilidadePK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.habilidade p WHERE p = :habilidade")
	public List<ProcessoHabilidade> findByHabilidade(@Param("habilidade")Habilidade habilidade);

	@Query("SELECT ph FROM ProcessoHabilidade ph INNER JOIN ph.habilidade h INNER JOIN ph.processo p WHERE h = :habilidade AND p = :processo")
	public ProcessoHabilidade findByProcessoAndHabilidade( @Param("processo") Processo processo, @Param("habilidade")Habilidade habilidade);
	
	@Query("SELECT ep FROM ProcessoHabilidade ep")
	public List<ProcessoHabilidade> findProcessoHabilidadeAll(Pageable pageable);

	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoHabilidade> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.habilidade p WHERE p.habilidadePK = :habilidadePK")
	public List<ProcessoHabilidade> findByHabilidadePK(@Param("habilidadePK")long habilidadePK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoHabilidade> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.habilidade p WHERE p.habilidadeNome like :habilidadeNome%")
	public List<ProcessoHabilidade> findByHabilidadeNome(@Param("habilidadeNome")String habilidadeNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoHabilidade> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM ProcessoHabilidade ep INNER JOIN ep.habilidade p WHERE p.habilidadeNome like :habilidadeNome%")
	public List<ProcessoHabilidade> findByHabilidadeNome(@Param("habilidadeNome")String habilidadeNome);
	

}