package br.com.j4business.saga.elemento.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.model.ElementoForm;

@Service
public interface ElementoService {
	
	public List<Elemento> getElementoAll();
	public Page<Elemento> getElementoAll(Pageable pageable);
	public Elemento getElementoByElementoPK(long elementoPK);
	public Elemento create(ElementoForm elementoForm);
	public Elemento save(ElementoForm elementoForm);
	public void delete(Long elementoPK);
	
	public Elemento converteElementoForm(ElementoForm elementoForm);
	public ElementoForm converteElemento(Elemento elemento);
	public ElementoForm converteElementoView(Elemento elemento);

	public ElementoForm elementoParametros(ElementoForm elementoForm);

	public Elemento getByElementoNomeCompleto(String elementoNome);
	public Elemento getByElementoDescricaoCompleto(String elementoDescricao);
	public List<Elemento> getByElementoNome(String elementoNome,Pageable pageable);
	public List<Elemento> getByElementoDescricao(String elementoDescricao,Pageable pageable);
	public List<Elemento> getByElementoNome(String elementoNome);
	public List<Elemento> getByElementoDescricao(String elementoDescricao);

}