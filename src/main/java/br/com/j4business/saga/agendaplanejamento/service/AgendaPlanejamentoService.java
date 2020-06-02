package br.com.j4business.saga.agendaplanejamento.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamento;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamentoForm;
import br.com.j4business.saga.email.Mensagem;

@Service
public interface AgendaPlanejamentoService {
	
	public List<AgendaPlanejamento> getAgendaPlanejamentoAll(Pageable pageable);
	public AgendaPlanejamento getAgendaPlanejamentoByAgendaPlanejamentoPK(long agendaPlanejamentoPK);
	public AgendaPlanejamento save(AgendaPlanejamentoForm agendaPlanejamentoForm);
	public void delete(Long agendaPlanejamentoPK);
	public void deleteByPlanejamento(Planejamento planejamento);
	public AgendaPlanejamento create(AgendaPlanejamentoForm agendaPlanejamentoForm);
	public AgendaPlanejamento getByAgendaAndPlanejamento(Agenda agenda, Planejamento planejamento);

	public AgendaPlanejamento converteAgendaPlanejamentoForm(AgendaPlanejamentoForm agendaPlanejamentoForm);
	public AgendaPlanejamentoForm converteAgendaPlanejamento(AgendaPlanejamento agendaPlanejamento);
	public AgendaPlanejamentoForm converteAgendaPlanejamentoView(AgendaPlanejamento agendaPlanejamento);

	public AgendaPlanejamentoForm agendaPlanejamentoParametros(AgendaPlanejamentoForm agendaPlanejamentoForm);

	public List<AgendaPlanejamento> getByAgendaPK(long agendaPK,Pageable pageable);
	public List<AgendaPlanejamento> getByPlanejamentoPK(long planejamentoPK,Pageable pageable);

	public List<AgendaPlanejamento> getByPlanejamentoNome(String planejamentoNome,Pageable pageable);
	public List<AgendaPlanejamento> getByAgendaNome(String agendaNome,Pageable pageable);
	public List<AgendaPlanejamento> getByPlanejamentoNome(String planejamentoNome);
	public List<AgendaPlanejamento> getByAgendaNome(String agendaNome);
	public List<Mensagem> getAgendaPlanejamentoMensagemAll();
	
}