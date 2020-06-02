package br.com.j4business.saga.contrato.model;

public class ContratoByContratoForm {

	private String searchContratoNome;
	private String searchContratoDescricao;
	private String contratoSortTipo;
	private boolean contratoNomeSortAsc = false ;
	private boolean contratoDescricaoSortAsc = false ;
	
	public ContratoByContratoForm() {
		super();
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getSearchContratoDescricao() {
		return searchContratoDescricao;
	}

	public void setSearchContratoDescricao(String searchContratoDescricao) {
		this.searchContratoDescricao = searchContratoDescricao;
	}

	public String getContratoSortTipo() {
		return contratoSortTipo;
	}

	public void setContratoSortTipo(String contratoSortTipo) {
		this.contratoSortTipo = contratoSortTipo;
	}

	public boolean isContratoNomeSortAsc() {
		return contratoNomeSortAsc;
	}

	public void setContratoNomeSortAsc(boolean contratoNomeSortAsc) {
		this.contratoNomeSortAsc = contratoNomeSortAsc;
	}

	public boolean isContratoDescricaoSortAsc() {
		return contratoDescricaoSortAsc;
	}

	public void setContratoDescricaoSortAsc(boolean contratoDescricaoSortAsc) {
		this.contratoDescricaoSortAsc = contratoDescricaoSortAsc;
	}

}
