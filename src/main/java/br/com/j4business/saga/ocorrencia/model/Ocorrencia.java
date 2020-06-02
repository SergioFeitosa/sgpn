package br.com.j4business.saga.ocorrencia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "ocorrencia", uniqueConstraints=@UniqueConstraint(columnNames={"nm_ocorrencia"}, name="ocorrenciaNome"))
public class Ocorrencia {

	@Id
	@Column(name = "id_ocorrencia")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ocorrenciaPK;
	
	@Column(name = "nm_ocorrencia",nullable = false, length = 50)
	private String ocorrenciaNome;
	
	@Column(name = "ds_ocorrencia")
	private String ocorrenciaDescricao;

	@Column(name = "oc_status")
	private String ocorrenciaStatus;

	@Column(name = "oc_motivooperacao")
	private String ocorrenciaMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	public Ocorrencia() {
		super();
	}

	public long getOcorrenciaPK() {
		return ocorrenciaPK;
	}

	public void setOcorrenciaPK(long ocorrenciaPK) {
		this.ocorrenciaPK = ocorrenciaPK;
	}

	public String getOcorrenciaNome() {
		return ocorrenciaNome;
	}

	public void setOcorrenciaNome(String ocorrenciaNome) {
		this.ocorrenciaNome = ocorrenciaNome;
	}

	public String getOcorrenciaDescricao() {
		return ocorrenciaDescricao;
	}

	public void setOcorrenciaDescricao(String ocorrenciaDescricao) {
		this.ocorrenciaDescricao = ocorrenciaDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ocorrenciaNome == null) ? 0 : ocorrenciaNome.hashCode());
		result = prime * result + (int) (ocorrenciaPK ^ (ocorrenciaPK >>> 32));
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
		Ocorrencia other = (Ocorrencia) obj;
		if (ocorrenciaNome == null) {
			if (other.ocorrenciaNome != null)
				return false;
		} else if (!ocorrenciaNome.equals(other.ocorrenciaNome))
			return false;
		if (ocorrenciaPK != other.ocorrenciaPK)
			return false;
		return true;
	}

	public String getOcorrenciaStatus() {
		return ocorrenciaStatus;
	}

	public void setOcorrenciaStatus(String ocorrenciaStatus) {
		this.ocorrenciaStatus = ocorrenciaStatus;
	}

	public String getOcorrenciaMotivoOperacao() {
		return ocorrenciaMotivoOperacao;
	}

	public void setOcorrenciaMotivoOperacao(String ocorrenciaMotivoOperacao) {
		this.ocorrenciaMotivoOperacao = ocorrenciaMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
