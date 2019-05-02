package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.DataBase;
import utility.EmailInnerClass;
import utility.PropertiesReader;
import utility.Response;
import utility.Email;

@WebServlet("/SendEmail")
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
	Email ema = new Email();

    public SendEmail() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			email(conn.getConnection(), request, response);
		} catch (MessagingException e) {
			e.getMessage();
		}
	}
	private void email(Connection connection, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, AddressException, MessagingException {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		EmailInnerClass emailInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), EmailInnerClass.class);
		Response<EmailInnerClass> resp = new Response<>();
		//HttpSession session = request.getSession();
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			System.out.println("Email");
			stmt = connection.prepareStatement(prop.getValue("email"));
			stmt.setString(1, emailInnerClass.getEmail());
			result = stmt.executeQuery();
			if(result.next()) {
				String email = result.getString("user_email");
				ema.sendBEmail(email);
				System.out.println(email);
			}else {
				System.out.println("Didnt work well (email)");
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
			resp.setData(null);
			resp.setRedirect(null);
			resp.setStatus(400);
			resp.setMessage("Server Error.");
			String res = objMapper.writeValueAsString(resp);
			response.getWriter().print(res);
		}
		System.out.println("email qlq");
	}

}
