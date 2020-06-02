package br.com.j4business.saga.texto.model;

public class TextoByTextoForm {

	private String searchTextoNome;
	private String searchTextoDescricao;
	private String textoSortTipo;
	private boolean textoNomeSortAsc = false ;
	private boolean textoDescricaoSortAsc = false ;
	
	public TextoByTextoForm() {
		super();
	}

	public String getSearchTextoNome() {
		return searchTextoNome;
	}

	public void setSearchTextoNome(String searchTextoNome) {
		this.searchTextoNome = searchTextoNome;
	}

	public String getSearchTextoDescricao() {
		return searchTextoDescricao;
	}

	public void setSearchTextoDescricao(String searchTextoDescricao) {
		this.searchTextoDescricao = searchTextoDescricao;
	}

	public String getTextoSortTipo() {
		return textoSortTipo;
	}

	public void setTextoSortTipo(String textoSortTipo) {
		this.textoSortTipo = textoSortTipo;
	}

	public boolean isTextoNomeSortAsc() {
		return textoNomeSortAsc;
	}

	public void setTextoNomeSortAsc(boolean textoNomeSortAsc) {
		this.textoNomeSortAsc = textoNomeSortAsc;
	}

	public boolean isTextoDescricaoSortAsc() {
		return textoDescricaoSortAsc;
	}

	public void setTextoDescricaoSortAsc(boolean textoDescricaoSortAsc) {
		this.textoDescricaoSortAsc = textoDescricaoSortAsc;
	}

}
