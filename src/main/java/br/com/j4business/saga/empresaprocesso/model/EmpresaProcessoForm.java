package br.com.j4business.saga.empresaprocesso.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class EmpresaProcessoForm {

	private long empresaProcessoPK;
	private long processoPK;
	private long empresaPK;
	
    @NotBlank(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotBlank(message = "Nome da Empresa é uma informação obrigatória.")
	@NotNull
	private String empresaNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  empresaProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus empresaProcessoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String empresaProcessoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String empresaProcessoMotivoOperacao;

	public EmpresaProcessoForm() {
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

	public long getEmpresaProcessoPK() {
		return empresaProcessoPK;
	}

	public void setEmpresaProcessoPK(long empresaProcessoPK) {
		this.empresaProcessoPK = empresaProcessoPK;
	}

	public long getEmpresaPK() {
		return empresaPK;
	}

	public void setEmpresaPK(long empresaPK) {
		this.empresaPK = empresaPK;
	}

	public String getEmpresaNome() {
		return empresaNome;
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}

	public String getEmpresaProcessoDataCriacao() {
		return empresaProcessoDataCriacao;
	}

	public void setEmpresaProcessoDataCriacao(String empresaProcessoDataCriacao) {
		this.empresaProcessoDataCriacao = empresaProcessoDataCriacao;
	}

	public AtributoStatus getEmpresaProcessoStatus() {
		return empresaProcessoStatus;
	}

	public void setEmpresaProcessoStatus(AtributoStatus empresaProcessoStatus) {
		this.empresaProcessoStatus = empresaProcessoStatus;
	}

	public String getEmpresaProcessoResponsavel() {
		return empresaProcessoResponsavel;
	}

	public void setEmpresaProcessoResponsavel(String empresaProcessoResponsavel) {
		this.empresaProcessoResponsavel = empresaProcessoResponsavel;
	}

	public String getEmpresaProcessoMotivoOperacao() {
		return empresaProcessoMotivoOperacao;
	}

	public void setEmpresaProcessoMotivoOperacao(String empresaProcessoMotivoOperacao) {
		this.empresaProcessoMotivoOperacao = empresaProcessoMotivoOperacao;
	}

}
