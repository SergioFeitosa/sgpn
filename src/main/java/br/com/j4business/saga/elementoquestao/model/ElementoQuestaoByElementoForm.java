package br.com.j4business.saga.elementoquestao.model;

public class ElementoQuestaoByElementoForm {

	private String searchQuestaoNome;
	private String searchElementoNome;
	private String elementoQuestaoSortTipo;
	private boolean questaoNomeSortAsc = false ;
	private boolean elementoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ElementoQuestaoByElementoForm() {
		super();
	}

	public String getSearchQuestaoNome() {
		return searchQuestaoNome;
	}

	public void setSearchQuestaoNome(String searchQuestaoNome) {
		this.searchQuestaoNome = searchQuestaoNome;
	}

	public String getSearchElementoNome() {
		return searchElementoNome;
	}

	public void setSearchElementoNome(String searchElementoNome) {
		this.searchElementoNome = searchElementoNome;
	}

	public String getElementoQuestaoSortTipo() {
		return elementoQuestaoSortTipo;
	}

	public void setElementoQuestaoSortTipo(String elementoQuestaoSortTipo) {
		this.elementoQuestaoSortTipo = elementoQuestaoSortTipo;
	}

	public boolean isQuestaoNomeSortAsc() {
		return questaoNomeSortAsc;
	}

	public void setQuestaoNomeSortAsc(boolean questaoNomeSortAsc) {
		this.questaoNomeSortAsc = questaoNomeSortAsc;
	}

	public boolean isElementoNomeSortAsc() {
		return elementoNomeSortAsc;
	}

	public void setElementoNomeSortAsc(boolean elementoNomeSortAsc) {
		this.elementoNomeSortAsc = elementoNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
