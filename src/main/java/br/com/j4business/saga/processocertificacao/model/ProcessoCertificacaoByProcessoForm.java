package br.com.j4business.saga.processocertificacao.model;

public class ProcessoCertificacaoByProcessoForm {

	private String searchCertificacaoNome;
	private String searchProcessoNome;
	private String processoCertificacaoSortTipo;
	private boolean certificacaoNomeSortAsc = false ;
	private boolean processoNomeSortAsc = false ;
	private boolean sequenciaSortAsc = false ;

	
	
	public ProcessoCertificacaoByProcessoForm() {
		super();
	}

	public String getSearchCertificacaoNome() {
		return searchCertificacaoNome;
	}

	public void setSearchCertificacaoNome(String searchCertificacaoNome) {
		this.searchCertificacaoNome = searchCertificacaoNome;
	}

	public String getSearchProcessoNome() {
		return searchProcessoNome;
	}

	public void setSearchProcessoNome(String searchProcessoNome) {
		this.searchProcessoNome = searchProcessoNome;
	}

	public String getProcessoCertificacaoSortTipo() {
		return processoCertificacaoSortTipo;
	}

	public void setProcessoCertificacaoSortTipo(String processoCertificacaoSortTipo) {
		this.processoCertificacaoSortTipo = processoCertificacaoSortTipo;
	}

	public boolean isCertificacaoNomeSortAsc() {
		return certificacaoNomeSortAsc;
	}

	public void setCertificacaoNomeSortAsc(boolean certificacaoNomeSortAsc) {
		this.certificacaoNomeSortAsc = certificacaoNomeSortAsc;
	}

	public boolean isProcessoNomeSortAsc() {
		return processoNomeSortAsc;
	}

	public void setProcessoNomeSortAsc(boolean processoNomeSortAsc) {
		this.processoNomeSortAsc = processoNomeSortAsc;
	}

	public boolean isSequenciaSortAsc() {
		return sequenciaSortAsc;
	}

	public void setSequenciaSortAsc(boolean sequenciaSortAsc) {
		this.sequenciaSortAsc = sequenciaSortAsc;
	}


}
