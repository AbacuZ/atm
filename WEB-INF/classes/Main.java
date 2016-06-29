import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;

public class Main extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");

		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");
		Account account = (Account) request.getSession().getAttribute("account");

		String accountUserId = accountUser.getAccountUserId();
		String firstname = accountUser.getFirstname();
		String lastname = accountUser.getLastname();
		String address = accountUser.getAddress();
		String email = accountUser.getEmail();
		double amount = account.getAmount();

		HttpSession session = request.getSession();

		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/css/login.css' />");
		out.println("<title>Bank Service</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='login_page'>");
		out.println("<div class='login_form'>");
		out.println("<center><div class='login_header'><h2>MAIN PAGE</h2></div></center>");
		out.println("<form action='customersdetail' method='POST'>");
		out.println("<span style='float:left;margin-left:20px;'><b>" + session.getId() + "</b></span><br/>");
		out.println("<span style='float:left;margin-left:20px;'><b>Customers ID : </b></span>");		
		out.println("<input type='submit' class='link' name='customerId' value='" + accountUserId + "'></input>");
		out.println("</form>");
		out.println("<span style='float:left;margin-left:20px;'><b>Name : </b>" + firstname + " " + lastname + "</span><br/><br/>");
		out.println("<span style='float:left;margin-left:20px;'><b>Address : </b>" + address + "</span><br/><br/>");
		out.println("<span style='float:left;margin-left:20px;'><b>E-mail : </b>" + email + "</span><br/><br/>");
		out.println("<span style='float:left;margin-left:20px;'><b>Amount : </b>" + amount + " " + "baht</span><br/><br/>");
		out.println("<center>");

		if ((Double.isNaN(amount)) || (amount == 0.0)) {
			out.println("<form>");
			out.println("<input type='submit' class='textButtonRed' value='Not Transfer' disabled></input>");
			out.println("</form>");
		} else {
			out.println("<form action='transfer' method='POST'>");
			out.println("<input type='submit' class='textButtonRed' value='Transfer'></input>");
			out.println("</form>");
		}

		out.println("<form action='loan' method='POST'>");
		out.println("<input type='submit' class='textButtonGreen' value='Loan'></input>");
		out.println("</form>");
		out.println("</center>");
		out.println("<form action='logout' method='POST'>");
		out.println("<input type='submit' class='login_submit' value='LOG OUT' style='float:right;margin-right:10px;'></input>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}