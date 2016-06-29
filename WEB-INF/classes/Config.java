public class Config {

	private String databaseDriver;
	private String databaseURL;
	private String databaseUsername;
	private String databasePassword;

	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	public String getDatabaseDriver() {
		return databaseDriver;
	}
	
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	public String getDatabaseURL() {
		return databaseURL;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}
	
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}
}