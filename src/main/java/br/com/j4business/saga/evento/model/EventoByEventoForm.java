package br.com.j4business.saga.evento.model;

public class EventoByEventoForm {

	private String searchEventoNome;
	private String searchEventoDescricao;
	private String eventoSortTipo;
	private boolean eventoNomeSortAsc = false ;
	private boolean eventoDescricaoSortAsc = false ;
	
	public EventoByEventoForm() {
		super();
	}

	public String getSearchEventoNome() {
		return searchEventoNome;
	}

	public void setSearchEventoNome(String searchEventoNome) {
		this.searchEventoNome = searchEventoNome;
	}

	public String getSearchEventoDescricao() {
		return searchEventoDescricao;
	}

	public void setSearchEventoDescricao(String searchEventoDescricao) {
		this.searchEventoDescricao = searchEventoDescricao;
	}

	public String getEventoSortTipo() {
		return eventoSortTipo;
	}

	public void setEventoSortTipo(String eventoSortTipo) {
		this.eventoSortTipo = eventoSortTipo;
	}

	public boolean isEventoNomeSortAsc() {
		return eventoNomeSortAsc;
	}

	public void setEventoNomeSortAsc(boolean eventoNomeSortAsc) {
		this.eventoNomeSortAsc = eventoNomeSortAsc;
	}

	public boolean isEventoDescricaoSortAsc() {
		return eventoDescricaoSortAsc;
	}

	public void setEventoDescricaoSortAsc(boolean eventoDescricaoSortAsc) {
		this.eventoDescricaoSortAsc = eventoDescricaoSortAsc;
	}

}
