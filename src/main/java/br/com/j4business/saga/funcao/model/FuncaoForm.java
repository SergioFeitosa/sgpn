package br.com.j4business.saga.funcao.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class FuncaoForm {

	private long funcaoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String funcaoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String funcaoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus funcaoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String funcaoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
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
