package br.com.j4business.saga.municipio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.estado.model.Estado;

@Entity
@Table(name = "municipio")
public class Municipio {

	@Id
	@Column(name = "id_municipio")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long municipioPK;
	
	@Column(name = "nm_municipio",nullable = false, length = 100)
	private String municipioNome;
	
	@Column(name = "nr_cep",nullable = false, length = 10)
	private String municipioCEP;

	@Column(name = "mu_status")
	private String municipioStatus;

	@Column(name = "mu_motivooperacao")
	private String municipioMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@ManyToOne(fetch=FetchType.LAZY)
	@NotNull
	private Estado estado;
	
	public Municipio() {
		super();
	}

	public long getMunicipioPK() {
		return municipioPK;
	}

	public void setMunicipioPK(long municipioPK) {
		this.municipioPK = municipioPK;
	}

	public String getMunicipioNome() {
		return municipioNome;
	}

	public void setMunicipioNome(String municipioNome) {
		this.municipioNome = municipioNome;
	}

	public String getMunicipioCEP() {
		return municipioCEP;
	}

	public void setMunicipioCEP(String municipioCEP) {
		this.municipioCEP = municipioCEP;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((municipioNome == null) ? 0 : municipioNome.hashCode());
		result = prime * result + (int) (municipioPK ^ (municipioPK >>> 32));
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
		Municipio other = (Municipio) obj;
		if (municipioNome == null) {
			if (other.municipioNome != null)
				return false;
		} else if (!municipioNome.equals(other.municipioNome))
			return false;
		if (municipioPK != other.municipioPK)
			return false;
		return true;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getMunicipioStatus() {
		return municipioStatus;
	}

	public void setMunicipioStatus(String municipioStatus) {
		this.municipioStatus = municipioStatus;
	}

	public String getMunicipioMotivoOperacao() {
		return municipioMotivoOperacao;
	}

	public void setMunicipioMotivoOperacao(String municipioMotivoOperacao) {
		this.municipioMotivoOperacao = municipioMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
