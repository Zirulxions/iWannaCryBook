package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.Response;
import utility.RegisterInnerClass;
import utility.DataBase;
import utility.Encrypt;
import utility.PropertiesReader;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Encrypt encPassword;
	private DataBase conn = new DataBase();

    public Register() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execRegister(conn.getConnection(), request, response);
	}
	
	public void execRegister(Connection connection, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ObjectMapper objMapper = new ObjectMapper();
    	Response<RegisterInnerClass> resp = new Response<>();
		PropertiesReader prop = PropertiesReader.getInstance();
		RegisterInnerClass rInnerClass = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), RegisterInnerClass.class);
		String user_username = rInnerClass.getUsername();
		String user_password = rInnerClass.getPassword();
		String user_name = rInnerClass.getName();
		String user_lastname = rInnerClass.getLastname();
		String user_email = rInnerClass.getEmail();
		String user_birthdate = rInnerClass.getBirthdate();
		String user_sex = rInnerClass.getSex();
		boolean boolsex = true;
		File newFolder = new File(prop.getValue("dirAvatarDelete") + "/" + user_username);
		File newAvatar = new File(prop.getValue("dirAvatarDelete") + "/" + user_username + "/Avatar");
		try {
			if(user_sex.trim() == "male") {
				boolsex = true;
			}
			if(user_sex.trim() == "female") {
				boolsex = false;
			}
        	PreparedStatement stat = null;
        	String signupQuery = prop.getValue("query_newUser");
        	encPassword = new Encrypt(user_password);
        	stat = connection.prepareStatement(signupQuery);
        	stat.setString(1, user_username);
        	stat.setString(2, encPassword.returnEncrypt());
        	stat.setString(3, user_name);
        	stat.setString(4, user_lastname);
        	stat.setString(5, user_email);
        	stat.setString(6, user_birthdate);
        	stat.setTimestamp(7, getCurrentTimeStamp());
        	stat.setBoolean(8, boolsex);
        	stat.executeUpdate();
        	System.out.println("Added to Database");
        	resp.setMessage("Operation Successful");
        	resp.setStatus(200);
        	resp.setRedirect("Login.html");
        	resp.setData(rInnerClass);
        	if (!newFolder.exists()) {
        	    System.out.println("Directory: " + newFolder.getName());
        	    boolean result = false;
        	    try{
        	        newFolder.mkdir();
        	        newAvatar.mkdir();
        	        result = true;
        	    } 
        	    catch(SecurityException se){
        	        System.out.println("Error: " + se.getMessage());
        	    }        
        	    if(result) {    
        	        System.out.println("Folder Created!");
        	        
        	    } else {
        	    	System.out.println("Folder Already Exists.");
        	    }
        	}
        	stat.close();
        	connection.close();
        	String res = objMapper.writeValueAsString(resp);
        	System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
        	response.getWriter().print(res);
        } catch (SQLException e) {
        	System.out.println("Error: "+e.getMessage());
        }
	}
	
	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
}



//Numero de la chama de los brazo gitanos: 0412-5800417 Soberanos: 20000
