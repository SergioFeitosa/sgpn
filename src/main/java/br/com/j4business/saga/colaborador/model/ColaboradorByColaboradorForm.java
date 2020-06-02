package br.com.j4business.saga.colaborador.model;

public class ColaboradorByColaboradorForm {

	private String searchColaboradorNome;
	private String searchColaboradorApelido;
	private String colaboradorSortTipo;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean colaboradorApelidoSortAsc = false ;
	
	public ColaboradorByColaboradorForm() {
		super();
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getSearchColaboradorApelido() {
		return searchColaboradorApelido;
	}

	public void setSearchColaboradorApelido(String searchColaboradorApelido) {
		this.searchColaboradorApelido = searchColaboradorApelido;
	}

	public String getColaboradorSortTipo() {
		return colaboradorSortTipo;
	}

	public void setColaboradorSortTipo(String colaboradorSortTipo) {
		this.colaboradorSortTipo = colaboradorSortTipo;
	}

	public boolean isColaboradorNomeSortAsc() {
		return colaboradorNomeSortAsc;
	}

	public void setColaboradorNomeSortAsc(boolean colaboradorNomeSortAsc) {
		this.colaboradorNomeSortAsc = colaboradorNomeSortAsc;
	}

	public boolean isColaboradorApelidoSortAsc() {
		return colaboradorApelidoSortAsc;
	}

	public void setColaboradorApelidoSortAsc(boolean colaboradorApelidoSortAsc) {
		this.colaboradorApelidoSortAsc = colaboradorApelidoSortAsc;
	}


}
