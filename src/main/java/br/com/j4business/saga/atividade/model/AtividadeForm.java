package br.com.j4business.saga.atividade.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AtividadeForm {

	private long atividadePK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 100, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String atividadeNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 300, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String atividadeDescricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus atividadeStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String atividadeResponsavel;

    @Min(value = 1,message = "Duração da Atividade deve ser igual ou maior que 1.")
	private int atividadeDuracao;

    @NotBlank(message = "O Gestor da Atividade é uma informação obrigatória.")
	@NotNull(message = "O Gestor da Atividade é uma informação obrigatória.")
	private String atividadeGestor;

    @NotBlank(message = "O Dono da Atividade é uma informação obrigatória.")
	@NotNull(message = "O Dono da Atividade é uma informação obrigatória.")
	private String atividadeDono;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String atividadeMotivoOperacao;

	public AtividadeForm() {
		super();
	}

	public long getAtividadePK() {
		return atividadePK;
	}

	public void setAtividadePK(long atividadePK) {
		this.atividadePK = atividadePK;
	}

	public String getAtividadeNome() {
		return atividadeNome;
	}

	public void setAtividadeNome(String atividadeNome) {
		this.atividadeNome = atividadeNome;
	}

	public String getAtividadeDescricao() {
		return atividadeDescricao;
	}

	public void setAtividadeDescricao(String atividadeDescricao) {
		this.atividadeDescricao = atividadeDescricao;
	}

	public AtributoStatus getAtividadeStatus() {
		return atividadeStatus;
	}

	public void setAtividadeStatus(AtributoStatus atividadeStatus) {
		this.atividadeStatus = atividadeStatus;
	}

	public String getAtividadeResponsavel() {
		return atividadeResponsavel;
	}

	public void setAtividadeResponsavel(String atividadeResponsavel) {
		this.atividadeResponsavel = atividadeResponsavel;
	}

	public String getAtividadeMotivoOperacao() {
		return atividadeMotivoOperacao;
	}

	public void setAtividadeMotivoOperacao(String atividadeMotivoOperacao) {
		this.atividadeMotivoOperacao = atividadeMotivoOperacao;
	}

	public String getAtividadeGestor() {
		return atividadeGestor;
	}

	public void setAtividadeGestor(String atividadeGestor) {
		this.atividadeGestor = atividadeGestor;
	}

	public String getAtividadeDono() {
		return atividadeDono;
	}

	public void setAtividadeDono(String atividadeDono) {
		this.atividadeDono = atividadeDono;
	}

	public int getAtividadeDuracao() {
		return atividadeDuracao;
	}

	public void setAtividadeDuracao(int atividadeDuracao) {
		this.atividadeDuracao = atividadeDuracao;
	}

}
