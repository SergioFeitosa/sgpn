package br.com.j4business.saga.fornecedorprocesso.model;

public class FornecedorProcessoByFornecedorForm {

	private String searchProcessoNome;
	private String searchFornecedorNome;
	private String fornecedorProcessoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean fornecedorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public FornecedorProcessoByFornecedorForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchFornecedorNome() {
		return searchFornecedorNome;
	}

	public void setSearchFornecedorNome(String searchFornecedorNome) {
		this.searchFornecedorNome = searchFornecedorNome;
	}

	public String getFornecedorProcessoSortTipo() {
		return fornecedorProcessoSortTipo;
	}

	public void setFornecedorProcessoSortTipo(String fornecedorProcessoSortTipo) {
		this.fornecedorProcessoSortTipo = fornecedorProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
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
