package br.com.j4business.saga.fornecedorprocesso.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcesso;

@Repository("fornecedorProcessoRepository")
public interface FornecedorProcessoRepository extends JpaRepository<FornecedorProcesso, Long>{

/*	 @Query("SELECT ea FROM FornecedorProcesso ea where ea.processo.processoPK = :id") 
	    List<FornecedorProcesso> findByProcessoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.processo p WHERE p = :processo")
	public List<FornecedorProcesso> findByProcesso(@Param("processo")Processo processo);

	@Query("SELECT fp FROM FornecedorProcesso fp INNER JOIN fp.processo p INNER JOIN fp.fornecedor f WHERE p = :processo AND f = :fornecedor")
	public FornecedorProcesso findByFornecedorAndProcesso( @Param("fornecedor") Fornecedor fornecedor, @Param("processo")Processo processo);
	
	@Query("SELECT ep FROM FornecedorProcesso ep")
	public List<FornecedorProcesso> findFornecedorProcessoAll(Pageable pageable);

	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.fornecedor e WHERE e.pessoaPK = :fornecedorPK")
	public List<FornecedorProcesso> findByFornecedorPK(@Param("fornecedorPK")long fornecedorPK,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.processo p WHERE p.processoPK = :processoPK")
	public List<FornecedorProcesso> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.fornecedor e WHERE e.pessoaNome like :fornecedorNome%")
	public List<FornecedorProcesso> findByFornecedorNome(@Param("fornecedorNome")String fornecedorNome,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<FornecedorProcesso> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.fornecedor e WHERE e.pessoaNome like :fornecedorNome%")
	public List<FornecedorProcesso> findByFornecedorNome(@Param("fornecedorNome")String fornecedorNome);
	
	@Query("SELECT ep FROM FornecedorProcesso ep INNER JOIN ep.processo p WHERE p.processoNome like :processoNome%")
	public List<FornecedorProcesso> findByProcessoNome(@Param("processoNome")String processoNome);
	

}