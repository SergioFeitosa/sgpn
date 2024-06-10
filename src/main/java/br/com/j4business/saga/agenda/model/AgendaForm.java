package br.com.j4business.saga.agenda.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AgendaForm {

	private long agendaPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String agendaNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String agendaDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus agendaStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String agendaResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String agendaMotivoOperacao;

	public AgendaForm() {
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

	public String getAgendaResponsavel() {
		return agendaResponsavel;
	}

	public void setAgendaResponsavel(String agendaResponsavel) {
		this.agendaResponsavel = agendaResponsavel;
	}

	public String getAgendaMotivoOperacao() {
		return agendaMotivoOperacao;
	}

	public void setAgendaMotivoOperacao(String agendaMotivoOperacao) {
		this.agendaMotivoOperacao = agendaMotivoOperacao;
	}

	public AtributoStatus getAgendaStatus() {
		return agendaStatus;
	}

	public void setAgendaStatus(AtributoStatus agendaStatus) {
		this.agendaStatus = agendaStatus;
	}

	
}
