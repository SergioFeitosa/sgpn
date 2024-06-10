package br.com.j4business.saga.processocertificacao.model;

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
import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "processo_certificacao", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_certificacao"}, name="processoCertificacaoUnique"))
public class ProcessoCertificacao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processocertificacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoCertificacaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_certificacao")
	private Certificacao certificacao;
	    
	@Column(name = "pc_status",nullable = false, length = 200)
	private String processoCertificacaoStatus;

	@Column(name = "pc_motivooperacao",nullable = false, length = 200)
	private String processoCertificacaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	public ProcessoCertificacao() {
		super();
	}

	public ProcessoCertificacao(Processo processo, Certificacao certificacao, int processoCertificacaoSequencia) {
		super();
		this.processo = processo;
		this.certificacao = certificacao;
	}

	public long getProcessoCertificacaoPK() {
		return processoCertificacaoPK;
	}

	public void setProcessoCertificacaoPK(long processoCertificacaoPK) {
		this.processoCertificacaoPK = processoCertificacaoPK;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
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
		result = prime * result + ((certificacao.getCertificacaoNome() == null) ? 0 : certificacao.getCertificacaoNome().hashCode());
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
		ProcessoCertificacao other = (ProcessoCertificacao) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (certificacao.getCertificacaoNome() == null) {
			if (other.certificacao.getCertificacaoNome() != null)
				return false;
		} else if (!certificacao.getCertificacaoNome().equals(other.certificacao.getCertificacaoNome()))
			return false;
		return true;
	}

	public String getProcessoCertificacaoStatus() {
		return processoCertificacaoStatus;
	}

	public void setProcessoCertificacaoStatus(String processoCertificacaoStatus) {
		this.processoCertificacaoStatus = processoCertificacaoStatus;
	}

	public String getProcessoCertificacaoMotivoOperacao() {
		return processoCertificacaoMotivoOperacao;
	}

	public void setProcessoCertificacaoMotivoOperacao(String processoCertificacaoMotivoOperacao) {
		this.processoCertificacaoMotivoOperacao = processoCertificacaoMotivoOperacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
