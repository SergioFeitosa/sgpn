package br.com.j4business.saga.colaboradorhabilidade.model;

public class ColaboradorHabilidadeByColaboradorForm {

	private String searchHabilidadeNome;
	private String searchColaboradorNome;
	private String colaboradorHabilidadeSortTipo;
	private boolean habilidadeNomeSortAsc = false ;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ColaboradorHabilidadeByColaboradorForm() {
		super();
	}

	public String getSearchHabilidadeNome() {
		return searchHabilidadeNome;
	}

	public void setSearchHabilidadeNome(String searchHabilidadeNome) {
		this.searchHabilidadeNome = searchHabilidadeNome;
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getColaboradorHabilidadeSortTipo() {
		return colaboradorHabilidadeSortTipo;
	}

	public void setColaboradorHabilidadeSortTipo(String colaboradorHabilidadeSortTipo) {
		this.colaboradorHabilidadeSortTipo = colaboradorHabilidadeSortTipo;
	}

	public boolean isHabilidadeNomeSortAsc() {
		return habilidadeNomeSortAsc;
	}

	public void setHabilidadeNomeSortAsc(boolean habilidadeNomeSortAsc) {
		this.habilidadeNomeSortAsc = habilidadeNomeSortAsc;
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
