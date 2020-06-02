package br.com.j4business.saga.agenda.model;

public class AgendaByAgendaForm {

	private String searchAgendaNome;
	private String searchAgendaDescricao;
	private String agendaSortTipo;
	private boolean agendaNomeSortAsc = false ;
	private boolean agendaDescricaoSortAsc = false ;
	
	public AgendaByAgendaForm() {
		super();
	}

	public String getSearchAgendaNome() {
		return searchAgendaNome;
	}

	public void setSearchAgendaNome(String searchAgendaNome) {
		this.searchAgendaNome = searchAgendaNome;
	}

	public String getSearchAgendaDescricao() {
		return searchAgendaDescricao;
	}

	public void setSearchAgendaDescricao(String searchAgendaDescricao) {
		this.searchAgendaDescricao = searchAgendaDescricao;
	}

	public String getAgendaSortTipo() {
		return agendaSortTipo;
	}

	public void setAgendaSortTipo(String agendaSortTipo) {
		this.agendaSortTipo = agendaSortTipo;
	}

	public boolean isAgendaNomeSortAsc() {
		return agendaNomeSortAsc;
	}

	public void setAgendaNomeSortAsc(boolean agendaNomeSortAsc) {
		this.agendaNomeSortAsc = agendaNomeSortAsc;
	}

	public boolean isAgendaDescricaoSortAsc() {
		return agendaDescricaoSortAsc;
	}

	public void setAgendaDescricaoSortAsc(boolean agendaDescricaoSortAsc) {
		this.agendaDescricaoSortAsc = agendaDescricaoSortAsc;
	}

}
