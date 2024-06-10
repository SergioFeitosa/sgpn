package br.com.j4business.saga.treinamento.model;

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
@Table(name = "treinamento", uniqueConstraints=@UniqueConstraint(columnNames={"nm_treinamento"}, name="treinamentoNome"))
public class Treinamento {

	@Id
	@Column(name = "id_treinamento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long treinamentoPK;
	
	@Column(name = "nm_treinamento",nullable = false, length = 50)
	private String treinamentoNome;
	
	@Column(name = "ds_treinamento")
	private String treinamentoDescricao;

	@Column(name = "tr_status")
	private String treinamentoStatus;

	@Column(name = "tr_motivooperacao")
	private String treinamentoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	@Column(name = "id_treinamentoaprovacao")
	private String treinamentoAprovacao;

	@Column(name = "dt_treinamentodataprevistainicio")
    private String  treinamentoDataPrevistaInicio;

	@Column(name = "dt_treinamentodataprevistatermino")
    private String  treinamentoDataPrevistaTermino;

	@Column(name = "dt_treinamentodatarealinicio")
    private String  treinamentoDataRealInicio;

	@Column(name = "dt_treinamentodatarealtermino")
    private String  treinamentoDataRealTermino;

	@Column(name = "id_treinamentooprazostatus")
	private String treinamentoPrazoStatus;

	@Column(name = "dt_treinamentoCustoPrevisto")
    private Double  treinamentoCustoPrevisto;

	@Column(name = "dt_treinamentoCustoFinal")
    private Double  treinamentoCustoFinal;

	@Column(name = "id_treinamentocustostatus")
	private String treinamentoCustoStatus;

	public Treinamento() {
		super();
	}

	public long getTreinamentoPK() {
		return treinamentoPK;
	}

	public void setTreinamentoPK(long treinamentoPK) {
		this.treinamentoPK = treinamentoPK;
	}

	public String getTreinamentoNome() {
		return treinamentoNome;
	}

	public void setTreinamentoNome(String treinamentoNome) {
		this.treinamentoNome = treinamentoNome;
	}

	public String getTreinamentoDescricao() {
		return treinamentoDescricao;
	}

	public void setTreinamentoDescricao(String treinamentoDescricao) {
		this.treinamentoDescricao = treinamentoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((treinamentoNome == null) ? 0 : treinamentoNome.hashCode());
		result = prime * result + (int) (treinamentoPK ^ (treinamentoPK >>> 32));
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
		Treinamento other = (Treinamento) obj;
		if (treinamentoNome == null) {
			if (other.treinamentoNome != null)
				return false;
		} else if (!treinamentoNome.equals(other.treinamentoNome))
			return false;
		if (treinamentoPK != other.treinamentoPK)
			return false;
		return true;
	}

	public String getTreinamentoStatus() {
		return treinamentoStatus;
	}

	public void setTreinamentoStatus(String treinamentoStatus) {
		this.treinamentoStatus = treinamentoStatus;
	}

	public String getTreinamentoMotivoOperacao() {
		return treinamentoMotivoOperacao;
	}

	public void setTreinamentoMotivoOperacao(String treinamentoMotivoOperacao) {
		this.treinamentoMotivoOperacao = treinamentoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
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

	public String getTreinamentoAprovacao() {
		return treinamentoAprovacao;
	}

	public void setTreinamentoAprovacao(String treinamentoAprovacao) {
		this.treinamentoAprovacao = treinamentoAprovacao;
	}

	public String getTreinamentoDataPrevistaInicio() {
		return treinamentoDataPrevistaInicio;
	}

	public void setTreinamentoDataPrevistaInicio(String treinamentoDataPrevistaInicio) {
		this.treinamentoDataPrevistaInicio = treinamentoDataPrevistaInicio;
	}

	public String getTreinamentoDataPrevistaTermino() {
		return treinamentoDataPrevistaTermino;
	}

	public void setTreinamentoDataPrevistaTermino(String treinamentoDataPrevistaTermino) {
		this.treinamentoDataPrevistaTermino = treinamentoDataPrevistaTermino;
	}

	public String getTreinamentoDataRealInicio() {
		return treinamentoDataRealInicio;
	}

	public void setTreinamentoDataRealInicio(String treinamentoDataRealInicio) {
		this.treinamentoDataRealInicio = treinamentoDataRealInicio;
	}

	public String getTreinamentoDataRealTermino() {
		return treinamentoDataRealTermino;
	}

	public void setTreinamentoDataRealTermino(String treinamentoDataRealTermino) {
		this.treinamentoDataRealTermino = treinamentoDataRealTermino;
	}

	public String getTreinamentoPrazoStatus() {
		return treinamentoPrazoStatus;
	}

	public void setTreinamentoPrazoStatus(String treinamentoPrazoStatus) {
		this.treinamentoPrazoStatus = treinamentoPrazoStatus;
	}

	public Double getTreinamentoCustoPrevisto() {
		return treinamentoCustoPrevisto;
	}

	public void setTreinamentoCustoPrevisto(Double treinamentoCustoPrevisto) {
		this.treinamentoCustoPrevisto = treinamentoCustoPrevisto;
	}

	public Double getTreinamentoCustoFinal() {
		return treinamentoCustoFinal;
	}

	public void setTreinamentoCustoFinal(Double treinamentoCustoFinal) {
		this.treinamentoCustoFinal = treinamentoCustoFinal;
	}

	public String getTreinamentoCustoStatus() {
		return treinamentoCustoStatus;
	}

	public void setTreinamentoCustoStatus(String treinamentoCustoStatus) {
		this.treinamentoCustoStatus = treinamentoCustoStatus;
	}

}
