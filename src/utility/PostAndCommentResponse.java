package utility;

import java.util.ArrayList;

public class PostAndCommentResponse <T>{
	private ArrayList<String> postUsername = new ArrayList<String>();
	private ArrayList<Integer> postId = new ArrayList<Integer>();
	private ArrayList<String> postContent = new ArrayList<String>();
	private ArrayList<String> commentUsername = new ArrayList<String>();
	private ArrayList<Integer> commentId = new ArrayList<Integer>();
	private ArrayList<String> commentContent = new ArrayList<String>();
	
	public void setPostUsername(ArrayList<String> postUsername) {
	    this.postUsername = postUsername;
	}
	
	public ArrayList<String> getStringList() {
	    return postUsername;
	}
}
