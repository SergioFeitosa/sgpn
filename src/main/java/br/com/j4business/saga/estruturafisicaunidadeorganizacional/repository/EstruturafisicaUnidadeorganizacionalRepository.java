package br.com.j4business.saga.estruturafisicaunidadeorganizacional.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;

@Repository("estruturafisicaUnidadeorganizacionalRepository")
public interface EstruturafisicaUnidadeorganizacionalRepository extends JpaRepository<EstruturafisicaUnidadeorganizacional, Long>{

/*	 @Query("SELECT ea FROM EstruturafisicaUnidadeorganizacional ea where ea.unidadeorganizacional.unidadeorganizacionalPK = :id") 
	    List<EstruturafisicaUnidadeorganizacional> findByUnidadeorganizacionalPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.unidadeorganizacional p WHERE p = :unidadeorganizacional")
	public List<EstruturafisicaUnidadeorganizacional> findByUnidadeorganizacional(@Param("unidadeorganizacional")Unidadeorganizacional unidadeorganizacional);

	@Query("SELECT eu FROM EstruturafisicaUnidadeorganizacional eu INNER JOIN eu.unidadeorganizacional u INNER JOIN eu.estruturafisica e WHERE u = :unidadeorganizacional AND e = :estruturafisica")
	public EstruturafisicaUnidadeorganizacional findByEstruturafisicaAndUnidadeorganizacional( @Param("estruturafisica") Estruturafisica estruturafisica, @Param("unidadeorganizacional")Unidadeorganizacional unidadeorganizacional);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep")
	public List<EstruturafisicaUnidadeorganizacional> findByEstruturafisicaUnidadeorganizacionalAll();

	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep")
	public Page<EstruturafisicaUnidadeorganizacional> findByEstruturafisicaUnidadeorganizacionalAll(Pageable pageable);

	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.estruturafisica e WHERE e.estruturafisicaPK = :estruturafisicaPK")
	public List<EstruturafisicaUnidadeorganizacional> findByEstruturafisicaPK(@Param("estruturafisicaPK")long estruturafisicaPK,Pageable pageable);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.unidadeorganizacional p WHERE p.unidadeorganizacionalPK = :unidadeorganizacionalPK")
	public Page<EstruturafisicaUnidadeorganizacional> findByUnidadeorganizacionalPK(@Param("unidadeorganizacionalPK")long unidadeorganizacionalPK,Pageable pageable);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.unidadeorganizacional p WHERE p.unidadeorganizacionalPK = :unidadeorganizacionalPK")
	public List<EstruturafisicaUnidadeorganizacional> findByUnidadeorganizacionalPK(@Param("unidadeorganizacionalPK")long unidadeorganizacionalPK);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.estruturafisica e WHERE e.estruturafisicaNome like :estruturafisicaNome%")
	public List<EstruturafisicaUnidadeorganizacional> findByEstruturafisicaNome(@Param("estruturafisicaNome")String estruturafisicaNome,Pageable pageable);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.unidadeorganizacional p WHERE p.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<EstruturafisicaUnidadeorganizacional> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome,Pageable pageable);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.estruturafisica e WHERE e.estruturafisicaNome like :estruturafisicaNome%")
	public List<EstruturafisicaUnidadeorganizacional> findByEstruturafisicaNome(@Param("estruturafisicaNome")String estruturafisicaNome);
	
	@Query("SELECT ep FROM EstruturafisicaUnidadeorganizacional ep INNER JOIN ep.unidadeorganizacional p WHERE p.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<EstruturafisicaUnidadeorganizacional> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome);
	

}