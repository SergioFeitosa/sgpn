package br.com.j4business.saga.agendaplanejamento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamento;

@Repository("agendaPlanejamentoRepository")
public interface AgendaPlanejamentoRepository extends PagingAndSortingRepository<AgendaPlanejamento, Long>{

/*	 @Query("SELECT ea FROM AgendaPlanejamento ea where ea.planejamento.planejamentoPK = :id") 
	    List<AgendaPlanejamento> findByPlanejamentoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.planejamento p WHERE p = :planejamento")
	public List<AgendaPlanejamento> findByPlanejamento(@Param("planejamento")Planejamento planejamento);

	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.planejamento p INNER JOIN ep.agenda e WHERE p = :planejamento AND s = :agenda")
	public AgendaPlanejamento findByAgendaAndPlanejamento( @Param("agenda") Agenda agenda, @Param("planejamento")Planejamento planejamento);
	
	@Query("SELECT ep FROM AgendaPlanejamento ep")
	public List<AgendaPlanejamento> findAgendaPlanejamentoAll(Pageable pageable);

	@Query("SELECT ep FROM AgendaPlanejamento ep")
	public List<AgendaPlanejamento> findAgendaPlanejamentoAll();

	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.agenda e WHERE e.agendaPK = :agendaPK")
	public List<AgendaPlanejamento> findByAgendaPK(@Param("agendaPK")long agendaPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.planejamento p WHERE p.planejamentoPK = :planejamentoPK")
	public List<AgendaPlanejamento> findByPlanejamentoPK(@Param("planejamentoPK")long planejamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaPlanejamento> findByAgendaNome(@Param("agendaNome")String agendaNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.planejamento p WHERE p.planejamentoNome like :planejamentoNome%")
	public List<AgendaPlanejamento> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaPlanejamento> findByAgendaNome(@Param("agendaNome")String agendaNome);
	
	@Query("SELECT ep FROM AgendaPlanejamento ep INNER JOIN ep.planejamento p WHERE p.planejamentoNome like :planejamentoNome%")
	public List<AgendaPlanejamento> findByPlanejamentoNome(@Param("planejamentoNome")String planejamentoNome);
	

}