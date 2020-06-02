package br.com.j4business.saga.acao.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AcaoForm {

	private long acaoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String acaoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String acaoDescricao;

    @NotEmpty(message = "O Gestor da Ação é uma informação obrigatória.")
	@NotNull(message = "O Gestor da Ação é uma informação obrigatória.")
	private String acaoGestor;

    @NotEmpty(message = "O Dono da Ação é uma informação obrigatória.")
	@NotNull(message = "O Dono da Ação é uma informação obrigatória.")
	private String acaoDono;

	@NotNull(message = "Aprovação é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoAprovacao acaoAprovacao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus acaoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String acaoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String acaoMotivoOperacao;

	private long acaoUsuarioPK;
	private String acaoUsuarioNome;
    
    
	public AcaoForm() {
		super();
	}

	public long getAcaoPK() {
		return acaoPK;
	}

	public void setAcaoPK(long acaoPK) {
		this.acaoPK = acaoPK;
	}

	public String getAcaoNome() {
		return acaoNome;
	}

	public void setAcaoNome(String acaoNome) {
		this.acaoNome = acaoNome;
	}

	public String getAcaoDescricao() {
		return acaoDescricao;
	}

	public void setAcaoDescricao(String acaoDescricao) {
		this.acaoDescricao = acaoDescricao;
	}

	public String getAcaoResponsavel() {
		return acaoResponsavel;
	}

	public void setAcaoResponsavel(String acaoResponsavel) {
		this.acaoResponsavel = acaoResponsavel;
	}

	public String getAcaoMotivoOperacao() {
		return acaoMotivoOperacao;
	}

	public void setAcaoMotivoOperacao(String acaoMotivoOperacao) {
		this.acaoMotivoOperacao = acaoMotivoOperacao;
	}

	public String getAcaoGestor() {
		return acaoGestor;
	}

	public void setAcaoGestor(String acaoGestor) {
		this.acaoGestor = acaoGestor;
	}

	public String getAcaoDono() {
		return acaoDono;
	}

	public void setAcaoDono(String acaoDono) {
		this.acaoDono = acaoDono;
	}

	public void setAcaoStatus(AtributoStatus acaoStatus) {
		this.acaoStatus = acaoStatus;
	}

	public AtributoStatus getAcaoStatus() {
		return acaoStatus;
	}

	public void setAcaoAprovacao(AtributoAprovacao acaoAprovacao) {
		this.acaoAprovacao = acaoAprovacao;
	}

	public AtributoAprovacao getAcaoAprovacao() {
		return acaoAprovacao;
	}

	public long getAcaoUsuarioPK() {
		return acaoUsuarioPK;
	}

	public void setAcaoUsuarioPK(long acaoUsuarioPK) {
		this.acaoUsuarioPK = acaoUsuarioPK;
	}

	public String getAcaoUsuarioNome() {
		return acaoUsuarioNome;
	}

	public void setAcaoUsuarioNome(String acaoUsuarioNome) {
		this.acaoUsuarioNome = acaoUsuarioNome;
	}

	
}
