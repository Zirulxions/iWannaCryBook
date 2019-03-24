package servlets;

import java.io.FileOutputStream;
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

	@SuppressWarnings("resource")
	private void createNewPost(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, ServletException {
		boolean valid = false;
		ObjectMapper objMapper = new ObjectMapper();
    	@SuppressWarnings("rawtypes")
		StandardResponse<?> resp = new StandardResponse();
		HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		String user_username = (String) session.getAttribute("usr");
		Integer user_id = (Integer) session.getAttribute("usid");
		if(user_username.trim() != null) {
			Integer option = Integer.parseInt(request.getParameter("option"));
			switch(option) {
				case 1:
					try {
						System.out.println("Create post with text only.");
						String upTextText = request.getParameter("upTextText");
						stmt = connection.prepareStatement(prop.getValue("query_insertPost"));
						stmt.setInt(1, (Integer) session.getAttribute("usid"));
						stmt.setInt(2, option);
						stmt.setString(3, upTextText);
						stmt.setString(4, "unknown");
						stmt.executeUpdate();
						stmt.close();
						connection.close();
						valid = true;
					} catch (SQLException e) {
						System.out.println(e.getMessage());
						valid = false;
					}
					break;
				case 2:
					try {
						System.out.println("Create post with image.");
						Part file = request.getPart("upImageFile");
						InputStream filecontent = file.getInputStream();
						OutputStream output = null;
						String dirBase = (prop.getValue("dirAvatarLocal") + user_username + "\\" + this.getFileName(file));
						String dirWeb = (prop.getValue("dirAvatarWeb") + user_username + "/" + this.getFileName(file));
						stmt = connection.prepareStatement(prop.getValue("query_instertPost"));
						stmt.setInt(1, user_id);
						stmt.setInt(2, option);
						stmt.setString(3, request.getParameter("upImageText"));
						stmt.setString(4, dirWeb);
						stmt.executeUpdate();
						output = new FileOutputStream(dirBase);
						int read = 0;
						byte [] bytes = new byte[1024];
						while((read = filecontent.read(bytes)) != -1) {
							output.write(bytes, 0, read);
						}
						stmt.close();
						connection.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
						valid = false;
					}
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
			if(valid == true) {
				resp.setStatus(200);
				resp.setMessage("Post Successfully Created.");
				response.getWriter().print(objMapper.writeValueAsString(resp));
			} else if (valid == false) {
				resp.setStatus(500);
				resp.setMessage("Failed to Upload.");
				response.getWriter().print(objMapper.writeValueAsString(resp));
			}
		} else {
			System.out.println("User not Logged.");
			resp.setStatus(500);
    		resp.setMessage("You are not Logged...!");
    		System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objMapper.writeValueAsString(resp)));
        	response.getWriter().print(objMapper.writeValueAsString(resp));
		}
		
	}
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
