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
<%@ page import="java.time.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


 
 <html>
 
	<head>
		<link type="text/css" rel="stylesheet" href = "/stylesheets/main.css" />
	</head>
 	<body id="body" onload="checkCookie()">
 	
 	
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
	    <script>
	    var col = document.getElementById("body");
	    var colors = ["#0074D9", "#7FDBFF", "#39CCCC", "#3D9970", "#2ECC40", "#FF851B", "#FF4136", "#85144b", "#F012BE", "#B10DC9"];
	    var colorIndex = 0;
	    function setCookie(cname,cvalue,exdays) {
	    	document.cookie = "color=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
	    	  var d = new Date();
	    	  d.setTime(d.getTime() + (exdays*24*60*60*1000));
	    	  var expires = "expires=" + d.toGMTString();
	    	  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	    	}

	    	function getCookie(cname) {
	    	  var name = cname + "=";
	    	  var decodedCookie = decodeURIComponent(document.cookie);
	    	  var ca = decodedCookie.split(';');
	    	  for(var i = 0; i < ca.length; i++) {
	    	    var c = ca[i];
	    	    while (c.charAt(0) == ' ') {
	    	      c = c.substring(1);
	    	    }
	    	    if (c.indexOf(name) == 0) {
	    	      return c.substring(name.length, c.length);
	    	    }
	    	  }
	    	  return "";
	    	}

	    	function checkCookie() {
	    	  var cookiecolor=getCookie("color");
	    	  if (cookiecolor != ""){
	    		  col.style.backgroundColor = cookiecolor;
	  			
	    	  } else {
	    		  col.style.backgroundColor = "#add8e6";
	    	  }
	    	}
	    	function changeColor() {
	    		if( colorIndex >= colors.length ) {
	                colorIndex = 0;
	            }
	    		if(colorIndex==0){
	    			col.style.backgroundColor = colors[0];
		    		document.cookie = "color=#0074D9";
		            colorIndex++;
	    		}
	    		else if(colorIndex==1){
	    			col.style.backgroundColor = colors[1];
		    		document.cookie = "color=#7FDBFF";
		            colorIndex++;
	    		}
	    		else if(colorIndex==2){
	    			col.style.backgroundColor = colors[2];
		    		document.cookie = "color=#39CCCC";
		            colorIndex++;
	    		}
	    		else if(colorIndex==3){
	    			col.style.backgroundColor = colors[3];
		    		document.cookie = "color=#39CCCC";
		            colorIndex++;
	    		}
	    		else if(colorIndex==4){
	    			col.style.backgroundColor = colors[4];
		    		document.cookie = "color=#2ECC40";
		            colorIndex++;
	    		}
	    		else if(colorIndex==5){
	    			col.style.backgroundColor = colors[5];
		    		document.cookie = "color=#FF851B";
		            colorIndex++;
	    		}
	    		else if(colorIndex==6){
	    			col.style.backgroundColor = colors[6];
		    		document.cookie = "color=#FF4136";
		            colorIndex++;
	    		}
	    		else if(colorIndex==7){
	    			col.style.backgroundColor = colors[7];
		    		document.cookie = "color=#85144b";
		            colorIndex++;
	    		}
	    		else if(colorIndex==8){
	    			col.style.backgroundColor = colors[8];
		    		document.cookie = "color=#F012BE";
		            colorIndex++;
	    		}
	    		else if(colorIndex==9){
	    			col.style.backgroundColor = colors[9];
		    		document.cookie = "color=#B10DC9";
		            colorIndex++;
	    		}
	    		
	    		
	    	}
	    	function resetColor() {
	    		var value = "#add8e6"
	    			document.cookie = "color=#add8e6";
	    			col.style.backgroundColor = value;
	    		
	    		
	    	}
	</script>
	<button onclick="changeColor();">Change color</button>
	<button onclick="resetColor();">Reset color</button>
	    



<%
	    
	


	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	    Key blogKey = KeyFactory.createKey("Blog", blogName);
	
	    // Run an ancestor query to ensure we see the most up-to-date
	    // 	view of the blog posts belonging to the selected blog.
	
	    Query query = new Query("Post", blogKey)
	    	.addSort("date", Query.SortDirection.DESCENDING);
	    List<Entity> posts = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10000));
	
	    if (posts.isEmpty()) {
	
	        %>
	        <p>Blog has no posts.</p>
	        <%
	
	    } else {
	
	        for (Entity post : posts) {
	
	            pageContext.setAttribute("post_content",
	
	                                     post.getProperty("content"));

	            pageContext.setAttribute("post_title",
	
	                                     post.getProperty("title"));
	            
	            
	            
	            Date postDate = (Date) post.getProperty("date");
	            
	            LocalDateTime ldt = postDate.toInstant()
	            	      .atZone(ZoneId.of("America/Chicago"))
	            	      .toLocalDateTime();
	            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("h:mm a EEE, MMM d");
	            String formattedDate = myFormatObj.format(ldt);
	            
	            pageContext.setAttribute("post_date",
	            		
                        formattedDate);

	
                pageContext.setAttribute("post_user", post.getProperty("user"));

                //colby is driving
                
                %>
                <hr>
                <p><b> ${fn:escapeXml(post_title)} </b> by: ${fn:escapeXml(post_user.nickname)} on ${fn:escapeXml(post_date)}</p>
  
	            <p class="postContent">${fn:escapeXml(post_content)}</p>
	            <%
	
	        }
	
	    }
	    
	    %>
	    <hr>
	    
	    <a href="/" class="button">Back to Blog Home</a>
	    
	    
	    
	    <br>
 	
 	</body>
 
 </html>