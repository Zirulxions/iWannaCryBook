package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.DataBase;
import utility.PropertiesReader;
import utility.StandardResponse;

/**
 * Servlet implementation class Friends
 */
@WebServlet("/Friends")
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friends() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			addFri3nd(conn.getConnection(),request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doGet(request, response);
	}
	@SuppressWarnings("resource")
	private void addFri3nd(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		ObjectMapper objMapper = new ObjectMapper();
		@SuppressWarnings("rawtypes")
		StandardResponse<?> resp = new StandardResponse();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		String user_username = (String) session.getAttribute("usr");
		Integer user_id = (Integer) session.getAttribute("usid");
		System.out.println("Add Friend!");
		String addFriends = request.getParameter("addFriends");
		stmt = connection.prepareStatement(prop.getValue("query_insertFriend"));
		stmt.setInt(2, (Integer) session.getAttribute("usid"));
		
	}
	
}
