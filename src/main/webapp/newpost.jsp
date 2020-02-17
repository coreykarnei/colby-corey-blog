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
 	<body>
 	
 	
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
	    	<th>
	    		<img src="blog.jpg" alt="Blog Pic" width="100%" height="50%">
	    	</th>
	    </tr>
	    
	    </table>
	    <%
	    
	%>
	<div class="center">
	 <form action="/sign" method="post">
	 <div class="cent"><textarea type="text" name="title" rows="1" cols="20"></textarea></div>
	   <div class="cent"><textarea width="100%" name="content" rows="3" cols="60"></textarea></div>
	   <div class="cent"><input class="button" type="submit" value="Post Greeting" ></div>
	   <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>	    
	   <a href="/" class="button">Cancel</a>
	 </form>
 	</div>
 	</body>
 
 </html>