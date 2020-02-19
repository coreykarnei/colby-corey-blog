package blogproject;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class SubscriptionDataServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	
		throws IOException{
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		response.setContentType("text/html;");
	    response.getOutputStream().println("<h1>Subscription Status</h1>");
	    response.getOutputStream().println("<ul>");
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    
		Key subscribedUserKey = KeyFactory.createKey("SubscribedUser", user.getEmail());
	    Entity existingUserEntity;
		try {
			existingUserEntity = datastore.get(subscribedUserKey);

		    Boolean existingUserIsSubscribed = (Boolean) existingUserEntity.getProperty("subscribed");
		    response.getOutputStream().println("<p> Your email: " + user.getEmail() + " is currently subscribed: " + existingUserIsSubscribed + "</li>");
		    
			
		} catch (EntityNotFoundException e) {
			response.getOutputStream().println("<p> No UserEntity exists for " + user.getEmail() + " </li>");
		    
		}	
	    
    	
	    Query query = new Query("SubscribedUser");
	    PreparedQuery results = datastore.prepare(query);
	    
	    for(Entity subscribedUserEntity : results.asIterable()) {
	    	
	    	String email = (String) subscribedUserEntity.getProperty("email");
	    	Boolean isSubscribed = (Boolean) subscribedUserEntity.getProperty("subscribed");
	    	
	    	response.getOutputStream().println("<li>" + email + " - " + isSubscribed + "</li>");
	    }
	    
	    response.getOutputStream().println("</ul>");
	    response.getOutputStream().println("<p><a href=\"/subscribe\">Back</a></p>");
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		
		Boolean Status = Boolean.valueOf(request.getParameter("status"));
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String email = user.getEmail();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query("SubscribedUser");
		
		Key existingUserKey = KeyFactory.createKey("SubscribedUser", email);
		
		try {
			
			Entity existingUserEntity = datastore.get(existingUserKey);		
			existingUserEntity.setProperty("subscribed", Status);
			datastore.put(existingUserEntity);
			
			
		} catch(EntityNotFoundException e) {
			
			System.out.println("User " + user.getEmail() + " is not in datastore. creating new entity.");
			
			Entity newUserEntity = new Entity("SubscribedUser", email);
			newUserEntity.setProperty("email", email);
			
			if(Status) {
					SendMail.send(email, "welcome");
			}

			newUserEntity.setProperty("subscribed", Status);
			datastore.put(newUserEntity);
			
		}
		
		System.out.println("User" + user.getEmail() + " wants to subscribe: " + Status);
		
		//datastore.put(subscribedUserEntity);
		
		response.sendRedirect("/subscriptionData");
		
	}
}