package br.com.j4business.saga.pessoa.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoPessoaTipo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class PessoaForm {

	private long pessoaPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String pessoaNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String pessoaDescricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoPessoaTipo pessoaTipo;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus pessoaStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Responsável não pode ter menos que 5 e mais que 200 caracteres")
	private String pessoaResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String pessoaMotivoOperacao;

	public PessoaForm() {
		super();
	}

	public long getPessoaPK() {
		return pessoaPK;
	}

	public void setPessoaPK(long pessoaPK) {
		this.pessoaPK = pessoaPK;
	}

	public String getPessoaNome() {
		return pessoaNome;
	}

	public void setPessoaNome(String pessoaNome) {
		this.pessoaNome = pessoaNome;
	}

	public String getPessoaDescricao() {
		return pessoaDescricao;
	}

	public void setPessoaDescricao(String pessoaDescricao) {
		this.pessoaDescricao = pessoaDescricao;
	}

	public AtributoStatus getPessoaStatus() {
		return pessoaStatus;
	}

	public void setPessoaStatus(AtributoStatus pessoaStatus) {
		this.pessoaStatus = pessoaStatus;
	}

	public String getPessoaResponsavel() {
		return pessoaResponsavel;
	}

	public void setPessoaResponsavel(String pessoaResponsavel) {
		this.pessoaResponsavel = pessoaResponsavel;
	}

	public String getPessoaMotivoOperacao() {
		return pessoaMotivoOperacao;
	}

	public void setPessoaMotivoOperacao(String pessoaMotivoOperacao) {
		this.pessoaMotivoOperacao = pessoaMotivoOperacao;
	}

	public AtributoPessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}

	public void setPessoaTipo(AtributoPessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}

	
}
