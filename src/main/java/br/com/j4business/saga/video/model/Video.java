package br.com.j4business.saga.video.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "video", uniqueConstraints=@UniqueConstraint(columnNames={"nm_video"}, name="videoNome"))
public class Video {

	@Id
	@Column(name = "id_video")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long videoPK;
	
	@Column(name = "nm_video",nullable = false, length = 50)
	private String videoNome;
	
	@Column(name = "ds_video")
	private String videoDescricao;

	@Column(name = "vi_status")
	private String videoStatus;

	@Column(name = "vi_motivooperacao")
	private String videoMotivoOperacao;

	@Column(name = "vi_urlvideo")
	private String videoURL;

	@Column(name = "vi_nomeBotao")
	private String videoNomeBotao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Video() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((videoNome == null) ? 0 : videoNome.hashCode());
		result = prime * result + (int) (videoPK ^ (videoPK >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (videoNome == null) {
			if (other.videoNome != null)
				return false;
		} else if (!videoNome.equals(other.videoNome))
			return false;
		if (videoPK != other.videoPK)
			return false;
		return true;
	}

	public String getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(String videoStatus) {
		this.videoStatus = videoStatus;
	}

	public String getVideoMotivoOperacao() {
		return videoMotivoOperacao;
	}

	public void setVideoMotivoOperacao(String videoMotivoOperacao) {
		this.videoMotivoOperacao = videoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
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
