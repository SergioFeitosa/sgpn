package br.com.j4business.saga.agendaevento.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.agendaevento.enumeration.AgendaEventoEnvio;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AgendaEventoForm {

	private long agendaEventoPK;
	private long eventoPK;
	private long agendaPK;
	
    @NotEmpty(message = "Nome do evento é uma informação obrigatória.")
	@NotNull(message = "Nome do evento é uma informação obrigatória.")
	private String eventoNome;
	
    @NotEmpty(message = "Nome da Agenda é uma informação obrigatória.")
	@NotNull(message = "Nome da Agenda é uma informação obrigatória.")
	private String agendaNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  agendaEventoDataCriacao;

	@NotNull(message = "Status da Agenda/Evento é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus agendaEventoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável pela Agenda/Evento é uma informação obrigatória.")
	private String agendaEventoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String agendaEventoMotivoOperacao;

	@NotNull(message = "Opção de Primeiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoAlertaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoAlertaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoAlertaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoAlertaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoAlertaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoAlertaSuperiorTerceiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaEventoAlertaMensagemPrimeiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaEventoAlertaMensagemSegundoEnvio;

    @NotEmpty(message = "Texto da Mensagem do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaEventoAlertaMensagemTerceiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaEventoAlertaDiaPrimeiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaEventoAlertaDiaSegundoEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaEventoAlertaDiaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoCobrancaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoCobrancaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoCobrancaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoCobrancaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoCobrancaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaEventoEnvio agendaEventoCobrancaSuperiorTerceiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaEventoCobrancaMensagemPrimeiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Segundo Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaEventoCobrancaMensagemSegundoEnvio;

    @NotEmpty(message = "Texto da Mensagem do Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaEventoCobrancaMensagemTerceiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Primeiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaEventoCobrancaDiaPrimeiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Segundo Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaEventoCobrancaDiaSegundoEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Terceiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaEventoCobrancaDiaTerceiroEnvio;

	
    
	public AgendaEventoForm() {
		super();
	}

	public long getEventoPK() {
		return eventoPK;
	}

	public void setEventoPK(long eventoPK) {
		this.eventoPK = eventoPK;
	}

	public String getEventoNome() {
		return eventoNome;
	}

	public void setEventoNome(String eventoNome) {
		this.eventoNome = eventoNome;
	}

	public long getAgendaEventoPK() {
		return agendaEventoPK;
	}

	public void setAgendaEventoPK(long agendaEventoPK) {
		this.agendaEventoPK = agendaEventoPK;
	}

	public long getAgendaPK() {
		return agendaPK;
	}

	public void setAgendaPK(long agendaPK) {
		this.agendaPK = agendaPK;
	}

	public String getAgendaNome() {
		return agendaNome;
	}

	public void setAgendaNome(String agendaNome) {
		this.agendaNome = agendaNome;
	}

	public String getAgendaEventoDataCriacao() {
		return agendaEventoDataCriacao;
	}

	public void setAgendaEventoDataCriacao(String agendaEventoDataCriacao) {
		this.agendaEventoDataCriacao = agendaEventoDataCriacao;
	}

	public AtributoStatus getAgendaEventoStatus() {
		return agendaEventoStatus;
	}

	public void setAgendaEventoStatus(AtributoStatus agendaEventoStatus) {
		this.agendaEventoStatus = agendaEventoStatus;
	}

	public String getAgendaEventoResponsavel() {
		return agendaEventoResponsavel;
	}

	public void setAgendaEventoResponsavel(String agendaEventoResponsavel) {
		this.agendaEventoResponsavel = agendaEventoResponsavel;
	}

	public String getAgendaEventoMotivoOperacao() {
		return agendaEventoMotivoOperacao;
	}

	public void setAgendaEventoMotivoOperacao(String agendaEventoMotivoOperacao) {
		this.agendaEventoMotivoOperacao = agendaEventoMotivoOperacao;
	}

	public AgendaEventoEnvio getAgendaEventoAlertaPrimeiroEnvio() {
		return agendaEventoAlertaPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaPrimeiroEnvio(AgendaEventoEnvio agendaEventoAlertaPrimeiroEnvio) {
		this.agendaEventoAlertaPrimeiroEnvio = agendaEventoAlertaPrimeiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoAlertaSegundoEnvio() {
		return agendaEventoAlertaSegundoEnvio;
	}

	public void setAgendaEventoAlertaSegundoEnvio(AgendaEventoEnvio agendaEventoAlertaSegundoEnvio) {
		this.agendaEventoAlertaSegundoEnvio = agendaEventoAlertaSegundoEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoAlertaTerceiroEnvio() {
		return agendaEventoAlertaTerceiroEnvio;
	}

	public void setAgendaEventoAlertaTerceiroEnvio(AgendaEventoEnvio agendaEventoAlertaTerceiroEnvio) {
		this.agendaEventoAlertaTerceiroEnvio = agendaEventoAlertaTerceiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoAlertaSuperiorPrimeiroEnvio() {
		return agendaEventoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaSuperiorPrimeiroEnvio(
			AgendaEventoEnvio agendaEventoAlertaSuperiorPrimeiroEnvio) {
		this.agendaEventoAlertaSuperiorPrimeiroEnvio = agendaEventoAlertaSuperiorPrimeiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoAlertaSuperiorSegundoEnvio() {
		return agendaEventoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaEventoAlertaSuperiorSegundoEnvio(
			AgendaEventoEnvio agendaEventoAlertaSuperiorSegundoEnvio) {
		this.agendaEventoAlertaSuperiorSegundoEnvio = agendaEventoAlertaSuperiorSegundoEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoAlertaSuperiorTerceiroEnvio() {
		return agendaEventoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaEventoAlertaSuperiorTerceiroEnvio(
			AgendaEventoEnvio agendaEventoAlertaSuperiorTerceiroEnvio) {
		this.agendaEventoAlertaSuperiorTerceiroEnvio = agendaEventoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaEventoAlertaMensagemPrimeiroEnvio() {
		return AgendaEventoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaMensagemPrimeiroEnvio(String agendaEventoAlertaMensagemPrimeiroEnvio) {
		AgendaEventoAlertaMensagemPrimeiroEnvio = agendaEventoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaEventoAlertaMensagemSegundoEnvio() {
		return AgendaEventoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaEventoAlertaMensagemSegundoEnvio(String agendaEventoAlertaMensagemSegundoEnvio) {
		AgendaEventoAlertaMensagemSegundoEnvio = agendaEventoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaEventoAlertaMensagemTerceiroEnvio() {
		return AgendaEventoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaEventoAlertaMensagemTerceiroEnvio(String agendaEventoAlertaMensagemTerceiroEnvio) {
		AgendaEventoAlertaMensagemTerceiroEnvio = agendaEventoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaEventoAlertaDiaPrimeiroEnvio() {
		return AgendaEventoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaEventoAlertaDiaPrimeiroEnvio(String agendaEventoAlertaDiaPrimeiroEnvio) {
		AgendaEventoAlertaDiaPrimeiroEnvio = agendaEventoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaEventoAlertaDiaSegundoEnvio() {
		return AgendaEventoAlertaDiaSegundoEnvio;
	}

	public void setAgendaEventoAlertaDiaSegundoEnvio(String agendaEventoAlertaDiaSegundoEnvio) {
		AgendaEventoAlertaDiaSegundoEnvio = agendaEventoAlertaDiaSegundoEnvio;
	}

	public String getAgendaEventoAlertaDiaTerceiroEnvio() {
		return AgendaEventoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaEventoAlertaDiaTerceiroEnvio(String agendaEventoAlertaDiaTerceiroEnvio) {
		AgendaEventoAlertaDiaTerceiroEnvio = agendaEventoAlertaDiaTerceiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoCobrancaPrimeiroEnvio() {
		return agendaEventoCobrancaPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaPrimeiroEnvio(AgendaEventoEnvio agendaEventoCobrancaPrimeiroEnvio) {
		this.agendaEventoCobrancaPrimeiroEnvio = agendaEventoCobrancaPrimeiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoCobrancaSegundoEnvio() {
		return agendaEventoCobrancaSegundoEnvio;
	}

	public void setAgendaEventoCobrancaSegundoEnvio(AgendaEventoEnvio agendaEventoCobrancaSegundoEnvio) {
		this.agendaEventoCobrancaSegundoEnvio = agendaEventoCobrancaSegundoEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoCobrancaTerceiroEnvio() {
		return agendaEventoCobrancaTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaTerceiroEnvio(AgendaEventoEnvio agendaEventoCobrancaTerceiroEnvio) {
		this.agendaEventoCobrancaTerceiroEnvio = agendaEventoCobrancaTerceiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoCobrancaSuperiorPrimeiroEnvio() {
		return agendaEventoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaSuperiorPrimeiroEnvio(
			AgendaEventoEnvio agendaEventoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaEventoCobrancaSuperiorPrimeiroEnvio = agendaEventoCobrancaSuperiorPrimeiroEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoCobrancaSuperiorSegundoEnvio() {
		return agendaEventoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaEventoCobrancaSuperiorSegundoEnvio(
			AgendaEventoEnvio agendaEventoCobrancaSuperiorSegundoEnvio) {
		this.agendaEventoCobrancaSuperiorSegundoEnvio = agendaEventoCobrancaSuperiorSegundoEnvio;
	}

	public AgendaEventoEnvio getAgendaEventoCobrancaSuperiorTerceiroEnvio() {
		return agendaEventoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaSuperiorTerceiroEnvio(
			AgendaEventoEnvio agendaEventoCobrancaSuperiorTerceiroEnvio) {
		this.agendaEventoCobrancaSuperiorTerceiroEnvio = agendaEventoCobrancaSuperiorTerceiroEnvio;
	}

	public String getAgendaEventoCobrancaMensagemPrimeiroEnvio() {
		return AgendaEventoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaMensagemPrimeiroEnvio(String agendaEventoCobrancaMensagemPrimeiroEnvio) {
		AgendaEventoCobrancaMensagemPrimeiroEnvio = agendaEventoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaEventoCobrancaMensagemSegundoEnvio() {
		return AgendaEventoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaEventoCobrancaMensagemSegundoEnvio(String agendaEventoCobrancaMensagemSegundoEnvio) {
		AgendaEventoCobrancaMensagemSegundoEnvio = agendaEventoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaEventoCobrancaMensagemTerceiroEnvio() {
		return AgendaEventoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaMensagemTerceiroEnvio(String agendaEventoCobrancaMensagemTerceiroEnvio) {
		AgendaEventoCobrancaMensagemTerceiroEnvio = agendaEventoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaEventoCobrancaDiaPrimeiroEnvio() {
		return AgendaEventoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaEventoCobrancaDiaPrimeiroEnvio(String agendaEventoCobrancaDiaPrimeiroEnvio) {
		AgendaEventoCobrancaDiaPrimeiroEnvio = agendaEventoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaEventoCobrancaDiaSegundoEnvio() {
		return AgendaEventoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaEventoCobrancaDiaSegundoEnvio(String agendaEventoCobrancaDiaSegundoEnvio) {
		AgendaEventoCobrancaDiaSegundoEnvio = agendaEventoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaEventoCobrancaDiaTerceiroEnvio() {
		return AgendaEventoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaEventoCobrancaDiaTerceiroEnvio(String agendaEventoCobrancaDiaTerceiroEnvio) {
		AgendaEventoCobrancaDiaTerceiroEnvio = agendaEventoCobrancaDiaTerceiroEnvio;
	}

}
