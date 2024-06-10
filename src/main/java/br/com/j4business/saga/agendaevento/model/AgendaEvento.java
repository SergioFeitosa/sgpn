package br.com.j4business.saga.agendaevento.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.agenda.model.Agenda;

@Entity
@Table(name = "agenda_evento", uniqueConstraints=@UniqueConstraint(columnNames={"id_agenda","id_evento"}, name="agendaEventoUnique"))

public class AgendaEvento implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_agendaevento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long agendaEventoPK;
	
	@ManyToOne
	@JoinColumn(name="id_agenda")
	private Agenda agenda;    
	
	@ManyToOne
	@JoinColumn(name="id_evento")
	private Evento evento;
	    
	@Column(name = "cl_status")
	private String agendaEventoStatus;

	@Column(name = "cl_motivooperacao")
	private String agendaEventoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ds_agendaeventoalertaprimeiroenvio")
	private String agendaEventoAlertaPrimeiroEnvio;

	@Column(name = "ds_agendaeventoalertasegundoenvio")
	private String agendaEventoAlertaSegundoEnvio;

	@Column(name = "ds_agendaeventoalertaterceiroenvio")
	private String agendaEventoAlertaTerceiroEnvio;

	@Column(name = "ds_agendaeventoalertamensagemprimeiroenvio")
	private String agendaEventoAlertaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendaeventoalertamensagemsegundoenvio")
	private String agendaEventoAlertaMensagemSegundoEnvio;

	@Column(name = "ds_agendaeventoalertamensagemterceiroenvio")
	private String agendaEventoAlertaMensagemTerceiroEnvio;

	@Column(name = "ds_agendaeventoalertadiaprimeiroenvio")
	private String agendaEventoAlertaDiaPrimeiroEnvio;

	@Column(name = "ds_agendaeventoalertadiasegundoenvio")
	private String agendaEventoAlertaDiaSegundoEnvio;

	@Column(name = "ds_agendaeventoalertadiaterceiroenvio")
	private String agendaEventoAlertaDiaTerceiroEnvio;

	@Column(name = "ds_agendaeventoalertasuperiorprimeiroenvio")
	private String agendaEventoAlertaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendaeventoalertasuperiorsegundoenvio")
	private String agendaEventoAlertaSuperiorSegundoEnvio;

	@Column(name = "ds_agendaeventoalertasuperiorterceiroenvio")
	private String agendaEventoAlertaSuperiorTerceiroEnvio;

	@Column(name = "ds_agendaeventocobrancaprimeiroenvio")
	private String agendaEventoCobrancaPrimeiroEnvio;

	@Column(name = "ds_agendaeventocobrancasegundoenvio")
	private String agendaEventoCobrancaSegundoEnvio;

	@Column(name = "ds_agendaeventocobrancaterceiroenvio")
	private String agendaEventoCobrancaTerceiroEnvio;

	@Column(name = "ds_agendaeventocobrancamensagemprimeiroenvio")
	private String agendaEventoCobrancaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendaeventocobrancamensagemsegundoenvio")
	private String agendaEventoCobrancaMensagemSegundoEnvio;

	@Column(name = "ds_agendaeventocobrancamensagemterceiroenvio")
	private String agendaEventoCobrancaMensagemTerceiroEnvio;

	@Column(name = "ds_agendaeventocobrancadiaprimeiroenvio")
	private String agendaEventoCobrancaDiaPrimeiroEnvio;

	@Column(name = "ds_agendaeventocobrancadiasegundoenvio")
	private String agendaEventoCobrancaDiaSegundoEnvio;

	@Column(name = "ds_agendaeventocobrancadiaterceiroenvio")
	private String agendaEventoCobrancaDiaTerceiroEnvio;

	@Column(name = "ds_agendaeventocobrancasuperiorprimeiroenvio")
	private String agendaEventoCobrancaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendaeventocobrancasuperiorsegundoenvio")
	private String agendaEventoCobrancaSuperiorSegundoEnvio;

	@Column(name = "ds_agendaeventocobrancasuperiorterceiroenvio")
	private String agendaEventoCobrancaSuperiorTerceiroEnvio;


	public AgendaEvento() {
		super();
	}

	public AgendaEvento(Agenda agenda, Evento evento, int agendaEventoSequencia) {
		super();
		this.agenda = agenda;
		this.evento = evento;
	}

	public long getAgendaEventoPK() {
		return agendaEventoPK;
	}

	public void setAgendaEventoPK(long agendaEventoPK) {
		this.agendaEventoPK = agendaEventoPK;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenda.getAgendaNome() == null) ? 0 : agenda.getAgendaNome().hashCode());
		result = prime * result + ((evento.getEventoNome() == null) ? 0 : evento.getEventoNome().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgendaEvento other = (AgendaEvento) obj;
		if (agenda.getAgendaNome() == null) {
			if (other.agenda.getAgendaNome() != null)
				return false;
		} else if (!agenda.getAgendaNome().equals(other.agenda.getAgendaNome()))
			return false;
		if (evento.getEventoNome() == null) {
			if (other.evento.getEventoNome() != null)
				return false;
		} else if (!evento.getEventoNome().equals(other.evento.getEventoNome()))
			return false;
		return true;
	}

	public String getAgendaEventoStatus() {
		return agendaEventoStatus;
	}

	public void setAgendaEventoStatus(String agendaEventoStatus) {
		this.agendaEventoStatus = agendaEventoStatus;
	}

	public String getAgendaEventoMotivoOperacao() {
		return agendaEventoMotivoOperacao;
	}

	public void setAgendaEventoMotivoOperacao(String agendaEventoMotivoOperacao) {
		this.agendaEventoMotivoOperacao = agendaEventoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getAgendaEventoAlertaPrimeiroEnvio() {
		return agendaEventoAlertaPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaPrimeiroEnvio(String agendaEventoAlertaPrimeiroEnvio) {
		this.agendaEventoAlertaPrimeiroEnvio = agendaEventoAlertaPrimeiroEnvio;
	}

	public String getAgendaEventoAlertaSegundoEnvio() {
		return agendaEventoAlertaSegundoEnvio;
	}

	public void setAgendaEventoAlertaSegundoEnvio(String agendaEventoAlertaSegundoEnvio) {
		this.agendaEventoAlertaSegundoEnvio = agendaEventoAlertaSegundoEnvio;
	}

	public String getAgendaEventoAlertaTerceiroEnvio() {
		return agendaEventoAlertaTerceiroEnvio;
	}

	public void setAgendaEventoAlertaTerceiroEnvio(String agendaEventoAlertaTerceiroEnvio) {
		this.agendaEventoAlertaTerceiroEnvio = agendaEventoAlertaTerceiroEnvio;
	}

	public String getAgendaEventoAlertaMensagemPrimeiroEnvio() {
		return agendaEventoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaMensagemPrimeiroEnvio(String agendaEventoAlertaMensagemPrimeiroEnvio) {
		this.agendaEventoAlertaMensagemPrimeiroEnvio = agendaEventoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaEventoAlertaMensagemSegundoEnvio() {
		return agendaEventoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaEventoAlertaMensagemSegundoEnvio(String agendaEventoAlertaMensagemSegundoEnvio) {
		this.agendaEventoAlertaMensagemSegundoEnvio = agendaEventoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaEventoAlertaMensagemTerceiroEnvio() {
		return agendaEventoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaEventoAlertaMensagemTerceiroEnvio(String agendaEventoAlertaMensagemTerceiroEnvio) {
		this.agendaEventoAlertaMensagemTerceiroEnvio = agendaEventoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaEventoAlertaDiaPrimeiroEnvio() {
		return agendaEventoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaDiaPrimeiroEnvio(String agendaEventoAlertaDiaPrimeiroEnvio) {
		this.agendaEventoAlertaDiaPrimeiroEnvio = agendaEventoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaEventoAlertaDiaSegundoEnvio() {
		return agendaEventoAlertaDiaSegundoEnvio;
	}

	public void setAgendaEventoAlertaDiaSegundoEnvio(String agendaEventoAlertaDiaSegundoEnvio) {
		this.agendaEventoAlertaDiaSegundoEnvio = agendaEventoAlertaDiaSegundoEnvio;
	}

	public String getAgendaEventoAlertaDiaTerceiroEnvio() {
		return agendaEventoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaEventoAlertaDiaTerceiroEnvio(String agendaEventoAlertaDiaTerceiroEnvio) {
		this.agendaEventoAlertaDiaTerceiroEnvio = agendaEventoAlertaDiaTerceiroEnvio;
	}

	public String getAgendaEventoAlertaSuperiorPrimeiroEnvio() {
		return agendaEventoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaSuperiorPrimeiroEnvio(String agendaEventoAlertaSuperiorPrimeiroEnvio) {
		this.agendaEventoAlertaSuperiorPrimeiroEnvio = agendaEventoAlertaSuperiorPrimeiroEnvio;
	}

	public String getAgendaEventoAlertaSuperiorSegundoEnvio() {
		return agendaEventoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaEventoAlertaSuperiorSegundoEnvio(String agendaEventoAlertaSuperiorSegundoEnvio) {
		this.agendaEventoAlertaSuperiorSegundoEnvio = agendaEventoAlertaSuperiorSegundoEnvio;
	}

	public String getAgendaEventoAlertaSuperiorTerceiroEnvio() {
		return agendaEventoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaEventoAlertaSuperiorTerceiroEnvio(String agendaEventoAlertaSuperiorTerceiroEnvio) {
		this.agendaEventoAlertaSuperiorTerceiroEnvio = agendaEventoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaEventoCobrancaPrimeiroEnvio() {
		return agendaEventoCobrancaPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaPrimeiroEnvio(String agendaEventoCobrancaPrimeiroEnvio) {
		this.agendaEventoCobrancaPrimeiroEnvio = agendaEventoCobrancaPrimeiroEnvio;
	}

	public String getAgendaEventoCobrancaSegundoEnvio() {
		return agendaEventoCobrancaSegundoEnvio;
	}

	public void setAgendaEventoCobrancaSegundoEnvio(String agendaEventoCobrancaSegundoEnvio) {
		this.agendaEventoCobrancaSegundoEnvio = agendaEventoCobrancaSegundoEnvio;
	}

	public String getAgendaEventoCobrancaTerceiroEnvio() {
		return agendaEventoCobrancaTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaTerceiroEnvio(String agendaEventoCobrancaTerceiroEnvio) {
		this.agendaEventoCobrancaTerceiroEnvio = agendaEventoCobrancaTerceiroEnvio;
	}

	public String getAgendaEventoCobrancaMensagemPrimeiroEnvio() {
		return agendaEventoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaMensagemPrimeiroEnvio(String agendaEventoCobrancaMensagemPrimeiroEnvio) {
		this.agendaEventoCobrancaMensagemPrimeiroEnvio = agendaEventoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaEventoCobrancaMensagemSegundoEnvio() {
		return agendaEventoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaEventoCobrancaMensagemSegundoEnvio(String agendaEventoCobrancaMensagemSegundoEnvio) {
		this.agendaEventoCobrancaMensagemSegundoEnvio = agendaEventoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaEventoCobrancaMensagemTerceiroEnvio() {
		return agendaEventoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaMensagemTerceiroEnvio(String agendaEventoCobrancaMensagemTerceiroEnvio) {
		this.agendaEventoCobrancaMensagemTerceiroEnvio = agendaEventoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaEventoCobrancaDiaPrimeiroEnvio() {
		return agendaEventoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaDiaPrimeiroEnvio(String agendaEventoCobrancaDiaPrimeiroEnvio) {
		this.agendaEventoCobrancaDiaPrimeiroEnvio = agendaEventoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaEventoCobrancaDiaSegundoEnvio() {
		return agendaEventoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaEventoCobrancaDiaSegundoEnvio(String agendaEventoCobrancaDiaSegundoEnvio) {
		this.agendaEventoCobrancaDiaSegundoEnvio = agendaEventoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaEventoCobrancaDiaTerceiroEnvio() {
		return agendaEventoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaDiaTerceiroEnvio(String agendaEventoCobrancaDiaTerceiroEnvio) {
		this.agendaEventoCobrancaDiaTerceiroEnvio = agendaEventoCobrancaDiaTerceiroEnvio;
	}

	public String getAgendaEventoCobrancaSuperiorPrimeiroEnvio() {
		return agendaEventoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaSuperiorPrimeiroEnvio(String agendaEventoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaEventoCobrancaSuperiorPrimeiroEnvio = agendaEventoCobrancaSuperiorPrimeiroEnvio;
	}

	public String getAgendaEventoCobrancaSuperiorSegundoEnvio() {
		return agendaEventoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaEventoCobrancaSuperiorSegundoEnvio(String agendaEventoCobrancaSuperiorSegundoEnvio) {
		this.agendaEventoCobrancaSuperiorSegundoEnvio = agendaEventoCobrancaSuperiorSegundoEnvio;
	}

	public String getAgendaEventoCobrancaSuperiorTerceiroEnvio() {
		return agendaEventoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaSuperiorTerceiroEnvio(String agendaEventoCobrancaSuperiorTerceiroEnvio) {
		this.agendaEventoCobrancaSuperiorTerceiroEnvio = agendaEventoCobrancaSuperiorTerceiroEnvio;
	}

}
