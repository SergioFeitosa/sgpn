package br.com.j4business.saga.cenario.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class CenarioForm {

	private long cenarioPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String cenarioNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String cenarioDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus cenarioStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String cenarioResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String cenarioMotivoOperacao;

	public CenarioForm() {
		super();
	}

	public long getCenarioPK() {
		return cenarioPK;
	}

	public void setCenarioPK(long cenarioPK) {
		this.cenarioPK = cenarioPK;
	}

	public String getCenarioNome() {
		return cenarioNome;
	}

	public void setCenarioNome(String cenarioNome) {
		this.cenarioNome = cenarioNome;
	}

	public String getCenarioDescricao() {
		return cenarioDescricao;
	}

	public void setCenarioDescricao(String cenarioDescricao) {
		this.cenarioDescricao = cenarioDescricao;
	}

	public AtributoStatus getCenarioStatus() {
		return cenarioStatus;
	}

	public void setCenarioStatus(AtributoStatus cenarioStatus) {
		this.cenarioStatus = cenarioStatus;
	}

	public String getCenarioResponsavel() {
		return cenarioResponsavel;
	}

	public void setCenarioResponsavel(String cenarioResponsavel) {
		this.cenarioResponsavel = cenarioResponsavel;
	}

	public String getCenarioMotivoOperacao() {
		return cenarioMotivoOperacao;
	}

	public void setCenarioMotivoOperacao(String cenarioMotivoOperacao) {
		this.cenarioMotivoOperacao = cenarioMotivoOperacao;
	}

	
}
