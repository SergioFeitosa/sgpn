package br.com.j4business.saga.ocorrencia.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class OcorrenciaForm {

	private long ocorrenciaPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String ocorrenciaNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String ocorrenciaDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus ocorrenciaStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String ocorrenciaResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String ocorrenciaMotivoOperacao;

	public OcorrenciaForm() {
		super();
	}

	public long getOcorrenciaPK() {
		return ocorrenciaPK;
	}

	public void setOcorrenciaPK(long ocorrenciaPK) {
		this.ocorrenciaPK = ocorrenciaPK;
	}

	public String getOcorrenciaNome() {
		return ocorrenciaNome;
	}

	public void setOcorrenciaNome(String ocorrenciaNome) {
		this.ocorrenciaNome = ocorrenciaNome;
	}

	public String getOcorrenciaDescricao() {
		return ocorrenciaDescricao;
	}

	public void setOcorrenciaDescricao(String ocorrenciaDescricao) {
		this.ocorrenciaDescricao = ocorrenciaDescricao;
	}

	public AtributoStatus getOcorrenciaStatus() {
		return ocorrenciaStatus;
	}

	public void setOcorrenciaStatus(AtributoStatus ocorrenciaStatus) {
		this.ocorrenciaStatus = ocorrenciaStatus;
	}

	public String getOcorrenciaResponsavel() {
		return ocorrenciaResponsavel;
	}

	public void setOcorrenciaResponsavel(String ocorrenciaResponsavel) {
		this.ocorrenciaResponsavel = ocorrenciaResponsavel;
	}

	public String getOcorrenciaMotivoOperacao() {
		return ocorrenciaMotivoOperacao;
	}

	public void setOcorrenciaMotivoOperacao(String ocorrenciaMotivoOperacao) {
		this.ocorrenciaMotivoOperacao = ocorrenciaMotivoOperacao;
	}

	
}
