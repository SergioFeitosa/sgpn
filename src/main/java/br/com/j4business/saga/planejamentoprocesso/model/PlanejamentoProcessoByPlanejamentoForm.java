package br.com.j4business.saga.planejamentoprocesso.model;

public class PlanejamentoProcessoByPlanejamentoForm {

	private String searchProcessoNome;
	private String searchPlanejamentoNome;
	private String planejamentoProcessoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean planejamentoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public PlanejamentoProcessoByPlanejamentoForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchPlanejamentoNome() {
		return searchPlanejamentoNome;
	}

	public void setSearchPlanejamentoNome(String searchPlanejamentoNome) {
		this.searchPlanejamentoNome = searchPlanejamentoNome;
	}

	public String getPlanejamentoProcessoSortTipo() {
		return planejamentoProcessoSortTipo;
	}

	public void setPlanejamentoProcessoSortTipo(String planejamentoProcessoSortTipo) {
		this.planejamentoProcessoSortTipo = planejamentoProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isPlanejamentoNomeSortAsc() {
		return planejamentoNomeSortAsc;
	}

	public void setPlanejamentoNomeSortAsc(boolean planejamentoNomeSortAsc) {
		this.planejamentoNomeSortAsc = planejamentoNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
