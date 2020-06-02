package br.com.j4business.saga.pais.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class PaisForm {

	private long paisPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 100, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String paisNome;
	
    @NotEmpty(message = "Código do País é uma informação obrigatória.")
	@Size(min = 3,max = 3, message = "O código do País deve ter 3 caracteres")
	private String paisCodigo;

    @NotEmpty(message = "Sigla do País é uma informação obrigatória.")
	@Size(min = 2,max = 2, message = "A sigla do País deve ter 2 caracteres")
	private String paisSigla;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus paisStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String paisResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String paisMotivoOperacao;

	public PaisForm() {
		super();
	}

	public long getPaisPK() {
		return paisPK;
	}

	public void setPaisPK(long paisPK) {
		this.paisPK = paisPK;
	}

	public String getPaisNome() {
		return paisNome;
	}

	public void setPaisNome(String paisNome) {
		this.paisNome = paisNome;
	}

	public AtributoStatus getPaisStatus() {
		return paisStatus;
	}

	public void setPaisStatus(AtributoStatus paisStatus) {
		this.paisStatus = paisStatus;
	}

	public String getPaisResponsavel() {
		return paisResponsavel;
	}

	public void setPaisResponsavel(String paisResponsavel) {
		this.paisResponsavel = paisResponsavel;
	}

	public String getPaisMotivoOperacao() {
		return paisMotivoOperacao;
	}

	public void setPaisMotivoOperacao(String paisMotivoOperacao) {
		this.paisMotivoOperacao = paisMotivoOperacao;
	}

	public String getPaisCodigo() {
		return paisCodigo;
	}

	public void setPaisCodigo(String paisCodigo) {
		this.paisCodigo = paisCodigo;
	}

	public String getPaisSigla() {
		return paisSigla;
	}

	public void setPaisSigla(String paisSigla) {
		this.paisSigla = paisSigla;
	}

	
}
