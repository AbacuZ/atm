import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Logout extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		session.invalidate();
		dispatcher = request.getRequestDispatcher("/login.html");
		dispatcher.forward(request, response);
	}
}