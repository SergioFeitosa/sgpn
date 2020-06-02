package br.com.j4business.saga.estruturafisicaunidadeorganizacional.model;

public class EstruturafisicaUnidadeorganizacionalByEstruturafisicaForm {

	private String searchUnidadeorganizacionalNome;
	private String searchEstruturafisicaNome;
	private String estruturafisicaUnidadeorganizacionalSortTipo;
	private boolean unidadeorganizacionalNomeSortAsc = false ;
	private boolean estruturafisicaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public EstruturafisicaUnidadeorganizacionalByEstruturafisicaForm() {
		super();
	}

	public String getSearchUnidadeorganizacionalNome() {
		return searchUnidadeorganizacionalNome;
	}

	public void setSearchUnidadeorganizacionalNome(String searchUnidadeorganizacionalNome) {
		this.searchUnidadeorganizacionalNome = searchUnidadeorganizacionalNome;
	}

	public String getSearchEstruturafisicaNome() {
		return searchEstruturafisicaNome;
	}

	public void setSearchEstruturafisicaNome(String searchEstruturafisicaNome) {
		this.searchEstruturafisicaNome = searchEstruturafisicaNome;
	}

	public String getEstruturafisicaUnidadeorganizacionalSortTipo() {
		return estruturafisicaUnidadeorganizacionalSortTipo;
	}

	public void setEstruturafisicaUnidadeorganizacionalSortTipo(String estruturafisicaUnidadeorganizacionalSortTipo) {
		this.estruturafisicaUnidadeorganizacionalSortTipo = estruturafisicaUnidadeorganizacionalSortTipo;
	}

	public boolean isUnidadeorganizacionalNomeSortAsc() {
		return unidadeorganizacionalNomeSortAsc;
	}

	public void setUnidadeorganizacionalNomeSortAsc(boolean unidadeorganizacionalNomeSortAsc) {
		this.unidadeorganizacionalNomeSortAsc = unidadeorganizacionalNomeSortAsc;
	}

	public boolean isEstruturafisicaNomeSortAsc() {
		return estruturafisicaNomeSortAsc;
	}

	public void setEstruturafisicaNomeSortAsc(boolean estruturafisicaNomeSortAsc) {
		this.estruturafisicaNomeSortAsc = estruturafisicaNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
