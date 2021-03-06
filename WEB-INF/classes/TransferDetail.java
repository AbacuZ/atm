import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.Date;

public class TransferDetail extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		RequestDispatcher dispatcher;
		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");
		Account account = (Account) request.getSession().getAttribute("account");
		
		String accountDeposit = accountUser.getAccountUserId();
		double cashAccountDeposit = account.getAmount();
		String firstnameDeposit = accountUser.getFirstname();
		String lastnameDeposit = accountUser.getLastname();
		String accountTransfer = request.getParameter("accountTransfer");
		String cashAccountTransfer = request.getParameter("cashAccountTransfer");
		double amountTransfer = Double.parseDouble(cashAccountTransfer);
		String firstnameTransfer = "";
		String lastnameTransfer = "";

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		String dateTime = dateFormat.format(date);

		Connection connection = null;
		PreparedStatement accountDepositPrepareStatement = null;
		try {
			connection = databaseConnection.getDatabaseConnection();
			accountDepositPrepareStatement = connection.prepareStatement(
				"SELECT firstname, lastname FROM accountuser WHERE accountUserId = ?");
			accountDepositPrepareStatement.setString(1, accountTransfer);
			ResultSet resultSet = accountDepositPrepareStatement.executeQuery();
			
			if (resultSet.next()) {
				firstnameTransfer = resultSet.getString("firstname");
				lastnameTransfer = resultSet.getString("lastname");
				AccountStatement accountStatement = new AccountStatement();
				accountStatement.setAccountUserId(accountTransfer);
				accountStatement.setFirstname(resultSet.getString("firstname"));
				accountStatement.setLastname(resultSet.getString("lastname"));
				accountStatement.setAmount(amountTransfer);
				accountStatement.setDate(dateTime);

				HttpSession session = request.getSession();
				session.setAttribute("accountStatement", accountStatement);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		double amount = Double.parseDouble(cashAccountTransfer);
		DecimalFormat formatter = new DecimalFormat("#,###.00");

		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/css/transferdetail.css' />");
		out.println("<title>Bank Service</title>");
		out.println("</head>");
		out.println("<body>");	
		out.println("<div class='login_page'>");
		out.println("<div class='login_form'>");
		out.println("<div class='login_header'><h2>CONFIRM TRANSFER</h2></div>");
		out.println("<form action='transferdetailinsertdatabase' method='POST'>");
		out.println("<label><b>From Account Deposit</b></label><br/><br/>");
		out.println("<label>" + firstnameDeposit + " " + lastnameDeposit + " - " + accountDeposit + "</label><br/><br/>");
		out.println("<label><b>To Account Transfer</b></label><br/><br/>");
		out.println("<label>" + firstnameTransfer + " " + lastnameTransfer + " - " + accountTransfer + "</label><br/><br/>");
		out.println("<label><b>Cash : </b>" + formatter.format(amount) + " Baht</label><br/><br/>");
		out.println("<label><b>Time : </b>" + dateTime + "</label><br/><br/>");
		out.println("<input type='submit' class='login_submit' name='OK' value='OK'></input>");
		out.println("<input type='submit' class='login_submit' name='CANCEL' value='CANCEL'></input>");
		out.println("<input type='hidden' name='accountTransfer' value='" + accountTransfer + "'>");
		out.println("<input type='hidden' name='accountDeposit' value='" + accountDeposit + "'>");
		out.println("<input type='hidden' name='cashAccountDeposit' value='" + cashAccountDeposit + "'>");
		out.println("<input type='hidden' name='cashAccountTransfer' value='" + cashAccountTransfer + "'>");
		out.println("<input type='hidden' name='dateTime' value='" + dateTime + "'>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}