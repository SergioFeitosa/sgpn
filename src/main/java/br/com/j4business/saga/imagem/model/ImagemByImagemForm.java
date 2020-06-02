package br.com.j4business.saga.imagem.model;

public class ImagemByImagemForm {

	private String searchImagemNome;
	private String searchImagemDescricao;
	private String imagemSortTipo;
	private boolean imagemNomeSortAsc = false ;
	private boolean imagemDescricaoSortAsc = false ;
	
	public ImagemByImagemForm() {
		super();
	}

	public String getSearchImagemNome() {
		return searchImagemNome;
	}

	public void setSearchImagemNome(String searchImagemNome) {
		this.searchImagemNome = searchImagemNome;
	}

	public String getSearchImagemDescricao() {
		return searchImagemDescricao;
	}

	public void setSearchImagemDescricao(String searchImagemDescricao) {
		this.searchImagemDescricao = searchImagemDescricao;
	}

	public String getImagemSortTipo() {
		return imagemSortTipo;
	}

	public void setImagemSortTipo(String imagemSortTipo) {
		this.imagemSortTipo = imagemSortTipo;
	}

	public boolean isImagemNomeSortAsc() {
		return imagemNomeSortAsc;
	}

	public void setImagemNomeSortAsc(boolean imagemNomeSortAsc) {
		this.imagemNomeSortAsc = imagemNomeSortAsc;
	}

	public boolean isImagemDescricaoSortAsc() {
		return imagemDescricaoSortAsc;
	}

	public void setImagemDescricaoSortAsc(boolean imagemDescricaoSortAsc) {
		this.imagemDescricaoSortAsc = imagemDescricaoSortAsc;
	}

}
