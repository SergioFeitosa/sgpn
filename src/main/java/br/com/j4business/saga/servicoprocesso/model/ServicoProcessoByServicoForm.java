package br.com.j4business.saga.servicoprocesso.model;

public class ServicoProcessoByServicoForm {

	private String searchProcessoNome;
	private String searchServicoNome;
	private String servicoProcessoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean servicoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ServicoProcessoByServicoForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchServicoNome() {
		return searchServicoNome;
	}

	public void setSearchServicoNome(String searchServicoNome) {
		this.searchServicoNome = searchServicoNome;
	}

	public String getServicoProcessoSortTipo() {
		return servicoProcessoSortTipo;
	}

	public void setServicoProcessoSortTipo(String servicoProcessoSortTipo) {
		this.servicoProcessoSortTipo = servicoProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isServicoNomeSortAsc() {
		return servicoNomeSortAsc;
	}

	public void setServicoNomeSortAsc(boolean servicoNomeSortAsc) {
		this.servicoNomeSortAsc = servicoNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
