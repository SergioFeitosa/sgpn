package br.com.j4business.saga.eventoprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;
import br.com.j4business.saga.eventoprocesso.model.EventoProcessoForm;

@Service
public interface EventoProcessoService {
	
	public List<EventoProcesso> getEventoProcessoAll(Pageable pageable);
	public EventoProcesso getEventoProcessoByEventoProcessoPK(long eventoProcessoPK);
	public EventoProcesso save(EventoProcessoForm eventoProcessoForm);
	public void delete(Long eventoProcessoPK);
	public void deleteByProcesso(Processo processo);
	public EventoProcesso create(EventoProcessoForm eventoProcessoForm);
	public EventoProcesso getByEventoAndProcesso(Evento evento, Processo processo);

	public EventoProcesso converteEventoProcessoForm(EventoProcessoForm eventoProcessoForm);
	public EventoProcessoForm converteEventoProcesso(EventoProcesso eventoProcesso);
	public EventoProcessoForm converteEventoProcessoView(EventoProcesso eventoProcesso);

	public EventoProcessoForm eventoProcessoParametros(EventoProcessoForm eventoProcessoForm);

	public List<EventoProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<EventoProcesso> getByEventoNome(String eventoNome,Pageable pageable);
	public List<EventoProcesso> getByProcessoNome(String processoNome);
	public List<EventoProcesso> getByEventoNome(String eventoNome);
	public List<EventoProcesso> getByEventoPK(long eventoPK);
	public List<EventoProcesso> getByEventoPK(long eventoPK,Pageable pageable);
	public List<EventoProcesso> getByProcessoPK(long processoPK,Pageable pageable);
}