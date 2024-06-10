package br.com.j4business.saga.treinamentotexto.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class TreinamentoTextoForm {

	private long treinamentoTextoPK;
	private long textoPK;
	private long treinamentoPK;
	
    @NotBlank(message = "Nome do texto é uma informação obrigatória.")
	@NotNull
	private String textoNome;
	
    @NotBlank(message = "Nome da Treinamento é uma informação obrigatória.")
	@NotNull
	private String treinamentoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoTextoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus treinamentoTextoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String treinamentoTextoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String treinamentoTextoMotivoOperacao;

	public TreinamentoTextoForm() {
		super();
	}

	public long getTextoPK() {
		return textoPK;
	}

	public void setTextoPK(long textoPK) {
		this.textoPK = textoPK;
	}

	public String getTextoNome() {
		return textoNome;
	}

	public void setTextoNome(String textoNome) {
		this.textoNome = textoNome;
	}

	public long getTreinamentoTextoPK() {
		return treinamentoTextoPK;
	}

	public void setTreinamentoTextoPK(long treinamentoTextoPK) {
		this.treinamentoTextoPK = treinamentoTextoPK;
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

	public String getTreinamentoTextoDataCriacao() {
		return treinamentoTextoDataCriacao;
	}

	public void setTreinamentoTextoDataCriacao(String treinamentoTextoDataCriacao) {
		this.treinamentoTextoDataCriacao = treinamentoTextoDataCriacao;
	}

	public AtributoStatus getTreinamentoTextoStatus() {
		return treinamentoTextoStatus;
	}

	public void setTreinamentoTextoStatus(AtributoStatus treinamentoTextoStatus) {
		this.treinamentoTextoStatus = treinamentoTextoStatus;
	}

	public String getTreinamentoTextoResponsavel() {
		return treinamentoTextoResponsavel;
	}

	public void setTreinamentoTextoResponsavel(String treinamentoTextoResponsavel) {
		this.treinamentoTextoResponsavel = treinamentoTextoResponsavel;
	}

	public String getTreinamentoTextoMotivoOperacao() {
		return treinamentoTextoMotivoOperacao;
	}

	public void setTreinamentoTextoMotivoOperacao(String treinamentoTextoMotivoOperacao) {
		this.treinamentoTextoMotivoOperacao = treinamentoTextoMotivoOperacao;
	}

}
