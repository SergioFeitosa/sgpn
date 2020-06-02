package br.com.j4business.saga.contratoimagem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contratoimagem.model.ContratoImagem;

@Repository("contratoImagemRepository")
public interface ContratoImagemRepository extends PagingAndSortingRepository<ContratoImagem, Long>{

/*	 @Query("SELECT ea FROM ContratoImagem ea where ea.imagem.imagemPK = :id") 
	    List<ContratoImagem> findByImagemPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.imagem p WHERE p = :imagem")
	public List<ContratoImagem> findByImagem(@Param("imagem")Imagem imagem);

	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.imagem p INNER JOIN ep.contrato e WHERE p = :imagem AND s = :contrato")
	public ContratoImagem findByContratoAndImagem( @Param("contrato") Contrato contrato, @Param("imagem")Imagem imagem);
	
	@Query("SELECT ep FROM ContratoImagem ep")
	public List<ContratoImagem> findContratoImagemAll(Pageable pageable);

	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK")
	public List<ContratoImagem> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK")
	public List<ContratoImagem> findByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.contrato e WHERE e.contratoPK = :contratoPK and ep.contratoImagemStatus = 'ATIVO'")
	public List<ContratoImagem> findContratoImagemAtivoByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.imagem p WHERE p.imagemPK = :imagemPK")
	public List<ContratoImagem> findByImagemPK(@Param("imagemPK")long imagemPK,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.contrato e WHERE e.contratoNome like :contratoNome%")
	public List<ContratoImagem> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.imagem p WHERE p.imagemNome like :imagemNome%")
	public List<ContratoImagem> findByImagemNome(@Param("imagemNome")String imagemNome,Pageable pageable);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.contrato e WHERE e.contratoNome like :contratoNome%")
	public List<ContratoImagem> findByContratoNome(@Param("contratoNome")String contratoNome);
	
	@Query("SELECT ep FROM ContratoImagem ep INNER JOIN ep.imagem p WHERE p.imagemNome like :imagemNome%")
	public List<ContratoImagem> findByImagemNome(@Param("imagemNome")String imagemNome);
	

}