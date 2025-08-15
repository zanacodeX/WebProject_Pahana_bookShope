package servlet;

import controller.BookController;
import dto.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet("/EditBookServlet")
public class EditBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookController bookController = new BookController();

    // Load book for edit (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int bookId = Integer.parseInt(idParam);
                Book book = bookController.getBookById(bookId);
                request.setAttribute("book", book);
                request.getRequestDispatcher("editBook.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("manageBooks.jsp?error=Invalid+book+ID");
            }
        } else {
            response.sendRedirect("manageBooks.jsp?error=No+book+ID+provided");
        }
    }

    // Handle update (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            String imagePath = request.getParameter("imagePath");

            Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setAuthor(author);
            book.setCategory(category);
            book.setPrice(price);
            book.setImagePath(imagePath);

            boolean updated = bookController.updateBook(book);

            if (updated) {
                response.sendRedirect("manageBooks.jsp?message=Book+updated+successfully");
            } else {
                request.setAttribute("book", book);
                request.setAttribute("error", "Failed to update book");
                request.getRequestDispatcher("editBook.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageBooks.jsp?error=Error+updating+book");
        }
    }
}
