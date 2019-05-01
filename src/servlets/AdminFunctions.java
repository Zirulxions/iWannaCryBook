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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.AdminInnerClass;
import utility.AdminResponse;
import utility.DataBase;
import utility.Encrypt;
import utility.LogInInnerClass;
import utility.PropertiesReader;

@WebServlet("/AdminFunctions")
public class AdminFunctions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
	private Encrypt encPassword;

    public AdminFunctions() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ObjectMapper objMap = new ObjectMapper();
		@SuppressWarnings("rawtypes")
		AdminResponse<?> admresp = new AdminResponse();
		Integer usid = (Integer) session.getAttribute("usid");
		String tusr = (String) session.getAttribute("tusr");
		String usr = (String) session.getAttribute("usr");
		if(tusr.contains("admin")) {
			admresp.setHtmlScript("Javascript/isAdminFunctionsScript.js");
		} else if (tusr.contains("user")) {
			admresp.setHtmlScript("noadm");
		}
		admresp.setMessage("User ID: " + usid + " Username: " + usr + " Type User: " + tusr);
		admresp.setStatus(200);
		response.getWriter().print(objMap.writeValueAsString(admresp));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, JsonProcessingException, IOException{
		try {
			updateAsAdmin(conn.getConnection(), request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateAsAdmin(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException, SQLException {
		HttpSession session = request.getSession();
		ObjectMapper objMap = new ObjectMapper();
		AdminInnerClass admInnerClass = objMap.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), AdminInnerClass.class);
		AdminResponse<AdminInnerClass> admresp = new AdminResponse<>();
		PreparedStatement stmt = null;
		ResultSet result = null;
		String tusr = (String) session.getAttribute("tusr");
		admresp.setHtmlScript(null);
		if(tusr.contains("admin")) {
			System.out.println("+++++++++++++++++++++++++++++++ ADMIN MODE +++++++++++++++++++++++++++++++");
			switch(admInnerClass.getOption()) {
				case 1:
					System.out.println("Ban Status for: " + admInnerClass.getBannedUser());
					stmt = connection.prepareStatement(prop.getValue("getBanStatus"));
					stmt.setString(1, admInnerClass.getBannedUser());
					result = stmt.executeQuery();
					if(result.next()) {
						boolean status = result.getBoolean("user_enabled");
						stmt = connection.prepareStatement(prop.getValue("bannedUser"));
						if(status) {
							stmt.setBoolean(1, false);
						} else {
							stmt.setBoolean(1, true);
						}
						stmt.setString(2, admInnerClass.getBannedUser());
						stmt.executeUpdate();
						result.close();
						System.out.println("User's: " + admInnerClass.getBannedUser() + " ban status changed to: " + !status);
						admresp.setMessage("User's: " + admInnerClass.getBannedUser() + " ban status changed to: " + !status);
					} else {
						admresp.setMessage("User: " + admInnerClass.getBannedUser() + " doesn't exist!");
					}
					break;
				case 2:
					System.out.println("Changing user's " + admInnerClass.getUsernameEdit() + " Password to: " + admInnerClass.getPasswordEdit());
					stmt = connection.prepareStatement(prop.getValue("selectUsersByUsername"));
					stmt.setString(1, admInnerClass.getUsernameEdit());
					result = stmt.executeQuery();
					if(result.next()) {
						encPassword = new Encrypt(admInnerClass.getPasswordEdit());
						stmt = connection.prepareStatement(prop.getValue("PasswordChange"));
						stmt.setString(1, encPassword.returnEncrypt());
						stmt.setString(2, admInnerClass.getUsernameEdit());
						stmt.executeUpdate();
						System.out.println("User's " + admInnerClass.getUsernameEdit() + " Password Updated. Encrypt: " + encPassword.returnEncrypt());
						admresp.setMessage("User's " + admInnerClass.getUsernameEdit() + " Password Updated. Encrypt: " + encPassword.returnEncrypt());
					} else {
						admresp.setMessage("User: " + admInnerClass.getUsernameEdit() + " doesn't exist!");
					}
					break;
				default:
					System.out.println("Unknown Resource.");
					admresp.setMessage("Forbiden. Unknown Resource/Option: " + admInnerClass.getOption());
					break;
			}
			stmt.close();
			connection.close();
			System.out.println("+++++++++++++++++++++++++++++++ FINISHED +++++++++++++++++++++++++++++++");
			admresp.setData(admInnerClass);
			admresp.setStatus(200);
		} else if (tusr.contains("user")) {
			admresp.setData(null);
			admresp.setMessage("No Admin Response, Just in case");
			admresp.setStatus(200);
		}
		response.getWriter().print(objMap.writeValueAsString(admresp));
	}
}
