package utility;

public class LikeResponse <T> {
	private String message;
	private Integer status;
	private Integer[] postsLike;
	private Integer[] postsDislike;
	private Integer[] postId;
	
	public void setPostsId(Integer[] postId) {
		this.postId = postId;
	}
	
	public Integer[] getPostsId() {
		return postId;
	}
	
	public void setPostsDislikes(Integer[] postsDislike) {
		this.postsDislike = postsDislike;
	}
	
	public Integer[] getPostsDislikes() {
		return postsDislike;
	}
	
	public void setPostsLikes(Integer[] postsLike) {
		this.postsLike = postsLike;
	}
	
	public Integer[] getPostsLikes() {
		return postsLike;
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
