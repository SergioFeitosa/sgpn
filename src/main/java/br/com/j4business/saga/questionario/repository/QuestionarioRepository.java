package br.com.j4business.saga.questionario.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.questionario.model.Questionario;


@Repository("questionarioRepository")

public interface QuestionarioRepository extends JpaRepository<Questionario, Long>{

	@Query("SELECT p FROM Questionario p WHERE p.questionarioDescricao like :questionarioDescricao%")
	public List<Questionario> findByQuestionarioDescricao(@Param("questionarioDescricao")String questionarioDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Questionario p WHERE p.questionarioNome like :questionarioNome%")
	public List<Questionario> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome,Pageable pageable);

	@Query("SELECT p FROM Questionario p WHERE p.questionarioDescricao like :questionarioDescricao%")
	public List<Questionario> findByQuestionarioDescricao(@Param("questionarioDescricao")String questionarioDescricao);
	
	@Query("SELECT p FROM Questionario p WHERE p.questionarioNome like :questionarioNome%")
	public List<Questionario> findByQuestionarioNome(@Param("questionarioNome")String questionarioNome);

	@Query("SELECT q FROM Questionario q INNER JOIN q.cenario ce WHERE ce.cenarioNome like :cenarioNome%")
	public List<Questionario> findByCenarioNome(@Param("cenarioNome")String cenarioNome,Pageable pageable);

	@Query("SELECT q FROM Questionario q INNER JOIN q.cenario ce WHERE ce.cenarioNome like :cenarioNome%")
	public List<Questionario> findByCenarioNome(@Param("cenarioNome")String cenarioNome);

}