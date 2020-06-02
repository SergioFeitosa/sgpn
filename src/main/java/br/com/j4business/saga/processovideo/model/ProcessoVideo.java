package br.com.j4business.saga.processovideo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.video.model.Video;

@Entity
@Table(name = "processo_video", uniqueConstraints=@UniqueConstraint(columnNames={"id_processo","id_video"}, name="processoVideoUnique"))

public class ProcessoVideo implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_processovideo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long processoVideoPK;
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;    
	
	@ManyToOne
	@JoinColumn(name="id_video")
	private Video video;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String processoVideoStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String processoVideoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ProcessoVideo() {
		super();
	}

	public ProcessoVideo(Processo processo, Video video, int processoVideoSequencia) {
		super();
		this.processo = processo;
		this.video = video;
	}

	public long getProcessoVideoPK() {
		return processoVideoPK;
	}

	public void setProcessoVideoPK(long processoVideoPK) {
		this.processoVideoPK = processoVideoPK;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
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
		ProcessoVideo other = (ProcessoVideo) obj;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		if (video.getVideoNome() == null) {
			if (other.video.getVideoNome() != null)
				return false;
		} else if (!video.getVideoNome().equals(other.video.getVideoNome()))
			return false;
		return true;
	}

	public String getProcessoVideoStatus() {
		return processoVideoStatus;
	}

	public void setProcessoVideoStatus(String processoVideoStatus) {
		this.processoVideoStatus = processoVideoStatus;
	}

	public String getProcessoVideoMotivoOperacao() {
		return processoVideoMotivoOperacao;
	}

	public void setProcessoVideoMotivoOperacao(String processoVideoMotivoOperacao) {
		this.processoVideoMotivoOperacao = processoVideoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
