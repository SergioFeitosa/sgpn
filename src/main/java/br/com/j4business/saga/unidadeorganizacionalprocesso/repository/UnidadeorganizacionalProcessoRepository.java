package br.com.j4business.saga.unidadeorganizacionalprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcesso;

@Repository("unidadeorganizacionalProcessoRepository")
public interface UnidadeorganizacionalProcessoRepository extends JpaRepository<UnidadeorganizacionalProcesso, Long>{

/*	 @Query("SELECT ea FROM UnidadeorganizacionalProcesso ea where ea.processo.processoPK = :id") 
	    List<UnidadeorganizacionalProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.processo p WHERE p = :processo")
	public List<UnidadeorganizacionalProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT up FROM UnidadeorganizacionalProcesso up INNER JOIN up.processo p INNER JOIN up.unidadeorganizacional u WHERE p = :processo AND u = :unidadeorganizacional")
	public UnidadeorganizacionalProcesso findByUnidadeorganizacionalAndProcesso( @Param("unidadeorganizacional") Unidadeorganizacional unidadeorganizacional, @Param("processo")Processo processo);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep")
	public List<UnidadeorganizacionalProcesso> findUnidadeorganizacionalProcessoAll(Pageable pageable);

	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalPK = :unidadeorganizacionalPK")
	public List<UnidadeorganizacionalProcesso> findByUnidadeorganizacionalPK(@Param("unidadeorganizacionalPK")long unidadeorganizacionalPK,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<UnidadeorganizacionalProcesso> findByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<UnidadeorganizacionalProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<UnidadeorganizacionalProcesso> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<UnidadeorganizacionalProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<UnidadeorganizacionalProcesso> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome);
	
	@Query("SELECT ep FROM UnidadeorganizacionalProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<UnidadeorganizacionalProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	

}