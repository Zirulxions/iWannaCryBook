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
		
	}
}
