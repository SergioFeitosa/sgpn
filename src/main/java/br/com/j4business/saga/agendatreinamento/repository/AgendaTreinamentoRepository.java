package br.com.j4business.saga.agendatreinamento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamento;

@Repository("agendaTreinamentoRepository")
public interface AgendaTreinamentoRepository extends PagingAndSortingRepository<AgendaTreinamento, Long>{

/*	 @Query("SELECT ea FROM AgendaTreinamento ea where ea.treinamento.treinamentoPK = :id") 
	    List<AgendaTreinamento> findByTreinamentoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.treinamento p WHERE p = :treinamento")
	public List<AgendaTreinamento> findByTreinamento(@Param("treinamento")Treinamento treinamento);

	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.treinamento p INNER JOIN ep.agenda e WHERE p = :treinamento AND s = :agenda")
	public AgendaTreinamento findByAgendaAndTreinamento( @Param("agenda") Agenda agenda, @Param("treinamento")Treinamento treinamento);
	
	@Query("SELECT ep FROM AgendaTreinamento ep")
	public List<AgendaTreinamento> findAgendaTreinamentoAll(Pageable pageable);

	@Query("SELECT ep FROM AgendaTreinamento ep")
	public List<AgendaTreinamento> findAgendaTreinamentoAll();

	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.agenda e WHERE e.agendaPK = :agendaPK")
	public List<AgendaTreinamento> findByAgendaPK(@Param("agendaPK")long agendaPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoPK = :treinamentoPK")
	public List<AgendaTreinamento> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoPK = :treinamentoPK")
	public List<AgendaTreinamento> findByTreinamentoPK(@Param("treinamentoPK")long treinamentoPK);
	
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaTreinamento> findByAgendaNome(@Param("agendaNome")String agendaNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoNome like :treinamentoNome%")
	public List<AgendaTreinamento> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaTreinamento> findByAgendaNome(@Param("agendaNome")String agendaNome);
	
	@Query("SELECT ep FROM AgendaTreinamento ep INNER JOIN ep.treinamento p WHERE p.treinamentoNome like :treinamentoNome%")
	public List<AgendaTreinamento> findByTreinamentoNome(@Param("treinamentoNome")String treinamentoNome);
	

}