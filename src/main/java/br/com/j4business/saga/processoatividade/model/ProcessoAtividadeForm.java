package br.com.j4business.saga.processoatividade.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoAtividadeForm {

	private long processoAtividadePK;
	private long processoPK;
	private long atividadePK;
	
    @NotBlank(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotBlank(message = "Nome da Atividade é uma informação obrigatória.")
	@NotNull
	private String atividadeNome;
	
    @NotBlank(message = "Sequência é uma informação obrigatória.")
	private String processoAtividadeSequencia;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoAtividadeDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoAtividadeStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoAtividadeResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoAtividadeMotivoOperacao;

	public ProcessoAtividadeForm() {
		super();
	}

	public long getProcessoPK() {
		return processoPK;
	}

	public void setProcessoPK(long processoPK) {
		this.processoPK = processoPK;
	}

	public String getProcessoNome() {
		return processoNome;
	}

	public void setProcessoNome(String processoNome) {
		this.processoNome = processoNome;
	}

	public long getProcessoAtividadePK() {
		return processoAtividadePK;
	}

	public void setProcessoAtividadePK(long processoAtividadePK) {
		this.processoAtividadePK = processoAtividadePK;
	}

	public long getAtividadePK() {
		return atividadePK;
	}

	public void setAtividadePK(long atividadePK) {
		this.atividadePK = atividadePK;
	}

	public String getAtividadeNome() {
		return atividadeNome;
	}

	public void setAtividadeNome(String atividadeNome) {
		this.atividadeNome = atividadeNome;
	}

	public String getProcessoAtividadeSequencia() {
		return processoAtividadeSequencia;
	}

	public void setProcessoAtividadeSequencia(String processoAtividadeSequencia) {
		this.processoAtividadeSequencia = processoAtividadeSequencia;
	}

	public String getProcessoAtividadeDataCriacao() {
		return processoAtividadeDataCriacao;
	}

	public void setProcessoAtividadeDataCriacao(String processoAtividadeDataCriacao) {
		this.processoAtividadeDataCriacao = processoAtividadeDataCriacao;
	}

	public AtributoStatus getProcessoAtividadeStatus() {
		return processoAtividadeStatus;
	}

	public void setProcessoAtividadeStatus(AtributoStatus processoAtividadeStatus) {
		this.processoAtividadeStatus = processoAtividadeStatus;
	}

	public String getProcessoAtividadeResponsavel() {
		return processoAtividadeResponsavel;
	}

	public void setProcessoAtividadeResponsavel(String processoAtividadeResponsavel) {
		this.processoAtividadeResponsavel = processoAtividadeResponsavel;
	}

	public String getProcessoAtividadeMotivoOperacao() {
		return processoAtividadeMotivoOperacao;
	}

	public void setProcessoAtividadeMotivoOperacao(String processoAtividadeMotivoOperacao) {
		this.processoAtividadeMotivoOperacao = processoAtividadeMotivoOperacao;
	}

}
