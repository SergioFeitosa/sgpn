package br.com.j4business.saga.colaboradorcurso.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.fornecedor.model.Fornecedor;

@Entity
@Table(name = "colaborador_curso", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_curso"}, name="colaboradorCursoUnique"))
public class ColaboradorCurso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradorcurso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorCursoPK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_curso")
	private Curso curso;
	    
	@Column(name = "cu_status",nullable = false, length = 200)
	private String colaboradorCursoStatus;

	@Column(name = "cu_motivooperacao",nullable = false, length = 200)
	private String colaboradorCursoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "dt_colaboradorcursodatainicio")
    private String  colaboradorCursoDataInicio;

	@Column(name = "dt_colaboradorcursodatatermino")
    private String  colaboradorCursoDataTermino;

	@Column(name = "dt_colaboradorcursodatavalidade")
    private String  colaboradorCursoDataValidade;

	@ManyToOne
	private Fornecedor capacitador;
	
	public ColaboradorCurso() {
		super();
	}

	public ColaboradorCurso(Colaborador colaborador, Curso curso, int colaboradorCursoSequencia) {
		super();
		this.colaborador = colaborador;
		this.curso = curso;
	}

	public long getColaboradorCursoPK() {
		return colaboradorCursoPK;
	}

	public void setColaboradorCursoPK(long colaboradorCursoPK) {
		this.colaboradorCursoPK = colaboradorCursoPK;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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
		result = prime * result + ((curso.getCursoNome() == null) ? 0 : curso.getCursoNome().hashCode());
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
		ColaboradorCurso other = (ColaboradorCurso) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (curso.getCursoNome() == null) {
			if (other.curso.getCursoNome() != null)
				return false;
		} else if (!curso.getCursoNome().equals(other.curso.getCursoNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorcursoDataInicio() {
		return colaboradorCursoDataInicio;
	}

	public void setColaboradorcursoDataInicio(String colaboradorCursoDataInicio) {
		this.colaboradorCursoDataInicio = colaboradorCursoDataInicio;
	}

	public String getColaboradorcursoDataTermino() {
		return colaboradorCursoDataTermino;
	}

	public void setColaboradorcursoDataTermino(String colaboradorCursoDataTermino) {
		this.colaboradorCursoDataTermino = colaboradorCursoDataTermino;
	}

	public String getColaboradorcursoDataValidade() {
		return colaboradorCursoDataValidade;
	}

	public void setColaboradorcursoDataValidade(String colaboradorCursoDataValidade) {
		this.colaboradorCursoDataValidade = colaboradorCursoDataValidade;
	}

	public Fornecedor getCapacitador() {
		return capacitador;
	}

	public void setCapacitador(Fornecedor capacitador) {
		this.capacitador = capacitador;
	}

	public String getColaboradorCursoStatus() {
		return colaboradorCursoStatus;
	}

	public void setColaboradorCursoStatus(String colaboradorCursoStatus) {
		this.colaboradorCursoStatus = colaboradorCursoStatus;
	}

	public String getColaboradorCursoMotivoOperacao() {
		return colaboradorCursoMotivoOperacao;
	}

	public void setColaboradorCursoMotivoOperacao(String colaboradorCursoMotivoOperacao) {
		this.colaboradorCursoMotivoOperacao = colaboradorCursoMotivoOperacao;
	}

	public String getColaboradorCursoDataInicio() {
		return colaboradorCursoDataInicio;
	}

	public void setColaboradorCursoDataInicio(String colaboradorCursoDataInicio) {
		this.colaboradorCursoDataInicio = colaboradorCursoDataInicio;
	}

	public String getColaboradorCursoDataTermino() {
		return colaboradorCursoDataTermino;
	}

	public void setColaboradorCursoDataTermino(String colaboradorCursoDataTermino) {
		this.colaboradorCursoDataTermino = colaboradorCursoDataTermino;
	}

	public String getColaboradorCursoDataValidade() {
		return colaboradorCursoDataValidade;
	}

	public void setColaboradorCursoDataValidade(String colaboradorCursoDataValidade) {
		this.colaboradorCursoDataValidade = colaboradorCursoDataValidade;
	}

}
