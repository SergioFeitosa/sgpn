package br.com.j4business.saga.treinamentoimagem.model;

public class TreinamentoImagemByTreinamentoForm {

	private String searchImagemNome;
	private String searchTreinamentoNome;
	private String searchStatus;
	private String treinamentoImagemSortTipo;
	private boolean imagemNomeSortAsc = false ;
	private boolean treinamentoNomeSortAsc = false ;
	private boolean statusSortAsc = false ;

	
	
	public TreinamentoImagemByTreinamentoForm() {
		super();
	}

	public String getSearchImagemNome() {
		return searchImagemNome;
	}

	public void setSearchImagemNome(String searchImagemNome) {
		this.searchImagemNome = searchImagemNome;
	}

	public String getSearchTreinamentoNome() {
		return searchTreinamentoNome;
	}

	public void setSearchTreinamentoNome(String searchTreinamentoNome) {
		this.searchTreinamentoNome = searchTreinamentoNome;
	}

	public String getTreinamentoImagemSortTipo() {
		return treinamentoImagemSortTipo;
	}

	public void setTreinamentoImagemSortTipo(String treinamentoImagemSortTipo) {
		this.treinamentoImagemSortTipo = treinamentoImagemSortTipo;
	}

	public boolean isImagemNomeSortAsc() {
		return imagemNomeSortAsc;
	}

	public void setImagemNomeSortAsc(boolean imagemNomeSortAsc) {
		this.imagemNomeSortAsc = imagemNomeSortAsc;
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
