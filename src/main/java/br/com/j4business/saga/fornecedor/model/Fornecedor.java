package br.com.j4business.saga.fornecedor.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcesso;
import br.com.j4business.saga.pessoajuridica.model.PessoaJuridica;

@Entity
@Table(name = "fornecedor")
public class Fornecedor  extends PessoaJuridica implements Serializable{ 

	private static final long serialVersionUID = -388580969043903416L;

	@Column(name = "nm_fornecedorfantasia")
	private String fornecedorNomeFantasia;

	@Column(name = "nm_fornecedorramo")
	private String fornecedorRamo;

	@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FornecedorProcesso> fornecedorProcessos = new HashSet<FornecedorProcesso>();
	
	
	// Endereco //
	
	@Column(name = "nm_enderecoLogradouro",nullable = false, length = 100)
	private String enderecoLogradouro;
	
	@Column(name = "nm_enderecoBairro")
	private String enderecoBairro;

	@Column(name = "nm_enderecoMunicipio")
	private String enderecoMunicipio;

	@Column(name = "nm_enderecoUF")
	private String enderecoUF;

	@Column(name = "nm_enderecoCEP")
	private String enderecoCEP;

	// Telefone //
	
	@Column(name = "nr_telefone")
	private String telefoneNumero;
    
	@Column(name = "nm_telefoneoperadora")
	private String telefoneOperadora;
    
	// Email //
	
	@Column(name = "nm_email")
	private String emailNome;
    
	// RedeSocial //

	@Column(name = "nm_redesocial")
	private String redeSocialNome;
    
	@Column(name = "nm_redesocialidentificacao")
	private String redeSocialIdentificacao;
    
	@Column(name = "fo_status")
	private String fornecedorStatus;

	@Column(name = "fo_motivooperacao")
	private String fornecedorMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Fornecedor() {
		super();
	}

	public Set<FornecedorProcesso> getFornecedorProcessos() {
		return fornecedorProcessos;
	}

	public void setFornecedorProcessos(Set<FornecedorProcesso> fornecedorProcessos) {
		this.fornecedorProcessos = fornecedorProcessos;
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

	public String getEnderecoBairro() {
		return enderecoBairro;
	}

	public void setEnderecoBairro(String enderecoBairro) {
		this.enderecoBairro = enderecoBairro;
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

	public String getEnderecoCEP() {
		return enderecoCEP;
	}

	public void setEnderecoCEP(String enderecoCEP) {
		this.enderecoCEP = enderecoCEP;
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

	public String getFornecedorRamo() {
		return fornecedorRamo;
	}

	public void setFornecedorRamo(String fornecedorRamo) {
		this.fornecedorRamo = fornecedorRamo;
	}

	public String getRedeSocialIdentificacao() {
		return redeSocialIdentificacao;
	}

	public void setRedeSocialIdentificacao(String redeSocialIdentificacao) {
		this.redeSocialIdentificacao = redeSocialIdentificacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getFornecedorStatus() {
		return fornecedorStatus;
	}

	public void setFornecedorStatus(String fornecedorStatus) {
		this.fornecedorStatus = fornecedorStatus;
	}

	public String getFornecedorMotivoOperacao() {
		return fornecedorMotivoOperacao;
	}

	public void setFornecedorMotivoOperacao(String fornecedorMotivoOperacao) {
		this.fornecedorMotivoOperacao = fornecedorMotivoOperacao;
	}

}
