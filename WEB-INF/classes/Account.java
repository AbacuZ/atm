public class Account {

	private double amount;
	private String accountUserId;

	public void setAccountUserId(String accountUserId) {
		this.accountUserId = accountUserId; 
	}

	public String getAccountUserId() {
		return accountUserId;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}
}