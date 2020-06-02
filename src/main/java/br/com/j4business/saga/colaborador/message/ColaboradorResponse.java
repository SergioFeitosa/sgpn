package br.com.j4business.saga.colaborador.message;

public class ColaboradorResponse {
	private String status;
	private Object data;
	
	public ColaboradorResponse(){
		
	}
	
	public ColaboradorResponse(String status, Object data){
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
