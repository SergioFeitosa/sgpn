package br.com.j4business.saga.fornecedorcontrato.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContrato;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContratoForm;

@Service
public interface FornecedorContratoService {
	
	public List<FornecedorContrato> getFornecedorContratoAll(Pageable pageable);
	public FornecedorContrato getFornecedorContratoByFornecedorContratoPK(long fornecedorContratoPK);
	public FornecedorContrato save(FornecedorContratoForm fornecedorContratoForm);
	public void delete(Long fornecedorContratoPK);
	public void deleteByContrato(Contrato contrato);
	public FornecedorContrato create(FornecedorContratoForm fornecedorContratoForm);
	public FornecedorContrato getByFornecedorAndContrato(Fornecedor fornecedor, Contrato contrato);

	public FornecedorContrato converteFornecedorContratoForm(FornecedorContratoForm fornecedorContratoForm);
	public FornecedorContratoForm converteFornecedorContrato(FornecedorContrato fornecedorContrato);
	public FornecedorContratoForm converteFornecedorContratoView(FornecedorContrato fornecedorContrato);

	public FornecedorContratoForm fornecedorContratoParametros(FornecedorContratoForm fornecedorContratoForm);

	public List<FornecedorContrato> getByFornecedorPK(long fornecedorPK,Pageable pageable);
	public List<FornecedorContrato> getByFornecedorPK(long fornecedorPK);
	public List<FornecedorContrato> getByContratoPK(long contratoPK,Pageable pageable);
	public List<FornecedorContrato> getByContratoPK(long contratoPK);

	public List<FornecedorContrato> getByContratoNome(String contratoNome,Pageable pageable);
	public List<FornecedorContrato> getByFornecedorNome(String fornecedorNome,Pageable pageable);
	public List<FornecedorContrato> getByContratoNome(String contratoNome);
	public List<FornecedorContrato> getByFornecedorNome(String fornecedorNome);
	
}