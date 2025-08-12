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
<title>Insert title here</title>
</head>
<meta charset="UTF-8">
    <title>Manage Books</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        h2 { color: #333; }
        .form-container, .table-container { margin-bottom: 30px; }
        input, select { padding: 5px; margin: 5px 0; width: 100%; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        table, th, td { border: 1px solid #ccc; }
        th, td { padding: 10px; text-align: center; }
        img { width: 60px; height: auto; }
        .actions button { margin: 0 5px; }
    </style>
</head>
<body>

    <h2>Manage Books</h2>

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