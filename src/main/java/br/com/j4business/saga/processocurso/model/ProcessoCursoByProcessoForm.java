package br.com.j4business.saga.processocurso.model;

public class ProcessoCursoByProcessoForm {

	private String searchCursoNome;
	private String searchProcessoNome;
	private String processoCursoSortTipo;
	private boolean cursoNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ProcessoCursoByProcessoForm() {
		super();
	}

	public String getSearchCursoNome() {
		return searchCursoNome;
	}

	public void setSearchCursoNome(String searchCursoNome) {
		this.searchCursoNome = searchCursoNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoCursoSortTipo() {
		return processoCursoSortTipo;
	}

	public void setProcessoCursoSortTipo(String processoCursoSortTipo) {
		this.processoCursoSortTipo = processoCursoSortTipo;
	}

	public boolean isCursoNomeSortAsc() {
		return cursoNomeSortAsc;
	}

	public void setCursoNomeSortAsc(boolean cursoNomeSortAsc) {
		this.cursoNomeSortAsc = cursoNomeSortAsc;
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
