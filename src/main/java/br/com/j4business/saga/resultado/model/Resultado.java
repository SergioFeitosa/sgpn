package br.com.j4business.saga.resultado.model;

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
@Table(name = "resultado", uniqueConstraints=@UniqueConstraint(columnNames={"nm_resultado"}, name="resultadoNome"))
public class Resultado {

	@Id
	@Column(name = "id_resultado")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long resultadoPK;
	
	@Column(name = "nm_resultado",nullable = false, length = 50)
	private String resultadoNome;
	
	@Column(name = "ds_resultado")
	private String resultadoDescricao;

	@Column(name = "re_status")
	private String resultadoStatus;

	@Column(name = "re_motivooperacao")
	private String resultadoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	
	public Resultado() {
		super();
	}

	public long getResultadoPK() {
		return resultadoPK;
	}

	public void setResultadoPK(long resultadoPK) {
		this.resultadoPK = resultadoPK;
	}

	public String getResultadoNome() {
		return resultadoNome;
	}

	public void setResultadoNome(String resultadoNome) {
		this.resultadoNome = resultadoNome;
	}

	public String getResultadoDescricao() {
		return resultadoDescricao;
	}

	public void setResultadoDescricao(String resultadoDescricao) {
		this.resultadoDescricao = resultadoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resultadoNome == null) ? 0 : resultadoNome.hashCode());
		result = prime * result + (int) (resultadoPK ^ (resultadoPK >>> 32));
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
		Resultado other = (Resultado) obj;
		if (resultadoNome == null) {
			if (other.resultadoNome != null)
				return false;
		} else if (!resultadoNome.equals(other.resultadoNome))
			return false;
		if (resultadoPK != other.resultadoPK)
			return false;
		return true;
	}

	public String getResultadoStatus() {
		return resultadoStatus;
	}

	public void setResultadoStatus(String resultadoStatus) {
		this.resultadoStatus = resultadoStatus;
	}

	public String getResultadoMotivoOperacao() {
		return resultadoMotivoOperacao;
	}

	public void setResultadoMotivoOperacao(String resultadoMotivoOperacao) {
		this.resultadoMotivoOperacao = resultadoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
