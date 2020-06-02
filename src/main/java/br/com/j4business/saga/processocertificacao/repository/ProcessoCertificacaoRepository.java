package br.com.j4business.saga.processocertificacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;

@Repository("processoCertificacaoRepository")
public interface ProcessoCertificacaoRepository extends PagingAndSortingRepository<ProcessoCertificacao, Long>{

/*	 @Query("SELECT ea FROM ProcessoCertificacao ea where ea.certificacao.certificacaoPK = :id") 
	    List<ProcessoCertificacao> findByCertificacaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.certificacao p WHERE p = :certificacao")
	public List<ProcessoCertificacao> findByCertificacao(@Param("certificacao")Certificacao certificacao);

	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.certificacao p INNER JOIN ep.processo e WHERE p = :certificacao AND s = :processo")
	public ProcessoCertificacao findByProcessoAndCertificacao( @Param("processo") Processo processo, @Param("certificacao")Certificacao certificacao);
	
	@Query("SELECT ep FROM ProcessoCertificacao ep")
	public List<ProcessoCertificacao> findProcessoCertificacaoAll(Pageable pageable);

	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoCertificacao> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.certificacao p WHERE p.certificacaoPK = :certificacaoPK")
	public List<ProcessoCertificacao> findByCertificacaoPK(@Param("certificacaoPK")long certificacaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoCertificacao> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.certificacao p WHERE p.certificacaoNome like :certificacaoNome%")
	public List<ProcessoCertificacao> findByCertificacaoNome(@Param("certificacaoNome")String certificacaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoCertificacao> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM ProcessoCertificacao ep INNER JOIN ep.certificacao p WHERE p.certificacaoNome like :certificacaoNome%")
	public List<ProcessoCertificacao> findByCertificacaoNome(@Param("certificacaoNome")String certificacaoNome);
	

}