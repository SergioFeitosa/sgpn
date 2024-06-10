package br.com.j4business.saga.questionario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "questionario", uniqueConstraints=@UniqueConstraint(columnNames={"nm_questionario"}, name="questionarioNome"))
public class Questionario {

	@Id
	@Column(name = "id_questionario")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long questionarioPK;
	
	@Column(name = "nm_questionario",nullable = false, length = 50)
	private String questionarioNome;
	
	@Column(name = "ds_questionario")
	private String questionarioDescricao;

	@ManyToOne
	private Cenario cenario;
	
	@Column(name = "qt_status")
	private String questionarioStatus;

	@Column(name = "qt_motivooperacao")
	private String questionarioMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	
	public Questionario() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionarioNome == null) ? 0 : questionarioNome.hashCode());
		result = prime * result + (int) (questionarioPK ^ (questionarioPK >>> 32));
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
		Questionario other = (Questionario) obj;
		if (questionarioNome == null) {
			if (other.questionarioNome != null)
				return false;
		} else if (!questionarioNome.equals(other.questionarioNome))
			return false;
		if (questionarioPK != other.questionarioPK)
			return false;
		return true;
	}

	public Cenario getCenario() {
		return cenario;
	}

	public void setCenario(Cenario cenario) {
		this.cenario = cenario;
	}

	public String getQuestionarioStatus() {
		return questionarioStatus;
	}

	public void setQuestionarioStatus(String questionarioStatus) {
		this.questionarioStatus = questionarioStatus;
	}

	public String getQuestionarioMotivoOperacao() {
		return questionarioMotivoOperacao;
	}

	public void setQuestionarioMotivoOperacao(String questionarioMotivoOperacao) {
		this.questionarioMotivoOperacao = questionarioMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
