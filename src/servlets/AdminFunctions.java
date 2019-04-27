package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.AdminResponse;

@WebServlet("/AdminFunctions")
public class AdminFunctions extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		doGet(request, response);
	}

}
