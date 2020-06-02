package br.com.j4business.saga.usuario.model;

public class UsuarioByUsuarioForm {

	private String searchUsuarioNome;
	private String usuarioSortTipo;
	private boolean usuarioNomeSortAsc = false ;
	
	public UsuarioByUsuarioForm() {
		super();
	}

	public String getSearchUsuarioNome() {
		return searchUsuarioNome;
	}

	public void setSearchUsuarioNome(String searchUsuarioNome) {
		this.searchUsuarioNome = searchUsuarioNome;
	}

	public String getUsuarioSortTipo() {
		return usuarioSortTipo;
	}

	public void setUsuarioSortTipo(String usuarioSortTipo) {
		this.usuarioSortTipo = usuarioSortTipo;
	}

	public boolean isUsuarioNomeSortAsc() {
		return usuarioNomeSortAsc;
	}

	public void setUsuarioNomeSortAsc(boolean usuarioNomeSortAsc) {
		this.usuarioNomeSortAsc = usuarioNomeSortAsc;
	}

}
