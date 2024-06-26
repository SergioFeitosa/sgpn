package br.com.j4business.saga.servicoprocesso.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ServicoProcessoForm {

	private long servicoProcessoPK;
	private long processoPK;
	private long servicoPK;
	
    @NotBlank(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotBlank(message = "Nome da Servico é uma informação obrigatória.")
	@NotNull
	private String servicoNome;
	
    @NotBlank(message = "Sequência é uma informação obrigatória.")
	private String servicoProcessoSequencia;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  servicoProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus servicoProcessoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String servicoProcessoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String servicoProcessoMotivoOperacao;

	public ServicoProcessoForm() {
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

	public long getServicoProcessoPK() {
		return servicoProcessoPK;
	}

	public void setServicoProcessoPK(long servicoProcessoPK) {
		this.servicoProcessoPK = servicoProcessoPK;
	}

	public long getServicoPK() {
		return servicoPK;
	}

	public void setServicoPK(long servicoPK) {
		this.servicoPK = servicoPK;
	}

	public String getServicoNome() {
		return servicoNome;
	}

	public void setServicoNome(String servicoNome) {
		this.servicoNome = servicoNome;
	}

	public String getServicoProcessoSequencia() {
		return servicoProcessoSequencia;
	}

	public void setServicoProcessoSequencia(String servicoProcessoSequencia) {
		this.servicoProcessoSequencia = servicoProcessoSequencia;
	}

	public String getServicoProcessoDataCriacao() {
		return servicoProcessoDataCriacao;
	}

	public void setServicoProcessoDataCriacao(String servicoProcessoDataCriacao) {
		this.servicoProcessoDataCriacao = servicoProcessoDataCriacao;
	}

	public AtributoStatus getServicoProcessoStatus() {
		return servicoProcessoStatus;
	}

	public void setServicoProcessoStatus(AtributoStatus servicoProcessoStatus) {
		this.servicoProcessoStatus = servicoProcessoStatus;
	}

	public String getServicoProcessoResponsavel() {
		return servicoProcessoResponsavel;
	}

	public void setServicoProcessoResponsavel(String servicoProcessoResponsavel) {
		this.servicoProcessoResponsavel = servicoProcessoResponsavel;
	}

	public String getServicoProcessoMotivoOperacao() {
		return servicoProcessoMotivoOperacao;
	}

	public void setServicoProcessoMotivoOperacao(String servicoProcessoMotivoOperacao) {
		this.servicoProcessoMotivoOperacao = servicoProcessoMotivoOperacao;
	}

	
}
