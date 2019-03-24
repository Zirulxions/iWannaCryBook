package utility;

public class PostResponse<T> {
	private String message;
	private Integer status;

	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}
	
}

