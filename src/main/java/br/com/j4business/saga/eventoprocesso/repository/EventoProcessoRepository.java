package br.com.j4business.saga.eventoprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;

@Repository("eventoProcessoRepository")
public interface EventoProcessoRepository extends PagingAndSortingRepository<EventoProcesso, Long>{

/*	 @Query("SELECT ea FROM EventoProcesso ea where ea.processo.processoPK = :id") 
	    List<EventoProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT pa FROM EventoProcesso pa INNER JOIN pa.processo p WHERE p = :processo")
	public List<EventoProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.processo p INNER JOIN ps.evento s WHERE p = :processo AND s = :evento")
	public EventoProcesso findByEventoAndProcesso( @Param("evento") Evento evento, @Param("processo")Processo processo);
	
	@Query("SELECT pa FROM EventoProcesso pa")
	public List<EventoProcesso> findEventoProcessoAll(Pageable pageable);

	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.evento s WHERE s.eventoNome like :eventoNome%")
	public List<EventoProcesso> findByEventoNome(@Param("eventoNome")String eventoNome,Pageable pageable);
	
	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.processo p WHERE p.processoNome like :processoNome%")
	public List<EventoProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.evento s WHERE s.eventoNome like :eventoNome%")
	public List<EventoProcesso> findByEventoNome(@Param("eventoNome")String eventoNome);
	
	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.processo p WHERE p.processoNome like :processoNome%")
	public List<EventoProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.evento s WHERE s.eventoPK = :eventoPK")
	public List<EventoProcesso> findByEventoPK(@Param("eventoPK")long eventoPK,Pageable pageable);
	
	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.evento s WHERE s.eventoPK = :eventoPK")
	public List<EventoProcesso> findByEventoPK(@Param("eventoPK")long eventoPK);
	
	@Query("SELECT ps FROM EventoProcesso ps INNER JOIN ps.processo p WHERE p.processoPK = :processoPK")
	public List<EventoProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	

}