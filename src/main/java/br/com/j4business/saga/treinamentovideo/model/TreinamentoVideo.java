package br.com.j4business.saga.treinamentovideo.model;

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

import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.video.model.Video;

@Entity
@Table(name = "treinamento_video", uniqueConstraints=@UniqueConstraint(columnNames={"id_treinamento","id_video"}, name="treinamentoVideoUnique"))

public class TreinamentoVideo implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_treinamentovideo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long treinamentoVideoPK;
	
	@ManyToOne
	@JoinColumn(name="id_treinamento")
	private Treinamento treinamento;    
	
	@ManyToOne
	@JoinColumn(name="id_video")
	private Video video;
	    
	@Column(name = "ph_status",nullable = false, length = 200)
	private String treinamentoVideoStatus;

	@Column(name = "ph_motivooperacao",nullable = false, length = 200)
	private String treinamentoVideoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public TreinamentoVideo() {
		super();
	}

	public TreinamentoVideo(Treinamento treinamento, Video video, int treinamentoVideoSequencia) {
		super();
		this.treinamento = treinamento;
		this.video = video;
	}

	public long getTreinamentoVideoPK() {
		return treinamentoVideoPK;
	}

	public void setTreinamentoVideoPK(long treinamentoVideoPK) {
		this.treinamentoVideoPK = treinamentoVideoPK;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Treinamento getTreinamento() {
		return treinamento;
	}

	public void setTreinamento(Treinamento treinamento) {
		this.treinamento = treinamento;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((treinamento.getTreinamentoNome() == null) ? 0 : treinamento.getTreinamentoNome().hashCode());
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
		TreinamentoVideo other = (TreinamentoVideo) obj;
		if (treinamento.getTreinamentoNome() == null) {
			if (other.treinamento.getTreinamentoNome() != null)
				return false;
		} else if (!treinamento.getTreinamentoNome().equals(other.treinamento.getTreinamentoNome()))
			return false;
		if (video.getVideoNome() == null) {
			if (other.video.getVideoNome() != null)
				return false;
		} else if (!video.getVideoNome().equals(other.video.getVideoNome()))
			return false;
		return true;
	}

	public String getTreinamentoVideoStatus() {
		return treinamentoVideoStatus;
	}

	public void setTreinamentoVideoStatus(String treinamentoVideoStatus) {
		this.treinamentoVideoStatus = treinamentoVideoStatus;
	}

	public String getTreinamentoVideoMotivoOperacao() {
		return treinamentoVideoMotivoOperacao;
	}

	public void setTreinamentoVideoMotivoOperacao(String treinamentoVideoMotivoOperacao) {
		this.treinamentoVideoMotivoOperacao = treinamentoVideoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
