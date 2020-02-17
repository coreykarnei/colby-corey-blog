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
	 		blogName = "CC Blog";
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
	    
	    
	


	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	    Key blogKey = KeyFactory.createKey("Blog", blogName);
	
	    // Run an ancestor query to ensure we see the most up-to-date
	    // 	view of the blog posts belonging to the selected blog.
	
	    Query query = new Query("Post", blogKey)
	    	.addSort("date", Query.SortDirection.DESCENDING);
	    List<Entity> posts = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	
	    if (posts.isEmpty()) {
	
	        %>
	        <p>Blog '${fn:escapeXml(blogName)}' has no posts.</p>
	        <%
	
	    } else {
	
	        %>
	        <p>Blog Name: '${fn:escapeXml(blogName)}'.</p>
	        <%
	
	        for (Entity post : posts) {
	
	            pageContext.setAttribute("post_content",
	
	                                     post.getProperty("content"));
	
	            if (post.getProperty("user") == null) {
	
	                %>
	                <p>An anonymous person wrote:</p>
	                <%
	
	            } else {
	
	                pageContext.setAttribute("post_user", post.getProperty("user"));
	
	                %>
	                <p><b>${fn:escapeXml(post_user.nickname)}</b> wrote:</p>
	                <%
	
	            }
	
	            %>
	            <blockquote>${fn:escapeXml(post_content)}</blockquote>
	            <%
	
	        }
	
	    }
	    
	    // corey is now driving
	    
	    if (user != null) {

		      pageContext.setAttribute("user", user);

			%>
			<p>Hello, ${fn:escapeXml(user.nickname)}! You can submit a post using the link below, or
		
			<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>!</p>
			
			<br>
			
			<div><input type="submit" value="Create Post" ></div>
			<%
		
			    } else {
		
			%>
			<p>Hello! Please
		
			<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
		
			to be able to post!</p>
			<%
		
			    }
	    
	%>
	 <form action="/sign" method="post">
	   <div><textarea name="content" rows="3" cols="60"></textarea></div>
	   <div><input type="submit" value="Post Greeting" ></div>
	   <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>
	 </form>
 	
 	</body>
 
 </html>