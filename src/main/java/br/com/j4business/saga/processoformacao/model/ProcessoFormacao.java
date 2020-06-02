package br.com.j4business.saga.processoformacao.model;

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

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.formacao.model.Formacao;

@Entity
@Table(name = "processo_formacao", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_formacao"}, name="processoFormacaoUnique"))

public class ProcessoFormacao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processoformacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoFormacaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_formacao")
	private Formacao formacao;
	
	@Column(name = "pf_status",nullable = false, length = 200)
	private String processoFormacaoStatus;

	@Column(name = "pf_motivooperacao",nullable = false, length = 200)
	private String processoFormacaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoFormacao() {
		super();
	}

	public ProcessoFormacao(Processo processo, Formacao formacao, int processoFormacaoSequencia) {
		super();
		this.processo = processo;
		this.formacao = formacao;
	}

	public long getProcessoFormacaoPK() {
		return processoFormacaoPK;
	}

	public void setProcessoFormacaoPK(long processoFormacaoPK) {
		this.processoFormacaoPK = processoFormacaoPK;
	}

	public Formacao getFormacao() {
		return formacao;
	}

	public void setFormacao(Formacao formacao) {
		this.formacao = formacao;
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
		result = prime * result + ((formacao.getFormacaoNome() == null) ? 0 : formacao.getFormacaoNome().hashCode());
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
		ProcessoFormacao other = (ProcessoFormacao) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (formacao.getFormacaoNome() == null) {
			if (other.formacao.getFormacaoNome() != null)
				return false;
		} else if (!formacao.getFormacaoNome().equals(other.formacao.getFormacaoNome()))
			return false;
		return true;
	}

	public String getProcessoFormacaoStatus() {
		return processoFormacaoStatus;
	}

	public void setProcessoFormacaoStatus(String processoFormacaoStatus) {
		this.processoFormacaoStatus = processoFormacaoStatus;
	}

	public String getProcessoFormacaoMotivoOperacao() {
		return processoFormacaoMotivoOperacao;
	}

	public void setProcessoFormacaoMotivoOperacao(String processoFormacaoMotivoOperacao) {
		this.processoFormacaoMotivoOperacao = processoFormacaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
