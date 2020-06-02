package br.com.j4business.saga.empresa.model;

public class EmpresaByEmpresaForm {

	private String searchEmpresaNome;
	private String searchEmpresaNomeFantasia;
	private String empresaSortTipo;
	private boolean empresaNomeSortAsc = false ;
	private boolean empresaNomeFantasiaSortAsc = false ;
	
	public EmpresaByEmpresaForm() {
		super();
	}

	public String getSearchEmpresaNome() {
		return searchEmpresaNome;
	}

	public void setSearchEmpresaNome(String searchEmpresaNome) {
		this.searchEmpresaNome = searchEmpresaNome;
	}

	public String getSearchEmpresaNomeFantasia() {
		return searchEmpresaNomeFantasia;
	}

	public void setSearchEmpresaNomeFantasia(String searchEmpresaNomeFantasia) {
		this.searchEmpresaNomeFantasia = searchEmpresaNomeFantasia;
	}

	public String getEmpresaSortTipo() {
		return empresaSortTipo;
	}

	public void setEmpresaSortTipo(String empresaSortTipo) {
		this.empresaSortTipo = empresaSortTipo;
	}

	public boolean isEmpresaNomeSortAsc() {
		return empresaNomeSortAsc;
	}

	public void setEmpresaNomeSortAsc(boolean empresaNomeSortAsc) {
		this.empresaNomeSortAsc = empresaNomeSortAsc;
	}

	public boolean isEmpresaNomeFantasiaSortAsc() {
		return empresaNomeFantasiaSortAsc;
	}

	public void setEmpresaNomeFantasiaSortAsc(boolean empresaNomeFantasiaSortAsc) {
		this.empresaNomeFantasiaSortAsc = empresaNomeFantasiaSortAsc;
	}


}
