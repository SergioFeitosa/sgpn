package br.com.j4business.saga.planejamento.model;

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

import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "planejamento", uniqueConstraints=@UniqueConstraint(columnNames={"nm_planejamento"}, name="planejamentoNome"))
public class Planejamento {

	@Id
	@Column(name = "id_planejamento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long planejamentoPK;
	
	@Column(name = "nm_planejamento",nullable = false, length = 50)
	private String planejamentoNome;
	
	@Column(name = "ds_planejamento")
	private String planejamentoDescricao;

	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	@Column(name = "id_planejamentoaprovacao")
	private String planejamentoAprovacao;

	@Column(name = "dt_planejamentodataprevistainicio")
    private String  planejamentoDataPrevistaInicio;

	@Column(name = "dt_planejamentodataprevistatermino")
    private String  planejamentoDataPrevistaTermino;

	@Column(name = "dt_planejamentodatarealinicio")
    private String  planejamentoDataRealInicio;

	@Column(name = "dt_planejamentodatarealtermino")
    private String  planejamentoDataRealTermino;

	@Column(name = "id_planejamentooprazostatus")
	private String planejamentoPrazoStatus;

	@Column(name = "dt_planejamentoCustoPrevisto")
    private Double  planejamentoCustoPrevisto;

	@Column(name = "dt_planejamentoCustoFinal")
    private Double  planejamentoCustoFinal;

	@Column(name = "id_planejamentocustostatus")
	private String planejamentoCustoStatus;

	@Column(name = "pl_status")
	private String planejamentoStatus;

	@Column(name = "pl_motivooperacao")
	private String planejamentoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@ManyToMany
	@JoinTable(name="join_planejamento_acao",joinColumns= { @JoinColumn(name="planejamentoPK")},
										inverseJoinColumns= {@JoinColumn(name="acaoPK")})
	private List<Acao> acaoList = new ArrayList<Acao>();

	
	public Planejamento() {
		super();
	}

	public long getPlanejamentoPK() {
		return planejamentoPK;
	}

	public void setPlanejamentoPK(long planejamentoPK) {
		this.planejamentoPK = planejamentoPK;
	}

	public String getPlanejamentoNome() {
		return planejamentoNome;
	}

	public void setPlanejamentoNome(String planejamentoNome) {
		this.planejamentoNome = planejamentoNome;
	}

	public String getPlanejamentoDescricao() {
		return planejamentoDescricao;
	}

	public void setPlanejamentoDescricao(String planejamentoDescricao) {
		this.planejamentoDescricao = planejamentoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planejamentoNome == null) ? 0 : planejamentoNome.hashCode());
		result = prime * result + (int) (planejamentoPK ^ (planejamentoPK >>> 32));
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
		Planejamento other = (Planejamento) obj;
		if (planejamentoNome == null) {
			if (other.planejamentoNome != null)
				return false;
		} else if (!planejamentoNome.equals(other.planejamentoNome))
			return false;
		if (planejamentoPK != other.planejamentoPK)
			return false;
		return true;
	}

	public String getPlanejamentoAprovacao() {
		return planejamentoAprovacao;
	}

	public void setPlanejamentoAprovacao(String planejamentoAprovacao) {
		this.planejamentoAprovacao = planejamentoAprovacao;
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

	public String getPlanejamentoDataPrevistaInicio() {
		return planejamentoDataPrevistaInicio;
	}

	public void setPlanejamentoDataPrevistaInicio(String planejamentoDataPrevistaInicio) {
		this.planejamentoDataPrevistaInicio = planejamentoDataPrevistaInicio;
	}

	public String getPlanejamentoDataPrevistaTermino() {
		return planejamentoDataPrevistaTermino;
	}

	public void setPlanejamentoDataPrevistaTermino(String planejamentoDataPrevistaTermino) {
		this.planejamentoDataPrevistaTermino = planejamentoDataPrevistaTermino;
	}

	public String getPlanejamentoDataRealInicio() {
		return planejamentoDataRealInicio;
	}

	public void setPlanejamentoDataRealInicio(String planejamentoDataRealInicio) {
		this.planejamentoDataRealInicio = planejamentoDataRealInicio;
	}

	public String getPlanejamentoDataRealTermino() {
		return planejamentoDataRealTermino;
	}

	public void setPlanejamentoDataRealTermino(String planejamentoDataRealTermino) {
		this.planejamentoDataRealTermino = planejamentoDataRealTermino;
	}

	public String getPlanejamentoPrazoStatus() {
		return planejamentoPrazoStatus;
	}

	public void setPlanejamentoPrazoStatus(String planejamentoPrazoStatus) {
		this.planejamentoPrazoStatus = planejamentoPrazoStatus;
	}

	public Double getPlanejamentoCustoPrevisto() {
		return planejamentoCustoPrevisto;
	}

	public void setPlanejamentoCustoPrevisto(Double planejamentoCustoPrevisto) {
		this.planejamentoCustoPrevisto = planejamentoCustoPrevisto;
	}

	public Double getPlanejamentoCustoFinal() {
		return planejamentoCustoFinal;
	}

	public void setPlanejamentoCustoFinal(Double planejamentoCustoFinal) {
		this.planejamentoCustoFinal = planejamentoCustoFinal;
	}

	public String getPlanejamentoCustoStatus() {
		return planejamentoCustoStatus;
	}

	public void setPlanejamentoCustoStatus(String planejamentoCustoStatus) {
		this.planejamentoCustoStatus = planejamentoCustoStatus;
	}

	public String getPlanejamentoStatus() {
		return planejamentoStatus;
	}

	public void setPlanejamentoStatus(String planejamentoStatus) {
		this.planejamentoStatus = planejamentoStatus;
	}

	public String getPlanejamentoMotivoOperacao() {
		return planejamentoMotivoOperacao;
	}

	public void setPlanejamentoMotivoOperacao(String planejamentoMotivoOperacao) {
		this.planejamentoMotivoOperacao = planejamentoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public List<Acao> getAcaoList() {
		return acaoList;
	}

	public void setAcaoList(List<Acao> acaoList) {
		this.acaoList = acaoList;
	}

}
