package utility;

public class AdminInnerClass {
	private String usernameEdit;
	private String bannedUser;
	private String passwordEdit;
	private Integer option;
	
	public void setOption(Integer option) {
		this.option = option;
	}
	
	public Integer getOption() {
		return this.option;
	}
	
	public void setUsernameEdit(String usernameEdit) {
		this.usernameEdit = usernameEdit;
	}
	
	public String getUsernameEdit() {
		return this.usernameEdit;
	}
	
	public void setBannedUser(String bannedUser) {
		this.bannedUser = bannedUser;
	}
	
	public String getBannedUser() {
		return this.bannedUser;
	}
	
	public void setPasswordEdit(String passwordEdit) {
		this.passwordEdit = passwordEdit;
	}
	
	public String getPasswordEdit() {
		return this.passwordEdit;
	}

}
