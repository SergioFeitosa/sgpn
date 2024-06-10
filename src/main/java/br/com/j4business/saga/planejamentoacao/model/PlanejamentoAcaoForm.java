package br.com.j4business.saga.planejamentoacao.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class PlanejamentoAcaoForm {

	private long planejamentoAcaoPK;
	private long planejamentoPK;
	private long acaoPK;
	
    @NotBlank(message = "Nome do planejamento é uma informação obrigatória.")
	@NotNull
	private String planejamentoNome;
	
    @NotBlank(message = "Nome da Acao é uma informação obrigatória.")
	@NotNull
	private String acaoNome;
	
    @NotBlank(message = "Sequência é uma informação obrigatória.")
	private String planejamentoAcaoSequencia;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoAcaoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus planejamentoAcaoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String planejamentoAcaoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String planejamentoAcaoMotivoOperacao;

	public PlanejamentoAcaoForm() {
		super();
	}

	public long getPlanejamentoPK() {
		return planejamentoPK;
	}

	public void setPlanejamentoPK(long planejamentoPK) {
		this.planejamentoPK = planejamentoPK;
	}

	public String getPlanejamentoNome() {
		return planejamentoNome;
	}

	public void setPlanejamentoNome(String planejamentoNome) {
		this.planejamentoNome = planejamentoNome;
	}

	public long getPlanejamentoAcaoPK() {
		return planejamentoAcaoPK;
	}

	public void setPlanejamentoAcaoPK(long planejamentoAcaoPK) {
		this.planejamentoAcaoPK = planejamentoAcaoPK;
	}

	public long getAcaoPK() {
		return acaoPK;
	}

	public void setAcaoPK(long acaoPK) {
		this.acaoPK = acaoPK;
	}

	public String getAcaoNome() {
		return acaoNome;
	}

	public void setAcaoNome(String acaoNome) {
		this.acaoNome = acaoNome;
	}

	public String getPlanejamentoAcaoSequencia() {
		return planejamentoAcaoSequencia;
	}

	public void setPlanejamentoAcaoSequencia(String planejamentoAcaoSequencia) {
		this.planejamentoAcaoSequencia = planejamentoAcaoSequencia;
	}

	public String getPlanejamentoAcaoDataCriacao() {
		return planejamentoAcaoDataCriacao;
	}

	public void setPlanejamentoAcaoDataCriacao(String planejamentoAcaoDataCriacao) {
		this.planejamentoAcaoDataCriacao = planejamentoAcaoDataCriacao;
	}

	public AtributoStatus getPlanejamentoAcaoStatus() {
		return planejamentoAcaoStatus;
	}

	public void setPlanejamentoAcaoStatus(AtributoStatus planejamentoAcaoStatus) {
		this.planejamentoAcaoStatus = planejamentoAcaoStatus;
	}

	public String getPlanejamentoAcaoResponsavel() {
		return planejamentoAcaoResponsavel;
	}

	public void setPlanejamentoAcaoResponsavel(String planejamentoAcaoResponsavel) {
		this.planejamentoAcaoResponsavel = planejamentoAcaoResponsavel;
	}

	public String getPlanejamentoAcaoMotivoOperacao() {
		return planejamentoAcaoMotivoOperacao;
	}

	public void setPlanejamentoAcaoMotivoOperacao(String planejamentoAcaoMotivoOperacao) {
		this.planejamentoAcaoMotivoOperacao = planejamentoAcaoMotivoOperacao;
	}

	
}
