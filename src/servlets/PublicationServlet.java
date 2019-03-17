package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.DataBase;
import utility.PropertiesReader;
import utility.StandardResponse;

@WebServlet("/PublicationServlet")
@MultipartConfig
public class PublicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
	
    public PublicationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createNewPost(conn.getConnection(), request, response);
	}

	private void createNewPost(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, ServletException, SQLException {
		ObjectMapper objMapper = new ObjectMapper();
    	@SuppressWarnings("rawtypes")
		StandardResponse<?> resp = new StandardResponse();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		String user_username = (String) session.getAttribute("usr");
		if(user_username.trim() != null) {
			Integer option = Integer.parseInt(request.getParameter("option"));
			switch(option) {
				case 1:
					
					break;
				case 2:
					System.out.println("Create post with image.");
					Part file = request.getPart("upImageFile");
					InputStream filecontent = file.getInputStream();
					OutputStream output = null;
					String query_getUserID = prop.getValue("query_getUserId");
					stmt = connection.prepareStatement(prop.getValue("query_getUserId"));
					stmt.setString(1, user_username);
					break;
				case 3:
					
					break;
				default:
					System.out.println("Error Case");
					resp.setStatus(500);
		    		resp.setMessage("Forbiden. Reload The Page.");
		    		System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objMapper.writeValueAsString(resp)));
		        	response.getWriter().print(objMapper.writeValueAsString(resp));
					break;
			}
		} else {
			System.out.println("User not Logged.");
			resp.setStatus(500);
    		resp.setMessage("You are not Logged...!");
    		System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objMapper.writeValueAsString(resp)));
        	response.getWriter().print(objMapper.writeValueAsString(resp));
		}
		
	}
}
