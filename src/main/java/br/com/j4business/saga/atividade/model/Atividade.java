package br.com.j4business.saga.atividade.model;

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
@Table(name = "atividade", uniqueConstraints=@UniqueConstraint(columnNames={"nm_atividade"}, name="atividadeNome"))

public class Atividade {

	@Id
	@Column(name = "id_atividade")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long atividadePK;
	
	@Column(name = "nm_atividade",nullable = false, length = 100)
	private String atividadeNome;
	
	@Column(name = "ds_atividade",nullable = false, length = 300)
	private String atividadeDescricao;

	@Column(name = "at_motivooperacao",nullable = false, length = 300)
	private String atividadeMotivoOperacao;

	@Column(name = "at_duracao",nullable = false)
	private int atividadeDuracao;

	@Column(name = "at_status",nullable = false, length = 300)
	private String atividadeStatus;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	public Atividade() {
		super();
	}

	public long getAtividadePK() {
		return atividadePK;
	}

	public void setAtividadePK(long atividadePK) {
		this.atividadePK = atividadePK;
	}

	public String getAtividadeNome() {
		return atividadeNome;
	}

	public void setAtividadeNome(String atividadeNome) {
		this.atividadeNome = atividadeNome;
	}

	public String getAtividadeDescricao() {
		return atividadeDescricao;
	}

	public void setAtividadeDescricao(String atividadeDescricao) {
		this.atividadeDescricao = atividadeDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atividadeNome == null) ? 0 : atividadeNome.hashCode());
		result = prime * result + (int) (atividadePK ^ (atividadePK >>> 32));
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
		Atividade other = (Atividade) obj;
		if (atividadeNome == null) {
			if (other.atividadeNome != null)
				return false;
		} else if (!atividadeNome.equals(other.atividadeNome))
			return false;
		if (atividadePK != other.atividadePK)
			return false;
		return true;
	}

	public Colaborador getColaboradorGestor() {
		return colaboradorGestor;
	}

	public void setColaboradorGestor(Colaborador colaboradorGestor) {
		this.colaboradorGestor = colaboradorGestor;
	}

	public Colaborador getColaboradorDono() {
		return colaboradorDono;
	}

	public void setColaboradorDono(Colaborador colaboradorDono) {
		this.colaboradorDono = colaboradorDono;
	}

	public String getAtividadeMotivoOperacao() {
		return atividadeMotivoOperacao;
	}

	public void setAtividadeMotivoOperacao(String atividadeMotivoOperacao) {
		this.atividadeMotivoOperacao = atividadeMotivoOperacao;
	}

	public String getAtividadeStatus() {
		return atividadeStatus;
	}

	public void setAtividadeStatus(String atividadeStatus) {
		this.atividadeStatus = atividadeStatus;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public int getAtividadeDuracao() {
		return atividadeDuracao;
	}

	public void setAtividadeDuracao(int atividadeDuracao) {
		this.atividadeDuracao = atividadeDuracao;
	}

	
}
