package br.com.j4business.saga.questionarioquestao.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questionario.model.Questionario;

@Entity
@Table(name = "questionario_questao", uniqueConstraints=@UniqueConstraint(columnNames={"id_questionario","id_questao"}, name="questionarioQuestaoUnique"))

public class QuestionarioQuestao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_questionarioquestao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long questionarioQuestaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_questionario")
	private Questionario questionario;    
	
	@ManyToOne
	@JoinColumn(name="id_questao")
	private Questao questao;
	    
	@Column(name = "sq_questionarioquestao",nullable = false, length = 200)
	private int questionarioQuestaoSequencia;

	@Column(name = "qq_status")
	private String questionarioQuestaoStatus;

	@Column(name = "qq_motivooperacao")
	private String questionarioQuestaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public QuestionarioQuestao() {
		super();
	}

	public QuestionarioQuestao(Questionario questionario, Questao questao, int questionarioQuestaoSequencia) {
		super();
		this.questionario = questionario;
		this.questao = questao;
		this.questionarioQuestaoSequencia = questionarioQuestaoSequencia;
	}

	public long getQuestionarioQuestaoPK() {
		return questionarioQuestaoPK;
	}

	public void setQuestionarioQuestaoPK(long questionarioQuestaoPK) {
		this.questionarioQuestaoPK = questionarioQuestaoPK;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionario.getQuestionarioNome() == null) ? 0 : questionario.getQuestionarioNome().hashCode());
		result = prime * result + ((questao.getQuestaoNome() == null) ? 0 : questao.getQuestaoNome().hashCode());
		result = prime * result + ((questionarioQuestaoSequencia+"" == null) ? 0 : questionarioQuestaoSequencia+"".hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionarioQuestao other = (QuestionarioQuestao) obj;
		if (questionario.getQuestionarioNome() == null) {
			if (other.questionario.getQuestionarioNome() != null)
				return false;
		} else if (!questionario.getQuestionarioNome().equals(other.questionario.getQuestionarioNome()))
			return false;
		if (questao.getQuestaoNome() == null) {
			if (other.questao.getQuestaoNome() != null)
				return false;
		} else if (!questao.getQuestaoNome().equals(other.questao.getQuestaoNome()))
			return false;
		return true;
	}

	public int getQuestionarioQuestaoSequencia() {
		return questionarioQuestaoSequencia;
	}

	public void setQuestionarioQuestaoSequencia(int questionarioQuestaoSequencia) {
		this.questionarioQuestaoSequencia = questionarioQuestaoSequencia;
	}

	public String getQuestionarioQuestaoStatus() {
		return questionarioQuestaoStatus;
	}

	public void setQuestionarioQuestaoStatus(String questionarioQuestaoStatus) {
		this.questionarioQuestaoStatus = questionarioQuestaoStatus;
	}

	public String getQuestionarioQuestaoMotivoOperacao() {
		return questionarioQuestaoMotivoOperacao;
	}

	public void setQuestionarioQuestaoMotivoOperacao(String questionarioQuestaoMotivoOperacao) {
		this.questionarioQuestaoMotivoOperacao = questionarioQuestaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
