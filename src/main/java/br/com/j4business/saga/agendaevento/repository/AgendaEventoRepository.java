package br.com.j4business.saga.agendaevento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;

@Repository("agendaEventoRepository")
public interface AgendaEventoRepository extends JpaRepository<AgendaEvento, Long>{

/*	 @Query("SELECT ea FROM AgendaEvento ea where ea.evento.eventoPK = :id") 
	    List<AgendaEvento> findByEventoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.evento p WHERE p = :evento")
	public List<AgendaEvento> findByEvento(@Param("evento")Evento evento);

	@Query("SELECT ae FROM AgendaEvento ae INNER JOIN ae.evento e INNER JOIN ae.agenda a WHERE e = :evento AND a = :agenda")
	public AgendaEvento findByAgendaAndEvento( @Param("agenda") Agenda agenda, @Param("evento")Evento evento);
	
	@Query("SELECT ep FROM AgendaEvento ep")
	public List<AgendaEvento> findAgendaEventoAll(Pageable pageable);

	@Query("SELECT ep FROM AgendaEvento ep")
	public List<AgendaEvento> findAgendaEventoAll();

	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.agenda e WHERE e.agendaPK = :agendaPK")
	public List<AgendaEvento> findByAgendaPK(@Param("agendaPK")long agendaPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.evento p WHERE p.eventoPK = :eventoPK")
	public List<AgendaEvento> findByEventoPK(@Param("eventoPK")long eventoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaEvento> findByAgendaNome(@Param("agendaNome")String agendaNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.evento p WHERE p.eventoNome like :eventoNome%")
	public List<AgendaEvento> findByEventoNome(@Param("eventoNome")String eventoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaEvento> findByAgendaNome(@Param("agendaNome")String agendaNome);
	
	@Query("SELECT ep FROM AgendaEvento ep INNER JOIN ep.evento p WHERE p.eventoNome like :eventoNome%")
	public List<AgendaEvento> findByEventoNome(@Param("eventoNome")String eventoNome);
	
	@Query("SELECT ae FROM AgendaEvento ae WHERE ae.agendaEventoPK = :agendaEventoPK")
	public AgendaEvento findByAgendaEventoPK(@Param("agendaEventoPK") long agendaEventoPK);


}