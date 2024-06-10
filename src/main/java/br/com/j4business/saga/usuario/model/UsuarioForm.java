package br.com.j4business.saga.usuario.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UsuarioForm {

    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String usuarioNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String usuarioDescricao;

	@NotNull(message = "Status é uma informação obrigatória.")
	private int usuarioStatus;

	public UsuarioForm() {
		super();
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioDescricao() {
		return usuarioDescricao;
	}

	public void setUsuarioDescricao(String usuarioDescricao) {
		this.usuarioDescricao = usuarioDescricao;
	}

	public int getUsuarioStatus() {
		return usuarioStatus;
	}

	public void setUsuarioStatus(int usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}

}
