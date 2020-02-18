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

import java.util.ArrayList;
import java.util.Date;

 

public class SignBlogServlet extends HttpServlet {
 

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

        // Colby is driving now:
        
        // Setting up datastore for blog:
        String blogName = req.getParameter("blogName");
        Key blogKey = KeyFactory.createKey("Blog", blogName);
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Date date = new Date();
        Entity post = new Entity("Post", blogKey);
        post.setProperty("user", user);
        post.setProperty("date", date);
        post.setProperty("content", content);
        post.setProperty("title", title);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(post);

        resp.sendRedirect("/blog.jsp?blogName=" + blogName);

    }

}

