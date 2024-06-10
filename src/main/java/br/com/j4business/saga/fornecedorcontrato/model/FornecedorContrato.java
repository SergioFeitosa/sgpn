package br.com.j4business.saga.fornecedorcontrato.model;

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
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.fornecedor.model.Fornecedor;

@Entity
@Table(name = "fornecedor_contrato", uniqueConstraints=@UniqueConstraint(columnNames={"id_fornecedor","id_contrato"}, name="fornecedorContratoUnique"))
public class FornecedorContrato implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_fornecedorcontrato")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fornecedorContratoPK;
	
	@ManyToOne
	@JoinColumn(name="id_fornecedor")
	private Fornecedor fornecedor;    
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;
	    
	@Column(name = "fc_status",nullable = false, length = 200)
	private String fornecedorContratoStatus;

	@Column(name = "fc_motivooperacao",nullable = false, length = 200)
	private String fornecedorContratoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public FornecedorContrato() {
		super();
	}

	public FornecedorContrato(Fornecedor fornecedor, Contrato contrato, int fornecedorContratoSequencia) {
		super();
		this.fornecedor = fornecedor;
		this.contrato = contrato;
	}

	public long getFornecedorContratoPK() {
		return fornecedorContratoPK;
	}

	public void setFornecedorContratoPK(long fornecedorContratoPK) {
		this.fornecedorContratoPK = fornecedorContratoPK;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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
		result = prime * result + ((contrato.getContratoNome() == null) ? 0 : contrato.getContratoNome().hashCode());
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
		FornecedorContrato other = (FornecedorContrato) obj;
		if (fornecedor.getPessoaNome() == null) {
			if (other.fornecedor.getPessoaNome() != null)
				return false;
		} else if (!fornecedor.getPessoaNome().equals(other.fornecedor.getPessoaNome()))
			return false;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
			return false;
		return true;
	}

	public String getFornecedorContratoStatus() {
		return fornecedorContratoStatus;
	}

	public void setFornecedorContratoStatus(String fornecedorContratoStatus) {
		this.fornecedorContratoStatus = fornecedorContratoStatus;
	}

	public String getFornecedorContratoMotivoOperacao() {
		return fornecedorContratoMotivoOperacao;
	}

	public void setFornecedorContratoMotivoOperacao(String fornecedorContratoMotivoOperacao) {
		this.fornecedorContratoMotivoOperacao = fornecedorContratoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
