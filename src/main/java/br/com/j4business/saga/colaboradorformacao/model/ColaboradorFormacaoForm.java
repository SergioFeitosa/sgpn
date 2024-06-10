package br.com.j4business.saga.colaboradorformacao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorFormacaoForm {

	private long colaboradorFormacaoPK;
	private long formacaoPK;
	private long colaboradorPK;
	
    @NotBlank(message = "Nome do formacao é uma informação obrigatória.")
	@NotNull
	private String formacaoNome;
	
    @NotBlank(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorFormacaoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorFormacaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String colaboradorFormacaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorFormacaoMotivoOperacao;

	@NotNull(message = "Data de Início da preparação para Formação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorFormacaoDataInicio;

	@NotNull(message = "Data de Término da preparação para Formação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorFormacaoDataTermino;

	@NotNull(message = "Data de Validade da Formação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorFormacaoDataValidade;

    @NotBlank(message = "Capacitador é uma informação obrigatória.")
	@NotNull(message = "Capacitador é uma informação obrigatória.")
	private String colaboradorFormacaoCapacitador;

	public ColaboradorFormacaoForm() {
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

	public long getColaboradorFormacaoPK() {
		return colaboradorFormacaoPK;
	}

	public void setColaboradorFormacaoPK(long colaboradorFormacaoPK) {
		this.colaboradorFormacaoPK = colaboradorFormacaoPK;
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

	public String getColaboradorFormacaoDataCriacao() {
		return colaboradorFormacaoDataCriacao;
	}

	public void setColaboradorFormacaoDataCriacao(String colaboradorFormacaoDataCriacao) {
		this.colaboradorFormacaoDataCriacao = colaboradorFormacaoDataCriacao;
	}

	public AtributoStatus getColaboradorFormacaoStatus() {
		return colaboradorFormacaoStatus;
	}

	public void setColaboradorFormacaoStatus(AtributoStatus colaboradorFormacaoStatus) {
		this.colaboradorFormacaoStatus = colaboradorFormacaoStatus;
	}

	public String getColaboradorFormacaoResponsavel() {
		return colaboradorFormacaoResponsavel;
	}

	public void setColaboradorFormacaoResponsavel(String colaboradorFormacaoResponsavel) {
		this.colaboradorFormacaoResponsavel = colaboradorFormacaoResponsavel;
	}

	public String getColaboradorFormacaoMotivoOperacao() {
		return colaboradorFormacaoMotivoOperacao;
	}

	public void setColaboradorFormacaoMotivoOperacao(String colaboradorFormacaoMotivoOperacao) {
		this.colaboradorFormacaoMotivoOperacao = colaboradorFormacaoMotivoOperacao;
	}

	public String getColaboradorFormacaoDataInicio() {
		return colaboradorFormacaoDataInicio;
	}

	public void setColaboradorFormacaoDataInicio(String colaboradorFormacaoDataInicio) {
		this.colaboradorFormacaoDataInicio = colaboradorFormacaoDataInicio;
	}

	public String getColaboradorFormacaoDataTermino() {
		return colaboradorFormacaoDataTermino;
	}

	public void setColaboradorFormacaoDataTermino(String colaboradorFormacaoDataTermino) {
		this.colaboradorFormacaoDataTermino = colaboradorFormacaoDataTermino;
	}

	public String getColaboradorFormacaoDataValidade() {
		return colaboradorFormacaoDataValidade;
	}

	public void setColaboradorFormacaoDataValidade(String colaboradorFormacaoDataValidade) {
		this.colaboradorFormacaoDataValidade = colaboradorFormacaoDataValidade;
	}

	public String getColaboradorFormacaoCapacitador() {
		return colaboradorFormacaoCapacitador;
	}

	public void setColaboradorFormacaoCapacitador(String colaboradorFormacaoCapacitador) {
		this.colaboradorFormacaoCapacitador = colaboradorFormacaoCapacitador;
	}

}
