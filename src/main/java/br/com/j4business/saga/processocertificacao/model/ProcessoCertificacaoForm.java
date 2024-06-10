package br.com.j4business.saga.processocertificacao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoCertificacaoForm {

	private long processoCertificacaoPK;
	private long certificacaoPK;
	private long processoPK;
	
    @NotBlank(message = "Nome do certificacao é uma informação obrigatória.")
	@NotNull
	private String certificacaoNome;
	
    @NotBlank(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoCertificacaoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoCertificacaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoCertificacaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoCertificacaoMotivoOperacao;

	public ProcessoCertificacaoForm() {
		super();
	}

	public long getCertificacaoPK() {
		return certificacaoPK;
	}

	public void setCertificacaoPK(long certificacaoPK) {
		this.certificacaoPK = certificacaoPK;
	}

	public String getCertificacaoNome() {
		return certificacaoNome;
	}

	public void setCertificacaoNome(String certificacaoNome) {
		this.certificacaoNome = certificacaoNome;
	}

	public long getProcessoCertificacaoPK() {
		return processoCertificacaoPK;
	}

	public void setProcessoCertificacaoPK(long processoCertificacaoPK) {
		this.processoCertificacaoPK = processoCertificacaoPK;
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

	public String getProcessoCertificacaoDataCriacao() {
		return processoCertificacaoDataCriacao;
	}

	public void setProcessoCertificacaoDataCriacao(String processoCertificacaoDataCriacao) {
		this.processoCertificacaoDataCriacao = processoCertificacaoDataCriacao;
	}

	public AtributoStatus getProcessoCertificacaoStatus() {
		return processoCertificacaoStatus;
	}

	public void setProcessoCertificacaoStatus(AtributoStatus processoCertificacaoStatus) {
		this.processoCertificacaoStatus = processoCertificacaoStatus;
	}

	public String getProcessoCertificacaoResponsavel() {
		return processoCertificacaoResponsavel;
	}

	public void setProcessoCertificacaoResponsavel(String processoCertificacaoResponsavel) {
		this.processoCertificacaoResponsavel = processoCertificacaoResponsavel;
	}

	public String getProcessoCertificacaoMotivoOperacao() {
		return processoCertificacaoMotivoOperacao;
	}

	public void setProcessoCertificacaoMotivoOperacao(String processoCertificacaoMotivoOperacao) {
		this.processoCertificacaoMotivoOperacao = processoCertificacaoMotivoOperacao;
	}

}
