package br.com.j4business.saga.processoatividade.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoAtividadeForm {

	private long processoAtividadePK;
	private long processoPK;
	private long atividadePK;
	
    @NotEmpty(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotEmpty(message = "Nome da Atividade é uma informação obrigatória.")
	@NotNull
	private String atividadeNome;
	
    @NotEmpty(message = "Sequência é uma informação obrigatória.")
	private String processoAtividadeSequencia;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoAtividadeDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoAtividadeStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoAtividadeResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
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
