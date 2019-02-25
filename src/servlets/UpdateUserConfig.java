package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		validateUserDataAndUpdate(conn.getConnection(), request, response);
	}

	private void validateUserDataAndUpdate(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		PreparedStatement stmt = null;
		Integer option = Integer.parseInt(request.getParameter("option"));
		switch(option) {
			case 1:
				System.out.println("Avatar Change");
				break;
			case 2:
				System.out.println("Password Change");
				
				break;
			case 3:
				System.out.println("Email Change");
				break;
			case 4:
				System.out.println("Name and LastName Change");
				break;
			case 5:
				System.out.println("Sex and BirthDate Change");
				break;
			default:
				System.out.println("Error Case");
				break;
		}
		
	}
}
