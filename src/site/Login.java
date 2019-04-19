package site;

import java.io.IOException;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import repos.User;
import repos.UserRepositiry;

@WebServlet("/login")
public class Login extends HttpServlet {
	/**
	 * 
	 */

	private static final long serialVersionUID = -5963175100243971365L;
	@Resource(name = "conn")
	private DataSource source;

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("LOGIN");
		String password = req.getParameter("PASSWORD");
		System.out.println(source == null);
		UserRepositiry repository = new UserRepositiry(source);
		User us = repository.verifyUser(login, password);
		if (us == null) {
			resp.getOutputStream().println("CANT login. Invalid login or password");
		} else {
			req.setAttribute("USER", us);
			req.setAttribute("DATA", source);
			RequestDispatcher disp = getServletContext().getRequestDispatcher("/resources");
			req.getSession(true);
			disp.forward(req, resp);
		}
		System.out.println(us);
	}
}
