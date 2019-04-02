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

	@SuppressWarnings("unused")
	private void addFri3nd(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException, JsonParseException, JsonMappingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		FriendInnerClass friendInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), FriendInnerClass.class);
		Response<FriendInnerClass> resp = new Response<>();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		ResultSet result = null;
		boolean valid;
		try {
			System.out.println("Add Friend!");
			Integer user_id = (Integer) session.getAttribute("usid");
			Integer user_idFriend = null;
			stmt = connection.prepareStatement(prop.getValue("query_getUserId"));
			stmt.setString(1, friendInnerClass.getUserFriend());
			result = stmt.executeQuery();
			if(result.next()) {
				user_idFriend = result.getInt("user_id");
				friendInnerClass.setUserFriendId(user_idFriend);
				if(user_idFriend != null) {
					stmt = null;
					stmt = connection.prepareStatement(prop.getValue("query_insertFriend"));
					stmt.setInt(1, user_id);
					stmt.setInt(2, user_idFriend);
					stmt.executeUpdate();
					valid = true;
				} else {
					valid = false;
				}
			}
			stmt.close();
			result.close();
			connection.close();
			if(valid = true) {
				resp.setStatus(200);
				resp.setMessage("Successfully Added!");
				resp.setRedirect(null);
				resp.setData(friendInnerClass);
			} else {
				resp.setStatus(500);
				resp.setMessage("Something is not good");
				resp.setRedirect(null);
				resp.setData(null);
			}
			String res = objMapper.writeValueAsString(resp);
			response.getWriter().print(res);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			resp.setData(null);
			resp.setRedirect(null);
			resp.setStatus(400);
			resp.setMessage("Server Error. Call an Admin");
			String res = objMapper.writeValueAsString(resp);
			response.getWriter().print(res);
		}
		
	}
	
}
