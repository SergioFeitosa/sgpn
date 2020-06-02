package br.com.j4business.saga.unidadeorganizacionalprocesso.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class UnidadeorganizacionalProcessoForm {

	private long unidadeorganizacionalProcessoPK;
	private long processoPK;
	private long unidadeorganizacionalPK;
	
    @NotEmpty(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotEmpty(message = "Nome da Unidadeorganizacional é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  unidadeorganizacionalProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus unidadeorganizacionalProcessoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalProcessoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String unidadeorganizacionalProcessoMotivoOperacao;

	public UnidadeorganizacionalProcessoForm() {
		super();
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

	public long getUnidadeorganizacionalProcessoPK() {
		return unidadeorganizacionalProcessoPK;
	}

	public void setUnidadeorganizacionalProcessoPK(long unidadeorganizacionalProcessoPK) {
		this.unidadeorganizacionalProcessoPK = unidadeorganizacionalProcessoPK;
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

	public String getUnidadeorganizacionalProcessoDataCriacao() {
		return unidadeorganizacionalProcessoDataCriacao;
	}

	public void setUnidadeorganizacionalProcessoDataCriacao(String unidadeorganizacionalProcessoDataCriacao) {
		this.unidadeorganizacionalProcessoDataCriacao = unidadeorganizacionalProcessoDataCriacao;
	}

	public AtributoStatus getUnidadeorganizacionalProcessoStatus() {
		return unidadeorganizacionalProcessoStatus;
	}

	public void setUnidadeorganizacionalProcessoStatus(AtributoStatus unidadeorganizacionalProcessoStatus) {
		this.unidadeorganizacionalProcessoStatus = unidadeorganizacionalProcessoStatus;
	}

	public String getUnidadeorganizacionalProcessoResponsavel() {
		return unidadeorganizacionalProcessoResponsavel;
	}

	public void setUnidadeorganizacionalProcessoResponsavel(String unidadeorganizacionalProcessoResponsavel) {
		this.unidadeorganizacionalProcessoResponsavel = unidadeorganizacionalProcessoResponsavel;
	}

	public String getUnidadeorganizacionalProcessoMotivoOperacao() {
		return unidadeorganizacionalProcessoMotivoOperacao;
	}

	public void setUnidadeorganizacionalProcessoMotivoOperacao(String unidadeorganizacionalProcessoMotivoOperacao) {
		this.unidadeorganizacionalProcessoMotivoOperacao = unidadeorganizacionalProcessoMotivoOperacao;
	}

}
