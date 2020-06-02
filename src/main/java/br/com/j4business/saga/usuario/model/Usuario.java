package br.com.j4business.saga.usuario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario", uniqueConstraints=@UniqueConstraint(columnNames={"username"}, name="usuarioNome"))
public class Usuario {

	@Id
	@Column(name = "username",nullable = false, length = 50)
	private String usuarioNome;
	
	@Column(name = "password")
	private String usuarioDescricao;

	@Column(name = "enabled")
	private int usuarioStatus;

	public Usuario() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarioNome == null) ? 0 : usuarioNome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuarioNome == null) {
			if (other.usuarioNome != null)
				return false;
		} else if (!usuarioNome.equals(other.usuarioNome))
			return false;
		return true;
	}

	public int getUsuarioStatus() {
		return usuarioStatus;
	}

	public void setUsuarioStatus(int usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}

}
