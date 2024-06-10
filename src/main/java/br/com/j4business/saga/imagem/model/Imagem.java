package br.com.j4business.saga.imagem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;

@Entity
@Table(name = "imagem", uniqueConstraints=@UniqueConstraint(columnNames={"nm_imagem"}, name="imagemNome"))
public class Imagem {

	@Id
	@Column(name = "id_imagem")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long imagemPK;
	
	@Column(name = "nm_imagem",nullable = false, length = 50)
	private String imagemNome;
	
	@Column(name = "ds_imagem")
	private String imagemDescricao;

	@Column(name = "po_status")
	private String imagemStatus;

	@Column(name = "po_motivooperacao")
	private String imagemMotivoOperacao;

	@Column(name = "po_urlimagem")
	private String imagemURL;

	@Column(name = "po_nomeBotao")
	private String imagemNomeBotao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Imagem() {
		super();
	}

	public long getImagemPK() {
		return imagemPK;
	}

	public void setImagemPK(long imagemPK) {
		this.imagemPK = imagemPK;
	}

	public String getImagemNome() {
		return imagemNome;
	}

	public void setImagemNome(String imagemNome) {
		this.imagemNome = imagemNome;
	}

	public String getImagemDescricao() {
		return imagemDescricao;
	}

	public void setImagemDescricao(String imagemDescricao) {
		this.imagemDescricao = imagemDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagemNome == null) ? 0 : imagemNome.hashCode());
		result = prime * result + (int) (imagemPK ^ (imagemPK >>> 32));
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
		Imagem other = (Imagem) obj;
		if (imagemNome == null) {
			if (other.imagemNome != null)
				return false;
		} else if (!imagemNome.equals(other.imagemNome))
			return false;
		if (imagemPK != other.imagemPK)
			return false;
		return true;
	}

	public String getImagemStatus() {
		return imagemStatus;
	}

	public void setImagemStatus(String imagemStatus) {
		this.imagemStatus = imagemStatus;
	}

	public String getImagemMotivoOperacao() {
		return imagemMotivoOperacao;
	}

	public void setImagemMotivoOperacao(String imagemMotivoOperacao) {
		this.imagemMotivoOperacao = imagemMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getImagemURL() {
		return imagemURL;
	}

	public void setImagemURL(String imagemURL) {
		this.imagemURL = imagemURL;
	}

	public String getImagemNomeBotao() {
		return imagemNomeBotao;
	}

	public void setImagemNomeBotao(String imagemNomeBotao) {
		this.imagemNomeBotao = imagemNomeBotao;
	}

}
