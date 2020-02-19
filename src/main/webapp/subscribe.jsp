<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


 
 <html>
 
	<head>
		<link type="text/css" rel="stylesheet" href = "/stylesheets/main.css" />
	</head>
 	<body id="body">
 	 	
 		<%-- colby is now driving --%>
 			

	 	<%
	 	String blogName = request.getParameter("blogName");
	 	if(blogName == null)
	 	{
	 		blogName = "main";
	 	}
	 	
	 	pageContext.setAttribute("blogName", blogName);
	 	
	 	UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    %>    
	    
	    <table style="width:100%" id="header">
	    	<tr>
	    	
	    		<th style="width:70%" >
	   				<p color="red">CC Blog</p>
	   				<p>Welcome to our blog! Here you can post blogs and blog related things!</p>
	    		</th>
	    		
	    		<th><img src="blog.jpg" alt="Blog Pic" width="100%" height="50%"></th>
	   		 </tr>
	    
	    </table>
	    
	    
	    <div class="cent">
		    <%
			if (user != null){
			
		    	pageContext.setAttribute("user", user);
		    	%>
		    	
		    	<p>Your current email:  ${fn:escapeXml(user.email)}</p>
		    	
		    	<a class="button" href="<%= userService.createLogoutURL(request.getRequestURI()) %>">SignOut</a>
				<br><br>
		    	
		    	<form method="POST" action="/subscriptionData">
		    		<input type="radio" id="yes" name="status" value=true>
		    		<label for="yes">Subscribe</label>
		    		<input type="radio" id="no" name="status" value=false>
		    		<label for="no">Unsubscribe</label>
		    		<br>
		    		<button>Submit</button>
		    	</form>
		    	
				<%
		    } else {
				%>
				
				<p>You need to
	
				<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
	
				in order to sign up for the subscription service!</p>
				
			<%
		    } 
		    %>
		    
		    <a href="/" class="button">Cancel</a>
		    
	     
	    </div>
		
 	</body>
 
 </html>