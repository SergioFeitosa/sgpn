package br.com.j4business.saga.fornecedor.message;

public class FornecedorResponse {
	private String status;
	private Object data;
	
	public FornecedorResponse(){
		
	}
	
	public FornecedorResponse(String status, Object data){
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
