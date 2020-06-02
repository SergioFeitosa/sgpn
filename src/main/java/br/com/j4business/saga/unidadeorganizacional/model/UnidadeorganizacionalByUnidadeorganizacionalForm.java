package br.com.j4business.saga.unidadeorganizacional.model;

public class UnidadeorganizacionalByUnidadeorganizacionalForm {

	private String searchUnidadeorganizacionalNome;
	private String searchUnidadeorganizacionalDescricao;
	private String unidadeorganizacionalSortTipo;
	private boolean unidadeorganizacionalNomeSortAsc = false ;
	private boolean unidadeorganizacionalDescricaoSortAsc = false ;
	
	public UnidadeorganizacionalByUnidadeorganizacionalForm() {
		super();
	}

	public String getSearchUnidadeorganizacionalNome() {
		return searchUnidadeorganizacionalNome;
	}

	public void setSearchUnidadeorganizacionalNome(String searchUnidadeorganizacionalNome) {
		this.searchUnidadeorganizacionalNome = searchUnidadeorganizacionalNome;
	}

	public String getSearchUnidadeorganizacionalDescricao() {
		return searchUnidadeorganizacionalDescricao;
	}

	public void setSearchUnidadeorganizacionalDescricao(String searchUnidadeorganizacionalDescricao) {
		this.searchUnidadeorganizacionalDescricao = searchUnidadeorganizacionalDescricao;
	}

	public String getUnidadeorganizacionalSortTipo() {
		return unidadeorganizacionalSortTipo;
	}

	public void setUnidadeorganizacionalSortTipo(String unidadeorganizacionalSortTipo) {
		this.unidadeorganizacionalSortTipo = unidadeorganizacionalSortTipo;
	}

	public boolean isUnidadeorganizacionalNomeSortAsc() {
		return unidadeorganizacionalNomeSortAsc;
	}

	public void setUnidadeorganizacionalNomeSortAsc(boolean unidadeorganizacionalNomeSortAsc) {
		this.unidadeorganizacionalNomeSortAsc = unidadeorganizacionalNomeSortAsc;
	}

	public boolean isUnidadeorganizacionalDescricaoSortAsc() {
		return unidadeorganizacionalDescricaoSortAsc;
	}

	public void setUnidadeorganizacionalDescricaoSortAsc(boolean unidadeorganizacionalDescricaoSortAsc) {
		this.unidadeorganizacionalDescricaoSortAsc = unidadeorganizacionalDescricaoSortAsc;
	}

}
