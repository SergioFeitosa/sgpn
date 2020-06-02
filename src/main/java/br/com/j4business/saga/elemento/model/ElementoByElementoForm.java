package br.com.j4business.saga.elemento.model;

public class ElementoByElementoForm {

	private String searchElementoNome;
	private String searchElementoDescricao;
	private String elementoSortTipo;
	private boolean elementoNomeSortAsc = false ;
	private boolean elementoDescricaoSortAsc = false ;
	
	public ElementoByElementoForm() {
		super();
	}

	public String getSearchElementoNome() {
		return searchElementoNome;
	}

	public void setSearchElementoNome(String searchElementoNome) {
		this.searchElementoNome = searchElementoNome;
	}

	public String getSearchElementoDescricao() {
		return searchElementoDescricao;
	}

	public void setSearchElementoDescricao(String searchElementoDescricao) {
		this.searchElementoDescricao = searchElementoDescricao;
	}

	public String getElementoSortTipo() {
		return elementoSortTipo;
	}

	public void setElementoSortTipo(String elementoSortTipo) {
		this.elementoSortTipo = elementoSortTipo;
	}

	public boolean isElementoNomeSortAsc() {
		return elementoNomeSortAsc;
	}

	public void setElementoNomeSortAsc(boolean elementoNomeSortAsc) {
		this.elementoNomeSortAsc = elementoNomeSortAsc;
	}

	public boolean isElementoDescricaoSortAsc() {
		return elementoDescricaoSortAsc;
	}

	public void setElementoDescricaoSortAsc(boolean elementoDescricaoSortAsc) {
		this.elementoDescricaoSortAsc = elementoDescricaoSortAsc;
	}

}
