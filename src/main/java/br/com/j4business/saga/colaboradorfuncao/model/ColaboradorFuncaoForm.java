package br.com.j4business.saga.colaboradorfuncao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorFuncaoForm {

	private long colaboradorFuncaoPK;
	private long funcaoPK;
	private long colaboradorPK;
	
    @NotBlank(message = "Nome do funcao é uma informação obrigatória.")
	@NotNull
	private String funcaoNome;
	
    @NotBlank(message = "Nome da Colaborador é uma informação obrigatória.")
	@NotNull
	private String colaboradorNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  colaboradorFuncaoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorFuncaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String colaboradorFuncaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorFuncaoMotivoOperacao;

	public ColaboradorFuncaoForm() {
		super();
	}

	public long getFuncaoPK() {
		return funcaoPK;
	}

	public void setFuncaoPK(long funcaoPK) {
		this.funcaoPK = funcaoPK;
	}

	public String getFuncaoNome() {
		return funcaoNome;
	}

	public void setFuncaoNome(String funcaoNome) {
		this.funcaoNome = funcaoNome;
	}

	public long getColaboradorFuncaoPK() {
		return colaboradorFuncaoPK;
	}

	public void setColaboradorFuncaoPK(long colaboradorFuncaoPK) {
		this.colaboradorFuncaoPK = colaboradorFuncaoPK;
	}

	public long getColaboradorPK() {
		return colaboradorPK;
	}

	public void setColaboradorPK(long colaboradorPK) {
		this.colaboradorPK = colaboradorPK;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public String getColaboradorFuncaoDataCriacao() {
		return colaboradorFuncaoDataCriacao;
	}

	public void setColaboradorFuncaoDataCriacao(String colaboradorFuncaoDataCriacao) {
		this.colaboradorFuncaoDataCriacao = colaboradorFuncaoDataCriacao;
	}

	public AtributoStatus getColaboradorFuncaoStatus() {
		return colaboradorFuncaoStatus;
	}

	public void setColaboradorFuncaoStatus(AtributoStatus colaboradorFuncaoStatus) {
		this.colaboradorFuncaoStatus = colaboradorFuncaoStatus;
	}

	public String getColaboradorFuncaoResponsavel() {
		return colaboradorFuncaoResponsavel;
	}

	public void setColaboradorFuncaoResponsavel(String colaboradorFuncaoResponsavel) {
		this.colaboradorFuncaoResponsavel = colaboradorFuncaoResponsavel;
	}

	public String getColaboradorFuncaoMotivoOperacao() {
		return colaboradorFuncaoMotivoOperacao;
	}

	public void setColaboradorFuncaoMotivoOperacao(String colaboradorFuncaoMotivoOperacao) {
		this.colaboradorFuncaoMotivoOperacao = colaboradorFuncaoMotivoOperacao;
	}

}
