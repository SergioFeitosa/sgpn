package br.com.j4business.saga.colaborador.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.colaborador.model.Colaborador;


@Repository("colaboradorRepository")

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{

	@Query("SELECT p FROM Colaborador p WHERE p.colaboradorApelido like :colaboradorApelido%")
	public List<Colaborador> findByColaboradorApelido(@Param("colaboradorApelido")String colaboradorApelido);

	@Query("SELECT p FROM Colaborador p WHERE p.colaboradorApelido like :colaboradorApelido%")
	public List<Colaborador> findByColaboradorApelido(@Param("colaboradorApelido")String colaboradorApelido,Pageable pageable);

	@Query("SELECT p FROM Colaborador p WHERE p.pessoaNome like :colaboradorNome%")
	public List<Colaborador> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome);

	@Query("SELECT p FROM Colaborador p WHERE p.pessoaNome like :colaboradorNome%")
	public List<Colaborador> findByColaboradorNome(@Param("colaboradorNome")String colaboradorNome,Pageable pageable);

}