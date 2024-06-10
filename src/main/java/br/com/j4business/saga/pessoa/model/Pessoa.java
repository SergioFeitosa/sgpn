package br.com.j4business.saga.pessoa.model;


import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="pessoa")
public class Pessoa implements Serializable{

	private static final long serialVersionUID = -4012575682417554593L;

	@Id
	@Column(name = "id_pessoa")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long pessoaPK;
	
	@Column(name = "nm_pessoa",nullable = false, length = 100)
	private String pessoaNome;
	
	public Pessoa() {
		super();
	}

	public long getPessoaPK() {
		return pessoaPK;
	}

	public void setPessoaPK(long pessoaPK) {
		this.pessoaPK = pessoaPK;
	}

	public String getPessoaNome() {
		return pessoaNome;
	}

	public void setPessoaNome(String pessoaNome) {
		this.pessoaNome = pessoaNome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoaNome == null) ? 0 : pessoaNome.hashCode());
		result = prime * result + (int) (pessoaPK ^ (pessoaPK >>> 32));
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
		Pessoa other = (Pessoa) obj;
		if (pessoaNome == null) {
			if (other.pessoaNome != null)
				return false;
		} else if (!pessoaNome.equals(other.pessoaNome))
			return false;
		if (pessoaPK != other.pessoaPK)
			return false;
		return true;
	}

}
