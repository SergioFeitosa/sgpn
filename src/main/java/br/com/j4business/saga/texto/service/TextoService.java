package br.com.j4business.saga.texto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.texto.model.TextoForm;

@Service
public interface TextoService {
	
	public List<Texto> getTextoAll();
	public Page<Texto> getTextoAll(Pageable pageable);
	public Texto getTextoByTextoPK(long textoPK);
	public Texto create(TextoForm textoForm);
	public Texto save(TextoForm textoForm);
	public void delete(Long textoPK);
	
	public Texto converteTextoForm(TextoForm textoForm);
	public TextoForm converteTexto(Texto texto);
	public TextoForm converteTextoView(Texto texto);

	public TextoForm textoParametros(TextoForm textoForm);

	public List<Texto> getByTextoNome(String textoNome,Pageable pageable);
	public List<Texto> getByTextoDescricao(String textoDescricao,Pageable pageable);
	public List<Texto> getByTextoNome(String textoNome);
	public List<Texto> getByTextoDescricao(String textoDescricao);

}