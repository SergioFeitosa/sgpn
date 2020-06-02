package br.com.j4business.saga.texto.model;

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
@Table(name = "texto", uniqueConstraints=@UniqueConstraint(columnNames={"nm_texto"}, name="textoNome"))
public class Texto {

	@Id
	@Column(name = "id_texto")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long textoPK;
	
	@Column(name = "nm_texto",nullable = false, length = 50)
	private String textoNome;
	
	@Column(name = "ds_texto")
	private String textoDescricao;

	@Column(name = "po_status")
	private String textoStatus;

	@Column(name = "po_motivooperacao")
	private String textoMotivoOperacao;

	@Column(name = "po_urltexto")
	private String textoURL;

	@Column(name = "po_nomeBotao")
	private String textoNomeBotao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Texto() {
		super();
	}

	public long getTextoPK() {
		return textoPK;
	}

	public void setTextoPK(long textoPK) {
		this.textoPK = textoPK;
	}

	public String getTextoNome() {
		return textoNome;
	}

	public void setTextoNome(String textoNome) {
		this.textoNome = textoNome;
	}

	public String getTextoDescricao() {
		return textoDescricao;
	}

	public void setTextoDescricao(String textoDescricao) {
		this.textoDescricao = textoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((textoNome == null) ? 0 : textoNome.hashCode());
		result = prime * result + (int) (textoPK ^ (textoPK >>> 32));
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
		Texto other = (Texto) obj;
		if (textoNome == null) {
			if (other.textoNome != null)
				return false;
		} else if (!textoNome.equals(other.textoNome))
			return false;
		if (textoPK != other.textoPK)
			return false;
		return true;
	}

	public String getTextoStatus() {
		return textoStatus;
	}

	public void setTextoStatus(String textoStatus) {
		this.textoStatus = textoStatus;
	}

	public String getTextoMotivoOperacao() {
		return textoMotivoOperacao;
	}

	public void setTextoMotivoOperacao(String textoMotivoOperacao) {
		this.textoMotivoOperacao = textoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getTextoURL() {
		return textoURL;
	}

	public void setTextoURL(String textoURL) {
		this.textoURL = textoURL;
	}

	public String getTextoNomeBotao() {
		return textoNomeBotao;
	}

	public void setTextoNomeBotao(String textoNomeBotao) {
		this.textoNomeBotao = textoNomeBotao;
	}

}
