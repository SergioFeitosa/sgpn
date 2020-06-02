package br.com.j4business.saga.contrato.model;

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
@Table(name = "contrato", uniqueConstraints=@UniqueConstraint(columnNames={"nm_contrato"}, name="contratoNome"))
public class Contrato {

	@Id
	@Column(name = "id_contrato")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contratoPK;
	
	@Column(name = "nm_contrato",nullable = false, length = 50)
	private String contratoNome;
	
	@Column(name = "ds_contrato")
	private String contratoDescricao;

	@Column(name = "ds_contratoduracao")
	private String contratoDuracao;

	@Column(name = "dt_contratodatainicio")
    private String  contratoDataInicio;

	@Column(name = "dt_contratodatatermino")
    private String  contratoDataTermino;

	@Column(name = "cn_status")
	private String contratoStatus;

	@Column(name = "cn_motivooperacao")
	private String contratoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	public Contrato() {
		super();
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

	public String getContratoDescricao() {
		return contratoDescricao;
	}

	public void setContratoDescricao(String contratoDescricao) {
		this.contratoDescricao = contratoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contratoNome == null) ? 0 : contratoNome.hashCode());
		result = prime * result + (int) (contratoPK ^ (contratoPK >>> 32));
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
		Contrato other = (Contrato) obj;
		if (contratoNome == null) {
			if (other.contratoNome != null)
				return false;
		} else if (!contratoNome.equals(other.contratoNome))
			return false;
		if (contratoPK != other.contratoPK)
			return false;
		return true;
	}

	public String getContratoDuracao() {
		return contratoDuracao;
	}

	public void setContratoDuracao(String contratoDuracao) {
		this.contratoDuracao = contratoDuracao;
	}

	public String getContratoDataInicio() {
		return contratoDataInicio;
	}

	public void setContratoDataInicio(String contratoDataInicio) {
		this.contratoDataInicio = contratoDataInicio;
	}

	public String getContratoDataTermino() {
		return contratoDataTermino;
	}

	public void setContratoDataTermino(String contratoDataTermino) {
		this.contratoDataTermino = contratoDataTermino;
	}

	public String getContratoStatus() {
		return contratoStatus;
	}

	public void setContratoStatus(String contratoStatus) {
		this.contratoStatus = contratoStatus;
	}

	public String getContratoMotivoOperacao() {
		return contratoMotivoOperacao;
	}

	public void setContratoMotivoOperacao(String contratoMotivoOperacao) {
		this.contratoMotivoOperacao = contratoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
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
	
}
