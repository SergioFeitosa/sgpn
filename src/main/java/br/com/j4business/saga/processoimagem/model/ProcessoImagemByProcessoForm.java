package br.com.j4business.saga.processoimagem.model;

public class ProcessoImagemByProcessoForm {

	private String searchImagemNome;
	private String searchProcessoNome;
	private String searchStatus;
	private String processoImagemSortTipo;
	private boolean imagemNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public ProcessoImagemByProcessoForm() {
		super();
	}

	public String getSearchImagemNome() {
		return searchImagemNome;
	}

	public void setSearchImagemNome(String searchImagemNome) {
		this.searchImagemNome = searchImagemNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoImagemSortTipo() {
		return processoImagemSortTipo;
	}

	public void setProcessoImagemSortTipo(String processoImagemSortTipo) {
		this.processoImagemSortTipo = processoImagemSortTipo;
	}

	public boolean isImagemNomeSortAsc() {
		return imagemNomeSortAsc;
	}

	public void setImagemNomeSortAsc(boolean imagemNomeSortAsc) {
		this.imagemNomeSortAsc = imagemNomeSortAsc;
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
