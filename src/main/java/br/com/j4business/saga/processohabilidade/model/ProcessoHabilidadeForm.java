package br.com.j4business.saga.processohabilidade.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoHabilidadeForm {

	private long processoHabilidadePK;
	private long habilidadePK;
	private long processoPK;
	
    @NotEmpty(message = "Nome do habilidade é uma informação obrigatória.")
	@NotNull
	private String habilidadeNome;
	
    @NotEmpty(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoHabilidadeDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoHabilidadeStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoHabilidadeResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoHabilidadeMotivoOperacao;

	public ProcessoHabilidadeForm() {
		super();
	}

	public long getHabilidadePK() {
		return habilidadePK;
	}

	public void setHabilidadePK(long habilidadePK) {
		this.habilidadePK = habilidadePK;
	}

	public String getHabilidadeNome() {
		return habilidadeNome;
	}

	public void setHabilidadeNome(String habilidadeNome) {
		this.habilidadeNome = habilidadeNome;
	}

	public long getProcessoHabilidadePK() {
		return processoHabilidadePK;
	}

	public void setProcessoHabilidadePK(long processoHabilidadePK) {
		this.processoHabilidadePK = processoHabilidadePK;
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

	public String getProcessoHabilidadeDataCriacao() {
		return processoHabilidadeDataCriacao;
	}

	public void setProcessoHabilidadeDataCriacao(String processoHabilidadeDataCriacao) {
		this.processoHabilidadeDataCriacao = processoHabilidadeDataCriacao;
	}

	public AtributoStatus getProcessoHabilidadeStatus() {
		return processoHabilidadeStatus;
	}

	public void setProcessoHabilidadeStatus(AtributoStatus processoHabilidadeStatus) {
		this.processoHabilidadeStatus = processoHabilidadeStatus;
	}

	public String getProcessoHabilidadeResponsavel() {
		return processoHabilidadeResponsavel;
	}

	public void setProcessoHabilidadeResponsavel(String processoHabilidadeResponsavel) {
		this.processoHabilidadeResponsavel = processoHabilidadeResponsavel;
	}

	public String getProcessoHabilidadeMotivoOperacao() {
		return processoHabilidadeMotivoOperacao;
	}

	public void setProcessoHabilidadeMotivoOperacao(String processoHabilidadeMotivoOperacao) {
		this.processoHabilidadeMotivoOperacao = processoHabilidadeMotivoOperacao;
	}

}
