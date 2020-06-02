package br.com.j4business.saga.processotexto.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processotexto.model.ProcessoTexto;
import br.com.j4business.saga.processotexto.model.ProcessoTextoForm;

@Service
public interface ProcessoTextoService {
	
	public List<ProcessoTexto> getProcessoTextoAll(Pageable pageable);
	public ProcessoTexto getProcessoTextoByProcessoTextoPK(long processoTextoPK);
	public ProcessoTexto save(ProcessoTextoForm processoTextoForm);
	public void delete(Long processoTextoPK);
	public void deleteByTexto(Texto texto);
	public ProcessoTexto create(ProcessoTextoForm processoTextoForm);
	public ProcessoTexto getByProcessoAndTexto(Processo processo, Texto texto);

	public ProcessoTexto converteProcessoTextoForm(ProcessoTextoForm processoTextoForm);
	public ProcessoTextoForm converteProcessoTexto(ProcessoTexto processoTexto);
	public ProcessoTextoForm converteProcessoTextoView(ProcessoTexto processoTexto);

	public ProcessoTextoForm processoTextoParametros(ProcessoTextoForm processoTextoForm);

	public List<ProcessoTexto> getByProcessoPK(long processoPK);
	public List<ProcessoTexto> getProcessoTextoAtivoByProcessoPK(long processoPK);
	public List<ProcessoTexto> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoTexto> getByTextoPK(long textoPK,Pageable pageable);

	public List<ProcessoTexto> getByTextoNome(String textoNome,Pageable pageable);
	public List<ProcessoTexto> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoTexto> getByTextoNome(String textoNome);
	public List<ProcessoTexto> getByProcessoNome(String processoNome);
	
}