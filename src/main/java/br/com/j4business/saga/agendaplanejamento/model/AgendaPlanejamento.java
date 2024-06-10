package br.com.j4business.saga.agendaplanejamento.model;

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
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.agenda.model.Agenda;

@Entity
@Table(name = "agenda_planejamento", uniqueConstraints=@UniqueConstraint(columnNames={"id_agenda","id_planejamento"}, name="agendaPlanejamentoUnique"))

public class AgendaPlanejamento implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_agendaplanejamento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long agendaPlanejamentoPK;
	
	@ManyToOne
	@JoinColumn(name="id_agenda")
	private Agenda agenda;    
	
	@ManyToOne
	@JoinColumn(name="id_planejamento")
	private Planejamento planejamento;
	    
	@Column(name = "cl_status")
	private String agendaPlanejamentoStatus;

	@Column(name = "cl_motivooperacao")
	private String agendaPlanejamentoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ds_agendaplanejamentoalertaprimeiroenvio")
	private String agendaPlanejamentoAlertaPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertasegundoenvio")
	private String agendaPlanejamentoAlertaSegundoEnvio;

	@Column(name = "ds_agendaplanejamentoalertaterceiroenvio")
	private String agendaPlanejamentoAlertaTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertamensagemprimeiroenvio")
	private String agendaPlanejamentoAlertaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertamensagemsegundoenvio")
	private String agendaPlanejamentoAlertaMensagemSegundoEnvio;

	@Column(name = "ds_agendaplanejamentoalertamensagemterceiroenvio")
	private String agendaPlanejamentoAlertaMensagemTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertadiaprimeiroenvio")
	private String agendaPlanejamentoAlertaDiaPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertadiasegundoenvio")
	private String agendaPlanejamentoAlertaDiaSegundoEnvio;

	@Column(name = "ds_agendaplanejamentoalertadiaterceiroenvio")
	private String agendaPlanejamentoAlertaDiaTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertasuperiorprimeiroenvio")
	private String agendaPlanejamentoAlertaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentoalertasuperiorsegundoenvio")
	private String agendaPlanejamentoAlertaSuperiorSegundoEnvio;

	@Column(name = "ds_agendaplanejamentoalertasuperiorterceiroenvio")
	private String agendaPlanejamentoAlertaSuperiorTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancaprimeiroenvio")
	private String agendaPlanejamentoCobrancaPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancasegundoenvio")
	private String agendaPlanejamentoCobrancaSegundoEnvio;

	@Column(name = "ds_agendaplanejamentocobrancaterceiroenvio")
	private String agendaPlanejamentoCobrancaTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancamensagemprimeiroenvio")
	private String agendaPlanejamentoCobrancaMensagemPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancamensagemsegundoenvio")
	private String agendaPlanejamentoCobrancaMensagemSegundoEnvio;

	@Column(name = "ds_agendaplanejamentocobrancamensagemterceiroenvio")
	private String agendaPlanejamentoCobrancaMensagemTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancadiaprimeiroenvio")
	private String agendaPlanejamentoCobrancaDiaPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancadiasegundoenvio")
	private String agendaPlanejamentoCobrancaDiaSegundoEnvio;

	@Column(name = "ds_agendaplanejamentocobrancadiaterceiroenvio")
	private String agendaPlanejamentoCobrancaDiaTerceiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancasuperiorprimeiroenvio")
	private String agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio;

	@Column(name = "ds_agendaplanejamentocobrancasuperiorsegundoenvio")
	private String agendaPlanejamentoCobrancaSuperiorSegundoEnvio;

	@Column(name = "ds_agendaplanejamentocobrancasuperiorterceiroenvio")
	private String agendaPlanejamentoCobrancaSuperiorTerceiroEnvio;


	public AgendaPlanejamento() {
		super();
	}

	public AgendaPlanejamento(Agenda agenda, Planejamento planejamento, int agendaPlanejamentoSequencia) {
		super();
		this.agenda = agenda;
		this.planejamento = planejamento;
	}

	public long getAgendaPlanejamentoPK() {
		return agendaPlanejamentoPK;
	}

	public void setAgendaPlanejamentoPK(long agendaPlanejamentoPK) {
		this.agendaPlanejamentoPK = agendaPlanejamentoPK;
	}

	public Planejamento getPlanejamento() {
		return planejamento;
	}

	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
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
		result = prime * result + ((planejamento.getPlanejamentoNome() == null) ? 0 : planejamento.getPlanejamentoNome().hashCode());
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
		AgendaPlanejamento other = (AgendaPlanejamento) obj;
		if (agenda.getAgendaNome() == null) {
			if (other.agenda.getAgendaNome() != null)
				return false;
		} else if (!agenda.getAgendaNome().equals(other.agenda.getAgendaNome()))
			return false;
		if (planejamento.getPlanejamentoNome() == null) {
			if (other.planejamento.getPlanejamentoNome() != null)
				return false;
		} else if (!planejamento.getPlanejamentoNome().equals(other.planejamento.getPlanejamentoNome()))
			return false;
		return true;
	}

	public String getAgendaPlanejamentoStatus() {
		return agendaPlanejamentoStatus;
	}

	public void setAgendaPlanejamentoStatus(String agendaPlanejamentoStatus) {
		this.agendaPlanejamentoStatus = agendaPlanejamentoStatus;
	}

	public String getAgendaPlanejamentoMotivoOperacao() {
		return agendaPlanejamentoMotivoOperacao;
	}

	public void setAgendaPlanejamentoMotivoOperacao(String agendaPlanejamentoMotivoOperacao) {
		this.agendaPlanejamentoMotivoOperacao = agendaPlanejamentoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getAgendaPlanejamentoAlertaPrimeiroEnvio() {
		return agendaPlanejamentoAlertaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaPrimeiroEnvio(String agendaPlanejamentoAlertaPrimeiroEnvio) {
		this.agendaPlanejamentoAlertaPrimeiroEnvio = agendaPlanejamentoAlertaPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaSegundoEnvio() {
		return agendaPlanejamentoAlertaSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaSegundoEnvio(String agendaPlanejamentoAlertaSegundoEnvio) {
		this.agendaPlanejamentoAlertaSegundoEnvio = agendaPlanejamentoAlertaSegundoEnvio;
	}

	public String getAgendaPlanejamentoAlertaTerceiroEnvio() {
		return agendaPlanejamentoAlertaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaTerceiroEnvio(String agendaPlanejamentoAlertaTerceiroEnvio) {
		this.agendaPlanejamentoAlertaTerceiroEnvio = agendaPlanejamentoAlertaTerceiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio() {
		return agendaPlanejamentoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaMensagemPrimeiroEnvio(String agendaPlanejamentoAlertaMensagemPrimeiroEnvio) {
		this.agendaPlanejamentoAlertaMensagemPrimeiroEnvio = agendaPlanejamentoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaMensagemSegundoEnvio() {
		return agendaPlanejamentoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaMensagemSegundoEnvio(String agendaPlanejamentoAlertaMensagemSegundoEnvio) {
		this.agendaPlanejamentoAlertaMensagemSegundoEnvio = agendaPlanejamentoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaPlanejamentoAlertaMensagemTerceiroEnvio() {
		return agendaPlanejamentoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaMensagemTerceiroEnvio(String agendaPlanejamentoAlertaMensagemTerceiroEnvio) {
		this.agendaPlanejamentoAlertaMensagemTerceiroEnvio = agendaPlanejamentoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaDiaPrimeiroEnvio() {
		return agendaPlanejamentoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaDiaPrimeiroEnvio(String agendaPlanejamentoAlertaDiaPrimeiroEnvio) {
		this.agendaPlanejamentoAlertaDiaPrimeiroEnvio = agendaPlanejamentoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaDiaSegundoEnvio() {
		return agendaPlanejamentoAlertaDiaSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaDiaSegundoEnvio(String agendaPlanejamentoAlertaDiaSegundoEnvio) {
		this.agendaPlanejamentoAlertaDiaSegundoEnvio = agendaPlanejamentoAlertaDiaSegundoEnvio;
	}

	public String getAgendaPlanejamentoAlertaDiaTerceiroEnvio() {
		return agendaPlanejamentoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaDiaTerceiroEnvio(String agendaPlanejamentoAlertaDiaTerceiroEnvio) {
		this.agendaPlanejamentoAlertaDiaTerceiroEnvio = agendaPlanejamentoAlertaDiaTerceiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio() {
		return agendaPlanejamentoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio(String agendaPlanejamentoAlertaSuperiorPrimeiroEnvio) {
		this.agendaPlanejamentoAlertaSuperiorPrimeiroEnvio = agendaPlanejamentoAlertaSuperiorPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaSuperiorSegundoEnvio() {
		return agendaPlanejamentoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaSuperiorSegundoEnvio(String agendaPlanejamentoAlertaSuperiorSegundoEnvio) {
		this.agendaPlanejamentoAlertaSuperiorSegundoEnvio = agendaPlanejamentoAlertaSuperiorSegundoEnvio;
	}

	public String getAgendaPlanejamentoAlertaSuperiorTerceiroEnvio() {
		return agendaPlanejamentoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaSuperiorTerceiroEnvio(String agendaPlanejamentoAlertaSuperiorTerceiroEnvio) {
		this.agendaPlanejamentoAlertaSuperiorTerceiroEnvio = agendaPlanejamentoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaPrimeiroEnvio() {
		return agendaPlanejamentoCobrancaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaPrimeiroEnvio(String agendaPlanejamentoCobrancaPrimeiroEnvio) {
		this.agendaPlanejamentoCobrancaPrimeiroEnvio = agendaPlanejamentoCobrancaPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaSegundoEnvio() {
		return agendaPlanejamentoCobrancaSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSegundoEnvio(String agendaPlanejamentoCobrancaSegundoEnvio) {
		this.agendaPlanejamentoCobrancaSegundoEnvio = agendaPlanejamentoCobrancaSegundoEnvio;
	}

	public String getAgendaPlanejamentoCobrancaTerceiroEnvio() {
		return agendaPlanejamentoCobrancaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaTerceiroEnvio(String agendaPlanejamentoCobrancaTerceiroEnvio) {
		this.agendaPlanejamentoCobrancaTerceiroEnvio = agendaPlanejamentoCobrancaTerceiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio() {
		return agendaPlanejamentoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio(String agendaPlanejamentoCobrancaMensagemPrimeiroEnvio) {
		this.agendaPlanejamentoCobrancaMensagemPrimeiroEnvio = agendaPlanejamentoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaMensagemSegundoEnvio() {
		return agendaPlanejamentoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaMensagemSegundoEnvio(String agendaPlanejamentoCobrancaMensagemSegundoEnvio) {
		this.agendaPlanejamentoCobrancaMensagemSegundoEnvio = agendaPlanejamentoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio() {
		return agendaPlanejamentoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaMensagemTerceiroEnvio(String agendaPlanejamentoCobrancaMensagemTerceiroEnvio) {
		this.agendaPlanejamentoCobrancaMensagemTerceiroEnvio = agendaPlanejamentoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaDiaPrimeiroEnvio() {
		return agendaPlanejamentoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaDiaPrimeiroEnvio(String agendaPlanejamentoCobrancaDiaPrimeiroEnvio) {
		this.agendaPlanejamentoCobrancaDiaPrimeiroEnvio = agendaPlanejamentoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaDiaSegundoEnvio() {
		return agendaPlanejamentoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaDiaSegundoEnvio(String agendaPlanejamentoCobrancaDiaSegundoEnvio) {
		this.agendaPlanejamentoCobrancaDiaSegundoEnvio = agendaPlanejamentoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaPlanejamentoCobrancaDiaTerceiroEnvio() {
		return agendaPlanejamentoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaDiaTerceiroEnvio(String agendaPlanejamentoCobrancaDiaTerceiroEnvio) {
		this.agendaPlanejamentoCobrancaDiaTerceiroEnvio = agendaPlanejamentoCobrancaDiaTerceiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio() {
		return agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio(String agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio = agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaSuperiorSegundoEnvio() {
		return agendaPlanejamentoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSuperiorSegundoEnvio(String agendaPlanejamentoCobrancaSuperiorSegundoEnvio) {
		this.agendaPlanejamentoCobrancaSuperiorSegundoEnvio = agendaPlanejamentoCobrancaSuperiorSegundoEnvio;
	}

	public String getAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio() {
		return agendaPlanejamentoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio(String agendaPlanejamentoCobrancaSuperiorTerceiroEnvio) {
		this.agendaPlanejamentoCobrancaSuperiorTerceiroEnvio = agendaPlanejamentoCobrancaSuperiorTerceiroEnvio;
	}

}
