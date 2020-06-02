package br.com.j4business.saga.cenarioelemento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenarioelemento.model.CenarioElemento;

@Repository("cenarioElementoRepository")
public interface CenarioElementoRepository extends PagingAndSortingRepository<CenarioElemento, Long>{

/*	 @Query("SELECT ea FROM CenarioElemento ea where ea.elemento.elementoPK = :id") 
	    List<CenarioElemento> findByElementoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.elemento p WHERE p = :elemento")
	public List<CenarioElemento> findByElemento(@Param("elemento")Elemento elemento);

	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.elemento p INNER JOIN ep.cenario e WHERE p = :elemento AND s = :cenario")
	public CenarioElemento findByCenarioAndElemento( @Param("cenario") Cenario cenario, @Param("elemento")Elemento elemento);
	
	@Query("SELECT ep FROM CenarioElemento ep")
	public List<CenarioElemento> findCenarioElementoAll(Pageable pageable);

	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.cenario e WHERE e.cenarioPK = :cenarioPK")
	public List<CenarioElemento> findByCenarioPK(@Param("cenarioPK")long cenarioPK,Pageable pageable);
	
	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.elemento p WHERE p.elementoPK = :elementoPK")
	public List<CenarioElemento> findByElementoPK(@Param("elementoPK")long elementoPK,Pageable pageable);
	
	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.cenario e WHERE e.cenarioNome like :cenarioNome%")
	public List<CenarioElemento> findByCenarioNome(@Param("cenarioNome")String cenarioNome,Pageable pageable);
	
	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.elemento p WHERE p.elementoNome like :elementoNome%")
	public List<CenarioElemento> findByElementoNome(@Param("elementoNome")String elementoNome,Pageable pageable);
	
	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.cenario e WHERE e.cenarioNome like :cenarioNome%")
	public List<CenarioElemento> findByCenarioNome(@Param("cenarioNome")String cenarioNome);
	
	@Query("SELECT ep FROM CenarioElemento ep INNER JOIN ep.elemento p WHERE p.elementoNome like :elementoNome%")
	public List<CenarioElemento> findByElementoNome(@Param("elementoNome")String elementoNome);
	

}