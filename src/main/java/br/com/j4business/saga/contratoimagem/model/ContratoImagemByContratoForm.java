package br.com.j4business.saga.contratoimagem.model;

public class ContratoImagemByContratoForm {

	private String searchImagemNome;
	private String searchContratoNome;
	private String searchStatus;
	private String contratoImagemSortTipo;
	private boolean imagemNomeSortAsc = false ;
	private boolean contratoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public ContratoImagemByContratoForm() {
		super();
	}

	public String getSearchImagemNome() {
		return searchImagemNome;
	}

	public void setSearchImagemNome(String searchImagemNome) {
		this.searchImagemNome = searchImagemNome;
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getContratoImagemSortTipo() {
		return contratoImagemSortTipo;
	}

	public void setContratoImagemSortTipo(String contratoImagemSortTipo) {
		this.contratoImagemSortTipo = contratoImagemSortTipo;
	}

	public boolean isImagemNomeSortAsc() {
		return imagemNomeSortAsc;
	}

	public void setImagemNomeSortAsc(boolean imagemNomeSortAsc) {
		this.imagemNomeSortAsc = imagemNomeSortAsc;
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
