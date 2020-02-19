package blogproject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.api.client.util.DateTime;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class SendMail{
	
	static String destinationAddress;	
	private static final Logger _logger = Logger.getLogger(SendMail.class.getName());
	
	public static void send(String email, String type) {
		
		destinationAddress = email;
		
		if(type != null && type.equals("welcome")) {
			
			sendMail();
			
		} else if (type != null && type.equals("update")) {

			sendUpdate();
		}
		
	}
	
	public static void sendMail() {
		
		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    
		try {
			
		      Message msg = new MimeMessage(session);
		      msg.setFrom(new InternetAddress("bot@colby-corey-blog.appspotmail.com", "colby-corey-blog subscription bot"));
		      msg.addRecipient(Message.RecipientType.TO,
		                       new InternetAddress(destinationAddress, "Subscribed User"));
		      msg.setSubject("You have subscribed for updates!");
		      msg.setText("Thank you for subscribing for daily blog updates! You will now recieve a daily digest at 5:00pm CST.");
		      
		      Transport.send(msg);
		      
	    } catch (AddressException e) {
		      _logger.info("Email address invalid.");
	    } catch (MessagingException e) {
	    	_logger.info("Caught messaging exception.");
	    } catch (UnsupportedEncodingException e) {
	    	_logger.info("Caught unsupported encoding exception.");
	    }
		
	}
	
	public static void sendUpdate() {

		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    
	    String content = "";
	    content += getPosts();

		try {
			
		      Message msg = new MimeMessage(session);
		      msg.setFrom(new InternetAddress("bot@colby-corey-blog.appspotmail.com", "colby-corey-blog subscription bot"));
		      msg.addRecipient(Message.RecipientType.TO,
		                       new InternetAddress(destinationAddress, "Subscribed User"));
		      msg.setSubject("CC Blog Daily Digest");
		      msg.setText("Thank you for subscribing for daily blog updates!");
		      
		      MimeBodyPart htmlPart = new MimeBodyPart();
			  htmlPart.setContent(content, "text/html");
			  Multipart multipart = new MimeMultipart();
			  multipart.addBodyPart(htmlPart);
			
		      msg.setContent(multipart);
		      
		      Transport.send(msg);
		      
	    } catch (AddressException e) {
		      _logger.info("Email address invalid.");
	    } catch (MessagingException e) {
	    	_logger.info("Caught messaging exception.");
	    } catch (UnsupportedEncodingException e) {
	    	_logger.info("Caught unsupported encoding exception.");
	    }
	}
	
	public static String getPosts() {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key blogKey = KeyFactory.createKey("Blog", "main");
		
		LocalDate today = LocalDate.now(ZoneId.of("America/Chicago"));
		LocalDate yesterday = today.minus(Period.ofDays(1));
		LocalTime five = LocalTime.of(5,00,00);
		LocalDateTime yesterdayAtFive = LocalDateTime.of(yesterday, five);
		
		LocalTime testTime = LocalTime.of(21,00,00);
		LocalDateTime testDateTime = LocalDateTime.of(today, testTime);

		Date date = Date.from(testDateTime.atZone(ZoneId.of("America/Chicago")).toInstant());
		
		Filter dayFilter = new FilterPredicate("date", FilterOperator.GREATER_THAN_OR_EQUAL, date);
		
		Query query = new Query("Post", blogKey).setFilter(dayFilter);
		List<Entity> posts = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10000));
		
		String messageContents = "<h1> 24 Hour Blog Updates </h1><p>Posts since " + ZonedDateTime.of(testDateTime, ZoneId.of("America/Chicago")) + "</p>";
		
		if (posts.isEmpty()) {
			
			messageContents += "<br><p>Blog has no new posts.</p>";
			_logger.info("No new posts since 5pm");
			
		} else {
		
			for (Entity post : posts) {
				
				String postData = "<hr><p><b>";
				
				postData += post.getProperty("title");
				
				postData += "</b> by: ";
				
	            postData += post.getProperty("user");
	            
	            postData += " on ";
	            
	            postData += post.getProperty("date");
	            
	            postData += "</p>";
	            
	            postData += post.getProperty("content");
	            
	            messageContents += postData;
	
	        }
		}
		
		return(messageContents);
		    
	}
	
}

