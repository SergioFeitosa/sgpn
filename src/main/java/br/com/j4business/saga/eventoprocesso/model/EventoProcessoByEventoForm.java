package br.com.j4business.saga.eventoprocesso.model;

public class EventoProcessoByEventoForm {

	private String searchProcessoNome;
	private String searchEventoNome;
	private String eventoProcessoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean eventoNomeSortAsc = false ;
	
	
	public EventoProcessoByEventoForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchEventoNome() {
		return searchEventoNome;
	}

	public void setSearchEventoNome(String searchEventoNome) {
		this.searchEventoNome = searchEventoNome;
	}

	public String getEventoProcessoSortTipo() {
		return eventoProcessoSortTipo;
	}

	public void setEventoProcessoSortTipo(String eventoProcessoSortTipo) {
		this.eventoProcessoSortTipo = eventoProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isEventoNomeSortAsc() {
		return eventoNomeSortAsc;
	}

	public void setEventoNomeSortAsc(boolean eventoNomeSortAsc) {
		this.eventoNomeSortAsc = eventoNomeSortAsc;
	}


}
