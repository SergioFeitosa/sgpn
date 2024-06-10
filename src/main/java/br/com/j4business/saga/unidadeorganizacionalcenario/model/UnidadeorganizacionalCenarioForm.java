package br.com.j4business.saga.unidadeorganizacionalcenario.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class UnidadeorganizacionalCenarioForm {

	private long unidadeorganizacionalCenarioPK;
	private long cenarioPK;
	private long unidadeorganizacionalPK;
	
    @NotBlank(message = "Nome do cenario é uma informação obrigatória.")
	@NotNull
	private String cenarioNome;
	
    @NotBlank(message = "Nome da Unidadeorganizacional é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  unidadeorganizacionalCenarioDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus unidadeorganizacionalCenarioStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String unidadeorganizacionalCenarioResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String unidadeorganizacionalCenarioMotivoOperacao;

	public UnidadeorganizacionalCenarioForm() {
		super();
	}

	public long getCenarioPK() {
		return cenarioPK;
	}

	public void setCenarioPK(long cenarioPK) {
		this.cenarioPK = cenarioPK;
	}

	public String getCenarioNome() {
		return cenarioNome;
	}

	public void setCenarioNome(String cenarioNome) {
		this.cenarioNome = cenarioNome;
	}

	public long getUnidadeorganizacionalCenarioPK() {
		return unidadeorganizacionalCenarioPK;
	}

	public void setUnidadeorganizacionalCenarioPK(long unidadeorganizacionalCenarioPK) {
		this.unidadeorganizacionalCenarioPK = unidadeorganizacionalCenarioPK;
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

	public String getUnidadeorganizacionalCenarioDataCriacao() {
		return unidadeorganizacionalCenarioDataCriacao;
	}

	public void setUnidadeorganizacionalCenarioDataCriacao(String unidadeorganizacionalCenarioDataCriacao) {
		this.unidadeorganizacionalCenarioDataCriacao = unidadeorganizacionalCenarioDataCriacao;
	}

	public AtributoStatus getUnidadeorganizacionalCenarioStatus() {
		return unidadeorganizacionalCenarioStatus;
	}

	public void setUnidadeorganizacionalCenarioStatus(AtributoStatus unidadeorganizacionalCenarioStatus) {
		this.unidadeorganizacionalCenarioStatus = unidadeorganizacionalCenarioStatus;
	}

	public String getUnidadeorganizacionalCenarioResponsavel() {
		return unidadeorganizacionalCenarioResponsavel;
	}

	public void setUnidadeorganizacionalCenarioResponsavel(String unidadeorganizacionalCenarioResponsavel) {
		this.unidadeorganizacionalCenarioResponsavel = unidadeorganizacionalCenarioResponsavel;
	}

	public String getUnidadeorganizacionalCenarioMotivoOperacao() {
		return unidadeorganizacionalCenarioMotivoOperacao;
	}

	public void setUnidadeorganizacionalCenarioMotivoOperacao(String unidadeorganizacionalCenarioMotivoOperacao) {
		this.unidadeorganizacionalCenarioMotivoOperacao = unidadeorganizacionalCenarioMotivoOperacao;
	}

}
