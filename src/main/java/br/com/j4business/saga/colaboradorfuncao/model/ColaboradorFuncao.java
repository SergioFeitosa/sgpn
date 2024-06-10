package br.com.j4business.saga.colaboradorfuncao.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.funcao.model.Funcao;

@Entity
@Table(name = "colaborador_funcao", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_funcao"}, name="colaboradorFuncaoUnique"))
public class ColaboradorFuncao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradorfuncao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorFuncaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_funcao")
	private Funcao funcao;

	@Column(name = "cc_status",nullable = false, length = 200)
	private String colaboradorFuncaoStatus;

	@Column(name = "cc_motivooperacao",nullable = false, length = 200)
	private String colaboradorFuncaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ColaboradorFuncao() {
		super();
	}

	public ColaboradorFuncao(Colaborador colaborador, Funcao funcao, int colaboradorFuncaoSequencia) {
		super();
		this.colaborador = colaborador;
		this.funcao = funcao;
	}

	public long getColaboradorFuncaoPK() {
		return colaboradorFuncaoPK;
	}

	public void setColaboradorFuncaoPK(long colaboradorFuncaoPK) {
		this.colaboradorFuncaoPK = colaboradorFuncaoPK;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
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
		result = prime * result + ((colaborador.getPessoaNome() == null) ? 0 : colaborador.getPessoaNome().hashCode());
		result = prime * result + ((funcao.getFuncaoNome() == null) ? 0 : funcao.getFuncaoNome().hashCode());
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
		ColaboradorFuncao other = (ColaboradorFuncao) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (funcao.getFuncaoNome() == null) {
			if (other.funcao.getFuncaoNome() != null)
				return false;
		} else if (!funcao.getFuncaoNome().equals(other.funcao.getFuncaoNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorFuncaoStatus() {
		return colaboradorFuncaoStatus;
	}

	public void setColaboradorFuncaoStatus(String colaboradorFuncaoStatus) {
		this.colaboradorFuncaoStatus = colaboradorFuncaoStatus;
	}

	public String getColaboradorFuncaoMotivoOperacao() {
		return colaboradorFuncaoMotivoOperacao;
	}

	public void setColaboradorFuncaoMotivoOperacao(String colaboradorFuncaoMotivoOperacao) {
		this.colaboradorFuncaoMotivoOperacao = colaboradorFuncaoMotivoOperacao;
	}

}
