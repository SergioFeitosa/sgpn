package br.com.j4business.saga.agendacontrato.model;

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
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.agenda.model.Agenda;

@Entity
@Table(name = "agenda_contrato", uniqueConstraints=@UniqueConstraint(columnNames={"id_agenda","id_contrato"}, name="agendaContratoUnique"))

public class AgendaContrato implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_agendacontrato")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long agendaContratoPK;
	
	@ManyToOne
	@JoinColumn(name="id_agenda")
	private Agenda agenda;    
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;
	    
	@Column(name = "cl_status")
	private String agendaContratoStatus;

	@Column(name = "cl_motivooperacao")
	private String agendaContratoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ds_agendacontratoalertaprimeiroenvio")
	private String agendaContratoAlertaPrimeiroEnvio;

	@Column(name = "ds_agendacontratoalertasegundoenvio")
	private String agendaContratoAlertaSegundoEnvio;

	@Column(name = "ds_agendacontratoalertaterceiroenvio")
	private String agendaContratoAlertaTerceiroEnvio;

	@Column(name = "ds_agendacontratoalertamensagemprimeiroenvio")
	private String agendaContratoAlertaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendacontratoalertamensagemsegundoenvio")
	private String agendaContratoAlertaMensagemSegundoEnvio;

	@Column(name = "ds_agendacontratoalertamensagemterceiroenvio")
	private String agendaContratoAlertaMensagemTerceiroEnvio;

	@Column(name = "ds_agendacontratoalertadiaprimeiroenvio")
	private String agendaContratoAlertaDiaPrimeiroEnvio;

	@Column(name = "ds_agendacontratoalertadiasegundoenvio")
	private String agendaContratoAlertaDiaSegundoEnvio;

	@Column(name = "ds_agendacontratoalertadiaterceiroenvio")
	private String agendaContratoAlertaDiaTerceiroEnvio;

	@Column(name = "ds_agendacontratoalertasuperiorprimeiroenvio")
	private String agendaContratoAlertaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendacontratoalertasuperiorsegundoenvio")
	private String agendaContratoAlertaSuperiorSegundoEnvio;

	@Column(name = "ds_agendacontratoalertasuperiorterceiroenvio")
	private String agendaContratoAlertaSuperiorTerceiroEnvio;

	@Column(name = "ds_agendacontratocobrancaprimeiroenvio")
	private String agendaContratoCobrancaPrimeiroEnvio;

	@Column(name = "ds_agendacontratocobrancasegundoenvio")
	private String agendaContratoCobrancaSegundoEnvio;

	@Column(name = "ds_agendacontratocobrancaterceiroenvio")
	private String agendaContratoCobrancaTerceiroEnvio;

	@Column(name = "ds_agendacontratocobrancamensagemprimeiroenvio")
	private String agendaContratoCobrancaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendacontratocobrancamensagemsegundoenvio")
	private String agendaContratoCobrancaMensagemSegundoEnvio;

	@Column(name = "ds_agendacontratocobrancamensagemterceiroenvio")
	private String agendaContratoCobrancaMensagemTerceiroEnvio;

	@Column(name = "ds_agendacontratocobrancadiaprimeiroenvio")
	private String agendaContratoCobrancaDiaPrimeiroEnvio;

	@Column(name = "ds_agendacontratocobrancadiasegundoenvio")
	private String agendaContratoCobrancaDiaSegundoEnvio;

	@Column(name = "ds_agendacontratocobrancadiaterceiroenvio")
	private String agendaContratoCobrancaDiaTerceiroEnvio;

	@Column(name = "ds_agendacontratocobrancasuperiorprimeiroenvio")
	private String agendaContratoCobrancaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendacontratocobrancasuperiorsegundoenvio")
	private String agendaContratoCobrancaSuperiorSegundoEnvio;

	@Column(name = "ds_agendacontratocobrancasuperiorterceiroenvio")
	private String agendaContratoCobrancaSuperiorTerceiroEnvio;


	public AgendaContrato() {
		super();
	}

	public AgendaContrato(Agenda agenda, Contrato contrato, int agendaContratoSequencia) {
		super();
		this.agenda = agenda;
		this.contrato = contrato;
	}

	public long getAgendaContratoPK() {
		return agendaContratoPK;
	}

	public void setAgendaContratoPK(long agendaContratoPK) {
		this.agendaContratoPK = agendaContratoPK;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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
		result = prime * result + ((contrato.getContratoNome() == null) ? 0 : contrato.getContratoNome().hashCode());
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
		AgendaContrato other = (AgendaContrato) obj;
		if (agenda.getAgendaNome() == null) {
			if (other.agenda.getAgendaNome() != null)
				return false;
		} else if (!agenda.getAgendaNome().equals(other.agenda.getAgendaNome()))
			return false;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
			return false;
		return true;
	}

	public String getAgendaContratoStatus() {
		return agendaContratoStatus;
	}

	public void setAgendaContratoStatus(String agendaContratoStatus) {
		this.agendaContratoStatus = agendaContratoStatus;
	}

	public String getAgendaContratoMotivoOperacao() {
		return agendaContratoMotivoOperacao;
	}

	public void setAgendaContratoMotivoOperacao(String agendaContratoMotivoOperacao) {
		this.agendaContratoMotivoOperacao = agendaContratoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getAgendaContratoAlertaPrimeiroEnvio() {
		return agendaContratoAlertaPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaPrimeiroEnvio(String agendaContratoAlertaPrimeiroEnvio) {
		this.agendaContratoAlertaPrimeiroEnvio = agendaContratoAlertaPrimeiroEnvio;
	}

	public String getAgendaContratoAlertaSegundoEnvio() {
		return agendaContratoAlertaSegundoEnvio;
	}

	public void setAgendaContratoAlertaSegundoEnvio(String agendaContratoAlertaSegundoEnvio) {
		this.agendaContratoAlertaSegundoEnvio = agendaContratoAlertaSegundoEnvio;
	}

	public String getAgendaContratoAlertaTerceiroEnvio() {
		return agendaContratoAlertaTerceiroEnvio;
	}

	public void setAgendaContratoAlertaTerceiroEnvio(String agendaContratoAlertaTerceiroEnvio) {
		this.agendaContratoAlertaTerceiroEnvio = agendaContratoAlertaTerceiroEnvio;
	}

	public String getAgendaContratoAlertaMensagemPrimeiroEnvio() {
		return agendaContratoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaMensagemPrimeiroEnvio(String agendaContratoAlertaMensagemPrimeiroEnvio) {
		this.agendaContratoAlertaMensagemPrimeiroEnvio = agendaContratoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaContratoAlertaMensagemSegundoEnvio() {
		return agendaContratoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaContratoAlertaMensagemSegundoEnvio(String agendaContratoAlertaMensagemSegundoEnvio) {
		this.agendaContratoAlertaMensagemSegundoEnvio = agendaContratoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaContratoAlertaMensagemTerceiroEnvio() {
		return agendaContratoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaContratoAlertaMensagemTerceiroEnvio(String agendaContratoAlertaMensagemTerceiroEnvio) {
		this.agendaContratoAlertaMensagemTerceiroEnvio = agendaContratoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaContratoAlertaDiaPrimeiroEnvio() {
		return agendaContratoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaDiaPrimeiroEnvio(String agendaContratoAlertaDiaPrimeiroEnvio) {
		this.agendaContratoAlertaDiaPrimeiroEnvio = agendaContratoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaContratoAlertaDiaSegundoEnvio() {
		return agendaContratoAlertaDiaSegundoEnvio;
	}

	public void setAgendaContratoAlertaDiaSegundoEnvio(String agendaContratoAlertaDiaSegundoEnvio) {
		this.agendaContratoAlertaDiaSegundoEnvio = agendaContratoAlertaDiaSegundoEnvio;
	}

	public String getAgendaContratoAlertaDiaTerceiroEnvio() {
		return agendaContratoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaContratoAlertaDiaTerceiroEnvio(String agendaContratoAlertaDiaTerceiroEnvio) {
		this.agendaContratoAlertaDiaTerceiroEnvio = agendaContratoAlertaDiaTerceiroEnvio;
	}

	public String getAgendaContratoAlertaSuperiorPrimeiroEnvio() {
		return agendaContratoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaSuperiorPrimeiroEnvio(String agendaContratoAlertaSuperiorPrimeiroEnvio) {
		this.agendaContratoAlertaSuperiorPrimeiroEnvio = agendaContratoAlertaSuperiorPrimeiroEnvio;
	}

	public String getAgendaContratoAlertaSuperiorSegundoEnvio() {
		return agendaContratoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaContratoAlertaSuperiorSegundoEnvio(String agendaContratoAlertaSuperiorSegundoEnvio) {
		this.agendaContratoAlertaSuperiorSegundoEnvio = agendaContratoAlertaSuperiorSegundoEnvio;
	}

	public String getAgendaContratoAlertaSuperiorTerceiroEnvio() {
		return agendaContratoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaContratoAlertaSuperiorTerceiroEnvio(String agendaContratoAlertaSuperiorTerceiroEnvio) {
		this.agendaContratoAlertaSuperiorTerceiroEnvio = agendaContratoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaContratoCobrancaPrimeiroEnvio() {
		return agendaContratoCobrancaPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaPrimeiroEnvio(String agendaContratoCobrancaPrimeiroEnvio) {
		this.agendaContratoCobrancaPrimeiroEnvio = agendaContratoCobrancaPrimeiroEnvio;
	}

	public String getAgendaContratoCobrancaSegundoEnvio() {
		return agendaContratoCobrancaSegundoEnvio;
	}

	public void setAgendaContratoCobrancaSegundoEnvio(String agendaContratoCobrancaSegundoEnvio) {
		this.agendaContratoCobrancaSegundoEnvio = agendaContratoCobrancaSegundoEnvio;
	}

	public String getAgendaContratoCobrancaTerceiroEnvio() {
		return agendaContratoCobrancaTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaTerceiroEnvio(String agendaContratoCobrancaTerceiroEnvio) {
		this.agendaContratoCobrancaTerceiroEnvio = agendaContratoCobrancaTerceiroEnvio;
	}

	public String getAgendaContratoCobrancaMensagemPrimeiroEnvio() {
		return agendaContratoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaMensagemPrimeiroEnvio(String agendaContratoCobrancaMensagemPrimeiroEnvio) {
		this.agendaContratoCobrancaMensagemPrimeiroEnvio = agendaContratoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaContratoCobrancaMensagemSegundoEnvio() {
		return agendaContratoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaContratoCobrancaMensagemSegundoEnvio(String agendaContratoCobrancaMensagemSegundoEnvio) {
		this.agendaContratoCobrancaMensagemSegundoEnvio = agendaContratoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaContratoCobrancaMensagemTerceiroEnvio() {
		return agendaContratoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaMensagemTerceiroEnvio(String agendaContratoCobrancaMensagemTerceiroEnvio) {
		this.agendaContratoCobrancaMensagemTerceiroEnvio = agendaContratoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaContratoCobrancaDiaPrimeiroEnvio() {
		return agendaContratoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaDiaPrimeiroEnvio(String agendaContratoCobrancaDiaPrimeiroEnvio) {
		this.agendaContratoCobrancaDiaPrimeiroEnvio = agendaContratoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaContratoCobrancaDiaSegundoEnvio() {
		return agendaContratoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaContratoCobrancaDiaSegundoEnvio(String agendaContratoCobrancaDiaSegundoEnvio) {
		this.agendaContratoCobrancaDiaSegundoEnvio = agendaContratoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaContratoCobrancaDiaTerceiroEnvio() {
		return agendaContratoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaDiaTerceiroEnvio(String agendaContratoCobrancaDiaTerceiroEnvio) {
		this.agendaContratoCobrancaDiaTerceiroEnvio = agendaContratoCobrancaDiaTerceiroEnvio;
	}

	public String getAgendaContratoCobrancaSuperiorPrimeiroEnvio() {
		return agendaContratoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaSuperiorPrimeiroEnvio(String agendaContratoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaContratoCobrancaSuperiorPrimeiroEnvio = agendaContratoCobrancaSuperiorPrimeiroEnvio;
	}

	public String getAgendaContratoCobrancaSuperiorSegundoEnvio() {
		return agendaContratoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaContratoCobrancaSuperiorSegundoEnvio(String agendaContratoCobrancaSuperiorSegundoEnvio) {
		this.agendaContratoCobrancaSuperiorSegundoEnvio = agendaContratoCobrancaSuperiorSegundoEnvio;
	}

	public String getAgendaContratoCobrancaSuperiorTerceiroEnvio() {
		return agendaContratoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaSuperiorTerceiroEnvio(String agendaContratoCobrancaSuperiorTerceiroEnvio) {
		this.agendaContratoCobrancaSuperiorTerceiroEnvio = agendaContratoCobrancaSuperiorTerceiroEnvio;
	}

}
