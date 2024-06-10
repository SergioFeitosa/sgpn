package br.com.j4business.saga.processo.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoForm {

	private long processoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String processoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String processoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoStatus;

    @NotBlank(message = "O Gestor do Processo é uma informação obrigatória.")
	@NotNull(message = "O Gestor do Processo é uma informação obrigatória.")
	private String processoGestor;

    @NotBlank(message = "O Dono do Processo é uma informação obrigatória.")
	@NotNull(message = "O Dono do Processo é uma informação obrigatória.")
	private String processoDono;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String processoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoMotivoOperacao;

	private int processoDuracao;
    
	public ProcessoForm() {
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

	public String getProcessoDescricao() {
		return processoDescricao;
	}

	public void setProcessoDescricao(String processoDescricao) {
		this.processoDescricao = processoDescricao;
	}

	public AtributoStatus getProcessoStatus() {
		return processoStatus;
	}

	public void setProcessoStatus(AtributoStatus processoStatus) {
		this.processoStatus = processoStatus;
	}

	public String getProcessoResponsavel() {
		return processoResponsavel;
	}

	public void setProcessoResponsavel(String processoResponsavel) {
		this.processoResponsavel = processoResponsavel;
	}

	public String getProcessoMotivoOperacao() {
		return processoMotivoOperacao;
	}

	public void setProcessoMotivoOperacao(String processoMotivoOperacao) {
		this.processoMotivoOperacao = processoMotivoOperacao;
	}

	public String getProcessoGestor() {
		return processoGestor;
	}

	public void setProcessoGestor(String processoGestor) {
		this.processoGestor = processoGestor;
	}

	public String getProcessoDono() {
		return processoDono;
	}

	public void setProcessoDono(String processoDono) {
		this.processoDono = processoDono;
	}

	public int getProcessoDuracao() {
		return processoDuracao;
	}

	public void setProcessoDuracao(int processoDuracao) {
		this.processoDuracao = processoDuracao;
	}

}
