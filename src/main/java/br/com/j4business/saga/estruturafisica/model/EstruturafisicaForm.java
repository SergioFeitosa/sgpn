package br.com.j4business.saga.estruturafisica.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class EstruturafisicaForm {

	private long estruturafisicaPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String estruturafisicaNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String estruturafisicaDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus estruturafisicaStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String estruturafisicaResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String estruturafisicaMotivoOperacao;

	public EstruturafisicaForm() {
		super();
	}

	public long getEstruturafisicaPK() {
		return estruturafisicaPK;
	}

	public void setEstruturafisicaPK(long estruturafisicaPK) {
		this.estruturafisicaPK = estruturafisicaPK;
	}

	public String getEstruturafisicaNome() {
		return estruturafisicaNome;
	}

	public void setEstruturafisicaNome(String estruturafisicaNome) {
		this.estruturafisicaNome = estruturafisicaNome;
	}

	public String getEstruturafisicaDescricao() {
		return estruturafisicaDescricao;
	}

	public void setEstruturafisicaDescricao(String estruturafisicaDescricao) {
		this.estruturafisicaDescricao = estruturafisicaDescricao;
	}

	public AtributoStatus getEstruturafisicaStatus() {
		return estruturafisicaStatus;
	}

	public void setEstruturafisicaStatus(AtributoStatus estruturafisicaStatus) {
		this.estruturafisicaStatus = estruturafisicaStatus;
	}

	public String getEstruturafisicaResponsavel() {
		return estruturafisicaResponsavel;
	}

	public void setEstruturafisicaResponsavel(String estruturafisicaResponsavel) {
		this.estruturafisicaResponsavel = estruturafisicaResponsavel;
	}

	public String getEstruturafisicaMotivoOperacao() {
		return estruturafisicaMotivoOperacao;
	}

	public void setEstruturafisicaMotivoOperacao(String estruturafisicaMotivoOperacao) {
		this.estruturafisicaMotivoOperacao = estruturafisicaMotivoOperacao;
	}

	
}
