import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Loan extends HttpServlet {
	
	private final DatabaseConnection databaseConnection = new DatabaseConnection();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");

		String accountUserId = accountUser.getAccountUserId();
		String firstname = accountUser.getFirstname();
		String lastname = accountUser.getLastname();
		double interest = 10;

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
		out.println("<div class='login_header'><h2>LOAN</h2></div>");
		out.println("<form action='loandetail' method='POST'>");
		out.println("<label><b>Account : </b>" + accountUserId + "</label><br/>");
		out.println("<label><b>Name : </b>" + firstname + " " + lastname + "</label><br/>");
		out.println("<label><b>Interest : </b>" + interest + "%</label><br/><br/>");
		out.println("<label><b>Money lent : </label>");
		out.println("<input type='number' step='0.01' name='amountInterest' onkeypress='return event.charCode >= 48 && event.charCode <= 57'></input><label> baht</b></label><br/>");
		out.println("<label><b>How many : </label>");
		out.println("<input type='number' step='0.01' name='dayOfInterest' onkeypress='return event.charCode >= 48 && event.charCode <= 57'></input><label> days</b></label><br/>");
		out.println("<input type='submit' class='login_submit' value='Next Step >'></input>");
		out.println("<input type='hidden' name='account' value='" + accountUserId + "'>");
		out.println("<input type='hidden' name='interest' value='" + interest + "'>");
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
	}
}