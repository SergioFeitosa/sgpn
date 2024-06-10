package br.com.j4business.saga.empresaprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.empresa.model.Empresa;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcesso;

@Repository("empresaProcessoRepository")
public interface EmpresaProcessoRepository extends JpaRepository<EmpresaProcesso, Long>{

/*	 @Query("SELECT ea FROM EmpresaProcesso ea where ea.processo.processoPK = :id") 
	    List<EmpresaProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.processo p WHERE p = :processo")
	public List<EmpresaProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.processo p INNER JOIN ep.empresa e WHERE p = :processo AND e = :empresa")
	public EmpresaProcesso findByEmpresaAndProcesso( @Param("empresa") Empresa empresa, @Param("processo")Processo processo);
	
	@Query("SELECT ep FROM EmpresaProcesso ep")
	public List<EmpresaProcesso> findEmpresaProcessoAll(Pageable pageable);

	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.empresa e WHERE e.pessoaPK = :empresaPK")
	public List<EmpresaProcesso> findByEmpresaPK(@Param("empresaPK")long empresaPK,Pageable pageable);
	
	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<EmpresaProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.empresa e WHERE e.pessoaNome like :empresaNome%")
	public List<EmpresaProcesso> findByEmpresaNome(@Param("empresaNome")String empresaNome,Pageable pageable);
	
	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<EmpresaProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.empresa e WHERE e.pessoaNome like :empresaNome%")
	public List<EmpresaProcesso> findByEmpresaNome(@Param("empresaNome")String empresaNome);
	
	@Query("SELECT ep FROM EmpresaProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<EmpresaProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	

}