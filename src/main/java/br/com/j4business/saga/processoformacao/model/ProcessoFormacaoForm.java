package br.com.j4business.saga.processoformacao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoFormacaoForm {

	private long processoFormacaoPK;
	private long formacaoPK;
	private long processoPK;
	
    @NotBlank(message = "Nome do formacao é uma informação obrigatória.")
	@NotNull
	private String formacaoNome;
	
    @NotBlank(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoFormacaoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoFormacaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoFormacaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoFormacaoMotivoOperacao;

	public ProcessoFormacaoForm() {
		super();
	}

	public long getFormacaoPK() {
		return formacaoPK;
	}

	public void setFormacaoPK(long formacaoPK) {
		this.formacaoPK = formacaoPK;
	}

	public String getFormacaoNome() {
		return formacaoNome;
	}

	public void setFormacaoNome(String formacaoNome) {
		this.formacaoNome = formacaoNome;
	}

	public long getProcessoFormacaoPK() {
		return processoFormacaoPK;
	}

	public void setProcessoFormacaoPK(long processoFormacaoPK) {
		this.processoFormacaoPK = processoFormacaoPK;
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

	public String getProcessoFormacaoDataCriacao() {
		return processoFormacaoDataCriacao;
	}

	public void setProcessoFormacaoDataCriacao(String processoFormacaoDataCriacao) {
		this.processoFormacaoDataCriacao = processoFormacaoDataCriacao;
	}

	public AtributoStatus getProcessoFormacaoStatus() {
		return processoFormacaoStatus;
	}

	public void setProcessoFormacaoStatus(AtributoStatus processoFormacaoStatus) {
		this.processoFormacaoStatus = processoFormacaoStatus;
	}

	public String getProcessoFormacaoResponsavel() {
		return processoFormacaoResponsavel;
	}

	public void setProcessoFormacaoResponsavel(String processoFormacaoResponsavel) {
		this.processoFormacaoResponsavel = processoFormacaoResponsavel;
	}

	public String getProcessoFormacaoMotivoOperacao() {
		return processoFormacaoMotivoOperacao;
	}

	public void setProcessoFormacaoMotivoOperacao(String processoFormacaoMotivoOperacao) {
		this.processoFormacaoMotivoOperacao = processoFormacaoMotivoOperacao;
	}

}
