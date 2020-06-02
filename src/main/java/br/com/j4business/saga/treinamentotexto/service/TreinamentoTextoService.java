package br.com.j4business.saga.treinamentotexto.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTexto;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTextoForm;

@Service
public interface TreinamentoTextoService {
	
	public List<TreinamentoTexto> getTreinamentoTextoAll(Pageable pageable);
	public TreinamentoTexto getTreinamentoTextoByTreinamentoTextoPK(long treinamentoTextoPK);
	public TreinamentoTexto save(TreinamentoTextoForm treinamentoTextoForm);
	public void delete(Long treinamentoTextoPK);
	public void deleteByTexto(Texto texto);
	public TreinamentoTexto create(TreinamentoTextoForm treinamentoTextoForm);
	public TreinamentoTexto getByTreinamentoAndTexto(Treinamento treinamento, Texto texto);

	public TreinamentoTexto converteTreinamentoTextoForm(TreinamentoTextoForm treinamentoTextoForm);
	public TreinamentoTextoForm converteTreinamentoTexto(TreinamentoTexto treinamentoTexto);
	public TreinamentoTextoForm converteTreinamentoTextoView(TreinamentoTexto treinamentoTexto);

	public TreinamentoTextoForm treinamentoTextoParametros(TreinamentoTextoForm treinamentoTextoForm);

	public List<TreinamentoTexto> getByTreinamentoPK(long treinamentoPK);
	public List<TreinamentoTexto> getTreinamentoTextoAtivoByTreinamentoPK(long treinamentoPK);
	public List<TreinamentoTexto> getByTreinamentoPK(long treinamentoPK,Pageable pageable);
	public List<TreinamentoTexto> getByTextoPK(long textoPK,Pageable pageable);

	public List<TreinamentoTexto> getByTextoNome(String textoNome,Pageable pageable);
	public List<TreinamentoTexto> getByTreinamentoNome(String treinamentoNome,Pageable pageable);
	public List<TreinamentoTexto> getByTextoNome(String textoNome);
	public List<TreinamentoTexto> getByTreinamentoNome(String treinamentoNome);
	
}