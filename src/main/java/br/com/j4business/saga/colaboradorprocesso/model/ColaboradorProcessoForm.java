package br.com.j4business.saga.colaboradorprocesso.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorProcessoForm {

	private long colaboradorProcessoPK;
	private long processoPK;
	private long colaboradorPK;
	
    @NotEmpty(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotEmpty(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorProcessoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String colaboradorProcessoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorProcessoMotivoOperacao;

	@NotNull(message = "Data de Início no processo é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorProcessoDataInicio;

	@NotNull(message = "Data de Término no processo é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorProcessoDataTermino;

    
	public ColaboradorProcessoForm() {
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

	public long getColaboradorProcessoPK() {
		return colaboradorProcessoPK;
	}

	public void setColaboradorProcessoPK(long colaboradorProcessoPK) {
		this.colaboradorProcessoPK = colaboradorProcessoPK;
	}

	public long getColaboradorPK() {
		return colaboradorPK;
	}

	public void setColaboradorPK(long colaboradorPK) {
		this.colaboradorPK = colaboradorPK;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public String getColaboradorProcessoDataCriacao() {
		return colaboradorProcessoDataCriacao;
	}

	public void setColaboradorProcessoDataCriacao(String colaboradorProcessoDataCriacao) {
		this.colaboradorProcessoDataCriacao = colaboradorProcessoDataCriacao;
	}

	public AtributoStatus getColaboradorProcessoStatus() {
		return colaboradorProcessoStatus;
	}

	public void setColaboradorProcessoStatus(AtributoStatus colaboradorProcessoStatus) {
		this.colaboradorProcessoStatus = colaboradorProcessoStatus;
	}

	public String getColaboradorProcessoResponsavel() {
		return colaboradorProcessoResponsavel;
	}

	public void setColaboradorProcessoResponsavel(String colaboradorProcessoResponsavel) {
		this.colaboradorProcessoResponsavel = colaboradorProcessoResponsavel;
	}

	public String getColaboradorProcessoMotivoOperacao() {
		return colaboradorProcessoMotivoOperacao;
	}

	public void setColaboradorProcessoMotivoOperacao(String colaboradorProcessoMotivoOperacao) {
		this.colaboradorProcessoMotivoOperacao = colaboradorProcessoMotivoOperacao;
	}

	public String getColaboradorProcessoDataInicio() {
		return colaboradorProcessoDataInicio;
	}

	public void setColaboradorProcessoDataInicio(String colaboradorProcessoDataInicio) {
		this.colaboradorProcessoDataInicio = colaboradorProcessoDataInicio;
	}

	public String getColaboradorProcessoDataTermino() {
		return colaboradorProcessoDataTermino;
	}

	public void setColaboradorProcessoDataTermino(String colaboradorProcessoDataTermino) {
		this.colaboradorProcessoDataTermino = colaboradorProcessoDataTermino;
	}

}
