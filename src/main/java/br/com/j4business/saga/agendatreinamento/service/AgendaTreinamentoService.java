package br.com.j4business.saga.agendatreinamento.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamento;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamentoForm;
import br.com.j4business.saga.email.Mensagem;

@Service
public interface AgendaTreinamentoService {
	
	public List<AgendaTreinamento> getAgendaTreinamentoAll(Pageable pageable);
	public AgendaTreinamento getAgendaTreinamentoByAgendaTreinamentoPK(long agendaTreinamentoPK);
	public AgendaTreinamento save(AgendaTreinamentoForm agendaTreinamentoForm);
	public void delete(Long agendaTreinamentoPK);
	public void deleteByTreinamento(Treinamento treinamento);
	public AgendaTreinamento create(AgendaTreinamentoForm agendaTreinamentoForm);
	public AgendaTreinamento getByAgendaAndTreinamento(Agenda agenda, Treinamento treinamento);

	public AgendaTreinamento converteAgendaTreinamentoForm(AgendaTreinamentoForm agendaTreinamentoForm);
	public AgendaTreinamentoForm converteAgendaTreinamento(AgendaTreinamento agendaTreinamento);
	public AgendaTreinamentoForm converteAgendaTreinamentoView(AgendaTreinamento agendaTreinamento);

	public AgendaTreinamentoForm agendaTreinamentoParametros(AgendaTreinamentoForm agendaTreinamentoForm);

	public List<AgendaTreinamento> getByAgendaPK(long agendaPK,Pageable pageable);
	public List<AgendaTreinamento> getByTreinamentoPK(long treinamentoPK,Pageable pageable);
	public List<AgendaTreinamento> getByTreinamentoPK(long treinamentoPK);

	public List<AgendaTreinamento> getByTreinamentoNome(String treinamentoNome,Pageable pageable);
	public List<AgendaTreinamento> getByAgendaNome(String agendaNome,Pageable pageable);
	public List<AgendaTreinamento> getByTreinamentoNome(String treinamentoNome);
	public List<AgendaTreinamento> getByAgendaNome(String agendaNome);
	public List<Mensagem> getAgendaTreinamentoMensagemAll();
	public List<Mensagem> getAgendaTreinamentoMensagemColaboradorAll();
	
}