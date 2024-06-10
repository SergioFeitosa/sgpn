package br.com.j4business.saga.contratovideo.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.video.model.Video;

@Entity
@Table(name = "contrato_video", uniqueConstraints=@UniqueConstraint(columnNames={"id_contrato","id_video"}, name="contratoVideoUnique"))

public class ContratoVideo implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_contratovideo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contratoVideoPK;
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;    
	
	@ManyToOne
	@JoinColumn(name="id_video")
	private Video video;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String contratoVideoStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String contratoVideoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ContratoVideo() {
		super();
	}

	public ContratoVideo(Contrato contrato, Video video, int contratoVideoSequencia) {
		super();
		this.contrato = contrato;
		this.video = video;
	}

	public long getContratoVideoPK() {
		return contratoVideoPK;
	}

	public void setContratoVideoPK(long contratoVideoPK) {
		this.contratoVideoPK = contratoVideoPK;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contrato.getContratoNome() == null) ? 0 : contrato.getContratoNome().hashCode());
		result = prime * result + ((video.getVideoNome() == null) ? 0 : video.getVideoNome().hashCode());
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
		ContratoVideo other = (ContratoVideo) obj;
		if (contrato.getContratoNome() == null) {
			if (other.contrato.getContratoNome() != null)
				return false;
		} else if (!contrato.getContratoNome().equals(other.contrato.getContratoNome()))
			return false;
		if (video.getVideoNome() == null) {
			if (other.video.getVideoNome() != null)
				return false;
		} else if (!video.getVideoNome().equals(other.video.getVideoNome()))
			return false;
		return true;
	}

	public String getContratoVideoStatus() {
		return contratoVideoStatus;
	}

	public void setContratoVideoStatus(String contratoVideoStatus) {
		this.contratoVideoStatus = contratoVideoStatus;
	}

	public String getContratoVideoMotivoOperacao() {
		return contratoVideoMotivoOperacao;
	}

	public void setContratoVideoMotivoOperacao(String contratoVideoMotivoOperacao) {
		this.contratoVideoMotivoOperacao = contratoVideoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
