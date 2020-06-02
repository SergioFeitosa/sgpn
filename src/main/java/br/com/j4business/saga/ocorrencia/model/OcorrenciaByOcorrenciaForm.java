package br.com.j4business.saga.ocorrencia.model;

public class OcorrenciaByOcorrenciaForm {

	private String searchOcorrenciaNome;
	private String searchOcorrenciaDescricao;
	private String ocorrenciaSortTipo;
	private boolean ocorrenciaNomeSortAsc = false ;
	private boolean ocorrenciaDescricaoSortAsc = false ;
	
	public OcorrenciaByOcorrenciaForm() {
		super();
	}

	public String getSearchOcorrenciaNome() {
		return searchOcorrenciaNome;
	}

	public void setSearchOcorrenciaNome(String searchOcorrenciaNome) {
		this.searchOcorrenciaNome = searchOcorrenciaNome;
	}

	public String getSearchOcorrenciaDescricao() {
		return searchOcorrenciaDescricao;
	}

	public void setSearchOcorrenciaDescricao(String searchOcorrenciaDescricao) {
		this.searchOcorrenciaDescricao = searchOcorrenciaDescricao;
	}

	public String getOcorrenciaSortTipo() {
		return ocorrenciaSortTipo;
	}

	public void setOcorrenciaSortTipo(String ocorrenciaSortTipo) {
		this.ocorrenciaSortTipo = ocorrenciaSortTipo;
	}

	public boolean isOcorrenciaNomeSortAsc() {
		return ocorrenciaNomeSortAsc;
	}

	public void setOcorrenciaNomeSortAsc(boolean ocorrenciaNomeSortAsc) {
		this.ocorrenciaNomeSortAsc = ocorrenciaNomeSortAsc;
	}

	public boolean isOcorrenciaDescricaoSortAsc() {
		return ocorrenciaDescricaoSortAsc;
	}

	public void setOcorrenciaDescricaoSortAsc(boolean ocorrenciaDescricaoSortAsc) {
		this.ocorrenciaDescricaoSortAsc = ocorrenciaDescricaoSortAsc;
	}

}
