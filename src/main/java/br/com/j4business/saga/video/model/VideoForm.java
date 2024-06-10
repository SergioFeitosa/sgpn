package br.com.j4business.saga.video.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class VideoForm {

	private long videoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String videoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String videoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus videoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String videoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String videoMotivoOperacao;

	@Size(min = 5,max = 200, message = "URL do vídeo não pode ter menos que 5 e mais que 200 caracteres")
	private String videoURL;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 3,max = 10, message = "Nome do botão do vídeo não pode ter menos que 3 e mais que 10 caracteres")
	private String videoNomeBotao;

    
	public VideoForm() {
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

	public String getVideoDescricao() {
		return videoDescricao;
	}

	public void setVideoDescricao(String videoDescricao) {
		this.videoDescricao = videoDescricao;
	}

	public AtributoStatus getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(AtributoStatus videoStatus) {
		this.videoStatus = videoStatus;
	}

	public String getVideoResponsavel() {
		return videoResponsavel;
	}

	public void setVideoResponsavel(String videoResponsavel) {
		this.videoResponsavel = videoResponsavel;
	}

	public String getVideoMotivoOperacao() {
		return videoMotivoOperacao;
	}

	public void setVideoMotivoOperacao(String videoMotivoOperacao) {
		this.videoMotivoOperacao = videoMotivoOperacao;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getVideoNomeBotao() {
		return videoNomeBotao;
	}

	public void setVideoNomeBotao(String videoNomeBotao) {
		this.videoNomeBotao = videoNomeBotao;
	}

	
}
