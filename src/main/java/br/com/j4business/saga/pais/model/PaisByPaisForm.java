package br.com.j4business.saga.pais.model;

public class PaisByPaisForm {

	private String searchPaisNome;
	private String searchPaisSigla;
	private String paisSortTipo;
	private boolean paisNomeSortAsc = false ;
	private boolean paisSiglaSortAsc = false ;
	
	public PaisByPaisForm() {
		super();
	}

	public String getSearchPaisNome() {
		return searchPaisNome;
	}

	public void setSearchPaisNome(String searchPaisNome) {
		this.searchPaisNome = searchPaisNome;
	}

	public String getSearchPaisSigla() {
		return searchPaisSigla;
	}

	public void setSearchPaisSigla(String searchPaisSigla) {
		this.searchPaisSigla = searchPaisSigla;
	}

	public String getPaisSortTipo() {
		return paisSortTipo;
	}

	public void setPaisSortTipo(String paisSortTipo) {
		this.paisSortTipo = paisSortTipo;
	}

	public boolean isPaisNomeSortAsc() {
		return paisNomeSortAsc;
	}

	public void setPaisNomeSortAsc(boolean paisNomeSortAsc) {
		this.paisNomeSortAsc = paisNomeSortAsc;
	}

	public boolean isPaisSiglaSortAsc() {
		return paisSiglaSortAsc;
	}

	public void setPaisSiglaSortAsc(boolean paisSiglaSortAsc) {
		this.paisSiglaSortAsc = paisSiglaSortAsc;
	}

}
