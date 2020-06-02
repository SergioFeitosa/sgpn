package br.com.j4business.saga.unidadeorganizacionalcenario.model;

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
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;

@Entity
@Table(name = "unidadeorganizacional_cenario", uniqueConstraints=@UniqueConstraint(columnNames={"id_unidadeorganizacional","id_cenario"}, name="unidadeorganizacionalCenarioUnique"))
public class UnidadeorganizacionalCenario implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_unidadeorganizacionalcenario")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long unidadeorganizacionalCenarioPK;
	
	@ManyToOne
	@JoinColumn(name="id_unidadeorganizacional")
	private Unidadeorganizacional unidadeorganizacional;    
	
	@ManyToOne
	@JoinColumn(name="id_cenario")
	private Cenario cenario;
	    
	@ManyToOne
	private Colaborador colaborador;
	
	public UnidadeorganizacionalCenario() {
		super();
	}

	public UnidadeorganizacionalCenario(Unidadeorganizacional unidadeorganizacional, Cenario cenario, int unidadeorganizacionalCenarioSequencia) {
		super();
		this.unidadeorganizacional = unidadeorganizacional;
		this.cenario = cenario;
	}

	public long getUnidadeorganizacionalCenarioPK() {
		return unidadeorganizacionalCenarioPK;
	}

	public void setUnidadeorganizacionalCenarioPK(long unidadeorganizacionalCenarioPK) {
		this.unidadeorganizacionalCenarioPK = unidadeorganizacionalCenarioPK;
	}

	public Cenario getCenario() {
		return cenario;
	}

	public void setCenario(Cenario cenario) {
		this.cenario = cenario;
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
		result = prime * result + ((cenario.getCenarioNome() == null) ? 0 : cenario.getCenarioNome().hashCode());
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
		UnidadeorganizacionalCenario other = (UnidadeorganizacionalCenario) obj;
		if (unidadeorganizacional.getUnidadeorganizacionalNome() == null) {
			if (other.unidadeorganizacional.getUnidadeorganizacionalNome() != null)
				return false;
		} else if (!unidadeorganizacional.getUnidadeorganizacionalNome().equals(other.unidadeorganizacional.getUnidadeorganizacionalNome()))
			return false;
		if (cenario.getCenarioNome() == null) {
			if (other.cenario.getCenarioNome() != null)
				return false;
		} else if (!cenario.getCenarioNome().equals(other.cenario.getCenarioNome()))
			return false;
		return true;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
