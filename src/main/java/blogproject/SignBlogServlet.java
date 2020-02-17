package blogproject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.Date;

 

public class SignBlogServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignBlogServlet.class.getName());

 

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

 

        String content = req.getParameter("content");

        if (content == null) {

            content = "(No greeting)";

        }

        if (user != null) {

            log.info("Greeting posted by user " + user.getNickname() + ": " + content);

        } else {

            log.info("Greeting posted anonymously: " + content);

        }

        resp.sendRedirect("/blog.jsp");

    }

}

