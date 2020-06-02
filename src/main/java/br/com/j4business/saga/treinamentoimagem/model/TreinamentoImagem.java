package br.com.j4business.saga.treinamentoimagem.model;

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
import br.com.j4business.saga.imagem.model.Imagem;

@Entity
@Table(name = "treinamento_imagem", uniqueConstraints=@UniqueConstraint(columnNames={"id_treinamento","id_imagem"}, name="treinamentoImagemUnique"))

public class TreinamentoImagem implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_treinamentoimagem")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long treinamentoImagemPK;
	
	@ManyToOne
	@JoinColumn(name="id_treinamento")
	private Treinamento treinamento;    
	
	@ManyToOne
	@JoinColumn(name="id_imagem")
	private Imagem imagem;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String treinamentoImagemStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String treinamentoImagemMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public TreinamentoImagem() {
		super();
	}

	public TreinamentoImagem(Treinamento treinamento, Imagem imagem, int treinamentoImagemSequencia) {
		super();
		this.treinamento = treinamento;
		this.imagem = imagem;
	}

	public long getTreinamentoImagemPK() {
		return treinamentoImagemPK;
	}

	public void setTreinamentoImagemPK(long treinamentoImagemPK) {
		this.treinamentoImagemPK = treinamentoImagemPK;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
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
		result = prime * result + ((imagem.getImagemNome() == null) ? 0 : imagem.getImagemNome().hashCode());
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
		TreinamentoImagem other = (TreinamentoImagem) obj;
		if (treinamento.getTreinamentoNome() == null) {
			if (other.treinamento.getTreinamentoNome() != null)
				return false;
		} else if (!treinamento.getTreinamentoNome().equals(other.treinamento.getTreinamentoNome()))
			return false;
		if (imagem.getImagemNome() == null) {
			if (other.imagem.getImagemNome() != null)
				return false;
		} else if (!imagem.getImagemNome().equals(other.imagem.getImagemNome()))
			return false;
		return true;
	}

	public String getTreinamentoImagemStatus() {
		return treinamentoImagemStatus;
	}

	public void setTreinamentoImagemStatus(String treinamentoImagemStatus) {
		this.treinamentoImagemStatus = treinamentoImagemStatus;
	}

	public String getTreinamentoImagemMotivoOperacao() {
		return treinamentoImagemMotivoOperacao;
	}

	public void setTreinamentoImagemMotivoOperacao(String treinamentoImagemMotivoOperacao) {
		this.treinamentoImagemMotivoOperacao = treinamentoImagemMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
