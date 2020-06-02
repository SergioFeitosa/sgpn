package br.com.j4business.saga.municipio.model;

public class MunicipioByMunicipioForm {

	private String searchMunicipioNome;
	private String searchMunicipioUF;
	private String searchMunicipioCEP;
	private String municipioSortTipo;
	private boolean municipioNomeSortAsc = false ;
	private boolean municipioCEPSortAsc = false ;
	
	public MunicipioByMunicipioForm() {
		super();
	}

	public String getSearchMunicipioNome() {
		return searchMunicipioNome;
	}

	public void setSearchMunicipioNome(String searchMunicipioNome) {
		this.searchMunicipioNome = searchMunicipioNome;
	}

	public String getSearchMunicipioCEP() {
		return searchMunicipioCEP;
	}

	public void setSearchMunicipioCEP(String searchMunicipioCEP) {
		this.searchMunicipioCEP = searchMunicipioCEP;
	}

	public String getMunicipioSortTipo() {
		return municipioSortTipo;
	}

	public void setMunicipioSortTipo(String municipioSortTipo) {
		this.municipioSortTipo = municipioSortTipo;
	}

	public boolean isMunicipioNomeSortAsc() {
		return municipioNomeSortAsc;
	}

	public void setMunicipioNomeSortAsc(boolean municipioNomeSortAsc) {
		this.municipioNomeSortAsc = municipioNomeSortAsc;
	}

	public boolean isMunicipioCEPSortAsc() {
		return municipioCEPSortAsc;
	}

	public void setMunicipioCEPSortAsc(boolean municipioCEPSortAsc) {
		this.municipioCEPSortAsc = municipioCEPSortAsc;
	}

	public String getSearchMunicipioUF() {
		return searchMunicipioUF;
	}

	public void setSearchMunicipioUF(String searchMunicipioUF) {
		this.searchMunicipioUF = searchMunicipioUF;
	}

}
