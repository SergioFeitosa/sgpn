package br.com.j4business.saga.agenda.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.model.AgendaForm;

@Service
public interface AgendaService {
	
	public List<Agenda> getAgendaAll();
	public Page<Agenda> getAgendaAll(Pageable pageable);
	public Agenda getAgendaByAgendaPK(long agendaPK);
	public Agenda create(AgendaForm agendaForm);
	public Agenda save(AgendaForm agendaForm);
	public void delete(Long agendaPK);
	
	public Agenda converteAgendaForm(AgendaForm agendaForm);
	public AgendaForm converteAgenda(Agenda agenda);
	public AgendaForm converteAgendaView(Agenda agenda);

	public AgendaForm agendaParametros(AgendaForm agendaForm);

	public List<Agenda> getByAgendaNome(String agendaNome,Pageable pageable);
	public List<Agenda> getByAgendaDescricao(String agendaDescricao,Pageable pageable);

	public List<Agenda> getByAgendaNome(String agendaNome);
	public List<Agenda> getByAgendaDescricao(String agendaDescricao);

}