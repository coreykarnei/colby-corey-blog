package blogproject;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		try {
			

			_logger.info("Cron Job has been executed");
			
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			
		 	UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
		    

		    String emailList = req.getParameter("emailList");
	        Key listKey = KeyFactory.createKey("List", emailList);
		    Entity emailEntry = new Entity("EmailEntry", listKey);
	        emailEntry.setProperty("user", user);
	        emailEntry.setProperty("email", user.getEmail());
	        
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	        datastore.put(emailEntry);
		    
		    Query query = new Query("List", listKey)
			    	.addSort("date", Query.SortDirection.DESCENDING);
			List<Entity> Emails = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10000));
		    Key blogKey = KeyFactory.createKey("Blog", "main");
			Query query1 = new Query("Post", blogKey)
			    	.addSort("date", Query.SortDirection.DESCENDING);
			
			
			for (Entity email : Emails) {
				try {
				      Message msg = new MimeMessage(session);
				      msg.setFrom(new InternetAddress("bot@colby-corey-blog.appspotmail.com", "colby-corey-blog bot"));
				      msg.addRecipient(Message.RecipientType.TO,
				                       new InternetAddress((String) user.getEmail(), "Blog User"));
				      msg.setSubject("24 Hour Update");
				      msg.setText("Thank you for subscribing to updates!");
				      _logger.info("Emailed: " + (String) email.getProperty("email"));
				      Transport.send(msg);
			    } catch (AddressException e) {
				      _logger.info("Email address invalid.");
			    } catch (MessagingException e) {
			    	_logger.info("Caught messaging exception.");
			    } catch (UnsupportedEncodingException e) {
			    	_logger.info("Caught unsupported encoding exception.");
			    }
			}
			

			
			
		}
		catch (Exception ex) {
			//Log any exceptions in your Cron Job
		}
	}
	@Override

	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}
}