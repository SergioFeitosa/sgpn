package br.com.j4business.saga.evento.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.evento.model.Evento;


@Repository("eventoRepository")

public interface EventoRepository extends JpaRepository<Evento, Long>{

	@Query("SELECT p FROM Evento p WHERE p.eventoDescricao like :eventoDescricao%")
	public List<Evento> findByEventoDescricao(@Param("eventoDescricao")String eventoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Evento p WHERE p.eventoNome like :eventoNome%")
	public List<Evento> findByEventoNome(@Param("eventoNome")String eventoNome,Pageable pageable);

	@Query("SELECT p FROM Evento p WHERE p.eventoDescricao like :eventoDescricao%")
	public List<Evento> findByEventoDescricao(@Param("eventoDescricao")String eventoDescricao);
	
	@Query("SELECT p FROM Evento p WHERE p.eventoNome like :eventoNome%")
	public List<Evento> findByEventoNome(@Param("eventoNome")String eventoNome);

}