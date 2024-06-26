package br.com.j4business.saga.certificacao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class CertificacaoForm {

	private long certificacaoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String certificacaoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String certificacaoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus certificacaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String certificacaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String certificacaoMotivoOperacao;

	public CertificacaoForm() {
		super();
	}

	public long getCertificacaoPK() {
		return certificacaoPK;
	}

	public void setCertificacaoPK(long certificacaoPK) {
		this.certificacaoPK = certificacaoPK;
	}

	public String getCertificacaoNome() {
		return certificacaoNome;
	}

	public void setCertificacaoNome(String certificacaoNome) {
		this.certificacaoNome = certificacaoNome;
	}

	public String getCertificacaoDescricao() {
		return certificacaoDescricao;
	}

	public void setCertificacaoDescricao(String certificacaoDescricao) {
		this.certificacaoDescricao = certificacaoDescricao;
	}

	public String getCertificacaoResponsavel() {
		return certificacaoResponsavel;
	}

	public void setCertificacaoResponsavel(String certificacaoResponsavel) {
		this.certificacaoResponsavel = certificacaoResponsavel;
	}

	public String getCertificacaoMotivoOperacao() {
		return certificacaoMotivoOperacao;
	}

	public void setCertificacaoMotivoOperacao(String certificacaoMotivoOperacao) {
		this.certificacaoMotivoOperacao = certificacaoMotivoOperacao;
	}

	public AtributoStatus getCertificacaoStatus() {
		return certificacaoStatus;
	}

	public void setCertificacaoStatus(AtributoStatus certificacaoStatus) {
		this.certificacaoStatus = certificacaoStatus;
	}

	
}
