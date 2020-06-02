package br.com.j4business.saga.certificacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.certificacao.model.Certificacao;


@Repository("certificacaoRepository")

public interface CertificacaoRepository extends JpaRepository<Certificacao, Long>{

	@Query("SELECT p FROM Certificacao p WHERE p.certificacaoDescricao like :certificacaoDescricao%")
	public List<Certificacao> findByCertificacaoDescricao(@Param("certificacaoDescricao")String certificacaoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Certificacao p WHERE p.certificacaoNome like :certificacaoNome%")
	public List<Certificacao> findByCertificacaoNome(@Param("certificacaoNome")String certificacaoNome,Pageable pageable);

	@Query("SELECT p FROM Certificacao p WHERE p.certificacaoDescricao like :certificacaoDescricao%")
	public List<Certificacao> findByCertificacaoDescricao(@Param("certificacaoDescricao")String certificacaoDescricao);
	
	@Query("SELECT p FROM Certificacao p WHERE p.certificacaoNome like :certificacaoNome%")
	public List<Certificacao> findByCertificacaoNome(@Param("certificacaoNome")String certificacaoNome);

}