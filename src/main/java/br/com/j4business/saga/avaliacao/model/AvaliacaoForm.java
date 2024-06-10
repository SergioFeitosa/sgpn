package br.com.j4business.saga.avaliacao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AvaliacaoForm {

	private long avaliacaoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String avaliacaoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String avaliacaoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus avaliacaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String avaliacaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String avaliacaoMotivoOperacao;

	public AvaliacaoForm() {
		super();
	}

	public long getAvaliacaoPK() {
		return avaliacaoPK;
	}

	public void setAvaliacaoPK(long avaliacaoPK) {
		this.avaliacaoPK = avaliacaoPK;
	}

	public String getAvaliacaoNome() {
		return avaliacaoNome;
	}

	public void setAvaliacaoNome(String avaliacaoNome) {
		this.avaliacaoNome = avaliacaoNome;
	}

	public String getAvaliacaoDescricao() {
		return avaliacaoDescricao;
	}

	public void setAvaliacaoDescricao(String avaliacaoDescricao) {
		this.avaliacaoDescricao = avaliacaoDescricao;
	}

	public String getAvaliacaoResponsavel() {
		return avaliacaoResponsavel;
	}

	public void setAvaliacaoResponsavel(String avaliacaoResponsavel) {
		this.avaliacaoResponsavel = avaliacaoResponsavel;
	}

	public String getAvaliacaoMotivoOperacao() {
		return avaliacaoMotivoOperacao;
	}

	public void setAvaliacaoMotivoOperacao(String avaliacaoMotivoOperacao) {
		this.avaliacaoMotivoOperacao = avaliacaoMotivoOperacao;
	}

	public AtributoStatus getAvaliacaoStatus() {
		return avaliacaoStatus;
	}

	public void setAvaliacaoStatus(AtributoStatus avaliacaoStatus) {
		this.avaliacaoStatus = avaliacaoStatus;
	}

	
}
