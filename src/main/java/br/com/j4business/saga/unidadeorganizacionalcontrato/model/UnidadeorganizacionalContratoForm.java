package br.com.j4business.saga.unidadeorganizacionalcontrato.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class UnidadeorganizacionalContratoForm {

	private long unidadeorganizacionalContratoPK;
	private long contratoPK;
	private long unidadeorganizacionalPK;
	
    @NotBlank(message = "Nome do contrato é uma informação obrigatória.")
	@NotNull
	private String contratoNome;
	
    @NotBlank(message = "Nome da Unidadeorganizacional é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  unidadeorganizacionalContratoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus unidadeorganizacionalContratoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalContratoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String unidadeorganizacionalContratoMotivoOperacao;

	public UnidadeorganizacionalContratoForm() {
		super();
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

	public long getUnidadeorganizacionalContratoPK() {
		return unidadeorganizacionalContratoPK;
	}

	public void setUnidadeorganizacionalContratoPK(long unidadeorganizacionalContratoPK) {
		this.unidadeorganizacionalContratoPK = unidadeorganizacionalContratoPK;
	}

	public long getUnidadeorganizacionalPK() {
		return unidadeorganizacionalPK;
	}

	public void setUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		this.unidadeorganizacionalPK = unidadeorganizacionalPK;
	}

	public String getUnidadeorganizacionalNome() {
		return unidadeorganizacionalNome;
	}

	public void setUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		this.unidadeorganizacionalNome = unidadeorganizacionalNome;
	}

	public String getUnidadeorganizacionalContratoDataCriacao() {
		return unidadeorganizacionalContratoDataCriacao;
	}

	public void setUnidadeorganizacionalContratoDataCriacao(String unidadeorganizacionalContratoDataCriacao) {
		this.unidadeorganizacionalContratoDataCriacao = unidadeorganizacionalContratoDataCriacao;
	}

	public AtributoStatus getUnidadeorganizacionalContratoStatus() {
		return unidadeorganizacionalContratoStatus;
	}

	public void setUnidadeorganizacionalContratoStatus(AtributoStatus unidadeorganizacionalContratoStatus) {
		this.unidadeorganizacionalContratoStatus = unidadeorganizacionalContratoStatus;
	}

	public String getUnidadeorganizacionalContratoResponsavel() {
		return unidadeorganizacionalContratoResponsavel;
	}

	public void setUnidadeorganizacionalContratoResponsavel(String unidadeorganizacionalContratoResponsavel) {
		this.unidadeorganizacionalContratoResponsavel = unidadeorganizacionalContratoResponsavel;
	}

	public String getUnidadeorganizacionalContratoMotivoOperacao() {
		return unidadeorganizacionalContratoMotivoOperacao;
	}

	public void setUnidadeorganizacionalContratoMotivoOperacao(String unidadeorganizacionalContratoMotivoOperacao) {
		this.unidadeorganizacionalContratoMotivoOperacao = unidadeorganizacionalContratoMotivoOperacao;
	}

}
