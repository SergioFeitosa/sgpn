package br.com.j4business.saga.atendimento.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AtendimentoForm {

	private long atendimentoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String atendimentoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String atendimentoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus atendimentoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String atendimentoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String atendimentoMotivoOperacao;

	public AtendimentoForm() {
		super();
	}

	public long getAtendimentoPK() {
		return atendimentoPK;
	}

	public void setAtendimentoPK(long atendimentoPK) {
		this.atendimentoPK = atendimentoPK;
	}

	public String getAtendimentoNome() {
		return atendimentoNome;
	}

	public void setAtendimentoNome(String atendimentoNome) {
		this.atendimentoNome = atendimentoNome;
	}

	public String getAtendimentoDescricao() {
		return atendimentoDescricao;
	}

	public void setAtendimentoDescricao(String atendimentoDescricao) {
		this.atendimentoDescricao = atendimentoDescricao;
	}

	public String getAtendimentoResponsavel() {
		return atendimentoResponsavel;
	}

	public void setAtendimentoResponsavel(String atendimentoResponsavel) {
		this.atendimentoResponsavel = atendimentoResponsavel;
	}

	public String getAtendimentoMotivoOperacao() {
		return atendimentoMotivoOperacao;
	}

	public void setAtendimentoMotivoOperacao(String atendimentoMotivoOperacao) {
		this.atendimentoMotivoOperacao = atendimentoMotivoOperacao;
	}

	public AtributoStatus getAtendimentoStatus() {
		return atendimentoStatus;
	}

	public void setAtendimentoStatus(AtributoStatus atendimentoStatus) {
		this.atendimentoStatus = atendimentoStatus;
	}

	
}
