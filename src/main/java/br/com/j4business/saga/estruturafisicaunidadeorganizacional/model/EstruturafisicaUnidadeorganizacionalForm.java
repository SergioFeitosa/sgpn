package br.com.j4business.saga.estruturafisicaunidadeorganizacional.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class EstruturafisicaUnidadeorganizacionalForm {

	private long estruturafisicaUnidadeorganizacionalPK;
	private long unidadeorganizacionalPK;
	private long estruturafisicaPK;
	
    @NotBlank(message = "Nome do unidadeorganizacional é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalNome;
	
    @NotBlank(message = "Nome da Estruturafisica é uma informação obrigatória.")
	@NotNull
	private String estruturafisicaNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  estruturafisicaUnidadeorganizacionalDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus estruturafisicaUnidadeorganizacionalStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String estruturafisicaUnidadeorganizacionalResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String estruturafisicaUnidadeorganizacionalMotivoOperacao;

	public EstruturafisicaUnidadeorganizacionalForm() {
		super();
	}

	public long getUnidadeorganizacionalPK() {
		return unidadeorganizacionalPK;
	}

	public void setUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		this.unidadeorganizacionalPK = unidadeorganizacionalPK;
	}

	public String getUnidadeorganizacionalNome() {
		return unidadeorganizacionalNome;
	}

	public void setUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		this.unidadeorganizacionalNome = unidadeorganizacionalNome;
	}

	public long getEstruturafisicaUnidadeorganizacionalPK() {
		return estruturafisicaUnidadeorganizacionalPK;
	}

	public void setEstruturafisicaUnidadeorganizacionalPK(long estruturafisicaUnidadeorganizacionalPK) {
		this.estruturafisicaUnidadeorganizacionalPK = estruturafisicaUnidadeorganizacionalPK;
	}

	public long getEstruturafisicaPK() {
		return estruturafisicaPK;
	}

	public void setEstruturafisicaPK(long estruturafisicaPK) {
		this.estruturafisicaPK = estruturafisicaPK;
	}

	public String getEstruturafisicaNome() {
		return estruturafisicaNome;
	}

	public void setEstruturafisicaNome(String estruturafisicaNome) {
		this.estruturafisicaNome = estruturafisicaNome;
	}

	public String getEstruturafisicaUnidadeorganizacionalDataCriacao() {
		return estruturafisicaUnidadeorganizacionalDataCriacao;
	}

	public void setEstruturafisicaUnidadeorganizacionalDataCriacao(String estruturafisicaUnidadeorganizacionalDataCriacao) {
		this.estruturafisicaUnidadeorganizacionalDataCriacao = estruturafisicaUnidadeorganizacionalDataCriacao;
	}

	public AtributoStatus getEstruturafisicaUnidadeorganizacionalStatus() {
		return estruturafisicaUnidadeorganizacionalStatus;
	}

	public void setEstruturafisicaUnidadeorganizacionalStatus(AtributoStatus estruturafisicaUnidadeorganizacionalStatus) {
		this.estruturafisicaUnidadeorganizacionalStatus = estruturafisicaUnidadeorganizacionalStatus;
	}

	public String getEstruturafisicaUnidadeorganizacionalResponsavel() {
		return estruturafisicaUnidadeorganizacionalResponsavel;
	}

	public void setEstruturafisicaUnidadeorganizacionalResponsavel(String estruturafisicaUnidadeorganizacionalResponsavel) {
		this.estruturafisicaUnidadeorganizacionalResponsavel = estruturafisicaUnidadeorganizacionalResponsavel;
	}

	public String getEstruturafisicaUnidadeorganizacionalMotivoOperacao() {
		return estruturafisicaUnidadeorganizacionalMotivoOperacao;
	}

	public void setEstruturafisicaUnidadeorganizacionalMotivoOperacao(String estruturafisicaUnidadeorganizacionalMotivoOperacao) {
		this.estruturafisicaUnidadeorganizacionalMotivoOperacao = estruturafisicaUnidadeorganizacionalMotivoOperacao;
	}

}
