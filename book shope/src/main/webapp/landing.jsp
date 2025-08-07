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
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .navbar {
            background-color: #333;
            color: white;
            padding: 10px;
            display: flex;
            justify-content: space-between;
        }
        .navbar a {
            color: white;
            padding: 10px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #555;
        }
        .books-container {
            display: flex;
            flex-wrap: wrap;
            padding: 20px;
        }
        .book-card {
            border: 1px solid #ccc;
            margin: 10px;
            padding: 10px;
            width: 200px;
            text-align: center;
        }
        .book-card img {
            width: 100%;
            height: 200px;
        }
        .footer {
            margin-top: 30px;
            background-color: #333;
            color: white;
            padding: 10px;
            text-align: center;
        }
        .cart-button {
            background-color: #28a745;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            margin-top: 10px;
        }
        .cart-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<!-- NAVIGATION BAR -->
<div class="navbar">
    <div>
        <a href="landing.jsp">Home</a>
        <a href="about.jsp">About</a>
        <a href="contact.jsp">Contact</a>
    </div>
    <div>
        <% if (isLoggedIn) { %>
            <span>Welcome, <%= userEmail %> | <a href="logout.jsp">Logout</a></span>
        <% } else { %>
            <a href="login.jsp">Login</a>
            <a href="register.jsp">Register</a>
        <% } %>
    </div>
</div>

<!-- BOOK LISTING -->
<h2 style="text-align:center; margin-top:20px;">Books Available</h2>
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
