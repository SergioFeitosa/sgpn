package br.com.j4business.saga.fornecedor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.model.FornecedorForm;

@Service
public interface FornecedorService {
	
	public List<Fornecedor> getFornecedorAll();
	public Page<Fornecedor> getFornecedorAll(Pageable pageable);
	public Fornecedor getFornecedorByFornecedorPK(long fornecedorPK);
	public Fornecedor create(FornecedorForm fornecedorForm);
	public Fornecedor save(FornecedorForm fornecedorForm);
	public void delete(Long fornecedorPK);
	
	public Fornecedor converteFornecedorForm(FornecedorForm fornecedorForm);
	public FornecedorForm converteFornecedor(Fornecedor fornecedor);
	public FornecedorForm converteFornecedorView(Fornecedor fornecedor);

	public FornecedorForm fornecedorParametros(FornecedorForm fornecedorForm);

	public List<Fornecedor> getByFornecedorNome(String fornecedorNome,Pageable pageable);
	public List<Fornecedor> getByFornecedorNomeFantasia(String fornecedorNomeFantasia,Pageable pageable);
	public List<Fornecedor> getByFornecedorNome(String fornecedorNome);
	public List<Fornecedor> getByFornecedorNomeFantasia(String fornecedorNomeFantasia);

}