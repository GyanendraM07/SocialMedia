<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
a{
    text-decoration: none;
}
.one{
    width: 15%;
    height:30px; 
    font-size:14pt;
    background-color:red;
    color:white;
    border-radius:6px;
}
 #di2{
        height: 1.7cm;
        padding-top: 0.2cm;
        padding-left: 5cm;
       }
       .h1{
        font-size: 1.5cm;
        font-family: sans-serif;

       }
       #di{
        background-image: url('https://images.pexels.com/photos/3721941/pexels-photo-3721941.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1');
   
        height: 18cm;
            width: 19cm;
            margin-left: 7.5cm;
       }
       a{
       color:white;
       }
    </style>
</head>600
<body style="background-image: url('https://images.pexels.com/photos/1629236/pexels-photo-1629236.jpeg?auto=compress&cs=tinysrgb&w=600');">
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
    </div>
    <div>
  &nbsp;&nbsp;&nbsp;&nbsp;<button class="one" > <a href="upload.jsp">UploadFile</a><br></button><br>
 <form  action="fetch" enctype="multipart/form-data">
  &nbsp;&nbsp;&nbsp;&nbsp; <input class="one"  type="submit" value="Playlist">
    </form>
    

</body>
</html>