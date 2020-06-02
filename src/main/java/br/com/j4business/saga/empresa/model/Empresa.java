package br.com.j4business.saga.empresa.model;


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
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcesso;
import br.com.j4business.saga.pessoajuridica.model.PessoaJuridica;

@Entity
@Table(name = "empresa")
public class Empresa  extends PessoaJuridica implements Serializable{ 

	private static final long serialVersionUID = -388580969043903416L;

	@Column(name = "nm_empresafantasia")
	private String empresaNomeFantasia;

	@Column(name = "nm_empresaramo")
	private String empresaRamo;

	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmpresaProcesso> empresaProcessos = new HashSet<EmpresaProcesso>();
	
	@Column(name = "em_status")
	private String empresaStatus;

	@Column(name = "em_motivooperacao")
	private String empresaMotivoOperacao;

	
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
    

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public Empresa() {
		super();
	}

	public Set<EmpresaProcesso> getEmpresaProcessos() {
		return empresaProcessos;
	}

	public void setEmpresaProcessos(Set<EmpresaProcesso> empresaProcessos) {
		this.empresaProcessos = empresaProcessos;
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

	public String getEmpresaRamo() {
		return empresaRamo;
	}

	public void setEmpresaRamo(String empresaRamo) {
		this.empresaRamo = empresaRamo;
	}

	public String getRedeSocialIdentificacao() {
		return redeSocialIdentificacao;
	}

	public void setRedeSocialIdentificacao(String redeSocialIdentificacao) {
		this.redeSocialIdentificacao = redeSocialIdentificacao;
	}

	public String getEmpresaStatus() {
		return empresaStatus;
	}

	public void setEmpresaStatus(String empresaStatus) {
		this.empresaStatus = empresaStatus;
	}

	public String getEmpresaMotivoOperacao() {
		return empresaMotivoOperacao;
	}

	public void setEmpresaMotivoOperacao(String empresaMotivoOperacao) {
		this.empresaMotivoOperacao = empresaMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

}
