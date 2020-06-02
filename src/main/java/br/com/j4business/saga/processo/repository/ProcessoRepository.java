package br.com.j4business.saga.processo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;


@Repository("processoRepository")

public interface ProcessoRepository extends JpaRepository<Processo, Long>{

	@Query("SELECT p FROM Processo p WHERE p.processoDescricao like :processoDescricao%")
	public List<Processo> findByProcessoDescricao(@Param("processoDescricao")String processoDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Processo p WHERE p.processoNome like :processoNome%")
	public List<Processo> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);

	@Query("SELECT p FROM Processo p WHERE p.processoDescricao like :processoDescricao%")
	public List<Processo> findByProcessoDescricao(@Param("processoDescricao")String processoDescricao);
	
	@Query("SELECT p FROM Processo p WHERE p.processoNome like :processoNome%")
	public List<Processo> findByProcessoNome(@Param("processoNome")String processoNome);

}