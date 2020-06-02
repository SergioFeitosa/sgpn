package br.com.j4business.saga.contrato.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.model.ContratoForm;

@Service
public interface ContratoService {
	
	public List<Contrato> getContratoAll();
	public Page<Contrato> getContratoAll(Pageable pageable);
	public Contrato getContratoByContratoPK(long contratoPK);
	public Contrato create(ContratoForm contratoForm);
	public Contrato save(ContratoForm contratoForm);
	public void delete(Long contratoPK);
	
	public Contrato converteContratoForm(ContratoForm contratoForm);
	public ContratoForm converteContrato(Contrato contrato);
	public ContratoForm converteContratoView(Contrato contrato);

	public ContratoForm contratoParametros(ContratoForm contratoForm);

	public List<Contrato> getByContratoNome(String contratoNome,Pageable pageable);
	public List<Contrato> getByContratoDescricao(String contratoDescricao,Pageable pageable);
	public List<Contrato> getByContratoNome(String contratoNome);
	public List<Contrato> getByContratoDescricao(String contratoDescricao);

}