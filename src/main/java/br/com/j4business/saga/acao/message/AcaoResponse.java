package br.com.j4business.saga.acao.message;

public class AcaoResponse {
	private String status;
	private Object data;
	
	public AcaoResponse(){
		
	}
	
	public AcaoResponse(String status, Object data){
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
