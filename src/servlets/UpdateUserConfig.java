package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.DataBase;
import utility.Encrypt;
import utility.InnerClass;
import utility.PropertiesReader;
import utility.Response;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			validateUserDataAndUpdate(conn.getConnection(), request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void validateUserDataAndUpdate(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, SQLException, JSONException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		PreparedStatement stmt = null;
		JSONObject jsonRet = new JSONObject();
		Integer option = Integer.parseInt(request.getParameter("option"));
		switch(option) {
			case 1:
				System.out.println("Avatar Change");
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
				break;
			case 4:
				System.out.println("Name and LastName Change");
				String newName = request.getParameter("newName");
				String newLName = request.getParameter("newLName");
				break;
			case 5:
				System.out.println("Sex and BirthDate Change");
				String sex = request.getParameter("sex");
				String birthdate = request.getParameter("birthdate");
				break;
			default:
				System.out.println("Error Case");
				break;
		}
		
		
	}
}
