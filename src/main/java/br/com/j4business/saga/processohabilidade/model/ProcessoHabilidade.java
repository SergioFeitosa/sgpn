package br.com.j4business.saga.processohabilidade.model;

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

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.habilidade.model.Habilidade;

@Entity
@Table(name = "processo_habilidade", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_habilidade"}, name="processoHabilidadeUnique"))

public class ProcessoHabilidade implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processohabilidade")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoHabilidadePK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_habilidade")
	private Habilidade habilidade;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String processoHabilidadeStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String processoHabilidadeMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoHabilidade() {
		super();
	}

	public ProcessoHabilidade(Processo processo, Habilidade habilidade, int processoHabilidadeSequencia) {
		super();
		this.processo = processo;
		this.habilidade = habilidade;
	}

	public long getProcessoHabilidadePK() {
		return processoHabilidadePK;
	}

	public void setProcessoHabilidadePK(long processoHabilidadePK) {
		this.processoHabilidadePK = processoHabilidadePK;
	}

	public Habilidade getHabilidade() {
		return habilidade;
	}

	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
		result = prime * result + ((habilidade.getHabilidadeNome() == null) ? 0 : habilidade.getHabilidadeNome().hashCode());
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
		ProcessoHabilidade other = (ProcessoHabilidade) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (habilidade.getHabilidadeNome() == null) {
			if (other.habilidade.getHabilidadeNome() != null)
				return false;
		} else if (!habilidade.getHabilidadeNome().equals(other.habilidade.getHabilidadeNome()))
			return false;
		return true;
	}

	public String getProcessoHabilidadeStatus() {
		return processoHabilidadeStatus;
	}

	public void setProcessoHabilidadeStatus(String processoHabilidadeStatus) {
		this.processoHabilidadeStatus = processoHabilidadeStatus;
	}

	public String getProcessoHabilidadeMotivoOperacao() {
		return processoHabilidadeMotivoOperacao;
	}

	public void setProcessoHabilidadeMotivoOperacao(String processoHabilidadeMotivoOperacao) {
		this.processoHabilidadeMotivoOperacao = processoHabilidadeMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
