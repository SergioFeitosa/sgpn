package br.com.j4business.saga.eventoprocesso.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.evento.model.Evento;

@Entity
@Table(name = "evento_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_evento","id_processo"}, name="eventoProcessoUnique"))
public class EventoProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_eventoprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long eventoProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_evento")
	private Evento evento;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "er_status",nullable = false, length = 200)
	private String eventoProcessoStatus;

	@Column(name = "er_motivooperacao",nullable = false, length = 200)
	private String eventoProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "ds_eventoprocessoimpacto")
	private String eventoProcessoImpacto;

	public EventoProcesso() {
		super();
	}

	public EventoProcesso(Evento evento, Processo processo) {
		super();
		this.evento = evento;
		this.processo = processo;
	}

	public long getEventoProcessoPK() {
		return eventoProcessoPK;
	}

	public void setEventoProcessoPK(long eventoProcessoPK) {
		this.eventoProcessoPK = eventoProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evento.getEventoNome() == null) ? 0 : evento.getEventoNome().hashCode());
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
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
		EventoProcesso other = (EventoProcesso) obj;
		if (evento.getEventoNome() == null) {
			if (other.evento.getEventoNome() != null)
				return false;
		} else if (!evento.getEventoNome().equals(other.evento.getEventoNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public String getEventoProcessoImpacto() {
		return eventoProcessoImpacto;
	}

	public void setEventoProcessoImpacto(String eventoProcessoImpacto) {
		this.eventoProcessoImpacto = eventoProcessoImpacto;
	}

	public String getEventoProcessoStatus() {
		return eventoProcessoStatus;
	}

	public void setEventoProcessoStatus(String eventoProcessoStatus) {
		this.eventoProcessoStatus = eventoProcessoStatus;
	}

	public String getEventoProcessoMotivoOperacao() {
		return eventoProcessoMotivoOperacao;
	}

	public void setEventoProcessoMotivoOperacao(String eventoProcessoMotivoOperacao) {
		this.eventoProcessoMotivoOperacao = eventoProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
