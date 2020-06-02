package br.com.j4business.saga.processoformacao.model;

public class ProcessoFormacaoByProcessoForm {

	private String searchFormacaoNome;
	private String searchProcessoNome;
	private String processoFormacaoSortTipo;
	private boolean formacaoNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ProcessoFormacaoByProcessoForm() {
		super();
	}

	public String getSearchFormacaoNome() {
		return searchFormacaoNome;
	}

	public void setSearchFormacaoNome(String searchFormacaoNome) {
		this.searchFormacaoNome = searchFormacaoNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoFormacaoSortTipo() {
		return processoFormacaoSortTipo;
	}

	public void setProcessoFormacaoSortTipo(String processoFormacaoSortTipo) {
		this.processoFormacaoSortTipo = processoFormacaoSortTipo;
	}

	public boolean isFormacaoNomeSortAsc() {
		return formacaoNomeSortAsc;
	}

	public void setFormacaoNomeSortAsc(boolean formacaoNomeSortAsc) {
		this.formacaoNomeSortAsc = formacaoNomeSortAsc;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
