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

import utility.DataBase;
import utility.LikeInnerClass;
import utility.LikeResponse;
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
		getLikes(conn.getConnection(), request, response);
	}

	private void getLikes(Connection connection, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		@SuppressWarnings("rawtypes")
		LikeResponse<?> resp = new LikeResponse();
		PreparedStatement stmt = null;
		ResultSet result = null;
		String[] postsId = ((String) request.getParameter("arrid")).split(",");
		Integer[] postArrId = new Integer[postsId.length];
		int x = 0;
		for(String oldStr : postsId) {
			postArrId[x] = Integer.parseInt(oldStr);
			x++;
		}
		Integer[] postLike = new Integer[postsId.length];
		Integer[] postDislike = new Integer[postsId.length];
		try {
			for(int i = 0; i < postsId.length; i++) {
				stmt = connection.prepareStatement(prop.getValue("getLike"));
				stmt.setInt(1, Integer.parseInt(postsId[i]));
				result = stmt.executeQuery();
				if(result.next()) {
					postLike[i] = result.getInt("count");
				} else {
					postLike[i] = 0;
				}
			}
			stmt = null;
			result = null;
			for(int i = 0; i < postsId.length; i++) {
				stmt = connection.prepareStatement(prop.getValue("getDislike"));
				stmt.setInt(1, Integer.parseInt(postsId[i]));
				result = stmt.executeQuery();
				if(result.next()) {
					postDislike[i] = result.getInt("count");
				} else {
					postDislike[i] = 0;
				}
			}
			resp.setPostsId(postArrId);
			resp.setPostsLikes(postLike);
			resp.setPostsDislikes(postDislike);
			resp.setStatus(200);
			resp.setMessage("Loaded Post's Likes");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp.setMessage("Internal Server Error");
			resp.setStatus(400);
		}
		String res = objMapper.writeValueAsString(resp);
		response.getWriter().print(res);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doLike(conn.getConnection(), request, response);
	}

	@SuppressWarnings("resource")
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
			stmt.setInt(2, likeInnerClass.getPostId());
			result = stmt.executeQuery();
			if(result.next()) {
				Integer typeLike = result.getInt("type_like_id");
				System.out.println(typeLike);
				stmt = connection.prepareStatement(prop.getValue("deleteLike"));
				stmt.setInt(1, userId);
				stmt.setInt(2, likeInnerClass.getPostId());
				stmt.setInt(3, typeLike);
				stmt.executeUpdate();
				if (typeLike == 1) {
					resp.setMessage("Like Deleted");
				} else if (typeLike == 2) {
					resp.setMessage("Dislike Deleted");
				}
				resp.setStatus(200);
				resp.setRedirect(null);
				resp.setData(likeInnerClass);
			} else {
				stmt = connection.prepareStatement(prop.getValue("newLike"));
				stmt.setInt(1, userId);
				stmt.setInt(2, likeInnerClass.getPostId());
				stmt.setInt(3, likeInnerClass.getTypeLike());
				stmt.executeUpdate();
				if(likeInnerClass.getTypeLike() == 1) {
					resp.setMessage("Like added... <3");
				} else if (likeInnerClass.getTypeLike() == 2){
					resp.setMessage("Dislike added... Bitch");
				}
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
