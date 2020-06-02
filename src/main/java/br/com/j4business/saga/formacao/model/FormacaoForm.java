package br.com.j4business.saga.formacao.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class FormacaoForm {

	private long formacaoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String formacaoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String formacaoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus formacaoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String formacaoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String formacaoMotivoOperacao;

    @NotEmpty(message = "O nível da formação é uma informação obrigatória.")
	@NotNull(message = "O nível da formação é uma informação obrigatória.")
	private String formacaoNivel;
    
	public FormacaoForm() {
		super();
	}

	public long getFormacaoPK() {
		return formacaoPK;
	}

	public void setFormacaoPK(long formacaoPK) {
		this.formacaoPK = formacaoPK;
	}

	public String getFormacaoNome() {
		return formacaoNome;
	}

	public void setFormacaoNome(String formacaoNome) {
		this.formacaoNome = formacaoNome;
	}

	public String getFormacaoDescricao() {
		return formacaoDescricao;
	}

	public void setFormacaoDescricao(String formacaoDescricao) {
		this.formacaoDescricao = formacaoDescricao;
	}

	public String getFormacaoResponsavel() {
		return formacaoResponsavel;
	}

	public void setFormacaoResponsavel(String formacaoResponsavel) {
		this.formacaoResponsavel = formacaoResponsavel;
	}

	public String getFormacaoMotivoOperacao() {
		return formacaoMotivoOperacao;
	}

	public void setFormacaoMotivoOperacao(String formacaoMotivoOperacao) {
		this.formacaoMotivoOperacao = formacaoMotivoOperacao;
	}

	public AtributoStatus getFormacaoStatus() {
		return formacaoStatus;
	}

	public void setFormacaoStatus(AtributoStatus formacaoStatus) {
		this.formacaoStatus = formacaoStatus;
	}

	public String getFormacaoNivel() {
		return formacaoNivel;
	}

	public void setFormacaoNivel(String formacaoNivel) {
		this.formacaoNivel = formacaoNivel;
	}

	
}
