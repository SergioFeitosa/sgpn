package br.com.j4business.saga.servico.message;

public class ServicoResponse {
	private String status;
	private Object data;
	
	public ServicoResponse(){
		
	}
	
	public ServicoResponse(String status, Object data){
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
