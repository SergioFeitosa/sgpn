package br.com.j4business.saga.contratotexto.model;

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
import br.com.j4business.saga.texto.model.Texto;

@Entity
@Table(name = "contrato_texto", uniqueConstraints=@UniqueConstraint(columnNames={"id_contrato","id_texto"}, name="contratoTextoUnique"))

public class ContratoTexto implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_contratotexto")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contratoTextoPK;
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;    
	
	@ManyToOne
	@JoinColumn(name="id_texto")
	private Texto texto;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String contratoTextoStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String contratoTextoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ContratoTexto() {
		super();
	}

	public ContratoTexto(Contrato contrato, Texto texto, int contratoTextoSequencia) {
		super();
		this.contrato = contrato;
		this.texto = texto;
	}

	public long getContratoTextoPK() {
		return contratoTextoPK;
	}

	public void setContratoTextoPK(long contratoTextoPK) {
		this.contratoTextoPK = contratoTextoPK;
	}

	public Texto getTexto() {
		return texto;
	}

	public void setTexto(Texto texto) {
		this.texto = texto;
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
		ContratoTexto other = (ContratoTexto) obj;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
			return false;
		if (texto.getTextoNome() == null) {
			if (other.texto.getTextoNome() != null)
				return false;
		} else if (!texto.getTextoNome().equals(other.texto.getTextoNome()))
			return false;
		return true;
	}

	public String getContratoTextoStatus() {
		return contratoTextoStatus;
	}

	public void setContratoTextoStatus(String contratoTextoStatus) {
		this.contratoTextoStatus = contratoTextoStatus;
	}

	public String getContratoTextoMotivoOperacao() {
		return contratoTextoMotivoOperacao;
	}

	public void setContratoTextoMotivoOperacao(String contratoTextoMotivoOperacao) {
		this.contratoTextoMotivoOperacao = contratoTextoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
