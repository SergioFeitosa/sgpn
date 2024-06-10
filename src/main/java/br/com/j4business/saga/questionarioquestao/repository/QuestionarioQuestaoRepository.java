package br.com.j4business.saga.questionarioquestao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestao;

@Repository("questionarioQuestaoRepository")
public interface QuestionarioQuestaoRepository extends JpaRepository<QuestionarioQuestao, Long>{

/*	 @Query("SELECT ea FROM QuestionarioQuestao ea where ea.questao.questaoPK = :id") 
	    List<QuestionarioQuestao> findByQuestaoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pa FROM QuestionarioQuestao pa INNER JOIN pa.questao p WHERE p = :questao")
	public List<QuestionarioQuestao> findByQuestao(@Param("questao")Questao questao);

	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questao p INNER JOIN ps.questionario s WHERE p = :questao AND s = :questionario")
	public QuestionarioQuestao findByQuestionarioAndQuestao( @Param("questionario") Questionario questionario, @Param("questao")Questao questao);
	
	@Query("SELECT pa FROM QuestionarioQuestao pa")
	public List<QuestionarioQuestao> findQuestionarioQuestaoAll(Pageable pageable);

	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questionario s WHERE s.questionarioNome like :questionarioNome%")
	public List<QuestionarioQuestao> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome,Pageable pageable);
	
	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questao p WHERE p.questaoNome like :questaoNome%")
	public List<QuestionarioQuestao> findByQuestaoNome(@Param("questaoNome")String questaoNome,Pageable pageable);
	
	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questionario s WHERE s.questionarioNome like :questionarioNome%")
	public List<QuestionarioQuestao> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome);
	
	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questao p WHERE p.questaoNome like :questaoNome%")
	public List<QuestionarioQuestao> findByQuestaoNome(@Param("questaoNome")String questaoNome);
	
	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questionario s WHERE s.questionarioPK = :questionarioPK")
	public List<QuestionarioQuestao> findByQuestionarioPK(@Param("questionarioPK")long questionarioPK,Pageable pageable);
	
	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questionario s WHERE s.questionarioPK = :questionarioPK")
	public List<QuestionarioQuestao> findByQuestionarioPK(@Param("questionarioPK")long questionarioPK);
	
	@Query("SELECT ps FROM QuestionarioQuestao ps INNER JOIN ps.questao p WHERE p.questaoPK = :questaoPK")
	public List<QuestionarioQuestao> findByQuestaoPK(@Param("questaoPK")long questaoPK,Pageable pageable);
	

}