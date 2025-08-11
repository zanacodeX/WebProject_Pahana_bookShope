<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ page import="dao.BookDAO" %>
<%@ page import="dto.Book" %>
<%@ page import="java.util.List" %>
<%
    BookDAO dao = new BookDAO();
    List<Book> books = dao.getAllBooks();

    String userType = (String) session.getAttribute("userType");
    String userEmail = (String) session.getAttribute("userEmail");
    boolean isLoggedIn = (userEmail != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Shop Landing Page</title>
    
    <style>
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
   
        
        
        }
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
        .books-container {
            display: flex;
            flex-wrap: wrap;
            padding: 30px;
            justify-content: center;
        }
        .book-card {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            margin: 15px;
            padding: 20px;
            width: 220px;
            text-align: center;
            transition: transform 0.2s ease-in-out;
        }
        .book-card:hover {
            transform: translateY(-5px);
        }
        .book-card img {
            width: 100%;
            height: 220px;
            object-fit: cover;
            border-radius: 8px;
        }
        .book-card h3 {
            color: #333;
        }
        .book-card p {
            color: #555;
            font-size: 14px;
        }
        .cart-button {
            background-color: #28a745;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 10px;
            transition: background-color 0.3s ease;
        }
        .cart-button:hover {
            background-color: #218838;
        }
        h2 {
            text-align: center;
            margin-top: 30px;
            color: Yellow;
            
        }
        .footer {
            margin-top: 30px;
            background-color: #333;
            color: white;
            padding: 15px;
            text-align: center;
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

<!-- BOOK LISTING -->
<h2>Available Books</h2>
<div class="books-container">
    <% if (books != null && !books.isEmpty()) {
        for (Book book : books) { %>
            <div class="book-card">
                <img src="<%= book.getImagePath() %>" alt="Book Image" onerror="this.src='images/default.jpg';" />
                <h3><%= book.getName() %></h3>
                <p>Author: <%= book.getAuthor() %></p>
                <p>Category: <%= book.getCategory() %></p>
                <p>Price: ₹<%= book.getPrice() %></p>
                <form action="cart.jsp" method="post" onsubmit="return checkLogin();">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button class="cart-button">Add to Cart</button>
                </form>
            </div>
    <%  }
      } else { %>
        <p><strong>No books available.</strong></p>
    <% } %>
</div>

<!-- FOOTER -->
<div class="footer">
    <p>© 2025 Pahana Edu Bookshop | Designed by Supun</p>
</div>

<script>
    function checkLogin() {
        <% if (!isLoggedIn) { %>
            alert('Please log in to add books to your cart.');
            window.location.href = 'login.jsp';
            return false;
        <% } else { %>
            return true;
        <% } %>
    }
</script>

</body>
</html>
