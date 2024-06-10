package br.com.j4business.saga.cenarioelemento.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class CenarioElementoForm {

	private long cenarioElementoPK;
	private long elementoPK;
	private long cenarioPK;
	
    @NotBlank(message = "Nome do elemento é uma informação obrigatória.")
	@NotNull
	private String elementoNome;
	
    @NotBlank(message = "Nome da Cenario é uma informação obrigatória.")
	@NotNull
	private String cenarioNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  cenarioElementoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus cenarioElementoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String cenarioElementoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String cenarioElementoMotivoOperacao;

	public CenarioElementoForm() {
		super();
	}

	public long getElementoPK() {
		return elementoPK;
	}

	public void setElementoPK(long elementoPK) {
		this.elementoPK = elementoPK;
	}

	public String getElementoNome() {
		return elementoNome;
	}

	public void setElementoNome(String elementoNome) {
		this.elementoNome = elementoNome;
	}

	public long getCenarioElementoPK() {
		return cenarioElementoPK;
	}

	public void setCenarioElementoPK(long cenarioElementoPK) {
		this.cenarioElementoPK = cenarioElementoPK;
	}

	public long getCenarioPK() {
		return cenarioPK;
	}

	public void setCenarioPK(long cenarioPK) {
		this.cenarioPK = cenarioPK;
	}

	public String getCenarioNome() {
		return cenarioNome;
	}

	public void setCenarioNome(String cenarioNome) {
		this.cenarioNome = cenarioNome;
	}

	public String getCenarioElementoDataCriacao() {
		return cenarioElementoDataCriacao;
	}

	public void setCenarioElementoDataCriacao(String cenarioElementoDataCriacao) {
		this.cenarioElementoDataCriacao = cenarioElementoDataCriacao;
	}

	public AtributoStatus getCenarioElementoStatus() {
		return cenarioElementoStatus;
	}

	public void setCenarioElementoStatus(AtributoStatus cenarioElementoStatus) {
		this.cenarioElementoStatus = cenarioElementoStatus;
	}

	public String getCenarioElementoResponsavel() {
		return cenarioElementoResponsavel;
	}

	public void setCenarioElementoResponsavel(String cenarioElementoResponsavel) {
		this.cenarioElementoResponsavel = cenarioElementoResponsavel;
	}

	public String getCenarioElementoMotivoOperacao() {
		return cenarioElementoMotivoOperacao;
	}

	public void setCenarioElementoMotivoOperacao(String cenarioElementoMotivoOperacao) {
		this.cenarioElementoMotivoOperacao = cenarioElementoMotivoOperacao;
	}

}
