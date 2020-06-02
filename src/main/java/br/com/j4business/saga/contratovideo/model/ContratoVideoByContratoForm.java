package br.com.j4business.saga.contratovideo.model;

public class ContratoVideoByContratoForm {

	private String searchVideoNome;
	private String searchContratoNome;
	private String searchStatus;
	private String contratoVideoSortTipo;
	private boolean videoNomeSortAsc = false ;
	private boolean contratoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public ContratoVideoByContratoForm() {
		super();
	}

	public String getSearchVideoNome() {
		return searchVideoNome;
	}

	public void setSearchVideoNome(String searchVideoNome) {
		this.searchVideoNome = searchVideoNome;
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getContratoVideoSortTipo() {
		return contratoVideoSortTipo;
	}

	public void setContratoVideoSortTipo(String contratoVideoSortTipo) {
		this.contratoVideoSortTipo = contratoVideoSortTipo;
	}

	public boolean isVideoNomeSortAsc() {
		return videoNomeSortAsc;
	}

	public void setVideoNomeSortAsc(boolean videoNomeSortAsc) {
		this.videoNomeSortAsc = videoNomeSortAsc;
	}

	public boolean isContratoNomeSortAsc() {
		return contratoNomeSortAsc;
	}

	public void setContratoNomeSortAsc(boolean contratoNomeSortAsc) {
		this.contratoNomeSortAsc = contratoNomeSortAsc;
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
