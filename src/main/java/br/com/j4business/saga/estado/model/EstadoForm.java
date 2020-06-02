package br.com.j4business.saga.estado.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class EstadoForm {

	private long estadoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 100, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String estadoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 300, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String estadoSigla;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus estadoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String estadoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String estadoMotivoOperacao;

    
	public EstadoForm() {
		super();
	}

	public long getEstadoPK() {
		return estadoPK;
	}

	public void setEstadoPK(long estadoPK) {
		this.estadoPK = estadoPK;
	}

	public String getEstadoNome() {
		return estadoNome;
	}

	public void setEstadoNome(String estadoNome) {
		this.estadoNome = estadoNome;
	}

	public String getEstadoSigla() {
		return estadoSigla;
	}

	public void setEstadoSigla(String estadoSigla) {
		this.estadoSigla = estadoSigla;
	}

	public AtributoStatus getEstadoStatus() {
		return estadoStatus;
	}

	public void setEstadoStatus(AtributoStatus estadoStatus) {
		this.estadoStatus = estadoStatus;
	}

	public String getEstadoResponsavel() {
		return estadoResponsavel;
	}

	public void setEstadoResponsavel(String estadoResponsavel) {
		this.estadoResponsavel = estadoResponsavel;
	}

	public String getEstadoMotivoOperacao() {
		return estadoMotivoOperacao;
	}

	public void setEstadoMotivoOperacao(String estadoMotivoOperacao) {
		this.estadoMotivoOperacao = estadoMotivoOperacao;
	}

	
}
