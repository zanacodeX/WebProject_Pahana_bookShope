<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.BookDAO" %>
<%@ page import="dto.Book" %>

<%
    String bookIdParam = request.getParameter("bookId");
    Book book = null;

    if (bookIdParam != null) {
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookDAO dao = new BookDAO();
            book = dao.getBookById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Place Order</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .order-container {
            max-width: 500px;
            margin: 0 auto;
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 8px;
        }
        .order-container img {
            max-width: 100%;
            height: auto;
            margin-bottom: 15px;
        }
        label { font-weight: bold; display: block; margin-top: 15px; }
        input[type="number"] {
            width: 60px; padding: 5px; font-size: 16px; margin-top: 5px;
        }
        .total-price {
            margin-top: 15px; font-size: 18px; font-weight: bold;
        }
        .submit-button {
            margin-top: 25px; background-color: #28a745; color: white;
            padding: 10px 20px; font-size: 16px; border: none;
            cursor: pointer; border-radius: 5px;
        }
        .submit-button:hover { background-color: #218838; }
        .error { color: red; font-weight: bold; text-align: center; }
    </style>
</head>
<body>

<div class="order-container">
    <% if (book != null) { %>
        <h2>Place Order for: <%= book.getName() %></h2>
        <img src="<%= book.getImagePath() %>" alt="Book Image" onerror="this.src='images/default.jpg';" />
        <p><strong>Author:</strong> <%= book.getAuthor() %></p>
        <p><strong>Category:</strong> <%= book.getCategory() %></p>
        <p><strong>Price (per unit):</strong> ₹<span id="price"><%= book.getPrice() %></span></p>

        <form action="placeOrder" method="post" onsubmit="return validateOrder()">
            <input type="hidden" name="book_id" value="<%= book.getId() %>" />
            <input type="hidden" name="book_name" value="<%= book.getName() %>" />
            <input type="hidden" name="author" value="<%= book.getAuthor() %>" />
            <input type="hidden" name="unit_price" value="<%= book.getPrice() %>" />

            <label for="units">Select Quantity:</label>
            <input type="number" id="units" name="quantity" value="1" min="1" oninput="calculateTotal()" required>

            <div class="total-price">
                Total Price: ₹<span id="totalPrice"><%= book.getPrice() %></span>
            </div>

            <button type="submit" class="submit-button">Confirm Order</button>
        </form>
    <% } else { %>
        <p class="error">Invalid book selected. Please go back and try again.</p>
    <% } %>
</div>

<script>
    const pricePerUnit = <%= book != null ? book.getPrice() : 0 %>;

    function calculateTotal() {
        const unitsInput = document.getElementById('units');
        const totalPriceSpan = document.getElementById('totalPrice');
        let units = parseInt(unitsInput.value);
        if (isNaN(units) || units < 1) {
            units = 1;
            unitsInput.value = 1;
        }
        const total = (pricePerUnit * units).toFixed(2);
        totalPriceSpan.textContent = total;
    }

    function validateOrder() {
        const unitsInput = document.getElementById('units');
        if (unitsInput.value < 1) {
            alert("Please select a valid quantity.");
            unitsInput.focus();
            return false;
        }
        return true;
    }
</script>

</body>
</html>
