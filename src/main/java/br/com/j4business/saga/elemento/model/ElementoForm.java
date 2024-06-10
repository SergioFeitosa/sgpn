package br.com.j4business.saga.elemento.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ElementoForm {

	private long elementoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String elementoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String elementoDescricao;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  elementoDataCriacao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus elementoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String elementoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String elementoMotivoOperacao;

	public ElementoForm() {
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

	public String getElementoDescricao() {
		return elementoDescricao;
	}

	public void setElementoDescricao(String elementoDescricao) {
		this.elementoDescricao = elementoDescricao;
	}

	public String getElementoDataCriacao() {
		return elementoDataCriacao;
	}

	public void setElementoDataCriacao(String elementoDataCriacao) {
		this.elementoDataCriacao = elementoDataCriacao;
	}

	public AtributoStatus getElementoStatus() {
		return elementoStatus;
	}

	public void setElementoStatus(AtributoStatus elementoStatus) {
		this.elementoStatus = elementoStatus;
	}

	public String getElementoResponsavel() {
		return elementoResponsavel;
	}

	public void setElementoResponsavel(String elementoResponsavel) {
		this.elementoResponsavel = elementoResponsavel;
	}

	public String getElementoMotivoOperacao() {
		return elementoMotivoOperacao;
	}

	public void setElementoMotivoOperacao(String elementoMotivoOperacao) {
		this.elementoMotivoOperacao = elementoMotivoOperacao;
	}

	
}
