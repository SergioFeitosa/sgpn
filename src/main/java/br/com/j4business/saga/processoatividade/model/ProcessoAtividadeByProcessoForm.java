package br.com.j4business.saga.processoatividade.model;

public class ProcessoAtividadeByProcessoForm {

	private String searchProcessoNome;
	private String searchAtividadeNome;
	private String processoAtividadeSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean atividadeNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ProcessoAtividadeByProcessoForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchAtividadeNome() {
		return searchAtividadeNome;
	}

	public void setSearchAtividadeNome(String searchAtividadeNome) {
		this.searchAtividadeNome = searchAtividadeNome;
	}

	public String getProcessoAtividadeSortTipo() {
		return processoAtividadeSortTipo;
	}

	public void setProcessoAtividadeSortTipo(String processoAtividadeSortTipo) {
		this.processoAtividadeSortTipo = processoAtividadeSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isAtividadeNomeSortAsc() {
		return atividadeNomeSortAsc;
	}

	public void setAtividadeNomeSortAsc(boolean atividadeNomeSortAsc) {
		this.atividadeNomeSortAsc = atividadeNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
