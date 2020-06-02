package br.com.j4business.saga.avaliacaocontrato.model;

public class AvaliacaoContratoByAvaliacaoForm {

	private String searchAvaliacaoNome;
	private String searchContratoNome;
	private String searchQuestionarioNome;
	private String searchPeriodoNome;
	private String avaliacaoContratoSortTipo;
	private boolean avaliacaoNomeSortAsc = false ;
	private boolean contratoNomeSortAsc = false ;
	private boolean questionarioNomeSortAsc = false ;
	private boolean periodoNomeSortAsc = false ;

	public AvaliacaoContratoByAvaliacaoForm() {
		super();
	}

	public String getSearchContratoNome() {
		return searchContratoNome;
	}

	public void setSearchContratoNome(String searchContratoNome) {
		this.searchContratoNome = searchContratoNome;
	}

	public String getSearchAvaliacaoNome() {
		return searchAvaliacaoNome;
	}

	public void setSearchAvaliacaoNome(String searchAvaliacaoNome) {
		this.searchAvaliacaoNome = searchAvaliacaoNome;
	}

	public String getAvaliacaoContratoSortTipo() {
		return avaliacaoContratoSortTipo;
	}

	public void setAvaliacaoContratoSortTipo(String avaliacaoContratoSortTipo) {
		this.avaliacaoContratoSortTipo = avaliacaoContratoSortTipo;
	}

	public boolean isContratoNomeSortAsc() {
		return contratoNomeSortAsc;
	}

	public void setContratoNomeSortAsc(boolean contratoNomeSortAsc) {
		this.contratoNomeSortAsc = contratoNomeSortAsc;
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
