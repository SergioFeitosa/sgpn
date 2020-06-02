package br.com.j4business.saga.planejamentoprocesso.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class PlanejamentoProcessoForm {

	private long planejamentoProcessoPK;
	private long processoPK;
	private long planejamentoPK;
	
    @NotEmpty(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotEmpty(message = "Nome da Planejamento é uma informação obrigatória.")
	@NotNull
	private String planejamentoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus planejamentoProcessoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String planejamentoProcessoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
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
