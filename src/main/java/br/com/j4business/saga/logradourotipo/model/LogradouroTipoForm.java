package br.com.j4business.saga.logradourotipo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class LogradouroTipoForm {

	private long logradouroTipoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String logradouroTipoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String logradouroTipoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus logradouroTipoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String logradouroTipoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String logradouroTipoMotivoOperacao;

	public LogradouroTipoForm() {
		super();
	}

	public long getLogradouroTipoPK() {
		return logradouroTipoPK;
	}

	public void setLogradouroTipoPK(long logradouroTipoPK) {
		this.logradouroTipoPK = logradouroTipoPK;
	}

	public String getLogradouroTipoNome() {
		return logradouroTipoNome;
	}

	public void setLogradouroTipoNome(String logradouroTipoNome) {
		this.logradouroTipoNome = logradouroTipoNome;
	}

	public String getLogradouroTipoDescricao() {
		return logradouroTipoDescricao;
	}

	public void setLogradouroTipoDescricao(String logradouroTipoDescricao) {
		this.logradouroTipoDescricao = logradouroTipoDescricao;
	}

	public AtributoStatus getLogradouroTipoStatus() {
		return logradouroTipoStatus;
	}

	public String getLogradouroTipoResponsavel() {
		return logradouroTipoResponsavel;
	}

	public void setLogradouroTipoResponsavel(String logradouroTipoResponsavel) {
		this.logradouroTipoResponsavel = logradouroTipoResponsavel;
	}

	public String getLogradouroTipoMotivoOperacao() {
		return logradouroTipoMotivoOperacao;
	}

	public void setLogradouroTipoMotivoOperacao(String logradouroTipoMotivoOperacao) {
		this.logradouroTipoMotivoOperacao = logradouroTipoMotivoOperacao;
	}

	public void setLogradouroTipoStatus(AtributoStatus logradouroTipoStatus) {
		this.logradouroTipoStatus = logradouroTipoStatus;
	}

	
}
