public class User {
	
	private int loginId;
	private String username;
	private String password;
	private String sessionId;

	public void setloginId(int loginId) {
		this.loginId = loginId; 
	}

	public int getloginId() {
		return loginId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId(){
		return sessionId;
	}
}