package br.com.j4business.saga.processocurso.model;

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

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.curso.model.Curso;

@Entity
@Table(name = "processo_curso", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_curso"}, name="processoCursoUnique"))
public class ProcessoCurso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processocurso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoCursoPK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_curso")
	private Curso curso;
	    
	@Column(name = "pu_status",nullable = false, length = 200)
	private String processoCursoStatus;

	@Column(name = "pu_motivooperacao",nullable = false, length = 200)
	private String processoCursoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoCurso() {
		super();
	}

	public ProcessoCurso(Processo processo, Curso curso, int processoCursoSequencia) {
		super();
		this.processo = processo;
		this.curso = curso;
	}

	public long getProcessoCursoPK() {
		return processoCursoPK;
	}

	public void setProcessoCursoPK(long processoCursoPK) {
		this.processoCursoPK = processoCursoPK;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
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
		ProcessoCurso other = (ProcessoCurso) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (curso.getCursoNome() == null) {
			if (other.curso.getCursoNome() != null)
				return false;
		} else if (!curso.getCursoNome().equals(other.curso.getCursoNome()))
			return false;
		return true;
	}

	public String getProcessoCursoStatus() {
		return processoCursoStatus;
	}

	public void setProcessoCursoStatus(String processoCursoStatus) {
		this.processoCursoStatus = processoCursoStatus;
	}

	public String getProcessoCursoMotivoOperacao() {
		return processoCursoMotivoOperacao;
	}

	public void setProcessoCursoMotivoOperacao(String processoCursoMotivoOperacao) {
		this.processoCursoMotivoOperacao = processoCursoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
