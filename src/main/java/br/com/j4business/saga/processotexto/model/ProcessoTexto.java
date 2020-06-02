package br.com.j4business.saga.processotexto.model;

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
import br.com.j4business.saga.texto.model.Texto;

@Entity
@Table(name = "processo_texto", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_texto"}, name="processoTextoUnique"))

public class ProcessoTexto implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processotexto")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoTextoPK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_texto")
	private Texto texto;
	    
	@Column(name = "ep_status",nullable = false, length = 20)
	private String processoTextoStatus;

	@Column(name = "ep_motivooperacao",nullable = false, length = 200)
	private String processoTextoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoTexto() {
		super();
	}

	public ProcessoTexto(Processo processo, Texto texto, int processoTextoSequencia) {
		super();
		this.processo = processo;
		this.texto = texto;
	}

	public long getProcessoTextoPK() {
		return processoTextoPK;
	}

	public void setProcessoTextoPK(long processoTextoPK) {
		this.processoTextoPK = processoTextoPK;
	}

	public Texto getTexto() {
		return texto;
	}

	public void setTexto(Texto texto) {
		this.texto = texto;
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
		result = prime * result + ((texto.getTextoNome() == null) ? 0 : texto.getTextoNome().hashCode());
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
		ProcessoTexto other = (ProcessoTexto) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (texto.getTextoNome() == null) {
			if (other.texto.getTextoNome() != null)
				return false;
		} else if (!texto.getTextoNome().equals(other.texto.getTextoNome()))
			return false;
		return true;
	}

	public String getProcessoTextoStatus() {
		return processoTextoStatus;
	}

	public void setProcessoTextoStatus(String processoTextoStatus) {
		this.processoTextoStatus = processoTextoStatus;
	}

	public String getProcessoTextoMotivoOperacao() {
		return processoTextoMotivoOperacao;
	}

	public void setProcessoTextoMotivoOperacao(String processoTextoMotivoOperacao) {
		this.processoTextoMotivoOperacao = processoTextoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
