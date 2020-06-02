package br.com.j4business.saga.evento.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.evento.model.EventoForm;

@Service
public interface EventoService {
	
	public List<Evento> getEventoAll();
	public Page<Evento> getEventoAll(Pageable pageable);
	public Evento getEventoByEventoPK(long eventoPK);
	public Evento create(EventoForm eventoForm);
	public Evento save(EventoForm eventoForm);
	public void delete(Long eventoPK);
	
	public Evento converteEventoForm(EventoForm eventoForm);
	public EventoForm converteEvento(Evento evento);
	public EventoForm converteEventoView(Evento evento);

	public EventoForm eventoParametros(EventoForm eventoForm);

	public List<Evento> getByEventoNome(String eventoNome,Pageable pageable);
	public List<Evento> getByEventoDescricao(String eventoDescricao,Pageable pageable);

	public List<Evento> getByEventoNome(String eventoNome);
	public List<Evento> getByEventoDescricao(String eventoDescricao);

}