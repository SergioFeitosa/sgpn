package br.com.j4business.saga.fornecedorprocesso.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class FornecedorProcessoForm {

	private long fornecedorProcessoPK;
	private long processoPK;
	private long fornecedorPK;
	
    @NotBlank(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotBlank(message = "Nome da Fornecedor é uma informação obrigatória.")
	@NotNull
	private String fornecedorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  fornecedorProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus fornecedorProcessoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String fornecedorProcessoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String fornecedorProcessoMotivoOperacao;

	public FornecedorProcessoForm() {
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

	public long getFornecedorProcessoPK() {
		return fornecedorProcessoPK;
	}

	public void setFornecedorProcessoPK(long fornecedorProcessoPK) {
		this.fornecedorProcessoPK = fornecedorProcessoPK;
	}

	public long getFornecedorPK() {
		return fornecedorPK;
	}

	public void setFornecedorPK(long fornecedorPK) {
		this.fornecedorPK = fornecedorPK;
	}

	public String getFornecedorNome() {
		return fornecedorNome;
	}

	public void setFornecedorNome(String fornecedorNome) {
		this.fornecedorNome = fornecedorNome;
	}

	public String getFornecedorProcessoDataCriacao() {
		return fornecedorProcessoDataCriacao;
	}

	public void setFornecedorProcessoDataCriacao(String fornecedorProcessoDataCriacao) {
		this.fornecedorProcessoDataCriacao = fornecedorProcessoDataCriacao;
	}

	public AtributoStatus getFornecedorProcessoStatus() {
		return fornecedorProcessoStatus;
	}

	public void setFornecedorProcessoStatus(AtributoStatus fornecedorProcessoStatus) {
		this.fornecedorProcessoStatus = fornecedorProcessoStatus;
	}

	public String getFornecedorProcessoResponsavel() {
		return fornecedorProcessoResponsavel;
	}

	public void setFornecedorProcessoResponsavel(String fornecedorProcessoResponsavel) {
		this.fornecedorProcessoResponsavel = fornecedorProcessoResponsavel;
	}

	public String getFornecedorProcessoMotivoOperacao() {
		return fornecedorProcessoMotivoOperacao;
	}

	public void setFornecedorProcessoMotivoOperacao(String fornecedorProcessoMotivoOperacao) {
		this.fornecedorProcessoMotivoOperacao = fornecedorProcessoMotivoOperacao;
	}

}
