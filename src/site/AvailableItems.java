package site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.websocket.Session;

import repos.Resource;
import repos.ResourceRepository;
import repos.User;

@WebServlet("/resources")
public class AvailableItems extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3094313479020393965L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getAttribute("USER");
		DataSource source = (DataSource) req.getAttribute("DATA");
		System.out.println(req.getQueryString() + " in items");
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=windows-1250");
		out.println(" 	<form action= /DB/resources" + " method=\"get\">");

		out.println("<input type=\"submit\" name=\"button\" value=\"LOGOUT\" /> ");
		out.println("</form>");
		out.println("<h2>Your Available items</h2>");
		if (user == null && source == null) {
			req.getSession().invalidate();
			System.out.println("invalidation");
			RequestDispatcher disp = getServletContext().getRequestDispatcher("/login");
			out.close();
			// disp.forward(req, resp);
		} else {
			ResourceRepository repos = new ResourceRepository(source);
			List<Resource> list = repos.getList(user.getId());

			out.println("<ol>");
			int counter = 0;
			for (Resource resource : list) {
				out.println("<li> <a href=\"info?id=" + resource.getId() + "\"> "
						+ resource.getName() + "</a></li>");
				req.setAttribute("RES" + counter, resource);

			}
			req.setAttribute("DATA", source);
			req.setAttribute("LIST", list);
			out.close();

		}
	}

	
	

}
