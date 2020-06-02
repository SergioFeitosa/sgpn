package br.com.j4business.saga.estado.model;

public class EstadoByEstadoForm {

	private String searchEstadoNome;
	private String searchEstadoSigla;
	private String estadoSortTipo;
	private boolean estadoNomeSortAsc = false ;
	private boolean estadoSiglaSortAsc = false ;
	
	public EstadoByEstadoForm() {
		super();
	}

	public String getSearchEstadoNome() {
		return searchEstadoNome;
	}

	public void setSearchEstadoNome(String searchEstadoNome) {
		this.searchEstadoNome = searchEstadoNome;
	}

	public String getSearchEstadoSigla() {
		return searchEstadoSigla;
	}

	public void setSearchEstadoSigla(String searchEstadoSigla) {
		this.searchEstadoSigla = searchEstadoSigla;
	}

	public String getEstadoSortTipo() {
		return estadoSortTipo;
	}

	public void setEstadoSortTipo(String estadoSortTipo) {
		this.estadoSortTipo = estadoSortTipo;
	}

	public boolean isEstadoNomeSortAsc() {
		return estadoNomeSortAsc;
	}

	public void setEstadoNomeSortAsc(boolean estadoNomeSortAsc) {
		this.estadoNomeSortAsc = estadoNomeSortAsc;
	}

	public boolean isEstadoSiglaSortAsc() {
		return estadoSiglaSortAsc;
	}

	public void setEstadoSiglaSortAsc(boolean estadoSiglaSortAsc) {
		this.estadoSiglaSortAsc = estadoSiglaSortAsc;
	}

}
