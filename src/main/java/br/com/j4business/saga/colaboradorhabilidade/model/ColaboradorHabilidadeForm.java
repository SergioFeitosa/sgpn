package br.com.j4business.saga.colaboradorhabilidade.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorHabilidadeForm {

	private long colaboradorHabilidadePK;
	private long habilidadePK;
	private long colaboradorPK;
	
    @NotEmpty(message = "Nome do habilidade é uma informação obrigatória.")
	@NotNull
	private String habilidadeNome;
	
    @NotEmpty(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorHabilidadeDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorHabilidadeStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String colaboradorHabilidadeResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorHabilidadeMotivoOperacao;

	@NotNull(message = "Data de Início da preparação da Habilidade é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorHabilidadeDataInicio;

	@NotNull(message = "Data de Término da preparação da Habilidade é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorHabilidadeDataTermino;

	@NotNull(message = "Data de Validade da Habilidade é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorHabilidadeDataValidade;

    @NotEmpty(message = "Capacitador é uma informação obrigatória.")
	@NotNull(message = "Capacitador é uma informação obrigatória.")
	private String colaboradorHabilidadeCapacitador;


	public ColaboradorHabilidadeForm() {
		super();
	}

	public long getHabilidadePK() {
		return habilidadePK;
	}

	public void setHabilidadePK(long habilidadePK) {
		this.habilidadePK = habilidadePK;
	}

	public String getHabilidadeNome() {
		return habilidadeNome;
	}

	public void setHabilidadeNome(String habilidadeNome) {
		this.habilidadeNome = habilidadeNome;
	}

	public long getColaboradorHabilidadePK() {
		return colaboradorHabilidadePK;
	}

	public void setColaboradorHabilidadePK(long colaboradorHabilidadePK) {
		this.colaboradorHabilidadePK = colaboradorHabilidadePK;
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

	public String getColaboradorHabilidadeDataCriacao() {
		return colaboradorHabilidadeDataCriacao;
	}

	public void setColaboradorHabilidadeDataCriacao(String colaboradorHabilidadeDataCriacao) {
		this.colaboradorHabilidadeDataCriacao = colaboradorHabilidadeDataCriacao;
	}

	public AtributoStatus getColaboradorHabilidadeStatus() {
		return colaboradorHabilidadeStatus;
	}

	public void setColaboradorHabilidadeStatus(AtributoStatus colaboradorHabilidadeStatus) {
		this.colaboradorHabilidadeStatus = colaboradorHabilidadeStatus;
	}

	public String getColaboradorHabilidadeResponsavel() {
		return colaboradorHabilidadeResponsavel;
	}

	public void setColaboradorHabilidadeResponsavel(String colaboradorHabilidadeResponsavel) {
		this.colaboradorHabilidadeResponsavel = colaboradorHabilidadeResponsavel;
	}

	public String getColaboradorHabilidadeMotivoOperacao() {
		return colaboradorHabilidadeMotivoOperacao;
	}

	public void setColaboradorHabilidadeMotivoOperacao(String colaboradorHabilidadeMotivoOperacao) {
		this.colaboradorHabilidadeMotivoOperacao = colaboradorHabilidadeMotivoOperacao;
	}

	public String getColaboradorHabilidadeDataInicio() {
		return colaboradorHabilidadeDataInicio;
	}

	public void setColaboradorHabilidadeDataInicio(String colaboradorHabilidadeDataInicio) {
		this.colaboradorHabilidadeDataInicio = colaboradorHabilidadeDataInicio;
	}

	public String getColaboradorHabilidadeDataTermino() {
		return colaboradorHabilidadeDataTermino;
	}

	public void setColaboradorHabilidadeDataTermino(String colaboradorHabilidadeDataTermino) {
		this.colaboradorHabilidadeDataTermino = colaboradorHabilidadeDataTermino;
	}

	public String getColaboradorHabilidadeDataValidade() {
		return colaboradorHabilidadeDataValidade;
	}

	public void setColaboradorHabilidadeDataValidade(String colaboradorHabilidadeDataValidade) {
		this.colaboradorHabilidadeDataValidade = colaboradorHabilidadeDataValidade;
	}

	public String getColaboradorHabilidadeCapacitador() {
		return colaboradorHabilidadeCapacitador;
	}

	public void setColaboradorHabilidadeCapacitador(String colaboradorHabilidadeCapacitador) {
		this.colaboradorHabilidadeCapacitador = colaboradorHabilidadeCapacitador;
	}

}
