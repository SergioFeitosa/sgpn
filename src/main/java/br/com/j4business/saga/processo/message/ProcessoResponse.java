package br.com.j4business.saga.processo.message;

public class ProcessoResponse {
	private String status;
	private Object data;
	
	public ProcessoResponse(){
		
	}
	
	public ProcessoResponse(String status, Object data){
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
