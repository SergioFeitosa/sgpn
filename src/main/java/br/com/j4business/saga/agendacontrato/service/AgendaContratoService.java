package br.com.j4business.saga.agendacontrato.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agendacontrato.model.AgendaContrato;
import br.com.j4business.saga.agendacontrato.model.AgendaContratoForm;
import br.com.j4business.saga.email.Mensagem;


@Service
public interface AgendaContratoService {
	
	public List<AgendaContrato> getAgendaContratoAll(Pageable pageable);
	public AgendaContrato getAgendaContratoByAgendaContratoPK(long agendaContratoPK);
	public AgendaContrato save(AgendaContratoForm agendaContratoForm);
	public void delete(Long agendaContratoPK);
	public void deleteByContrato(Contrato contrato);
	public AgendaContrato create(AgendaContratoForm agendaContratoForm);
	public AgendaContrato getByAgendaAndContrato(Agenda agenda, Contrato contrato);

	public AgendaContrato converteAgendaContratoForm(AgendaContratoForm agendaContratoForm);
	public AgendaContratoForm converteAgendaContrato(AgendaContrato agendaContrato);
	public AgendaContratoForm converteAgendaContratoView(AgendaContrato agendaContrato);

	public AgendaContratoForm agendaContratoParametros(AgendaContratoForm agendaContratoForm);

	public List<AgendaContrato> getByAgendaPK(long agendaPK,Pageable pageable);
	public List<AgendaContrato> getByContratoPK(long contratoPK,Pageable pageable);

	public List<AgendaContrato> getByContratoNome(String contratoNome,Pageable pageable);
	public List<AgendaContrato> getByAgendaNome(String agendaNome,Pageable pageable);
	public List<AgendaContrato> getByContratoNome(String contratoNome);
	public List<AgendaContrato> getByAgendaNome(String agendaNome);
	public List<Mensagem> getAgendaContratoMensagemAll();
	
}