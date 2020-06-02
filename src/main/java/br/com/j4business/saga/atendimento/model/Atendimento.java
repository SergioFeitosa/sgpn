package br.com.j4business.saga.atendimento.model;


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
@Table(name = "atendimento", uniqueConstraints=@UniqueConstraint(columnNames={"nm_atendimento"}, name="atendimentoNome"))
public class Atendimento {

	@Id
	@Column(name = "id_atendimento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long atendimentoPK;
	
	@Column(name = "nm_atendimento",nullable = false, length = 50)
	private String atendimentoNome;
	
	@Column(name = "ds_atendimento")
	private String atendimentoDescricao;

	@Column(name = "at_status")
	private String atendimentoStatus;
	 
	@Column(name = "at_motivooperacao")
	private String atendimentoMotivoOperacao;
	 
	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Atendimento() {
		super();
	}

	public long getAtendimentoPK() {
		return atendimentoPK;
	}

	public void setAtendimentoPK(long atendimentoPK) {
		this.atendimentoPK = atendimentoPK;
	}

	public String getAtendimentoNome() {
		return atendimentoNome;
	}

	public void setAtendimentoNome(String atendimentoNome) {
		this.atendimentoNome = atendimentoNome;
	}

	public String getAtendimentoDescricao() {
		return atendimentoDescricao;
	}

	public void setAtendimentoDescricao(String atendimentoDescricao) {
		this.atendimentoDescricao = atendimentoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atendimentoNome == null) ? 0 : atendimentoNome.hashCode());
		result = prime * result + (int) (atendimentoPK ^ (atendimentoPK >>> 32));
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
		Atendimento other = (Atendimento) obj;
		if (atendimentoNome == null) {
			if (other.atendimentoNome != null)
				return false;
		} else if (!atendimentoNome.equals(other.atendimentoNome))
			return false;
		if (atendimentoPK != other.atendimentoPK)
			return false;
		return true;
	}

	public String getAtendimentoMotivoOperacao() {
		return atendimentoMotivoOperacao;
	}

	public void setAtendimentoMotivoOperacao(String atendimentoMotivoOperacao) {
		this.atendimentoMotivoOperacao = atendimentoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getAtendimentoStatus() {
		return atendimentoStatus;
	}

	public void setAtendimentoStatus(String atendimentoStatus) {
		this.atendimentoStatus = atendimentoStatus;
	}

}
