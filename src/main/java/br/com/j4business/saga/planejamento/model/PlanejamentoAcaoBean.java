package br.com.j4business.saga.planejamento.model;

public class PlanejamentoAcaoBean {

	private long planejamentoPK;
	private String planejamentoNome;
	private long acaoPK;
	private String acaoNome;
	private boolean planejamentoAcaoChecked;
	

	public PlanejamentoAcaoBean() {
		super();
	}

	public long getPlanejamentoPK() {
		return planejamentoPK;
	}

	public void setPlanejamentoPK(long planejamentoPK) {
		this.planejamentoPK = planejamentoPK;
	}

	public long getAcaoPK() {
		return acaoPK;
	}

	public void setAcaoPK(long acaoPK) {
		this.acaoPK = acaoPK;
	}

	public boolean isPlanejamentoAcaoChecked() {
		return planejamentoAcaoChecked;
	}

	public void setPlanejamentoAcaoChecked(boolean planejamentoAcaoChecked) {
		this.planejamentoAcaoChecked = planejamentoAcaoChecked;
	}

	public String getPlanejamentoNome() {
		return planejamentoNome;
	}

	public void setPlanejamentoNome(String planejamentoNome) {
		this.planejamentoNome = planejamentoNome;
	}

	public String getAcaoNome() {
		return acaoNome;
	}

	public void setAcaoNome(String acaoNome) {
		this.acaoNome = acaoNome;
	}

}
