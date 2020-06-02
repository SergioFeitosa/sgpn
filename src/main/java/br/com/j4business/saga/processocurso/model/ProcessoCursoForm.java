package br.com.j4business.saga.processocurso.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoCursoForm {

	private long processoCursoPK;
	private long cursoPK;
	private long processoPK;
	
    @NotEmpty(message = "Nome do curso é uma informação obrigatória.")
	@NotNull
	private String cursoNome;
	
    @NotEmpty(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoCursoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoCursoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoCursoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoCursoMotivoOperacao;

	public ProcessoCursoForm() {
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

	public long getProcessoCursoPK() {
		return processoCursoPK;
	}

	public void setProcessoCursoPK(long processoCursoPK) {
		this.processoCursoPK = processoCursoPK;
	}

	public long getProcessoPK() {
		return processoPK;
	}

	public void setProcessoPK(long processoPK) {
		this.processoPK = processoPK;
	}

	public String getProcessoNome() {
		return processoNome;
	}

	public void setProcessoNome(String processoNome) {
		this.processoNome = processoNome;
	}

	public String getProcessoCursoDataCriacao() {
		return processoCursoDataCriacao;
	}

	public void setProcessoCursoDataCriacao(String processoCursoDataCriacao) {
		this.processoCursoDataCriacao = processoCursoDataCriacao;
	}

	public AtributoStatus getProcessoCursoStatus() {
		return processoCursoStatus;
	}

	public void setProcessoCursoStatus(AtributoStatus processoCursoStatus) {
		this.processoCursoStatus = processoCursoStatus;
	}

	public String getProcessoCursoResponsavel() {
		return processoCursoResponsavel;
	}

	public void setProcessoCursoResponsavel(String processoCursoResponsavel) {
		this.processoCursoResponsavel = processoCursoResponsavel;
	}

	public String getProcessoCursoMotivoOperacao() {
		return processoCursoMotivoOperacao;
	}

	public void setProcessoCursoMotivoOperacao(String processoCursoMotivoOperacao) {
		this.processoCursoMotivoOperacao = processoCursoMotivoOperacao;
	}

}
