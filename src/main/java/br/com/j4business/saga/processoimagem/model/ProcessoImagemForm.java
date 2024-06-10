package br.com.j4business.saga.processoimagem.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoImagemForm {

	private long processoImagemPK;
	private long imagemPK;
	private long processoPK;
	
    @NotBlank(message = "Nome do imagem é uma informação obrigatória.")
	@NotNull
	private String imagemNome;
	
    @NotBlank(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoImagemDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoImagemStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoImagemResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoImagemMotivoOperacao;

	public ProcessoImagemForm() {
		super();
	}

	public long getImagemPK() {
		return imagemPK;
	}

	public void setImagemPK(long imagemPK) {
		this.imagemPK = imagemPK;
	}

	public String getImagemNome() {
		return imagemNome;
	}

	public void setImagemNome(String imagemNome) {
		this.imagemNome = imagemNome;
	}

	public long getProcessoImagemPK() {
		return processoImagemPK;
	}

	public void setProcessoImagemPK(long processoImagemPK) {
		this.processoImagemPK = processoImagemPK;
	}

	public long getProcessoPK() {
		return processoPK;
	}

	public void setProcessoPK(long processoPK) {
		this.processoPK = processoPK;
	}

	public String getProcessoNome() {
		return processoNome;
	}

	public void setProcessoNome(String processoNome) {
		this.processoNome = processoNome;
	}

	public String getProcessoImagemDataCriacao() {
		return processoImagemDataCriacao;
	}

	public void setProcessoImagemDataCriacao(String processoImagemDataCriacao) {
		this.processoImagemDataCriacao = processoImagemDataCriacao;
	}

	public AtributoStatus getProcessoImagemStatus() {
		return processoImagemStatus;
	}

	public void setProcessoImagemStatus(AtributoStatus processoImagemStatus) {
		this.processoImagemStatus = processoImagemStatus;
	}

	public String getProcessoImagemResponsavel() {
		return processoImagemResponsavel;
	}

	public void setProcessoImagemResponsavel(String processoImagemResponsavel) {
		this.processoImagemResponsavel = processoImagemResponsavel;
	}

	public String getProcessoImagemMotivoOperacao() {
		return processoImagemMotivoOperacao;
	}

	public void setProcessoImagemMotivoOperacao(String processoImagemMotivoOperacao) {
		this.processoImagemMotivoOperacao = processoImagemMotivoOperacao;
	}

}
