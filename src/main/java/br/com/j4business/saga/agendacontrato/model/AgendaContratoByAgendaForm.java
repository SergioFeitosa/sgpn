package br.com.j4business.saga.agendacontrato.model;

public class AgendaContratoByAgendaForm {

	private String searchContratoNome;
	private String searchAgendaNome;
	private String agendaContratoSortTipo;
	private boolean contratoNomeSortAsc = false ;
	private boolean agendaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public AgendaContratoByAgendaForm() {
		super();
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getSearchAgendaNome() {
		return searchAgendaNome;
	}

	public void setSearchAgendaNome(String searchAgendaNome) {
		this.searchAgendaNome = searchAgendaNome;
	}

	public String getAgendaContratoSortTipo() {
		return agendaContratoSortTipo;
	}

	public void setAgendaContratoSortTipo(String agendaContratoSortTipo) {
		this.agendaContratoSortTipo = agendaContratoSortTipo;
	}

	public boolean isContratoNomeSortAsc() {
		return contratoNomeSortAsc;
	}

	public void setContratoNomeSortAsc(boolean contratoNomeSortAsc) {
		this.contratoNomeSortAsc = contratoNomeSortAsc;
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
