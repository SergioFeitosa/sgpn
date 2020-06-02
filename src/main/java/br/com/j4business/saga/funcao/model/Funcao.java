package br.com.j4business.saga.funcao.model;

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
@Table(name = "funcao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_funcao"}, name="funcaoNome"))
public class Funcao {

	@Id
	@Column(name = "id_funcao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long funcaoPK;
	
	@Column(name = "nm_funcao",nullable = false, length = 50)
	private String funcaoNome;
	
	@Column(name = "ds_funcao")
	private String funcaoDescricao;
	
	@Column(name = "fu_status")
	private String funcaoStatus;

	@Column(name = "fu_motivooperacao")
	private String funcaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Funcao() {
		super();
	}

	public long getFuncaoPK() {
		return funcaoPK;
	}

	public void setFuncaoPK(long funcaoPK) {
		this.funcaoPK = funcaoPK;
	}

	public String getFuncaoNome() {
		return funcaoNome;
	}

	public void setFuncaoNome(String funcaoNome) {
		this.funcaoNome = funcaoNome;
	}

	public String getFuncaoDescricao() {
		return funcaoDescricao;
	}

	public void setFuncaoDescricao(String funcaoDescricao) {
		this.funcaoDescricao = funcaoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcaoNome == null) ? 0 : funcaoNome.hashCode());
		result = prime * result + (int) (funcaoPK ^ (funcaoPK >>> 32));
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
		Funcao other = (Funcao) obj;
		if (funcaoNome == null) {
			if (other.funcaoNome != null)
				return false;
		} else if (!funcaoNome.equals(other.funcaoNome))
			return false;
		if (funcaoPK != other.funcaoPK)
			return false;
		return true;
	}

	public String getFuncaoStatus() {
		return funcaoStatus;
	}

	public void setFuncaoStatus(String funcaoStatus) {
		this.funcaoStatus = funcaoStatus;
	}

	public String getFuncaoMotivoOperacao() {
		return funcaoMotivoOperacao;
	}

	public void setFuncaoMotivoOperacao(String funcaoMotivoOperacao) {
		this.funcaoMotivoOperacao = funcaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
