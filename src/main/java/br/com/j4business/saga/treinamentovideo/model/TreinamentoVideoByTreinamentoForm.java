package br.com.j4business.saga.treinamentovideo.model;

public class TreinamentoVideoByTreinamentoForm {

	private String searchVideoNome;
	private String searchTreinamentoNome;
	private String searchStatus;
	private String treinamentoVideoSortTipo;
	private boolean videoNomeSortAsc = false ;
	private boolean treinamentoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public TreinamentoVideoByTreinamentoForm() {
		super();
	}

	public String getSearchVideoNome() {
		return searchVideoNome;
	}

	public void setSearchVideoNome(String searchVideoNome) {
		this.searchVideoNome = searchVideoNome;
	}

	public String getSearchTreinamentoNome() {
		return searchTreinamentoNome;
	}

	public void setSearchTreinamentoNome(String searchTreinamentoNome) {
		this.searchTreinamentoNome = searchTreinamentoNome;
	}

	public String getTreinamentoVideoSortTipo() {
		return treinamentoVideoSortTipo;
	}

	public void setTreinamentoVideoSortTipo(String treinamentoVideoSortTipo) {
		this.treinamentoVideoSortTipo = treinamentoVideoSortTipo;
	}

	public boolean isVideoNomeSortAsc() {
		return videoNomeSortAsc;
	}

	public void setVideoNomeSortAsc(boolean videoNomeSortAsc) {
		this.videoNomeSortAsc = videoNomeSortAsc;
	}

	public boolean isTreinamentoNomeSortAsc() {
		return treinamentoNomeSortAsc;
	}

	public void setTreinamentoNomeSortAsc(boolean treinamentoNomeSortAsc) {
		this.treinamentoNomeSortAsc = treinamentoNomeSortAsc;
	}

	public boolean isStatusSortAsc() {
		return statusSortAsc;
	}

	public void setStatusSortAsc(boolean statusSortAsc) {
		this.statusSortAsc = statusSortAsc;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}


}
