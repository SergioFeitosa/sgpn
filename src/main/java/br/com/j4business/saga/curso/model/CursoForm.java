package br.com.j4business.saga.curso.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class CursoForm {

	private long cursoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String cursoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String cursoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus cursoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String cursoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String cursoMotivoOperacao;

	public CursoForm() {
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

	public AtributoStatus getCursoStatus() {
		return cursoStatus;
	}

	public void setCursoStatus(AtributoStatus cursoStatus) {
		this.cursoStatus = cursoStatus;
	}

	public String getCursoResponsavel() {
		return cursoResponsavel;
	}

	public void setCursoResponsavel(String cursoResponsavel) {
		this.cursoResponsavel = cursoResponsavel;
	}

	public String getCursoMotivoOperacao() {
		return cursoMotivoOperacao;
	}

	public void setCursoMotivoOperacao(String cursoMotivoOperacao) {
		this.cursoMotivoOperacao = cursoMotivoOperacao;
	}

	
}
