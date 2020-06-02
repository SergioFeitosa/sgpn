package br.com.j4business.saga.servico.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;

@Entity
@Table(name = "servico", uniqueConstraints=@UniqueConstraint(columnNames={"nm_servico"}, name="servicoNome"))
public class Servico {

	@Id
	@Column(name = "id_servico")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long servicoPK;
	
	@Column(name = "nm_servico",nullable = false, length = 50)
	private String servicoNome;
	
	@Column(name = "ds_servico")
	private String servicoDescricao;

	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	@Column(name = "se_status")
	private String servicoStatus;

	@Column(name = "se_motivooperacao")
	private String servicoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	public Servico() {
		super();
	}

	public long getServicoPK() {
		return servicoPK;
	}

	public void setServicoPK(long servicoPK) {
		this.servicoPK = servicoPK;
	}

	public String getServicoNome() {
		return servicoNome;
	}

	public void setServicoNome(String servicoNome) {
		this.servicoNome = servicoNome;
	}

	public String getServicoDescricao() {
		return servicoDescricao;
	}

	public void setServicoDescricao(String servicoDescricao) {
		this.servicoDescricao = servicoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((servicoNome == null) ? 0 : servicoNome.hashCode());
		result = prime * result + (int) (servicoPK ^ (servicoPK >>> 32));
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
		Servico other = (Servico) obj;
		if (servicoNome == null) {
			if (other.servicoNome != null)
				return false;
		} else if (!servicoNome.equals(other.servicoNome))
			return false;
		if (servicoPK != other.servicoPK)
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

	public String getServicoStatus() {
		return servicoStatus;
	}

	public void setServicoStatus(String servicoStatus) {
		this.servicoStatus = servicoStatus;
	}

	public String getServicoMotivoOperacao() {
		return servicoMotivoOperacao;
	}

	public void setServicoMotivoOperacao(String servicoMotivoOperacao) {
		this.servicoMotivoOperacao = servicoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
