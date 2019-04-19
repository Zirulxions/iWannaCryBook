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

import utility.CommentInnerClass;
import utility.CommentResponse;
import utility.DataBase;
import utility.PropertiesReader;
import utility.Response;

@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();

    public Comments() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getComment(conn.getConnection(), request, response);
	}

	private void getComment(Connection connection, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		@SuppressWarnings("rawtypes")
		CommentResponse<?> resp = new CommentResponse();
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		//Response Arrays...
		String[] commentText = null;
		String[] commentUrl = null;
		Integer[] postsId = null;
		Integer[] userId = null;
		String[] userUsername = null;
		
		String postIdString = request.getParameter("arrid");
		String[] postsIdStr = postIdString.split(",");
		for(int i = 0; i < postsIdStr.length; i++) {
			System.out.println("Array Spot (String) " + i + ": " + postsIdStr[i]);
		}
		Integer[] postsIntArray = new Integer[postsIdStr.length];
		int i = 0;
		int x = 0;
		for(String oldStr : postsIdStr) {
			postsIntArray[x] = Integer.parseInt(oldStr);
			x++;
		}
		/*
		for(i = 0; i < postsIdStr.length; i++) {
			System.out.println("Array Spot (Integer) " + i + ": " + postsIntArray[i]);
		}
		*/
		try {
			for(i = 0; i < postsIntArray.length; i++) {
				stmt = connection.prepareStatement(prop.getValue("selectComments"));
				stmt.setInt(1, postsIntArray[1]);
				result = stmt.executeQuery();
				while(result.next()) {
					
				}
				stmt = null;
				result = null;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		resp.setPostId(postsIntArray);
		resp.setMessage("Operation Successfull!");
		resp.setStatus(200);
		String res = objMapper.writeValueAsString(resp);
		response.getWriter().print(res);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doComment(conn.getConnection(), request, response);
	}

	private void doComment(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		CommentInnerClass commentInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), CommentInnerClass.class);
		Response<CommentInnerClass> resp = new Response<>();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		String userLoggedUsername = (String) session.getAttribute("usr");
		Integer userLoggedId = (Integer) session.getAttribute("usid");
		if((userLoggedUsername.trim()) != null && (userLoggedId != null)) {
			commentInnerClass.setUserId(userLoggedId);
			try {
				System.out.println("Create New Comment");
				stmt = connection.prepareStatement(prop.getValue("newComment"));
				stmt.setString(1, commentInnerClass.getCommentText());
				stmt.setString(2, commentInnerClass.getCommentUrl());
				stmt.setInt(3, commentInnerClass.getUserId());
				stmt.setInt(4, commentInnerClass.getPostId());
				stmt.executeUpdate();
				stmt.close();
				connection.close();
				resp.setMessage("Operation Successfull!");
				resp.setStatus(200);
				resp.setRedirect(null);
				resp.setData(commentInnerClass);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				resp.setMessage("Unable to post comment...!");
				resp.setStatus(400);
				resp.setRedirect(null);
				resp.setData(commentInnerClass);
			}
			String res = objMapper.writeValueAsString(resp);
			response.getWriter().print(res);
		} else {
			System.out.println("User Not Logged");
			resp.setMessage("Unable to post comment...!");
			resp.setStatus(500);
			resp.setRedirect(null);
			resp.setData(commentInnerClass);
			String res = objMapper.writeValueAsString(resp);
			response.getWriter().print(res);
		}
	}
}
