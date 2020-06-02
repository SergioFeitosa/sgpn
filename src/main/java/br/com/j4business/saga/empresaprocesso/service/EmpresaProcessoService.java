package br.com.j4business.saga.empresaprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.empresa.model.Empresa;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcesso;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcessoForm;

@Service
public interface EmpresaProcessoService {
	
	public List<EmpresaProcesso> getEmpresaProcessoAll(Pageable pageable);
	public EmpresaProcesso getEmpresaProcessoByEmpresaProcessoPK(long empresaProcessoPK);
	public EmpresaProcesso save(EmpresaProcessoForm empresaProcessoForm);
	public void delete(Long empresaProcessoPK);
	public void deleteByProcesso(Processo processo);
	public EmpresaProcesso create(EmpresaProcessoForm empresaProcessoForm);
	public EmpresaProcesso getByEmpresaAndProcesso(Empresa empresa, Processo processo);

	public EmpresaProcesso converteEmpresaProcessoForm(EmpresaProcessoForm empresaProcessoForm);
	public EmpresaProcessoForm converteEmpresaProcesso(EmpresaProcesso empresaProcesso);
	public EmpresaProcessoForm converteEmpresaProcessoView(EmpresaProcesso empresaProcesso);

	public EmpresaProcessoForm empresaProcessoParametros(EmpresaProcessoForm empresaProcessoForm);

	public List<EmpresaProcesso> getByEmpresaPK(long empresaPK,Pageable pageable);
	public List<EmpresaProcesso> getByProcessoPK(long processoPK,Pageable pageable);

	public List<EmpresaProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<EmpresaProcesso> getByEmpresaNome(String empresaNome,Pageable pageable);
	public List<EmpresaProcesso> getByProcessoNome(String processoNome);
	public List<EmpresaProcesso> getByEmpresaNome(String empresaNome);
	
}