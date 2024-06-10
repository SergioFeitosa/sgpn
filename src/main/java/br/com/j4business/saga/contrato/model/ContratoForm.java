package br.com.j4business.saga.contrato.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ContratoForm {

	private long contratoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String contratoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String contratoDescricao;

	@NotNull(message = "Descrição é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private String contratoDuracao;

	@NotNull(message = "Data de Início é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  contratoDataInicio;

	@NotNull(message = "Data de Término é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  contratoDataTermino;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus contratoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String contratoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String contratoMotivoOperacao;

    @NotBlank(message = "O Gestor do Contrato é uma informação obrigatória.")
	@NotNull(message = "O Gestor do Contrato é uma informação obrigatória.")
	private String contratoGestor;

    @NotBlank(message = "O Dono do Contrato é uma informação obrigatória.")
	@NotNull(message = "O Dono do Contrato é uma informação obrigatória.")
	private String contratoDono;
    
	public ContratoForm() {
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

	public String getContratoDescricao() {
		return contratoDescricao;
	}

	public void setContratoDescricao(String contratoDescricao) {
		this.contratoDescricao = contratoDescricao;
	}

	public AtributoStatus getContratoStatus() {
		return contratoStatus;
	}

	public void setContratoStatus(AtributoStatus contratoStatus) {
		this.contratoStatus = contratoStatus;
	}

	public String getContratoResponsavel() {
		return contratoResponsavel;
	}

	public void setContratoResponsavel(String contratoResponsavel) {
		this.contratoResponsavel = contratoResponsavel;
	}

	public String getContratoMotivoOperacao() {
		return contratoMotivoOperacao;
	}

	public void setContratoMotivoOperacao(String contratoMotivoOperacao) {
		this.contratoMotivoOperacao = contratoMotivoOperacao;
	}

	public String getContratoDuracao() {
		return contratoDuracao;
	}

	public void setContratoDuracao(String contratoDuracao) {
		this.contratoDuracao = contratoDuracao;
	}

	public String getContratoDataInicio() {
		return contratoDataInicio;
	}

	public void setContratoDataInicio(String contratoDataInicio) {
		this.contratoDataInicio = contratoDataInicio;
	}

	public String getContratoDataTermino() {
		return contratoDataTermino;
	}

	public void setContratoDataTermino(String contratoDataTermino) {
		this.contratoDataTermino = contratoDataTermino;
	}

	public String getContratoGestor() {
		return contratoGestor;
	}

	public void setContratoGestor(String contratoGestor) {
		this.contratoGestor = contratoGestor;
	}

	public String getContratoDono() {
		return contratoDono;
	}

	public void setContratoDono(String contratoDono) {
		this.contratoDono = contratoDono;
	}

	
}
