package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utility.AdminStatsResponse;
import utility.DataBase;
import utility.PropertiesReader;

@WebServlet("/AdminStats")
public class AdminStats extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBase conn = new DataBase();
	PropertiesReader prop = new PropertiesReader();
	
    public AdminStats() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			getStats(conn.getConnection(), request, response);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	private void getStats(Connection connection, HttpServletRequest request, HttpServletResponse response) throws SQLException, JsonProcessingException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		PreparedStatement stmt = null;
		ResultSet result = null;
		@SuppressWarnings("rawtypes")
		AdminStatsResponse<?> admStaResp = new AdminStatsResponse();
		PropertiesReader prop = PropertiesReader.getInstance();
		System.out.println("++++++++++++++++++++++++++ ADMIN STATS ++++++++++++++++++++++++++");
		//post by Text
		stmt = connection.prepareStatement(prop.getValue("getPostByText"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setPostsByText(result.getInt("count"));
		}
		result = null;
		stmt = null;
		//post by Image
		stmt = connection.prepareStatement(prop.getValue("getPostByImage"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setPostsByImage(result.getInt("count"));
		}
		result = null;
		stmt = null;
		///post by Video
		stmt = connection.prepareStatement(prop.getValue("getPostsByVideo"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setPostsByVideo(result.getInt("count"));
		}
		result = null;
		stmt = null;
		//male Users
		stmt = connection.prepareStatement(prop.getValue("getMaleUsers"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setMaleUsers(result.getInt("count"));
		}
		result = null;
		stmt = null;
		//female Users
		stmt = connection.prepareStatement(prop.getValue("getFemaleUsers"));
		result = stmt.executeQuery();
		if(result.next()) {
			admStaResp.setFemaleUsers(result.getInt("count"));
		}
		result.close();
		stmt.close();
		connection.close();
		System.out.println("++++++++++++++++++++++++++ FINISH ++++++++++++++++++++++++++");
		admStaResp.setStatus(200);
		admStaResp.setMessage("Admin Stats Loaded.");
		response.getWriter().print(objMapper.writeValueAsString(admStaResp));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		searchByContent(conn.getConnection(), request, response);
	}

	private void searchByContent(Connection connection, HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper objMapper = new ObjectMapper();
		PropertiesReader prop = PropertiesReader.getInstance();
		PreparedStatement stmt = null;
		ResultSet result = null;
		
	}
}
