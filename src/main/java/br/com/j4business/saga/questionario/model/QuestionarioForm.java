package br.com.j4business.saga.questionario.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class QuestionarioForm {

	private long questionarioPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String questionarioNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String questionarioDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus questionarioStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String questionarioResponsavel;

    @NotBlank(message = "Cenário é uma informação obrigatória.")
	@NotNull(message = "Cenário é uma informação obrigatória.")
	private String questionarioCenario;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String questionarioMotivoOperacao;

	public QuestionarioForm() {
		super();
	}

	public long getQuestionarioPK() {
		return questionarioPK;
	}

	public void setQuestionarioPK(long questionarioPK) {
		this.questionarioPK = questionarioPK;
	}

	public String getQuestionarioNome() {
		return questionarioNome;
	}

	public void setQuestionarioNome(String questionarioNome) {
		this.questionarioNome = questionarioNome;
	}

	public String getQuestionarioDescricao() {
		return questionarioDescricao;
	}

	public void setQuestionarioDescricao(String questionarioDescricao) {
		this.questionarioDescricao = questionarioDescricao;
	}

	public String getQuestionarioResponsavel() {
		return questionarioResponsavel;
	}

	public void setQuestionarioResponsavel(String questionarioResponsavel) {
		this.questionarioResponsavel = questionarioResponsavel;
	}

	public String getQuestionarioMotivoOperacao() {
		return questionarioMotivoOperacao;
	}

	public void setQuestionarioMotivoOperacao(String questionarioMotivoOperacao) {
		this.questionarioMotivoOperacao = questionarioMotivoOperacao;
	}

	public String getQuestionarioCenario() {
		return questionarioCenario;
	}

	public void setQuestionarioCenario(String questionarioCenario) {
		this.questionarioCenario = questionarioCenario;
	}

	public void setQuestionarioStatus(AtributoStatus questionarioStatus) {
		this.questionarioStatus = questionarioStatus;
	}

	public AtributoStatus getQuestionarioStatus() {
		return questionarioStatus;
	}

	
}
