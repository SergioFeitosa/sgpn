package br.com.j4business.saga.ocorrenciaatendimento.model;

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
import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.ocorrencia.model.Ocorrencia;

@Entity
@Table(name = "ocorrencia_atendimento", uniqueConstraints=@UniqueConstraint(columnNames={"id_ocorrencia","id_atendimento"}, name="ocorrenciaAtendimentoUnique"))
public class OcorrenciaAtendimento implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_ocorrenciaatendimento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ocorrenciaAtendimentoPK;
	
	@ManyToOne
	@JoinColumn(name="id_ocorrencia")
	private Ocorrencia ocorrencia;    
	
	@ManyToOne
	@JoinColumn(name="id_atendimento")
	private Atendimento atendimento;
	    
	@Column(name = "sq_ocorrenciaatendimento",nullable = false, length = 200)
	private int ocorrenciaAtendimentoSequencia;

	@Column(name = "oa_status")
	private String ocorrenciaAtendimentoStatus;

	@Column(name = "oa_motivooperacao")
	private String ocorrenciaAtendimentoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public OcorrenciaAtendimento() {
		super();
	}

	public OcorrenciaAtendimento(Ocorrencia ocorrencia, Atendimento atendimento, int ocorrenciaAtendimentoSequencia) {
		super();
		this.ocorrencia = ocorrencia;
		this.atendimento = atendimento;
		this.ocorrenciaAtendimentoSequencia = ocorrenciaAtendimentoSequencia;
	}

	public long getOcorrenciaAtendimentoPK() {
		return ocorrenciaAtendimentoPK;
	}

	public void setOcorrenciaAtendimentoPK(long ocorrenciaAtendimentoPK) {
		this.ocorrenciaAtendimentoPK = ocorrenciaAtendimentoPK;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ocorrencia.getOcorrenciaNome() == null) ? 0 : ocorrencia.getOcorrenciaNome().hashCode());
		result = prime * result + ((atendimento.getAtendimentoNome() == null) ? 0 : atendimento.getAtendimentoNome().hashCode());
		result = prime * result + ((ocorrenciaAtendimentoSequencia+"" == null) ? 0 : ocorrenciaAtendimentoSequencia+"".hashCode());
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
		OcorrenciaAtendimento other = (OcorrenciaAtendimento) obj;
		if (ocorrencia.getOcorrenciaNome() == null) {
			if (other.ocorrencia.getOcorrenciaNome() != null)
				return false;
		} else if (!ocorrencia.getOcorrenciaNome().equals(other.ocorrencia.getOcorrenciaNome()))
			return false;
		if (atendimento.getAtendimentoNome() == null) {
			if (other.atendimento.getAtendimentoNome() != null)
				return false;
		} else if (!atendimento.getAtendimentoNome().equals(other.atendimento.getAtendimentoNome()))
			return false;
		return true;
	}

	public int getOcorrenciaAtendimentoSequencia() {
		return ocorrenciaAtendimentoSequencia;
	}

	public void setOcorrenciaAtendimentoSequencia(int ocorrenciaAtendimentoSequencia) {
		this.ocorrenciaAtendimentoSequencia = ocorrenciaAtendimentoSequencia;
	}

	public String getOcorrenciaAtendimentoStatus() {
		return ocorrenciaAtendimentoStatus;
	}

	public void setOcorrenciaAtendimentoStatus(String ocorrenciaAtendimentoStatus) {
		this.ocorrenciaAtendimentoStatus = ocorrenciaAtendimentoStatus;
	}

	public String getOcorrenciaAtendimentoMotivoOperacao() {
		return ocorrenciaAtendimentoMotivoOperacao;
	}

	public void setOcorrenciaAtendimentoMotivoOperacao(String ocorrenciaAtendimentoMotivoOperacao) {
		this.ocorrenciaAtendimentoMotivoOperacao = ocorrenciaAtendimentoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
