import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DataAccountUser extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		RequestDispatcher dispatcher;

		User user = (User) request.getSession().getAttribute("user");
		int loginId = user.getloginId();

		Connection connection = null;
		PreparedStatement accountUserPrepareStatement = null;
		
		try {
			connection = databaseConnection.getDatabaseConnection();
			accountUserPrepareStatement = connection.prepareStatement(
				"SELECT * FROM accountuser , login WHERE accountuser.loginId = ?");
			accountUserPrepareStatement.setInt(1, loginId);
			ResultSet resultSet = accountUserPrepareStatement.executeQuery();

			while (resultSet.next()) {
				AccountUser accountUser = new AccountUser();
				accountUser.setAccountUserId(resultSet.getString("accountUserId"));
				accountUser.setFirstname(resultSet.getString("firstname"));
				accountUser.setLastname(resultSet.getString("lastname"));
				accountUser.setAddress(resultSet.getString("address"));
				accountUser.setEmail(resultSet.getString("email"));

				HttpSession session = request.getSession();
				session.setAttribute("accountUser", accountUser);

				String accountUserId = resultSet.getString("accountUserId");

				accountUserPrepareStatement = connection.prepareStatement(
					"SELECT amount FROM account WHERE accountUserId = ?");
				accountUserPrepareStatement.setString(1, accountUserId);
				resultSet = accountUserPrepareStatement.executeQuery();

				while (resultSet.next()) {
					Account account = new Account();
					account.setAccountUserId(accountUserId);
					account.setAmount(resultSet.getDouble("amount"));
					session.setAttribute("account", account);
				}

				accountUserPrepareStatement = connection.prepareStatement(
					"SELECT * FROM bank");
				resultSet = accountUserPrepareStatement.executeQuery();

				while (resultSet.next()) {
					Bank bank = new Bank();
					bank.setBankId(resultSet.getString("bankId"));
					bank.setAmount(resultSet.getDouble("amount"));
					session.setAttribute("bank", bank);
				}
			}
			
			dispatcher = request.getRequestDispatcher("/main");
			dispatcher.forward(request, response);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}