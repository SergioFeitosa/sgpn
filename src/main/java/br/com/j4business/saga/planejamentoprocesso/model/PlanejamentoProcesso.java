package br.com.j4business.saga.planejamentoprocesso.model;

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
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.planejamento.model.Planejamento;

@Entity
@Table(name = "planejamento_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_planejamento","id_processo"}, name="planejamentoProcessoUnique"))
public class PlanejamentoProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_planejamentoprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long planejamentoProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_planejamento")
	private Planejamento planejamento;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "pp_status")
	private String planejamentoProcessoStatus;

	@Column(name = "pp_motivooperacao")
	private String planejamentoProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public PlanejamentoProcesso() {
		super();
	}

	public PlanejamentoProcesso(Planejamento planejamento, Processo processo, int planejamentoProcessoSequencia) {
		super();
		this.planejamento = planejamento;
		this.processo = processo;
	}

	public long getPlanejamentoProcessoPK() {
		return planejamentoProcessoPK;
	}

	public void setPlanejamentoProcessoPK(long planejamentoProcessoPK) {
		this.planejamentoProcessoPK = planejamentoProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Planejamento getPlanejamento() {
		return planejamento;
	}

	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planejamento.getPlanejamentoNome() == null) ? 0 : planejamento.getPlanejamentoNome().hashCode());
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
		PlanejamentoProcesso other = (PlanejamentoProcesso) obj;
		if (planejamento.getPlanejamentoNome() == null) {
			if (other.planejamento.getPlanejamentoNome() != null)
				return false;
		} else if (!planejamento.getPlanejamentoNome().equals(other.planejamento.getPlanejamentoNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public String getPlanejamentoProcessoStatus() {
		return planejamentoProcessoStatus;
	}

	public void setPlanejamentoProcessoStatus(String planejamentoProcessoStatus) {
		this.planejamentoProcessoStatus = planejamentoProcessoStatus;
	}

	public String getPlanejamentoProcessoMotivoOperacao() {
		return planejamentoProcessoMotivoOperacao;
	}

	public void setPlanejamentoProcessoMotivoOperacao(String planejamentoProcessoMotivoOperacao) {
		this.planejamentoProcessoMotivoOperacao = planejamentoProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
