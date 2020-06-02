package br.com.j4business.saga.planejamentoacao.model;

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

import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.planejamento.model.Planejamento;

@Entity
@Table(name = "planejamento_acao", uniqueConstraints=@UniqueConstraint(columnNames={"id_planejamento","id_acao"}, name="planejamentoAcaoUnique"))
public class PlanejamentoAcao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_planejamentoacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long planejamentoAcaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_planejamento")
	private Planejamento planejamento;
	    
	@ManyToOne
	@JoinColumn(name="id_acao")
	private Acao acao;    
	
	@Column(name = "sq_planejamentoacao",nullable = false, length = 200)
	private String planejamentoAcaoSequencia;

	@Column(name = "pa_status")
	private String planejamentoAcaoStatus;

	@Column(name = "pa_motivooperacao")
	private String planejamentoAcaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public PlanejamentoAcao() {
		super();
	}

	public PlanejamentoAcao(Planejamento planejamento, Acao acao, String planejamentoAcaoSequencia) {
		super();
		this.planejamento = planejamento;
		this.acao = acao;
		this.planejamentoAcaoSequencia = planejamentoAcaoSequencia;
	}

	public long getPlanejamentoAcaoPK() {
		return planejamentoAcaoPK;
	}

	public void setPlanejamentoAcaoPK(long planejamentoAcaoPK) {
		this.planejamentoAcaoPK = planejamentoAcaoPK;
	}

	public Planejamento getPlanejamento() {
		return planejamento;
	}

	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acao.getAcaoNome() == null) ? 0 : acao.getAcaoNome().hashCode());
		result = prime * result + ((planejamento.getPlanejamentoNome() == null) ? 0 : planejamento.getPlanejamentoNome().hashCode());
		result = prime * result + ((planejamentoAcaoSequencia == null) ? 0 : planejamentoAcaoSequencia.hashCode());
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
		PlanejamentoAcao other = (PlanejamentoAcao) obj;
		if (acao.getAcaoNome() == null) {
			if (other.acao.getAcaoNome() != null)
				return false;
		} else if (!acao.getAcaoNome().equals(other.acao.getAcaoNome()))
			return false;
		if (planejamento.getPlanejamentoNome() == null) {
			if (other.planejamento.getPlanejamentoNome() != null)
				return false;
		} else if (!planejamento.getPlanejamentoNome().equals(other.planejamento.getPlanejamentoNome()))
			return false;
		if (planejamentoAcaoSequencia == null) {
			if (other.planejamentoAcaoSequencia != null)
				return false;
		} else if (!planejamentoAcaoSequencia.equals(other.planejamentoAcaoSequencia))
			return false;
		return true;
	}

	public String getPlanejamentoAcaoSequencia() {
		return planejamentoAcaoSequencia;
	}

	public void setPlanejamentoAcaoSequencia(String planejamentoAcaoSequencia) {
		this.planejamentoAcaoSequencia = planejamentoAcaoSequencia;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getPlanejamentoAcaoStatus() {
		return planejamentoAcaoStatus;
	}

	public void setPlanejamentoAcaoStatus(String planejamentoAcaoStatus) {
		this.planejamentoAcaoStatus = planejamentoAcaoStatus;
	}

	public String getPlanejamentoAcaoMotivoOperacao() {
		return planejamentoAcaoMotivoOperacao;
	}

	public void setPlanejamentoAcaoMotivoOperacao(String planejamentoAcaoMotivoOperacao) {
		this.planejamentoAcaoMotivoOperacao = planejamentoAcaoMotivoOperacao;
	}


}
