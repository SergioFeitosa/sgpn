package br.com.j4business.saga.agendatreinamento.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.agendatreinamento.enumeration.AgendaTreinamentoEnvio;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AgendaTreinamentoForm {

	private long agendaTreinamentoPK;
	private long treinamentoPK;
	private long agendaPK;
	
    @NotEmpty(message = "Nome do treinamento é uma informação obrigatória.")
	@NotNull(message = "Nome do treinamento é uma informação obrigatória.")
	private String treinamentoNome;
	
    @NotEmpty(message = "Nome da Agenda é uma informação obrigatória.")
	@NotNull(message = "Nome da Agenda é uma informação obrigatória.")
	private String agendaNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  agendaTreinamentoDataCriacao;

	@NotNull(message = "Status da Agenda/Treinamento é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus agendaTreinamentoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável pela Agenda/Treinamento é uma informação obrigatória.")
	private String agendaTreinamentoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String agendaTreinamentoMotivoOperacao;

	@NotNull(message = "Opção de Primeiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoAlertaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoAlertaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoAlertaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoAlertaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoAlertaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoAlertaSuperiorTerceiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaTreinamentoAlertaMensagemPrimeiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaTreinamentoAlertaMensagemSegundoEnvio;

    @NotEmpty(message = "Texto da Mensagem do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaTreinamentoAlertaMensagemTerceiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaTreinamentoAlertaDiaPrimeiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaTreinamentoAlertaDiaSegundoEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaTreinamentoAlertaDiaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoCobrancaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoCobrancaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoCobrancaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoCobrancaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoCobrancaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaTreinamentoEnvio agendaTreinamentoCobrancaSuperiorTerceiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaTreinamentoCobrancaMensagemPrimeiroEnvio;

    @NotEmpty(message = "Texto da Mensagem do Segundo Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaTreinamentoCobrancaMensagemSegundoEnvio;

    @NotEmpty(message = "Texto da Mensagem do Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaTreinamentoCobrancaMensagemTerceiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Primeiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaTreinamentoCobrancaDiaPrimeiroEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Segundo Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaTreinamentoCobrancaDiaSegundoEnvio;

    @NotEmpty(message = "Nº de dias de antecedência do Terceiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaTreinamentoCobrancaDiaTerceiroEnvio;

	
    
	public AgendaTreinamentoForm() {
		super();
	}

	public long getTreinamentoPK() {
		return treinamentoPK;
	}

	public void setTreinamentoPK(long treinamentoPK) {
		this.treinamentoPK = treinamentoPK;
	}

	public String getTreinamentoNome() {
		return treinamentoNome;
	}

	public void setTreinamentoNome(String treinamentoNome) {
		this.treinamentoNome = treinamentoNome;
	}

	public long getAgendaTreinamentoPK() {
		return agendaTreinamentoPK;
	}

	public void setAgendaTreinamentoPK(long agendaTreinamentoPK) {
		this.agendaTreinamentoPK = agendaTreinamentoPK;
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

	public String getAgendaTreinamentoDataCriacao() {
		return agendaTreinamentoDataCriacao;
	}

	public void setAgendaTreinamentoDataCriacao(String agendaTreinamentoDataCriacao) {
		this.agendaTreinamentoDataCriacao = agendaTreinamentoDataCriacao;
	}

	public AtributoStatus getAgendaTreinamentoStatus() {
		return agendaTreinamentoStatus;
	}

	public void setAgendaTreinamentoStatus(AtributoStatus agendaTreinamentoStatus) {
		this.agendaTreinamentoStatus = agendaTreinamentoStatus;
	}

	public String getAgendaTreinamentoResponsavel() {
		return agendaTreinamentoResponsavel;
	}

	public void setAgendaTreinamentoResponsavel(String agendaTreinamentoResponsavel) {
		this.agendaTreinamentoResponsavel = agendaTreinamentoResponsavel;
	}

	public String getAgendaTreinamentoMotivoOperacao() {
		return agendaTreinamentoMotivoOperacao;
	}

	public void setAgendaTreinamentoMotivoOperacao(String agendaTreinamentoMotivoOperacao) {
		this.agendaTreinamentoMotivoOperacao = agendaTreinamentoMotivoOperacao;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoAlertaPrimeiroEnvio() {
		return agendaTreinamentoAlertaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaPrimeiroEnvio(AgendaTreinamentoEnvio agendaTreinamentoAlertaPrimeiroEnvio) {
		this.agendaTreinamentoAlertaPrimeiroEnvio = agendaTreinamentoAlertaPrimeiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoAlertaSegundoEnvio() {
		return agendaTreinamentoAlertaSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaSegundoEnvio(AgendaTreinamentoEnvio agendaTreinamentoAlertaSegundoEnvio) {
		this.agendaTreinamentoAlertaSegundoEnvio = agendaTreinamentoAlertaSegundoEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoAlertaTerceiroEnvio() {
		return agendaTreinamentoAlertaTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaTerceiroEnvio(AgendaTreinamentoEnvio agendaTreinamentoAlertaTerceiroEnvio) {
		this.agendaTreinamentoAlertaTerceiroEnvio = agendaTreinamentoAlertaTerceiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoAlertaSuperiorPrimeiroEnvio() {
		return agendaTreinamentoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaSuperiorPrimeiroEnvio(
			AgendaTreinamentoEnvio agendaTreinamentoAlertaSuperiorPrimeiroEnvio) {
		this.agendaTreinamentoAlertaSuperiorPrimeiroEnvio = agendaTreinamentoAlertaSuperiorPrimeiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoAlertaSuperiorSegundoEnvio() {
		return agendaTreinamentoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaSuperiorSegundoEnvio(
			AgendaTreinamentoEnvio agendaTreinamentoAlertaSuperiorSegundoEnvio) {
		this.agendaTreinamentoAlertaSuperiorSegundoEnvio = agendaTreinamentoAlertaSuperiorSegundoEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoAlertaSuperiorTerceiroEnvio() {
		return agendaTreinamentoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaSuperiorTerceiroEnvio(
			AgendaTreinamentoEnvio agendaTreinamentoAlertaSuperiorTerceiroEnvio) {
		this.agendaTreinamentoAlertaSuperiorTerceiroEnvio = agendaTreinamentoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaTreinamentoAlertaMensagemPrimeiroEnvio() {
		return AgendaTreinamentoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaMensagemPrimeiroEnvio(String agendaTreinamentoAlertaMensagemPrimeiroEnvio) {
		AgendaTreinamentoAlertaMensagemPrimeiroEnvio = agendaTreinamentoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaTreinamentoAlertaMensagemSegundoEnvio() {
		return AgendaTreinamentoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaMensagemSegundoEnvio(String agendaTreinamentoAlertaMensagemSegundoEnvio) {
		AgendaTreinamentoAlertaMensagemSegundoEnvio = agendaTreinamentoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaTreinamentoAlertaMensagemTerceiroEnvio() {
		return AgendaTreinamentoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaMensagemTerceiroEnvio(String agendaTreinamentoAlertaMensagemTerceiroEnvio) {
		AgendaTreinamentoAlertaMensagemTerceiroEnvio = agendaTreinamentoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaTreinamentoAlertaDiaPrimeiroEnvio() {
		return AgendaTreinamentoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoAlertaDiaPrimeiroEnvio(String agendaTreinamentoAlertaDiaPrimeiroEnvio) {
		AgendaTreinamentoAlertaDiaPrimeiroEnvio = agendaTreinamentoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaTreinamentoAlertaDiaSegundoEnvio() {
		return AgendaTreinamentoAlertaDiaSegundoEnvio;
	}

	public void setAgendaTreinamentoAlertaDiaSegundoEnvio(String agendaTreinamentoAlertaDiaSegundoEnvio) {
		AgendaTreinamentoAlertaDiaSegundoEnvio = agendaTreinamentoAlertaDiaSegundoEnvio;
	}

	public String getAgendaTreinamentoAlertaDiaTerceiroEnvio() {
		return AgendaTreinamentoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaTreinamentoAlertaDiaTerceiroEnvio(String agendaTreinamentoAlertaDiaTerceiroEnvio) {
		AgendaTreinamentoAlertaDiaTerceiroEnvio = agendaTreinamentoAlertaDiaTerceiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoCobrancaPrimeiroEnvio() {
		return agendaTreinamentoCobrancaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaPrimeiroEnvio(AgendaTreinamentoEnvio agendaTreinamentoCobrancaPrimeiroEnvio) {
		this.agendaTreinamentoCobrancaPrimeiroEnvio = agendaTreinamentoCobrancaPrimeiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoCobrancaSegundoEnvio() {
		return agendaTreinamentoCobrancaSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaSegundoEnvio(AgendaTreinamentoEnvio agendaTreinamentoCobrancaSegundoEnvio) {
		this.agendaTreinamentoCobrancaSegundoEnvio = agendaTreinamentoCobrancaSegundoEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoCobrancaTerceiroEnvio() {
		return agendaTreinamentoCobrancaTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaTerceiroEnvio(AgendaTreinamentoEnvio agendaTreinamentoCobrancaTerceiroEnvio) {
		this.agendaTreinamentoCobrancaTerceiroEnvio = agendaTreinamentoCobrancaTerceiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio() {
		return agendaTreinamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio(
			AgendaTreinamentoEnvio agendaTreinamentoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaTreinamentoCobrancaSuperiorPrimeiroEnvio = agendaTreinamentoCobrancaSuperiorPrimeiroEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoCobrancaSuperiorSegundoEnvio() {
		return agendaTreinamentoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaSuperiorSegundoEnvio(
			AgendaTreinamentoEnvio agendaTreinamentoCobrancaSuperiorSegundoEnvio) {
		this.agendaTreinamentoCobrancaSuperiorSegundoEnvio = agendaTreinamentoCobrancaSuperiorSegundoEnvio;
	}

	public AgendaTreinamentoEnvio getAgendaTreinamentoCobrancaSuperiorTerceiroEnvio() {
		return agendaTreinamentoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaSuperiorTerceiroEnvio(
			AgendaTreinamentoEnvio agendaTreinamentoCobrancaSuperiorTerceiroEnvio) {
		this.agendaTreinamentoCobrancaSuperiorTerceiroEnvio = agendaTreinamentoCobrancaSuperiorTerceiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio() {
		return AgendaTreinamentoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaMensagemPrimeiroEnvio(String agendaTreinamentoCobrancaMensagemPrimeiroEnvio) {
		AgendaTreinamentoCobrancaMensagemPrimeiroEnvio = agendaTreinamentoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaMensagemSegundoEnvio() {
		return AgendaTreinamentoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaMensagemSegundoEnvio(String agendaTreinamentoCobrancaMensagemSegundoEnvio) {
		AgendaTreinamentoCobrancaMensagemSegundoEnvio = agendaTreinamentoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaTreinamentoCobrancaMensagemTerceiroEnvio() {
		return AgendaTreinamentoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaMensagemTerceiroEnvio(String agendaTreinamentoCobrancaMensagemTerceiroEnvio) {
		AgendaTreinamentoCobrancaMensagemTerceiroEnvio = agendaTreinamentoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaDiaPrimeiroEnvio() {
		return AgendaTreinamentoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaDiaPrimeiroEnvio(String agendaTreinamentoCobrancaDiaPrimeiroEnvio) {
		AgendaTreinamentoCobrancaDiaPrimeiroEnvio = agendaTreinamentoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaTreinamentoCobrancaDiaSegundoEnvio() {
		return AgendaTreinamentoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaTreinamentoCobrancaDiaSegundoEnvio(String agendaTreinamentoCobrancaDiaSegundoEnvio) {
		AgendaTreinamentoCobrancaDiaSegundoEnvio = agendaTreinamentoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaTreinamentoCobrancaDiaTerceiroEnvio() {
		return AgendaTreinamentoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaTreinamentoCobrancaDiaTerceiroEnvio(String agendaTreinamentoCobrancaDiaTerceiroEnvio) {
		AgendaTreinamentoCobrancaDiaTerceiroEnvio = agendaTreinamentoCobrancaDiaTerceiroEnvio;
	}

}
