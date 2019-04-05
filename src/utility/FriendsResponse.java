package utility;

public class FriendsResponse<T> {
	private String message;
	private Integer status;
	private Integer[] friendsId;
	private String[] friendsUserName;
	
	public void setFriendsId(Integer[] friendsId) {
		this.friendsId = friendsId;
	}
	
	public Integer[] getFriendsId() {
		return friendsId;
	}
	
	public void setFriendsUserName(String[] friendsUserName) {
		this.friendsUserName = friendsUserName;
	}
	
	public String[] getFriendsUserName() {
		return friendsUserName;
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
