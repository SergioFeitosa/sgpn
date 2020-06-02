package br.com.j4business.saga.atendimento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.atendimento.model.Atendimento;


@Repository("atendimentoRepository")

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long>{

	@Query("SELECT p FROM Atendimento p WHERE p.atendimentoDescricao like :atendimentoDescricao%")
	public List<Atendimento> findByAtendimentoDescricao(@Param("atendimentoDescricao")String atendimentoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Atendimento p WHERE p.atendimentoNome like :atendimentoNome%")
	public List<Atendimento> findByAtendimentoNome(@Param("atendimentoNome")String atendimentoNome,Pageable pageable);

	@Query("SELECT p FROM Atendimento p WHERE p.atendimentoDescricao like :atendimentoDescricao%")
	public List<Atendimento> findByAtendimentoDescricao(@Param("atendimentoDescricao")String atendimentoDescricao);
	
	@Query("SELECT p FROM Atendimento p WHERE p.atendimentoNome like :atendimentoNome%")
	public List<Atendimento> findByAtendimentoNome(@Param("atendimentoNome")String atendimentoNome);

}