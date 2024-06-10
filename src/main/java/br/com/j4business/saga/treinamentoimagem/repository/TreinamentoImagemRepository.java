package br.com.j4business.saga.treinamentoimagem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagem;

@Repository("treinamentoImagemRepository")
public interface TreinamentoImagemRepository extends JpaRepository<TreinamentoImagem, Long>{

/*	 @Query("SELECT ea FROM TreinamentoImagem ea where ea.imagem.imagemPK = :id") 
	    List<TreinamentoImagem> findByImagemPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.imagem p WHERE p = :imagem")
	public List<TreinamentoImagem> findByImagem(@Param("imagem")Imagem imagem);

	@Query("SELECT ti FROM TreinamentoImagem ti INNER JOIN ti.imagem i INNER JOIN ti.treinamento t WHERE i = :imagem AND t = :treinamento")
	public TreinamentoImagem findByTreinamentoAndImagem( @Param("treinamento") Treinamento treinamento, @Param("imagem")Imagem imagem);
	
	@Query("SELECT ep FROM TreinamentoImagem ep")
	public List<TreinamentoImagem> findTreinamentoImagemAll(Pageable pageable);

	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK")
	public List<TreinamentoImagem> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK")
	public List<TreinamentoImagem> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK and ep.treinamentoImagemStatus = 'ATIVO'")
	public List<TreinamentoImagem> findTreinamentoImagemAtivoByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.imagem p WHERE p.imagemPK = :imagemPK")
	public List<TreinamentoImagem> findByImagemPK(@Param("imagemPK")long imagemPK,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.treinamento e WHERE e.treinamentoNome like :treinamentoNome%")
	public List<TreinamentoImagem> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.imagem p WHERE p.imagemNome like :imagemNome%")
	public List<TreinamentoImagem> findByImagemNome(@Param("imagemNome")String imagemNome,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.treinamento e WHERE e.treinamentoNome like :treinamentoNome%")
	public List<TreinamentoImagem> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome);
	
	@Query("SELECT ep FROM TreinamentoImagem ep INNER JOIN ep.imagem p WHERE p.imagemNome like :imagemNome%")
	public List<TreinamentoImagem> findByImagemNome(@Param("imagemNome")String imagemNome);
	

}