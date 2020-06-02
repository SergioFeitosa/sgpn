package br.com.j4business.saga.colaboradorformacao.model;

public class ColaboradorFormacaoByColaboradorForm {

	private String searchFormacaoNome;
	private String searchColaboradorNome;
	private String colaboradorFormacaoSortTipo;
	private boolean formacaoNomeSortAsc = false ;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ColaboradorFormacaoByColaboradorForm() {
		super();
	}

	public String getSearchFormacaoNome() {
		return searchFormacaoNome;
	}

	public void setSearchFormacaoNome(String searchFormacaoNome) {
		this.searchFormacaoNome = searchFormacaoNome;
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getColaboradorFormacaoSortTipo() {
		return colaboradorFormacaoSortTipo;
	}

	public void setColaboradorFormacaoSortTipo(String colaboradorFormacaoSortTipo) {
		this.colaboradorFormacaoSortTipo = colaboradorFormacaoSortTipo;
	}

	public boolean isFormacaoNomeSortAsc() {
		return formacaoNomeSortAsc;
	}

	public void setFormacaoNomeSortAsc(boolean formacaoNomeSortAsc) {
		this.formacaoNomeSortAsc = formacaoNomeSortAsc;
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
