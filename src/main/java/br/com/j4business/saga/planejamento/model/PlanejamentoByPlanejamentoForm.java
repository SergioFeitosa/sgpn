package br.com.j4business.saga.planejamento.model;

public class PlanejamentoByPlanejamentoForm {

	private String searchPlanejamentoNome;
	private String searchPlanejamentoDescricao;
	private String planejamentoSortTipo;
	private boolean planejamentoNomeSortAsc = false ;
	private boolean planejamentoDescricaoSortAsc = false ;
	
	public PlanejamentoByPlanejamentoForm() {
		super();
	}

	public String getSearchPlanejamentoNome() {
		return searchPlanejamentoNome;
	}

	public void setSearchPlanejamentoNome(String searchPlanejamentoNome) {
		this.searchPlanejamentoNome = searchPlanejamentoNome;
	}

	public String getSearchPlanejamentoDescricao() {
		return searchPlanejamentoDescricao;
	}

	public void setSearchPlanejamentoDescricao(String searchPlanejamentoDescricao) {
		this.searchPlanejamentoDescricao = searchPlanejamentoDescricao;
	}

	public String getPlanejamentoSortTipo() {
		return planejamentoSortTipo;
	}

	public void setPlanejamentoSortTipo(String planejamentoSortTipo) {
		this.planejamentoSortTipo = planejamentoSortTipo;
	}

	public boolean isPlanejamentoNomeSortAsc() {
		return planejamentoNomeSortAsc;
	}

	public void setPlanejamentoNomeSortAsc(boolean planejamentoNomeSortAsc) {
		this.planejamentoNomeSortAsc = planejamentoNomeSortAsc;
	}

	public boolean isPlanejamentoDescricaoSortAsc() {
		return planejamentoDescricaoSortAsc;
	}

	public void setPlanejamentoDescricaoSortAsc(boolean planejamentoDescricaoSortAsc) {
		this.planejamentoDescricaoSortAsc = planejamentoDescricaoSortAsc;
	}

}
