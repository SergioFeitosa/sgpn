package br.com.j4business.saga.colaboradortreinamento.model;

public class ColaboradorTreinamentoByColaboradorForm {

	private String searchTreinamentoNome;
	private String searchColaboradorNome;
	private String colaboradorTreinamentoSortTipo;
	private boolean treinamentoNomeSortAsc = false ;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ColaboradorTreinamentoByColaboradorForm() {
		super();
	}

	public String getSearchTreinamentoNome() {
		return searchTreinamentoNome;
	}

	public void setSearchTreinamentoNome(String searchTreinamentoNome) {
		this.searchTreinamentoNome = searchTreinamentoNome;
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getColaboradorTreinamentoSortTipo() {
		return colaboradorTreinamentoSortTipo;
	}

	public void setColaboradorTreinamentoSortTipo(String colaboradorTreinamentoSortTipo) {
		this.colaboradorTreinamentoSortTipo = colaboradorTreinamentoSortTipo;
	}

	public boolean isTreinamentoNomeSortAsc() {
		return treinamentoNomeSortAsc;
	}

	public void setTreinamentoNomeSortAsc(boolean treinamentoNomeSortAsc) {
		this.treinamentoNomeSortAsc = treinamentoNomeSortAsc;
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
