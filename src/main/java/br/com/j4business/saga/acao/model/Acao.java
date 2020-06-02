package br.com.j4business.saga.acao.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.planejamento.model.Planejamento;

@Entity
@Table(name = "acao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_acao"}, name="acaoNome"))
public class Acao {

	@Id
	@Column(name = "id_acao") // ac
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long acaoPK;
	
	@Column(name = "nm_acao",nullable = false, length = 100)
	private String acaoNome;
	
	@Column(name = "ds_acao")
	private String acaoDescricao;

	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	@Column(name = "id_acaoaprovacao")
	private String acaoAprovacao;

	@Column(name = "ac_status")
	private String acaoStatus;

	@Column(name = "ac_motivooperacao")
	private String acaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@ManyToMany
	@JoinTable(name="join_planejamento_acao",joinColumns= { @JoinColumn(name="acaoPK")},
										inverseJoinColumns= {@JoinColumn(name="planejamentoPK")})
	private List<Planejamento> planejamentoList = new ArrayList<Planejamento>();

	
	public Acao() {
		super();
	}

	public long getAcaoPK() {
		return acaoPK;
	}

	public void setAcaoPK(long acaoPK) {
		this.acaoPK = acaoPK;
	}

	public String getAcaoNome() {
		return acaoNome;
	}

	public void setAcaoNome(String acaoNome) {
		this.acaoNome = acaoNome;
	}

	public String getAcaoDescricao() {
		return acaoDescricao;
	}

	public void setAcaoDescricao(String acaoDescricao) {
		this.acaoDescricao = acaoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acaoNome == null) ? 0 : acaoNome.hashCode());
		result = prime * result + (int) (acaoPK ^ (acaoPK >>> 32));
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
		Acao other = (Acao) obj;
		if (acaoNome == null) {
			if (other.acaoNome != null)
				return false;
		} else if (!acaoNome.equals(other.acaoNome))
			return false;
		if (acaoPK != other.acaoPK)
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

	public String getAcaoMotivoOperacao() {
		return acaoMotivoOperacao;
	}

	public void setAcaoMotivoOperacao(String acaoMotivoOperacao) {
		this.acaoMotivoOperacao = acaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getAcaoAprovacao() {
		return acaoAprovacao;
	}

	public void setAcaoAprovacao(String acaoAprovacao) {
		this.acaoAprovacao = acaoAprovacao;
	}

	public String getAcaoStatus() {
		return acaoStatus;
	}

	public void setAcaoStatus(String acaoStatus) {
		this.acaoStatus = acaoStatus;
	}

	public List<Planejamento> getPlanejamentoList() {
		return planejamentoList;
	}

	public void setPlanejamentoList(List<Planejamento> planejamentoList) {
		this.planejamentoList = planejamentoList;
	}

}
