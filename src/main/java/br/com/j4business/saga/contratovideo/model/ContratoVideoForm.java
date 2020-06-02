package br.com.j4business.saga.contratovideo.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ContratoVideoForm {

	private long contratoVideoPK;
	private long videoPK;
	private long contratoPK;
	
    @NotEmpty(message = "Nome do video é uma informação obrigatória.")
	@NotNull
	private String videoNome;
	
    @NotEmpty(message = "Nome da Contrato é uma informação obrigatória.")
	@NotNull
	private String contratoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  contratoVideoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus contratoVideoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String contratoVideoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String contratoVideoMotivoOperacao;

	public ContratoVideoForm() {
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

	public long getContratoVideoPK() {
		return contratoVideoPK;
	}

	public void setContratoVideoPK(long contratoVideoPK) {
		this.contratoVideoPK = contratoVideoPK;
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

	public String getContratoVideoDataCriacao() {
		return contratoVideoDataCriacao;
	}

	public void setContratoVideoDataCriacao(String contratoVideoDataCriacao) {
		this.contratoVideoDataCriacao = contratoVideoDataCriacao;
	}

	public AtributoStatus getContratoVideoStatus() {
		return contratoVideoStatus;
	}

	public void setContratoVideoStatus(AtributoStatus contratoVideoStatus) {
		this.contratoVideoStatus = contratoVideoStatus;
	}

	public String getContratoVideoResponsavel() {
		return contratoVideoResponsavel;
	}

	public void setContratoVideoResponsavel(String contratoVideoResponsavel) {
		this.contratoVideoResponsavel = contratoVideoResponsavel;
	}

	public String getContratoVideoMotivoOperacao() {
		return contratoVideoMotivoOperacao;
	}

	public void setContratoVideoMotivoOperacao(String contratoVideoMotivoOperacao) {
		this.contratoVideoMotivoOperacao = contratoVideoMotivoOperacao;
	}

}
