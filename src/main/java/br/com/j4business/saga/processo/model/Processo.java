package br.com.j4business.saga.processo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "processo", uniqueConstraints=@UniqueConstraint(columnNames={"nm_processo"}, name="processoNome"))
public class Processo {

	@Id
	@Column(name = "id_processo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoPK;
	
	@Column(name = "nm_processo",nullable = false, length = 50)
	private String processoNome;
	
	@Column(name = "ds_processo")
	private String processoDescricao;

	@Column(name = "pr_status")
	private String processoStatus;

	@Column(name = "pr_motivooperacao")
	private String processoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	private int processoDuracao;

	public Processo() {
		super();
	}

	public long getProcessoPK() {
		return processoPK;
	}

	public void setProcessoPK(long processoPK) {
		this.processoPK = processoPK;
	}

	public String getProcessoNome() {
		return processoNome;
	}

	public void setProcessoNome(String processoNome) {
		this.processoNome = processoNome;
	}

	public String getProcessoDescricao() {
		return processoDescricao;
	}

	public void setProcessoDescricao(String processoDescricao) {
		this.processoDescricao = processoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processoNome == null) ? 0 : processoNome.hashCode());
		result = prime * result + (int) (processoPK ^ (processoPK >>> 32));
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
		Processo other = (Processo) obj;
		if (processoNome == null) {
			if (other.processoNome != null)
				return false;
		} else if (!processoNome.equals(other.processoNome))
			return false;
		if (processoPK != other.processoPK)
			return false;
		return true;
	}

	public Colaborador getColaboradorGestor() {
		return colaboradorGestor;
	}

	public void setColaboradorGestor(Colaborador colaboradorGestor) {
		this.colaboradorGestor = colaboradorGestor;
	}

	public Colaborador getColaboradorDono() {
		return colaboradorDono;
	}

	public void setColaboradorDono(Colaborador colaboradorDono) {
		this.colaboradorDono = colaboradorDono;
	}

	public String getProcessoStatus() {
		return processoStatus;
	}

	public void setProcessoStatus(String processoStatus) {
		this.processoStatus = processoStatus;
	}

	public String getProcessoMotivoOperacao() {
		return processoMotivoOperacao;
	}

	public void setProcessoMotivoOperacao(String processoMotivoOperacao) {
		this.processoMotivoOperacao = processoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public int getProcessoDuracao() {
		return processoDuracao;
	}

	public void setProcessoDuracao(int processoDuracao) {
		this.processoDuracao = processoDuracao;
	}

	
}
