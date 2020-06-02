package br.com.j4business.saga.processotexto.model;

public class ProcessoTextoByProcessoForm {

	private String searchTextoNome;
	private String searchProcessoNome;
	private String searchStatus;
	private String processoTextoSortTipo;
	private boolean textoNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public ProcessoTextoByProcessoForm() {
		super();
	}

	public String getSearchTextoNome() {
		return searchTextoNome;
	}

	public void setSearchTextoNome(String searchTextoNome) {
		this.searchTextoNome = searchTextoNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoTextoSortTipo() {
		return processoTextoSortTipo;
	}

	public void setProcessoTextoSortTipo(String processoTextoSortTipo) {
		this.processoTextoSortTipo = processoTextoSortTipo;
	}

	public boolean isTextoNomeSortAsc() {
		return textoNomeSortAsc;
	}

	public void setTextoNomeSortAsc(boolean textoNomeSortAsc) {
		this.textoNomeSortAsc = textoNomeSortAsc;
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
