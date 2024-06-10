package br.com.j4business.saga.elemento.model;

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
@Table(name = "elemento", uniqueConstraints=@UniqueConstraint(columnNames={"nm_elemento"}, name="elementoNome"))
public class Elemento {

	@Id
	@Column(name = "id_elemento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long elementoPK;
	
	@Column(name = "nm_elemento",nullable = false, length = 50)
	private String elementoNome;
	
	@Column(name = "ds_elemento")
	private String elementoDescricao;

	@Column(name = "el_status")
	private String elementoStatus;

	@Column(name = "el_motivooperacao")
	private String elementoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Elemento() {
		super();
	}

	public long getElementoPK() {
		return elementoPK;
	}

	public void setElementoPK(long elementoPK) {
		this.elementoPK = elementoPK;
	}

	public String getElementoNome() {
		return elementoNome;
	}

	public void setElementoNome(String elementoNome) {
		this.elementoNome = elementoNome;
	}

	public String getElementoDescricao() {
		return elementoDescricao;
	}

	public void setElementoDescricao(String elementoDescricao) {
		this.elementoDescricao = elementoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementoNome == null) ? 0 : elementoNome.hashCode());
		result = prime * result + (int) (elementoPK ^ (elementoPK >>> 32));
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
		Elemento other = (Elemento) obj;
		if (elementoNome == null) {
			if (other.elementoNome != null)
				return false;
		} else if (!elementoNome.equals(other.elementoNome))
			return false;
		if (elementoPK != other.elementoPK)
			return false;
		return true;
	}

	public String getElementoStatus() {
		return elementoStatus;
	}

	public void setElementoStatus(String elementoStatus) {
		this.elementoStatus = elementoStatus;
	}

	public String getElementoMotivoOperacao() {
		return elementoMotivoOperacao;
	}

	public void setElementoMotivoOperacao(String elementoMotivoOperacao) {
		this.elementoMotivoOperacao = elementoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
