<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
<%@page import="javax.security.auth.message.callback.SecretKeyCallback.Request"%>
<%@page import="com.user1.model.UserDtata"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   
<style>
.h1{
        font-size: 1.5cm;
        font-family: sans-serif;

       }
       #di{
        background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3UiPml3mlKkyBXRLyd9L6xXzzW640Gg2_FHPfVQrv1PAWvMVquGqX-ykr8jvr56ofisw&usqp=CAU');
   
        height: 20cm;
            width: 19cm;
            margin-left: 7.5cm;
            overflow-x: hidden;
       overflow-y:scroll;
       }
       #di2{
        height: 1.7cm;
        padding-top: 0.2cm;
        padding-left: 5cm;
       }

 #btn{
        height: 0.9cm;
            width: 3cm;
            font-size: 20px;
            background-color: red;
            color: white;
            border-radius: 8px;
            border-color: red;
       }
#demo{
height: 0.9cm;
text-align:center;
            width: 13cm;
            font-size: 20px;
            border-radius: 8px;
            border-color: blue;
            color: white;
            margin-left: 1cm;
            background-image: url('https://images.pexels.com/photos/1629236/pexels-photo-1629236.jpeg?auto=compress&cs=tinysrgb&w=600');
       }
       button{
            width: 2cm;
            border-color:red;
            border-radius: 6px;
            color: white;
            background-color: red;
        }
        a{
        text-decoration: none;
        }
</style>
</head>

<body style="background-image: url('https://images.pexels.com/photos/1629236/pexels-photo-1629236.jpeg?auto=compress&cs=tinysrgb&w=600');">
  <a href="first.jsp"><button> <i class="fa fa-long-arrow-left" style="font-size: 36px;"></i></button></a>
  <div id="di">
   <div id="di2">
    <Span  class="h1" style="color: cyan;">M</Span>
    <Span class="h1" style="color:salmon;">U</Span>
    <Span class="h1" style="color:yellow">S</Span>
    <Span class="h1" style="color: blueviolet;">I</Span>
    <Span class="h1" style="color:blueviolet">C</Span>
    <Span class="h1" style="color: yellow">A</Span>
    <Span class="h1" style="color: salmon;">P</Span>
    <Span class="h1" style="color: cyan;">P</Span>
    </div><br>
    <div>
<%
List<UserDtata> user=(List<UserDtata>)request.getAttribute("list");
%>
	
	<%
	for(UserDtata s:user){
				%>
		
		

		<form  action="download">
		<input id="demo" name="filename" value="<%=s.getSong()%>" size="30">
		<input type="hidden" value="<%=s.getId()%>" name="id">
		<input id="btn" onclick="don()" type="submit" value="Download">
		</form>
		<form action="delete">
		<input type="hidden" value="<%=s.getId()%>" name="id">
		<input id="btn" onclick="del()" type="submit" value="Delete">
		</form>
		
		<%
		}
		%>
</div>
</div>
<script>
function don(){
	alert("Successfully Deleted ")
}
function del(){
	alert("Successfully Deleted ")
}
</script>
</body>
</html>