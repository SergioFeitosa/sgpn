package br.com.j4business.saga.funcao.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class FuncaoForm {

	private long funcaoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String funcaoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String funcaoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus funcaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String funcaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String funcaoMotivoOperacao;

	public FuncaoForm() {
		super();
	}

	public long getFuncaoPK() {
		return funcaoPK;
	}

	public void setFuncaoPK(long funcaoPK) {
		this.funcaoPK = funcaoPK;
	}

	public String getFuncaoNome() {
		return funcaoNome;
	}

	public void setFuncaoNome(String funcaoNome) {
		this.funcaoNome = funcaoNome;
	}

	public String getFuncaoDescricao() {
		return funcaoDescricao;
	}

	public void setFuncaoDescricao(String funcaoDescricao) {
		this.funcaoDescricao = funcaoDescricao;
	}

	public AtributoStatus getFuncaoStatus() {
		return funcaoStatus;
	}

	public void setFuncaoStatus(AtributoStatus funcaoStatus) {
		this.funcaoStatus = funcaoStatus;
	}

	public String getFuncaoResponsavel() {
		return funcaoResponsavel;
	}

	public void setFuncaoResponsavel(String funcaoResponsavel) {
		this.funcaoResponsavel = funcaoResponsavel;
	}

	public String getFuncaoMotivoOperacao() {
		return funcaoMotivoOperacao;
	}

	public void setFuncaoMotivoOperacao(String funcaoMotivoOperacao) {
		this.funcaoMotivoOperacao = funcaoMotivoOperacao;
	}

	
}
