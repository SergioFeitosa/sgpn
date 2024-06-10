package br.com.j4business.saga.estruturafisicaunidadeorganizacional.model;

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
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;

@Entity
@Table(name = "estruturafisica_unidadeorganizacional", uniqueConstraints=@UniqueConstraint(columnNames={"id_estruturafisica","id_unidadeorganizacional"}, name="estruturafisicaUnidadeorganizacionalUnique"))
public class EstruturafisicaUnidadeorganizacional implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_estruturafisicaunidadeorganizacional")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long estruturafisicaUnidadeorganizacionalPK;
	
	@ManyToOne
	@JoinColumn(name="id_estruturafisica")
	private Estruturafisica estruturafisica;    
	
	@ManyToOne
	@JoinColumn(name="id_unidadeorganizacional")
	private Unidadeorganizacional unidadeorganizacional;
	    
	@Column(name = "eu_status")
	private String estruturafisicaUnidadeorganizacionalStatus;

	@Column(name = "eu_motivooperacao")
	private String estruturafisicaUnidadeorganizacionalMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public EstruturafisicaUnidadeorganizacional() {
		super();
	}

	public EstruturafisicaUnidadeorganizacional(Estruturafisica estruturafisica, Unidadeorganizacional unidadeorganizacional, int estruturafisicaUnidadeorganizacionalSequencia) {
		super();
		this.estruturafisica = estruturafisica;
		this.unidadeorganizacional = unidadeorganizacional;
	}

	public long getEstruturafisicaUnidadeorganizacionalPK() {
		return estruturafisicaUnidadeorganizacionalPK;
	}

	public void setEstruturafisicaUnidadeorganizacionalPK(long estruturafisicaUnidadeorganizacionalPK) {
		this.estruturafisicaUnidadeorganizacionalPK = estruturafisicaUnidadeorganizacionalPK;
	}

	public Unidadeorganizacional getUnidadeorganizacional() {
		return unidadeorganizacional;
	}

	public void setUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional) {
		this.unidadeorganizacional = unidadeorganizacional;
	}

	public Estruturafisica getEstruturafisica() {
		return estruturafisica;
	}

	public void setEstruturafisica(Estruturafisica estruturafisica) {
		this.estruturafisica = estruturafisica;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estruturafisica.getEstruturafisicaNome() == null) ? 0 : estruturafisica.getEstruturafisicaNome().hashCode());
		result = prime * result + ((unidadeorganizacional.getUnidadeorganizacionalNome() == null) ? 0 : unidadeorganizacional.getUnidadeorganizacionalNome().hashCode());
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
		EstruturafisicaUnidadeorganizacional other = (EstruturafisicaUnidadeorganizacional) obj;
		if (estruturafisica.getEstruturafisicaNome() == null) {
			if (other.estruturafisica.getEstruturafisicaNome() != null)
				return false;
		} else if (!estruturafisica.getEstruturafisicaNome().equals(other.estruturafisica.getEstruturafisicaNome()))
			return false;
		if (unidadeorganizacional.getUnidadeorganizacionalNome() == null) {
			if (other.unidadeorganizacional.getUnidadeorganizacionalNome() != null)
				return false;
		} else if (!unidadeorganizacional.getUnidadeorganizacionalNome().equals(other.unidadeorganizacional.getUnidadeorganizacionalNome()))
			return false;
		return true;
	}

	public String getEstruturafisicaUnidadeorganizacionalStatus() {
		return estruturafisicaUnidadeorganizacionalStatus;
	}

	public void setEstruturafisicaUnidadeorganizacionalStatus(String estruturafisicaUnidadeorganizacionalStatus) {
		this.estruturafisicaUnidadeorganizacionalStatus = estruturafisicaUnidadeorganizacionalStatus;
	}

	public String getEstruturafisicaUnidadeorganizacionalMotivoOperacao() {
		return estruturafisicaUnidadeorganizacionalMotivoOperacao;
	}

	public void setEstruturafisicaUnidadeorganizacionalMotivoOperacao(
			String estruturafisicaUnidadeorganizacionalMotivoOperacao) {
		this.estruturafisicaUnidadeorganizacionalMotivoOperacao = estruturafisicaUnidadeorganizacionalMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
