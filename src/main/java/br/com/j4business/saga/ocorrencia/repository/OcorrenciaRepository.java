package br.com.j4business.saga.ocorrencia.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.ocorrencia.model.Ocorrencia;


@Repository("ocorrenciaRepository")

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long>{

	@Query("SELECT p FROM Ocorrencia p WHERE p.ocorrenciaDescricao like :ocorrenciaDescricao%")
	public List<Ocorrencia> findByOcorrenciaDescricao(@Param("ocorrenciaDescricao")String ocorrenciaDescricao,Pageable pageable);
	
	@Query("SELECT p FROM Ocorrencia p WHERE p.ocorrenciaNome like :ocorrenciaNome%")
	public List<Ocorrencia> findByOcorrenciaNome(@Param("ocorrenciaNome")String ocorrenciaNome,Pageable pageable);

	@Query("SELECT p FROM Ocorrencia p WHERE p.ocorrenciaDescricao like :ocorrenciaDescricao%")
	public List<Ocorrencia> findByOcorrenciaDescricao(@Param("ocorrenciaDescricao")String ocorrenciaDescricao);
	
	@Query("SELECT p FROM Ocorrencia p WHERE p.ocorrenciaNome like :ocorrenciaNome%")
	public List<Ocorrencia> findByOcorrenciaNome(@Param("ocorrenciaNome")String ocorrenciaNome);

}