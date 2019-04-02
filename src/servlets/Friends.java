package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.DataBase;
import utility.FriendInnerClass;
import utility.PropertiesReader;
import utility.Response;

@WebServlet("/Friends")
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();

    public Friends() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			addFri3nd(conn.getConnection(),request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void addFri3nd(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException, JsonParseException, JsonMappingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		FriendInnerClass friendInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), FriendInnerClass.class);
		Response<FriendInnerClass> resp = new Response<>();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String user_username = (String) session.getAttribute("usr");
			Integer user_id = (Integer) session.getAttribute("usid");
			System.out.println("Add Friend!");
			String addFriends = request.getParameter("addFriends");
			stmt = connection.prepareStatement(prop.getValue("query_getUserId"));
			stmt.setString(1, addFriends);
			result = stmt.executeQuery();
			int user_idFriend = result.getInt("user_id");
			stmt = connection.prepareStatement(prop.getValue("query_insertFriend"));
			stmt.setString(2, "usid");
			stmt.setInt(3, user_idFriend);
			stmt.close();
			result.close();
			connection.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
