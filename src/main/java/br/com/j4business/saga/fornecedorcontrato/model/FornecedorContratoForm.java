package br.com.j4business.saga.fornecedorcontrato.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class FornecedorContratoForm {

	private long fornecedorContratoPK;
	private long contratoPK;
	private long fornecedorPK;
	
    @NotEmpty(message = "Nome do contrato é uma informação obrigatória.")
	@NotNull
	private String contratoNome;
	
    @NotEmpty(message = "Nome da Fornecedor é uma informação obrigatória.")
	@NotNull
	private String fornecedorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  fornecedorContratoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus fornecedorContratoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String fornecedorContratoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String fornecedorContratoMotivoOperacao;

	public FornecedorContratoForm() {
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

	public long getFornecedorContratoPK() {
		return fornecedorContratoPK;
	}

	public void setFornecedorContratoPK(long fornecedorContratoPK) {
		this.fornecedorContratoPK = fornecedorContratoPK;
	}

	public long getFornecedorPK() {
		return fornecedorPK;
	}

	public void setFornecedorPK(long fornecedorPK) {
		this.fornecedorPK = fornecedorPK;
	}

	public String getFornecedorNome() {
		return fornecedorNome;
	}

	public void setFornecedorNome(String fornecedorNome) {
		this.fornecedorNome = fornecedorNome;
	}

	public String getFornecedorContratoDataCriacao() {
		return fornecedorContratoDataCriacao;
	}

	public void setFornecedorContratoDataCriacao(String fornecedorContratoDataCriacao) {
		this.fornecedorContratoDataCriacao = fornecedorContratoDataCriacao;
	}

	public AtributoStatus getFornecedorContratoStatus() {
		return fornecedorContratoStatus;
	}

	public void setFornecedorContratoStatus(AtributoStatus fornecedorContratoStatus) {
		this.fornecedorContratoStatus = fornecedorContratoStatus;
	}

	public String getFornecedorContratoResponsavel() {
		return fornecedorContratoResponsavel;
	}

	public void setFornecedorContratoResponsavel(String fornecedorContratoResponsavel) {
		this.fornecedorContratoResponsavel = fornecedorContratoResponsavel;
	}

	public String getFornecedorContratoMotivoOperacao() {
		return fornecedorContratoMotivoOperacao;
	}

	public void setFornecedorContratoMotivoOperacao(String fornecedorContratoMotivoOperacao) {
		this.fornecedorContratoMotivoOperacao = fornecedorContratoMotivoOperacao;
	}

}
