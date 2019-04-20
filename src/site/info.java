package site;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.annotation.Resource;

import repos.ResourceRepository;
import repos.User;

@WebServlet("/info")
public class info extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1884242451057187460L;

	@Resource(name = "conn")
	private DataSource source;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/html; charset=windows-1250");
		out.println(" 	<form action= resources" + " method=\"get\">");

		out.println("<input type=\"submit\" name=\"button\" value=\"LOGOUT\" /> ");
		out.println("</form>");
		int id = Integer.parseInt(req.getQueryString().split("=")[1]);
		System.out.println(id);
		System.out.println(req.getQueryString());
//		int idUs = Integer.parseInt(req.getQueryString().split("=[0-9]+![a-zA-Z]")[2]);
//		System.out.println(idUs + " user id");
		// DataSource source = (DataSource) req.getAttribute("DATA");
		ResourceRepository repos = new ResourceRepository(source);
		// List<repos.Resource> list = repos.getList(id);
		// System.out.println(list.size());
		// List<String> l = list.stream().filter(e -> e.getId()==id).map(e ->
		// e.getDescription())
		// .collect(Collectors.toList());
		// for (String string : l) {
		// resp.getOutputStream().println(string);
		// System.out.println(string);
		// }
		out.println(repos.getResDescrById(id));
		String ref = req.getHeader("Referer");
		int startIndex =ref.indexOf("?");
		ref = ref.substring(startIndex+1);
		System.out.println(ref);
//		Matcher matcher =(Pattern.compile("[A-Z]+ = [A-Z]+")).matcher(ref);
//		if(matcher.find()) {
//			System.out.println(matcher.group(0));
//			System.out.println(matcher.group(1));
//		}
	
//		out.println(ref);
		String[] arr =ref.split("&");
		String log =arr[0].split("=")[1];
		String pas = arr[1].split("=")[1];
		
		resp.setContentType("text/html; charset=windows-1250");
		out.println("<form action= /DB/resources>" + 
				"    <input type=\"submit\" value=\"GO BACK\" />\r\n" + 
				"</form>");
		req.setAttribute("USER", new User(1, log, pas));
		req.setAttribute("DATA", source);
	}
}
