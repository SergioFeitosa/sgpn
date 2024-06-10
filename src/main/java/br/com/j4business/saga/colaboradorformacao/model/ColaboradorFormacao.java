package br.com.j4business.saga.colaboradorformacao.model;

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
import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.fornecedor.model.Fornecedor;

@Entity
@Table(name = "colaborador_formacao", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_formacao"}, name="colaboradorFormacaoUnique"))
public class ColaboradorFormacao implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradorformacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorFormacaoPK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_formacao")
	private Formacao formacao;

	@Column(name = "cf_status",nullable = false, length = 200)
	private String colaboradorFormacaoStatus;

	@Column(name = "cf_motivooperacao",nullable = false, length = 200)
	private String colaboradorFormacaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "dt_colaboradorformacaodatainicio")
    private String  colaboradorFormacaoDataInicio;

	@Column(name = "dt_colaboradorformacaodatatermino")
    private String  colaboradorFormacaoDataTermino;

	@Column(name = "dt_colaboradorformacaodatavalidade")
    private String  colaboradorFormacaoDataValidade;

	@ManyToOne
	private Fornecedor colaboradorFormacaoCapacitador;
	

	public ColaboradorFormacao() {
		super();
	}

	public ColaboradorFormacao(Colaborador colaborador, Formacao formacao, int colaboradorFormacaoSequencia) {
		super();
		this.colaborador = colaborador;
		this.formacao = formacao;
	}

	public long getColaboradorFormacaoPK() {
		return colaboradorFormacaoPK;
	}

	public void setColaboradorFormacaoPK(long colaboradorFormacaoPK) {
		this.colaboradorFormacaoPK = colaboradorFormacaoPK;
	}

	public Formacao getFormacao() {
		return formacao;
	}

	public void setFormacao(Formacao formacao) {
		this.formacao = formacao;
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
		result = prime * result + ((formacao.getFormacaoNome() == null) ? 0 : formacao.getFormacaoNome().hashCode());
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
		ColaboradorFormacao other = (ColaboradorFormacao) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (formacao.getFormacaoNome() == null) {
			if (other.formacao.getFormacaoNome() != null)
				return false;
		} else if (!formacao.getFormacaoNome().equals(other.formacao.getFormacaoNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorFormacaoDataInicio() {
		return colaboradorFormacaoDataInicio;
	}

	public void setColaboradorFormacaoDataInicio(String colaboradorFormacaoDataInicio) {
		this.colaboradorFormacaoDataInicio = colaboradorFormacaoDataInicio;
	}

	public String getColaboradorFormacaoDataTermino() {
		return colaboradorFormacaoDataTermino;
	}

	public void setColaboradorFormacaoDataTermino(String colaboradorFormacaoDataTermino) {
		this.colaboradorFormacaoDataTermino = colaboradorFormacaoDataTermino;
	}

	public String getColaboradorFormacaoDataValidade() {
		return colaboradorFormacaoDataValidade;
	}

	public void setColaboradorFormacaoDataValidade(String colaboradorFormacaoDataValidade) {
		this.colaboradorFormacaoDataValidade = colaboradorFormacaoDataValidade;
	}

	public Fornecedor getColaboradorFormacaoCapacitador() {
		return colaboradorFormacaoCapacitador;
	}

	public void setColaboradorFormacaoCapacitador(Fornecedor colaboradorFormacaoCapacitador) {
		this.colaboradorFormacaoCapacitador = colaboradorFormacaoCapacitador;
	}

	public String getColaboradorFormacaoStatus() {
		return colaboradorFormacaoStatus;
	}

	public void setColaboradorFormacaoStatus(String colaboradorFormacaoStatus) {
		this.colaboradorFormacaoStatus = colaboradorFormacaoStatus;
	}

	public String getColaboradorFormacaoMotivoOperacao() {
		return colaboradorFormacaoMotivoOperacao;
	}

	public void setColaboradorFormacaoMotivoOperacao(String colaboradorFormacaoMotivoOperacao) {
		this.colaboradorFormacaoMotivoOperacao = colaboradorFormacaoMotivoOperacao;
	}

}
