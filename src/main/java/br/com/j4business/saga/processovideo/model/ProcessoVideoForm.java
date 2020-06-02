package br.com.j4business.saga.processovideo.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ProcessoVideoForm {

	private long processoVideoPK;
	private long videoPK;
	private long processoPK;
	
    @NotEmpty(message = "Nome do video é uma informação obrigatória.")
	@NotNull
	private String videoNome;
	
    @NotEmpty(message = "Nome da Processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  processoVideoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus processoVideoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String processoVideoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String processoVideoMotivoOperacao;

	public ProcessoVideoForm() {
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

	public long getProcessoVideoPK() {
		return processoVideoPK;
	}

	public void setProcessoVideoPK(long processoVideoPK) {
		this.processoVideoPK = processoVideoPK;
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

	public String getProcessoVideoDataCriacao() {
		return processoVideoDataCriacao;
	}

	public void setProcessoVideoDataCriacao(String processoVideoDataCriacao) {
		this.processoVideoDataCriacao = processoVideoDataCriacao;
	}

	public AtributoStatus getProcessoVideoStatus() {
		return processoVideoStatus;
	}

	public void setProcessoVideoStatus(AtributoStatus processoVideoStatus) {
		this.processoVideoStatus = processoVideoStatus;
	}

	public String getProcessoVideoResponsavel() {
		return processoVideoResponsavel;
	}

	public void setProcessoVideoResponsavel(String processoVideoResponsavel) {
		this.processoVideoResponsavel = processoVideoResponsavel;
	}

	public String getProcessoVideoMotivoOperacao() {
		return processoVideoMotivoOperacao;
	}

	public void setProcessoVideoMotivoOperacao(String processoVideoMotivoOperacao) {
		this.processoVideoMotivoOperacao = processoVideoMotivoOperacao;
	}

}
