package br.com.j4business.saga.logradourotipo.model;

public class LogradouroTipoByLogradouroTipoForm {

	private String searchLogradouroTipoNome;
	private String searchLogradouroTipoDescricao;
	private String logradouroTipoSortTipo;
	private boolean logradouroTipoNomeSortAsc = false ;
	private boolean logradouroTipoDescricaoSortAsc = false ;
	
	public LogradouroTipoByLogradouroTipoForm() {
		super();
	}

	public String getSearchLogradouroTipoNome() {
		return searchLogradouroTipoNome;
	}

	public void setSearchLogradouroTipoNome(String searchLogradouroTipoNome) {
		this.searchLogradouroTipoNome = searchLogradouroTipoNome;
	}

	public String getSearchLogradouroTipoDescricao() {
		return searchLogradouroTipoDescricao;
	}

	public void setSearchLogradouroTipoDescricao(String searchLogradouroTipoDescricao) {
		this.searchLogradouroTipoDescricao = searchLogradouroTipoDescricao;
	}

	public String getLogradouroTipoSortTipo() {
		return logradouroTipoSortTipo;
	}

	public void setLogradouroTipoSortTipo(String logradouroTipoSortTipo) {
		this.logradouroTipoSortTipo = logradouroTipoSortTipo;
	}

	public boolean isLogradouroTipoNomeSortAsc() {
		return logradouroTipoNomeSortAsc;
	}

	public void setLogradouroTipoNomeSortAsc(boolean logradouroTipoNomeSortAsc) {
		this.logradouroTipoNomeSortAsc = logradouroTipoNomeSortAsc;
	}

	public boolean isLogradouroTipoDescricaoSortAsc() {
		return logradouroTipoDescricaoSortAsc;
	}

	public void setLogradouroTipoDescricaoSortAsc(boolean logradouroTipoDescricaoSortAsc) {
		this.logradouroTipoDescricaoSortAsc = logradouroTipoDescricaoSortAsc;
	}

}
