package br.com.j4business.saga.avaliacao.model;


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
@Table(name = "avaliacao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_avaliacao"}, name="avaliacaoNome"))

public class Avaliacao {

	@Id
	@Column(name = "id_avaliacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long avaliacaoPK;
	
	@Column(name = "nm_avaliacao",nullable = false, length = 50)
	private String avaliacaoNome;
	
	@Column(name = "ds_avaliacao")
	private String avaliacaoDescricao;

	@Column(name = "al_motivooperacao")
	private String avaliacaoMotivoOperacao;

	@Column(name = "al_status")
	private String avaliacaoStatus;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Avaliacao() {
		super();
	}

	public long getAvaliacaoPK() {
		return avaliacaoPK;
	}

	public void setAvaliacaoPK(long avaliacaoPK) {
		this.avaliacaoPK = avaliacaoPK;
	}

	public String getAvaliacaoNome() {
		return avaliacaoNome;
	}

	public void setAvaliacaoNome(String avaliacaoNome) {
		this.avaliacaoNome = avaliacaoNome;
	}

	public String getAvaliacaoDescricao() {
		return avaliacaoDescricao;
	}

	public void setAvaliacaoDescricao(String avaliacaoDescricao) {
		this.avaliacaoDescricao = avaliacaoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avaliacaoNome == null) ? 0 : avaliacaoNome.hashCode());
		result = prime * result + (int) (avaliacaoPK ^ (avaliacaoPK >>> 32));
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
		Avaliacao other = (Avaliacao) obj;
		if (avaliacaoNome == null) {
			if (other.avaliacaoNome != null)
				return false;
		} else if (!avaliacaoNome.equals(other.avaliacaoNome))
			return false;
		if (avaliacaoPK != other.avaliacaoPK)
			return false;
		return true;
	}

	public String getAvaliacaoMotivoOperacao() {
		return avaliacaoMotivoOperacao;
	}

	public void setAvaliacaoMotivoOperacao(String avaliacaoMotivoOperacao) {
		this.avaliacaoMotivoOperacao = avaliacaoMotivoOperacao;
	}

	public String getAvaliacaoStatus() {
		return avaliacaoStatus;
	}

	public void setAvaliacaoStatus(String avaliacaoStatus) {
		this.avaliacaoStatus = avaliacaoStatus;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
