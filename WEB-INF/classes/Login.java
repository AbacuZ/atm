import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Login extends HttpServlet {

	private final DatabaseConnection databaseConnection = new DatabaseConnection();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		HttpSession session = request.getSession(true);		
		RequestDispatcher dispatcher;
		
		if (request.getParameter("username").isEmpty() || request.getParameter("password").isEmpty()) {
			dispatcher = request.getRequestDispatcher("help.html");
			dispatcher.forward(request, response);
		} else {
			String getUsername = request.getParameter("username");
			String getPassword = request.getParameter("password");

			Connection connection = null;
			PreparedStatement loginPreparedStatement = null;

			try {
				connection = databaseConnection.getDatabaseConnection();
				loginPreparedStatement = connection.prepareStatement(
					"SELECT * FROM login WHERE username = ? AND password = ?");
				loginPreparedStatement.setString(1, getUsername); 
				loginPreparedStatement.setString(2, getPassword);
				ResultSet resultSet = loginPreparedStatement.executeQuery();
		
				if (resultSet.next()) {
					User user = new User();
					user.setloginId(resultSet.getInt("loginId"));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					session.setAttribute("user", user);

					dispatcher = request.getRequestDispatcher("/dataaccountuser");
					dispatcher.forward(request, response);

				} else {
					dispatcher = request.getRequestDispatcher("help.html");
					dispatcher.forward(request, response);
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}