package br.com.j4business.saga.habilidade.model;

public class HabilidadeByHabilidadeForm {

	private String searchHabilidadeNome;
	private String searchHabilidadeDescricao;
	private String habilidadeSortTipo;
	private boolean habilidadeNomeSortAsc = false ;
	private boolean habilidadeDescricaoSortAsc = false ;
	
	public HabilidadeByHabilidadeForm() {
		super();
	}

	public String getSearchHabilidadeNome() {
		return searchHabilidadeNome;
	}

	public void setSearchHabilidadeNome(String searchHabilidadeNome) {
		this.searchHabilidadeNome = searchHabilidadeNome;
	}

	public String getSearchHabilidadeDescricao() {
		return searchHabilidadeDescricao;
	}

	public void setSearchHabilidadeDescricao(String searchHabilidadeDescricao) {
		this.searchHabilidadeDescricao = searchHabilidadeDescricao;
	}

	public String getHabilidadeSortTipo() {
		return habilidadeSortTipo;
	}

	public void setHabilidadeSortTipo(String habilidadeSortTipo) {
		this.habilidadeSortTipo = habilidadeSortTipo;
	}

	public boolean isHabilidadeNomeSortAsc() {
		return habilidadeNomeSortAsc;
	}

	public void setHabilidadeNomeSortAsc(boolean habilidadeNomeSortAsc) {
		this.habilidadeNomeSortAsc = habilidadeNomeSortAsc;
	}

	public boolean isHabilidadeDescricaoSortAsc() {
		return habilidadeDescricaoSortAsc;
	}

	public void setHabilidadeDescricaoSortAsc(boolean habilidadeDescricaoSortAsc) {
		this.habilidadeDescricaoSortAsc = habilidadeDescricaoSortAsc;
	}

}
