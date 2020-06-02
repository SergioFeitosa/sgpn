package br.com.j4business.saga.video.model;

public class VideoByVideoForm {

	private String searchVideoNome;
	private String searchVideoDescricao;
	private String videoSortTipo;
	private boolean videoNomeSortAsc = false ;
	private boolean videoDescricaoSortAsc = false ;
	
	public VideoByVideoForm() {
		super();
	}

	public String getSearchVideoNome() {
		return searchVideoNome;
	}

	public void setSearchVideoNome(String searchVideoNome) {
		this.searchVideoNome = searchVideoNome;
	}

	public String getSearchVideoDescricao() {
		return searchVideoDescricao;
	}

	public void setSearchVideoDescricao(String searchVideoDescricao) {
		this.searchVideoDescricao = searchVideoDescricao;
	}

	public String getVideoSortTipo() {
		return videoSortTipo;
	}

	public void setVideoSortTipo(String videoSortTipo) {
		this.videoSortTipo = videoSortTipo;
	}

	public boolean isVideoNomeSortAsc() {
		return videoNomeSortAsc;
	}

	public void setVideoNomeSortAsc(boolean videoNomeSortAsc) {
		this.videoNomeSortAsc = videoNomeSortAsc;
	}

	public boolean isVideoDescricaoSortAsc() {
		return videoDescricaoSortAsc;
	}

	public void setVideoDescricaoSortAsc(boolean videoDescricaoSortAsc) {
		this.videoDescricaoSortAsc = videoDescricaoSortAsc;
	}

}
