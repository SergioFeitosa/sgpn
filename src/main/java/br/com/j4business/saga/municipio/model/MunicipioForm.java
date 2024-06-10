package br.com.j4business.saga.municipio.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class MunicipioForm {

	private long municipioPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 100, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String municipioNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 9, message = "A descrição não pode ter menos que 5 e mais que 9 caracteres")
	private String municipioCEP;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus municipioStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String municipioResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String municipioMotivoOperacao;

    
	public MunicipioForm() {
		super();
	}

	public long getMunicipioPK() {
		return municipioPK;
	}

	public void setMunicipioPK(long municipioPK) {
		this.municipioPK = municipioPK;
	}

	public String getMunicipioNome() {
		return municipioNome;
	}

	public void setMunicipioNome(String municipioNome) {
		this.municipioNome = municipioNome;
	}

	public String getMunicipioCEP() {
		return municipioCEP;
	}

	public void setMunicipioCEP(String municipioCEP) {
		this.municipioCEP = municipioCEP;
	}

	public AtributoStatus getMunicipioStatus() {
		return municipioStatus;
	}

	public void setMunicipioStatus(AtributoStatus municipioStatus) {
		this.municipioStatus = municipioStatus;
	}

	public String getMunicipioResponsavel() {
		return municipioResponsavel;
	}

	public void setMunicipioResponsavel(String municipioResponsavel) {
		this.municipioResponsavel = municipioResponsavel;
	}

	public String getMunicipioMotivoOperacao() {
		return municipioMotivoOperacao;
	}

	public void setMunicipioMotivoOperacao(String municipioMotivoOperacao) {
		this.municipioMotivoOperacao = municipioMotivoOperacao;
	}

	
}
