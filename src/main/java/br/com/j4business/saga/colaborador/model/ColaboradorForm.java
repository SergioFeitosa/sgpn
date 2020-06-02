package br.com.j4business.saga.colaborador.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ColaboradorForm {

	private long colaboradorPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 200 caracteres")
	private String colaboradorNome;
	
    @NotEmpty(message = "Apelido é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O apelido não pode ter menos que 3 e mais que 200 caracteres")
	private String colaboradorApelido;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@CPF(message = "CPF deve ser válido.")
	private String colaboradorCPF;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus colaboradorStatus;

	@NotNull(message = "Responsável é uma informação obrigatória.")
    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	private String colaboradorResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String colaboradorMotivoOperacao;

    @NotEmpty(message = "Logradouro é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O Logradouro não pode ter menos que 3 e mais que 200 caracteres")
	private String enderecoLogradouro;
    @NotEmpty(message = "Bairro é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O Bairro não pode ter menos que 3 e mais que 200 caracteres")
	private String enderecoBairro;
    @NotEmpty(message = "Município é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O município não pode ter menos que 3 e mais que 200 caracteres")
	private String enderecoMunicipio;
    @NotEmpty(message = "U.F. é uma informação obrigatória.")
	@Size(min = 2,max = 2, message = "A U.F. deve ter 2 caracteres")
	private String enderecoUF;
    
    @NotNull(message = "CEP é uma informação obrigatória.")
    @NotEmpty(message = "CEP é uma informação obrigatória.")
	@Size(min = 9,max = 9, message = "O CEP deve ter 9 caracteres")
    private String enderecoCEP;


    @NotEmpty(message = "Número de telefone é uma informação obrigatória.")
	@Size(min = 13,max = 14, message = "Número de telefone deve ter 13 ou 14 caracteres")
	private String telefoneNumero;
    
    @NotEmpty(message = "Operadora de telefone é uma informação obrigatória.")
	@Size(min = 2,max = 20, message = "Operadora de Telefone deve ter de 2 a 20 caracteres")
	private String telefoneOperadora;
    
    @Email(message = "Email inválido. Informa novamente")
	@Size(min = 5,max = 50, message = "Email deve ter de 5 a 50 caracteres")
    private String emailNome;
    
    @NotEmpty(message = "Rede Social é uma informação obrigatória.")
	@Size(min = 2,max = 30, message = "A Rede Social deve ter de 2 a 30 caracteres")
	private String redeSocialNome;
    
    @NotEmpty(message = "A identificação na Rede Social é uma informação obrigatória.")
	@Size(min = 2,max = 30, message = "A Identificação na Rede Social deve ter de 2 a 30 caracteres")
	private String redeSocialIdentificacao;
    
	public String getEnderecoBairro() {
		return enderecoBairro;
	}

	public String getTelefoneNumero() {
		return telefoneNumero;
	}

	public void setTelefoneNumero(String telefoneNumero) {
		this.telefoneNumero = telefoneNumero;
	}

	public String getTelefoneOperadora() {
		return telefoneOperadora;
	}

	public void setTelefoneOperadora(String telefoneOperadora) {
		this.telefoneOperadora = telefoneOperadora;
	}

	public String getEmailNome() {
		return emailNome;
	}

	public void setEmailNome(String emailNome) {
		this.emailNome = emailNome;
	}

	public String getRedeSocialNome() {
		return redeSocialNome;
	}

	public void setRedeSocialNome(String redeSocialNome) {
		this.redeSocialNome = redeSocialNome;
	}

	public String getEnderecoMunicipio() {
		return enderecoMunicipio;
	}

	public void setEnderecoMunicipio(String enderecoMunicipio) {
		this.enderecoMunicipio = enderecoMunicipio;
	}

	public String getEnderecoUF() {
		return enderecoUF;
	}

	public void setEnderecoUF(String enderecoUF) {
		this.enderecoUF = enderecoUF;
	}

	public void setEnderecoBairro(String enderecoBairro) {
		this.enderecoBairro = enderecoBairro;
	}


	public ColaboradorForm() {
		super();
	}

	public long getColaboradorPK() {
		return colaboradorPK;
	}

	public void setColaboradorPK(long colaboradorPK) {
		this.colaboradorPK = colaboradorPK;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public String getColaboradorCPF() {
		return colaboradorCPF;
	}

	public void setColaboradorCPF(String colaboradorCPF) {
		this.colaboradorCPF = colaboradorCPF;
	}

	public AtributoStatus getColaboradorStatus() {
		return colaboradorStatus;
	}

	public void setColaboradorStatus(AtributoStatus colaboradorStatus) {
		this.colaboradorStatus = colaboradorStatus;
	}

	public String getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(String colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorMotivoOperacao() {
		return colaboradorMotivoOperacao;
	}

	public void setColaboradorMotivoOperacao(String colaboradorMotivoOperacao) {
		this.colaboradorMotivoOperacao = colaboradorMotivoOperacao;
	}

	public String getColaboradorApelido() {
		return colaboradorApelido;
	}

	public void setColaboradorApelido(String colaboradorApelido) {
		this.colaboradorApelido = colaboradorApelido;
	}

	public String getEnderecoLogradouro() {
		return enderecoLogradouro;
	}

	public void setEnderecoLogradouro(String enderecoLogradouro) {
		this.enderecoLogradouro = enderecoLogradouro;
	}

	public String getEnderecoCEP() {
		return enderecoCEP;
	}

	public void setEnderecoCEP(String enderecoCEP) {
		this.enderecoCEP = enderecoCEP;
	}

	public String getRedeSocialIdentificacao() {
		return redeSocialIdentificacao;
	}

	public void setRedeSocialIdentificacao(String redeSocialIdentificacao) {
		this.redeSocialIdentificacao = redeSocialIdentificacao;
	}

}
