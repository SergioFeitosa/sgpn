package br.com.j4business.saga.planejamentoacao.model;

public class PlanejamentoAcaoByPlanejamentoForm {

	private String searchPlanejamentoNome;
	private String searchAcaoNome;
	private String planejamentoAcaoSortTipo;
	private boolean planejamentoNomeSortAsc = false ;
	private boolean acaoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public PlanejamentoAcaoByPlanejamentoForm() {
		super();
	}

	public String getSearchPlanejamentoNome() {
		return searchPlanejamentoNome;
	}

	public void setSearchPlanejamentoNome(String searchPlanejamentoNome) {
		this.searchPlanejamentoNome = searchPlanejamentoNome;
	}

	public String getSearchAcaoNome() {
		return searchAcaoNome;
	}

	public void setSearchAcaoNome(String searchAcaoNome) {
		this.searchAcaoNome = searchAcaoNome;
	}

	public String getPlanejamentoAcaoSortTipo() {
		return planejamentoAcaoSortTipo;
	}

	public void setPlanejamentoAcaoSortTipo(String planejamentoAcaoSortTipo) {
		this.planejamentoAcaoSortTipo = planejamentoAcaoSortTipo;
	}

	public boolean isPlanejamentoNomeSortAsc() {
		return planejamentoNomeSortAsc;
	}

	public void setPlanejamentoNomeSortAsc(boolean planejamentoNomeSortAsc) {
		this.planejamentoNomeSortAsc = planejamentoNomeSortAsc;
	}

	public boolean isAcaoNomeSortAsc() {
		return acaoNomeSortAsc;
	}

	public void setAcaoNomeSortAsc(boolean acaoNomeSortAsc) {
		this.acaoNomeSortAsc = acaoNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
