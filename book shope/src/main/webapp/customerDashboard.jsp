<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.BookDAO" %>
<%@ page import="dto.Book" %>
<%@ page import="dto.Customer" %>
<%@ page import="java.util.List" %>

<%
    BookDAO dao = new BookDAO();
    List<Book> books = dao.getAllBooks();

    Customer customer = (Customer) request.getAttribute("customer");
    String userEmail = (String) session.getAttribute("userEmail");
    boolean isLoggedIn = (userEmail != null);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }

        .navbar {
            background-color: #333;
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar a {
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            cursor: pointer;
        }

        .navbar a:hover {
            background-color: #555;
        }

        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            right: 20px;
            top: 60px;
            background-color: white;
            color: black;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 320px;
            padding: 20px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.2);
        }

        .modal h3 {
            margin-top: 0;
            border-bottom: 1px solid #ccc;
            padding-bottom: 8px;
        }

        .modal p {
            margin: 8px 0;
        }

        .close-btn {
            color: red;
            float: right;
            font-weight: bold;
            font-size: 18px;
            cursor: pointer;
        }

        .book-card {
            border: 1px solid #ccc;
            margin: 10px;
            padding: 10px;
            width: 200px;
            text-align: center;
            display: inline-block;
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

<!-- NAVBAR -->
<div class="navbar">
    <div>
        <a href="customerDashboard.jsp">Dashboard</a>
        <a href="customerOrderStatus"> Orders Status</a>
        <a href="completedOrders">Completed Orders</a>
    </div>
    <div>
        <!-- Always show Profile link -->
        <a onclick="toggleModal()">Profile</a>

        <% if (isLoggedIn) { %>
            | <a href="LogoutServlet">Logout</a>
        <% } else { %>
            | <a href="login.jsp">Login</a>
            <a href="register.jsp">Register</a>
        <% } %>
    </div>
</div>

<!-- PROFILE MODAL -->
<div class="modal" id="profileModal">
    <span class="close-btn" onclick="toggleModal()">✖</span>
    <h3>Customer Profile</h3>
    <% if (customer != null) { %>
        <p><strong>Name:</strong> <%= customer.getName() %></p>
        <p><strong>Account #:</strong> <%= customer.getAccountNumber() %></p>
        <p><strong>Address:</strong> <%= customer.getAddress() %></p>
        <p><strong>Phone:</strong> <%= customer.getPhone() %></p>
        <p><strong>Units Consumed:</strong> <%= customer.getUnitsConsumed() %></p>
    <% } else { %>
        <p>No customer data available.</p>
    <% } %>
</div>

<!-- BOOK LISTING -->
<h2 style="text-align:center; margin-top:20px;">Books Available</h2>
<div class="books-container" style="padding: 20px;">
    <% if (books != null && !books.isEmpty()) {
        for (Book book : books) { %>
            <div class="book-card">
                <img src="<%= book.getImagePath() %>" alt="Book Image" onerror="this.src='images/default.jpg';" />
                <h3><%= book.getName() %></h3>
                <p>Author: <%= book.getAuthor() %></p>
                <p>Category: <%= book.getCategory() %></p>
                <p>Price: ₹<%= book.getPrice() %></p>
                <!-- NO login check on cart button -->
                <form action="placeOrder.jsp" method="post">
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

<!-- SCRIPTS -->
<script>
    function toggleModal() {
        const modal = document.getElementById("profileModal");
        modal.style.display = (modal.style.display === "block") ? "none" : "block";
    }

    window.onclick = function(event) {
        const modal = document.getElementById("profileModal");
        if (event.target !== modal && !event.target.closest('a[onclick="toggleModal()"]')) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>
