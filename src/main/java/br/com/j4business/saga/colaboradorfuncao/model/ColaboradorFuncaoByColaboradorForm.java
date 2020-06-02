package br.com.j4business.saga.colaboradorfuncao.model;

public class ColaboradorFuncaoByColaboradorForm {

	private String searchFuncaoNome;
	private String searchColaboradorNome;
	private String colaboradorFuncaoSortTipo;
	private boolean funcaoNomeSortAsc = false ;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ColaboradorFuncaoByColaboradorForm() {
		super();
	}

	public String getSearchFuncaoNome() {
		return searchFuncaoNome;
	}

	public void setSearchFuncaoNome(String searchFuncaoNome) {
		this.searchFuncaoNome = searchFuncaoNome;
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getColaboradorFuncaoSortTipo() {
		return colaboradorFuncaoSortTipo;
	}

	public void setColaboradorFuncaoSortTipo(String colaboradorFuncaoSortTipo) {
		this.colaboradorFuncaoSortTipo = colaboradorFuncaoSortTipo;
	}

	public boolean isFuncaoNomeSortAsc() {
		return funcaoNomeSortAsc;
	}

	public void setFuncaoNomeSortAsc(boolean funcaoNomeSortAsc) {
		this.funcaoNomeSortAsc = funcaoNomeSortAsc;
	}

	public boolean isColaboradorNomeSortAsc() {
		return colaboradorNomeSortAsc;
	}

	public void setColaboradorNomeSortAsc(boolean colaboradorNomeSortAsc) {
		this.colaboradorNomeSortAsc = colaboradorNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
