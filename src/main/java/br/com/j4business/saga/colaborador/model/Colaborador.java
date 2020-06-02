package br.com.j4business.saga.colaborador.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.j4business.saga.pessoafisica.model.PessoaFisica;

@Entity
@Table(name="colaborador")

public class Colaborador  extends PessoaFisica implements Serializable{

	private static final long serialVersionUID = -192790905599861464L;

	@Column(name = "co_status")
	private String colaboradorStatus;

	@Column(name = "co_motivooperacao")
	private String colaboradorMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@Column(name = "nm_colaboradorApelido")
	private String colaboradorApelido;

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
    

	
	public String getColaboradorApelido() {
		return colaboradorApelido;
	}

	public void setColaboradorApelido(String colaboradorApelido) {
		this.colaboradorApelido = colaboradorApelido;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public Colaborador() {
		super();
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

	public String getRedeSocialIdentificacao() {
		return redeSocialIdentificacao;
	}

	public void setRedeSocialIdentificacao(String redeSocialIdentificacao) {
		this.redeSocialIdentificacao = redeSocialIdentificacao;
	}

	public String getColaboradorStatus() {
		return colaboradorStatus;
	}

	public void setColaboradorStatus(String colaboradorStatus) {
		this.colaboradorStatus = colaboradorStatus;
	}

	public String getColaboradorMotivoOperacao() {
		return colaboradorMotivoOperacao;
	}

	public void setColaboradorMotivoOperacao(String colaboradorMotivoOperacao) {
		this.colaboradorMotivoOperacao = colaboradorMotivoOperacao;
	}

	
}
