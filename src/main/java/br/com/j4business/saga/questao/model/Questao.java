package br.com.j4business.saga.questao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.elemento.model.Elemento;

@Entity
@Table(name = "questao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_questao"}, name="questaoNome"))
public class Questao {

	@Id
	@Column(name = "id_questao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long questaoPK;
	
	@Column(name = "nm_questao",nullable = false, length = 50)
	private String questaoNome;
	
	@Column(name = "ds_questao")
	private String questaoDescricao;

	@Column(name = "qs_status")
	private String questaoStatus;

	@Column(name = "qs_motivooperacao")
	private String questaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	
	@ManyToOne
	private Elemento elemento;
	
	public Questao() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questaoNome == null) ? 0 : questaoNome.hashCode());
		result = prime * result + (int) (questaoPK ^ (questaoPK >>> 32));
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
		Questao other = (Questao) obj;
		if (questaoNome == null) {
			if (other.questaoNome != null)
				return false;
		} else if (!questaoNome.equals(other.questaoNome))
			return false;
		if (questaoPK != other.questaoPK)
			return false;
		return true;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	public String getQuestaoStatus() {
		return questaoStatus;
	}

	public void setQuestaoStatus(String questaoStatus) {
		this.questaoStatus = questaoStatus;
	}

	public String getQuestaoMotivoOperacao() {
		return questaoMotivoOperacao;
	}

	public void setQuestaoMotivoOperacao(String questaoMotivoOperacao) {
		this.questaoMotivoOperacao = questaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
