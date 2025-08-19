<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Book</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f9;
        margin: 0;
        padding: 0;
    }

    h2 {
        text-align: center;
        color: #2c3e50;
        margin-top: 30px;
        font-size: 32px;
    }

    form {
        max-width: 450px;
        margin: 30px auto;
        background-color: #ffffff;
        padding: 25px 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }

    label {
        display: block;
        margin-top: 10px;
        font-weight: bold;
        color: #34495e;
    }

    input, select {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        margin-bottom: 15px;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 14px;
        box-sizing: border-box;
    }

    button {
        padding: 10px 20px;
        font-size: 16px;
        border-radius: 6px;
        border: none;
        cursor: pointer;
        transition: 0.3s ease;
        margin-right: 10px;
    }

    button[type="submit"] {
        background-color: #4CAF50;
        color: white;
    }

    button[type="submit"]:hover {
        background-color: #45a049;
    }

    button[type="button"] {
        background-color: #3498db;
        color: white;
    }

    button[type="button"]:hover {
        background-color: #2980b9;
    }

    p[style*="color:red"] {
        text-align: center;
        font-weight: bold;
        margin-top: 20px;
    }
</style>
</head>
<body>

<%
    Book book = (Book) request.getAttribute("book");
    if (book == null) {
%>
    <p style="color:red;">Book not found.</p>
<%
    } else {
%>

<h2>Edit Book</h2>
<form action="EditBookServlet" method="post">
    <input type="hidden" name="id" value="<%= book.getId() %>">

    <label>Book Name:</label>
    <input type="text" name="name" value="<%= book.getName() %>" required>

    <label>Author:</label>
    <input type="text" name="author" value="<%= book.getAuthor() %>" required>

    <label>Category:</label>
    <input type="text" name="category" value="<%= book.getCategory() %>" required>

    <label>Price:</label>
    <input type="number" step="0.01" name="price" value="<%= book.getPrice() %>" required>

    <label>Image Path:</label>
    <input type="text" name="imagePath" value="<%= book.getImagePath() %>" required>

    <br><br>
    <button type="submit">Update</button>
    <button type="button" onclick="window.location.href='manageBooks.jsp';">Cancel</button>
</form>

<%
    }
%>

</body>
</html>