package br.com.j4business.saga.cenarioelemento.model;

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
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.cenario.model.Cenario;

@Entity
@Table(name = "cenario_elemento", uniqueConstraints=@UniqueConstraint(columnNames={"id_cenario","id_elemento"}, name="cenarioElementoUnique"))

public class CenarioElemento implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_cenarioelemento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long cenarioElementoPK;
	
	@ManyToOne
	@JoinColumn(name="id_cenario")
	private Cenario cenario;    
	
	@ManyToOne
	@JoinColumn(name="id_elemento")
	private Elemento elemento;
	    
	@Column(name = "cl_status")
	private String cenarioElementoStatus;

	@Column(name = "cl_motivooperacao")
	private String cenarioElementoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public CenarioElemento() {
		super();
	}

	public CenarioElemento(Cenario cenario, Elemento elemento, int cenarioElementoSequencia) {
		super();
		this.cenario = cenario;
		this.elemento = elemento;
	}

	public long getCenarioElementoPK() {
		return cenarioElementoPK;
	}

	public void setCenarioElementoPK(long cenarioElementoPK) {
		this.cenarioElementoPK = cenarioElementoPK;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	public Cenario getCenario() {
		return cenario;
	}

	public void setCenario(Cenario cenario) {
		this.cenario = cenario;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cenario.getCenarioNome() == null) ? 0 : cenario.getCenarioNome().hashCode());
		result = prime * result + ((elemento.getElementoNome() == null) ? 0 : elemento.getElementoNome().hashCode());
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
		CenarioElemento other = (CenarioElemento) obj;
		if (cenario.getCenarioNome() == null) {
			if (other.cenario.getCenarioNome() != null)
				return false;
		} else if (!cenario.getCenarioNome().equals(other.cenario.getCenarioNome()))
			return false;
		if (elemento.getElementoNome() == null) {
			if (other.elemento.getElementoNome() != null)
				return false;
		} else if (!elemento.getElementoNome().equals(other.elemento.getElementoNome()))
			return false;
		return true;
	}

	public String getCenarioElementoStatus() {
		return cenarioElementoStatus;
	}

	public void setCenarioElementoStatus(String cenarioElementoStatus) {
		this.cenarioElementoStatus = cenarioElementoStatus;
	}

	public String getCenarioElementoMotivoOperacao() {
		return cenarioElementoMotivoOperacao;
	}

	public void setCenarioElementoMotivoOperacao(String cenarioElementoMotivoOperacao) {
		this.cenarioElementoMotivoOperacao = cenarioElementoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
