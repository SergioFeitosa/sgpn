package br.com.j4business.saga.imagem.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class ImagemForm {

	private long imagemPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String imagemNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String imagemDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus imagemStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String imagemResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String imagemMotivoOperacao;

	@Size(min = 5,max = 200, message = "URL do vídeo não pode ter menos que 5 e mais que 200 caracteres")
	private String imagemURL;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 3,max = 10, message = "Nome do botão do vídeo não pode ter menos que 3 e mais que 10 caracteres")
	private String imagemNomeBotao;

    
	public ImagemForm() {
		super();
	}

	public long getImagemPK() {
		return imagemPK;
	}

	public void setImagemPK(long imagemPK) {
		this.imagemPK = imagemPK;
	}

	public String getImagemNome() {
		return imagemNome;
	}

	public void setImagemNome(String imagemNome) {
		this.imagemNome = imagemNome;
	}

	public String getImagemDescricao() {
		return imagemDescricao;
	}

	public void setImagemDescricao(String imagemDescricao) {
		this.imagemDescricao = imagemDescricao;
	}

	public AtributoStatus getImagemStatus() {
		return imagemStatus;
	}

	public void setImagemStatus(AtributoStatus imagemStatus) {
		this.imagemStatus = imagemStatus;
	}

	public String getImagemResponsavel() {
		return imagemResponsavel;
	}

	public void setImagemResponsavel(String imagemResponsavel) {
		this.imagemResponsavel = imagemResponsavel;
	}

	public String getImagemMotivoOperacao() {
		return imagemMotivoOperacao;
	}

	public void setImagemMotivoOperacao(String imagemMotivoOperacao) {
		this.imagemMotivoOperacao = imagemMotivoOperacao;
	}

	public String getImagemURL() {
		return imagemURL;
	}

	public void setImagemURL(String imagemURL) {
		this.imagemURL = imagemURL;
	}

	public String getImagemNomeBotao() {
		return imagemNomeBotao;
	}

	public void setImagemNomeBotao(String imagemNomeBotao) {
		this.imagemNomeBotao = imagemNomeBotao;
	}

	
}
