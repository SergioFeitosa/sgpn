package br.com.j4business.saga.empresa.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.empresa.enumeration.EmpresaRamo;

public class EmpresaForm {

	private long empresaPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 200 caracteres")
	private String empresaNome;
	
    @NotBlank(message = "Nome Fantasia é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome fantasia não pode ter menos que 3 e mais que 200 caracteres")
	private String empresaNomeFantasia;
	
    @NotNull(message = "Ramo é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private EmpresaRamo empresaRamo;
	
    @NotBlank(message = "CNPJ é uma informação obrigatória.")
	@CNPJ(message = "CNPJ inválido. Informe Novamente.")
	private String empresaCNPJ;
	
	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus empresaStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String empresaResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String empresaMotivoOperacao;

	private String searchEmpresaNome;
	private String searchEmpresaNomeFantasia;
	private String empresaSortTipo;
	private boolean empresaNomeSortAsc = false ;
	private boolean empresaDescricaoSortAsc = false ;

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
	@Size(min = 2,max = 20, message = "Operadora de Telefone deve ter 2 a 20 caracteres")
	private String telefoneOperadora;
    
    @Email(message = "Email inválido. Informa novamente")
	@Size(min = 5,max = 50, message = "Email deve ter de 5 a 50 caracteres")
    private String emailNome;
    
    @NotBlank(message = "Rede Social é uma informação obrigatória.")
	@Size(min = 2,max = 30, message = "A Rede Social deve ter de 2 a 30 caracteres")
	private String redeSocialNome;
    
    @NotBlank(message = "Usuário da Rede Social é uma informação obrigatória.")
	@Size(min = 2,max = 30, message = "O usuário da Rede Social deve ter de 2 a 30 caracteres")
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

	public EmpresaForm() {
		super();
	}

	public long getEmpresaPK() {
		return empresaPK;
	}

	public void setEmpresaPK(long empresaPK) {
		this.empresaPK = empresaPK;
	}

	public String getEmpresaNome() {
		return empresaNome;
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}

	public AtributoStatus getEmpresaStatus() {
		return empresaStatus;
	}

	public void setEmpresaStatus(AtributoStatus empresaStatus) {
		this.empresaStatus = empresaStatus;
	}

	public String getEmpresaResponsavel() {
		return empresaResponsavel;
	}

	public void setEmpresaResponsavel(String empresaResponsavel) {
		this.empresaResponsavel = empresaResponsavel;
	}

	public String getEmpresaMotivoOperacao() {
		return empresaMotivoOperacao;
	}

	public void setEmpresaMotivoOperacao(String empresaMotivoOperacao) {
		this.empresaMotivoOperacao = empresaMotivoOperacao;
	}

	public String getSearchEmpresaNome() {
		return searchEmpresaNome;
	}

	public void setSearchEmpresaNome(String searchEmpresaNome) {
		this.searchEmpresaNome = searchEmpresaNome;
	}

	public String getSearchEmpresaNomeFantasia() {
		return searchEmpresaNomeFantasia;
	}

	public void setSearchEmpresaNomeFantasia(String searchEmpresaNomeFantasia) {
		this.searchEmpresaNomeFantasia = searchEmpresaNomeFantasia;
	}

	public String getEmpresaSortTipo() {
		return empresaSortTipo;
	}

	public void setEmpresaSortTipo(String empresaSortTipo) {
		this.empresaSortTipo = empresaSortTipo;
	}

	public boolean isEmpresaNomeSortAsc() {
		return empresaNomeSortAsc;
	}

	public void setEmpresaNomeSortAsc(boolean empresaNomeSortAsc) {
		this.empresaNomeSortAsc = empresaNomeSortAsc;
	}

	public boolean isEmpresaDescricaoSortAsc() {
		return empresaDescricaoSortAsc;
	}

	public void setEmpresaDescricaoSortAsc(boolean empresaDescricaoSortAsc) {
		this.empresaDescricaoSortAsc = empresaDescricaoSortAsc;
	}

	public String getEmpresaNomeFantasia() {
		return empresaNomeFantasia;
	}

	public void setEmpresaNomeFantasia(String empresaNomeFantasia) {
		this.empresaNomeFantasia = empresaNomeFantasia;
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

	public String getEmpresaCNPJ() {
		return empresaCNPJ;
	}

	public void setEmpresaCNPJ(String empresaCNPJ) {
		this.empresaCNPJ = empresaCNPJ;
	}

	public void setEmpresaRamo(EmpresaRamo empresaRamo) {
		this.empresaRamo = empresaRamo;
	}

	public EmpresaRamo getEmpresaRamo() {
		return empresaRamo;
	}

	public String getRedeSocialIdentificacao() {
		return redeSocialIdentificacao;
	}

	public void setRedeSocialIdentificacao(String redeSocialIdentificacao) {
		this.redeSocialIdentificacao = redeSocialIdentificacao;
	}

	
}
