package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

	private void getComment(Connection connection, HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		
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
				stmt = connection.prepareStatement(prop.getValue("newUser"));
				stmt.setString(1, commentInnerClass.getCommentText());
				stmt.setString(2, commentInnerClass.getCommentUrl());
				stmt.setInt(3, commentInnerClass.getUserId());
				stmt.setInt(4, commentInnerClass.getPostId());
				stmt.executeUpdate();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			resp.setMessage("Operation Successfull!");
			resp.setStatus(200);
			resp.setRedirect(null);
			resp.setData(commentInnerClass);
			String res = objMapper.writeValueAsString(resp);
			response.getWriter().print(res);
		} else {
			System.out.println("User Not Logged");
		}
	}
}
