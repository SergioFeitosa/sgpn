package br.com.j4business.saga.servico.model;

public class ServicoByServicoForm {

	private String searchServicoNome;
	private String searchServicoDescricao;
	private String servicoSortTipo;
	private boolean servicoNomeSortAsc = false ;
	private boolean servicoDescricaoSortAsc = false ;
	private boolean servicoDuracaoSortAsc = false ;
	
	private int servicoDuracao;
	
	public ServicoByServicoForm() {
		super();
	}

	public String getSearchServicoNome() {
		return searchServicoNome;
	}

	public void setSearchServicoNome(String searchServicoNome) {
		this.searchServicoNome = searchServicoNome;
	}

	public String getSearchServicoDescricao() {
		return searchServicoDescricao;
	}

	public void setSearchServicoDescricao(String searchServicoDescricao) {
		this.searchServicoDescricao = searchServicoDescricao;
	}

	public String getServicoSortTipo() {
		return servicoSortTipo;
	}

	public void setServicoSortTipo(String servicoSortTipo) {
		this.servicoSortTipo = servicoSortTipo;
	}

	public boolean isServicoNomeSortAsc() {
		return servicoNomeSortAsc;
	}

	public void setServicoNomeSortAsc(boolean servicoNomeSortAsc) {
		this.servicoNomeSortAsc = servicoNomeSortAsc;
	}

	public boolean isServicoDescricaoSortAsc() {
		return servicoDescricaoSortAsc;
	}

	public void setServicoDescricaoSortAsc(boolean servicoDescricaoSortAsc) {
		this.servicoDescricaoSortAsc = servicoDescricaoSortAsc;
	}

	public boolean isServicoDuracaoSortAsc() {
		return servicoDuracaoSortAsc;
	}

	public void setServicoDuracaoSortAsc(boolean servicoDuracaoSortAsc) {
		this.servicoDuracaoSortAsc = servicoDuracaoSortAsc;
	}

	public int getServicoDuracao() {
		return servicoDuracao;
	}

	public void setServicoDuracao(int servicoDuracao) {
		this.servicoDuracao = servicoDuracao;
	}

}
