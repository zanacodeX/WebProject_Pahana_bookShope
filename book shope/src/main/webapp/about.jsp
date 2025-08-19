<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    boolean isLoggedIn = (userEmail != null);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Us - Pahana Edu Bookshop</title>
<style>
    html, body {
        height: 100%;
        margin: 0;
        font-family: 'Roboto', sans-serif;
    }

     body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f9fc;
            
     
    background-image: url("<%= request.getContextPath() %>/image/icon.jpeg");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center center; 
         
       background-filter: brightness(0.4);/* Centers the image */
    background-attachment: fixed; 
    background-z-index: -1;
    
     .navbar {
            background-color: #4a90e2;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: sticky;
            top: 0;
            z-index: 100;
        }
        .nav-left {
            display: flex;
            align-items: center;
        }
        .nav-left img {
            width: 500%;
            height: 100%;
            max-height: 50px;
            margin-right: 15px;
        }
        
        .nav-center a {
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            font-weight: 500;
        }
        .nav-center a:hover {
            background-color: #3d7ecb;
            border-radius: 5px;
        }
        .nav-right {
            color: white;
        }
        .nav-right a {
            color: white;
            text-decoration: none;
            margin-left: 15px;
            font-weight: bold;
        }

    .content {
        max-width: 900px;
        max-height:100%;
        margin: 30px auto;
        padding: 200px;
        background-color: #fff;
        border-radius: 10px;
        
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        flex: 2 ; /* pushes footer to bottom */
    }
    h2 {
            text-align: center;
            margin-top: 30px;
            color: Black;
            style:bold;
            position:center;
            }
            p {
            text-align: center;
            margin-top: 30px;
            color: Black;
            text-align:center;
            position:center;}

    .footer {
        background-color: #333;
        color: white;
        padding: 15px;
        text-align: center;
        position: sticky;
        /* ensure text is visible */
    }
</style>

</head>
<body>

<!-- NAVIGATION BAR -->
<div class="navbar">
    <div class="nav-left">
        <img src="image/icon.jpeg" alt="Book Icon" />
    </div>
    <div class="nav-center">
        <a href="landing.jsp">Home</a>
        <a href="about.jsp">About</a>
        <a href="contact.jsp">Contact</a>
    </div>
    <div class="nav-right">
        <% if (isLoggedIn) { %>
            <span>👤 <%= userEmail %> | <a href="logout.jsp">Logout</a></span>
        <% } else { %>
            <a href="login.jsp">👤 Login</a>
            <a href="register.jsp">Register</a>
        <% } %>
    </div>
</div>

<!-- CONTENT -->
<div class="content">
    <h2>About Pahana Edu Bookshop</h2>
    <p>Welcome to Pahana Edu Bookshop!</p>
    <p> We are dedicated to providing a wide variety of books for students, educators, and book enthusiasts. Our mission is to promote learning and reading across all age groups with affordable and quality books.</p>
    <p>Founded in 2025, Pahana Edu aims to create a seamless online shopping experience for book lovers in all over the Country. We provide fast delivery, easy payments, and exceptional customer support.</p>
    <p>Because of the though sheduel of the every one they cannot waste their time while looking for physical book shop and thats why we create
    you a these kind online facilities forom our book shop</p>
</div>

<!-- FOOTER -->
<div class="footer">
    <p>© 2025 Pahana Edu Bookshop | Designed by Supun</p>
</div>

</body>
</html>
