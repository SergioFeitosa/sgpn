package br.com.j4business.saga.colaboradorformacao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacao;

@Repository("colaboradorFormacaoRepository")
public interface ColaboradorFormacaoRepository extends JpaRepository<ColaboradorFormacao, Long>{

/*	 @Query("SELECT ea FROM ColaboradorFormacao ea where ea.formacao.formacaoPK = :id") 
	    List<ColaboradorFormacao> findByFormacaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.formacao f WHERE f = :formacao")
	public List<ColaboradorFormacao> findByFormacao(@Param("formacao")Formacao formacao);

	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.formacao f INNER JOIN cf.colaborador c WHERE f = :formacao AND c = :colaborador")
	public ColaboradorFormacao findByColaboradorAndFormacao( @Param("colaborador") Colaborador colaborador, @Param("formacao")Formacao formacao);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf")
	public List<ColaboradorFormacao> findColaboradorFormacaoAll(Pageable pageable);

	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.colaborador c WHERE c.pessoaPK = :colaboradorPK")
	public List<ColaboradorFormacao> findByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.formacao f WHERE f.formacaoPK = :formacaoPK")
	public List<ColaboradorFormacao> findByFormacaoPK(@Param("formacaoPK")long formacaoPK,Pageable pageable);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.colaborador c WHERE c.pessoaNome like :colaboradorNome%")
	public List<ColaboradorFormacao> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.formacao f WHERE f.formacaoNome like :formacaoNome%")
	public List<ColaboradorFormacao> findByFormacaoNome(@Param("formacaoNome")String formacaoNome,Pageable pageable);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.colaborador c WHERE c.pessoaNome like :colaboradorNome%")
	public List<ColaboradorFormacao> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.formacao f WHERE f.formacaoNome like :formacaoNome%")
	public List<ColaboradorFormacao> findByFormacaoNome(@Param("formacaoNome")String formacaoNome);
	
	@Query("SELECT cf FROM ColaboradorFormacao cf INNER JOIN cf.formacao f INNER JOIN cf.colaborador c WHERE c.pessoaPK = :colaboradorPK order by f.formacaoNivel desc")
	public List<ColaboradorFormacao> findMaxNivelByColaboradorPK(@Param("colaboradorPK")long colaboradorPK,Pageable pageable);

}