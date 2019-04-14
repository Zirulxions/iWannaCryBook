package utility;

public class PostResponse<T> {
	private String message;
	private Integer status;
	private Integer[] postId;
	private String[] postText;
	private String[] postUrl;
	private Integer[] postUserId;
	private Integer[] postType;
	private Integer postCounter;
	
	public void setPostId(Integer[] postId) {
		this.postId = postId;
	}
	
	public Integer[] getPostId() {
		return postId;
	}
	
	public void setPostType(Integer[] postType) {
		this.postType = postType;
	}
	
	public Integer[] getPostType() {
		return postType;
	}
	
	public void setPostText(String[] postText) {
		this.postText = postText;
		//for(int i = 1; i <= this.postText.length; i++) {
		//	System.out.println("Values: " + this.postText[i-1]);
		//}
	}
	
	public String[] getPostText() {
		return postText;
	}
	
	public void setPostUrl(String[] postUrl) {
		this.postUrl = postUrl;
	}
	
	public String[] getPostUrl() {
		return postUrl;
	}
	
	public void setPostUserId(Integer[] postUserId) {
		this.postUserId = postUserId;
	}
	
	public Integer[] getPostUserId() {
		return postUserId;
	}
	
	public void setPostCounter(Integer postCounter) {
		this.postCounter = postCounter;
	}
	
	public Integer getPostCounter() {
		return postCounter;
	}
	
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

