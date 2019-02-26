package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.AvatarResponse;
import utility.DataBase;
import utility.Encrypt;
import utility.PropertiesReader;

@WebServlet("/UpdateUserConfig")
@MultipartConfig
public class UpdateUserConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Encrypt encPassword;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();

    public UpdateUserConfig() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	try {
			getAvatar(conn.getConnection(), request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void getAvatar(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	ObjectMapper objMapper = new ObjectMapper();
    	AvatarResponse<?> resp = new AvatarResponse();
    	HttpSession session = request.getSession();
    	PreparedStatement stat = null;
    	stat = connection.prepareStatement(prop.getValue("query_getAvatar"));
    	stat.setString(1, (String) session.getAttribute("usr"));
    	ResultSet res = stat.executeQuery();
    	if(res.next()) {
    		String avatarURL = res.getString("user_avatar");
    		resp.setStatus(200);
    		resp.setMessage("Operation Successful.");
    		resp.setURL(avatarURL);
    	}
    	String respo = objMapper.writeValueAsString(resp);
    	System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(respo));
    	response.getWriter().print(respo);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			validateUserDataAndUpdate(conn.getConnection(), request, response);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void validateUserDataAndUpdate(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, SQLException, JSONException, ServletException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		PreparedStatement stmt = null;
		JSONObject jsonRet = new JSONObject();
		Integer option = Integer.parseInt(request.getParameter("option"));
		switch(option) {
			case 1:
				System.out.println("Avatar Change");
				Part file = request.getPart("file");
				InputStream filecontent = file.getInputStream();
				OutputStream output = null;
				if((String) session.getAttribute("usr") != null) {
					String dirBase = (prop.getValue("dirAvatarLocal") + (String) session.getAttribute("usr") + "\\Avatar\\" + this.getFileName(file));
					String dirWeb = (prop.getValue("dirAvatarWeb") + (String) session.getAttribute("usr") + "/Avatar/" + this.getFileName(file));
					File caseDelete = new File (prop.getValue("dirAvatarDelete") + (String) session.getAttribute("usr") + "/Avatar");
					if(caseDelete.exists()) {
						FileUtils.deleteDirectory(caseDelete);
					}
					caseDelete.mkdir();
					PreparedStatement stat = null;
					output = new FileOutputStream(dirBase);
					int read = 0;
					byte [] bytes = new byte[1024];
					while ((read = filecontent.read(bytes)) != -1) {
						output.write(bytes, 0, read);
					}
					stat = connection.prepareStatement(prop.getValue("query_updateAvatar"));
					stat.setString(1, dirWeb);
					stat.setString(2, (String) session.getAttribute("usr"));
					stat.executeUpdate();
					stat.close();
					jsonRet.put("status", 200).put("message", "Successful." );
					out.print(jsonRet.toString());
				}
				break;
			case 2:
				System.out.println("Password Change");
				String oldPass = request.getParameter("oldPass");
				String newPass = request.getParameter("newPass");
				encPassword = new Encrypt(oldPass);
				stmt = connection.prepareStatement(prop.getValue("query_logIn"));
				stmt.setString(1, (String) session.getAttribute("usr"));
				stmt.setString(2, encPassword.returnEncrypt());
				ResultSet res = stmt.executeQuery();
				if(res.next()) {
					stmt.close();
					res.close();
					encPassword = new Encrypt(newPass);
					stmt = connection.prepareStatement(prop.getValue("query_PasswordChange"));
					stmt.setString(1, encPassword.returnEncrypt());
					stmt.setString(2, (String) session.getAttribute("usr"));
					stmt.executeUpdate();
					stmt.close();
				}
				jsonRet.put("status",200).put("message","Password Updated!");
				out.print(jsonRet.toString());
				break;
			case 3:
				System.out.println("Email Change");
				String oldEmail = request.getParameter("oldEmail");
				String newEmail = request.getParameter("newEmail");
				stmt = connection.prepareStatement(prop.getValue("query_updateEmail"));
				stmt.setString(1, oldEmail);
				stmt.setString(2, (String) session.getAttribute("usr"));
				stmt.setString(3, newEmail);
				stmt.executeUpdate();
				stmt.close();
				jsonRet.put("status",200).put("message", "Operation Successful. Your new Email is: " + newEmail);
				out.println(jsonRet.toString());
				break;
			case 4:
				System.out.println("Name and LastName Change");
				String newName = request.getParameter("newName");
				String newLName = request.getParameter("newLName");
				if((newName.trim() != null) && (newLName.trim() != null)) {
					stmt = connection.prepareStatement(prop.getValue("query_updateName"));
					stmt.setString(1, newName);
					stmt.setString(2, newLName);
					stmt.setString(3, (String) session.getAttribute("usr"));
					stmt.executeUpdate();
					stmt.close();
					jsonRet.put("status", 200).put("message", "Done. Name: " + newName + " LastName: " + newLName + " has been updated!");
					out.print(jsonRet.toString());
				}
				break;
			default:
				System.out.println("Error Case");
				jsonRet.put("status", 404).put("message", "Forbiden. Reload the page please.");
				out.print(jsonRet.toString());
				break;
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