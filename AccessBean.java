package beans.pattern;

public class AccessBean {
	private String 	userId;
	private String 	userPassword;
	private String 	userType;
	private String 	userName;
	private String 	userEtc;
	private char 	rType;
	private String 	location;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEtc() {
		return userEtc;
	}
	public void setUserEtc(String userEtc) {
		this.userEtc = userEtc;
	}
	public char getrType() {
		return rType;
	}
	public void setrType(char rType) {
		this.rType = rType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
