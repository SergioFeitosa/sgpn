package br.com.j4business.saga.habilidade.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class HabilidadeForm {

	private long habilidadePK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String habilidadeNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String habilidadeDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus habilidadeStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String habilidadeResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String habilidadeMotivoOperacao;

	public HabilidadeForm() {
		super();
	}

	public long getHabilidadePK() {
		return habilidadePK;
	}

	public void setHabilidadePK(long habilidadePK) {
		this.habilidadePK = habilidadePK;
	}

	public String getHabilidadeNome() {
		return habilidadeNome;
	}

	public void setHabilidadeNome(String habilidadeNome) {
		this.habilidadeNome = habilidadeNome;
	}

	public String getHabilidadeDescricao() {
		return habilidadeDescricao;
	}

	public void setHabilidadeDescricao(String habilidadeDescricao) {
		this.habilidadeDescricao = habilidadeDescricao;
	}

	public AtributoStatus getHabilidadeStatus() {
		return habilidadeStatus;
	}

	public void setHabilidadeStatus(AtributoStatus habilidadeStatus) {
		this.habilidadeStatus = habilidadeStatus;
	}

	public String getHabilidadeResponsavel() {
		return habilidadeResponsavel;
	}

	public void setHabilidadeResponsavel(String habilidadeResponsavel) {
		this.habilidadeResponsavel = habilidadeResponsavel;
	}

	public String getHabilidadeMotivoOperacao() {
		return habilidadeMotivoOperacao;
	}

	public void setHabilidadeMotivoOperacao(String habilidadeMotivoOperacao) {
		this.habilidadeMotivoOperacao = habilidadeMotivoOperacao;
	}

	
}
