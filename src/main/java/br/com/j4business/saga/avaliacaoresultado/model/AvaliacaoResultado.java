package br.com.j4business.saga.avaliacaoresultado.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcesso;

@Entity
@Table(name="avaliacao_resultado")
public class AvaliacaoResultado implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_avaliacaoresultado")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long avaliacaoResultadoPK;
	
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
	@JoinColumn(name="id_avaliacaoprocesso")
	private AvaliacaoProcesso avaliacaoProcesso;    
	
	@Column(name = "qt_avaliacaoelemento")
	private double avaliacaoElementoQuantidade;
	    
	@Column(name = "rp_avaliacaoelemento")
	private double avaliacaoElementoResposta;
	    
	@ManyToOne
	private Colaborador colaborador;
	
	public AvaliacaoResultado() {
		super();
	}

	public AvaliacaoResultado(Avaliacao avaliacao, Resultado resultado, int avaliacaoResultadoSequencia) {
		super();
		this.avaliacao = avaliacao;
		this.resultado = resultado;
	}

	public long getAvaliacaoResultadoPK() {
		return avaliacaoResultadoPK;
	}

	public void setAvaliacaoResultadoPK(long avaliacaoResultadoPK) {
		this.avaliacaoResultadoPK = avaliacaoResultadoPK;
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
		AvaliacaoResultado other = (AvaliacaoResultado) obj;
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

	public AvaliacaoProcesso getAvaliacaoProcesso() {
		return avaliacaoProcesso;
	}

	public void setAvaliacaoProcesso(AvaliacaoProcesso avaliacaoProcesso) {
		this.avaliacaoProcesso = avaliacaoProcesso;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

}
