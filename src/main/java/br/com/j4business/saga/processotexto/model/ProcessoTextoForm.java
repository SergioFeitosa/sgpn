package br.com.j4business.saga.processotexto.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoTextoForm {

	private long processoTextoPK;
	private long textoPK;
	private long processoPK;
	
    @NotEmpty(message = "Nome do texto é uma informação obrigatória.")
	@NotNull
	private String textoNome;
	
    @NotEmpty(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoTextoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoTextoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoTextoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoTextoMotivoOperacao;

	public ProcessoTextoForm() {
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

	public long getProcessoTextoPK() {
		return processoTextoPK;
	}

	public void setProcessoTextoPK(long processoTextoPK) {
		this.processoTextoPK = processoTextoPK;
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

	public String getProcessoTextoDataCriacao() {
		return processoTextoDataCriacao;
	}

	public void setProcessoTextoDataCriacao(String processoTextoDataCriacao) {
		this.processoTextoDataCriacao = processoTextoDataCriacao;
	}

	public AtributoStatus getProcessoTextoStatus() {
		return processoTextoStatus;
	}

	public void setProcessoTextoStatus(AtributoStatus processoTextoStatus) {
		this.processoTextoStatus = processoTextoStatus;
	}

	public String getProcessoTextoResponsavel() {
		return processoTextoResponsavel;
	}

	public void setProcessoTextoResponsavel(String processoTextoResponsavel) {
		this.processoTextoResponsavel = processoTextoResponsavel;
	}

	public String getProcessoTextoMotivoOperacao() {
		return processoTextoMotivoOperacao;
	}

	public void setProcessoTextoMotivoOperacao(String processoTextoMotivoOperacao) {
		this.processoTextoMotivoOperacao = processoTextoMotivoOperacao;
	}

}
