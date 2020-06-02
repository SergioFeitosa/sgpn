package br.com.j4business.saga.questao.model;

public class QuestaoByQuestaoForm {

	private String searchQuestaoNome;
	private String searchQuestaoDescricao;
	private String questaoSortTipo;
	private boolean questaoNomeSortAsc = false ;
	private boolean questaoDescricaoSortAsc = false ;
	
	public QuestaoByQuestaoForm() {
		super();
	}

	public String getSearchQuestaoNome() {
		return searchQuestaoNome;
	}

	public void setSearchQuestaoNome(String searchQuestaoNome) {
		this.searchQuestaoNome = searchQuestaoNome;
	}

	public String getSearchQuestaoDescricao() {
		return searchQuestaoDescricao;
	}

	public void setSearchQuestaoDescricao(String searchQuestaoDescricao) {
		this.searchQuestaoDescricao = searchQuestaoDescricao;
	}

	public String getQuestaoSortTipo() {
		return questaoSortTipo;
	}

	public void setQuestaoSortTipo(String questaoSortTipo) {
		this.questaoSortTipo = questaoSortTipo;
	}

	public boolean isQuestaoNomeSortAsc() {
		return questaoNomeSortAsc;
	}

	public void setQuestaoNomeSortAsc(boolean questaoNomeSortAsc) {
		this.questaoNomeSortAsc = questaoNomeSortAsc;
	}

	public boolean isQuestaoDescricaoSortAsc() {
		return questaoDescricaoSortAsc;
	}

	public void setQuestaoDescricaoSortAsc(boolean questaoDescricaoSortAsc) {
		this.questaoDescricaoSortAsc = questaoDescricaoSortAsc;
	}

}
