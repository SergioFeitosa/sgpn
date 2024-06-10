package br.com.j4business.saga.contratoimagem.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ContratoImagemForm {

	private long contratoImagemPK;
	private long imagemPK;
	private long contratoPK;
	
    @NotBlank(message = "Nome do imagem é uma informação obrigatória.")
	@NotNull
	private String imagemNome;
	
    @NotBlank(message = "Nome da Contrato é uma informação obrigatória.")
	@NotNull
	private String contratoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  contratoImagemDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus contratoImagemStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String contratoImagemResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String contratoImagemMotivoOperacao;

	public ContratoImagemForm() {
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

	public long getContratoImagemPK() {
		return contratoImagemPK;
	}

	public void setContratoImagemPK(long contratoImagemPK) {
		this.contratoImagemPK = contratoImagemPK;
	}

	public long getContratoPK() {
		return contratoPK;
	}

	public void setContratoPK(long contratoPK) {
		this.contratoPK = contratoPK;
	}

	public String getContratoNome() {
		return contratoNome;
	}

	public void setContratoNome(String contratoNome) {
		this.contratoNome = contratoNome;
	}

	public String getContratoImagemDataCriacao() {
		return contratoImagemDataCriacao;
	}

	public void setContratoImagemDataCriacao(String contratoImagemDataCriacao) {
		this.contratoImagemDataCriacao = contratoImagemDataCriacao;
	}

	public AtributoStatus getContratoImagemStatus() {
		return contratoImagemStatus;
	}

	public void setContratoImagemStatus(AtributoStatus contratoImagemStatus) {
		this.contratoImagemStatus = contratoImagemStatus;
	}

	public String getContratoImagemResponsavel() {
		return contratoImagemResponsavel;
	}

	public void setContratoImagemResponsavel(String contratoImagemResponsavel) {
		this.contratoImagemResponsavel = contratoImagemResponsavel;
	}

	public String getContratoImagemMotivoOperacao() {
		return contratoImagemMotivoOperacao;
	}

	public void setContratoImagemMotivoOperacao(String contratoImagemMotivoOperacao) {
		this.contratoImagemMotivoOperacao = contratoImagemMotivoOperacao;
	}

}
