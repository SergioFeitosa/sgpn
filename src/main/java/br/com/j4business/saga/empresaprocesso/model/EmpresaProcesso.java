package br.com.j4business.saga.empresaprocesso.model;

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
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.empresa.model.Empresa;

@Entity
@Table(name = "empresa_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_empresa","id_processo"}, name="empresaProcessoUnique"))

public class EmpresaProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_empresaprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long empresaProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_empresa")
	private Empresa empresa;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "ep_status",nullable = false, length = 200)
	private String empresaProcessoStatus;

	@Column(name = "ep_motivooperacao",nullable = false, length = 200)
	private String empresaProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public EmpresaProcesso() {
		super();
	}

	public EmpresaProcesso(Empresa empresa, Processo processo, int empresaProcessoSequencia) {
		super();
		this.empresa = empresa;
		this.processo = processo;
	}

	public long getEmpresaProcessoPK() {
		return empresaProcessoPK;
	}

	public void setEmpresaProcessoPK(long empresaProcessoPK) {
		this.empresaProcessoPK = empresaProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa.getPessoaNome() == null) ? 0 : empresa.getPessoaNome().hashCode());
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
		EmpresaProcesso other = (EmpresaProcesso) obj;
		if (empresa.getPessoaNome() == null) {
			if (other.empresa.getPessoaNome() != null)
				return false;
		} else if (!empresa.getPessoaNome().equals(other.empresa.getPessoaNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public String getEmpresaProcessoStatus() {
		return empresaProcessoStatus;
	}

	public void setEmpresaProcessoStatus(String empresaProcessoStatus) {
		this.empresaProcessoStatus = empresaProcessoStatus;
	}

	public String getEmpresaProcessoMotivoOperacao() {
		return empresaProcessoMotivoOperacao;
	}

	public void setEmpresaProcessoMotivoOperacao(String empresaProcessoMotivoOperacao) {
		this.empresaProcessoMotivoOperacao = empresaProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
