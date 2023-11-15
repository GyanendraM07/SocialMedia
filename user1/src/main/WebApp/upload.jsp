<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
        div{
           
            height: 17.6cm;
            width: 17cm;
            margin-left: 8.5cm;
            margin-top: -1.25cm;
            background-image: url('https://i.pinimg.com/736x/d7/73/05/d77305f816c7e2a37dae14b8f32603e5.jpg');
        }
        label{
            color: aliceblue;
            font-size: 25px;
            padding-left: 2cm;
            font-family: sans-serif;
        }
       #spa{
        margin-left: 4.4cm;
       }
       .h1{
        font-size: 1cm;
        font-family: sans-serif;

       }
        .in{
            height: 0.7cm;
            width: 9cm;
            font-size: 20px;
            border-radius: 8px;
            border-color: white;
            background-color: whitesmoke;

        }
        #btn{
            margin-left: 7cm;
            height: 1.2cm;
            width: 3cm;
            font-size: 20px;
            background-color: red;
            color: white;
            border-radius: 8px;
            border-color: red;
            
        }
         button{
            width: 2cm;
            background-color:red;
            border-radius: 6px;
            color: white;
        }
        a{
        text-decoration: none;
        }

    </style>
</head>
<body style="background-image: url('https://images.pexels.com/photos/1629236/pexels-photo-1629236.jpeg?auto=compress&cs=tinysrgb&w=600');">
  <a href="first.jsp"><button> <i class="fa fa-long-arrow-left" style="font-size: 36px;"></i></button></a>
        <div>
        
<Span id="spa" class="h1" style="color: cyan;">M</Span>
<Span class="h1" style="color:salmon;">U</Span>
<Span class="h1" style="color:yellow">S</Span>
<Span class="h1" style="color: blueviolet;">I</Span>
<Span class="h1" style="color:blueviolet">C</Span>
<Span class="h1" style="color: yellow">A</Span>
<Span class="h1" style="color: salmon;">P</Span>
<Span class="h1" style="color: cyan;">P</Span>
<br>
            <form method="post" action="data"enctype="multipart/form-data" >
                <label>SongName</label>&nbsp;&nbsp;&nbsp;
               <input class="in" type="text" name="song"><br><br>
               <label>SingerName</label>&nbsp;&nbsp;&nbsp;
                <input class="in" type="text" name="singer"><br><br>
                <label>MusicDirector</label>&nbsp;&nbsp;&nbsp;
                <input class="in" type="text" name="musicd"><br><br>
                <label>ReleaseDate</label>&nbsp;&nbsp;&nbsp;
                <input class="in" type="date" name="date"><br><br>
                <label>UploadFile</label>&nbsp;&nbsp;&nbsp;
               <input class="in" type="file" name="file"><br><br>
                <input id="btn" onclick="fun()" type="submit" value="Upload">
                </form>
        </div>
        <script>
        function fun(){
        	alert("File successfully uploaded")
        }
        </script>
</body>
</html>