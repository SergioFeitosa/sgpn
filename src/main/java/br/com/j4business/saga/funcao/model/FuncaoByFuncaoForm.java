package br.com.j4business.saga.funcao.model;

public class FuncaoByFuncaoForm {

	private String searchFuncaoNome;
	private String searchFuncaoDescricao;
	private String funcaoSortTipo;
	private boolean funcaoNomeSortAsc = false ;
	private boolean funcaoDescricaoSortAsc = false ;
	
	public FuncaoByFuncaoForm() {
		super();
	}

	public String getSearchFuncaoNome() {
		return searchFuncaoNome;
	}

	public void setSearchFuncaoNome(String searchFuncaoNome) {
		this.searchFuncaoNome = searchFuncaoNome;
	}

	public String getSearchFuncaoDescricao() {
		return searchFuncaoDescricao;
	}

	public void setSearchFuncaoDescricao(String searchFuncaoDescricao) {
		this.searchFuncaoDescricao = searchFuncaoDescricao;
	}

	public String getFuncaoSortTipo() {
		return funcaoSortTipo;
	}

	public void setFuncaoSortTipo(String funcaoSortTipo) {
		this.funcaoSortTipo = funcaoSortTipo;
	}

	public boolean isFuncaoNomeSortAsc() {
		return funcaoNomeSortAsc;
	}

	public void setFuncaoNomeSortAsc(boolean funcaoNomeSortAsc) {
		this.funcaoNomeSortAsc = funcaoNomeSortAsc;
	}

	public boolean isFuncaoDescricaoSortAsc() {
		return funcaoDescricaoSortAsc;
	}

	public void setFuncaoDescricaoSortAsc(boolean funcaoDescricaoSortAsc) {
		this.funcaoDescricaoSortAsc = funcaoDescricaoSortAsc;
	}

}
