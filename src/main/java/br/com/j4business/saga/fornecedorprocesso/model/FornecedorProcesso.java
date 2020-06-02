package br.com.j4business.saga.fornecedorprocesso.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.fornecedor.model.Fornecedor;

@Entity
@Table(name = "fornecedor_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_fornecedor","id_processo"}, name="fornecedorProcessoUnique"))
public class FornecedorProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_fornecedorprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fornecedorProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_fornecedor")
	private Fornecedor fornecedor;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "fp_status",nullable = false, length = 200)
	private String fornecedorProcessoStatus;

	@Column(name = "fp_motivooperacao",nullable = false, length = 200)
	private String fornecedorProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public FornecedorProcesso() {
		super();
	}

	public FornecedorProcesso(Fornecedor fornecedor, Processo processo, int fornecedorProcessoSequencia) {
		super();
		this.fornecedor = fornecedor;
		this.processo = processo;
	}

	public long getFornecedorProcessoPK() {
		return fornecedorProcessoPK;
	}

	public void setFornecedorProcessoPK(long fornecedorProcessoPK) {
		this.fornecedorProcessoPK = fornecedorProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fornecedor.getPessoaNome() == null) ? 0 : fornecedor.getPessoaNome().hashCode());
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
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
		FornecedorProcesso other = (FornecedorProcesso) obj;
		if (fornecedor.getPessoaNome() == null) {
			if (other.fornecedor.getPessoaNome() != null)
				return false;
		} else if (!fornecedor.getPessoaNome().equals(other.fornecedor.getPessoaNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public String getFornecedorProcessoStatus() {
		return fornecedorProcessoStatus;
	}

	public void setFornecedorProcessoStatus(String fornecedorProcessoStatus) {
		this.fornecedorProcessoStatus = fornecedorProcessoStatus;
	}

	public String getFornecedorProcessoMotivoOperacao() {
		return fornecedorProcessoMotivoOperacao;
	}

	public void setFornecedorProcessoMotivoOperacao(String fornecedorProcessoMotivoOperacao) {
		this.fornecedorProcessoMotivoOperacao = fornecedorProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
