package utility;

public class AdminStatsResponse <T>{
	private Integer status;
	private String message;
	private Integer postsByText;
	private Integer postsByImage;
	private Integer postsByVideo;
	private Integer maleUserList;
	private Integer femaleUserList;
	
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
	
	public Integer getPostsByText() {
		return this.postsByText;
	}
	
	public void setPostsByText(Integer postsByText) {
		this.postsByText = postsByText;
	}
	
	public Integer getPostsByImage() {
		return this.postsByImage;
	}
	
	public void setPostsByImage(Integer postsByImage) {
		this.postsByImage = postsByImage;
	}
	
	public Integer getPostsByVideo() {
		return this.postsByVideo;
	}
	
	public void setPostsByVideo(Integer postsByVideo) {
		this.postsByVideo = postsByVideo;
	}
	
	public Integer getMaleUsers() {
		return this.maleUserList;
	}
	
	public void setMaleUsers(Integer maleUserList) {
		this.maleUserList = maleUserList;
	}
	
	public Integer getFemaleUsers() {
		return this.femaleUserList;
	}
	
	public void setFemaleUsers(Integer femaleUserList) {
		this.femaleUserList = femaleUserList;
	}
}
