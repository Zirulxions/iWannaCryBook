package servlets;

import java.io.IOException;
import java.sql.Connection;
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
	
	private void doLike(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		LikeInnerClass likeInnerClass  = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), LikeInnerClass.class);
		Response<LikeInnerClass> resp = new Response<>();
		HttpSession session = request.getSession();
		//PreparedStatement stmt = null;
		likeInnerClass.setUserId((Integer) session.getAttribute("usid"));
		
		//trai kach
		
		resp.setMessage("Operation Successfull!");
		resp.setStatus(200);
		resp.setRedirect(null);
		resp.setData(likeInnerClass);
		String res = objMapper.writeValueAsString(resp);
		response.getWriter().print(res);
	}
}
