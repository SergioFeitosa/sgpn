package br.com.j4business.saga.contratotexto.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contratotexto.model.ContratoTexto;

@Repository("contratoTextoRepository")
public interface ContratoTextoRepository extends JpaRepository<ContratoTexto, Long>{

/*	 @Query("SELECT ea FROM ContratoTexto ea where ea.texto.textoPK = :id") 
	    List<ContratoTexto> findByTextoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.texto p WHERE p = :texto")
	public List<ContratoTexto> findByTexto(@Param("texto")Texto texto);

	@Query("SELECT ct FROM ContratoTexto ct INNER JOIN ct.texto t INNER JOIN ct.contrato c WHERE t = :texto AND c = :contrato")
	public ContratoTexto findByContratoAndTexto( @Param("contrato") Contrato contrato, @Param("texto")Texto texto);
	
	@Query("SELECT ep FROM ContratoTexto ep")
	public List<ContratoTexto> findContratoTextoAll(Pageable pageable);

	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK")
	public List<ContratoTexto> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK")
	public List<ContratoTexto> findByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK and ep.contratoTextoStatus = 'ATIVO'")
	public List<ContratoTexto> findContratoTextoAtivoByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.texto p WHERE p.textoPK = :textoPK")
	public List<ContratoTexto> findByTextoPK(@Param("textoPK")long textoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.contrato e WHERE e.contratoNome like :contratoNome%")
	public List<ContratoTexto> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.texto p WHERE p.textoNome like :textoNome%")
	public List<ContratoTexto> findByTextoNome(@Param("textoNome")String textoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.contrato e WHERE e.contratoNome like :contratoNome%")
	public List<ContratoTexto> findByContratoNome(@Param("contratoNome")String contratoNome);
	
	@Query("SELECT ep FROM ContratoTexto ep INNER JOIN ep.texto p WHERE p.textoNome like :textoNome%")
	public List<ContratoTexto> findByTextoNome(@Param("textoNome")String textoNome);
	

}