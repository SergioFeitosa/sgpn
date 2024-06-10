package br.com.j4business.saga.contratoimagem.model;

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

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.imagem.model.Imagem;

@Entity
@Table(name = "contrato_imagem", uniqueConstraints=@UniqueConstraint(columnNames={"id_contrato","id_imagem"}, name="contratoImagemUnique"))

public class ContratoImagem implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_contratoimagem")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contratoImagemPK;
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;    
	
	@ManyToOne
	@JoinColumn(name="id_imagem")
	private Imagem imagem;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String contratoImagemStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String contratoImagemMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ContratoImagem() {
		super();
	}

	public ContratoImagem(Contrato contrato, Imagem imagem, int contratoImagemSequencia) {
		super();
		this.contrato = contrato;
		this.imagem = imagem;
	}

	public long getContratoImagemPK() {
		return contratoImagemPK;
	}

	public void setContratoImagemPK(long contratoImagemPK) {
		this.contratoImagemPK = contratoImagemPK;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contrato.getContratoNome() == null) ? 0 : contrato.getContratoNome().hashCode());
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
		ContratoImagem other = (ContratoImagem) obj;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
			return false;
		if (imagem.getImagemNome() == null) {
			if (other.imagem.getImagemNome() != null)
				return false;
		} else if (!imagem.getImagemNome().equals(other.imagem.getImagemNome()))
			return false;
		return true;
	}

	public String getContratoImagemStatus() {
		return contratoImagemStatus;
	}

	public void setContratoImagemStatus(String contratoImagemStatus) {
		this.contratoImagemStatus = contratoImagemStatus;
	}

	public String getContratoImagemMotivoOperacao() {
		return contratoImagemMotivoOperacao;
	}

	public void setContratoImagemMotivoOperacao(String contratoImagemMotivoOperacao) {
		this.contratoImagemMotivoOperacao = contratoImagemMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
