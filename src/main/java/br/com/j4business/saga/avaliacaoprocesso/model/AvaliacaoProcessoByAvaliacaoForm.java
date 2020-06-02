package br.com.j4business.saga.avaliacaoprocesso.model;

public class AvaliacaoProcessoByAvaliacaoForm {

	private String searchAvaliacaoNome;
	private String searchProcessoNome;
	private String searchQuestionarioNome;
	private String searchPeriodoNome;
	private String avaliacaoProcessoSortTipo;
	private boolean avaliacaoNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean questionarioNomeSortAsc = false ;
	private boolean periodoNomeSortAsc = false ;

	public AvaliacaoProcessoByAvaliacaoForm() {
		super();
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getSearchAvaliacaoNome() {
		return searchAvaliacaoNome;
	}

	public void setSearchAvaliacaoNome(String searchAvaliacaoNome) {
		this.searchAvaliacaoNome = searchAvaliacaoNome;
	}

	public String getAvaliacaoProcessoSortTipo() {
		return avaliacaoProcessoSortTipo;
	}

	public void setAvaliacaoProcessoSortTipo(String avaliacaoProcessoSortTipo) {
		this.avaliacaoProcessoSortTipo = avaliacaoProcessoSortTipo;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isAvaliacaoNomeSortAsc() {
		return avaliacaoNomeSortAsc;
	}

	public void setAvaliacaoNomeSortAsc(boolean avaliacaoNomeSortAsc) {
		this.avaliacaoNomeSortAsc = avaliacaoNomeSortAsc;
	}

	public String getSearchQuestionarioNome() {
		return searchQuestionarioNome;
	}

	public void setSearchQuestionarioNome(String searchQuestionarioNome) {
		this.searchQuestionarioNome = searchQuestionarioNome;
	}

	public boolean isQuestionarioNomeSortAsc() {
		return questionarioNomeSortAsc;
	}

	public void setQuestionarioNomeSortAsc(boolean questionarioNomeSortAsc) {
		this.questionarioNomeSortAsc = questionarioNomeSortAsc;
	}

	public String getSearchPeriodoNome() {
		return searchPeriodoNome;
	}

	public void setSearchPeriodoNome(String searchPeriodoNome) {
		this.searchPeriodoNome = searchPeriodoNome;
	}

	public boolean isPeriodoNomeSortAsc() {
		return periodoNomeSortAsc;
	}

	public void setPeriodoNomeSortAsc(boolean periodoNomeSortAsc) {
		this.periodoNomeSortAsc = periodoNomeSortAsc;
	}


}
