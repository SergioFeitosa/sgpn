package br.com.j4business.saga.treinamento.model;

public class TreinamentoByTreinamentoForm {

	private String searchTreinamentoNome;
	private String searchTreinamentoDescricao;
	private String treinamentoSortTipo;
	private boolean treinamentoNomeSortAsc = false ;
	private boolean treinamentoDescricaoSortAsc = false ;
	
	public TreinamentoByTreinamentoForm() {
		super();
	}

	public String getSearchTreinamentoNome() {
		return searchTreinamentoNome;
	}

	public void setSearchTreinamentoNome(String searchTreinamentoNome) {
		this.searchTreinamentoNome = searchTreinamentoNome;
	}

	public String getSearchTreinamentoDescricao() {
		return searchTreinamentoDescricao;
	}

	public void setSearchTreinamentoDescricao(String searchTreinamentoDescricao) {
		this.searchTreinamentoDescricao = searchTreinamentoDescricao;
	}

	public String getTreinamentoSortTipo() {
		return treinamentoSortTipo;
	}

	public void setTreinamentoSortTipo(String treinamentoSortTipo) {
		this.treinamentoSortTipo = treinamentoSortTipo;
	}

	public boolean isTreinamentoNomeSortAsc() {
		return treinamentoNomeSortAsc;
	}

	public void setTreinamentoNomeSortAsc(boolean treinamentoNomeSortAsc) {
		this.treinamentoNomeSortAsc = treinamentoNomeSortAsc;
	}

	public boolean isTreinamentoDescricaoSortAsc() {
		return treinamentoDescricaoSortAsc;
	}

	public void setTreinamentoDescricaoSortAsc(boolean treinamentoDescricaoSortAsc) {
		this.treinamentoDescricaoSortAsc = treinamentoDescricaoSortAsc;
	}

}
