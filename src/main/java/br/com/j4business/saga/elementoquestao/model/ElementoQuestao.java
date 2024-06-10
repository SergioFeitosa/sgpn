package br.com.j4business.saga.elementoquestao.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.elemento.model.Elemento;

@Entity
@Table(name = "elemento_questao", uniqueConstraints=@UniqueConstraint(columnNames={"id_elemento","id_questao"}, name="elementoQuestaoUnique"))
public class ElementoQuestao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_elementoquestao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long elementoQuestaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_elemento")
	private Elemento elemento;    
	
	@ManyToOne
	@JoinColumn(name="id_questao")
	private Questao questao;
	    
	@Column(name = "eq_status")
	private String elementoQuestaoStatus;

	@Column(name = "eq_motivooperacao")
	private String elementoQuestaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ElementoQuestao() {
		super();
	}

	public ElementoQuestao(Elemento elemento, Questao questao, int elementoQuestaoSequencia) {
		super();
		this.elemento = elemento;
		this.questao = questao;
	}

	public long getElementoQuestaoPK() {
		return elementoQuestaoPK;
	}

	public void setElementoQuestaoPK(long elementoQuestaoPK) {
		this.elementoQuestaoPK = elementoQuestaoPK;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elemento.getElementoNome() == null) ? 0 : elemento.getElementoNome().hashCode());
		result = prime * result + ((questao.getQuestaoNome() == null) ? 0 : questao.getQuestaoNome().hashCode());
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
		ElementoQuestao other = (ElementoQuestao) obj;
		if (elemento.getElementoNome() == null) {
			if (other.elemento.getElementoNome() != null)
				return false;
		} else if (!elemento.getElementoNome().equals(other.elemento.getElementoNome()))
			return false;
		if (questao.getQuestaoNome() == null) {
			if (other.questao.getQuestaoNome() != null)
				return false;
		} else if (!questao.getQuestaoNome().equals(other.questao.getQuestaoNome()))
			return false;
		return true;
	}

	public String getElementoQuestaoStatus() {
		return elementoQuestaoStatus;
	}

	public void setElementoQuestaoStatus(String elementoQuestaoStatus) {
		this.elementoQuestaoStatus = elementoQuestaoStatus;
	}

	public String getElementoQuestaoMotivoOperacao() {
		return elementoQuestaoMotivoOperacao;
	}

	public void setElementoQuestaoMotivoOperacao(String elementoQuestaoMotivoOperacao) {
		this.elementoQuestaoMotivoOperacao = elementoQuestaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
