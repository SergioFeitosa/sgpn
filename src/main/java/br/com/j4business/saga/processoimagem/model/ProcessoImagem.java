package br.com.j4business.saga.processoimagem.model;

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
import br.com.j4business.saga.imagem.model.Imagem;

@Entity
@Table(name = "processo_imagem", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_imagem"}, name="processoImagemUnique"))

public class ProcessoImagem implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processoimagem")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoImagemPK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_imagem")
	private Imagem imagem;
	    
	@Column(name = "ep_status",nullable = false, length = 20)
	private String processoImagemStatus;

	@Column(name = "ep_motivooperacao",nullable = false, length = 200)
	private String processoImagemMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoImagem() {
		super();
	}

	public ProcessoImagem(Processo processo, Imagem imagem, int processoImagemSequencia) {
		super();
		this.processo = processo;
		this.imagem = imagem;
	}

	public long getProcessoImagemPK() {
		return processoImagemPK;
	}

	public void setProcessoImagemPK(long processoImagemPK) {
		this.processoImagemPK = processoImagemPK;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
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
		result = prime * result + ((imagem.getImagemNome() == null) ? 0 : imagem.getImagemNome().hashCode());
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
		ProcessoImagem other = (ProcessoImagem) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (imagem.getImagemNome() == null) {
			if (other.imagem.getImagemNome() != null)
				return false;
		} else if (!imagem.getImagemNome().equals(other.imagem.getImagemNome()))
			return false;
		return true;
	}

	public String getProcessoImagemStatus() {
		return processoImagemStatus;
	}

	public void setProcessoImagemStatus(String processoImagemStatus) {
		this.processoImagemStatus = processoImagemStatus;
	}

	public String getProcessoImagemMotivoOperacao() {
		return processoImagemMotivoOperacao;
	}

	public void setProcessoImagemMotivoOperacao(String processoImagemMotivoOperacao) {
		this.processoImagemMotivoOperacao = processoImagemMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
