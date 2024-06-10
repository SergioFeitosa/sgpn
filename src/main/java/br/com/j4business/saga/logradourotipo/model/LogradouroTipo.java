package br.com.j4business.saga.logradourotipo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "logradourotipo", uniqueConstraints=@UniqueConstraint(columnNames={"nm_logradouroTipo"}, name="logradouroTipoNome"))
public class LogradouroTipo {

	@Id
	@Column(name = "id_logradouroTipo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long logradouroTipoPK;
	
	@Column(name = "nm_logradouroTipo",nullable = false, length = 50)
	private String logradouroTipoNome;
	
	@Column(name = "ds_logradouroTipo")
	private String logradouroTipoDescricao;

	@ManyToOne
	private Colaborador colaborador;
	
	public LogradouroTipo() {
		super();
	}

	public long getLogradouroTipoPK() {
		return logradouroTipoPK;
	}

	public void setLogradouroTipoPK(long logradouroTipoPK) {
		this.logradouroTipoPK = logradouroTipoPK;
	}

	public String getLogradouroTipoNome() {
		return logradouroTipoNome;
	}

	public void setLogradouroTipoNome(String logradouroTipoNome) {
		this.logradouroTipoNome = logradouroTipoNome;
	}

	public String getLogradouroTipoDescricao() {
		return logradouroTipoDescricao;
	}

	public void setLogradouroTipoDescricao(String logradouroTipoDescricao) {
		this.logradouroTipoDescricao = logradouroTipoDescricao;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logradouroTipoNome == null) ? 0 : logradouroTipoNome.hashCode());
		result = prime * result + (int) (logradouroTipoPK ^ (logradouroTipoPK >>> 32));
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
		LogradouroTipo other = (LogradouroTipo) obj;
		if (logradouroTipoNome == null) {
			if (other.logradouroTipoNome != null)
				return false;
		} else if (!logradouroTipoNome.equals(other.logradouroTipoNome))
			return false;
		if (logradouroTipoPK != other.logradouroTipoPK)
			return false;
		return true;
	}

}
