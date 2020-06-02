package br.com.j4business.saga.unidadeorganizacionalprocesso.model;

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
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;

@Entity
@Table(name = "unidadeorganizacional_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_unidadeorganizacional","id_processo"}, name="unidadeorganizacionalProcessoUnique"))

public class UnidadeorganizacionalProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_unidadeorganizacionalprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long unidadeorganizacionalProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_unidadeorganizacional")
	private Unidadeorganizacional unidadeorganizacional;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "up_status")
	private String unidadeorganizacionalProcessoStatus;

	@Column(name = "up_motivooperacao")
	private String unidadeorganizacionalProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public UnidadeorganizacionalProcesso() {
		super();
	}

	public UnidadeorganizacionalProcesso(Unidadeorganizacional unidadeorganizacional, Processo processo, int unidadeorganizacionalProcessoSequencia) {
		super();
		this.unidadeorganizacional = unidadeorganizacional;
		this.processo = processo;
	}

	public long getUnidadeorganizacionalProcessoPK() {
		return unidadeorganizacionalProcessoPK;
	}

	public void setUnidadeorganizacionalProcessoPK(long unidadeorganizacionalProcessoPK) {
		this.unidadeorganizacionalProcessoPK = unidadeorganizacionalProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Unidadeorganizacional getUnidadeorganizacional() {
		return unidadeorganizacional;
	}

	public void setUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional) {
		this.unidadeorganizacional = unidadeorganizacional;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unidadeorganizacional.getUnidadeorganizacionalNome() == null) ? 0 : unidadeorganizacional.getUnidadeorganizacionalNome().hashCode());
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
		UnidadeorganizacionalProcesso other = (UnidadeorganizacionalProcesso) obj;
		if (unidadeorganizacional.getUnidadeorganizacionalNome() == null) {
			if (other.unidadeorganizacional.getUnidadeorganizacionalNome() != null)
				return false;
		} else if (!unidadeorganizacional.getUnidadeorganizacionalNome().equals(other.unidadeorganizacional.getUnidadeorganizacionalNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public String getUnidadeorganizacionalProcessoStatus() {
		return unidadeorganizacionalProcessoStatus;
	}

	public void setUnidadeorganizacionalProcessoStatus(String unidadeorganizacionalProcessoStatus) {
		this.unidadeorganizacionalProcessoStatus = unidadeorganizacionalProcessoStatus;
	}

	public String getUnidadeorganizacionalProcessoMotivoOperacao() {
		return unidadeorganizacionalProcessoMotivoOperacao;
	}

	public void setUnidadeorganizacionalProcessoMotivoOperacao(String unidadeorganizacionalProcessoMotivoOperacao) {
		this.unidadeorganizacionalProcessoMotivoOperacao = unidadeorganizacionalProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
