package br.com.j4business.saga.formacao.model;

public class FormacaoByFormacaoForm {

	private String searchFormacaoNome;
	private String searchFormacaoDescricao;
	private String formacaoSortTipo;
	private boolean formacaoNomeSortAsc = false ;
	private boolean formacaoDescricaoSortAsc = false ;
	
	public FormacaoByFormacaoForm() {
		super();
	}

	public String getSearchFormacaoNome() {
		return searchFormacaoNome;
	}

	public void setSearchFormacaoNome(String searchFormacaoNome) {
		this.searchFormacaoNome = searchFormacaoNome;
	}

	public String getSearchFormacaoDescricao() {
		return searchFormacaoDescricao;
	}

	public void setSearchFormacaoDescricao(String searchFormacaoDescricao) {
		this.searchFormacaoDescricao = searchFormacaoDescricao;
	}

	public String getFormacaoSortTipo() {
		return formacaoSortTipo;
	}

	public void setFormacaoSortTipo(String formacaoSortTipo) {
		this.formacaoSortTipo = formacaoSortTipo;
	}

	public boolean isFormacaoNomeSortAsc() {
		return formacaoNomeSortAsc;
	}

	public void setFormacaoNomeSortAsc(boolean formacaoNomeSortAsc) {
		this.formacaoNomeSortAsc = formacaoNomeSortAsc;
	}

	public boolean isFormacaoDescricaoSortAsc() {
		return formacaoDescricaoSortAsc;
	}

	public void setFormacaoDescricaoSortAsc(boolean formacaoDescricaoSortAsc) {
		this.formacaoDescricaoSortAsc = formacaoDescricaoSortAsc;
	}

}
