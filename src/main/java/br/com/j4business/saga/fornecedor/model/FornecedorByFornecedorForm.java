package br.com.j4business.saga.fornecedor.model;

public class FornecedorByFornecedorForm {

	private String searchFornecedorNome;
	private String searchFornecedorNomeFantasia;
	private String fornecedorSortTipo;
	private boolean fornecedorNomeSortAsc = false ;
	private boolean fornecedorNomeFantasiaSortAsc = false ;
	
	public FornecedorByFornecedorForm() {
		super();
	}

	public String getSearchFornecedorNome() {
		return searchFornecedorNome;
	}

	public void setSearchFornecedorNome(String searchFornecedorNome) {
		this.searchFornecedorNome = searchFornecedorNome;
	}

	public String getSearchFornecedorNomeFantasia() {
		return searchFornecedorNomeFantasia;
	}

	public void setSearchFornecedorNomeFantasia(String searchFornecedorNomeFantasia) {
		this.searchFornecedorNomeFantasia = searchFornecedorNomeFantasia;
	}

	public String getFornecedorSortTipo() {
		return fornecedorSortTipo;
	}

	public void setFornecedorSortTipo(String fornecedorSortTipo) {
		this.fornecedorSortTipo = fornecedorSortTipo;
	}

	public boolean isFornecedorNomeSortAsc() {
		return fornecedorNomeSortAsc;
	}

	public void setFornecedorNomeSortAsc(boolean fornecedorNomeSortAsc) {
		this.fornecedorNomeSortAsc = fornecedorNomeSortAsc;
	}

	public boolean isFornecedorNomeFantasiaSortAsc() {
		return fornecedorNomeFantasiaSortAsc;
	}

	public void setFornecedorNomeFantasiaSortAsc(boolean fornecedorNomeFantasiaSortAsc) {
		this.fornecedorNomeFantasiaSortAsc = fornecedorNomeFantasiaSortAsc;
	}


}
