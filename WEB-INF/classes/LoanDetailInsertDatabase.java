import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoanDetailInsertDatabase extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");
		Account account = (Account) request.getSession().getAttribute("account");
		Bank bank = (Bank) request.getSession().getAttribute("bank");

		String accountUserId = accountUser.getAccountUserId();
		double amountUserInterest = account.getAmount();
		double amountBank = bank.getAmount();
		String typeLoan = "Loan";

		Connection connection = null;
		PreparedStatement accountUserLoanStatement = null;

		try {
			if (request.getParameter("OK") != null) {
				String cash = request.getParameter("cash");
				String interestMoney = request.getParameter("interestMoney");
				String interest = request.getParameter("interest");
				String dayOfInterest = request.getParameter("dayOfInterest");
				String dateTime = request.getParameter("dateTime");

				connection = databaseConnection.getDatabaseConnection();
				accountUserLoanStatement = connection.prepareStatement(
					"INSERT INTO loan(account, amount, interestMoney, Interest, dateofInterest, date)VALUES(?, ?, ?, ?, ?, ?)");
				accountUserLoanStatement.setString(1, accountUserId);
				accountUserLoanStatement.setString(2, cash);
				accountUserLoanStatement.setString(3, interestMoney);
				accountUserLoanStatement.setString(4, interest);
				accountUserLoanStatement.setString(5, dayOfInterest);
				accountUserLoanStatement.setString(6, dateTime);
				accountUserLoanStatement.executeUpdate();

				double totalAmountBank = amountBank - Double.parseDouble(cash);

				accountUserLoanStatement = connection.prepareStatement(
					"UPDATE bank SET amount = ?");
				accountUserLoanStatement.setDouble(1, totalAmountBank);
				accountUserLoanStatement.executeUpdate();

				double totalAmountUserInterest = amountUserInterest + Double.parseDouble(cash);

				accountUserLoanStatement = connection.prepareStatement(
					"UPDATE account SET amount = ? WHERE accountUserId = ?");
				accountUserLoanStatement.setDouble(1, totalAmountUserInterest);
				accountUserLoanStatement.setString(2, accountUserId);
				accountUserLoanStatement.executeUpdate();

				accountUserLoanStatement = connection.prepareStatement(
					"INSERT INTO transaction(transactionAccount, transactionAmount, transactionType, transactionDate) VALUES(?, ?, ?, ?)");
				accountUserLoanStatement.setString(1, accountUserId);
				accountUserLoanStatement.setDouble(2, Double.parseDouble(cash));
				accountUserLoanStatement.setString(3, typeLoan);
				accountUserLoanStatement.setString(4, dateTime);
				accountUserLoanStatement.executeUpdate();

				dispatcher = request.getRequestDispatcher("/dataaccountuser");
				dispatcher.forward(request, response);
			}
			if (request.getParameter("CANCEL") != null) {
				dispatcher = request.getRequestDispatcher("/dataaccountuser");
				dispatcher.forward(request, response);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}