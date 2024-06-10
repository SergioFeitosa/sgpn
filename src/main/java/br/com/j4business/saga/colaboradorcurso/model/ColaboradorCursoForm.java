package br.com.j4business.saga.colaboradorcurso.model;


import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ColaboradorCursoForm {

	private long colaboradorCursoPK;
	private long cursoPK;
	private long colaboradorPK;
	
    @NotBlank(message = "Nome do curso é uma informação obrigatória.")
	@NotNull
	private String cursoNome;
	
    @NotBlank(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCursoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorCursoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String colaboradorCursoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorCursoMotivoOperacao;

	@NotNull(message = "Data de Início da preparação para Certificação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCursoDataInicio;

	@NotNull(message = "Data de Término da preparação para Certificação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCursoDataTermino;

	@NotNull(message = "Data de Validade da Certificação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCursoDataValidade;

    @NotBlank(message = "Capacitador é uma informação obrigatória.")
	@NotNull(message = "Capacitador é uma informação obrigatória.")
	private String capacitador;


    
	public ColaboradorCursoForm() {
		super();
	}

	public long getCursoPK() {
		return cursoPK;
	}

	public void setCursoPK(long cursoPK) {
		this.cursoPK = cursoPK;
	}

	public String getCursoNome() {
		return cursoNome;
	}

	public void setCursoNome(String cursoNome) {
		this.cursoNome = cursoNome;
	}

	public long getColaboradorCursoPK() {
		return colaboradorCursoPK;
	}

	public void setColaboradorCursoPK(long colaboradorCursoPK) {
		this.colaboradorCursoPK = colaboradorCursoPK;
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

	public String getColaboradorCursoDataCriacao() {
		return colaboradorCursoDataCriacao;
	}

	public void setColaboradorCursoDataCriacao(String colaboradorCursoDataCriacao) {
		this.colaboradorCursoDataCriacao = colaboradorCursoDataCriacao;
	}

	public AtributoStatus getColaboradorCursoStatus() {
		return colaboradorCursoStatus;
	}

	public void setColaboradorCursoStatus(AtributoStatus colaboradorCursoStatus) {
		this.colaboradorCursoStatus = colaboradorCursoStatus;
	}

	public String getColaboradorCursoResponsavel() {
		return colaboradorCursoResponsavel;
	}

	public void setColaboradorCursoResponsavel(String colaboradorCursoResponsavel) {
		this.colaboradorCursoResponsavel = colaboradorCursoResponsavel;
	}

	public String getColaboradorCursoMotivoOperacao() {
		return colaboradorCursoMotivoOperacao;
	}

	public void setColaboradorCursoMotivoOperacao(String colaboradorCursoMotivoOperacao) {
		this.colaboradorCursoMotivoOperacao = colaboradorCursoMotivoOperacao;
	}

	public String getColaboradorCursoDataInicio() {
		return colaboradorCursoDataInicio;
	}

	public void setColaboradorCursoDataInicio(String colaboradorCursoDataInicio) {
		this.colaboradorCursoDataInicio = colaboradorCursoDataInicio;
	}

	public String getColaboradorCursoDataTermino() {
		return colaboradorCursoDataTermino;
	}

	public void setColaboradorCursoDataTermino(String colaboradorCursoDataTermino) {
		this.colaboradorCursoDataTermino = colaboradorCursoDataTermino;
	}

	public String getColaboradorCursoDataValidade() {
		return colaboradorCursoDataValidade;
	}

	public void setColaboradorCursoDataValidade(String colaboradorCursoDataValidade) {
		this.colaboradorCursoDataValidade = colaboradorCursoDataValidade;
	}

	public String getCapacitador() {
		return capacitador;
	}

	public void setCapacitador(String capacitador) {
		this.capacitador = capacitador;
	}

}
