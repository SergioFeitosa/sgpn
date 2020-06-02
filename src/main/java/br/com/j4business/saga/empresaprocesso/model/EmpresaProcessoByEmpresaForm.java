package br.com.j4business.saga.empresaprocesso.model;

public class EmpresaProcessoByEmpresaForm {

	private String searchProcessoNome;
	private String searchEmpresaNome;
	private String empresaProcessoSortTipo;
	private boolean processoNomeSortAsc = false ;
	private boolean empresaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public EmpresaProcessoByEmpresaForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchEmpresaNome() {
		return searchEmpresaNome;
	}

	public void setSearchEmpresaNome(String searchEmpresaNome) {
		this.searchEmpresaNome = searchEmpresaNome;
	}

	public String getEmpresaProcessoSortTipo() {
		return empresaProcessoSortTipo;
	}

	public void setEmpresaProcessoSortTipo(String empresaProcessoSortTipo) {
		this.empresaProcessoSortTipo = empresaProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isEmpresaNomeSortAsc() {
		return empresaNomeSortAsc;
	}

	public void setEmpresaNomeSortAsc(boolean empresaNomeSortAsc) {
		this.empresaNomeSortAsc = empresaNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
