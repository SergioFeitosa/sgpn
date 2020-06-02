package br.com.j4business.saga.acao.model;

public class AcaoByAcaoForm {

	private String searchAcaoNome;
	private String searchAcaoDescricao;
	private String acaoSortTipo;
	private boolean acaoNomeSortAsc = false ;
	private boolean acaoDescricaoSortAsc = false ;
	
	public AcaoByAcaoForm() {
		super();
	}

	public String getSearchAcaoNome() {
		return searchAcaoNome;
	}

	public void setSearchAcaoNome(String searchAcaoNome) {
		this.searchAcaoNome = searchAcaoNome;
	}

	public String getSearchAcaoDescricao() {
		return searchAcaoDescricao;
	}

	public void setSearchAcaoDescricao(String searchAcaoDescricao) {
		this.searchAcaoDescricao = searchAcaoDescricao;
	}

	public String getAcaoSortTipo() {
		return acaoSortTipo;
	}

	public void setAcaoSortTipo(String acaoSortTipo) {
		this.acaoSortTipo = acaoSortTipo;
	}

	public boolean isAcaoNomeSortAsc() {
		return acaoNomeSortAsc;
	}

	public void setAcaoNomeSortAsc(boolean acaoNomeSortAsc) {
		this.acaoNomeSortAsc = acaoNomeSortAsc;
	}

	public boolean isAcaoDescricaoSortAsc() {
		return acaoDescricaoSortAsc;
	}

	public void setAcaoDescricaoSortAsc(boolean acaoDescricaoSortAsc) {
		this.acaoDescricaoSortAsc = acaoDescricaoSortAsc;
	}

}
