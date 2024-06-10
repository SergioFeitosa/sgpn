package br.com.j4business.saga.avaliacaoprocesso.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.avaliacao.model.Avaliacao;

@Entity
@Table(name = "avaliacao_processo")

public class AvaliacaoProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_avaliacaoprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long avaliacaoProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_avaliacao")
	private Avaliacao avaliacao;    
	
	@ManyToOne
	@JoinColumn(name="id_estruturafisica")
	private Estruturafisica estruturafisica;
	    
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@ManyToOne
	@JoinColumn(name="id_unidadeorganizacional")
	private Unidadeorganizacional unidadeorganizacional;
	    
	@ManyToOne
	@JoinColumn(name="id_questionario")
	private Questionario questionario;
	    
	@JoinColumn(name="pe_avaliacaoprocesso")
	private String avaliacaoProcessoPeriodo;
	    
	@Column(name = "ap_status")
	private String avaliacaoProcessoStatus;

	@Column(name = "ap_motivooperacao")
	private String avaliacaoProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public AvaliacaoProcesso() {
		super();
	}

	public AvaliacaoProcesso(Avaliacao avaliacao, Processo processo, int avaliacaoProcessoSequencia) {
		super();
		this.avaliacao = avaliacao;
		this.processo = processo;
	}

	public long getAvaliacaoProcessoPK() {
		return avaliacaoProcessoPK;
	}

	public void setAvaliacaoProcessoPK(long avaliacaoProcessoPK) {
		this.avaliacaoProcessoPK = avaliacaoProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avaliacao.getAvaliacaoNome() == null) ? 0 : avaliacao.getAvaliacaoNome().hashCode());
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
		AvaliacaoProcesso other = (AvaliacaoProcesso) obj;
		if (avaliacao.getAvaliacaoNome() == null) {
			if (other.avaliacao.getAvaliacaoNome() != null)
				return false;
		} else if (!avaliacao.getAvaliacaoNome().equals(other.avaliacao.getAvaliacaoNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public Estruturafisica getEstruturafisica() {
		return estruturafisica;
	}

	public void setEstruturafisica(Estruturafisica estruturafisica) {
		this.estruturafisica = estruturafisica;
	}

	public Unidadeorganizacional getUnidadeorganizacional() {
		return unidadeorganizacional;
	}

	public void setUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional) {
		this.unidadeorganizacional = unidadeorganizacional;
	}

	public String getAvaliacaoProcessoPeriodo() {
		return avaliacaoProcessoPeriodo;
	}

	public void setAvaliacaoProcessoPeriodo(String avaliacaoProcessoPeriodo) {
		this.avaliacaoProcessoPeriodo = avaliacaoProcessoPeriodo;
	}

	public String getAvaliacaoProcessoStatus() {
		return avaliacaoProcessoStatus;
	}

	public void setAvaliacaoProcessoStatus(String avaliacaoProcessoStatus) {
		this.avaliacaoProcessoStatus = avaliacaoProcessoStatus;
	}

	public String getAvaliacaoProcessoMotivoOperacao() {
		return avaliacaoProcessoMotivoOperacao;
	}

	public void setAvaliacaoProcessoMotivoOperacao(String avaliacaoProcessoMotivoOperacao) {
		this.avaliacaoProcessoMotivoOperacao = avaliacaoProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
