package br.com.j4business.saga.questao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class QuestaoForm {

	private long questaoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String questaoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String questaoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus questaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String questaoResponsavel;

    @NotBlank(message = "Elemento é uma informação obrigatória.")
	@NotNull(message = "Elemento é uma informação obrigatória.")
	private String questaoElemento;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String questaoMotivoOperacao;

	public QuestaoForm() {
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

	public String getQuestaoDescricao() {
		return questaoDescricao;
	}

	public void setQuestaoDescricao(String questaoDescricao) {
		this.questaoDescricao = questaoDescricao;
	}

	public String getQuestaoResponsavel() {
		return questaoResponsavel;
	}

	public void setQuestaoResponsavel(String questaoResponsavel) {
		this.questaoResponsavel = questaoResponsavel;
	}

	public String getQuestaoMotivoOperacao() {
		return questaoMotivoOperacao;
	}

	public void setQuestaoMotivoOperacao(String questaoMotivoOperacao) {
		this.questaoMotivoOperacao = questaoMotivoOperacao;
	}

	public String getQuestaoElemento() {
		return questaoElemento;
	}

	public void setQuestaoElemento(String questaoElemento) {
		this.questaoElemento = questaoElemento;
	}

	public AtributoStatus getQuestaoStatus() {
		return questaoStatus;
	}

	public void setQuestaoStatus(AtributoStatus questaoStatus) {
		this.questaoStatus = questaoStatus;
	}

	
}
