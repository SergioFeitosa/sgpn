package br.com.j4business.saga.questionarioquestao.model;

public class QuestionarioQuestaoByQuestionarioForm {

	private String searchQuestaoNome;
	private String searchQuestionarioNome;
	private String questionarioQuestaoSortTipo;
	private boolean questaoNomeSortAsc = false ;
	private boolean questionarioNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public QuestionarioQuestaoByQuestionarioForm() {
		super();
	}

	public String getSearchQuestaoNome() {
		return searchQuestaoNome;
	}

	public void setSearchQuestaoNome(String searchQuestaoNome) {
		this.searchQuestaoNome = searchQuestaoNome;
	}

	public String getSearchQuestionarioNome() {
		return searchQuestionarioNome;
	}

	public void setSearchQuestionarioNome(String searchQuestionarioNome) {
		this.searchQuestionarioNome = searchQuestionarioNome;
	}

	public String getQuestionarioQuestaoSortTipo() {
		return questionarioQuestaoSortTipo;
	}

	public void setQuestionarioQuestaoSortTipo(String questionarioQuestaoSortTipo) {
		this.questionarioQuestaoSortTipo = questionarioQuestaoSortTipo;
	}

	public boolean isQuestaoNomeSortAsc() {
		return questaoNomeSortAsc;
	}

	public void setQuestaoNomeSortAsc(boolean questaoNomeSortAsc) {
		this.questaoNomeSortAsc = questaoNomeSortAsc;
	}

	public boolean isQuestionarioNomeSortAsc() {
		return questionarioNomeSortAsc;
	}

	public void setQuestionarioNomeSortAsc(boolean questionarioNomeSortAsc) {
		this.questionarioNomeSortAsc = questionarioNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
