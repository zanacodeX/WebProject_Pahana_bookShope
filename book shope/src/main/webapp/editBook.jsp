<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Book</title>
<style>
    body { font-family: Arial; padding: 20px; }
    label { display: block; margin-top: 10px; }
    input, select { padding: 5px; width: 100%; }
    button { padding: 8px 15px; margin-top: 10px; }
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