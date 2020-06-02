package br.com.j4business.saga.colaboradorcertificacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;

@Repository("colaboradorCertificacaoRepository")
public interface ColaboradorCertificacaoRepository extends PagingAndSortingRepository<ColaboradorCertificacao, Long>{

/*	 @Query("SELECT ea FROM ColaboradorCertificacao ea where ea.certificacao.certificacaoPK = :id") 
	    List<ColaboradorCertificacao> findByCertificacaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.certificacao ce WHERE ce = :certificacao")
	public List<ColaboradorCertificacao> findByCertificacao(@Param("certificacao")Certificacao certificacao);

	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.certificacao ce INNER JOIN cc.colaborador co WHERE ce = :certificacao AND co = :colaborador")
	public ColaboradorCertificacao findByColaboradorAndCertificacao( @Param("colaborador") Colaborador colaborador, @Param("certificacao")Certificacao certificacao);
	
	@Query("SELECT cc FROM ColaboradorCertificacao cc")
	public List<ColaboradorCertificacao> findColaboradorCertificacaoAll(Pageable pageable);

	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.colaborador co WHERE co.pessoaPK = :colaboradorPK")
	public List<ColaboradorCertificacao> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.certificacao ce WHERE ce.certificacaoPK = :certificacaoPK")
	public List<ColaboradorCertificacao> findByCertificacaoPK(@Param("certificacaoPK")long certificacaoPK,Pageable pageable);
	
	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.colaborador co WHERE co.pessoaNome like :colaboradorNome%")
	public List<ColaboradorCertificacao> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.certificacao ce WHERE ce.certificacaoNome like :certificacaoNome%")
	public List<ColaboradorCertificacao> findByCertificacaoNome(@Param("certificacaoNome")String certificacaoNome,Pageable pageable);
	
	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.colaborador co WHERE co.pessoaNome like :colaboradorNome%")
	public List<ColaboradorCertificacao> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT cc FROM ColaboradorCertificacao cc INNER JOIN cc.certificacao ce WHERE ce.certificacaoNome like :certificacaoNome%")
	public List<ColaboradorCertificacao> findByCertificacaoNome(@Param("certificacaoNome")String certificacaoNome);
	

}