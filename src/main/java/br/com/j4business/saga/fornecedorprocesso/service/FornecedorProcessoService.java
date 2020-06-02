package br.com.j4business.saga.fornecedorprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcesso;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcessoForm;

@Service
public interface FornecedorProcessoService {
	
	public List<FornecedorProcesso> getFornecedorProcessoAll(Pageable pageable);
	public FornecedorProcesso getFornecedorProcessoByFornecedorProcessoPK(long fornecedorProcessoPK);
	public FornecedorProcesso save(FornecedorProcessoForm fornecedorProcessoForm);
	public void delete(Long fornecedorProcessoPK);
	public void deleteByProcesso(Processo processo);
	public FornecedorProcesso create(FornecedorProcessoForm fornecedorProcessoForm);
	public FornecedorProcesso getByFornecedorAndProcesso(Fornecedor fornecedor, Processo processo);

	public FornecedorProcesso converteFornecedorProcessoForm(FornecedorProcessoForm fornecedorProcessoForm);
	public FornecedorProcessoForm converteFornecedorProcesso(FornecedorProcesso fornecedorProcesso);
	public FornecedorProcessoForm converteFornecedorProcessoView(FornecedorProcesso fornecedorProcesso);

	public FornecedorProcessoForm fornecedorProcessoParametros(FornecedorProcessoForm fornecedorProcessoForm);

	public List<FornecedorProcesso> getByFornecedorPK(long fornecedorPK,Pageable pageable);
	public List<FornecedorProcesso> getByProcessoPK(long processoPK,Pageable pageable);

	public List<FornecedorProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<FornecedorProcesso> getByFornecedorNome(String fornecedorNome,Pageable pageable);
	public List<FornecedorProcesso> getByProcessoNome(String processoNome);
	public List<FornecedorProcesso> getByFornecedorNome(String fornecedorNome);
	
}