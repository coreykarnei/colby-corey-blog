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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;

import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		try {
			

			_logger.info("Cron Job has been executed");
			
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			//TODO: Retrieve list of emails or entities that are currently subscribed.

			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Filter subscribedFilter = new FilterPredicate("subscribed", FilterOperator.EQUAL, true);
			
			Query query = new Query("SubscribedUser").setFilter(subscribedFilter);
		    PreparedQuery results = datastore.prepare(query);
		    
		    for(Entity subscribedUserEntity : results.asIterable()) {
		    	
		    	String email = (String) subscribedUserEntity.getProperty("email");
		    	Boolean isSubscribed = (Boolean) subscribedUserEntity.getProperty("subscribed");
		    	_logger.info(email + " is subscribed! Sending them a daily report.");
		    	SendMail.send(email, "update");
		    	
		    }
			
		}
		catch (Exception ex) {
			_logger.info(ex.getMessage());
		}
	}
	@Override

	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}
}