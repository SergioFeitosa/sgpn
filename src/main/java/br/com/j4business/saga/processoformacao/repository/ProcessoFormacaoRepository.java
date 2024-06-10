package br.com.j4business.saga.processoformacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacao;

@Repository("processoFormacaoRepository")
public interface ProcessoFormacaoRepository extends JpaRepository<ProcessoFormacao, Long>{

/*	 @Query("SELECT ea FROM ProcessoFormacao ea where ea.formacao.formacaoPK = :id") 
	    List<ProcessoFormacao> findByFormacaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.formacao p WHERE p = :formacao")
	public List<ProcessoFormacao> findByFormacao(@Param("formacao")Formacao formacao);

	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.formacao f INNER JOIN pf.processo p WHERE f = :formacao AND p = :processo")
	public ProcessoFormacao findByProcessoAndFormacao( @Param("processo") Processo processo, @Param("formacao")Formacao formacao);
	
	@Query("SELECT pf FROM ProcessoFormacao pf")
	public List<ProcessoFormacao> findProcessoFormacaoAll(Pageable pageable);

	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoFormacao> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.formacao p WHERE p.formacaoPK = :formacaoPK")
	public List<ProcessoFormacao> findByFormacaoPK(@Param("formacaoPK")long formacaoPK,Pageable pageable);
	
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoFormacao> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.formacao p WHERE p.formacaoNome like :formacaoNome%")
	public List<ProcessoFormacao> findByFormacaoNome(@Param("formacaoNome")String formacaoNome,Pageable pageable);
	
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoFormacao> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.formacao p WHERE p.formacaoNome like :formacaoNome%")
	public List<ProcessoFormacao> findByFormacaoNome(@Param("formacaoNome")String formacaoNome);
	
	@Query("SELECT pf FROM ProcessoFormacao pf INNER JOIN pf.formacao f INNER JOIN pf.processo p WHERE  p.processoPK = :processoPK")
	public List<ProcessoFormacao> findMaxNivelByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);


}