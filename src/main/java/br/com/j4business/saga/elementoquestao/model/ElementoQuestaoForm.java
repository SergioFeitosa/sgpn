package br.com.j4business.saga.elementoquestao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ElementoQuestaoForm {

	private long elementoQuestaoPK;
	private long questaoPK;
	private long elementoPK;
	
    @NotBlank(message = "Nome do questao é uma informação obrigatória.")
	@NotNull
	private String questaoNome;
	
    @NotBlank(message = "Nome da Elemento é uma informação obrigatória.")
	@NotNull
	private String elementoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  elementoQuestaoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus elementoQuestaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String elementoQuestaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String elementoQuestaoMotivoOperacao;

	public ElementoQuestaoForm() {
		super();
	}

	public long getQuestaoPK() {
		return questaoPK;
	}

	public void setQuestaoPK(long questaoPK) {
		this.questaoPK = questaoPK;
	}

	public String getQuestaoNome() {
		return questaoNome;
	}

	public void setQuestaoNome(String questaoNome) {
		this.questaoNome = questaoNome;
	}

	public long getElementoQuestaoPK() {
		return elementoQuestaoPK;
	}

	public void setElementoQuestaoPK(long elementoQuestaoPK) {
		this.elementoQuestaoPK = elementoQuestaoPK;
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

	public String getElementoQuestaoDataCriacao() {
		return elementoQuestaoDataCriacao;
	}

	public void setElementoQuestaoDataCriacao(String elementoQuestaoDataCriacao) {
		this.elementoQuestaoDataCriacao = elementoQuestaoDataCriacao;
	}

	public AtributoStatus getElementoQuestaoStatus() {
		return elementoQuestaoStatus;
	}

	public void setElementoQuestaoStatus(AtributoStatus elementoQuestaoStatus) {
		this.elementoQuestaoStatus = elementoQuestaoStatus;
	}

	public String getElementoQuestaoResponsavel() {
		return elementoQuestaoResponsavel;
	}

	public void setElementoQuestaoResponsavel(String elementoQuestaoResponsavel) {
		this.elementoQuestaoResponsavel = elementoQuestaoResponsavel;
	}

	public String getElementoQuestaoMotivoOperacao() {
		return elementoQuestaoMotivoOperacao;
	}

	public void setElementoQuestaoMotivoOperacao(String elementoQuestaoMotivoOperacao) {
		this.elementoQuestaoMotivoOperacao = elementoQuestaoMotivoOperacao;
	}

}
