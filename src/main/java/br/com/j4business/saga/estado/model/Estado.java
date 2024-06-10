package br.com.j4business.saga.estado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.pais.model.Pais;

@Entity
@Table(name = "estado", uniqueConstraints= {@UniqueConstraint(columnNames={"nm_estado"}, name="estadoNome"),@UniqueConstraint(columnNames={"cd_estado"}, name="estadoSigla")})

public class Estado {

	@Id
	@Column(name = "id_estado")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long estadoPK;
	
	@Column(name = "nm_estado",nullable = false, length = 100)
	private String estadoNome;
	
	@Column(name = "cd_estado",nullable = false, length = 2)
	private String estadoSigla;

	@Column(name = "es_status")
	private String estadoStatus;

	@Column(name = "es_motivooperacao")
	private String estadoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotNull
	private Pais pais;
	
	public Estado() {
		super();
	}

	public long getEstadoPK() {
		return estadoPK;
	}

	public void setEstadoPK(long estadoPK) {
		this.estadoPK = estadoPK;
	}

	public String getEstadoNome() {
		return estadoNome;
	}

	public void setEstadoNome(String estadoNome) {
		this.estadoNome = estadoNome;
	}

	public String getEstadoSigla() {
		return estadoSigla;
	}

	public void setEstadoSigla(String estadoSigla) {
		this.estadoSigla = estadoSigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estadoNome == null) ? 0 : estadoNome.hashCode());
		result = prime * result + (int) (estadoPK ^ (estadoPK >>> 32));
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
		Estado other = (Estado) obj;
		if (estadoNome == null) {
			if (other.estadoNome != null)
				return false;
		} else if (!estadoNome.equals(other.estadoNome))
			return false;
		if (estadoPK != other.estadoPK)
			return false;
		return true;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Pais getPais() {
		return pais;
	}

	public String getEstadoStatus() {
		return estadoStatus;
	}

	public void setEstadoStatus(String estadoStatus) {
		this.estadoStatus = estadoStatus;
	}

	public String getEstadoMotivoOperacao() {
		return estadoMotivoOperacao;
	}

	public void setEstadoMotivoOperacao(String estadoMotivoOperacao) {
		this.estadoMotivoOperacao = estadoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
