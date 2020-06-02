package br.com.j4business.saga.processoatividade.model;

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

import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.processo.model.Processo;

@Entity
@Table(name = "processo_atividade", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_atividade"}, name="processoAtividadeUnique"))

public class ProcessoAtividade implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processoatividade")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoAtividadePK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@ManyToOne
	@JoinColumn(name="id_atividade")
	private Atividade atividade;    
	
	@Column(name = "sq_processoatividade",nullable = false)
	private String processoAtividadeSequencia;

	@Column(name = "pa_status",nullable = false, length = 200)
	private String processoAtividadeStatus;

	@Column(name = "pa_motivooperacao",nullable = false, length = 200)
	private String processoAtividadeMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoAtividade() {
		super();
	}

	public ProcessoAtividade(Processo processo, Atividade atividade, String processoAtividadeSequencia) {
		super();
		this.processo = processo;
		this.atividade = atividade;
		this.processoAtividadeSequencia = processoAtividadeSequencia;
	}

	public long getProcessoAtividadePK() {
		return processoAtividadePK;
	}

	public void setProcessoAtividadePK(long processoAtividadePK) {
		this.processoAtividadePK = processoAtividadePK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atividade.getAtividadeNome() == null) ? 0 : atividade.getAtividadeNome().hashCode());
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
		result = prime * result + ((processoAtividadeSequencia == null) ? 0 : processoAtividadeSequencia.hashCode());
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
		ProcessoAtividade other = (ProcessoAtividade) obj;
		if (atividade.getAtividadeNome() == null) {
			if (other.atividade.getAtividadeNome() != null)
				return false;
		} else if (!atividade.getAtividadeNome().equals(other.atividade.getAtividadeNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (processoAtividadeSequencia == null) {
			if (other.processoAtividadeSequencia != null)
				return false;
		} else if (!processoAtividadeSequencia.equals(other.processoAtividadeSequencia))
			return false;
		return true;
	}

	public String getProcessoAtividadeSequencia() {
		return processoAtividadeSequencia;
	}

	public void setProcessoAtividadeSequencia(String processoAtividadeSequencia) {
		this.processoAtividadeSequencia = processoAtividadeSequencia;
	}

	public String getProcessoAtividadeStatus() {
		return processoAtividadeStatus;
	}

	public void setProcessoAtividadeStatus(String processoAtividadeStatus) {
		this.processoAtividadeStatus = processoAtividadeStatus;
	}

	public String getProcessoAtividadeMotivoOperacao() {
		return processoAtividadeMotivoOperacao;
	}

	public void setProcessoAtividadeMotivoOperacao(String processoAtividadeMotivoOperacao) {
		this.processoAtividadeMotivoOperacao = processoAtividadeMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
