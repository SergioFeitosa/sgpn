package br.com.j4business.saga.contratotexto.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contratotexto.model.ContratoTexto;
import br.com.j4business.saga.contratotexto.model.ContratoTextoForm;

@Service
public interface ContratoTextoService {
	
	public List<ContratoTexto> getContratoTextoAll(Pageable pageable);
	public ContratoTexto getContratoTextoByContratoTextoPK(long contratoTextoPK);
	public ContratoTexto save(ContratoTextoForm contratoTextoForm);
	public void delete(Long contratoTextoPK);
	public void deleteByTexto(Texto texto);
	public ContratoTexto create(ContratoTextoForm contratoTextoForm);
	public ContratoTexto getByContratoAndTexto(Contrato contrato, Texto texto);

	public ContratoTexto converteContratoTextoForm(ContratoTextoForm contratoTextoForm);
	public ContratoTextoForm converteContratoTexto(ContratoTexto contratoTexto);
	public ContratoTextoForm converteContratoTextoView(ContratoTexto contratoTexto);

	public ContratoTextoForm contratoTextoParametros(ContratoTextoForm contratoTextoForm);

	public List<ContratoTexto> getByContratoPK(long contratoPK);
	public List<ContratoTexto> getContratoTextoAtivoByContratoPK(long contratoPK);
	public List<ContratoTexto> getByContratoPK(long contratoPK,Pageable pageable);
	public List<ContratoTexto> getByTextoPK(long textoPK,Pageable pageable);

	public List<ContratoTexto> getByTextoNome(String textoNome,Pageable pageable);
	public List<ContratoTexto> getByContratoNome(String contratoNome,Pageable pageable);
	public List<ContratoTexto> getByTextoNome(String textoNome);
	public List<ContratoTexto> getByContratoNome(String contratoNome);
	
}