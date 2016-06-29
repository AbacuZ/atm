public class AccountUser {

	private String accountUserId;
	private String firstname;
	private String lastname;
	private String address;
	private String email;

	public void setAccountUserId(String accountUserId) {
		this.accountUserId = accountUserId; 
	}

	public String getAccountUserId() {
		return accountUserId;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}