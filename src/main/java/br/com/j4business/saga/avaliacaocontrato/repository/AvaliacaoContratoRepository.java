package br.com.j4business.saga.avaliacaocontrato.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContrato;

@Repository("avaliacaoContratoRepository")
public interface AvaliacaoContratoRepository extends JpaRepository<AvaliacaoContrato, Long>{

/*	 @Query("SELECT ea FROM AvaliacaoContrato ea where ea.contrato.contratoPK = :id") 
	    List<AvaliacaoContrato> findByContratoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.contrato p WHERE p = :contrato")
	public List<AvaliacaoContrato> findByContrato(@Param("contrato")Contrato contrato);

	@Query("SELECT ac FROM AvaliacaoContrato ac INNER JOIN ac.contrato c INNER JOIN ac.avaliacao a WHERE c = :contrato AND a = :avaliacao")
	public AvaliacaoContrato findByAvaliacaoAndContrato( @Param("avaliacao") Avaliacao avaliacao, @Param("contrato")Contrato contrato);
	
	@Query("SELECT ep FROM AvaliacaoContrato ep")
	public List<AvaliacaoContrato> findAvaliacaoContratoAll(Pageable pageable);

	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoPK = :avaliacaoPK")
	public List<AvaliacaoContrato> findByAvaliacaoPK(@Param("avaliacaoPK")long avaliacaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.contrato p WHERE p.contratoPK = :contratoPK")
	public List<AvaliacaoContrato> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoContrato> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<AvaliacaoContrato> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoContrato> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome);
	
	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<AvaliacaoContrato> findByContratoNome(@Param("contratoNome")String contratoNome);
	
	@Query("SELECT ap FROM AvaliacaoContrato ap INNER JOIN ap.contrato p INNER JOIN ap.questionario q WHERE p = :contrato AND q = :questionario")
	public List<AvaliacaoContrato> findByContratoAndQuestionario( @Param("contrato")Contrato contrato, @Param("questionario") Questionario questionario);

	@Query("SELECT ap FROM AvaliacaoContrato ap "
			+ "INNER JOIN ap.avaliacao av "
			+ "INNER JOIN ap.contrato p "
			+ "INNER JOIN ap.questionario q "
			+ "WHERE av = :avaliacao AND "
			+ "p = :contrato AND "
			+ "q = :questionario AND "
			+ "ap.avaliacaoContratoPeriodo like :periodoNome% "
			)
	public AvaliacaoContrato findByPeriodo(
			@Param("avaliacao")Avaliacao avaliacao,
			@Param("contrato")Contrato contrato, 
			@Param("questionario") Questionario questionario,
			@Param("periodoNome") String periodoNome);

	@Query("SELECT ep FROM AvaliacaoContrato ep WHERE ep.avaliacaoContratoPeriodo like :periodoNome%")
	public List<AvaliacaoContrato> findByPeriodoNome(@Param("periodoNome")String periodoNome);

	@Query("SELECT ep FROM AvaliacaoContrato ep WHERE ep.avaliacaoContratoPeriodo like :periodoNome%")
	public List<AvaliacaoContrato> findByPeriodoNome(@Param("periodoNome")String periodoNome,Pageable pageable);

	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.questionario q WHERE q.questionarioNome like :questionarioNome%")
	public List<AvaliacaoContrato> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome);

	@Query("SELECT ep FROM AvaliacaoContrato ep INNER JOIN ep.questionario q WHERE q.questionarioNome like :questionarioNome%")
	public List<AvaliacaoContrato> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome,Pageable pageable);
	
	@Query("SELECT ac FROM AvaliacaoContrato ac WHERE ac.avaliacaoContratoPK = :avaliacaoContratoPK")
	public AvaliacaoContrato findByAvaliacaocontratoPK(@Param("avaliacaoContratoPK") long avaliacaoContratoPK);


}