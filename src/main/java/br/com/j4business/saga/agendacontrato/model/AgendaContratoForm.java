package br.com.j4business.saga.agendacontrato.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.agendacontrato.enumeration.AgendaContratoEnvio;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AgendaContratoForm {

	private long agendaContratoPK;
	private long contratoPK;
	private long agendaPK;
	
    @NotBlank(message = "Nome do contrato é uma informação obrigatória.")
	@NotNull(message = "Nome do contrato é uma informação obrigatória.")
	private String contratoNome;
	
    @NotBlank(message = "Nome da Agenda é uma informação obrigatória.")
	@NotNull(message = "Nome da Agenda é uma informação obrigatória.")
	private String agendaNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  agendaContratoDataCriacao;

	@NotNull(message = "Status da Agenda/Contrato é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus agendaContratoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável pela Agenda/Contrato é uma informação obrigatória.")
	private String agendaContratoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String agendaContratoMotivoOperacao;

	@NotNull(message = "Opção de Primeiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoAlertaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoAlertaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoAlertaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoAlertaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoAlertaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Alerta para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoAlertaSuperiorTerceiroEnvio;

    @NotBlank(message = "Texto da Mensagem do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaContratoAlertaMensagemPrimeiroEnvio;

    @NotBlank(message = "Texto da Mensagem do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaContratoAlertaMensagemSegundoEnvio;

    @NotBlank(message = "Texto da Mensagem do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Alerta não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaContratoAlertaMensagemTerceiroEnvio;

    @NotBlank(message = "Nº de dias de antecedência do Primeiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaContratoAlertaDiaPrimeiroEnvio;

    @NotBlank(message = "Nº de dias de antecedência do Segundo Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaContratoAlertaDiaSegundoEnvio;

    @NotBlank(message = "Nº de dias de antecedência do Terceiro Envio de Alerta é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Alerta não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaContratoAlertaDiaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoCobrancaPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoCobrancaSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoCobrancaTerceiroEnvio;

	@NotNull(message = "Opção de Primeiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoCobrancaSuperiorPrimeiroEnvio;

	@NotNull(message = "Opção de Segundo Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoCobrancaSuperiorSegundoEnvio;

	@NotNull(message = "Opção de Terceiro Envio de Cobrança para Superior é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AgendaContratoEnvio agendaContratoCobrancaSuperiorTerceiroEnvio;

    @NotBlank(message = "Texto da Mensagem do Primeiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Primeiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaContratoCobrancaMensagemPrimeiroEnvio;

    @NotBlank(message = "Texto da Mensagem do Segundo Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Segundo Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaContratoCobrancaMensagemSegundoEnvio;

    @NotBlank(message = "Texto da Mensagem do Terceiro Envio de Cobrança é uma informação obrigatória.")
	@Size(min = 5,max = 50, message = "Texto da Mensagem do Terceiro Envio de Cobrança não pode ter menos que 5 e mais que 50 caracteres")
	private String AgendaContratoCobrancaMensagemTerceiroEnvio;

    @NotBlank(message = "Nº de dias de antecedência do Primeiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Primeiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaContratoCobrancaDiaPrimeiroEnvio;

    @NotBlank(message = "Nº de dias de antecedência do Segundo Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Segundo Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaContratoCobrancaDiaSegundoEnvio;

    @NotBlank(message = "Nº de dias de antecedência do Terceiro Envio é uma informação obrigatória.")
	@Size(min = 1,max = 3, message = "Nº de dias de antecedência do Terceiro Envio de Cobrança não pode ter menos que 1 e mais que 3 caracteres")
	private String AgendaContratoCobrancaDiaTerceiroEnvio;

	
    
	public AgendaContratoForm() {
		super();
	}

	public long getContratoPK() {
		return contratoPK;
	}

	public void setContratoPK(long contratoPK) {
		this.contratoPK = contratoPK;
	}

	public String getContratoNome() {
		return contratoNome;
	}

	public void setContratoNome(String contratoNome) {
		this.contratoNome = contratoNome;
	}

	public long getAgendaContratoPK() {
		return agendaContratoPK;
	}

	public void setAgendaContratoPK(long agendaContratoPK) {
		this.agendaContratoPK = agendaContratoPK;
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

	public String getAgendaContratoDataCriacao() {
		return agendaContratoDataCriacao;
	}

	public void setAgendaContratoDataCriacao(String agendaContratoDataCriacao) {
		this.agendaContratoDataCriacao = agendaContratoDataCriacao;
	}

	public AtributoStatus getAgendaContratoStatus() {
		return agendaContratoStatus;
	}

	public void setAgendaContratoStatus(AtributoStatus agendaContratoStatus) {
		this.agendaContratoStatus = agendaContratoStatus;
	}

	public String getAgendaContratoResponsavel() {
		return agendaContratoResponsavel;
	}

	public void setAgendaContratoResponsavel(String agendaContratoResponsavel) {
		this.agendaContratoResponsavel = agendaContratoResponsavel;
	}

	public String getAgendaContratoMotivoOperacao() {
		return agendaContratoMotivoOperacao;
	}

	public void setAgendaContratoMotivoOperacao(String agendaContratoMotivoOperacao) {
		this.agendaContratoMotivoOperacao = agendaContratoMotivoOperacao;
	}

	public AgendaContratoEnvio getAgendaContratoAlertaPrimeiroEnvio() {
		return agendaContratoAlertaPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaPrimeiroEnvio(AgendaContratoEnvio agendaContratoAlertaPrimeiroEnvio) {
		this.agendaContratoAlertaPrimeiroEnvio = agendaContratoAlertaPrimeiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoAlertaSegundoEnvio() {
		return agendaContratoAlertaSegundoEnvio;
	}

	public void setAgendaContratoAlertaSegundoEnvio(AgendaContratoEnvio agendaContratoAlertaSegundoEnvio) {
		this.agendaContratoAlertaSegundoEnvio = agendaContratoAlertaSegundoEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoAlertaTerceiroEnvio() {
		return agendaContratoAlertaTerceiroEnvio;
	}

	public void setAgendaContratoAlertaTerceiroEnvio(AgendaContratoEnvio agendaContratoAlertaTerceiroEnvio) {
		this.agendaContratoAlertaTerceiroEnvio = agendaContratoAlertaTerceiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoAlertaSuperiorPrimeiroEnvio() {
		return agendaContratoAlertaSuperiorPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaSuperiorPrimeiroEnvio(
			AgendaContratoEnvio agendaContratoAlertaSuperiorPrimeiroEnvio) {
		this.agendaContratoAlertaSuperiorPrimeiroEnvio = agendaContratoAlertaSuperiorPrimeiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoAlertaSuperiorSegundoEnvio() {
		return agendaContratoAlertaSuperiorSegundoEnvio;
	}

	public void setAgendaContratoAlertaSuperiorSegundoEnvio(
			AgendaContratoEnvio agendaContratoAlertaSuperiorSegundoEnvio) {
		this.agendaContratoAlertaSuperiorSegundoEnvio = agendaContratoAlertaSuperiorSegundoEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoAlertaSuperiorTerceiroEnvio() {
		return agendaContratoAlertaSuperiorTerceiroEnvio;
	}

	public void setAgendaContratoAlertaSuperiorTerceiroEnvio(
			AgendaContratoEnvio agendaContratoAlertaSuperiorTerceiroEnvio) {
		this.agendaContratoAlertaSuperiorTerceiroEnvio = agendaContratoAlertaSuperiorTerceiroEnvio;
	}

	public String getAgendaContratoAlertaMensagemPrimeiroEnvio() {
		return AgendaContratoAlertaMensagemPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaMensagemPrimeiroEnvio(String agendaContratoAlertaMensagemPrimeiroEnvio) {
		AgendaContratoAlertaMensagemPrimeiroEnvio = agendaContratoAlertaMensagemPrimeiroEnvio;
	}

	public String getAgendaContratoAlertaMensagemSegundoEnvio() {
		return AgendaContratoAlertaMensagemSegundoEnvio;
	}

	public void setAgendaContratoAlertaMensagemSegundoEnvio(String agendaContratoAlertaMensagemSegundoEnvio) {
		AgendaContratoAlertaMensagemSegundoEnvio = agendaContratoAlertaMensagemSegundoEnvio;
	}

	public String getAgendaContratoAlertaMensagemTerceiroEnvio() {
		return AgendaContratoAlertaMensagemTerceiroEnvio;
	}

	public void setAgendaContratoAlertaMensagemTerceiroEnvio(String agendaContratoAlertaMensagemTerceiroEnvio) {
		AgendaContratoAlertaMensagemTerceiroEnvio = agendaContratoAlertaMensagemTerceiroEnvio;
	}

	public String getAgendaContratoAlertaDiaPrimeiroEnvio() {
		return AgendaContratoAlertaDiaPrimeiroEnvio;
	}

	public void setAgendaContratoAlertaDiaPrimeiroEnvio(String agendaContratoAlertaDiaPrimeiroEnvio) {
		AgendaContratoAlertaDiaPrimeiroEnvio = agendaContratoAlertaDiaPrimeiroEnvio;
	}

	public String getAgendaContratoAlertaDiaSegundoEnvio() {
		return AgendaContratoAlertaDiaSegundoEnvio;
	}

	public void setAgendaContratoAlertaDiaSegundoEnvio(String agendaContratoAlertaDiaSegundoEnvio) {
		AgendaContratoAlertaDiaSegundoEnvio = agendaContratoAlertaDiaSegundoEnvio;
	}

	public String getAgendaContratoAlertaDiaTerceiroEnvio() {
		return AgendaContratoAlertaDiaTerceiroEnvio;
	}

	public void setAgendaContratoAlertaDiaTerceiroEnvio(String agendaContratoAlertaDiaTerceiroEnvio) {
		AgendaContratoAlertaDiaTerceiroEnvio = agendaContratoAlertaDiaTerceiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoCobrancaPrimeiroEnvio() {
		return agendaContratoCobrancaPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaPrimeiroEnvio(AgendaContratoEnvio agendaContratoCobrancaPrimeiroEnvio) {
		this.agendaContratoCobrancaPrimeiroEnvio = agendaContratoCobrancaPrimeiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoCobrancaSegundoEnvio() {
		return agendaContratoCobrancaSegundoEnvio;
	}

	public void setAgendaContratoCobrancaSegundoEnvio(AgendaContratoEnvio agendaContratoCobrancaSegundoEnvio) {
		this.agendaContratoCobrancaSegundoEnvio = agendaContratoCobrancaSegundoEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoCobrancaTerceiroEnvio() {
		return agendaContratoCobrancaTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaTerceiroEnvio(AgendaContratoEnvio agendaContratoCobrancaTerceiroEnvio) {
		this.agendaContratoCobrancaTerceiroEnvio = agendaContratoCobrancaTerceiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoCobrancaSuperiorPrimeiroEnvio() {
		return agendaContratoCobrancaSuperiorPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaSuperiorPrimeiroEnvio(
			AgendaContratoEnvio agendaContratoCobrancaSuperiorPrimeiroEnvio) {
		this.agendaContratoCobrancaSuperiorPrimeiroEnvio = agendaContratoCobrancaSuperiorPrimeiroEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoCobrancaSuperiorSegundoEnvio() {
		return agendaContratoCobrancaSuperiorSegundoEnvio;
	}

	public void setAgendaContratoCobrancaSuperiorSegundoEnvio(
			AgendaContratoEnvio agendaContratoCobrancaSuperiorSegundoEnvio) {
		this.agendaContratoCobrancaSuperiorSegundoEnvio = agendaContratoCobrancaSuperiorSegundoEnvio;
	}

	public AgendaContratoEnvio getAgendaContratoCobrancaSuperiorTerceiroEnvio() {
		return agendaContratoCobrancaSuperiorTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaSuperiorTerceiroEnvio(
			AgendaContratoEnvio agendaContratoCobrancaSuperiorTerceiroEnvio) {
		this.agendaContratoCobrancaSuperiorTerceiroEnvio = agendaContratoCobrancaSuperiorTerceiroEnvio;
	}

	public String getAgendaContratoCobrancaMensagemPrimeiroEnvio() {
		return AgendaContratoCobrancaMensagemPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaMensagemPrimeiroEnvio(String agendaContratoCobrancaMensagemPrimeiroEnvio) {
		AgendaContratoCobrancaMensagemPrimeiroEnvio = agendaContratoCobrancaMensagemPrimeiroEnvio;
	}

	public String getAgendaContratoCobrancaMensagemSegundoEnvio() {
		return AgendaContratoCobrancaMensagemSegundoEnvio;
	}

	public void setAgendaContratoCobrancaMensagemSegundoEnvio(String agendaContratoCobrancaMensagemSegundoEnvio) {
		AgendaContratoCobrancaMensagemSegundoEnvio = agendaContratoCobrancaMensagemSegundoEnvio;
	}

	public String getAgendaContratoCobrancaMensagemTerceiroEnvio() {
		return AgendaContratoCobrancaMensagemTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaMensagemTerceiroEnvio(String agendaContratoCobrancaMensagemTerceiroEnvio) {
		AgendaContratoCobrancaMensagemTerceiroEnvio = agendaContratoCobrancaMensagemTerceiroEnvio;
	}

	public String getAgendaContratoCobrancaDiaPrimeiroEnvio() {
		return AgendaContratoCobrancaDiaPrimeiroEnvio;
	}

	public void setAgendaContratoCobrancaDiaPrimeiroEnvio(String agendaContratoCobrancaDiaPrimeiroEnvio) {
		AgendaContratoCobrancaDiaPrimeiroEnvio = agendaContratoCobrancaDiaPrimeiroEnvio;
	}

	public String getAgendaContratoCobrancaDiaSegundoEnvio() {
		return AgendaContratoCobrancaDiaSegundoEnvio;
	}

	public void setAgendaContratoCobrancaDiaSegundoEnvio(String agendaContratoCobrancaDiaSegundoEnvio) {
		AgendaContratoCobrancaDiaSegundoEnvio = agendaContratoCobrancaDiaSegundoEnvio;
	}

	public String getAgendaContratoCobrancaDiaTerceiroEnvio() {
		return AgendaContratoCobrancaDiaTerceiroEnvio;
	}

	public void setAgendaContratoCobrancaDiaTerceiroEnvio(String agendaContratoCobrancaDiaTerceiroEnvio) {
		AgendaContratoCobrancaDiaTerceiroEnvio = agendaContratoCobrancaDiaTerceiroEnvio;
	}

}
