package br.com.j4business.saga.empresa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.empresa.model.Empresa;
import br.com.j4business.saga.empresa.model.EmpresaForm;

@Service
public interface EmpresaService {
	
	public List<Empresa> getEmpresaAll();
	public Page<Empresa> getEmpresaAll(Pageable pageable);
	public Empresa getEmpresaByEmpresaPK(long empresaPK);
	public Empresa create(EmpresaForm empresaForm);
	public Empresa save(EmpresaForm empresaForm);
	public void delete(Long empresaPK);
	
	public Empresa converteEmpresaForm(EmpresaForm empresaForm);
	public EmpresaForm converteEmpresa(Empresa empresa);
	public EmpresaForm converteEmpresaView(Empresa empresa);

	public EmpresaForm empresaParametros(EmpresaForm empresaForm);

	public List<Empresa> getByEmpresaNome(String empresaNome,Pageable pageable);
	public List<Empresa> getByEmpresaNomeFantasia(String empresaNomeFantasia,Pageable pageable);
	public List<Empresa> getByEmpresaNome(String empresaNome);
	public List<Empresa> getByEmpresaNomeFantasia(String empresaNomeFantasia);

}