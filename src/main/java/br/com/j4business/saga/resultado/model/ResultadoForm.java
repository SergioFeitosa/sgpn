package br.com.j4business.saga.resultado.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ResultadoForm {

	private long resultadoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String resultadoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String resultadoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus resultadoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String resultadoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String resultadoMotivoOperacao;

	public ResultadoForm() {
		super();
	}

	public long getResultadoPK() {
		return resultadoPK;
	}

	public void setResultadoPK(long resultadoPK) {
		this.resultadoPK = resultadoPK;
	}

	public String getResultadoNome() {
		return resultadoNome;
	}

	public void setResultadoNome(String resultadoNome) {
		this.resultadoNome = resultadoNome;
	}

	public String getResultadoDescricao() {
		return resultadoDescricao;
	}

	public void setResultadoDescricao(String resultadoDescricao) {
		this.resultadoDescricao = resultadoDescricao;
	}

	public AtributoStatus getResultadoStatus() {
		return resultadoStatus;
	}

	public void setResultadoStatus(AtributoStatus resultadoStatus) {
		this.resultadoStatus = resultadoStatus;
	}

	public String getResultadoResponsavel() {
		return resultadoResponsavel;
	}

	public void setResultadoResponsavel(String resultadoResponsavel) {
		this.resultadoResponsavel = resultadoResponsavel;
	}

	public String getResultadoMotivoOperacao() {
		return resultadoMotivoOperacao;
	}

	public void setResultadoMotivoOperacao(String resultadoMotivoOperacao) {
		this.resultadoMotivoOperacao = resultadoMotivoOperacao;
	}

	
}
