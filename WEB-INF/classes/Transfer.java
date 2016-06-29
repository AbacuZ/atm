import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Transfer extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher;
		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");

		String accountUserId = accountUser.getAccountUserId();
		String firstname = "";
		String lastname = "";

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");
		String dateTime = dateFormat.format(date);

		Connection connection = null;
		PreparedStatement transferStatement = null;

		try {
			response.setContentType("Text/html");
			PrintWriter out = response.getWriter();

			out.println("<html>");
			out.println("<head>");
			out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/css/transfer.css' />");
			out.println("<title>Bank Service</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class='login_page'>");
			out.println("<div class='login_form'>");
			out.println("<div class='login_header'><h2>TRANSFER</h2></div>");
			out.println("<form action='transferdetail' method='POST'>");
			out.println("<b>Account Transfer : </b>");

			connection = databaseConnection.getDatabaseConnection();
			transferStatement = connection.prepareStatement(
				"SELECT accountUserId, firstname, lastname FROM accountuser WHERE NOT accountUserId = ?");
			transferStatement.setString(1, accountUserId);
			ResultSet resultSet = transferStatement.executeQuery();
			
			out.println("<select name='accountTransfer'>");
			out.println("<option selected>Select account</option>");
							
			while (resultSet.next()) {
				out.println("<option value='" + resultSet.getString("accountUserId") + "'>" + resultSet.getString("firstname") + " " + resultSet.getString("lastname") + " - " + resultSet.getString("accountUserId") + "</option>");
			}

			out.println("</select>");
			out.println("<br/><br/>");
			out.println("<label><b>Amount : </label>");
			out.println("<input type='number' step='0.01' name='cashAccountTransfer'></input><label> baht</b></label><br/><br/><br/>");
			out.println("<input type='submit' class='login_submit' value='Next Step >'></input>");
			out.println("</form>");
			out.println("<div class='fixed_form'>");
			out.println("<form action='dataaccountuser' method='POST'>");
			out.println("<input type='submit' class='login_submit' value='< Back' style='margin-top:-10px;margin-left:10px;background-color:#C62828;'></input>");
			out.println("</form>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");			
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}