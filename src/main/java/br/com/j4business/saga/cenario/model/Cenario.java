package br.com.j4business.saga.cenario.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "cenario", uniqueConstraints=@UniqueConstraint(columnNames={"nm_cenario"}, name="cenarioNome"))

public class Cenario {

	@Id
	@Column(name = "id_cenario")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long cenarioPK;
	
	@Column(name = "nm_cenario",nullable = false, length = 100)
	private String cenarioNome;
	
	@Column(name = "ds_cenario")
	private String cenarioDescricao;

	@Column(name = "ce_status")
	private String cenarioStatus;

	@Column(name = "ce_motivooperacao")
	private String cenarioMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Cenario() {
		super();
	}

	public long getCenarioPK() {
		return cenarioPK;
	}

	public void setCenarioPK(long cenarioPK) {
		this.cenarioPK = cenarioPK;
	}

	public String getCenarioNome() {
		return cenarioNome;
	}

	public void setCenarioNome(String cenarioNome) {
		this.cenarioNome = cenarioNome;
	}

	public String getCenarioDescricao() {
		return cenarioDescricao;
	}

	public void setCenarioDescricao(String cenarioDescricao) {
		this.cenarioDescricao = cenarioDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cenarioNome == null) ? 0 : cenarioNome.hashCode());
		result = prime * result + (int) (cenarioPK ^ (cenarioPK >>> 32));
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
		Cenario other = (Cenario) obj;
		if (cenarioNome == null) {
			if (other.cenarioNome != null)
				return false;
		} else if (!cenarioNome.equals(other.cenarioNome))
			return false;
		if (cenarioPK != other.cenarioPK)
			return false;
		return true;
	}

	public String getCenarioStatus() {
		return cenarioStatus;
	}

	public void setCenarioStatus(String cenarioStatus) {
		this.cenarioStatus = cenarioStatus;
	}

	public String getCenarioMotivoOperacao() {
		return cenarioMotivoOperacao;
	}

	public void setCenarioMotivoOperacao(String cenarioMotivoOperacao) {
		this.cenarioMotivoOperacao = cenarioMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
