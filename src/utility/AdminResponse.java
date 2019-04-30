package utility;

public class AdminResponse<T> {
	private String message;
	private Integer status;
	private String htmlScript;
	private T data;
	
	public Object getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setHtmlScript(String htmlScript) {
		this.htmlScript = htmlScript;
	}
	
	public String getHtmlScript() {
		return this.htmlScript;
	}
}
