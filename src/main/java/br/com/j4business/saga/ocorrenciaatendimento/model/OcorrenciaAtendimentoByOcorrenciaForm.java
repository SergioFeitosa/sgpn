package br.com.j4business.saga.ocorrenciaatendimento.model;

public class OcorrenciaAtendimentoByOcorrenciaForm {

	private String searchAtendimentoNome;
	private String searchOcorrenciaNome;
	private String ocorrenciaAtendimentoSortTipo;
	private boolean atendimentoNomeSortAsc = false ;
	private boolean ocorrenciaNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public OcorrenciaAtendimentoByOcorrenciaForm() {
		super();
	}

	public String getSearchAtendimentoNome() {
		return searchAtendimentoNome;
	}

	public void setSearchAtendimentoNome(String searchAtendimentoNome) {
		this.searchAtendimentoNome = searchAtendimentoNome;
	}

	public String getSearchOcorrenciaNome() {
		return searchOcorrenciaNome;
	}

	public void setSearchOcorrenciaNome(String searchOcorrenciaNome) {
		this.searchOcorrenciaNome = searchOcorrenciaNome;
	}

	public String getOcorrenciaAtendimentoSortTipo() {
		return ocorrenciaAtendimentoSortTipo;
	}

	public void setOcorrenciaAtendimentoSortTipo(String ocorrenciaAtendimentoSortTipo) {
		this.ocorrenciaAtendimentoSortTipo = ocorrenciaAtendimentoSortTipo;
	}

	public boolean isAtendimentoNomeSortAsc() {
		return atendimentoNomeSortAsc;
	}

	public void setAtendimentoNomeSortAsc(boolean atendimentoNomeSortAsc) {
		this.atendimentoNomeSortAsc = atendimentoNomeSortAsc;
	}

	public boolean isOcorrenciaNomeSortAsc() {
		return ocorrenciaNomeSortAsc;
	}

	public void setOcorrenciaNomeSortAsc(boolean ocorrenciaNomeSortAsc) {
		this.ocorrenciaNomeSortAsc = ocorrenciaNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
