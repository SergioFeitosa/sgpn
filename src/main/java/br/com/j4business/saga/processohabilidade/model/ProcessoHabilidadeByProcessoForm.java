package br.com.j4business.saga.processohabilidade.model;

public class ProcessoHabilidadeByProcessoForm {

	private String searchHabilidadeNome;
	private String searchProcessoNome;
	private String processoHabilidadeSortTipo;
	private boolean habilidadeNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ProcessoHabilidadeByProcessoForm() {
		super();
	}

	public String getSearchHabilidadeNome() {
		return searchHabilidadeNome;
	}

	public void setSearchHabilidadeNome(String searchHabilidadeNome) {
		this.searchHabilidadeNome = searchHabilidadeNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoHabilidadeSortTipo() {
		return processoHabilidadeSortTipo;
	}

	public void setProcessoHabilidadeSortTipo(String processoHabilidadeSortTipo) {
		this.processoHabilidadeSortTipo = processoHabilidadeSortTipo;
	}

	public boolean isHabilidadeNomeSortAsc() {
		return habilidadeNomeSortAsc;
	}

	public void setHabilidadeNomeSortAsc(boolean habilidadeNomeSortAsc) {
		this.habilidadeNomeSortAsc = habilidadeNomeSortAsc;
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
