package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import dao.BookDAO;
import dto.Book;

@MultipartConfig
public class AddBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Read form fields
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));

        // 2. Get image URL from input
        String imageUrl = request.getParameter("imageUrl");

        // 3. Save book to DB
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setCategory(category);
        book.setPrice(price);
        book.setImagePath(imageUrl);  // saving image URL directly

        BookDAO dao = new BookDAO();
        dao.insertBook(book);

        // 4. Redirect back to dashboard
        response.sendRedirect("manageBooks.jsp");
    }
}
