package br.com.j4business.saga.estado.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.estado.model.Estado;


@Repository("estadoRepository")

public interface EstadoRepository extends JpaRepository<Estado, Long>{

	@Query("SELECT p FROM Estado p WHERE p.estadoNome like :estadoNome%")
	public List<Estado> findByEstadoNome(@Param("estadoNome")String estadoNome,Pageable pageable);

	@Query("SELECT p FROM Estado p WHERE p.estadoSigla like :estadoSigla%")
	public List<Estado> findByEstadoSigla(@Param("estadoSigla")String estadoSigla,Pageable pageable);
	
	@Query("SELECT p FROM Estado p WHERE p.estadoNome like :estadoNome%")
	public List<Estado> findByEstadoNome(@Param("estadoNome")String estadoNome);

	@Query("SELECT p FROM Estado p WHERE p.estadoSigla like :estadoSigla%")
	public List<Estado> findByEstadoSigla(@Param("estadoSigla")String estadoSigla);
	
}