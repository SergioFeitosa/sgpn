package br.com.j4business.saga.treinamentovideo.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class TreinamentoVideoForm {

	private long treinamentoVideoPK;
	private long videoPK;
	private long treinamentoPK;
	
    @NotBlank(message = "Nome do video é uma informação obrigatória.")
	@NotNull
	private String videoNome;
	
    @NotBlank(message = "Nome da Treinamento é uma informação obrigatória.")
	@NotNull
	private String treinamentoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoVideoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus treinamentoVideoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String treinamentoVideoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String treinamentoVideoMotivoOperacao;

	public TreinamentoVideoForm() {
		super();
	}

	public long getVideoPK() {
		return videoPK;
	}

	public void setVideoPK(long videoPK) {
		this.videoPK = videoPK;
	}

	public String getVideoNome() {
		return videoNome;
	}

	public void setVideoNome(String videoNome) {
		this.videoNome = videoNome;
	}

	public long getTreinamentoVideoPK() {
		return treinamentoVideoPK;
	}

	public void setTreinamentoVideoPK(long treinamentoVideoPK) {
		this.treinamentoVideoPK = treinamentoVideoPK;
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

	public String getTreinamentoVideoDataCriacao() {
		return treinamentoVideoDataCriacao;
	}

	public void setTreinamentoVideoDataCriacao(String treinamentoVideoDataCriacao) {
		this.treinamentoVideoDataCriacao = treinamentoVideoDataCriacao;
	}

	public AtributoStatus getTreinamentoVideoStatus() {
		return treinamentoVideoStatus;
	}

	public void setTreinamentoVideoStatus(AtributoStatus treinamentoVideoStatus) {
		this.treinamentoVideoStatus = treinamentoVideoStatus;
	}

	public String getTreinamentoVideoResponsavel() {
		return treinamentoVideoResponsavel;
	}

	public void setTreinamentoVideoResponsavel(String treinamentoVideoResponsavel) {
		this.treinamentoVideoResponsavel = treinamentoVideoResponsavel;
	}

	public String getTreinamentoVideoMotivoOperacao() {
		return treinamentoVideoMotivoOperacao;
	}

	public void setTreinamentoVideoMotivoOperacao(String treinamentoVideoMotivoOperacao) {
		this.treinamentoVideoMotivoOperacao = treinamentoVideoMotivoOperacao;
	}

}
