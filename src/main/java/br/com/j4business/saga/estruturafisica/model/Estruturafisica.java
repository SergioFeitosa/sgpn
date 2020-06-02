package br.com.j4business.saga.estruturafisica.model;

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
@Table(name = "estruturafisica", uniqueConstraints=@UniqueConstraint(columnNames={"nm_estruturafisica"}, name="estruturafisicaNome"))
public class Estruturafisica {

	@Id
	@Column(name = "id_estruturafisica")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long estruturafisicaPK;
	
	@Column(name = "nm_estruturafisica",nullable = false, length = 50)
	private String estruturafisicaNome;
	
	@Column(name = "ds_estruturafisica")
	private String estruturafisicaDescricao;

	@Column(name = "ef_status")
	private String estruturafisicaStatus;

	@Column(name = "ef_motivooperacao")
	private String estruturafisicaMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Estruturafisica() {
		super();
	}

	public long getEstruturafisicaPK() {
		return estruturafisicaPK;
	}

	public void setEstruturafisicaPK(long estruturafisicaPK) {
		this.estruturafisicaPK = estruturafisicaPK;
	}

	public String getEstruturafisicaNome() {
		return estruturafisicaNome;
	}

	public void setEstruturafisicaNome(String estruturafisicaNome) {
		this.estruturafisicaNome = estruturafisicaNome;
	}

	public String getEstruturafisicaDescricao() {
		return estruturafisicaDescricao;
	}

	public void setEstruturafisicaDescricao(String estruturafisicaDescricao) {
		this.estruturafisicaDescricao = estruturafisicaDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estruturafisicaNome == null) ? 0 : estruturafisicaNome.hashCode());
		result = prime * result + (int) (estruturafisicaPK ^ (estruturafisicaPK >>> 32));
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
		Estruturafisica other = (Estruturafisica) obj;
		if (estruturafisicaNome == null) {
			if (other.estruturafisicaNome != null)
				return false;
		} else if (!estruturafisicaNome.equals(other.estruturafisicaNome))
			return false;
		if (estruturafisicaPK != other.estruturafisicaPK)
			return false;
		return true;
	}

	public String getEstruturafisicaStatus() {
		return estruturafisicaStatus;
	}

	public void setEstruturafisicaStatus(String estruturafisicaStatus) {
		this.estruturafisicaStatus = estruturafisicaStatus;
	}

	public String getEstruturafisicaMotivoOperacao() {
		return estruturafisicaMotivoOperacao;
	}

	public void setEstruturafisicaMotivoOperacao(String estruturafisicaMotivoOperacao) {
		this.estruturafisicaMotivoOperacao = estruturafisicaMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
