package blogproject;


import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class BlogServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().print("hello world");
	}
}