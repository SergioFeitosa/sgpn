package br.com.j4business.saga.unidadeorganizacionalcenario.model;

public class UnidadeorganizacionalCenarioByUnidadeorganizacionalForm {

	private String searchCenarioNome;
	private String searchUnidadeorganizacionalNome;
	private String unidadeorganizacionalCenarioSortTipo;
	private boolean cenarioNomeSortAsc = false ;
	private boolean unidadeorganizacionalNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public UnidadeorganizacionalCenarioByUnidadeorganizacionalForm() {
		super();
	}

	public String getSearchCenarioNome() {
		return searchCenarioNome;
	}

	public void setSearchCenarioNome(String searchCenarioNome) {
		this.searchCenarioNome = searchCenarioNome;
	}

	public String getSearchUnidadeorganizacionalNome() {
		return searchUnidadeorganizacionalNome;
	}

	public void setSearchUnidadeorganizacionalNome(String searchUnidadeorganizacionalNome) {
		this.searchUnidadeorganizacionalNome = searchUnidadeorganizacionalNome;
	}

	public String getUnidadeorganizacionalCenarioSortTipo() {
		return unidadeorganizacionalCenarioSortTipo;
	}

	public void setUnidadeorganizacionalCenarioSortTipo(String unidadeorganizacionalCenarioSortTipo) {
		this.unidadeorganizacionalCenarioSortTipo = unidadeorganizacionalCenarioSortTipo;
	}

	public boolean isCenarioNomeSortAsc() {
		return cenarioNomeSortAsc;
	}

	public void setCenarioNomeSortAsc(boolean cenarioNomeSortAsc) {
		this.cenarioNomeSortAsc = cenarioNomeSortAsc;
	}

	public boolean isUnidadeorganizacionalNomeSortAsc() {
		return unidadeorganizacionalNomeSortAsc;
	}

	public void setUnidadeorganizacionalNomeSortAsc(boolean unidadeorganizacionalNomeSortAsc) {
		this.unidadeorganizacionalNomeSortAsc = unidadeorganizacionalNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
