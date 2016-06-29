import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LoanDetail extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		RequestDispatcher dispatcher;
		AccountUser accountUser = (AccountUser) request.getSession().getAttribute("accountUser");

		String accountUserId = accountUser.getAccountUserId();
		String firstname = accountUser.getFirstname();
		String lastname = accountUser.getLastname();
		String amountInterest = request.getParameter("amountInterest");
		String dayOfInterest = request.getParameter("dayOfInterest");
		String interest = request.getParameter("interest");

		double interestMoney = (Double.parseDouble(amountInterest) * .01) * Double.parseDouble(dayOfInterest);
		double totalAmount = Double.parseDouble(amountInterest) + interestMoney;

		DecimalFormat formatter = new DecimalFormat("#,###.00");

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		String dateTime = dateFormat.format(date);

		
		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() +  "/css/loan.css' />");
		out.println("<title>Bank Service</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='login_page'>");
		out.println("<div class='login_form'>");
		out.println("<div class='login_header'><h2>CONFIRM LOAN</h2></div>");
		out.println("<form action='loandetailinsertdatabase' method='POST'>");
		out.println("<label><b>Account : </b>" + accountUserId + "</label><br/><br/>");
		out.println("<label><b>Name : </b>" + firstname + " " + lastname + "</label><br/><br/>");
		out.println("<label><b>Money lent : </b>" + formatter.format(Double.parseDouble(amountInterest)) + " Baht</label><br/><br/>");
		out.println("<label><b>Day : </b>" + request.getParameter("dayOfInterest") + " days</label>");
		out.println("<label><b>Interest : </b>" + request.getParameter("interest") + "%</label><br/><br/>");
		out.println("<label><b>Interest Money : </b>" + formatter.format(interestMoney) + " Baht</label><br/><br/>");
		out.println("<label><b>Total Cash : </b>" + formatter.format(totalAmount) + " Baht</label><br/><br/>");
		out.println("<label><b>Date/Time : </b>" + dateTime + "</label><br/><br/>");
		out.println("<input type='submit' class='login_submit' name='OK' value='OK'></input>");
		out.println("<input type='submit' class='login_submit' name='CANCEL' value='CANCEL'></input>");
		out.println("<input type='hidden' name='account' value='" + accountUserId + "'>");
		out.println("<input type='hidden' name='cash' value='" + amountInterest + "'>");
		out.println("<input type='hidden' name='interestMoney' value='" + interestMoney + "'>");
		out.println("<input type='hidden' name='interest' value='" + request.getParameter("interest") + "'>");
		out.println("<input type='hidden' name='dayOfInterest' value='" + request.getParameter("dayOfInterest") + "'>");
		out.println("<input type='hidden' name='totalCash' value='" + totalAmount + "'>");
		out.println("<input type='hidden' name='dateTime' value='" + dateTime + "'>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}