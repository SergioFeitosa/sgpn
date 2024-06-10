package br.com.j4business.saga.certificacao.model;

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
@Table(name = "certificacao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_certificacao"}, name="certificacaoNome"))
public class Certificacao {

	@Id
	@Column(name = "id_certificacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long certificacaoPK;
	
	@Column(name = "nm_certificacao",nullable = false, length = 100)
	private String certificacaoNome;
	
	@Column(name = "ds_certificacao")
	private String certificacaoDescricao;

	@Column(name = "ct_status")
	private String certificacaoStatus;

	@Column(name = "ct_motivooperacao")
	private String certificacaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Certificacao() {
		super();
	}

	public long getCertificacaoPK() {
		return certificacaoPK;
	}

	public void setCertificacaoPK(long certificacaoPK) {
		this.certificacaoPK = certificacaoPK;
	}

	public String getCertificacaoNome() {
		return certificacaoNome;
	}

	public void setCertificacaoNome(String certificacaoNome) {
		this.certificacaoNome = certificacaoNome;
	}

	public String getCertificacaoDescricao() {
		return certificacaoDescricao;
	}

	public void setCertificacaoDescricao(String certificacaoDescricao) {
		this.certificacaoDescricao = certificacaoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((certificacaoNome == null) ? 0 : certificacaoNome.hashCode());
		result = prime * result + (int) (certificacaoPK ^ (certificacaoPK >>> 32));
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
		Certificacao other = (Certificacao) obj;
		if (certificacaoNome == null) {
			if (other.certificacaoNome != null)
				return false;
		} else if (!certificacaoNome.equals(other.certificacaoNome))
			return false;
		if (certificacaoPK != other.certificacaoPK)
			return false;
		return true;
	}

	public String getCertificacaoStatus() {
		return certificacaoStatus;
	}

	public void setCertificacaoStatus(String certificacaoStatus) {
		this.certificacaoStatus = certificacaoStatus;
	}

	public String getCertificacaoMotivoOperacao() {
		return certificacaoMotivoOperacao;
	}

	public void setCertificacaoMotivoOperacao(String certificacaoMotivoOperacao) {
		this.certificacaoMotivoOperacao = certificacaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
