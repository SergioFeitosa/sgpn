package br.com.j4business.saga.avaliacaocontrato.model;

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
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.avaliacao.model.Avaliacao;

@Entity
@Table(name = "avaliacao_contrato")

public class AvaliacaoContrato implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_avaliacaocontrato")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long avaliacaoContratoPK;
	
	@ManyToOne
	@JoinColumn(name="id_avaliacao")
	private Avaliacao avaliacao;    
	
	@ManyToOne
	@JoinColumn(name="id_estruturafisica")
	private Estruturafisica estruturafisica;
	    
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;
	    
	@ManyToOne
	@JoinColumn(name="id_unidadeorganizacional")
	private Unidadeorganizacional unidadeorganizacional;
	    
	@ManyToOne
	@JoinColumn(name="id_questionario")
	private Questionario questionario;
	    
	@JoinColumn(name="pe_avaliacaocontrato")
	private String avaliacaoContratoPeriodo;
	    
	@Column(name = "ap_status")
	private String avaliacaoContratoStatus;

	@Column(name = "ap_motivooperacao")
	private String avaliacaoContratoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public AvaliacaoContrato() {
		super();
	}

	public AvaliacaoContrato(Avaliacao avaliacao, Contrato contrato, int avaliacaoContratoSequencia) {
		super();
		this.avaliacao = avaliacao;
		this.contrato = contrato;
	}

	public long getAvaliacaoContratoPK() {
		return avaliacaoContratoPK;
	}

	public void setAvaliacaoContratoPK(long avaliacaoContratoPK) {
		this.avaliacaoContratoPK = avaliacaoContratoPK;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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
		AvaliacaoContrato other = (AvaliacaoContrato) obj;
		if (avaliacao.getAvaliacaoNome() == null) {
			if (other.avaliacao.getAvaliacaoNome() != null)
				return false;
		} else if (!avaliacao.getAvaliacaoNome().equals(other.avaliacao.getAvaliacaoNome()))
			return false;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
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

	public String getAvaliacaoContratoPeriodo() {
		return avaliacaoContratoPeriodo;
	}

	public void setAvaliacaoContratoPeriodo(String avaliacaoContratoPeriodo) {
		this.avaliacaoContratoPeriodo = avaliacaoContratoPeriodo;
	}

	public String getAvaliacaoContratoStatus() {
		return avaliacaoContratoStatus;
	}

	public void setAvaliacaoContratoStatus(String avaliacaoContratoStatus) {
		this.avaliacaoContratoStatus = avaliacaoContratoStatus;
	}

	public String getAvaliacaoContratoMotivoOperacao() {
		return avaliacaoContratoMotivoOperacao;
	}

	public void setAvaliacaoContratoMotivoOperacao(String avaliacaoContratoMotivoOperacao) {
		this.avaliacaoContratoMotivoOperacao = avaliacaoContratoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
