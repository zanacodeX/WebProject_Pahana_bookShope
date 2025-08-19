<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.net.URLDecoder" %>
    <%@ page import="java.util.List" %>
<%@ page import="dao.BookDAO" %>
<%@ page import="dto.Book" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
    <title>Manage Books</title>
    <style>
        body {
        font-family: "Segoe UI", Arial, sans-serif;
        background: #f4f6f9;
        margin: 0;
        padding: 0;
    }
    header {
        background: #2c3e50;
        color: white;
        padding: 15px;
        text-align: center;
        position: relative;
    }
    header h2 {
        margin: 0;
        font-size: 28px;
    }
    .back-btn {
        position: absolute;
        left: 20px;
        top: 15px;
        background: #3498db;
        color: white;
        border: none;
        padding: 8px 15px;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
        transition: 0.3s;
    }
    .back-btn:hover {
        background: #2980b9;
    }
    .container {
        width: 90%;
        max-width: 1100px;
        margin: 30px auto;
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .form-container {
        margin-bottom: 20px;
    }
    input[type="text"], input[type="submit"], button {
        padding: 10px;
        margin: 5px 0;
        border-radius: 5px;
        border: 1px solid #ccc;
        font-size: 14px;
    }
    input[type="text"] {
        width: 70%;
    }
    input[type="submit"], button {
        background: #27ae60;
        color: white;
        border: none;
        cursor: pointer;
        transition: 0.3s;
    }
    input[type="submit"]:hover, button:hover {
        background: #219150;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    table, th, td {
        border: 1px solid #ddd;
    }
    th {
        background: #34495e;
        color: white;
    }
    th, td {
        padding: 12px;
        text-align: center;
    }
    img {
        width: 60px;
        height: auto;
        border-radius: 5px;
    }
    .actions button {
        background: #e74c3c;
        margin: 0 5px;
    }
    .actions button:first-child {
        background: #f39c12;
    }
    .actions button:hover {
        opacity: 0.85;
    }
    </style>
</head>
<body>
<header>
    <form action="adminDashboard.jsp" method="get" style="display:inline;">
        <button type="submit" class="back-btn">← Back</button>
    </form>
    <h2>Manage Books</h2>
</header>
    <!-- Add Book Form -->
    <div class="form-container">
    <form action="addBook.jsp" method="get">
        <button type="submit">Add New Book</button>
    </form>
</div>

    <!-- Search Form -->
    <div class="form-container">
        <form method="get" action="manageBooks.jsp">
            <input type="text" name="search" placeholder="Search by book title or author">
            <input type="submit" value="Search">
        </form>
    </div>

    <!-- Book List -->
    <div class="table-container">
        <h3>Book List</h3>
        <table>
            <tr>
                <th>Book Image</th>
                <th>Book Name</th>
                <th>Author</th>
                <th>Category</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>

            <%
                BookDAO dao = new BookDAO();
                String searchTerm = request.getParameter("search");
                List<Book> books = (searchTerm != null && !searchTerm.isEmpty()) ?
                    dao.searchBooks(searchTerm) : dao.getAllBooks();

                for (Book book : books) {
            %>
           <tr>
           <%-- Import URLDecoder --%>


<%
    String decodedImageURL = URLDecoder.decode(book.getImagePath(), "UTF-8");
%>
    <td><img src="<%= book.getImagePath() %>" width="100" height="150" alt="Book Image"></td>
    <td><%= book.getName() %></td>
    <td><%= book.getAuthor() %></td>
    <td><%= book.getCategory() %></td>
    <td>Rs. <%= book.getPrice() %></td>
    
    <td class="actions">
        <form action="EditBookServlet" method="get" style="display:inline;">
            <input type="hidden" name="id" value="<%= book.getId() %>">
            <button type="submit">Edit</button>
        </form>
        <form action="deleteBook" method="post" style="display:inline;" onsubmit="return confirm('Are you sure?');">
            <input type="hidden" name="id" value="<%= book.getId() %>">
            <button type="submit">Delete</button>
        </form>
    </td>
</tr>
            <% } %>
        </table>
    </div>

</body>
</html>