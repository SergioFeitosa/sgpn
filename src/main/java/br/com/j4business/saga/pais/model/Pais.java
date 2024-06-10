package br.com.j4business.saga.pais.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.estado.model.Estado;

@Entity
@Table(name = "pais", uniqueConstraints=@UniqueConstraint(columnNames={"nm_pais"}, name="paisNome"))
public class Pais {

	@Id
	@Column(name = "id_pais")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long paisPK;
	
	@Column(name = "cd_pais",nullable = false, length = 5)
	private String paisCodigo;
	
	@Column(name = "sg_pais",nullable = false, length = 2)
	private String paisSigla;

	@Column(name = "nm_pais",nullable = false, length = 300)
	private String paisNome;

	@Column(name = "pa_status")
	private String paisStatus;

	@Column(name = "pa_motivooperacao")
	private String paisMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	
	@OneToMany(mappedBy="pais")
	private List<Estado> estados;
	
	public Pais() {
		super();
	}

	public long getPaisPK() {
		return paisPK;
	}

	public void setPaisPK(long paisPK) {
		this.paisPK = paisPK;
	}

	public String getPaisNome() {
		return paisNome;
	}

	public void setPaisNome(String paisNome) {
		this.paisNome = paisNome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paisNome == null) ? 0 : paisNome.hashCode());
		result = prime * result + (int) (paisPK ^ (paisPK >>> 32));
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
		Pais other = (Pais) obj;
		if (paisNome == null) {
			if (other.paisNome != null)
				return false;
		} else if (!paisNome.equals(other.paisNome))
			return false;
		if (paisPK != other.paisPK)
			return false;
		return true;
	}

	public String getPaisCodigo() {
		return paisCodigo;
	}

	public void setPaisCodigo(String paisCodigo) {
		this.paisCodigo = paisCodigo;
	}

	public String getPaisSigla() {
		return paisSigla;
	}

	public void setPaisSigla(String paisSigla) {
		this.paisSigla = paisSigla;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public String getPaisStatus() {
		return paisStatus;
	}

	public void setPaisStatus(String paisStatus) {
		this.paisStatus = paisStatus;
	}

	public String getPaisMotivoOperacao() {
		return paisMotivoOperacao;
	}

	public void setPaisMotivoOperacao(String paisMotivoOperacao) {
		this.paisMotivoOperacao = paisMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
