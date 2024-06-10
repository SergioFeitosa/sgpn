package br.com.j4business.saga.unidadeorganizacionalcontrato.model;

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
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;

@Entity
@Table(name = "unidadeorganizacional_contrato", uniqueConstraints=@UniqueConstraint(columnNames={"id_unidadeorganizacional","id_contrato"}, name="unidadeorganizacionalContratoUnique"))

public class UnidadeorganizacionalContrato implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_unidadeorganizacionalcontrato")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long unidadeorganizacionalContratoPK;
	
	@ManyToOne
	@JoinColumn(name="id_unidadeorganizacional")
	private Unidadeorganizacional unidadeorganizacional;    
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;
	    
	@Column(name = "up_status")
	private String unidadeorganizacionalContratoStatus;

	@Column(name = "up_motivooperacao")
	private String unidadeorganizacionalContratoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public UnidadeorganizacionalContrato() {
		super();
	}

	public UnidadeorganizacionalContrato(Unidadeorganizacional unidadeorganizacional, Contrato contrato, int unidadeorganizacionalContratoSequencia) {
		super();
		this.unidadeorganizacional = unidadeorganizacional;
		this.contrato = contrato;
	}

	public long getUnidadeorganizacionalContratoPK() {
		return unidadeorganizacionalContratoPK;
	}

	public void setUnidadeorganizacionalContratoPK(long unidadeorganizacionalContratoPK) {
		this.unidadeorganizacionalContratoPK = unidadeorganizacionalContratoPK;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Unidadeorganizacional getUnidadeorganizacional() {
		return unidadeorganizacional;
	}

	public void setUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional) {
		this.unidadeorganizacional = unidadeorganizacional;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unidadeorganizacional.getUnidadeorganizacionalNome() == null) ? 0 : unidadeorganizacional.getUnidadeorganizacionalNome().hashCode());
		result = prime * result + ((contrato.getContratoNome() == null) ? 0 : contrato.getContratoNome().hashCode());
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
		UnidadeorganizacionalContrato other = (UnidadeorganizacionalContrato) obj;
		if (unidadeorganizacional.getUnidadeorganizacionalNome() == null) {
			if (other.unidadeorganizacional.getUnidadeorganizacionalNome() != null)
				return false;
		} else if (!unidadeorganizacional.getUnidadeorganizacionalNome().equals(other.unidadeorganizacional.getUnidadeorganizacionalNome()))
			return false;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
			return false;
		return true;
	}

	public String getUnidadeorganizacionalContratoStatus() {
		return unidadeorganizacionalContratoStatus;
	}

	public void setUnidadeorganizacionalContratoStatus(String unidadeorganizacionalContratoStatus) {
		this.unidadeorganizacionalContratoStatus = unidadeorganizacionalContratoStatus;
	}

	public String getUnidadeorganizacionalContratoMotivoOperacao() {
		return unidadeorganizacionalContratoMotivoOperacao;
	}

	public void setUnidadeorganizacionalContratoMotivoOperacao(String unidadeorganizacionalContratoMotivoOperacao) {
		this.unidadeorganizacionalContratoMotivoOperacao = unidadeorganizacionalContratoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
