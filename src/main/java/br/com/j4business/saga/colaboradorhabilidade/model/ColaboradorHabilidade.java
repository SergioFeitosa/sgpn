package br.com.j4business.saga.colaboradorhabilidade.model;

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
import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.fornecedor.model.Fornecedor;

@Entity
@Table(name = "colaborador_habilidade", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_habilidade"}, name="colaboradorHabilidadeUnique"))
public class ColaboradorHabilidade implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradorhabilidade")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorHabilidadePK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_habilidade")
	private Habilidade habilidade;
	    
	@Column(name = "ch_status",nullable = false, length = 200)
	private String colaboradorHabilidadeStatus;

	@Column(name = "ch_motivooperacao",nullable = false, length = 200)
	private String colaboradorHabilidadeMotivoOperacao;
 
	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "dt_colaboradorhabilidadedatainicio")
    private String  colaboradorHabilidadeDataInicio;

	@Column(name = "dt_colaboradorhabilidadedatatermino")
    private String  colaboradorHabilidadeDataTermino;

	@Column(name = "dt_colaboradorhabilidadedatavalidade")
    private String  colaboradorHabilidadeDataValidade;

	@ManyToOne
	private Fornecedor colaboradorHabilidadeCapacitador;
	

	public ColaboradorHabilidade() {
		super();
	}

	public ColaboradorHabilidade(Colaborador colaborador, Habilidade habilidade, int colaboradorHabilidadeSequencia) {
		super();
		this.colaborador = colaborador;
		this.habilidade = habilidade;
	}

	public long getColaboradorHabilidadePK() {
		return colaboradorHabilidadePK;
	}

	public void setColaboradorHabilidadePK(long colaboradorHabilidadePK) {
		this.colaboradorHabilidadePK = colaboradorHabilidadePK;
	}

	public Habilidade getHabilidade() {
		return habilidade;
	}

	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
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
		result = prime * result + ((habilidade.getHabilidadeNome() == null) ? 0 : habilidade.getHabilidadeNome().hashCode());
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
		ColaboradorHabilidade other = (ColaboradorHabilidade) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (habilidade.getHabilidadeNome() == null) {
			if (other.habilidade.getHabilidadeNome() != null)
				return false;
		} else if (!habilidade.getHabilidadeNome().equals(other.habilidade.getHabilidadeNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorHabilidadeDataInicio() {
		return colaboradorHabilidadeDataInicio;
	}

	public void setColaboradorHabilidadeDataInicio(String colaboradorHabilidadeDataInicio) {
		this.colaboradorHabilidadeDataInicio = colaboradorHabilidadeDataInicio;
	}

	public String getColaboradorHabilidadeDataTermino() {
		return colaboradorHabilidadeDataTermino;
	}

	public void setColaboradorHabilidadeDataTermino(String colaboradorHabilidadeDataTermino) {
		this.colaboradorHabilidadeDataTermino = colaboradorHabilidadeDataTermino;
	}

	public String getColaboradorHabilidadeDataValidade() {
		return colaboradorHabilidadeDataValidade;
	}

	public void setColaboradorHabilidadeDataValidade(String colaboradorHabilidadeDataValidade) {
		this.colaboradorHabilidadeDataValidade = colaboradorHabilidadeDataValidade;
	}

	public Fornecedor getColaboradorHabilidadeCapacitador() {
		return colaboradorHabilidadeCapacitador;
	}

	public void setColaboradorHabilidadeCapacitador(Fornecedor colaboradorHabilidadeCapacitador) {
		this.colaboradorHabilidadeCapacitador = colaboradorHabilidadeCapacitador;
	}

	public String getColaboradorHabilidadeStatus() {
		return colaboradorHabilidadeStatus;
	}

	public void setColaboradorHabilidadeStatus(String colaboradorHabilidadeStatus) {
		this.colaboradorHabilidadeStatus = colaboradorHabilidadeStatus;
	}

	public String getColaboradorHabilidadeMotivoOperacao() {
		return colaboradorHabilidadeMotivoOperacao;
	}

	public void setColaboradorHabilidadeMotivoOperacao(String colaboradorHabilidadeMotivoOperacao) {
		this.colaboradorHabilidadeMotivoOperacao = colaboradorHabilidadeMotivoOperacao;
	}

}
