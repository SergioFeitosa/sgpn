package br.com.j4business.saga.certificacao.model;

public class CertificacaoByCertificacaoForm {

	private String searchCertificacaoNome;
	private String searchCertificacaoDescricao;
	private String certificacaoSortTipo;
	private boolean certificacaoNomeSortAsc = false ;
	private boolean certificacaoDescricaoSortAsc = false ;
	
	public CertificacaoByCertificacaoForm() {
		super();
	}

	public String getSearchCertificacaoNome() {
		return searchCertificacaoNome;
	}

	public void setSearchCertificacaoNome(String searchCertificacaoNome) {
		this.searchCertificacaoNome = searchCertificacaoNome;
	}

	public String getSearchCertificacaoDescricao() {
		return searchCertificacaoDescricao;
	}

	public void setSearchCertificacaoDescricao(String searchCertificacaoDescricao) {
		this.searchCertificacaoDescricao = searchCertificacaoDescricao;
	}

	public String getCertificacaoSortTipo() {
		return certificacaoSortTipo;
	}

	public void setCertificacaoSortTipo(String certificacaoSortTipo) {
		this.certificacaoSortTipo = certificacaoSortTipo;
	}

	public boolean isCertificacaoNomeSortAsc() {
		return certificacaoNomeSortAsc;
	}

	public void setCertificacaoNomeSortAsc(boolean certificacaoNomeSortAsc) {
		this.certificacaoNomeSortAsc = certificacaoNomeSortAsc;
	}

	public boolean isCertificacaoDescricaoSortAsc() {
		return certificacaoDescricaoSortAsc;
	}

	public void setCertificacaoDescricaoSortAsc(boolean certificacaoDescricaoSortAsc) {
		this.certificacaoDescricaoSortAsc = certificacaoDescricaoSortAsc;
	}

}
