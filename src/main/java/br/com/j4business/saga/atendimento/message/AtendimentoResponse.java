package br.com.j4business.saga.atendimento.message;

public class AtendimentoResponse {
	private String status;
	private Object data;
	
	public AtendimentoResponse(){
		
	}
	
	public AtendimentoResponse(String status, Object data){
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
