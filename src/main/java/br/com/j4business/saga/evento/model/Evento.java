package br.com.j4business.saga.evento.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;

@Entity
@Table(name = "evento", uniqueConstraints=@UniqueConstraint(columnNames={"nm_evento"}, name="eventoNome"))
public class Evento {

	@Id
	@Column(name = "id_evento")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long eventoPK;
	
	@Column(name = "nm_evento",nullable = false, length = 100)
	private String eventoNome;
	
	@Column(name = "ds_evento")
	private String eventoDescricao;

	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventoProcesso> eventoProcessos = new HashSet<EventoProcesso>();
	
	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ds_eventoprioridade")
	private String eventoPrioridade;

	@Column(name = "dt_eventodatainicio")
    private String  eventoDataInicio;

	@Column(name = "dt_eventodatatermino")
    private String  eventoDataTermino;

	@Column(name = "ds_eventofonte",nullable = false, length = 100)
	private String eventoFonte;

	@Column(name = "ev_status")
	private String eventoStatus;

	@Column(name = "ev_motivooperacao")
	private String eventoMotivoOperacao;

	
	public Evento() {
		super();
	}

	public long getEventoPK() {
		return eventoPK;
	}

	public void setEventoPK(long eventoPK) {
		this.eventoPK = eventoPK;
	}

	public String getEventoNome() {
		return eventoNome;
	}

	public void setEventoNome(String eventoNome) {
		this.eventoNome = eventoNome;
	}

	public String getEventoDescricao() {
		return eventoDescricao;
	}

	public void setEventoDescricao(String eventoDescricao) {
		this.eventoDescricao = eventoDescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventoNome == null) ? 0 : eventoNome.hashCode());
		result = prime * result + (int) (eventoPK ^ (eventoPK >>> 32));
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
		Evento other = (Evento) obj;
		if (eventoNome == null) {
			if (other.eventoNome != null)
				return false;
		} else if (!eventoNome.equals(other.eventoNome))
			return false;
		if (eventoPK != other.eventoPK)
			return false;
		return true;
	}

	public Set<EventoProcesso> getEventoProcessos() {
		return eventoProcessos;
	}

	public void setEventoProcessos(Set<EventoProcesso> eventoProcessos) {
		this.eventoProcessos = eventoProcessos;
	}


	public String getEventoDataInicio() {
		return eventoDataInicio;
	}

	public void setEventoDataInicio(String eventoDataInicio) {
		this.eventoDataInicio = eventoDataInicio;
	}

	public String getEventoDataTermino() {
		return eventoDataTermino;
	}

	public void setEventoDataTermino(String eventoDataTermino) {
		this.eventoDataTermino = eventoDataTermino;
	}

	public String getEventoFonte() {
		return eventoFonte;
	}

	public void setEventoFonte(String eventoFonte) {
		this.eventoFonte = eventoFonte;
	}

	public String getEventoPrioridade() {
		return eventoPrioridade;
	}

	public void setEventoPrioridade(String eventoPrioridade) {
		this.eventoPrioridade = eventoPrioridade;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getEventoStatus() {
		return eventoStatus;
	}

	public void setEventoStatus(String eventoStatus) {
		this.eventoStatus = eventoStatus;
	}

	public String getEventoMotivoOperacao() {
		return eventoMotivoOperacao;
	}

	public void setEventoMotivoOperacao(String eventoMotivoOperacao) {
		this.eventoMotivoOperacao = eventoMotivoOperacao;
	}

	
}
