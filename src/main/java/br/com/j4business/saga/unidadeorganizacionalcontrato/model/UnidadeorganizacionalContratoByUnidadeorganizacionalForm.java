package br.com.j4business.saga.unidadeorganizacionalcontrato.model;

public class UnidadeorganizacionalContratoByUnidadeorganizacionalForm {

	private String searchContratoNome;
	private String searchUnidadeorganizacionalNome;
	private String unidadeorganizacionalContratoSortTipo;
	private boolean contratoNomeSortAsc = false ;
	private boolean unidadeorganizacionalNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public UnidadeorganizacionalContratoByUnidadeorganizacionalForm() {
		super();
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getSearchUnidadeorganizacionalNome() {
		return searchUnidadeorganizacionalNome;
	}

	public void setSearchUnidadeorganizacionalNome(String searchUnidadeorganizacionalNome) {
		this.searchUnidadeorganizacionalNome = searchUnidadeorganizacionalNome;
	}

	public String getUnidadeorganizacionalContratoSortTipo() {
		return unidadeorganizacionalContratoSortTipo;
	}

	public void setUnidadeorganizacionalContratoSortTipo(String unidadeorganizacionalContratoSortTipo) {
		this.unidadeorganizacionalContratoSortTipo = unidadeorganizacionalContratoSortTipo;
	}

	public boolean isContratoNomeSortAsc() {
		return contratoNomeSortAsc;
	}

	public void setContratoNomeSortAsc(boolean contratoNomeSortAsc) {
		this.contratoNomeSortAsc = contratoNomeSortAsc;
	}

	public boolean isUnidadeorganizacionalNomeSortAsc() {
		return unidadeorganizacionalNomeSortAsc;
	}

	public void setUnidadeorganizacionalNomeSortAsc(boolean unidadeorganizacionalNomeSortAsc) {
		this.unidadeorganizacionalNomeSortAsc = unidadeorganizacionalNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
