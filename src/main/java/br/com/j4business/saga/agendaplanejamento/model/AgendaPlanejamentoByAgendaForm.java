package br.com.j4business.saga.agendaplanejamento.model;

public class AgendaPlanejamentoByAgendaForm {

	private String searchPlanejamentoNome;
	private String searchAgendaNome;
	private String agendaPlanejamentoSortTipo;
	private boolean planejamentoNomeSortAsc = false ;
	private boolean agendaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public AgendaPlanejamentoByAgendaForm() {
		super();
	}

	public String getSearchPlanejamentoNome() {
		return searchPlanejamentoNome;
	}

	public void setSearchPlanejamentoNome(String searchPlanejamentoNome) {
		this.searchPlanejamentoNome = searchPlanejamentoNome;
	}

	public String getSearchAgendaNome() {
		return searchAgendaNome;
	}

	public void setSearchAgendaNome(String searchAgendaNome) {
		this.searchAgendaNome = searchAgendaNome;
	}

	public String getAgendaPlanejamentoSortTipo() {
		return agendaPlanejamentoSortTipo;
	}

	public void setAgendaPlanejamentoSortTipo(String agendaPlanejamentoSortTipo) {
		this.agendaPlanejamentoSortTipo = agendaPlanejamentoSortTipo;
	}

	public boolean isPlanejamentoNomeSortAsc() {
		return planejamentoNomeSortAsc;
	}

	public void setPlanejamentoNomeSortAsc(boolean planejamentoNomeSortAsc) {
		this.planejamentoNomeSortAsc = planejamentoNomeSortAsc;
	}

	public boolean isAgendaNomeSortAsc() {
		return agendaNomeSortAsc;
	}

	public void setAgendaNomeSortAsc(boolean agendaNomeSortAsc) {
		this.agendaNomeSortAsc = agendaNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
