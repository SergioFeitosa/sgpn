package br.com.j4business.saga.resultado.model;

public class ResultadoByResultadoForm {

	private String searchResultadoNome;
	private String searchResultadoDescricao;
	private String resultadoSortTipo;
	private boolean resultadoNomeSortAsc = false ;
	private boolean resultadoDescricaoSortAsc = false ;
	
	public ResultadoByResultadoForm() {
		super();
	}

	public String getSearchResultadoNome() {
		return searchResultadoNome;
	}

	public void setSearchResultadoNome(String searchResultadoNome) {
		this.searchResultadoNome = searchResultadoNome;
	}

	public String getSearchResultadoDescricao() {
		return searchResultadoDescricao;
	}

	public void setSearchResultadoDescricao(String searchResultadoDescricao) {
		this.searchResultadoDescricao = searchResultadoDescricao;
	}

	public String getResultadoSortTipo() {
		return resultadoSortTipo;
	}

	public void setResultadoSortTipo(String resultadoSortTipo) {
		this.resultadoSortTipo = resultadoSortTipo;
	}

	public boolean isResultadoNomeSortAsc() {
		return resultadoNomeSortAsc;
	}

	public void setResultadoNomeSortAsc(boolean resultadoNomeSortAsc) {
		this.resultadoNomeSortAsc = resultadoNomeSortAsc;
	}

	public boolean isResultadoDescricaoSortAsc() {
		return resultadoDescricaoSortAsc;
	}

	public void setResultadoDescricaoSortAsc(boolean resultadoDescricaoSortAsc) {
		this.resultadoDescricaoSortAsc = resultadoDescricaoSortAsc;
	}

}
