package br.com.j4business.saga.colaboradorcertificacao.model;

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
import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.fornecedor.model.Fornecedor;

@Entity
@Table(name = "colaborador_certificacao", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_certificacao"}, name="colaboradorCertificacaoUnique"))
public class ColaboradorCertificacao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradorcertificacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorCertificacaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_certificacao")
	private Certificacao certificacao;

	@Column(name = "dt_colaboradorcertificacaodatainicio")
    private String  colaboradorcertificacaoDataInicio;

	@Column(name = "dt_colaboradorcertificacaodatatermino")
    private String  colaboradorcertificacaoDataTermino;

	@Column(name = "dt_colaboradorcertificacaodatavalidade")
    private String  colaboradorcertificacaoDataValidade;

	@Column(name = "cc_status",nullable = false, length = 200)
	private String colaboradorCertificacaoStatus;

	@Column(name = "cc_motivooperacao",nullable = false, length = 200)
	private String colaboradorCertificacaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@ManyToOne
	private Fornecedor capacitador;
	
	public ColaboradorCertificacao() {
		super();
	}

	public ColaboradorCertificacao(Colaborador colaborador, Certificacao certificacao, int colaboradorCertificacaoSequencia) {
		super();
		this.colaborador = colaborador;
		this.certificacao = certificacao;
	}

	public long getColaboradorCertificacaoPK() {
		return colaboradorCertificacaoPK;
	}

	public void setColaboradorCertificacaoPK(long colaboradorCertificacaoPK) {
		this.colaboradorCertificacaoPK = colaboradorCertificacaoPK;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colaborador.getPessoaNome() == null) ? 0 : colaborador.getPessoaNome().hashCode());
		result = prime * result + ((certificacao.getCertificacaoNome() == null) ? 0 : certificacao.getCertificacaoNome().hashCode());
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
		ColaboradorCertificacao other = (ColaboradorCertificacao) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (certificacao.getCertificacaoNome() == null) {
			if (other.certificacao.getCertificacaoNome() != null)
				return false;
		} else if (!certificacao.getCertificacaoNome().equals(other.certificacao.getCertificacaoNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorcertificacaoDataInicio() {
		return colaboradorcertificacaoDataInicio;
	}

	public void setColaboradorcertificacaoDataInicio(String colaboradorcertificacaoDataInicio) {
		this.colaboradorcertificacaoDataInicio = colaboradorcertificacaoDataInicio;
	}

	public String getColaboradorcertificacaoDataTermino() {
		return colaboradorcertificacaoDataTermino;
	}

	public void setColaboradorcertificacaoDataTermino(String colaboradorcertificacaoDataTermino) {
		this.colaboradorcertificacaoDataTermino = colaboradorcertificacaoDataTermino;
	}

	public String getColaboradorcertificacaoDataValidade() {
		return colaboradorcertificacaoDataValidade;
	}

	public void setColaboradorcertificacaoDataValidade(String colaboradorcertificacaoDataValidade) {
		this.colaboradorcertificacaoDataValidade = colaboradorcertificacaoDataValidade;
	}

	public Fornecedor getCapacitador() {
		return capacitador;
	}

	public void setCapacitador(Fornecedor capacitador) {
		this.capacitador = capacitador;
	}

	public String getColaboradorCertificacaoStatus() {
		return colaboradorCertificacaoStatus;
	}

	public void setColaboradorCertificacaoStatus(String colaboradorCertificacaoStatus) {
		this.colaboradorCertificacaoStatus = colaboradorCertificacaoStatus;
	}

	public String getColaboradorCertificacaoMotivoOperacao() {
		return colaboradorCertificacaoMotivoOperacao;
	}

	public void setColaboradorCertificacaoMotivoOperacao(String colaboradorCertificacaoMotivoOperacao) {
		this.colaboradorCertificacaoMotivoOperacao = colaboradorCertificacaoMotivoOperacao;
	}

}
