package br.com.j4business.saga.processo.model;

public class ProcessoByProcessoForm {

	private String searchProcessoNome;
	private String searchProcessoDescricao;
	private String processoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean processoDescricaoSortAsc = false ;
	
	public ProcessoByProcessoForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchProcessoDescricao() {
		return searchProcessoDescricao;
	}

	public void setSearchProcessoDescricao(String searchProcessoDescricao) {
		this.searchProcessoDescricao = searchProcessoDescricao;
	}

	public String getProcessoSortTipo() {
		return processoSortTipo;
	}

	public void setProcessoSortTipo(String processoSortTipo) {
		this.processoSortTipo = processoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isProcessoDescricaoSortAsc() {
		return processoDescricaoSortAsc;
	}

	public void setProcessoDescricaoSortAsc(boolean processoDescricaoSortAsc) {
		this.processoDescricaoSortAsc = processoDescricaoSortAsc;
	}

}
