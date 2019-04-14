package utility;

public class CommentResponse {
	private String commentText;
	private String commentUrl;
	private Integer postId;
	private Integer userId;
	
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
