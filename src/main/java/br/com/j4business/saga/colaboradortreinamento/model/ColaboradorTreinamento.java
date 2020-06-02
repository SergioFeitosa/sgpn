package br.com.j4business.saga.colaboradortreinamento.model;

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
import br.com.j4business.saga.treinamento.model.Treinamento;

@Entity
@Table(name = "colaborador_treinamento", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_treinamento"}, name="colaboradorTreinamentoUnique"))
public class ColaboradorTreinamento implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradortreinamento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorTreinamentoPK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_treinamento")
	private Treinamento treinamento;
	    
	@Column(name = "cp_status",nullable = false, length = 200)
	private String colaboradorTreinamentoStatus;

	@Column(name = "cp_motivooperacao",nullable = false, length = 200)
	private String colaboradorTreinamentoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ColaboradorTreinamento() {
		super();
	}

	public ColaboradorTreinamento(Colaborador colaborador, Treinamento treinamento, int colaboradorTreinamentoSequencia) {
		super();
		this.colaborador = colaborador;
		this.treinamento = treinamento;
	}

	public long getColaboradorTreinamentoPK() {
		return colaboradorTreinamentoPK;
	}

	public void setColaboradorTreinamentoPK(long colaboradorTreinamentoPK) {
		this.colaboradorTreinamentoPK = colaboradorTreinamentoPK;
	}

	public Treinamento getTreinamento() {
		return treinamento;
	}

	public void setTreinamento(Treinamento treinamento) {
		this.treinamento = treinamento;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colaborador.getPessoaNome() == null) ? 0 : colaborador.getPessoaNome().hashCode());
		result = prime * result + ((treinamento.getTreinamentoNome() == null) ? 0 : treinamento.getTreinamentoNome().hashCode());
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
		ColaboradorTreinamento other = (ColaboradorTreinamento) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (treinamento.getTreinamentoNome() == null) {
			if (other.treinamento.getTreinamentoNome() != null)
				return false;
		} else if (!treinamento.getTreinamentoNome().equals(other.treinamento.getTreinamentoNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorTreinamentoStatus() {
		return colaboradorTreinamentoStatus;
	}

	public void setColaboradorTreinamentoStatus(String colaboradorTreinamentoStatus) {
		this.colaboradorTreinamentoStatus = colaboradorTreinamentoStatus;
	}

	public String getColaboradorTreinamentoMotivoOperacao() {
		return colaboradorTreinamentoMotivoOperacao;
	}

	public void setColaboradorTreinamentoMotivoOperacao(String colaboradorTreinamentoMotivoOperacao) {
		this.colaboradorTreinamentoMotivoOperacao = colaboradorTreinamentoMotivoOperacao;
	}

}
