package br.com.j4business.saga.unidadeorganizacional.model;

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
@Table(name = "unidadeorganizacional", uniqueConstraints=@UniqueConstraint(columnNames={"nm_unidadeorganizacional"}, name="unidadeorganizacionalNome"))
public class Unidadeorganizacional {

	@Id
	@Column(name = "id_unidadeorganizacional")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long unidadeorganizacionalPK;
	
	@Column(name = "nm_unidadeorganizacional",nullable = false, length = 50)
	private String unidadeorganizacionalNome;
	
	@Column(name = "ds_unidadeorganizacional")
	private String unidadeorganizacionalDescricao;

	@Column(name = "uo_status")
	private String unidadeorganizacionalStatus;

	@Column(name = "uo_motivooperacao")
	private String unidadeorganizacionalMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	
	public Unidadeorganizacional() {
		super();
	}

	public long getUnidadeorganizacionalPK() {
		return unidadeorganizacionalPK;
	}

	public void setUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		this.unidadeorganizacionalPK = unidadeorganizacionalPK;
	}

	public String getUnidadeorganizacionalNome() {
		return unidadeorganizacionalNome;
	}

	public void setUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		this.unidadeorganizacionalNome = unidadeorganizacionalNome;
	}

	public String getUnidadeorganizacionalDescricao() {
		return unidadeorganizacionalDescricao;
	}

	public void setUnidadeorganizacionalDescricao(String unidadeorganizacionalDescricao) {
		this.unidadeorganizacionalDescricao = unidadeorganizacionalDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unidadeorganizacionalNome == null) ? 0 : unidadeorganizacionalNome.hashCode());
		result = prime * result + (int) (unidadeorganizacionalPK ^ (unidadeorganizacionalPK >>> 32));
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
		Unidadeorganizacional other = (Unidadeorganizacional) obj;
		if (unidadeorganizacionalNome == null) {
			if (other.unidadeorganizacionalNome != null)
				return false;
		} else if (!unidadeorganizacionalNome.equals(other.unidadeorganizacionalNome))
			return false;
		if (unidadeorganizacionalPK != other.unidadeorganizacionalPK)
			return false;
		return true;
	}

	public String getUnidadeorganizacionalStatus() {
		return unidadeorganizacionalStatus;
	}

	public void setUnidadeorganizacionalStatus(String unidadeorganizacionalStatus) {
		this.unidadeorganizacionalStatus = unidadeorganizacionalStatus;
	}

	public String getUnidadeorganizacionalMotivoOperacao() {
		return unidadeorganizacionalMotivoOperacao;
	}

	public void setUnidadeorganizacionalMotivoOperacao(String unidadeorganizacionalMotivoOperacao) {
		this.unidadeorganizacionalMotivoOperacao = unidadeorganizacionalMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
