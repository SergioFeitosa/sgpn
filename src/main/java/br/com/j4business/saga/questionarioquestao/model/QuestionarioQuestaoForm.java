package br.com.j4business.saga.questionarioquestao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class QuestionarioQuestaoForm {

	private long questionarioQuestaoPK;
	private long questaoPK;
	private long questionarioPK;
	
    @NotBlank(message = "Nome do questao é uma informação obrigatória.")
	@NotNull
	private String questaoNome;
	
    @NotBlank(message = "Nome da Questionario é uma informação obrigatória.")
	@NotNull
	private String questionarioNome;
	
    @NotBlank(message = "Sequência é uma informação obrigatória.")
	private String questionarioQuestaoSequencia;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus questionarioQuestaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String questionarioQuestaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String questionarioQuestaoMotivoOperacao;

	public QuestionarioQuestaoForm() {
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

	public long getQuestionarioQuestaoPK() {
		return questionarioQuestaoPK;
	}

	public void setQuestionarioQuestaoPK(long questionarioQuestaoPK) {
		this.questionarioQuestaoPK = questionarioQuestaoPK;
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

	public String getQuestionarioQuestaoSequencia() {
		return questionarioQuestaoSequencia;
	}

	public void setQuestionarioQuestaoSequencia(String questionarioQuestaoSequencia) {
		this.questionarioQuestaoSequencia = questionarioQuestaoSequencia;
	}

	public AtributoStatus getQuestionarioQuestaoStatus() {
		return questionarioQuestaoStatus;
	}

	public void setQuestionarioQuestaoStatus(AtributoStatus questionarioQuestaoStatus) {
		this.questionarioQuestaoStatus = questionarioQuestaoStatus;
	}

	public String getQuestionarioQuestaoResponsavel() {
		return questionarioQuestaoResponsavel;
	}

	public void setQuestionarioQuestaoResponsavel(String questionarioQuestaoResponsavel) {
		this.questionarioQuestaoResponsavel = questionarioQuestaoResponsavel;
	}

	public String getQuestionarioQuestaoMotivoOperacao() {
		return questionarioQuestaoMotivoOperacao;
	}

	public void setQuestionarioQuestaoMotivoOperacao(String questionarioQuestaoMotivoOperacao) {
		this.questionarioQuestaoMotivoOperacao = questionarioQuestaoMotivoOperacao;
	}

	
}
