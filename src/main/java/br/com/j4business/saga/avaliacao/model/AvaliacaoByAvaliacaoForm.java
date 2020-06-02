package br.com.j4business.saga.avaliacao.model;

public class AvaliacaoByAvaliacaoForm {

	private String searchAvaliacaoNome;
	private String searchAvaliacaoDescricao;
	private String avaliacaoSortTipo;
	private boolean avaliacaoNomeSortAsc = false ;
	private boolean avaliacaoDescricaoSortAsc = false ;
	
	public AvaliacaoByAvaliacaoForm() {
		super();
	}

	public String getSearchAvaliacaoNome() {
		return searchAvaliacaoNome;
	}

	public void setSearchAvaliacaoNome(String searchAvaliacaoNome) {
		this.searchAvaliacaoNome = searchAvaliacaoNome;
	}

	public String getSearchAvaliacaoDescricao() {
		return searchAvaliacaoDescricao;
	}

	public void setSearchAvaliacaoDescricao(String searchAvaliacaoDescricao) {
		this.searchAvaliacaoDescricao = searchAvaliacaoDescricao;
	}

	public String getAvaliacaoSortTipo() {
		return avaliacaoSortTipo;
	}

	public void setAvaliacaoSortTipo(String avaliacaoSortTipo) {
		this.avaliacaoSortTipo = avaliacaoSortTipo;
	}

	public boolean isAvaliacaoNomeSortAsc() {
		return avaliacaoNomeSortAsc;
	}

	public void setAvaliacaoNomeSortAsc(boolean avaliacaoNomeSortAsc) {
		this.avaliacaoNomeSortAsc = avaliacaoNomeSortAsc;
	}

	public boolean isAvaliacaoDescricaoSortAsc() {
		return avaliacaoDescricaoSortAsc;
	}

	public void setAvaliacaoDescricaoSortAsc(boolean avaliacaoDescricaoSortAsc) {
		this.avaliacaoDescricaoSortAsc = avaliacaoDescricaoSortAsc;
	}

}
