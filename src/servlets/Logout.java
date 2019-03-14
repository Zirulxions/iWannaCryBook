package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import utility.Response;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		HttpSession session = request.getSession();
		Response<?> resp = new Response<>();
        session.invalidate();
        System.out.println("Logged Out");
        resp.setMessage("Logout Successful");
        resp.setStatus(200);
        resp.setRedirect("Login.html");   	
        String res = objMapper.writeValueAsString(resp);
        System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
        response.getWriter().print(res);
	}
}
