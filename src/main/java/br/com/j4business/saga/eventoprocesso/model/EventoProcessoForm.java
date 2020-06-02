package br.com.j4business.saga.eventoprocesso.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.eventoprocesso.enumeration.EventoProcessoImpacto;

public class EventoProcessoForm {

	private long eventoProcessoPK;
	private long processoPK;
	private long eventoPK;
	
    @NotEmpty(message = "Nome do processo é uma informação obrigatória.")
	@NotNull
	private String processoNome;
	
    @NotEmpty(message = "Nome da Evento é uma informação obrigatória.")
	@NotNull
	private String eventoNome;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  eventoProcessoDataCriacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AtributoStatus eventoProcessoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull
	private String eventoProcessoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String eventoProcessoMotivoOperacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EventoProcessoImpacto eventoProcessoImpacto;


	public EventoProcessoForm() {
		super();
	}

	public long getProcessoPK() {
		return processoPK;
	}

	public void setProcessoPK(long processoPK) {
		this.processoPK = processoPK;
	}

	public String getProcessoNome() {
		return processoNome;
	}

	public void setProcessoNome(String processoNome) {
		this.processoNome = processoNome;
	}

	public long getEventoProcessoPK() {
		return eventoProcessoPK;
	}

	public void setEventoProcessoPK(long eventoProcessoPK) {
		this.eventoProcessoPK = eventoProcessoPK;
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

	public String getEventoProcessoDataCriacao() {
		return eventoProcessoDataCriacao;
	}

	public void setEventoProcessoDataCriacao(String eventoProcessoDataCriacao) {
		this.eventoProcessoDataCriacao = eventoProcessoDataCriacao;
	}

	public AtributoStatus getEventoProcessoStatus() {
		return eventoProcessoStatus;
	}

	public void setEventoProcessoStatus(AtributoStatus eventoProcessoStatus) {
		this.eventoProcessoStatus = eventoProcessoStatus;
	}

	public String getEventoProcessoResponsavel() {
		return eventoProcessoResponsavel;
	}

	public void setEventoProcessoResponsavel(String eventoProcessoResponsavel) {
		this.eventoProcessoResponsavel = eventoProcessoResponsavel;
	}

	public String getEventoProcessoMotivoOperacao() {
		return eventoProcessoMotivoOperacao;
	}

	public void setEventoProcessoMotivoOperacao(String eventoProcessoMotivoOperacao) {
		this.eventoProcessoMotivoOperacao = eventoProcessoMotivoOperacao;
	}

	public EventoProcessoImpacto getEventoProcessoImpacto() {
		return eventoProcessoImpacto;
	}

	public void setEventoProcessoImpacto(EventoProcessoImpacto eventoProcessoImpacto) {
		this.eventoProcessoImpacto = eventoProcessoImpacto;
	}

}
