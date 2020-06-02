package br.com.j4business.saga.agendaevento.model;

public class AgendaEventoByAgendaForm {

	private String searchEventoNome;
	private String searchAgendaNome;
	private String agendaEventoSortTipo;
	private boolean eventoNomeSortAsc = false ;
	private boolean agendaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public AgendaEventoByAgendaForm() {
		super();
	}

	public String getSearchEventoNome() {
		return searchEventoNome;
	}

	public void setSearchEventoNome(String searchEventoNome) {
		this.searchEventoNome = searchEventoNome;
	}

	public String getSearchAgendaNome() {
		return searchAgendaNome;
	}

	public void setSearchAgendaNome(String searchAgendaNome) {
		this.searchAgendaNome = searchAgendaNome;
	}

	public String getAgendaEventoSortTipo() {
		return agendaEventoSortTipo;
	}

	public void setAgendaEventoSortTipo(String agendaEventoSortTipo) {
		this.agendaEventoSortTipo = agendaEventoSortTipo;
	}

	public boolean isEventoNomeSortAsc() {
		return eventoNomeSortAsc;
	}

	public void setEventoNomeSortAsc(boolean eventoNomeSortAsc) {
		this.eventoNomeSortAsc = eventoNomeSortAsc;
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
