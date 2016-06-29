public class AccountStatement {

	private String accountUserId;
	private String firstname;
	private String lastname;
	private double amount;
	private double amountStatement;
	private String dateTime;

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

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount(){
		return amount;
	}

	public void setDate(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDate() {
		return dateTime;
	}
}