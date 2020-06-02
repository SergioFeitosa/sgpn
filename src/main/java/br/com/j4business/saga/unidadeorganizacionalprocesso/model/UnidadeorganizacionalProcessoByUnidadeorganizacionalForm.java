package br.com.j4business.saga.unidadeorganizacionalprocesso.model;

public class UnidadeorganizacionalProcessoByUnidadeorganizacionalForm {

	private String searchProcessoNome;
	private String searchUnidadeorganizacionalNome;
	private String unidadeorganizacionalProcessoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean unidadeorganizacionalNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public UnidadeorganizacionalProcessoByUnidadeorganizacionalForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchUnidadeorganizacionalNome() {
		return searchUnidadeorganizacionalNome;
	}

	public void setSearchUnidadeorganizacionalNome(String searchUnidadeorganizacionalNome) {
		this.searchUnidadeorganizacionalNome = searchUnidadeorganizacionalNome;
	}

	public String getUnidadeorganizacionalProcessoSortTipo() {
		return unidadeorganizacionalProcessoSortTipo;
	}

	public void setUnidadeorganizacionalProcessoSortTipo(String unidadeorganizacionalProcessoSortTipo) {
		this.unidadeorganizacionalProcessoSortTipo = unidadeorganizacionalProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
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
