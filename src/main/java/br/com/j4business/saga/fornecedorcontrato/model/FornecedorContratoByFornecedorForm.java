package br.com.j4business.saga.fornecedorcontrato.model;

public class FornecedorContratoByFornecedorForm {

	private String searchContratoNome;
	private String searchFornecedorNome;
	private String fornecedorContratoSortTipo;
	private boolean contratoNomeSortAsc = false ;
	private boolean fornecedorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public FornecedorContratoByFornecedorForm() {
		super();
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getSearchFornecedorNome() {
		return searchFornecedorNome;
	}

	public void setSearchFornecedorNome(String searchFornecedorNome) {
		this.searchFornecedorNome = searchFornecedorNome;
	}

	public String getFornecedorContratoSortTipo() {
		return fornecedorContratoSortTipo;
	}

	public void setFornecedorContratoSortTipo(String fornecedorContratoSortTipo) {
		this.fornecedorContratoSortTipo = fornecedorContratoSortTipo;
	}

	public boolean isContratoNomeSortAsc() {
		return contratoNomeSortAsc;
	}

	public void setContratoNomeSortAsc(boolean contratoNomeSortAsc) {
		this.contratoNomeSortAsc = contratoNomeSortAsc;
	}

	public boolean isFornecedorNomeSortAsc() {
		return fornecedorNomeSortAsc;
	}

	public void setFornecedorNomeSortAsc(boolean fornecedorNomeSortAsc) {
		this.fornecedorNomeSortAsc = fornecedorNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
