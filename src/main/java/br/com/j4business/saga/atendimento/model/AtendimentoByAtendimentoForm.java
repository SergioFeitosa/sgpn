package br.com.j4business.saga.atendimento.model;

public class AtendimentoByAtendimentoForm {

	private String searchAtendimentoNome;
	private String searchAtendimentoDescricao;
	private String atendimentoSortTipo;
	private boolean atendimentoNomeSortAsc = false ;
	private boolean atendimentoDescricaoSortAsc = false ;
	
	public AtendimentoByAtendimentoForm() {
		super();
	}

	public String getSearchAtendimentoNome() {
		return searchAtendimentoNome;
	}

	public void setSearchAtendimentoNome(String searchAtendimentoNome) {
		this.searchAtendimentoNome = searchAtendimentoNome;
	}

	public String getSearchAtendimentoDescricao() {
		return searchAtendimentoDescricao;
	}

	public void setSearchAtendimentoDescricao(String searchAtendimentoDescricao) {
		this.searchAtendimentoDescricao = searchAtendimentoDescricao;
	}

	public String getAtendimentoSortTipo() {
		return atendimentoSortTipo;
	}

	public void setAtendimentoSortTipo(String atendimentoSortTipo) {
		this.atendimentoSortTipo = atendimentoSortTipo;
	}

	public boolean isAtendimentoNomeSortAsc() {
		return atendimentoNomeSortAsc;
	}

	public void setAtendimentoNomeSortAsc(boolean atendimentoNomeSortAsc) {
		this.atendimentoNomeSortAsc = atendimentoNomeSortAsc;
	}

	public boolean isAtendimentoDescricaoSortAsc() {
		return atendimentoDescricaoSortAsc;
	}

	public void setAtendimentoDescricaoSortAsc(boolean atendimentoDescricaoSortAsc) {
		this.atendimentoDescricaoSortAsc = atendimentoDescricaoSortAsc;
	}

}
