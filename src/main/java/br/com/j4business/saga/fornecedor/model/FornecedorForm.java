package br.com.j4business.saga.fornecedor.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.fornecedor.enumeration.FornecedorRamo;

public class FornecedorForm {

	private long fornecedorPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 200 caracteres")
	private String fornecedorNome;
	
    @NotBlank(message = "Nome Fantasia é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome fantasia não pode ter menos que 3 e mais que 200 caracteres")
	private String fornecedorNomeFantasia;
	
    @NotNull(message = "Ramo é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private FornecedorRamo fornecedorRamo;
	
    @NotBlank(message = "CNPJ é uma informação obrigatória.")
	@CNPJ(message = "CNPJ inválido. Informe Novamente.")
	private String fornecedorCNPJ;
	
	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus fornecedorStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String fornecedorResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String fornecedorMotivoOperacao;

	private String searchFornecedorNome;
	private String searchFornecedorNomeFantasia;
	private String fornecedorSortTipo;
	private boolean fornecedorNomeSortAsc = false ;
	private boolean fornecedorDescricaoSortAsc = false ;

    @NotBlank(message = "Logradouro é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O Logradouro não pode ter menos que 3 e mais que 200 caracteres")
	private String enderecoLogradouro;
    @NotBlank(message = "Bairro é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O Bairro não pode ter menos que 3 e mais que 200 caracteres")
	private String enderecoBairro;
    @NotBlank(message = "Município é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O município não pode ter menos que 3 e mais que 200 caracteres")
	private String enderecoMunicipio;
    @NotBlank(message = "U.F. é uma informação obrigatória.")
	@Size(min = 2,max = 2, message = "A U.F. deve ter 2 caracteres")
	private String enderecoUF;
    @NotBlank(message = "CEP é uma informação obrigatória.")
	@Size(min = 9,max = 9, message = "O CEP deve ter 9 caracteres")
    private String enderecoCEP;


    @NotBlank(message = "Número de telefone é uma informação obrigatória.")
	@Size(min = 13,max = 14, message = "Número de telefone deve ter 13 ou 14 caracteres")
	private String telefoneNumero;
    
    @NotBlank(message = "Operadora de telefone é uma informação obrigatória.")
	@Size(min = 2,max = 20, message = "Operadora de Telefone deve ter de 2 a 20 caracteres")
	private String telefoneOperadora;
    
    @Email(message = "Email inválido. Informa novamente")
	@Size(min = 5,max = 50, message = "Email deve ter de 2 a 50caracteres")
    private String emailNome;
    
    @NotBlank(message = "Rede Social é uma informação obrigatória.")
	@Size(min = 2,max = 30, message = "A Rede Social deve ter de 2 a 30 caracteres")
	private String redeSocialNome;
    
    @NotBlank(message = "Identificação na Rede Social é uma informação obrigatória.")
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

	public FornecedorForm() {
		super();
	}

	public long getFornecedorPK() {
		return fornecedorPK;
	}

	public void setFornecedorPK(long fornecedorPK) {
		this.fornecedorPK = fornecedorPK;
	}

	public String getFornecedorNome() {
		return fornecedorNome;
	}

	public void setFornecedorNome(String fornecedorNome) {
		this.fornecedorNome = fornecedorNome;
	}

	public AtributoStatus getFornecedorStatus() {
		return fornecedorStatus;
	}

	public void setFornecedorStatus(AtributoStatus fornecedorStatus) {
		this.fornecedorStatus = fornecedorStatus;
	}

	public String getFornecedorResponsavel() {
		return fornecedorResponsavel;
	}

	public void setFornecedorResponsavel(String fornecedorResponsavel) {
		this.fornecedorResponsavel = fornecedorResponsavel;
	}

	public String getFornecedorMotivoOperacao() {
		return fornecedorMotivoOperacao;
	}

	public void setFornecedorMotivoOperacao(String fornecedorMotivoOperacao) {
		this.fornecedorMotivoOperacao = fornecedorMotivoOperacao;
	}

	public String getSearchFornecedorNome() {
		return searchFornecedorNome;
	}

	public void setSearchFornecedorNome(String searchFornecedorNome) {
		this.searchFornecedorNome = searchFornecedorNome;
	}

	public String getSearchFornecedorNomeFantasia() {
		return searchFornecedorNomeFantasia;
	}

	public void setSearchFornecedorNomeFantasia(String searchFornecedorNomeFantasia) {
		this.searchFornecedorNomeFantasia = searchFornecedorNomeFantasia;
	}

	public String getFornecedorSortTipo() {
		return fornecedorSortTipo;
	}

	public void setFornecedorSortTipo(String fornecedorSortTipo) {
		this.fornecedorSortTipo = fornecedorSortTipo;
	}

	public boolean isFornecedorNomeSortAsc() {
		return fornecedorNomeSortAsc;
	}

	public void setFornecedorNomeSortAsc(boolean fornecedorNomeSortAsc) {
		this.fornecedorNomeSortAsc = fornecedorNomeSortAsc;
	}

	public boolean isFornecedorDescricaoSortAsc() {
		return fornecedorDescricaoSortAsc;
	}

	public void setFornecedorDescricaoSortAsc(boolean fornecedorDescricaoSortAsc) {
		this.fornecedorDescricaoSortAsc = fornecedorDescricaoSortAsc;
	}

	public String getFornecedorNomeFantasia() {
		return fornecedorNomeFantasia;
	}

	public void setFornecedorNomeFantasia(String fornecedorNomeFantasia) {
		this.fornecedorNomeFantasia = fornecedorNomeFantasia;
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

	public String getFornecedorCNPJ() {
		return fornecedorCNPJ;
	}

	public void setFornecedorCNPJ(String fornecedorCNPJ) {
		this.fornecedorCNPJ = fornecedorCNPJ;
	}

	public void setFornecedorRamo(FornecedorRamo fornecedorRamo) {
		this.fornecedorRamo = fornecedorRamo;
	}

	public FornecedorRamo getFornecedorRamo() {
		return fornecedorRamo;
	}

	public String getRedeSocialIdentificacao() {
		return redeSocialIdentificacao;
	}

	public void setRedeSocialIdentificacao(String redeSocialIdentificacao) {
		this.redeSocialIdentificacao = redeSocialIdentificacao;
	}

	
}
