package br.com.j4business.saga.cenario.model;

public class CenarioByCenarioForm {

	private String searchCenarioNome;
	private String searchCenarioDescricao;
	private String cenarioSortTipo;
	private boolean cenarioNomeSortAsc = false ;
	private boolean cenarioDescricaoSortAsc = false ;
	
	public CenarioByCenarioForm() {
		super();
	}

	public String getSearchCenarioNome() {
		return searchCenarioNome;
	}

	public void setSearchCenarioNome(String searchCenarioNome) {
		this.searchCenarioNome = searchCenarioNome;
	}

	public String getSearchCenarioDescricao() {
		return searchCenarioDescricao;
	}

	public void setSearchCenarioDescricao(String searchCenarioDescricao) {
		this.searchCenarioDescricao = searchCenarioDescricao;
	}

	public String getCenarioSortTipo() {
		return cenarioSortTipo;
	}

	public void setCenarioSortTipo(String cenarioSortTipo) {
		this.cenarioSortTipo = cenarioSortTipo;
	}

	public boolean isCenarioNomeSortAsc() {
		return cenarioNomeSortAsc;
	}

	public void setCenarioNomeSortAsc(boolean cenarioNomeSortAsc) {
		this.cenarioNomeSortAsc = cenarioNomeSortAsc;
	}

	public boolean isCenarioDescricaoSortAsc() {
		return cenarioDescricaoSortAsc;
	}

	public void setCenarioDescricaoSortAsc(boolean cenarioDescricaoSortAsc) {
		this.cenarioDescricaoSortAsc = cenarioDescricaoSortAsc;
	}

}
