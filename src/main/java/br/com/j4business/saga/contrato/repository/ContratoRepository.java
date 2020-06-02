package br.com.j4business.saga.contrato.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.contrato.model.Contrato;


@Repository("contratoRepository")

public interface ContratoRepository extends JpaRepository<Contrato, Long>{

	@Query("SELECT p FROM Contrato p WHERE p.contratoDescricao like :contratoDescricao%")
	public List<Contrato> findByContratoDescricao(@Param("contratoDescricao")String contratoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Contrato p WHERE p.contratoNome like :contratoNome%")
	public List<Contrato> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);

	@Query("SELECT p FROM Contrato p WHERE p.contratoDescricao like :contratoDescricao%")
	public List<Contrato> findByContratoDescricao(@Param("contratoDescricao")String contratoDescricao);
	
	@Query("SELECT p FROM Contrato p WHERE p.contratoNome like :contratoNome%")
	public List<Contrato> findByContratoNome(@Param("contratoNome")String contratoNome);

}