package utility;

import java.util.ArrayList;

public class PostAndCommentResponse <T>{
	private Integer status;
	private String message;
	private ArrayList<String> postUsername = new ArrayList<String>();
	private ArrayList<Integer> postId = new ArrayList<Integer>();
	private ArrayList<String> postContent = new ArrayList<String>();
	private ArrayList<String> commentUsername = new ArrayList<String>();
	private ArrayList<Integer> commentId = new ArrayList<Integer>();
	private ArrayList<String> commentContent = new ArrayList<String>();
	
	public void setCommentContent(ArrayList<String> commentContent) {
		this.commentContent = commentContent;
	}
	
	public ArrayList<String> getCommentContent(){
		return commentContent;
	}
	
	public void setCommentId(ArrayList<Integer> commentId) {
		this.commentId = commentId;
	}
	
	public ArrayList<Integer> getCommentId(){
		return commentId;
	}
	
	public void setCommentUsername(ArrayList<String> commentUsername) {
		this.commentUsername = commentUsername;
	}
	
	public ArrayList<String> getCommentUsername(){
		return commentUsername;
	}
	
	public void setPostContent(ArrayList<String> postContent) {
		this.postContent = postContent;
	}
	
	public ArrayList<String> getPostContent(){
		return postContent;
	}
	
	public void setPostId(ArrayList<Integer> postId) {
		this.postId = postId;
	}
	
	public ArrayList<Integer> getPostId(){
		return postId;
	}
	
	public void setPostUsername(ArrayList<String> postUsername) {
	    this.postUsername = postUsername;
	}
	
	public ArrayList<String> getStringList() {
	    return postUsername;
	}
	
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
}
