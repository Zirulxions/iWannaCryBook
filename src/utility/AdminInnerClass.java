package utility;

public class AdminInnerClass {
	private String usernameEdit;
	private boolean banUser;
	private String passwordEdit;
	
	public void setUsernameEdit(String usernameEdit) {
		this.usernameEdit = usernameEdit;
	}
	
	public String getUsernameEdit() {
		return this.usernameEdit;
	}
	
	public void setBannedUser(boolean banUser) {
		this.banUser = banUser;
	}
	
	public boolean getBannedUser() {
		return this.banUser;
	}
	
	public void setPasswordEdit(String passwordEdit) {
		this.passwordEdit = passwordEdit;
	}
	
	public String getPasswordEdit() {
		return this.passwordEdit;
	}

}
