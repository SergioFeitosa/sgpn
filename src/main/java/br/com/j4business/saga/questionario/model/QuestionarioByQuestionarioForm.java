package br.com.j4business.saga.questionario.model;

public class QuestionarioByQuestionarioForm {

	private String searchQuestionarioNome;
	private String searchQuestionarioDescricao;
	private String searchCenarioNome;
	private String questionarioSortTipo;
	private boolean questionarioNomeSortAsc = false ;
	private boolean cenarioNomeSortAsc = false ;
	private boolean questionarioDescricaoSortAsc = false ;
	
	public QuestionarioByQuestionarioForm() {
		super();
	}

	public String getSearchQuestionarioNome() {
		return searchQuestionarioNome;
	}

	public void setSearchQuestionarioNome(String searchQuestionarioNome) {
		this.searchQuestionarioNome = searchQuestionarioNome;
	}

	public String getSearchQuestionarioDescricao() {
		return searchQuestionarioDescricao;
	}

	public void setSearchQuestionarioDescricao(String searchQuestionarioDescricao) {
		this.searchQuestionarioDescricao = searchQuestionarioDescricao;
	}

	public String getQuestionarioSortTipo() {
		return questionarioSortTipo;
	}

	public void setQuestionarioSortTipo(String questionarioSortTipo) {
		this.questionarioSortTipo = questionarioSortTipo;
	}

	public boolean isQuestionarioNomeSortAsc() {
		return questionarioNomeSortAsc;
	}

	public void setQuestionarioNomeSortAsc(boolean questionarioNomeSortAsc) {
		this.questionarioNomeSortAsc = questionarioNomeSortAsc;
	}

	public boolean isQuestionarioDescricaoSortAsc() {
		return questionarioDescricaoSortAsc;
	}

	public void setQuestionarioDescricaoSortAsc(boolean questionarioDescricaoSortAsc) {
		this.questionarioDescricaoSortAsc = questionarioDescricaoSortAsc;
	}

	public String getSearchCenarioNome() {
		return searchCenarioNome;
	}

	public void setSearchCenarioNome(String searchCenarioNome) {
		this.searchCenarioNome = searchCenarioNome;
	}

	public boolean isCenarioNomeSortAsc() {
		return cenarioNomeSortAsc;
	}

	public void setCenarioNomeSortAsc(boolean cenarioNomeSortAsc) {
		this.cenarioNomeSortAsc = cenarioNomeSortAsc;
	}

}
