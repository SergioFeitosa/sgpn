package br.com.j4business.saga.cenarioelemento.model;

public class CenarioElementoByCenarioForm {

	private String searchElementoNome;
	private String searchCenarioNome;
	private String cenarioElementoSortTipo;
	private boolean elementoNomeSortAsc = false ;
	private boolean cenarioNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public CenarioElementoByCenarioForm() {
		super();
	}

	public String getSearchElementoNome() {
		return searchElementoNome;
	}

	public void setSearchElementoNome(String searchElementoNome) {
		this.searchElementoNome = searchElementoNome;
	}

	public String getSearchCenarioNome() {
		return searchCenarioNome;
	}

	public void setSearchCenarioNome(String searchCenarioNome) {
		this.searchCenarioNome = searchCenarioNome;
	}

	public String getCenarioElementoSortTipo() {
		return cenarioElementoSortTipo;
	}

	public void setCenarioElementoSortTipo(String cenarioElementoSortTipo) {
		this.cenarioElementoSortTipo = cenarioElementoSortTipo;
	}

	public boolean isElementoNomeSortAsc() {
		return elementoNomeSortAsc;
	}

	public void setElementoNomeSortAsc(boolean elementoNomeSortAsc) {
		this.elementoNomeSortAsc = elementoNomeSortAsc;
	}

	public boolean isCenarioNomeSortAsc() {
		return cenarioNomeSortAsc;
	}

	public void setCenarioNomeSortAsc(boolean cenarioNomeSortAsc) {
		this.cenarioNomeSortAsc = cenarioNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
