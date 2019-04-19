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
			disp.forward(req, resp);
		} else {
			List<Resource> list = getList(user.getId(), source);

			out.println("<ol>");
			int counter=0;
			for (Resource resource : list) {
				out.println("<li>" +resource.getName() +"</li>");
				req.setAttribute("RES"+counter, resource);
				
			}
			
			out.close();
			
		}
	}
	public void redirect( Resource res,HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("FINAL",res );
		RequestDispatcher disp = getServletContext().getRequestDispatcher("/info");
		try {
			disp.forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private List<Resource> getList(int id, DataSource source) {
		ResourceRepository repos = new ResourceRepository(source);
		List<Integer> ids = repos.getResourceIds(id);
		List<Resource> list = new ArrayList<>();
		ids.stream().map(e -> repos.getResoursesForId(e)).sequential().forEach(e -> list.addAll(e));
		return list;
	}
}
