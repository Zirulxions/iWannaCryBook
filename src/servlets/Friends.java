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
import utility.FriendsResponse;
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
		getFriendList(conn.getConnection(),request,response);
	}

	private void getFriendList(Connection connection, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		@SuppressWarnings("rawtypes")
		FriendsResponse<?> resp = new FriendsResponse();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		ResultSet result = null;
		Integer[] friendsId;
		Integer counter = null;
		String[] friendsUserName = null;
		try {
			System.out.println("Getting Friends...");
			stmt = connection.prepareStatement(prop.getValue("selectFriendCount"));
			stmt.setInt(1, (Integer) session.getAttribute("usid"));
			result = stmt.executeQuery();
			if(result.next()) {
				counter = result.getInt("count");
			}
			if(counter > 0) {
				resp.setFriendCounter(counter);
				result = null;
				stmt = null;
				friendsId = new Integer[counter];
				friendsUserName = new String[counter];
				stmt = connection.prepareStatement(prop.getValue("selectFriends"));
				stmt.setInt(1, (Integer) session.getAttribute("usid"));
				result = stmt.executeQuery();
				Integer i = 0;
				while (result.next()) {
					friendsId[i] = result.getInt("user_id2");
					i++;
				}
				stmt = null;
				result = null;
				System.out.println("Friend ID 1: " + friendsId[0]);
				for (Integer x = 0; x < i; x++) {
					stmt = connection.prepareStatement(prop.getValue("selectUsersById"));
					stmt.setInt(1, friendsId[x]);
					result = stmt.executeQuery();
					if(result.next()) {
						friendsUserName[x] = result.getString("user_username");
					}
				}
				resp.setFriendsId(friendsId);
				resp.setFriendsUserName(friendsUserName);
				resp.setStatus(200);
				resp.setMessage("Woah! You have Friends!!");
				String res = objMapper.writeValueAsString(resp);
				response.getWriter().print(res);
			} else {
				resp.setMessage("No friends... Noob");
				resp.setStatus(200);
				resp.setFriendsId(null);
				resp.setFriendsUserName(null);
				String res = objMapper.writeValueAsString(resp);
				response.getWriter().print(res);
			}
			result.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
		boolean valid = false;
		try {
			System.out.println("Add Friend!");
			Integer user_id = (Integer) session.getAttribute("usid");
			Integer user_idFriend = null;
			stmt = connection.prepareStatement(prop.getValue("getUserId"));
			stmt.setString(1, friendInnerClass.getUserFriend());
			result = stmt.executeQuery();
			if(result.next()) {
				user_idFriend = result.getInt("user_id");
				friendInnerClass.setUserFriendId(user_idFriend);
				if(user_idFriend != null && user_idFriend > 0) { // Usar camel case
					stmt = null;
					stmt = connection.prepareStatement(prop.getValue("insertFriend"));
					stmt.setInt(1, user_id);
					stmt.setInt(2, user_idFriend);
					stmt.executeUpdate();
					valid = true;
				} else {
					valid = false;
				}
			} // else para result.next()
			stmt.close();
			result.close();
			connection.close();
			if(valid) {
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
