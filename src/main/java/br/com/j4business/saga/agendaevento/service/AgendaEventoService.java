package br.com.j4business.saga.agendaevento.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.agendaevento.model.AgendaEventoForm;
import br.com.j4business.saga.email.Mensagem;


@Service
public interface AgendaEventoService {
	
	public List<AgendaEvento> getAgendaEventoAll(Pageable pageable);
	public AgendaEvento getAgendaEventoByAgendaEventoPK(long agendaEventoPK);
	public AgendaEvento save(AgendaEventoForm agendaEventoForm);
	public void delete(Long agendaEventoPK);
	public void deleteByEvento(Evento evento);
	public AgendaEvento create(AgendaEventoForm agendaEventoForm);
	public AgendaEvento getByAgendaAndEvento(Agenda agenda, Evento evento);

	public AgendaEvento converteAgendaEventoForm(AgendaEventoForm agendaEventoForm);
	public AgendaEventoForm converteAgendaEvento(AgendaEvento agendaEvento);
	public AgendaEventoForm converteAgendaEventoView(AgendaEvento agendaEvento);

	public AgendaEventoForm agendaEventoParametros(AgendaEventoForm agendaEventoForm);

	public List<AgendaEvento> getByAgendaPK(long agendaPK,Pageable pageable);
	public List<AgendaEvento> getByEventoPK(long eventoPK,Pageable pageable);

	public List<AgendaEvento> getByEventoNome(String eventoNome,Pageable pageable);
	public List<AgendaEvento> getByAgendaNome(String agendaNome,Pageable pageable);
	public List<AgendaEvento> getByEventoNome(String eventoNome);
	public List<AgendaEvento> getByAgendaNome(String agendaNome);
	public List<Mensagem> getAgendaEventoMensagemAll();
	
}