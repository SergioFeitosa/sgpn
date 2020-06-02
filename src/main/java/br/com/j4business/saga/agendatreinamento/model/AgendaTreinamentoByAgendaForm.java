package br.com.j4business.saga.agendatreinamento.model;

public class AgendaTreinamentoByAgendaForm {

	private String searchTreinamentoNome;
	private String searchAgendaNome;
	private String agendaTreinamentoSortTipo;
	private boolean treinamentoNomeSortAsc = false ;
	private boolean agendaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public AgendaTreinamentoByAgendaForm() {
		super();
	}

	public String getSearchTreinamentoNome() {
		return searchTreinamentoNome;
	}

	public void setSearchTreinamentoNome(String searchTreinamentoNome) {
		this.searchTreinamentoNome = searchTreinamentoNome;
	}

	public String getSearchAgendaNome() {
		return searchAgendaNome;
	}

	public void setSearchAgendaNome(String searchAgendaNome) {
		this.searchAgendaNome = searchAgendaNome;
	}

	public String getAgendaTreinamentoSortTipo() {
		return agendaTreinamentoSortTipo;
	}

	public void setAgendaTreinamentoSortTipo(String agendaTreinamentoSortTipo) {
		this.agendaTreinamentoSortTipo = agendaTreinamentoSortTipo;
	}

	public boolean isTreinamentoNomeSortAsc() {
		return treinamentoNomeSortAsc;
	}

	public void setTreinamentoNomeSortAsc(boolean treinamentoNomeSortAsc) {
		this.treinamentoNomeSortAsc = treinamentoNomeSortAsc;
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
