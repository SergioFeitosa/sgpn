package br.com.j4business.saga.pessoajuridica.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.br.CNPJ;
import br.com.j4business.saga.pessoa.model.Pessoa;

@Entity
@Table(name = "pessoajuridica", uniqueConstraints=@UniqueConstraint(columnNames={"nr_cnpj"}, name="cnpj"))
public class PessoaJuridica  extends Pessoa implements Serializable{

	private static final long serialVersionUID = 3996874422865645563L;

	@CNPJ
	@Column(name = "nr_cnpj",nullable = false, length = 50)
	private String cnpj;
	
	public PessoaJuridica() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
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
		PessoaJuridica other = (PessoaJuridica) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		return true;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


}
