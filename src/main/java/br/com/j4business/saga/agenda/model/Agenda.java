package br.com.j4business.saga.agenda.model;



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
@Table(name = "agenda", uniqueConstraints=@UniqueConstraint(columnNames={"nm_agenda"}, name="agendaNome"))
public class Agenda {

	@Id
	@Column(name = "id_agenda")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long agendaPK;
	
	@Column(name = "nm_agenda",nullable = false, length = 50)
	private String agendaNome;
	
	@Column(name = "ds_agenda")
	private String agendaDescricao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ag_status")
	private String agendaStatus;

	@Column(name = "ag_motivooperacao")
	private String agendaMotivoOperacao;
	
	public Agenda() {
		super();
	}

	public long getAgendaPK() {
		return agendaPK;
	}

	public void setAgendaPK(long agendaPK) {
		this.agendaPK = agendaPK;
	}

	public String getAgendaNome() {
		return agendaNome;
	}

	public void setAgendaNome(String agendaNome) {
		this.agendaNome = agendaNome;
	}

	public String getAgendaDescricao() {
		return agendaDescricao;
	}

	public void setAgendaDescricao(String agendaDescricao) {
		this.agendaDescricao = agendaDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agendaNome == null) ? 0 : agendaNome.hashCode());
		result = prime * result + (int) (agendaPK ^ (agendaPK >>> 32));
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
		Agenda other = (Agenda) obj;
		if (agendaNome == null) {
			if (other.agendaNome != null)
				return false;
		} else if (!agendaNome.equals(other.agendaNome))
			return false;
		if (agendaPK != other.agendaPK)
			return false;
		return true;
	}

	public String getAgendaStatus() {
		return agendaStatus;
	}

	public void setAgendaStatus(String agendaStatus) {
		this.agendaStatus = agendaStatus;
	}

	public String getAgendaMotivoOperacao() {
		return agendaMotivoOperacao;
	}

	public void setAgendaMotivoOperacao(String agendaMotivoOperacao) {
		this.agendaMotivoOperacao = agendaMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
