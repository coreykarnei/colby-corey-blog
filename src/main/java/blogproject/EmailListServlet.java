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

 

public class EmailListServlet extends HttpServlet {
 

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

    	
        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

        // Colby is driving now:
        
        // Setting up datastore for email cronjob:
        String emailList = req.getParameter("emailList");
        Key listKey = KeyFactory.createKey("List", emailList);
        Entity emailEntry = new Entity("EmailEntry", listKey);
        emailEntry.setProperty("user", user);
        emailEntry.setProperty("email", user.getEmail());
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(emailEntry);

        resp.sendRedirect("/blog.jsp?blogName=" + emailList);

    }

}

