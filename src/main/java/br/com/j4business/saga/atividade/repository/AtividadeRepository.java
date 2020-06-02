package br.com.j4business.saga.atividade.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.atividade.model.Atividade;


@Repository("atividadeRepository")

public interface AtividadeRepository extends JpaRepository<Atividade, Long>{

	@Query("SELECT p FROM Atividade p WHERE p.atividadeNome like :atividadeNome%")
	public List<Atividade> findByAtividadeNome(@Param("atividadeNome")String atividadeNome,Pageable pageable);

	@Query("SELECT p FROM Atividade p WHERE p.atividadeNome like :atividadeNome%")
	public List<Atividade> findByAtividadeNome(@Param("atividadeNome")String atividadeNome);

	@Query("SELECT p FROM Atividade p WHERE p.atividadeDescricao like :atividadeDescricao%")
	public List<Atividade> findByAtividadeDescricao(@Param("atividadeDescricao")String atividadeDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Atividade p WHERE p.atividadeDescricao like :atividadeDescricao%")
	public List<Atividade> findByAtividadeDescricao(@Param("atividadeDescricao")String atividadeDescricao);
	
}