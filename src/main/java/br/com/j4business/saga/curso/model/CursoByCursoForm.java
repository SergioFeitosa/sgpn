package br.com.j4business.saga.curso.model;

public class CursoByCursoForm {

	private String searchCursoNome;
	private String searchCursoDescricao;
	private String cursoSortTipo;
	private boolean cursoNomeSortAsc = false ;
	private boolean cursoDescricaoSortAsc = false ;
	
	public CursoByCursoForm() {
		super();
	}

	public String getSearchCursoNome() {
		return searchCursoNome;
	}

	public void setSearchCursoNome(String searchCursoNome) {
		this.searchCursoNome = searchCursoNome;
	}

	public String getSearchCursoDescricao() {
		return searchCursoDescricao;
	}

	public void setSearchCursoDescricao(String searchCursoDescricao) {
		this.searchCursoDescricao = searchCursoDescricao;
	}

	public String getCursoSortTipo() {
		return cursoSortTipo;
	}

	public void setCursoSortTipo(String cursoSortTipo) {
		this.cursoSortTipo = cursoSortTipo;
	}

	public boolean isCursoNomeSortAsc() {
		return cursoNomeSortAsc;
	}

	public void setCursoNomeSortAsc(boolean cursoNomeSortAsc) {
		this.cursoNomeSortAsc = cursoNomeSortAsc;
	}

	public boolean isCursoDescricaoSortAsc() {
		return cursoDescricaoSortAsc;
	}

	public void setCursoDescricaoSortAsc(boolean cursoDescricaoSortAsc) {
		this.cursoDescricaoSortAsc = cursoDescricaoSortAsc;
	}

}
