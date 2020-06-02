package br.com.j4business.saga.fornecedorcontrato.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContrato;

@Repository("fornecedorContratoRepository")
public interface FornecedorContratoRepository extends PagingAndSortingRepository<FornecedorContrato, Long>{

/*	 @Query("SELECT ea FROM FornecedorContrato ea where ea.contrato.contratoPK = :id") 
	    List<FornecedorContrato> findByContratoPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.contrato p WHERE p = :contrato")
	public List<FornecedorContrato> findByContrato(@Param("contrato")Contrato contrato);

	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.contrato p INNER JOIN ep.fornecedor e WHERE p = :contrato AND s = :fornecedor")
	public FornecedorContrato findByFornecedorAndContrato( @Param("fornecedor") Fornecedor fornecedor, @Param("contrato")Contrato contrato);
	
	@Query("SELECT ep FROM FornecedorContrato ep")
	public List<FornecedorContrato> findFornecedorContratoAll(Pageable pageable);

	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.fornecedor e WHERE e.pessoaPK = :fornecedorPK")
	public List<FornecedorContrato> findByFornecedorPK(@Param("fornecedorPK")long fornecedorPK,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.contrato p WHERE p.contratoPK = :contratoPK")
	public List<FornecedorContrato> findByContratoPK(@Param("contratoPK")long contratoPK,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.fornecedor e WHERE e.pessoaPK = :fornecedorPK")
	public List<FornecedorContrato> findByFornecedorPK(@Param("fornecedorPK")long fornecedorPK);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.contrato p WHERE p.contratoPK = :contratoPK")
	public List<FornecedorContrato> findByContratoPK(@Param("contratoPK")long contratoPK);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.fornecedor e WHERE e.pessoaNome like :fornecedorNome%")
	public List<FornecedorContrato> findByFornecedorNome(@Param("fornecedorNome")String fornecedorNome,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<FornecedorContrato> findByContratoNome(@Param("contratoNome")String contratoNome,Pageable pageable);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.fornecedor e WHERE e.pessoaNome like :fornecedorNome%")
	public List<FornecedorContrato> findByFornecedorNome(@Param("fornecedorNome")String fornecedorNome);
	
	@Query("SELECT ep FROM FornecedorContrato ep INNER JOIN ep.contrato p WHERE p.contratoNome like :contratoNome%")
	public List<FornecedorContrato> findByContratoNome(@Param("contratoNome")String contratoNome);
	

}