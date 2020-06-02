package br.com.j4business.saga.servico.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ServicoForm {

	private long servicoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String servicoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String servicoDescricao;

    @NotEmpty(message = "O Gestor do Serviço é uma informação obrigatória.")
	@NotNull(message = "O Gestor do Serviço é uma informação obrigatória.")
	private String servicoGestor;

    @NotEmpty(message = "O Dono do Serviço é uma informação obrigatória.")
	@NotNull(message = "O Dono do Serviço é uma informação obrigatória.")
	private String servicoDono;


	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus servicoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String servicoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String servicoMotivoOperacao;

	private int servicoDuracao;

	public ServicoForm() {
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

	public AtributoStatus getServicoStatus() {
		return servicoStatus;
	}

	public void setServicoStatus(AtributoStatus servicoStatus) {
		this.servicoStatus = servicoStatus;
	}

	public String getServicoResponsavel() {
		return servicoResponsavel;
	}

	public void setServicoResponsavel(String servicoResponsavel) {
		this.servicoResponsavel = servicoResponsavel;
	}

	public String getServicoMotivoOperacao() {
		return servicoMotivoOperacao;
	}

	public void setServicoMotivoOperacao(String servicoMotivoOperacao) {
		this.servicoMotivoOperacao = servicoMotivoOperacao;
	}

	public String getServicoGestor() {
		return servicoGestor;
	}

	public void setServicoGestor(String servicoGestor) {
		this.servicoGestor = servicoGestor;
	}

	public String getServicoDono() {
		return servicoDono;
	}

	public void setServicoDono(String servicoDono) {
		this.servicoDono = servicoDono;
	}

	public int getServicoDuracao() {
		return servicoDuracao;
	}

	public void setServicoDuracao(int servicoDuracao) {
		this.servicoDuracao = servicoDuracao;
	}

	
}
