package br.com.j4business.saga.treinamentoimagem.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class TreinamentoImagemForm {

	private long treinamentoImagemPK;
	private long imagemPK;
	private long treinamentoPK;
	
    @NotEmpty(message = "Nome do imagem é uma informação obrigatória.")
	@NotNull
	private String imagemNome;
	
    @NotEmpty(message = "Nome da Treinamento é uma informação obrigatória.")
	@NotNull
	private String treinamentoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoImagemDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus treinamentoImagemStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String treinamentoImagemResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String treinamentoImagemMotivoOperacao;

	public TreinamentoImagemForm() {
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

	public long getTreinamentoImagemPK() {
		return treinamentoImagemPK;
	}

	public void setTreinamentoImagemPK(long treinamentoImagemPK) {
		this.treinamentoImagemPK = treinamentoImagemPK;
	}

	public long getTreinamentoPK() {
		return treinamentoPK;
	}

	public void setTreinamentoPK(long treinamentoPK) {
		this.treinamentoPK = treinamentoPK;
	}

	public String getTreinamentoNome() {
		return treinamentoNome;
	}

	public void setTreinamentoNome(String treinamentoNome) {
		this.treinamentoNome = treinamentoNome;
	}

	public String getTreinamentoImagemDataCriacao() {
		return treinamentoImagemDataCriacao;
	}

	public void setTreinamentoImagemDataCriacao(String treinamentoImagemDataCriacao) {
		this.treinamentoImagemDataCriacao = treinamentoImagemDataCriacao;
	}

	public AtributoStatus getTreinamentoImagemStatus() {
		return treinamentoImagemStatus;
	}

	public void setTreinamentoImagemStatus(AtributoStatus treinamentoImagemStatus) {
		this.treinamentoImagemStatus = treinamentoImagemStatus;
	}

	public String getTreinamentoImagemResponsavel() {
		return treinamentoImagemResponsavel;
	}

	public void setTreinamentoImagemResponsavel(String treinamentoImagemResponsavel) {
		this.treinamentoImagemResponsavel = treinamentoImagemResponsavel;
	}

	public String getTreinamentoImagemMotivoOperacao() {
		return treinamentoImagemMotivoOperacao;
	}

	public void setTreinamentoImagemMotivoOperacao(String treinamentoImagemMotivoOperacao) {
		this.treinamentoImagemMotivoOperacao = treinamentoImagemMotivoOperacao;
	}

}
