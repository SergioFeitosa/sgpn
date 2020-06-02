package br.com.j4business.saga.texto.message;

public class TextoResponse {
	private String status;
	private Object data;
	
	public TextoResponse(){
		
	}
	
	public TextoResponse(String status, Object data){
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
