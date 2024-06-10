package br.com.j4business.saga.curso.model;

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
@Table(name = "curso", uniqueConstraints=@UniqueConstraint(columnNames={"nm_curso"}, name="cursoNome"))
public class Curso {

	@Id
	@Column(name = "id_curso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long cursoPK;
	
	@Column(name = "nm_curso",nullable = false, length = 50)
	private String cursoNome;
	
	@Column(name = "ds_curso")
	private String cursoDescricao;

	@Column(name = "cr_status")
	private String cursoStatus;

	@Column(name = "cr_motivooperacao")
	private String cursoMotivoOperacao;

	
	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Curso() {
		super();
	}

	public long getCursoPK() {
		return cursoPK;
	}

	public void setCursoPK(long cursoPK) {
		this.cursoPK = cursoPK;
	}

	public String getCursoNome() {
		return cursoNome;
	}

	public void setCursoNome(String cursoNome) {
		this.cursoNome = cursoNome;
	}

	public String getCursoDescricao() {
		return cursoDescricao;
	}

	public void setCursoDescricao(String cursoDescricao) {
		this.cursoDescricao = cursoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cursoNome == null) ? 0 : cursoNome.hashCode());
		result = prime * result + (int) (cursoPK ^ (cursoPK >>> 32));
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
		Curso other = (Curso) obj;
		if (cursoNome == null) {
			if (other.cursoNome != null)
				return false;
		} else if (!cursoNome.equals(other.cursoNome))
			return false;
		if (cursoPK != other.cursoPK)
			return false;
		return true;
	}

	public String getCursoStatus() {
		return cursoStatus;
	}

	public void setCursoStatus(String cursoStatus) {
		this.cursoStatus = cursoStatus;
	}

	public String getCursoMotivoOperacao() {
		return cursoMotivoOperacao;
	}

	public void setCursoMotivoOperacao(String cursoMotivoOperacao) {
		this.cursoMotivoOperacao = cursoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}
	
}
