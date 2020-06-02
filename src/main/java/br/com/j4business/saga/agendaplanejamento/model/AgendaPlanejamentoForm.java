package br.com.j4business.saga.agendaplanejamento.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.agendaplanejamento.enumeration.AgendaPlanejamentoEnvio;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AgendaPlanejamentoForm {

	private long agendaPlanejamentoPK;
	private long planejamentoPK;
	private long agendaPK;
	
    @NotEmpty(message = "Nome do planejamento é uma informação obrigatória.")
	@NotNull(message = "Nome do planejamento é uma informação obrigatória.")
	private String planejamentoNome;
	
    @NotEmpty(message = "Nome da Agenda é uma informação obrigatória.")
	@NotNull(message = "Nome da Agenda é uma informação obrigatória.")
	private String agendaNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  agendaPlanejamentoDataCriacao;

	@NotNull(message = "Status da Agenda/Planejamento é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus agendaPlanejamentoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável pela Agenda/Planejamento é uma informação obrigatória.")
	private String agendaPlanejamentoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String agendaPlanejamentoMotivoOperacao;

	@NotNull(message = "Opção de Primeiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoAlertaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoAlertaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSuperiorTerceiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaPlanejamentoAlertaMensagemPrimeiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaPlanejamentoAlertaMensagemSegundoEnvio;

    @NotEmpty(message = "Texto da Mensagem do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaPlanejamentoAlertaMensagemTerceiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaPlanejamentoAlertaDiaPrimeiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaPlanejamentoAlertaDiaSegundoEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaPlanejamentoAlertaDiaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSuperiorTerceiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaPlanejamentoCobrancaMensagemPrimeiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Segundo Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaPlanejamentoCobrancaMensagemSegundoEnvio;

    @NotEmpty(message = "Texto da Mensagem do Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaPlanejamentoCobrancaMensagemTerceiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Primeiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaPlanejamentoCobrancaDiaPrimeiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Segundo Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaPlanejamentoCobrancaDiaSegundoEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Terceiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaPlanejamentoCobrancaDiaTerceiroEnvio;

	
    
	public AgendaPlanejamentoForm() {
		super();
	}

	public long getPlanejamentoPK() {
		return planejamentoPK;
	}

	public void setPlanejamentoPK(long planejamentoPK) {
		this.planejamentoPK = planejamentoPK;
	}

	public String getPlanejamentoNome() {
		return planejamentoNome;
	}

	public void setPlanejamentoNome(String planejamentoNome) {
		this.planejamentoNome = planejamentoNome;
	}

	public long getAgendaPlanejamentoPK() {
		return agendaPlanejamentoPK;
	}

	public void setAgendaPlanejamentoPK(long agendaPlanejamentoPK) {
		this.agendaPlanejamentoPK = agendaPlanejamentoPK;
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

	public String getAgendaPlanejamentoDataCriacao() {
		return agendaPlanejamentoDataCriacao;
	}

	public void setAgendaPlanejamentoDataCriacao(String agendaPlanejamentoDataCriacao) {
		this.agendaPlanejamentoDataCriacao = agendaPlanejamentoDataCriacao;
	}

	public AtributoStatus getAgendaPlanejamentoStatus() {
		return agendaPlanejamentoStatus;
	}

	public void setAgendaPlanejamentoStatus(AtributoStatus agendaPlanejamentoStatus) {
		this.agendaPlanejamentoStatus = agendaPlanejamentoStatus;
	}

	public String getAgendaPlanejamentoResponsavel() {
		return agendaPlanejamentoResponsavel;
	}

	public void setAgendaPlanejamentoResponsavel(String agendaPlanejamentoResponsavel) {
		this.agendaPlanejamentoResponsavel = agendaPlanejamentoResponsavel;
	}

	public String getAgendaPlanejamentoMotivoOperacao() {
		return agendaPlanejamentoMotivoOperacao;
	}

	public void setAgendaPlanejamentoMotivoOperacao(String agendaPlanejamentoMotivoOperacao) {
		this.agendaPlanejamentoMotivoOperacao = agendaPlanejamentoMotivoOperacao;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoAlertaPrimeiroEnvio() {
		return agendaPlanejamentoAlertaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaPrimeiroEnvio(AgendaPlanejamentoEnvio agendaPlanejamentoAlertaPrimeiroEnvio) {
		this.agendaPlanejamentoAlertaPrimeiroEnvio = agendaPlanejamentoAlertaPrimeiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoAlertaSegundoEnvio() {
		return agendaPlanejamentoAlertaSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaSegundoEnvio(AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSegundoEnvio) {
		this.agendaPlanejamentoAlertaSegundoEnvio = agendaPlanejamentoAlertaSegundoEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoAlertaTerceiroEnvio() {
		return agendaPlanejamentoAlertaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaTerceiroEnvio(AgendaPlanejamentoEnvio agendaPlanejamentoAlertaTerceiroEnvio) {
		this.agendaPlanejamentoAlertaTerceiroEnvio = agendaPlanejamentoAlertaTerceiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio() {
		return agendaPlanejamentoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio(
			AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSuperiorPrimeiroEnvio) {
		this.agendaPlanejamentoAlertaSuperiorPrimeiroEnvio = agendaPlanejamentoAlertaSuperiorPrimeiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoAlertaSuperiorSegundoEnvio() {
		return agendaPlanejamentoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaSuperiorSegundoEnvio(
			AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSuperiorSegundoEnvio) {
		this.agendaPlanejamentoAlertaSuperiorSegundoEnvio = agendaPlanejamentoAlertaSuperiorSegundoEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoAlertaSuperiorTerceiroEnvio() {
		return agendaPlanejamentoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaSuperiorTerceiroEnvio(
			AgendaPlanejamentoEnvio agendaPlanejamentoAlertaSuperiorTerceiroEnvio) {
		this.agendaPlanejamentoAlertaSuperiorTerceiroEnvio = agendaPlanejamentoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio() {
		return AgendaPlanejamentoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaMensagemPrimeiroEnvio(String agendaPlanejamentoAlertaMensagemPrimeiroEnvio) {
		AgendaPlanejamentoAlertaMensagemPrimeiroEnvio = agendaPlanejamentoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaMensagemSegundoEnvio() {
		return AgendaPlanejamentoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaMensagemSegundoEnvio(String agendaPlanejamentoAlertaMensagemSegundoEnvio) {
		AgendaPlanejamentoAlertaMensagemSegundoEnvio = agendaPlanejamentoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaPlanejamentoAlertaMensagemTerceiroEnvio() {
		return AgendaPlanejamentoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaMensagemTerceiroEnvio(String agendaPlanejamentoAlertaMensagemTerceiroEnvio) {
		AgendaPlanejamentoAlertaMensagemTerceiroEnvio = agendaPlanejamentoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaDiaPrimeiroEnvio() {
		return AgendaPlanejamentoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaDiaPrimeiroEnvio(String agendaPlanejamentoAlertaDiaPrimeiroEnvio) {
		AgendaPlanejamentoAlertaDiaPrimeiroEnvio = agendaPlanejamentoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoAlertaDiaSegundoEnvio() {
		return AgendaPlanejamentoAlertaDiaSegundoEnvio;
	}

	public void setAgendaPlanejamentoAlertaDiaSegundoEnvio(String agendaPlanejamentoAlertaDiaSegundoEnvio) {
		AgendaPlanejamentoAlertaDiaSegundoEnvio = agendaPlanejamentoAlertaDiaSegundoEnvio;
	}

	public String getAgendaPlanejamentoAlertaDiaTerceiroEnvio() {
		return AgendaPlanejamentoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoAlertaDiaTerceiroEnvio(String agendaPlanejamentoAlertaDiaTerceiroEnvio) {
		AgendaPlanejamentoAlertaDiaTerceiroEnvio = agendaPlanejamentoAlertaDiaTerceiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoCobrancaPrimeiroEnvio() {
		return agendaPlanejamentoCobrancaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaPrimeiroEnvio(AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaPrimeiroEnvio) {
		this.agendaPlanejamentoCobrancaPrimeiroEnvio = agendaPlanejamentoCobrancaPrimeiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoCobrancaSegundoEnvio() {
		return agendaPlanejamentoCobrancaSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSegundoEnvio(AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSegundoEnvio) {
		this.agendaPlanejamentoCobrancaSegundoEnvio = agendaPlanejamentoCobrancaSegundoEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoCobrancaTerceiroEnvio() {
		return agendaPlanejamentoCobrancaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaTerceiroEnvio(AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaTerceiroEnvio) {
		this.agendaPlanejamentoCobrancaTerceiroEnvio = agendaPlanejamentoCobrancaTerceiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio() {
		return agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio(
			AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio = agendaPlanejamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoCobrancaSuperiorSegundoEnvio() {
		return agendaPlanejamentoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSuperiorSegundoEnvio(
			AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSuperiorSegundoEnvio) {
		this.agendaPlanejamentoCobrancaSuperiorSegundoEnvio = agendaPlanejamentoCobrancaSuperiorSegundoEnvio;
	}

	public AgendaPlanejamentoEnvio getAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio() {
		return agendaPlanejamentoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio(
			AgendaPlanejamentoEnvio agendaPlanejamentoCobrancaSuperiorTerceiroEnvio) {
		this.agendaPlanejamentoCobrancaSuperiorTerceiroEnvio = agendaPlanejamentoCobrancaSuperiorTerceiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio() {
		return AgendaPlanejamentoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio(String agendaPlanejamentoCobrancaMensagemPrimeiroEnvio) {
		AgendaPlanejamentoCobrancaMensagemPrimeiroEnvio = agendaPlanejamentoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaMensagemSegundoEnvio() {
		return AgendaPlanejamentoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaMensagemSegundoEnvio(String agendaPlanejamentoCobrancaMensagemSegundoEnvio) {
		AgendaPlanejamentoCobrancaMensagemSegundoEnvio = agendaPlanejamentoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio() {
		return AgendaPlanejamentoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaMensagemTerceiroEnvio(String agendaPlanejamentoCobrancaMensagemTerceiroEnvio) {
		AgendaPlanejamentoCobrancaMensagemTerceiroEnvio = agendaPlanejamentoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaDiaPrimeiroEnvio() {
		return AgendaPlanejamentoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaDiaPrimeiroEnvio(String agendaPlanejamentoCobrancaDiaPrimeiroEnvio) {
		AgendaPlanejamentoCobrancaDiaPrimeiroEnvio = agendaPlanejamentoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaPlanejamentoCobrancaDiaSegundoEnvio() {
		return AgendaPlanejamentoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaPlanejamentoCobrancaDiaSegundoEnvio(String agendaPlanejamentoCobrancaDiaSegundoEnvio) {
		AgendaPlanejamentoCobrancaDiaSegundoEnvio = agendaPlanejamentoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaPlanejamentoCobrancaDiaTerceiroEnvio() {
		return AgendaPlanejamentoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaPlanejamentoCobrancaDiaTerceiroEnvio(String agendaPlanejamentoCobrancaDiaTerceiroEnvio) {
		AgendaPlanejamentoCobrancaDiaTerceiroEnvio = agendaPlanejamentoCobrancaDiaTerceiroEnvio;
	}

}
