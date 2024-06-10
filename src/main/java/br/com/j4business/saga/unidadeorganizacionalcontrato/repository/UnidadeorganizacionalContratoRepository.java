package br.com.j4business.saga.unidadeorganizacionalcontrato.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContrato;

@Repository("unidadeorganizacionalContratoRepository")
public interface UnidadeorganizacionalContratoRepository extends JpaRepository<UnidadeorganizacionalContrato, Long>{

/*	 @Query("SELECT ea FROM UnidadeorganizacionalContrato ea where ea.contrato.contratoPK = :id") 
	    List<UnidadeorganizacionalContrato> findByContratoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.contrato p WHERE p = :contrato")
	public List<UnidadeorganizacionalContrato> findByContrato(@Param("contrato")Contrato contrato);

	@Query("SELECT uc FROM UnidadeorganizacionalContrato uc INNER JOIN uc.contrato c INNER JOIN uc.unidadeorganizacional u WHERE c = :contrato AND u = :unidadeorganizacional")
	public UnidadeorganizacionalContrato findByUnidadeorganizacionalAndContrato( @Param("unidadeorganizacional") Unidadeorganizacional unidadeorganizacional, @Param("contrato")Contrato contrato);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep")
	public List<UnidadeorganizacionalContrato> findUnidadeorganizacionalContratoAll(Pageable pageable);

	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalPK = :unidadeorganizacionalPK")
	public List<UnidadeorganizacionalContrato> findByUnidadeorganizacionalPK(@Param("unidadeorganizacionalPK")long unidadeorganizacionalPK,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.contrato p WHERE p.contratoPK = :contratoPK")
	public List<UnidadeorganizacionalContrato> findByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.contrato p WHERE p.contratoPK = :contratoPK")
	public List<UnidadeorganizacionalContrato> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<UnidadeorganizacionalContrato> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<UnidadeorganizacionalContrato> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.unidadeorganizacional e WHERE e.unidadeorganizacionalNome like :unidadeorganizacionalNome%")
	public List<UnidadeorganizacionalContrato> findByUnidadeorganizacionalNome(@Param("unidadeorganizacionalNome")String unidadeorganizacionalNome);
	
	@Query("SELECT ep FROM UnidadeorganizacionalContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<UnidadeorganizacionalContrato> findByContratoNome(@Param("contratoNome")String contratoNome);
	

}