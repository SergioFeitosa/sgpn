package br.com.j4business.saga.evento.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class EventoForm {

	private long eventoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String eventoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String eventoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus eventoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String eventoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String eventoMotivoOperacao;

	@NotNull(message = "Prioridade é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoPrioridade eventoPrioridade;

	@NotNull(message = "Data de Início é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  eventoDataInicio;

	@NotNull(message = "Data de término é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  eventoDataTermino;

    @NotEmpty(message = "Fonte de origem do evento é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Fonte de origem do evento não pode ter menos que 5 e mais que 200 caracteres")
	private String eventoFonte;


    
	public EventoForm() {
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

	public AtributoStatus getEventoStatus() {
		return eventoStatus;
	}

	public void setEventoStatus(AtributoStatus eventoStatus) {
		this.eventoStatus = eventoStatus;
	}

	public String getEventoResponsavel() {
		return eventoResponsavel;
	}

	public void setEventoResponsavel(String eventoResponsavel) {
		this.eventoResponsavel = eventoResponsavel;
	}

	public String getEventoMotivoOperacao() {
		return eventoMotivoOperacao;
	}

	public void setEventoMotivoOperacao(String eventoMotivoOperacao) {
		this.eventoMotivoOperacao = eventoMotivoOperacao;
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

	public AtributoPrioridade getEventoPrioridade() {
		return eventoPrioridade;
	}

	public void setEventoPrioridade(AtributoPrioridade eventoPrioridade) {
		this.eventoPrioridade = eventoPrioridade;
	}

	
}
