package br.com.j4business.saga.agendatreinamento.model;

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
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.agenda.model.Agenda;

@Entity
@Table(name = "agenda_treinamento", uniqueConstraints=@UniqueConstraint(columnNames={"id_agenda","id_treinamento"}, name="agendaTreinamentoUnique"))

public class AgendaTreinamento implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_agendatreinamento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long agendaTreinamentoPK;
	
	@ManyToOne
	@JoinColumn(name="id_agenda")
	private Agenda agenda;    
	
	@ManyToOne
	@JoinColumn(name="id_treinamento")
	private Treinamento treinamento;
	    
	@Column(name = "cl_status")
	private String agendaTreinamentoStatus;

	@Column(name = "cl_motivooperacao")
	private String agendaTreinamentoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ds_agendatreinamentoalertaprimeiroenvio")
	private String agendaTreinamentoAlertaPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentoalertasegundoenvio")
	private String agendaTreinamentoAlertaSegundoEnvio;

	@Column(name = "ds_agendatreinamentoalertaterceiroenvio")
	private String agendaTreinamentoAlertaTerceiroEnvio;

	@Column(name = "ds_agendatreinamentoalertamensagemprimeiroenvio")
	private String agendaTreinamentoAlertaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentoalertamensagemsegundoenvio")
	private String agendaTreinamentoAlertaMensagemSegundoEnvio;

	@Column(name = "ds_agendatreinamentoalertamensagemterceiroenvio")
	private String agendaTreinamentoAlertaMensagemTerceiroEnvio;

	@Column(name = "ds_agendatreinamentoalertadiaprimeiroenvio")
	private String agendaTreinamentoAlertaDiaPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentoalertadiasegundoenvio")
	private String agendaTreinamentoAlertaDiaSegundoEnvio;

	@Column(name = "ds_agendatreinamentoalertadiaterceiroenvio")
	private String agendaTreinamentoAlertaDiaTerceiroEnvio;

	@Column(name = "ds_agendatreinamentoalertasuperiorprimeiroenvio")
	private String agendaTreinamentoAlertaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentoalertasuperiorsegundoenvio")
	private String agendaTreinamentoAlertaSuperiorSegundoEnvio;

	@Column(name = "ds_agendatreinamentoalertasuperiorterceiroenvio")
	private String agendaTreinamentoAlertaSuperiorTerceiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancaprimeiroenvio")
	private String agendaTreinamentoCobrancaPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancasegundoenvio")
	private String agendaTreinamentoCobrancaSegundoEnvio;

	@Column(name = "ds_agendatreinamentocobrancaterceiroenvio")
	private String agendaTreinamentoCobrancaTerceiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancamensagemprimeiroenvio")
	private String agendaTreinamentoCobrancaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancamensagemsegundoenvio")
	private String agendaTreinamentoCobrancaMensagemSegundoEnvio;

	@Column(name = "ds_agendatreinamentocobrancamensagemterceiroenvio")
	private String agendaTreinamentoCobrancaMensagemTerceiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancadiaprimeiroenvio")
	private String agendaTreinamentoCobrancaDiaPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancadiasegundoenvio")
	private String agendaTreinamentoCobrancaDiaSegundoEnvio;

	@Column(name = "ds_agendatreinamentocobrancadiaterceiroenvio")
	private String agendaTreinamentoCobrancaDiaTerceiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancasuperiorprimeiroenvio")
	private String agendaTreinamentoCobrancaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendatreinamentocobrancasuperiorsegundoenvio")
	private String agendaTreinamentoCobrancaSuperiorSegundoEnvio;

	@Column(name = "ds_agendatreinamentocobrancasuperiorterceiroenvio")
	private String agendaTreinamentoCobrancaSuperiorTerceiroEnvio;


	public AgendaTreinamento() {
		super();
	}

	public AgendaTreinamento(Agenda agenda, Treinamento treinamento, int agendaTreinamentoSequencia) {
		super();
		this.agenda = agenda;
		this.treinamento = treinamento;
	}

	public long getAgendaTreinamentoPK() {
		return agendaTreinamentoPK;
	}

	public void setAgendaTreinamentoPK(long agendaTreinamentoPK) {
		this.agendaTreinamentoPK = agendaTreinamentoPK;
	}

	public Treinamento getTreinamento() {
		return treinamento;
	}

	public void setTreinamento(Treinamento treinamento) {
		this.treinamento = treinamento;
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
		result = prime * result + ((treinamento.getTreinamentoNome() == null) ? 0 : treinamento.getTreinamentoNome().hashCode());
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
		AgendaTreinamento other = (AgendaTreinamento) obj;
		if (agenda.getAgendaNome() == null) {
			if (other.agenda.getAgendaNome() != null)
				return false;
		} else if (!agenda.getAgendaNome().equals(other.agenda.getAgendaNome()))
			return false;
		if (treinamento.getTreinamentoNome() == null) {
			if (other.treinamento.getTreinamentoNome() != null)
				return false;
		} else if (!treinamento.getTreinamentoNome().equals(other.treinamento.getTreinamentoNome()))
			return false;
		return true;
	}

	public String getAgendaTreinamentoStatus() {
		return agendaTreinamentoStatus;
	}

	public void setAgendaTreinamentoStatus(String agendaTreinamentoStatus) {
		this.agendaTreinamentoStatus = agendaTreinamentoStatus;
	}

	public String getAgendaTreinamentoMotivoOperacao() {
		return agendaTreinamentoMotivoOperacao;
	}

	public void setAgendaTreinamentoMotivoOperacao(String agendaTreinamentoMotivoOperacao) {
		this.agendaTreinamentoMotivoOperacao = agendaTreinamentoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getAgendaTreinamentoAlertaPrimeiroEnvio() {
		return agendaTreinamentoAlertaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaPrimeiroEnvio(String agendaTreinamentoAlertaPrimeiroEnvio) {
		this.agendaTreinamentoAlertaPrimeiroEnvio = agendaTreinamentoAlertaPrimeiroEnvio;
	}

	public String getAgendaTreinamentoAlertaSegundoEnvio() {
		return agendaTreinamentoAlertaSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaSegundoEnvio(String agendaTreinamentoAlertaSegundoEnvio) {
		this.agendaTreinamentoAlertaSegundoEnvio = agendaTreinamentoAlertaSegundoEnvio;
	}

	public String getAgendaTreinamentoAlertaTerceiroEnvio() {
		return agendaTreinamentoAlertaTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaTerceiroEnvio(String agendaTreinamentoAlertaTerceiroEnvio) {
		this.agendaTreinamentoAlertaTerceiroEnvio = agendaTreinamentoAlertaTerceiroEnvio;
	}

	public String getAgendaTreinamentoAlertaMensagemPrimeiroEnvio() {
		return agendaTreinamentoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaMensagemPrimeiroEnvio(String agendaTreinamentoAlertaMensagemPrimeiroEnvio) {
		this.agendaTreinamentoAlertaMensagemPrimeiroEnvio = agendaTreinamentoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaTreinamentoAlertaMensagemSegundoEnvio() {
		return agendaTreinamentoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaMensagemSegundoEnvio(String agendaTreinamentoAlertaMensagemSegundoEnvio) {
		this.agendaTreinamentoAlertaMensagemSegundoEnvio = agendaTreinamentoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaTreinamentoAlertaMensagemTerceiroEnvio() {
		return agendaTreinamentoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaMensagemTerceiroEnvio(String agendaTreinamentoAlertaMensagemTerceiroEnvio) {
		this.agendaTreinamentoAlertaMensagemTerceiroEnvio = agendaTreinamentoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaTreinamentoAlertaDiaPrimeiroEnvio() {
		return agendaTreinamentoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaDiaPrimeiroEnvio(String agendaTreinamentoAlertaDiaPrimeiroEnvio) {
		this.agendaTreinamentoAlertaDiaPrimeiroEnvio = agendaTreinamentoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaTreinamentoAlertaDiaSegundoEnvio() {
		return agendaTreinamentoAlertaDiaSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaDiaSegundoEnvio(String agendaTreinamentoAlertaDiaSegundoEnvio) {
		this.agendaTreinamentoAlertaDiaSegundoEnvio = agendaTreinamentoAlertaDiaSegundoEnvio;
	}

	public String getAgendaTreinamentoAlertaDiaTerceiroEnvio() {
		return agendaTreinamentoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaDiaTerceiroEnvio(String agendaTreinamentoAlertaDiaTerceiroEnvio) {
		this.agendaTreinamentoAlertaDiaTerceiroEnvio = agendaTreinamentoAlertaDiaTerceiroEnvio;
	}

	public String getAgendaTreinamentoAlertaSuperiorPrimeiroEnvio() {
		return agendaTreinamentoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaSuperiorPrimeiroEnvio(String agendaTreinamentoAlertaSuperiorPrimeiroEnvio) {
		this.agendaTreinamentoAlertaSuperiorPrimeiroEnvio = agendaTreinamentoAlertaSuperiorPrimeiroEnvio;
	}

	public String getAgendaTreinamentoAlertaSuperiorSegundoEnvio() {
		return agendaTreinamentoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaSuperiorSegundoEnvio(String agendaTreinamentoAlertaSuperiorSegundoEnvio) {
		this.agendaTreinamentoAlertaSuperiorSegundoEnvio = agendaTreinamentoAlertaSuperiorSegundoEnvio;
	}

	public String getAgendaTreinamentoAlertaSuperiorTerceiroEnvio() {
		return agendaTreinamentoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaSuperiorTerceiroEnvio(String agendaTreinamentoAlertaSuperiorTerceiroEnvio) {
		this.agendaTreinamentoAlertaSuperiorTerceiroEnvio = agendaTreinamentoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaPrimeiroEnvio() {
		return agendaTreinamentoCobrancaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaPrimeiroEnvio(String agendaTreinamentoCobrancaPrimeiroEnvio) {
		this.agendaTreinamentoCobrancaPrimeiroEnvio = agendaTreinamentoCobrancaPrimeiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaSegundoEnvio() {
		return agendaTreinamentoCobrancaSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaSegundoEnvio(String agendaTreinamentoCobrancaSegundoEnvio) {
		this.agendaTreinamentoCobrancaSegundoEnvio = agendaTreinamentoCobrancaSegundoEnvio;
	}

	public String getAgendaTreinamentoCobrancaTerceiroEnvio() {
		return agendaTreinamentoCobrancaTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaTerceiroEnvio(String agendaTreinamentoCobrancaTerceiroEnvio) {
		this.agendaTreinamentoCobrancaTerceiroEnvio = agendaTreinamentoCobrancaTerceiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio() {
		return agendaTreinamentoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaMensagemPrimeiroEnvio(String agendaTreinamentoCobrancaMensagemPrimeiroEnvio) {
		this.agendaTreinamentoCobrancaMensagemPrimeiroEnvio = agendaTreinamentoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaMensagemSegundoEnvio() {
		return agendaTreinamentoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaMensagemSegundoEnvio(String agendaTreinamentoCobrancaMensagemSegundoEnvio) {
		this.agendaTreinamentoCobrancaMensagemSegundoEnvio = agendaTreinamentoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaTreinamentoCobrancaMensagemTerceiroEnvio() {
		return agendaTreinamentoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaMensagemTerceiroEnvio(String agendaTreinamentoCobrancaMensagemTerceiroEnvio) {
		this.agendaTreinamentoCobrancaMensagemTerceiroEnvio = agendaTreinamentoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaDiaPrimeiroEnvio() {
		return agendaTreinamentoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaDiaPrimeiroEnvio(String agendaTreinamentoCobrancaDiaPrimeiroEnvio) {
		this.agendaTreinamentoCobrancaDiaPrimeiroEnvio = agendaTreinamentoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaDiaSegundoEnvio() {
		return agendaTreinamentoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaDiaSegundoEnvio(String agendaTreinamentoCobrancaDiaSegundoEnvio) {
		this.agendaTreinamentoCobrancaDiaSegundoEnvio = agendaTreinamentoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaTreinamentoCobrancaDiaTerceiroEnvio() {
		return agendaTreinamentoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaDiaTerceiroEnvio(String agendaTreinamentoCobrancaDiaTerceiroEnvio) {
		this.agendaTreinamentoCobrancaDiaTerceiroEnvio = agendaTreinamentoCobrancaDiaTerceiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio() {
		return agendaTreinamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio(String agendaTreinamentoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaTreinamentoCobrancaSuperiorPrimeiroEnvio = agendaTreinamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaSuperiorSegundoEnvio() {
		return agendaTreinamentoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaSuperiorSegundoEnvio(String agendaTreinamentoCobrancaSuperiorSegundoEnvio) {
		this.agendaTreinamentoCobrancaSuperiorSegundoEnvio = agendaTreinamentoCobrancaSuperiorSegundoEnvio;
	}

	public String getAgendaTreinamentoCobrancaSuperiorTerceiroEnvio() {
		return agendaTreinamentoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaSuperiorTerceiroEnvio(String agendaTreinamentoCobrancaSuperiorTerceiroEnvio) {
		this.agendaTreinamentoCobrancaSuperiorTerceiroEnvio = agendaTreinamentoCobrancaSuperiorTerceiroEnvio;
	}

}
