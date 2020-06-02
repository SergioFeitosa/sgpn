package br.com.j4business.saga.pessoafisica.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.br.CPF;

import br.com.j4business.saga.pessoa.model.Pessoa;

@Entity
@Table(name = "pessoafisica", uniqueConstraints=@UniqueConstraint(columnNames={"nr_pessoaCPF"}, name="pessoaCPF"))
public class PessoaFisica extends Pessoa implements Serializable{

	private static final long serialVersionUID = 7403670427636861494L;

	
	@CPF
	@Column(name = "nr_pessoaCPF",nullable = false, length = 50)
	private String pessoaCPF;
	
	public PessoaFisica() {
		super();
	}



	public String getPessoaCPF() {
		return pessoaCPF;
	}

	public void setPessoaCPF(String pessoaCPF) {
		this.pessoaCPF = pessoaCPF;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pessoaCPF == null) ? 0 : pessoaCPF.hashCode());
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
		PessoaFisica other = (PessoaFisica) obj;
		if (pessoaCPF == null) {
			if (other.pessoaCPF != null)
				return false;
		} else if (!pessoaCPF.equals(other.pessoaCPF))
			return false;
		return true;
	}


}
