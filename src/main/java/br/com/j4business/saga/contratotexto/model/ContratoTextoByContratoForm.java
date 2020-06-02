package br.com.j4business.saga.contratotexto.model;

public class ContratoTextoByContratoForm {

	private String searchTextoNome;
	private String searchContratoNome;
	private String searchStatus;
	private String contratoTextoSortTipo;
	private boolean textoNomeSortAsc = false ;
	private boolean contratoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public ContratoTextoByContratoForm() {
		super();
	}

	public String getSearchTextoNome() {
		return searchTextoNome;
	}

	public void setSearchTextoNome(String searchTextoNome) {
		this.searchTextoNome = searchTextoNome;
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getContratoTextoSortTipo() {
		return contratoTextoSortTipo;
	}

	public void setContratoTextoSortTipo(String contratoTextoSortTipo) {
		this.contratoTextoSortTipo = contratoTextoSortTipo;
	}

	public boolean isTextoNomeSortAsc() {
		return textoNomeSortAsc;
	}

	public void setTextoNomeSortAsc(boolean textoNomeSortAsc) {
		this.textoNomeSortAsc = textoNomeSortAsc;
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
