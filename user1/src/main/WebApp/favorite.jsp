<%@page import="com.user1.model.UserFavdata"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>jjhjh</h1>
<%
List<UserFavdata> user=(List<UserFavdata>)request.getAttribute("list");
%>
	<h1>View Details</h1>
	<h3>UserPath</h3>>
	<%
	for(UserFavdata s:user){
				%>
		
	
		<input id="demo" name="filename" value="<%=s.getSong()%>" size="30">
		
		
		<%
		}
		%>
</body>
</html>