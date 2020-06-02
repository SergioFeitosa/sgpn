package br.com.j4business.saga.planejamento.message;

public class PlanejamentoResponse {
	private String status;
	private Object data;
	
	public PlanejamentoResponse(){
		
	}
	
	public PlanejamentoResponse(String status, Object data){
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
