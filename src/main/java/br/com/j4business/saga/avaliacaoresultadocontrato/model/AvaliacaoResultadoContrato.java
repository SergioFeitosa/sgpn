package br.com.j4business.saga.avaliacaoresultadocontrato.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContrato;

@Entity
@Table(name="avaliacao_resultado_contrato")
public class AvaliacaoResultadoContrato implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_avaliacaoresultadocontrato")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long avaliacaoResultadoContratoPK;
	
	@ManyToOne
	@JoinColumn(name="id_avaliacao")
	private Avaliacao avaliacao;    
	
	@ManyToOne
	@JoinColumn(name="id_resultado")
	private Resultado resultado;
	    
	@ManyToOne
	@JoinColumn(name="id_elemento")
	private Elemento elemento;
	    
	@ManyToOne
	@JoinColumn(name="id_avaliacaocontrato")
	private AvaliacaoContrato avaliacaoContrato;    
	
	@Column(name = "qt_avaliacaoelemento")
	private double avaliacaoElementoQuantidade;
	    
	@Column(name = "rp_avaliacaoelemento")
	private double avaliacaoElementoResposta;
	    
	@ManyToOne
	private Colaborador colaborador;
	
	public AvaliacaoResultadoContrato() {
		super();
	}

	public AvaliacaoResultadoContrato(Avaliacao avaliacao, Resultado resultado, int avaliacaoResultadoContratoSequencia) {
		super();
		this.avaliacao = avaliacao;
		this.resultado = resultado;
	}

	public long getAvaliacaoResultadoContratoPK() {
		return avaliacaoResultadoContratoPK;
	}

	public void setAvaliacaoResultadoContratoPK(long avaliacaoResultadoContratoPK) {
		this.avaliacaoResultadoContratoPK = avaliacaoResultadoContratoPK;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avaliacao.getAvaliacaoNome() == null) ? 0 : avaliacao.getAvaliacaoNome().hashCode());
		result = prime * result + ((resultado.getResultadoNome() == null) ? 0 : resultado.getResultadoNome().hashCode());
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
		AvaliacaoResultadoContrato other = (AvaliacaoResultadoContrato) obj;
		if (avaliacao.getAvaliacaoNome() == null) {
			if (other.avaliacao.getAvaliacaoNome() != null)
				return false;
		} else if (!avaliacao.getAvaliacaoNome().equals(other.avaliacao.getAvaliacaoNome()))
			return false;
		if (resultado.getResultadoNome() == null) {
			if (other.resultado.getResultadoNome() != null)
				return false;
		} else if (!resultado.getResultadoNome().equals(other.resultado.getResultadoNome()))
			return false;
		return true;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public double getAvaliacaoElementoQuantidade() {
		return avaliacaoElementoQuantidade;
	}

	public void setAvaliacaoElementoQuantidade(double avaliacaoElementoQuantidade) {
		this.avaliacaoElementoQuantidade = avaliacaoElementoQuantidade;
	}

	public double getAvaliacaoElementoResposta() {
		return avaliacaoElementoResposta;
	}

	public void setAvaliacaoElementoResposta(double avaliacaoElementoResposta) {
		this.avaliacaoElementoResposta = avaliacaoElementoResposta;
	}

	public AvaliacaoContrato getAvaliacaoContrato() {
		return avaliacaoContrato;
	}

	public void setAvaliacaoContrato(AvaliacaoContrato avaliacaoContrato) {
		this.avaliacaoContrato = avaliacaoContrato;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

}
