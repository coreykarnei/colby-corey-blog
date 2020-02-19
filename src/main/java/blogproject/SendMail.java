package blogproject;

import java.io.IOException;
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
import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.*;

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


public class SendMail{
	
	static String destinationAddress;	
	private static final Logger _logger = Logger.getLogger(SendMail.class.getName());
	
	public static void send(String email, String type) {
		
		destinationAddress = email;
		
		if(type != null && type.equals("welcome")) {
			
			sendMail();
			
		} else {
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
		      msg.setSubject("24 Hour Update");
		      msg.setText("Thank you for subscribing for updates! <br> this is a test!");
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

