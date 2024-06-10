package br.com.j4business.saga.treinamentotexto.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTexto;

@Repository("treinamentoTextoRepository")
public interface TreinamentoTextoRepository extends JpaRepository<TreinamentoTexto, Long>{

/*	 @Query("SELECT ea FROM TreinamentoTexto ea where ea.texto.textoPK = :id") 
	    List<TreinamentoTexto> findByTextoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.texto p WHERE p = :texto")
	public List<TreinamentoTexto> findByTexto(@Param("texto")Texto texto);

	@Query("SELECT tr FROM TreinamentoTexto tr INNER JOIN tr.texto t INNER JOIN tr.treinamento r WHERE t = :texto AND r = :treinamento")
	public TreinamentoTexto findByTreinamentoAndTexto( @Param("treinamento") Treinamento treinamento, @Param("texto")Texto texto);
	
	@Query("SELECT ep FROM TreinamentoTexto ep")
	public List<TreinamentoTexto> findTreinamentoTextoAll(Pageable pageable);

	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK")
	public List<TreinamentoTexto> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK")
	public List<TreinamentoTexto> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.treinamento e WHERE e.treinamentoPK = :treinamentoPK and ep.treinamentoTextoStatus = 'ATIVO'")
	public List<TreinamentoTexto> findTreinamentoTextoAtivoByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.texto p WHERE p.textoPK = :textoPK")
	public List<TreinamentoTexto> findByTextoPK(@Param("textoPK")long textoPK,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.treinamento e WHERE e.treinamentoNome like :treinamentoNome%")
	public List<TreinamentoTexto> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.texto p WHERE p.textoNome like :textoNome%")
	public List<TreinamentoTexto> findByTextoNome(@Param("textoNome")String textoNome,Pageable pageable);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.treinamento e WHERE e.treinamentoNome like :treinamentoNome%")
	public List<TreinamentoTexto> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome);
	
	@Query("SELECT ep FROM TreinamentoTexto ep INNER JOIN ep.texto p WHERE p.textoNome like :textoNome%")
	public List<TreinamentoTexto> findByTextoNome(@Param("textoNome")String textoNome);
	

}