package br.com.j4business.saga.unidadeorganizacionalcenario.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;

@Repository("unidadeorganizacionalCenarioRepository")
public interface UnidadeorganizacionalCenarioRepository extends JpaRepository<UnidadeorganizacionalCenario, Long>{

/*	 @Query("SELECT ea FROM UnidadeorganizacionalCenario ea where ea.cenario.cenarioPK = :id") 
	    List<UnidadeorganizacionalCenario> findByCenarioPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.cenario p WHERE p = :cenario")
	public List<UnidadeorganizacionalCenario> findByCenario(@Param("cenario")Cenario cenario);

	@Query("SELECT uc FROM UnidadeorganizacionalCenario uc INNER JOIN uc.cenario c INNER JOIN uc.unidadeorganizacional u WHERE c = :cenario AND u = :unidadeorganizacional")
	public UnidadeorganizacionalCenario findByUnidadeorganizacionalAndCenario( @Param("unidadeorganizacional") Unidadeorganizacional unidadeorganizacional, @Param("cenario")Cenario cenario);
	
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep")
	public List<UnidadeorganizacionalCenario> findUnidadeorganizacionalCenarioAll(Pageable pageable);

	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalPK = :unidadeorganizacionalPK")
	public List<UnidadeorganizacionalCenario> findByUnidadeorganizacionalPK(@Param("unidadeorganizacionalPK")long unidadeorganizacionalPK,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.cenario p WHERE p.cenarioPK = :cenarioPK")
	public List<UnidadeorganizacionalCenario> findByCenarioPK(@Param("cenarioPK")long cenarioPK,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<UnidadeorganizacionalCenario> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.cenario p WHERE p.cenarioNome like :cenarioNome%")
	public List<UnidadeorganizacionalCenario> findByCenarioNome(@Param("cenarioNome")String cenarioNome,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<UnidadeorganizacionalCenario> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome);
	
	@Query("SELECT ep FROM UnidadeorganizacionalCenario ep INNER JOIN ep.cenario p WHERE p.cenarioNome like :cenarioNome%")
	public List<UnidadeorganizacionalCenario> findByCenarioNome(@Param("cenarioNome")String cenarioNome);
	

}