package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.AdminStatsResponse;
import utility.DataBase;
import utility.DeletedPostComment;
import utility.PostAndCommentResponse;
import utility.PropertiesReader;
import utility.Response;

@WebServlet("/AdminStats")
@MultipartConfig
public class AdminStats extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
	
    public AdminStats() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			getStats(conn.getConnection(), request, response);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	private void getStats(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException, JsonProcessingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PreparedStatement stmt = null;
		ResultSet result = null;
		@SuppressWarnings("rawtypes")
		AdminStatsResponse<?> admStaResp = new AdminStatsResponse();
		PropertiesReader prop = PropertiesReader.getInstance();
		System.out.println("++++++++++++++++++++++++++ ADMIN STATS ++++++++++++++++++++++++++");
		//post by Text
		stmt = connection.prepareStatement(prop.getValue("getPostByText"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setPostsByText(result.getInt("count"));
		}
		result = null;
		stmt = null;
		//post by Image
		stmt = connection.prepareStatement(prop.getValue("getPostByImage"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setPostsByImage(result.getInt("count"));
		}
		result = null;
		stmt = null;
		///post by Video
		stmt = connection.prepareStatement(prop.getValue("getPostsByVideo"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setPostsByVideo(result.getInt("count"));
		}
		result = null;
		stmt = null;
		//male Users
		stmt = connection.prepareStatement(prop.getValue("getMaleUsers"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setMaleUsers(result.getInt("count"));
		}
		result = null;
		stmt = null;
		//female Users
		stmt = connection.prepareStatement(prop.getValue("getFemaleUsers"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setFemaleUsers(result.getInt("count"));
		}
		result.close();
		stmt.close();
		connection.close();
		System.out.println("++++++++++++++++++++++++++ FINISH ++++++++++++++++++++++++++");
		admStaResp.setStatus(200);
		admStaResp.setMessage("Admin Stats Loaded.");
		response.getWriter().print(objMapper.writeValueAsString(admStaResp));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			searchByContent(conn.getConnection(), request, response);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	private void searchByContent(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException, JsonProcessingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		PreparedStatement stmt = null;
		ResultSet result = null;
		@SuppressWarnings("rawtypes")
		PostAndCommentResponse<?> resp = new PostAndCommentResponse();
		String dataSearch = request.getParameter("dataSearch");
		System.out.println("++++++++++++++++++++++++++ ADMIN SEARCH ++++++++++++++++++++++++++");
		
		ArrayList<String> pUsernameAvatar = new ArrayList<String>();
		ArrayList<String> postUsername = new ArrayList<String>();
		ArrayList<Integer> postId = new ArrayList<Integer>();
		ArrayList<String> postContent = new ArrayList<String>();
		stmt = connection.prepareStatement(prop.getValue("selectPostContent"));
		stmt.setString(1, "%" + dataSearch + "%");
		result = stmt.executeQuery();
		while(result.next()) {
			postUsername.add(result.getString("user_username"));
			postId.add(result.getInt("post_id"));
			postContent.add(result.getString("post_text"));
			pUsernameAvatar.add(result.getString("user_avatar"));
		}
		result = null;
		stmt = null;
		resp.setPostContent(postContent);
		resp.setPostId(postId);
		resp.setPostUsername(postUsername);
		resp.setPosttAvatar(pUsernameAvatar);
		
		ArrayList<String> cUsernameAvatar = new ArrayList<String>();
		ArrayList<String> commentUsername = new ArrayList<String>();
		ArrayList<Integer> commentId = new ArrayList<Integer>();
		ArrayList<String> commentContent = new ArrayList<String>();
		stmt = connection.prepareStatement(prop.getValue("selectCommentContent"));
		stmt.setString(1, "%" + dataSearch + "%");
		result = stmt.executeQuery();
		while(result.next()) {
			commentUsername.add(result.getString("user_username"));
			commentId.add(result.getInt("comment_id"));
			commentContent.add(result.getString("comment_text"));
			cUsernameAvatar.add(result.getString("user_avatar"));
		}
		result.close();
		stmt.close();
		connection.close();
		resp.setCommentContent(commentContent);
		resp.setCommentId(commentId);
		resp.setCommentUsername(commentUsername);
		resp.setCommentAvatar(cUsernameAvatar);
		
		resp.setMessage("Search Finished. You were looking for: " + dataSearch);
		resp.setStatus(200);
		
		System.out.println("++++++++++++++++++++++++++ FINISH ++++++++++++++++++++++++++");
		
		response.getWriter().print(objMapper.writeValueAsString(resp));
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		try {
			execDelete(conn.getConnection(), request, response);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	private void execDelete(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, SQLException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		PreparedStatement stmt = null;
		DeletedPostComment delInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), DeletedPostComment.class);
		Response<DeletedPostComment> resp = new Response<>();
		System.out.println("++++++++++++++++++++++++++ DELETE AS ADMIN ++++++++++++++++++++++++++");
		//System.out.println("Value: " + delInnerClass.getOption());
		resp.setData(null);
		resp.setRedirect(null);
		switch(delInnerClass.getOption()) {
		case 1:
			System.out.println("DELETE COMMENT");
			stmt = connection.prepareStatement(prop.getValue("deleteComment"));
			stmt.setInt(1, delInnerClass.getComment());
			stmt.executeUpdate();
			stmt.close();
			connection.close();
			resp.setMessage("Comment Deleted.!");
			resp.setStatus(200);
			break;
		case 2:
			System.out.println("DELETE POST");
			stmt = connection.prepareStatement(prop.getValue("deleteLikes"));
			stmt.setInt(1, delInnerClass.getPost());
			stmt.executeUpdate();
			stmt = connection.prepareStatement(prop.getValue("deleteComments"));
			stmt.setInt(1, delInnerClass.getPost());
			stmt.executeUpdate();
			stmt = connection.prepareStatement(prop.getValue("deletePost"));
			stmt.setInt(1, delInnerClass.getPost());
			stmt.executeUpdate();
			stmt.close();
			connection.close();
			resp.setMessage("Post Deleted..!");
			resp.setStatus(200);
			break;
		default:
			break;
		}
		System.out.println("++++++++++++++++++++++++++ FINISH ++++++++++++++++++++++++++");
		response.getWriter().print(objMapper.writeValueAsString(resp));
	}
}
