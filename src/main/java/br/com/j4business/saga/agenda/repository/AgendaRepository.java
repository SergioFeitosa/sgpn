package br.com.j4business.saga.agenda.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.agenda.model.Agenda;


@Repository("agendaRepository")

public interface AgendaRepository extends JpaRepository<Agenda, Long>{

	@Query("SELECT p FROM Agenda p WHERE p.agendaDescricao like :agendaDescricao%")
	public List<Agenda> findByAgendaDescricao(@Param("agendaDescricao")String agendaDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Agenda p WHERE p.agendaNome like :agendaNome%")
	public List<Agenda> findByAgendaNome(@Param("agendaNome")String agendaNome,Pageable pageable);

	@Query("SELECT p FROM Agenda p WHERE p.agendaDescricao like :agendaDescricao%")
	public List<Agenda> findByAgendaDescricao(@Param("agendaDescricao")String agendaDescricao);
	
	@Query("SELECT p FROM Agenda p WHERE p.agendaNome like :agendaNome%")
	public List<Agenda> findByAgendaNome(@Param("agendaNome")String agendaNome);

}