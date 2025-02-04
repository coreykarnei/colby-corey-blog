package blogproject;


import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class BlogServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException{
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if(user != null) {

			resp.setContentType("text/plain");
			resp.getWriter().print("Welcome to our blog, " + user.getNickname());
			
		} else {
			
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			
		}
		
	}
}