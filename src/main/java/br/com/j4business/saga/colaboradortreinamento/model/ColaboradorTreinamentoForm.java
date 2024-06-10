package br.com.j4business.saga.colaboradortreinamento.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorTreinamentoForm {

	private long colaboradorTreinamentoPK;
	private long treinamentoPK;
	private long colaboradorPK;
	
    @NotBlank(message = "Nome do treinamento é uma informação obrigatória.")
	@NotNull
	private String treinamentoNome;
	
    @NotBlank(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorTreinamentoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorTreinamentoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String colaboradorTreinamentoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorTreinamentoMotivoOperacao;

	public ColaboradorTreinamentoForm() {
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

	public long getColaboradorTreinamentoPK() {
		return colaboradorTreinamentoPK;
	}

	public void setColaboradorTreinamentoPK(long colaboradorTreinamentoPK) {
		this.colaboradorTreinamentoPK = colaboradorTreinamentoPK;
	}

	public long getColaboradorPK() {
		return colaboradorPK;
	}

	public void setColaboradorPK(long colaboradorPK) {
		this.colaboradorPK = colaboradorPK;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public String getColaboradorTreinamentoDataCriacao() {
		return colaboradorTreinamentoDataCriacao;
	}

	public void setColaboradorTreinamentoDataCriacao(String colaboradorTreinamentoDataCriacao) {
		this.colaboradorTreinamentoDataCriacao = colaboradorTreinamentoDataCriacao;
	}

	public AtributoStatus getColaboradorTreinamentoStatus() {
		return colaboradorTreinamentoStatus;
	}

	public void setColaboradorTreinamentoStatus(AtributoStatus colaboradorTreinamentoStatus) {
		this.colaboradorTreinamentoStatus = colaboradorTreinamentoStatus;
	}

	public String getColaboradorTreinamentoResponsavel() {
		return colaboradorTreinamentoResponsavel;
	}

	public void setColaboradorTreinamentoResponsavel(String colaboradorTreinamentoResponsavel) {
		this.colaboradorTreinamentoResponsavel = colaboradorTreinamentoResponsavel;
	}

	public String getColaboradorTreinamentoMotivoOperacao() {
		return colaboradorTreinamentoMotivoOperacao;
	}

	public void setColaboradorTreinamentoMotivoOperacao(String colaboradorTreinamentoMotivoOperacao) {
		this.colaboradorTreinamentoMotivoOperacao = colaboradorTreinamentoMotivoOperacao;
	}

}
