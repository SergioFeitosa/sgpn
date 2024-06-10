package br.com.j4business.saga.formacao.model;

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
@Table(name = "formacao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_formacao"}, name="formacaoNome"))
public class Formacao {

	@Id
	@Column(name = "id_formacao")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long formacaoPK;
	
	@Column(name = "nm_formacao",nullable = false, length = 50)
	private String formacaoNome;
	
	@Column(name = "ds_formacao")
	private String formacaoDescricao;

	@Column(name = "fo_status")
	private String formacaoStatus;

	@Column(name = "fo_motivooperacao")
	private String formacaoMotivoOperacao;
	
	@Column(name = "fo_nivel",nullable = false)
	private int formacaoNivel;

	
	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Formacao() {
		super();
	}

	public long getFormacaoPK() {
		return formacaoPK;
	}

	public void setFormacaoPK(long formacaoPK) {
		this.formacaoPK = formacaoPK;
	}

	public String getFormacaoNome() {
		return formacaoNome;
	}

	public void setFormacaoNome(String formacaoNome) {
		this.formacaoNome = formacaoNome;
	}

	public String getFormacaoDescricao() {
		return formacaoDescricao;
	}

	public void setFormacaoDescricao(String formacaoDescricao) {
		this.formacaoDescricao = formacaoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formacaoNome == null) ? 0 : formacaoNome.hashCode());
		result = prime * result + (int) (formacaoPK ^ (formacaoPK >>> 32));
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
		Formacao other = (Formacao) obj;
		if (formacaoNome == null) {
			if (other.formacaoNome != null)
				return false;
		} else if (!formacaoNome.equals(other.formacaoNome))
			return false;
		if (formacaoPK != other.formacaoPK)
			return false;
		return true;
	}

	public String getFormacaoStatus() {
		return formacaoStatus;
	}

	public void setFormacaoStatus(String formacaoStatus) {
		this.formacaoStatus = formacaoStatus;
	}

	public String getFormacaoMotivoOperacao() {
		return formacaoMotivoOperacao;
	}

	public void setFormacaoMotivoOperacao(String formacaoMotivoOperacao) {
		this.formacaoMotivoOperacao = formacaoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public int getFormacaoNivel() {
		return formacaoNivel;
	}

	public void setFormacaoNivel(int formacaoNivel) {
		this.formacaoNivel = formacaoNivel;
	}

}
