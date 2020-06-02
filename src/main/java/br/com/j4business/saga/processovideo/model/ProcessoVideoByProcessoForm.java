package br.com.j4business.saga.processovideo.model;

public class ProcessoVideoByProcessoForm {

	private String searchVideoNome;
	private String searchProcessoNome;
	private String searchStatus;
	private String processoVideoSortTipo;
	private boolean videoNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public ProcessoVideoByProcessoForm() {
		super();
	}

	public String getSearchVideoNome() {
		return searchVideoNome;
	}

	public void setSearchVideoNome(String searchVideoNome) {
		this.searchVideoNome = searchVideoNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoVideoSortTipo() {
		return processoVideoSortTipo;
	}

	public void setProcessoVideoSortTipo(String processoVideoSortTipo) {
		this.processoVideoSortTipo = processoVideoSortTipo;
	}

	public boolean isVideoNomeSortAsc() {
		return videoNomeSortAsc;
	}

	public void setVideoNomeSortAsc(boolean videoNomeSortAsc) {
		this.videoNomeSortAsc = videoNomeSortAsc;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
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
