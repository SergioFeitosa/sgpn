package br.com.j4business.saga.atividade.model;

public class AtividadeByAtividadeForm {

	private String searchAtividadeNome;
	private String searchAtividadeDescricao;
	private String atividadeSortTipo;
	private boolean atividadeNomeSortAsc = false ;
	private boolean atividadeDescricaoSortAsc = false ;
	
	public AtividadeByAtividadeForm() {
		super();
	}

	public String getSearchAtividadeNome() {
		return searchAtividadeNome;
	}

	public void setSearchAtividadeNome(String searchAtividadeNome) {
		this.searchAtividadeNome = searchAtividadeNome;
	}

	public String getSearchAtividadeDescricao() {
		return searchAtividadeDescricao;
	}

	public void setSearchAtividadeDescricao(String searchAtividadeDescricao) {
		this.searchAtividadeDescricao = searchAtividadeDescricao;
	}

	public String getAtividadeSortTipo() {
		return atividadeSortTipo;
	}

	public void setAtividadeSortTipo(String atividadeSortTipo) {
		this.atividadeSortTipo = atividadeSortTipo;
	}

	public boolean isAtividadeNomeSortAsc() {
		return atividadeNomeSortAsc;
	}

	public void setAtividadeNomeSortAsc(boolean atividadeNomeSortAsc) {
		this.atividadeNomeSortAsc = atividadeNomeSortAsc;
	}

	public boolean isAtividadeDescricaoSortAsc() {
		return atividadeDescricaoSortAsc;
	}

	public void setAtividadeDescricaoSortAsc(boolean atividadeDescricaoSortAsc) {
		this.atividadeDescricaoSortAsc = atividadeDescricaoSortAsc;
	}

}
