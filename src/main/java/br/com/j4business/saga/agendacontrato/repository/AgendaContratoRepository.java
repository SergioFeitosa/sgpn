package br.com.j4business.saga.agendacontrato.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendacontrato.model.AgendaContrato;

@Repository("agendaContratoRepository")
public interface AgendaContratoRepository extends JpaRepository<AgendaContrato, Long> {

/*	 @Query("SELECT ea FROM AgendaContrato ea where ea.contrato.contratoPK = :id") 
	    List<AgendaContrato> findByContratoPK(@Param("id") Long id);
*/	 

	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.contrato p WHERE p = :contrato")
	public List<AgendaContrato> findByContrato(@Param("contrato")Contrato contrato);

	@Query("SELECT ac FROM AgendaContrato ac INNER JOIN ac.contrato c INNER JOIN ac.agenda a WHERE c = :contrato AND a = :agenda")
	public AgendaContrato findByAgendaAndContrato( @Param("agenda") Agenda agenda, @Param("contrato")Contrato contrato);
	
	@Query("SELECT ep FROM AgendaContrato ep")
	public List<AgendaContrato> findAgendaContratoAll(Pageable pageable);

	@Query("SELECT ep FROM AgendaContrato ep")
	public List<AgendaContrato> findAgendaContratoAll();

	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.agenda e WHERE e.agendaPK = :agendaPK")
	public List<AgendaContrato> findByAgendaPK(@Param("agendaPK")long agendaPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.contrato p WHERE p.contratoPK = :contratoPK")
	public List<AgendaContrato> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaContrato> findByAgendaNome(@Param("agendaNome")String agendaNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<AgendaContrato> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.agenda e WHERE e.agendaNome like :agendaNome%")
	public List<AgendaContrato> findByAgendaNome(@Param("agendaNome")String agendaNome);
	
	@Query("SELECT ep FROM AgendaContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<AgendaContrato> findByContratoNome(@Param("contratoNome")String contratoNome);
	
	@Query("SELECT ac FROM AgendaContrato ac WHERE ac.agendaContratoPK = :agendaContratoPK")
	public AgendaContrato findByAgendaContratoPK(@Param("agendaContratoPK") long agendaContratoPK);


}