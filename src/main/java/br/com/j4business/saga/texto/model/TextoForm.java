package br.com.j4business.saga.texto.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class TextoForm {

	private long textoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String textoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String textoDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus textoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String textoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String textoMotivoOperacao;

	@Size(min = 5,max = 200, message = "URL do vídeo não pode ter menos que 5 e mais que 200 caracteres")
	private String textoURL;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 3,max = 10, message = "Nome do botão do vídeo não pode ter menos que 3 e mais que 10 caracteres")
	private String textoNomeBotao;

    
	public TextoForm() {
		super();
	}

	public long getTextoPK() {
		return textoPK;
	}

	public void setTextoPK(long textoPK) {
		this.textoPK = textoPK;
	}

	public String getTextoNome() {
		return textoNome;
	}

	public void setTextoNome(String textoNome) {
		this.textoNome = textoNome;
	}

	public String getTextoDescricao() {
		return textoDescricao;
	}

	public void setTextoDescricao(String textoDescricao) {
		this.textoDescricao = textoDescricao;
	}

	public AtributoStatus getTextoStatus() {
		return textoStatus;
	}

	public void setTextoStatus(AtributoStatus textoStatus) {
		this.textoStatus = textoStatus;
	}

	public String getTextoResponsavel() {
		return textoResponsavel;
	}

	public void setTextoResponsavel(String textoResponsavel) {
		this.textoResponsavel = textoResponsavel;
	}

	public String getTextoMotivoOperacao() {
		return textoMotivoOperacao;
	}

	public void setTextoMotivoOperacao(String textoMotivoOperacao) {
		this.textoMotivoOperacao = textoMotivoOperacao;
	}

	public String getTextoURL() {
		return textoURL;
	}

	public void setTextoURL(String textoURL) {
		this.textoURL = textoURL;
	}

	public String getTextoNomeBotao() {
		return textoNomeBotao;
	}

	public void setTextoNomeBotao(String textoNomeBotao) {
		this.textoNomeBotao = textoNomeBotao;
	}

	
}
