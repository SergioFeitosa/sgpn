package br.com.j4business.saga.estruturafisica.model;

public class EstruturafisicaByEstruturafisicaForm {

	private String searchEstruturafisicaNome;
	private String searchEstruturafisicaDescricao;
	private String estruturafisicaSortTipo;
	private boolean estruturafisicaNomeSortAsc = false ;
	private boolean estruturafisicaDescricaoSortAsc = false ;
	
	public EstruturafisicaByEstruturafisicaForm() {
		super();
	}

	public String getSearchEstruturafisicaNome() {
		return searchEstruturafisicaNome;
	}

	public void setSearchEstruturafisicaNome(String searchEstruturafisicaNome) {
		this.searchEstruturafisicaNome = searchEstruturafisicaNome;
	}

	public String getSearchEstruturafisicaDescricao() {
		return searchEstruturafisicaDescricao;
	}

	public void setSearchEstruturafisicaDescricao(String searchEstruturafisicaDescricao) {
		this.searchEstruturafisicaDescricao = searchEstruturafisicaDescricao;
	}

	public String getEstruturafisicaSortTipo() {
		return estruturafisicaSortTipo;
	}

	public void setEstruturafisicaSortTipo(String estruturafisicaSortTipo) {
		this.estruturafisicaSortTipo = estruturafisicaSortTipo;
	}

	public boolean isEstruturafisicaNomeSortAsc() {
		return estruturafisicaNomeSortAsc;
	}

	public void setEstruturafisicaNomeSortAsc(boolean estruturafisicaNomeSortAsc) {
		this.estruturafisicaNomeSortAsc = estruturafisicaNomeSortAsc;
	}

	public boolean isEstruturafisicaDescricaoSortAsc() {
		return estruturafisicaDescricaoSortAsc;
	}

	public void setEstruturafisicaDescricaoSortAsc(boolean estruturafisicaDescricaoSortAsc) {
		this.estruturafisicaDescricaoSortAsc = estruturafisicaDescricaoSortAsc;
	}

}
