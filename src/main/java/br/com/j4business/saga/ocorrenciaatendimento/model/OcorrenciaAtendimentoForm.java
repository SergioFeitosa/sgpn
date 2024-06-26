package br.com.j4business.saga.ocorrenciaatendimento.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class OcorrenciaAtendimentoForm {

	private long ocorrenciaAtendimentoPK;
	private long atendimentoPK;
	private long ocorrenciaPK;
	
    @NotBlank(message = "Nome do atendimento é uma informação obrigatória.")
	@NotNull
	private String atendimentoNome;
	
    @NotBlank(message = "Nome da Ocorrencia é uma informação obrigatória.")
	@NotNull
	private String ocorrenciaNome;
	
    @NotBlank(message = "Sequência é uma informação obrigatória.")
	private String ocorrenciaAtendimentoSequencia;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  ocorrenciaAtendimentoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus ocorrenciaAtendimentoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String ocorrenciaAtendimentoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String ocorrenciaAtendimentoMotivoOperacao;

	public OcorrenciaAtendimentoForm() {
		super();
	}

	public long getAtendimentoPK() {
		return atendimentoPK;
	}

	public void setAtendimentoPK(long atendimentoPK) {
		this.atendimentoPK = atendimentoPK;
	}

	public String getAtendimentoNome() {
		return atendimentoNome;
	}

	public void setAtendimentoNome(String atendimentoNome) {
		this.atendimentoNome = atendimentoNome;
	}

	public long getOcorrenciaAtendimentoPK() {
		return ocorrenciaAtendimentoPK;
	}

	public void setOcorrenciaAtendimentoPK(long ocorrenciaAtendimentoPK) {
		this.ocorrenciaAtendimentoPK = ocorrenciaAtendimentoPK;
	}

	public long getOcorrenciaPK() {
		return ocorrenciaPK;
	}

	public void setOcorrenciaPK(long ocorrenciaPK) {
		this.ocorrenciaPK = ocorrenciaPK;
	}

	public String getOcorrenciaNome() {
		return ocorrenciaNome;
	}

	public void setOcorrenciaNome(String ocorrenciaNome) {
		this.ocorrenciaNome = ocorrenciaNome;
	}

	public String getOcorrenciaAtendimentoSequencia() {
		return ocorrenciaAtendimentoSequencia;
	}

	public void setOcorrenciaAtendimentoSequencia(String ocorrenciaAtendimentoSequencia) {
		this.ocorrenciaAtendimentoSequencia = ocorrenciaAtendimentoSequencia;
	}

	public String getOcorrenciaAtendimentoDataCriacao() {
		return ocorrenciaAtendimentoDataCriacao;
	}

	public void setOcorrenciaAtendimentoDataCriacao(String ocorrenciaAtendimentoDataCriacao) {
		this.ocorrenciaAtendimentoDataCriacao = ocorrenciaAtendimentoDataCriacao;
	}

	public AtributoStatus getOcorrenciaAtendimentoStatus() {
		return ocorrenciaAtendimentoStatus;
	}

	public void setOcorrenciaAtendimentoStatus(AtributoStatus ocorrenciaAtendimentoStatus) {
		this.ocorrenciaAtendimentoStatus = ocorrenciaAtendimentoStatus;
	}

	public String getOcorrenciaAtendimentoResponsavel() {
		return ocorrenciaAtendimentoResponsavel;
	}

	public void setOcorrenciaAtendimentoResponsavel(String ocorrenciaAtendimentoResponsavel) {
		this.ocorrenciaAtendimentoResponsavel = ocorrenciaAtendimentoResponsavel;
	}

	public String getOcorrenciaAtendimentoMotivoOperacao() {
		return ocorrenciaAtendimentoMotivoOperacao;
	}

	public void setOcorrenciaAtendimentoMotivoOperacao(String ocorrenciaAtendimentoMotivoOperacao) {
		this.ocorrenciaAtendimentoMotivoOperacao = ocorrenciaAtendimentoMotivoOperacao;
	}

	
}
