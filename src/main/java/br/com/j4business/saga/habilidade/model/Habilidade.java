package br.com.j4business.saga.habilidade.model;

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
@Table(name = "habilidade", uniqueConstraints=@UniqueConstraint(columnNames={"nm_habilidade"}, name="habilidadeNome"))
public class Habilidade {

	@Id
	@Column(name = "id_habilidade")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long habilidadePK;
	
	@Column(name = "nm_habilidade",nullable = false, length = 50)
	private String habilidadeNome;
	
	@Column(name = "ds_habilidade")
	private String habilidadeDescricao;

	@Column(name = "ha_status")
	private String habilidadeStatus;

	@Column(name = "ha_motivooperacao")
	private String habilidadeMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Habilidade() {
		super();
	}

	public long getHabilidadePK() {
		return habilidadePK;
	}

	public void setHabilidadePK(long habilidadePK) {
		this.habilidadePK = habilidadePK;
	}

	public String getHabilidadeNome() {
		return habilidadeNome;
	}

	public void setHabilidadeNome(String habilidadeNome) {
		this.habilidadeNome = habilidadeNome;
	}

	public String getHabilidadeDescricao() {
		return habilidadeDescricao;
	}

	public void setHabilidadeDescricao(String habilidadeDescricao) {
		this.habilidadeDescricao = habilidadeDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((habilidadeNome == null) ? 0 : habilidadeNome.hashCode());
		result = prime * result + (int) (habilidadePK ^ (habilidadePK >>> 32));
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
		Habilidade other = (Habilidade) obj;
		if (habilidadeNome == null) {
			if (other.habilidadeNome != null)
				return false;
		} else if (!habilidadeNome.equals(other.habilidadeNome))
			return false;
		if (habilidadePK != other.habilidadePK)
			return false;
		return true;
	}

	public String getHabilidadeStatus() {
		return habilidadeStatus;
	}

	public void setHabilidadeStatus(String habilidadeStatus) {
		this.habilidadeStatus = habilidadeStatus;
	}

	public String getHabilidadeMotivoOperacao() {
		return habilidadeMotivoOperacao;
	}

	public void setHabilidadeMotivoOperacao(String habilidadeMotivoOperacao) {
		this.habilidadeMotivoOperacao = habilidadeMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
