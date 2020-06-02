package br.com.j4business.saga.colaboradorcertificacao.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorCertificacaoForm {

	private long colaboradorCertificacaoPK;
	private long certificacaoPK;
	private long colaboradorPK;
	
    @NotEmpty(message = "Nome do certificacao é uma informação obrigatória.")
	@NotNull
	private String certificacaoNome;
	
    @NotEmpty(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
	@NotNull(message = "Data de Início da preparação para Certificação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCertificacaoDataInicio;

	@NotNull(message = "Data de Término da preparação para Certificação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCertificacaoDataTermino;

	@NotNull(message = "Data de Validade da Certificação é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorCertificacaoDataValidade;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorCertificacaoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String colaboradorCertificacaoResponsavel;

    @NotEmpty(message = "Capacitador é uma informação obrigatória.")
	@NotNull(message = "Capacitador é uma informação obrigatória.")
	private String capacitador;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorCertificacaoMotivoOperacao;

	public ColaboradorCertificacaoForm() {
		super();
	}

	public long getCertificacaoPK() {
		return certificacaoPK;
	}

	public void setCertificacaoPK(long certificacaoPK) {
		this.certificacaoPK = certificacaoPK;
	}

	public String getCertificacaoNome() {
		return certificacaoNome;
	}

	public void setCertificacaoNome(String certificacaoNome) {
		this.certificacaoNome = certificacaoNome;
	}

	public long getColaboradorCertificacaoPK() {
		return colaboradorCertificacaoPK;
	}

	public void setColaboradorCertificacaoPK(long colaboradorCertificacaoPK) {
		this.colaboradorCertificacaoPK = colaboradorCertificacaoPK;
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

	public AtributoStatus getColaboradorCertificacaoStatus() {
		return colaboradorCertificacaoStatus;
	}

	public void setColaboradorCertificacaoStatus(AtributoStatus colaboradorCertificacaoStatus) {
		this.colaboradorCertificacaoStatus = colaboradorCertificacaoStatus;
	}

	public String getColaboradorCertificacaoResponsavel() {
		return colaboradorCertificacaoResponsavel;
	}

	public void setColaboradorCertificacaoResponsavel(String colaboradorCertificacaoResponsavel) {
		this.colaboradorCertificacaoResponsavel = colaboradorCertificacaoResponsavel;
	}

	public String getColaboradorCertificacaoMotivoOperacao() {
		return colaboradorCertificacaoMotivoOperacao;
	}

	public void setColaboradorCertificacaoMotivoOperacao(String colaboradorCertificacaoMotivoOperacao) {
		this.colaboradorCertificacaoMotivoOperacao = colaboradorCertificacaoMotivoOperacao;
	}

	public String getColaboradorCertificacaoDataInicio() {
		return colaboradorCertificacaoDataInicio;
	}

	public void setColaboradorCertificacaoDataInicio(String colaboradorCertificacaoDataInicio) {
		this.colaboradorCertificacaoDataInicio = colaboradorCertificacaoDataInicio;
	}

	public String getColaboradorCertificacaoDataTermino() {
		return colaboradorCertificacaoDataTermino;
	}

	public void setColaboradorCertificacaoDataTermino(String colaboradorCertificacaoDataTermino) {
		this.colaboradorCertificacaoDataTermino = colaboradorCertificacaoDataTermino;
	}

	public String getColaboradorCertificacaoDataValidade() {
		return colaboradorCertificacaoDataValidade;
	}

	public void setColaboradorCertificacaoDataValidade(String colaboradorCertificacaoDataValidade) {
		this.colaboradorCertificacaoDataValidade = colaboradorCertificacaoDataValidade;
	}

	public String getCapacitador() {
		return capacitador;
	}

	public void setCapacitador(String capacitador) {
		this.capacitador = capacitador;
	}


}
