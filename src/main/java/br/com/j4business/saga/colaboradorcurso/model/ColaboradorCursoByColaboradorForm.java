package br.com.j4business.saga.colaboradorcurso.model;

public class ColaboradorCursoByColaboradorForm {

	private String searchCursoNome;
	private String searchColaboradorNome;
	private String colaboradorCursoSortTipo;
	private boolean cursoNomeSortAsc = false ;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ColaboradorCursoByColaboradorForm() {
		super();
	}

	public String getSearchCursoNome() {
		return searchCursoNome;
	}

	public void setSearchCursoNome(String searchCursoNome) {
		this.searchCursoNome = searchCursoNome;
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getColaboradorCursoSortTipo() {
		return colaboradorCursoSortTipo;
	}

	public void setColaboradorCursoSortTipo(String colaboradorCursoSortTipo) {
		this.colaboradorCursoSortTipo = colaboradorCursoSortTipo;
	}

	public boolean isCursoNomeSortAsc() {
		return cursoNomeSortAsc;
	}

	public void setCursoNomeSortAsc(boolean cursoNomeSortAsc) {
		this.cursoNomeSortAsc = cursoNomeSortAsc;
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
