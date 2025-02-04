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
 	<body id="body" onload="checkCookie()">
 	
 	
 		<%-- colby is now driving --%>
 			
 		
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
	    
	    
 	<button onclick="changeColor();">Change color</button>
	<button onclick="resetColor();">Reset color</button>
	<br><br>
	    <%
	    
	%>
	<div class="center">
	 <form action="/sign" method="post">
	 	<p>Title:</p>
	 	<div class="cent"><textarea type="text" name="title" rows="1" cols="30"></textarea></div>
	 	<p>Post Content:</p>
	 	<div class="cent"><textarea width="100%" name="content" rows="5" cols="60"></textarea></div>
	 	<br>
	 	<div class="cent"><input class="button" type="submit" value="Publish Blog Post" ></div>
	 	<br><br>
	 	<input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>	    
	 	<a href="/" class="button">Cancel</a>
	 </form>
 	</div>
 	</body>
 
 </html>