package br.com.j4business.saga.unidadeorganizacional.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class UnidadeorganizacionalForm {

	private long unidadeorganizacionalPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String unidadeorganizacionalNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String unidadeorganizacionalDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus unidadeorganizacionalStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String unidadeorganizacionalResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String unidadeorganizacionalMotivoOperacao;

	public UnidadeorganizacionalForm() {
		super();
	}

	public long getUnidadeorganizacionalPK() {
		return unidadeorganizacionalPK;
	}

	public void setUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		this.unidadeorganizacionalPK = unidadeorganizacionalPK;
	}

	public String getUnidadeorganizacionalNome() {
		return unidadeorganizacionalNome;
	}

	public void setUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		this.unidadeorganizacionalNome = unidadeorganizacionalNome;
	}

	public String getUnidadeorganizacionalDescricao() {
		return unidadeorganizacionalDescricao;
	}

	public void setUnidadeorganizacionalDescricao(String unidadeorganizacionalDescricao) {
		this.unidadeorganizacionalDescricao = unidadeorganizacionalDescricao;
	}

	public AtributoStatus getUnidadeorganizacionalStatus() {
		return unidadeorganizacionalStatus;
	}

	public void setUnidadeorganizacionalStatus(AtributoStatus unidadeorganizacionalStatus) {
		this.unidadeorganizacionalStatus = unidadeorganizacionalStatus;
	}

	public String getUnidadeorganizacionalResponsavel() {
		return unidadeorganizacionalResponsavel;
	}

	public void setUnidadeorganizacionalResponsavel(String unidadeorganizacionalResponsavel) {
		this.unidadeorganizacionalResponsavel = unidadeorganizacionalResponsavel;
	}

	public String getUnidadeorganizacionalMotivoOperacao() {
		return unidadeorganizacionalMotivoOperacao;
	}

	public void setUnidadeorganizacionalMotivoOperacao(String unidadeorganizacionalMotivoOperacao) {
		this.unidadeorganizacionalMotivoOperacao = unidadeorganizacionalMotivoOperacao;
	}

	
}
