package br.com.j4business.saga.colaboradorcertificacao.model;

public class ColaboradorCertificacaoByColaboradorForm {

	private String searchCertificacaoNome;
	private String searchColaboradorNome;
	private String colaboradorCertificacaoSortTipo;
	private boolean certificacaoNomeSortAsc = false ;
	private boolean colaboradorNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ColaboradorCertificacaoByColaboradorForm() {
		super();
	}

	public String getSearchCertificacaoNome() {
		return searchCertificacaoNome;
	}

	public void setSearchCertificacaoNome(String searchCertificacaoNome) {
		this.searchCertificacaoNome = searchCertificacaoNome;
	}

	public String getSearchColaboradorNome() {
		return searchColaboradorNome;
	}

	public void setSearchColaboradorNome(String searchColaboradorNome) {
		this.searchColaboradorNome = searchColaboradorNome;
	}

	public String getColaboradorCertificacaoSortTipo() {
		return colaboradorCertificacaoSortTipo;
	}

	public void setColaboradorCertificacaoSortTipo(String colaboradorCertificacaoSortTipo) {
		this.colaboradorCertificacaoSortTipo = colaboradorCertificacaoSortTipo;
	}

	public boolean isCertificacaoNomeSortAsc() {
		return certificacaoNomeSortAsc;
	}

	public void setCertificacaoNomeSortAsc(boolean certificacaoNomeSortAsc) {
		this.certificacaoNomeSortAsc = certificacaoNomeSortAsc;
	}

	public boolean isColaboradorNomeSortAsc() {
		return colaboradorNomeSortAsc;
	}

	public void setColaboradorNomeSortAsc(boolean colaboradorNomeSortAsc) {
		this.colaboradorNomeSortAsc = colaboradorNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
