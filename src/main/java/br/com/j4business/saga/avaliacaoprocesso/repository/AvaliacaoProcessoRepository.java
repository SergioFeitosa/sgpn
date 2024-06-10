package br.com.j4business.saga.avaliacaoprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcesso;

@Repository("avaliacaoProcessoRepository")
public interface AvaliacaoProcessoRepository extends JpaRepository<AvaliacaoProcesso, Long>{

/*	 @Query("SELECT ea FROM AvaliacaoProcesso ea where ea.processo.processoPK = :id") 
	    List<AvaliacaoProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.processo p WHERE p = :processo")
	public List<AvaliacaoProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT ap FROM AvaliacaoProcesso ap INNER JOIN ap.processo p INNER JOIN ap.avaliacao a WHERE p = :processo AND a = :avaliacao")
	public AvaliacaoProcesso findByAvaliacaoAndProcesso( @Param("avaliacao") Avaliacao avaliacao, @Param("processo")Processo processo);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep")
	public List<AvaliacaoProcesso> findAvaliacaoProcessoAll(Pageable pageable);

	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoPK = :avaliacaoPK")
	public List<AvaliacaoProcesso> findByAvaliacaoPK(@Param("avaliacaoPK")long avaliacaoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<AvaliacaoProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoProcesso> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<AvaliacaoProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.questionario q WHERE q.questionarioNome like :questionarioNome%")
	public List<AvaliacaoProcesso> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep WHERE ep.avaliacaoProcessoPeriodo like :periodoNome%")
	public List<AvaliacaoProcesso> findByPeriodoNome(@Param("periodoNome")String periodoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.avaliacao e WHERE e.avaliacaoNome like :avaliacaoNome%")
	public List<AvaliacaoProcesso> findByAvaliacaoNome(@Param("avaliacaoNome")String avaliacaoNome);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<AvaliacaoProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep INNER JOIN ep.questionario q WHERE q.questionarioNome like :questionarioNome%")
	public List<AvaliacaoProcesso> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome);
	
	@Query("SELECT ep FROM AvaliacaoProcesso ep WHERE ep.avaliacaoProcessoPeriodo like :periodoNome%")
	public List<AvaliacaoProcesso> findByPeriodoNome(@Param("periodoNome")String periodoNome);
	
	@Query("SELECT ap FROM AvaliacaoProcesso ap INNER JOIN ap.processo p INNER JOIN ap.questionario q WHERE p = :processo AND q = :questionario")
	public List<AvaliacaoProcesso> findByProcessoAndQuestionario( @Param("processo")Processo processo, @Param("questionario") Questionario questionario,Pageable pageable);
	
	@Query("SELECT ap FROM AvaliacaoProcesso ap INNER JOIN ap.processo p INNER JOIN ap.questionario q WHERE p = :processo AND q = :questionario")
	public List<AvaliacaoProcesso> findByProcessoAndQuestionario( @Param("processo")Processo processo, @Param("questionario") Questionario questionario);
	
	@Query("SELECT ap FROM AvaliacaoProcesso ap "
			+ "INNER JOIN ap.avaliacao av "
			+ "INNER JOIN ap.processo p "
			+ "INNER JOIN ap.questionario q "
			+ "WHERE av = :avaliacao AND "
			+ "p = :processo AND "
			+ "q = :questionario AND "
			+ "ap.avaliacaoProcessoPeriodo like :periodoNome% "
			)
	public AvaliacaoProcesso findByPeriodoNome(
			@Param("avaliacao")Avaliacao avaliacao,
			@Param("processo")Processo processo, 
			@Param("questionario") Questionario questionario,
			@Param("periodoNome") String periodoNome);
	

}