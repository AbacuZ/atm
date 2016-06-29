import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class CustomersDetail extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		RequestDispatcher dispatcher;

		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' type='text/css' href='/bank/css/style.css' />");
		out.println("<title>Bank Service</title>");
		out.println("</head>");
		out.println("<body>");
		
			try {
				int rowNumber = 1;
				String account = request.getParameter("customerId");
				Connection connection = databaseConnection.getDatabaseConnection();
				Statement statement = null;
				statement = connection.createStatement();
				String sql = "SELECT * FROM transaction WHERE transactionAccount='" + account + "' Order By transactionDate DESC";
				ResultSet resultSet = statement.executeQuery(sql);

				out.println("<div class='datagrid'>");
				out.println("<table>");
				out.println("<thead>");
				out.println("<tr>");	
				out.println("<th>No</th>");
				out.println("<th>Date</th>");
				out.println("<th>Time</th>");
				out.println("<th>Account</th>");
				out.println("<th>Cash</th>");
				out.println("<th>Type</th>");
				out.println("</tr>");
				out.println("</thead>");
				
				while (resultSet.next()) {	
					out.println("<tbody>");
					out.println("<tr>");
					out.println("<td>" + rowNumber + "</td>");
					out.println("<td>" + resultSet.getDate("transactionDate") + "</td>");
					out.println("<td>" + resultSet.getTime("transactionDate") + "</td>");
					out.println("<td>" + resultSet.getString("transactionAccount") + "</td>");
					out.println("<td style='text-align:right'>" + resultSet.getDouble("transactionAmount") + "</td>");
					out.println("<td>" + resultSet.getString("transactionType") + "</td>");
					out.println("</tr>");
					out.println("</tbody>");
					rowNumber++;
				}
				out.println("</table>");
				out.println("<br/>");
				out.println("<form action='dataaccountuser' method='POST'>");
				out.println("<input type='submit' class='back_submit' value='< Back'></input>");
				out.println("</form>");
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