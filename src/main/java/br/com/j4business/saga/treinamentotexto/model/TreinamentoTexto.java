package br.com.j4business.saga.treinamentotexto.model;

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

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.texto.model.Texto;

@Entity
@Table(name = "treinamento_texto", uniqueConstraints=@UniqueConstraint(columnNames={"id_treinamento","id_texto"}, name="treinamentoTextoUnique"))

public class TreinamentoTexto implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_treinamentotexto")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long treinamentoTextoPK;
	
	@ManyToOne
	@JoinColumn(name="id_treinamento")
	private Treinamento treinamento;    
	
	@ManyToOne
	@JoinColumn(name="id_texto")
	private Texto texto;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String treinamentoTextoStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String treinamentoTextoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public TreinamentoTexto() {
		super();
	}

	public TreinamentoTexto(Treinamento treinamento, Texto texto, int treinamentoTextoSequencia) {
		super();
		this.treinamento = treinamento;
		this.texto = texto;
	}

	public long getTreinamentoTextoPK() {
		return treinamentoTextoPK;
	}

	public void setTreinamentoTextoPK(long treinamentoTextoPK) {
		this.treinamentoTextoPK = treinamentoTextoPK;
	}

	public Texto getTexto() {
		return texto;
	}

	public void setTexto(Texto texto) {
		this.texto = texto;
	}

	public Treinamento getTreinamento() {
		return treinamento;
	}

	public void setTreinamento(Treinamento treinamento) {
		this.treinamento = treinamento;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((treinamento.getTreinamentoNome() == null) ? 0 : treinamento.getTreinamentoNome().hashCode());
		result = prime * result + ((texto.getTextoNome() == null) ? 0 : texto.getTextoNome().hashCode());
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
		TreinamentoTexto other = (TreinamentoTexto) obj;
		if (treinamento.getTreinamentoNome() == null) {
			if (other.treinamento.getTreinamentoNome() != null)
				return false;
		} else if (!treinamento.getTreinamentoNome().equals(other.treinamento.getTreinamentoNome()))
			return false;
		if (texto.getTextoNome() == null) {
			if (other.texto.getTextoNome() != null)
				return false;
		} else if (!texto.getTextoNome().equals(other.texto.getTextoNome()))
			return false;
		return true;
	}

	public String getTreinamentoTextoStatus() {
		return treinamentoTextoStatus;
	}

	public void setTreinamentoTextoStatus(String treinamentoTextoStatus) {
		this.treinamentoTextoStatus = treinamentoTextoStatus;
	}

	public String getTreinamentoTextoMotivoOperacao() {
		return treinamentoTextoMotivoOperacao;
	}

	public void setTreinamentoTextoMotivoOperacao(String treinamentoTextoMotivoOperacao) {
		this.treinamentoTextoMotivoOperacao = treinamentoTextoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
