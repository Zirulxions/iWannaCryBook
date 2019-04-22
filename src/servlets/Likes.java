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

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.CommentInnerClass;
import utility.DataBase;
import utility.LikeInnerClass;
import utility.PropertiesReader;
import utility.Response;

@WebServlet("/Likes")
public class Likes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
	
    public Likes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doLike(conn.getConnection(), request, response);
	}

	private void doLike(Connection connection, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		HttpSession session = request.getSession();
		PropertiesReader prop = PropertiesReader.getInstance();
		Integer userId = (Integer) session.getAttribute("usid");
		PreparedStatement stmt = null;
		ResultSet result = null;
		LikeInnerClass likeInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), LikeInnerClass.class);
		Response<LikeInnerClass> resp = new Response<>();
		try {
			likeInnerClass.setUserId(userId);
			stmt = connection.prepareStatement(prop.getValue("validateLike"));
			stmt.setInt(1, userId);
			result = stmt.executeQuery();
			if(result.next()) {
				resp.setMessage("Like already exist...!");
				resp.setStatus(200);
				resp.setRedirect(null);
				resp.setData(likeInnerClass);
			} else {
				stmt = connection.prepareStatement(prop.getValue("newLike"));
				stmt.setInt(1, userId);
				stmt.setInt(2, likeInnerClass.getPostId());
				stmt.setInt(3, likeInnerClass.getTypeLike());
				stmt.executeUpdate();
				resp.setMessage("Like added");
				resp.setStatus(200);
				resp.setRedirect(null);
				resp.setData(likeInnerClass);
			}
			stmt.close();
			result.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp.setData(null);
			resp.setMessage("Internal Server Error");
			resp.setRedirect(null);
			resp.setStatus(400);
		}
		String res = objMapper.writeValueAsString(resp);
		response.getWriter().print(res);
	}
}
