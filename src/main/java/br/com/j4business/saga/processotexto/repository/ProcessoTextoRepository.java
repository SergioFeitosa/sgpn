package br.com.j4business.saga.processotexto.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processotexto.model.ProcessoTexto;

@Repository("processoTextoRepository")
public interface ProcessoTextoRepository extends JpaRepository<ProcessoTexto, Long>{

/*	 @Query("SELECT ea FROM ProcessoTexto ea where ea.texto.textoPK = :id") 
	    List<ProcessoTexto> findByTextoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.texto p WHERE p = :texto")
	public List<ProcessoTexto> findByTexto(@Param("texto")Texto texto);

	@Query("SELECT pt FROM ProcessoTexto pt INNER JOIN pt.texto t INNER JOIN pt.processo p WHERE t = :texto AND p = :processo")
	public ProcessoTexto findByProcessoAndTexto( @Param("processo") Processo processo, @Param("texto")Texto texto);
	
	@Query("SELECT ep FROM ProcessoTexto ep")
	public List<ProcessoTexto> findProcessoTextoAll(Pageable pageable);

	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoTexto> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoTexto> findByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK and ep.processoTextoStatus = 'ATIVO'")
	public List<ProcessoTexto> findProcessoTextoAtivoByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.texto p WHERE p.textoPK = :textoPK")
	public List<ProcessoTexto> findByTextoPK(@Param("textoPK")long textoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoTexto> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.texto p WHERE p.textoNome like :textoNome%")
	public List<ProcessoTexto> findByTextoNome(@Param("textoNome")String textoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoTexto> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM ProcessoTexto ep INNER JOIN ep.texto p WHERE p.textoNome like :textoNome%")
	public List<ProcessoTexto> findByTextoNome(@Param("textoNome")String textoNome);
	

}