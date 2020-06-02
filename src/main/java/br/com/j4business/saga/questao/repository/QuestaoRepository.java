package br.com.j4business.saga.questao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.questao.model.Questao;


@Repository("questaoRepository")

public interface QuestaoRepository extends JpaRepository<Questao, Long>{

	@Query("SELECT p FROM Questao p WHERE p.questaoDescricao like :questaoDescricao%")
	public List<Questao> findByQuestaoDescricao(@Param("questaoDescricao")String questaoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Questao p WHERE p.questaoNome like :questaoNome%")
	public List<Questao> findByQuestaoNome(@Param("questaoNome")String questaoNome,Pageable pageable);

	@Query("SELECT p FROM Questao p WHERE p.questaoDescricao like :questaoDescricao%")
	public List<Questao> findByQuestaoDescricao(@Param("questaoDescricao")String questaoDescricao);
	
	@Query("SELECT p FROM Questao p WHERE p.questaoNome like :questaoNome%")
	public List<Questao> findByQuestaoNome(@Param("questaoNome")String questaoNome);

}