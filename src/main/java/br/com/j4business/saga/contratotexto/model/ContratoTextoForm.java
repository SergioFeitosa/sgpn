package br.com.j4business.saga.contratotexto.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ContratoTextoForm {

	private long contratoTextoPK;
	private long textoPK;
	private long contratoPK;
	
    @NotBlank(message = "Nome do texto é uma informação obrigatória.")
	@NotNull
	private String textoNome;
	
    @NotBlank(message = "Nome da Contrato é uma informação obrigatória.")
	@NotNull
	private String contratoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  contratoTextoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus contratoTextoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String contratoTextoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String contratoTextoMotivoOperacao;

	public ContratoTextoForm() {
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

	public long getContratoTextoPK() {
		return contratoTextoPK;
	}

	public void setContratoTextoPK(long contratoTextoPK) {
		this.contratoTextoPK = contratoTextoPK;
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

	public String getContratoTextoDataCriacao() {
		return contratoTextoDataCriacao;
	}

	public void setContratoTextoDataCriacao(String contratoTextoDataCriacao) {
		this.contratoTextoDataCriacao = contratoTextoDataCriacao;
	}

	public AtributoStatus getContratoTextoStatus() {
		return contratoTextoStatus;
	}

	public void setContratoTextoStatus(AtributoStatus contratoTextoStatus) {
		this.contratoTextoStatus = contratoTextoStatus;
	}

	public String getContratoTextoResponsavel() {
		return contratoTextoResponsavel;
	}

	public void setContratoTextoResponsavel(String contratoTextoResponsavel) {
		this.contratoTextoResponsavel = contratoTextoResponsavel;
	}

	public String getContratoTextoMotivoOperacao() {
		return contratoTextoMotivoOperacao;
	}

	public void setContratoTextoMotivoOperacao(String contratoTextoMotivoOperacao) {
		this.contratoTextoMotivoOperacao = contratoTextoMotivoOperacao;
	}

}
