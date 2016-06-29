import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TransferDetailInsertDatabase extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");
		Account account = (Account) request.getSession().getAttribute("account");
		AccountStatement accountStatement = (AccountStatement) request.getSession().getAttribute("accountStatement");

		String accountDeposit = accountUser.getAccountUserId();
		String accountTransfer = accountStatement.getAccountUserId();
		String dateTime = accountStatement.getDate();
		String typeDebit = "Debit";
		String typeCredit = "Credit";
		double amountDeposit = account.getAmount();
		double amountTransfer = accountStatement.getAmount();
		
		RequestDispatcher dispatcher;
		Connection connection = null;
		PreparedStatement insertTransferPrepareStatement = null;

			try {
				if (request.getParameter("OK") != null) {

					connection = databaseConnection.getDatabaseConnection();
					insertTransferPrepareStatement = connection.prepareStatement(
						"INSERT INTO transfer(accountDeposit, accountTransfer, amount, date) VALUES(?, ?, ?, ?)");
					insertTransferPrepareStatement.setString(1, accountDeposit);
					insertTransferPrepareStatement.setString(2, accountTransfer);
					insertTransferPrepareStatement.setDouble(3, amountTransfer);
					insertTransferPrepareStatement.setString(4, dateTime);
					insertTransferPrepareStatement.executeUpdate();

					double totalAmount = amountDeposit - amountTransfer;

					insertTransferPrepareStatement = connection.prepareStatement(
						"UPDATE account SET amount = ? WHERE accountUserId = ?");
					insertTransferPrepareStatement.setDouble(1, totalAmount);
					insertTransferPrepareStatement.setString(2, accountDeposit);
					insertTransferPrepareStatement.executeUpdate();

					insertTransferPrepareStatement = connection.prepareStatement(
						"SELECT amount FROM account where accountUserId = ?");
					insertTransferPrepareStatement.setString(1, accountTransfer);
					ResultSet resultSet = insertTransferPrepareStatement.executeQuery();

					if (resultSet.next()) {
						double amount = resultSet.getDouble("amount");
						double totalAmountAccountTransfer = amount + amountTransfer;

						insertTransferPrepareStatement = connection.prepareStatement(
							"UPDATE account SET amount = ? WHERE accountUserId = ?");
						insertTransferPrepareStatement.setDouble(1, totalAmountAccountTransfer);
						insertTransferPrepareStatement.setString(2, accountTransfer);
						insertTransferPrepareStatement.executeUpdate();
					}

					insertTransferPrepareStatement = connection.prepareStatement(
						"INSERT INTO transaction(transactionAccount, transactionAmount, transactionType, transactionDate) VALUES(?, ?, ?, ?)");
					insertTransferPrepareStatement.setString(1, accountDeposit);
					insertTransferPrepareStatement.setDouble(2, amountTransfer);
					insertTransferPrepareStatement.setString(3, typeDebit);
					insertTransferPrepareStatement.setString(4, dateTime);
					insertTransferPrepareStatement.executeUpdate();

					insertTransferPrepareStatement = connection.prepareStatement(
						"INSERT INTO transaction(transactionAccount, transactionAmount, transactionType, transactionDate) VALUES(?, ?, ?, ?)");
					insertTransferPrepareStatement.setString(1, accountTransfer);
					insertTransferPrepareStatement.setDouble(2, amountTransfer);
					insertTransferPrepareStatement.setString(3, typeCredit);
					insertTransferPrepareStatement.setString(4, dateTime);
					insertTransferPrepareStatement.executeUpdate();

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