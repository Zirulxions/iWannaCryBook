package utility;

public class CommentResponse<T> {
	private String message;
	private Integer status;
	private String commentText;
	private String commentUrl;
	private Integer postId;
	private Integer userId;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getCommentText() {
		return commentText; 
	}
	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}
	public String getCommentUrl() {
		return commentUrl;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserId(){
		return userId;
	}
}
