package br.com.j4business.saga.treinamentotexto.model;

public class TreinamentoTextoByTreinamentoForm {

	private String searchTextoNome;
	private String searchTreinamentoNome;
	private String searchStatus;
	private String treinamentoTextoSortTipo;
	private boolean textoNomeSortAsc = false ;
	private boolean treinamentoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public TreinamentoTextoByTreinamentoForm() {
		super();
	}

	public String getSearchTextoNome() {
		return searchTextoNome;
	}

	public void setSearchTextoNome(String searchTextoNome) {
		this.searchTextoNome = searchTextoNome;
	}

	public String getSearchTreinamentoNome() {
		return searchTreinamentoNome;
	}

	public void setSearchTreinamentoNome(String searchTreinamentoNome) {
		this.searchTreinamentoNome = searchTreinamentoNome;
	}

	public String getTreinamentoTextoSortTipo() {
		return treinamentoTextoSortTipo;
	}

	public void setTreinamentoTextoSortTipo(String treinamentoTextoSortTipo) {
		this.treinamentoTextoSortTipo = treinamentoTextoSortTipo;
	}

	public boolean isTextoNomeSortAsc() {
		return textoNomeSortAsc;
	}

	public void setTextoNomeSortAsc(boolean textoNomeSortAsc) {
		this.textoNomeSortAsc = textoNomeSortAsc;
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
