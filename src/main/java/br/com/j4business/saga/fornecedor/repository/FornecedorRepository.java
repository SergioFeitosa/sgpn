package br.com.j4business.saga.fornecedor.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.fornecedor.model.Fornecedor;


@Repository("fornecedorRepository")

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

	@Query("SELECT p FROM Fornecedor p WHERE p.fornecedorNomeFantasia like :fornecedorNomeFantasia%")
	public List<Fornecedor> findByFornecedorNomeFantasia(@Param("fornecedorNomeFantasia")String fornecedorNomeFantasia,Pageable pageable);
	
	@Query("SELECT p FROM Fornecedor p WHERE p.pessoaNome like :fornecedorNome%")
	public List<Fornecedor> findByFornecedorNome(@Param("fornecedorNome")String fornecedorNome,Pageable pageable);

	@Query("SELECT p FROM Fornecedor p WHERE p.fornecedorNomeFantasia like :fornecedorNomeFantasia%")
	public List<Fornecedor> findByFornecedorNomeFantasia(@Param("fornecedorNomeFantasia")String fornecedorNomeFantasia);
	
	@Query("SELECT p FROM Fornecedor p WHERE p.pessoaNome like :fornecedorNome%")
	public List<Fornecedor> findByFornecedorNome(@Param("fornecedorNome")String fornecedorNome);

}