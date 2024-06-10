package br.com.j4business.saga.planejamentoprocesso.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class PlanejamentoProcessoForm {

	private long planejamentoProcessoPK;
	private long processoPK;
	private long planejamentoPK;
	
    @NotBlank(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotBlank(message = "Nome da Planejamento é uma informação obrigatória.")
	@NotNull
	private String planejamentoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus planejamentoProcessoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String planejamentoProcessoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String planejamentoProcessoMotivoOperacao;

	public PlanejamentoProcessoForm() {
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

	public long getPlanejamentoProcessoPK() {
		return planejamentoProcessoPK;
	}

	public void setPlanejamentoProcessoPK(long planejamentoProcessoPK) {
		this.planejamentoProcessoPK = planejamentoProcessoPK;
	}

	public long getPlanejamentoPK() {
		return planejamentoPK;
	}

	public void setPlanejamentoPK(long planejamentoPK) {
		this.planejamentoPK = planejamentoPK;
	}

	public String getPlanejamentoNome() {
		return planejamentoNome;
	}

	public void setPlanejamentoNome(String planejamentoNome) {
		this.planejamentoNome = planejamentoNome;
	}

	public String getPlanejamentoProcessoDataCriacao() {
		return planejamentoProcessoDataCriacao;
	}

	public void setPlanejamentoProcessoDataCriacao(String planejamentoProcessoDataCriacao) {
		this.planejamentoProcessoDataCriacao = planejamentoProcessoDataCriacao;
	}

	public AtributoStatus getPlanejamentoProcessoStatus() {
		return planejamentoProcessoStatus;
	}

	public void setPlanejamentoProcessoStatus(AtributoStatus planejamentoProcessoStatus) {
		this.planejamentoProcessoStatus = planejamentoProcessoStatus;
	}

	public String getPlanejamentoProcessoResponsavel() {
		return planejamentoProcessoResponsavel;
	}

	public void setPlanejamentoProcessoResponsavel(String planejamentoProcessoResponsavel) {
		this.planejamentoProcessoResponsavel = planejamentoProcessoResponsavel;
	}

	public String getPlanejamentoProcessoMotivoOperacao() {
		return planejamentoProcessoMotivoOperacao;
	}

	public void setPlanejamentoProcessoMotivoOperacao(String planejamentoProcessoMotivoOperacao) {
		this.planejamentoProcessoMotivoOperacao = planejamentoProcessoMotivoOperacao;
	}

}
